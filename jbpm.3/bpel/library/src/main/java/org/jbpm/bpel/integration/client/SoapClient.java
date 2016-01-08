/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the JBPM BPEL PUBLIC LICENSE AGREEMENT as
 * published by JBoss Inc.; either version 1.0 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.jbpm.bpel.integration.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Binding;
import javax.wsdl.BindingFault;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.Fault;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPFault;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import com.ibm.wsdl.extensions.soap.SOAPConstants;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.exe.FaultInstance;
import org.jbpm.bpel.integration.jms.IntegrationControl;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:06:10 $
 */
public class SoapClient {

  private final Binding binding;
  private final URL address;

  private static final Log log = LogFactory.getLog(SoapClient.class);

  public SoapClient(Port port) {
    binding = port.getBinding();
    SOAPBinding soapBinding = (SOAPBinding) WsdlUtil.getExtension(binding.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_BINDING);
    // exclude non-soap bindings
    if (soapBinding == null) {
      throw new IllegalArgumentException("non-soap bindings not supported: "
          + binding);
    }
    // exclude non-http transport protocols
    String transport = soapBinding.getTransportURI();
    if (!SoapBindConstants.HTTP_TRANSPORT_URI.equals(transport)) {
      throw new IllegalArgumentException("non-http transports not supported: "
          + transport);
    }
    // exclude non-soap ports
    SOAPAddress soapAddress = (SOAPAddress) WsdlUtil.getExtension(port.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_ADDRESS);
    if (soapAddress == null) {
      throw new BpelException("non-soap ports not supported: " + port);
    }
    // exclude non-url address locations
    String location = soapAddress.getLocationURI();
    try {
      address = new URL(location);
    }
    catch (MalformedURLException e) {
      throw new BpelException("invalid address location: " + location, e);
    }
  }

  public Map call(String operation, Map inputParts) throws BpelFaultException {
    try {
      SOAPMessage soapOutput = callImpl(operation, inputParts);
      // prepare output message
      SOAPEnvelope envelope = soapOutput.getSOAPPart().getEnvelope();
      if (envelope.getBody().hasFault()) {
        FaultInstance faultInstance = readFault(operation, envelope);
        throw new BpelFaultException(faultInstance);
      }
      return readMessage(operation, envelope);
    }
    catch (SOAPException e) {
      throw new BpelException("failed to call soap endpoint: " + getAddress(),
          e);
    }
  }

  public void callOneWay(String operation, Map inputParts) {
    try {
      callImpl(operation, inputParts);
    }
    catch (SOAPException e) {
      throw new BpelException("failed to call soap endpoint: " + getAddress(),
          e);
    }
  }

  private URL getAddress() {
    return address;
  }

  protected SOAPMessage callImpl(String operation, Map inputParts)
      throws SOAPException {
    // prepare input message
    SOAPMessage soapInput = writeMessage(operation, inputParts);
    // call endpoint
    SOAPConnection soapConnection = SOAPConnectionFactory.newInstance()
        .createConnection();
    try {
      return soapConnection.call(soapInput, getAddress());
    }
    finally {
      try {
        soapConnection.close();
      }
      catch (SOAPException e) {
        log.warn("coult not close soap connection", e);
      }
    }
  }

  public Binding getBinding() {
    return binding;
  }

  public SOAPMessage writeMessage(String operation, Map inputParts)
      throws SOAPException {
    BindingOperation bindOperation = binding.getBindingOperation(operation,
        null,
        null);
    // create soap message
    MessageFactory messageFactory = MessageFactory.newInstance();
    SOAPMessage soapMessage = messageFactory.createMessage();
    SOAPPart soapPart = soapMessage.getSOAPPart();
    // obtain info on the operation as a whole
    SOAPOperation soapOperation = (SOAPOperation) WsdlUtil.getExtension(bindOperation.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_OPERATION);
    // set the value of the SOAPAction HTTP header
    String action = soapOperation.getSoapActionURI();
    soapPart.setMimeHeader(SoapBindConstants.SOAP_ACTION_HEADER, action);
    // determine whether the operation is rpc-oriented or document-oriented
    String style = soapOperation.getStyle();
    if (style == null) {
      // default to the value specified in the soap:binding element
      SOAPBinding soapBinding = (SOAPBinding) WsdlUtil.getExtension(getBinding().getExtensibilityElements(),
          SOAPConstants.Q_ELEM_SOAP_BINDING);
      style = soapBinding.getStyle();
      if (style == null) {
        // if the soap:binding element does not specify a style, 'document' is
        // assumed
        style = SoapBindConstants.DOCUMENT_STYLE;
      }
    }
    SOAPEnvelope envelope = soapPart.getEnvelope();
    // write body element
    if (SoapBindConstants.DOCUMENT_STYLE.equals(style)) {
      writeDocumentBody(bindOperation, inputParts, envelope);
    }
    else {
      writeRpcBody(bindOperation, inputParts, envelope);
    }
    return soapMessage;
  }

  protected void writeRpcBody(BindingOperation bindOperation, Map inputParts,
      SOAPEnvelope envelope) throws SOAPException {
    // obtain info on how message parts appear inside the body element
    BindingInput bindInput = bindOperation.getBindingInput();
    javax.wsdl.extensions.soap.SOAPBody soapBody = (javax.wsdl.extensions.soap.SOAPBody) WsdlUtil.getExtension(bindInput.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_BODY);
    // exclude the use of encodings
    if (SoapBindConstants.ENCODED_USE.equals(soapBody.getUse())) {
      throw new BpelException("encoded use not supported");
    }
    // obtain the definitions of parts which appear inside the body, in
    // parameter order
    List partNames = soapBody.getParts();
    Operation operation = bindOperation.getOperation();
    List parameterOrder = operation.getParameterOrdering();
    if (parameterOrder != null) {
      if (partNames != null) {
        // retain only parts specified by soap:body keeping the parameter order
        parameterOrder = new ArrayList(parameterOrder);
        parameterOrder.retainAll(partNames);
      }
      partNames = parameterOrder;
    }
    Message message = operation.getInput().getMessage();
    // the message filters out parts that it does not define
    Collection parts = getParts(message, partNames);
    // create operation wrapper
    Name operationName = envelope.createName(bindOperation.getName(),
        "soapBodyNS",
        soapBody.getNamespaceURI());
    SOAPBodyElement operationElem = envelope.getBody()
        .addBodyElement(operationName);
    // fill in part values
    Iterator partIt = parts.iterator();
    while (partIt.hasNext()) {
      Part part = (Part) partIt.next();
      String partName = part.getName();
      // create part accessor under operation wrapper
      SOAPElement operPartAccessor = operationElem.addChildElement(partName);
      // get part accessor from input message data
      Element inputPartAccessor = (Element) inputParts.get(partName);
      // copy input part to operation part
      XmlUtil.copy(operPartAccessor, inputPartAccessor);
    }
  }

  protected void writeDocumentBody(BindingOperation bindOperation,
      Map inputParts, SOAPEnvelope envelope) throws SOAPException {
    // obtain info on how message parts appear inside the body element
    BindingInput bindInput = bindOperation.getBindingInput();
    javax.wsdl.extensions.soap.SOAPBody soapBody = (javax.wsdl.extensions.soap.SOAPBody) WsdlUtil.getExtension(bindInput.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_BODY);
    // exclude the use of encodings
    if (SoapBindConstants.ENCODED_USE.equals(soapBody.getUse())) {
      throw new BpelException("encoded use not supported");
    }
    // obtain the definitions of parts which appear inside the body
    Message message = bindOperation.getOperation().getInput().getMessage();
    Collection parts = getParts(message, soapBody.getParts());
    // fill in part values
    Iterator partIt = parts.iterator();
    switch (parts.size()) {
    case 0:
      break;
    case 1: {
      Part part = (Part) partIt.next();
      if (part.getTypeName() != null) {
        // a schema type defines the content of the body
        XmlUtil.copy(envelope.getBody(),
            (Element) inputParts.get(part.getName()));
      }
      else {
        writeDocumentPart(part, inputParts, envelope);
      }
      break;
    }
    default:
      while (partIt.hasNext()) {
        Part part = (Part) partIt.next();
        writeDocumentPart(part, inputParts, envelope);
      }
    }
  }

  private static Collection getParts(Message message, List partNames) {
    return message.getOrderedParts(partNames);
  }

  private static void writeDocumentPart(Part part, Map inputParts,
      SOAPEnvelope envelope) throws SOAPException {
    QName elementName = part.getElementName();
    if (elementName == null) {
      throw new BpelException("only one part may be specified if a "
          + "type defines the contents of the body for document style");
    }
    String namespaceUri = elementName.getNamespaceURI();
    String localName = elementName.getLocalPart();
    // get part element from input message
    Element inputPartElem = (Element) inputParts.get(part.getName());
    // create part element directly under soap body
    Name bodyPartName = envelope.createName(localName,
        inputPartElem.getPrefix(),
        namespaceUri);
    SOAPElement bodyPartElem = envelope.getBody().addBodyElement(bodyPartName);
    XmlUtil.copy(bodyPartElem, inputPartElem);
  }

  public Map readMessage(String operation, SOAPEnvelope envelope)
      throws SOAPException {
    BindingOperation bindOperation = binding.getBindingOperation(operation,
        null,
        null);
    // obtain info on the operation as a whole
    SOAPOperation soapOperation = (SOAPOperation) WsdlUtil.getExtension(bindOperation.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_OPERATION);
    // determine whether the operation is rpc-oriented or document-oriented
    String style = soapOperation.getStyle();
    if (style == null) {
      // default to the value specified in the soap:binding element
      SOAPBinding soapBinding = (SOAPBinding) WsdlUtil.getExtension(binding.getExtensibilityElements(),
          SOAPConstants.Q_ELEM_SOAP_BINDING);
      style = soapBinding.getStyle();
      if (style == null) {
        // if the soap:binding element does not specify a style, 'document' is
        // assumed
        style = SoapBindConstants.DOCUMENT_STYLE;
      }
    }
    // read body element
    Map outputParts;
    if (SoapBindConstants.DOCUMENT_STYLE.equals(style)) {
      outputParts = readDocumentBody(bindOperation, envelope);
    }
    else {
      outputParts = readRpcBody(bindOperation, envelope);
    }
    return outputParts;
  }

  protected Map readRpcBody(BindingOperation bindOperation,
      SOAPEnvelope envelope) throws SOAPException {
    // obtain info on how message parts appear inside the body element
    List extensions = bindOperation.getBindingOutput()
        .getExtensibilityElements();
    javax.wsdl.extensions.soap.SOAPBody soapBody = (javax.wsdl.extensions.soap.SOAPBody) WsdlUtil.getExtension(extensions,
        SOAPConstants.Q_ELEM_SOAP_BODY);
    // exclude the use of encodings
    if (SoapBindConstants.ENCODED_USE.equals(soapBody.getUse())) {
      throw new BpelException("encoded use not supported");
    }
    // obtain the definitions of parts which appear inside the body
    Message message = bindOperation.getOperation().getOutput().getMessage();
    Collection parts = getParts(message, soapBody.getParts());
    // get the operation wrapper
    SOAPElement operationElem = XmlUtil.getElement(envelope.getBody());
    // create the output message instance
    Map outputParts = new HashMap();
    // fill in part values
    Iterator partIt = parts.iterator();
    while (partIt.hasNext()) {
      Part part = (Part) partIt.next();
      String partName = part.getName();
      // get part accessor from operation wrapper
      SOAPElement operPartAccessor = XmlUtil.getElement(operationElem, partName);
      // create part accessor in output message
      Element outputPartAccessor = XmlUtil.createElement(null, partName);
      outputParts.put(partName, outputPartAccessor);
      // copy operation part to output part
      XmlUtil.copy(outputPartAccessor, operPartAccessor);
    }
    return outputParts;
  }

  protected Map readDocumentBody(BindingOperation bindOperation,
      SOAPEnvelope envelope) throws SOAPException {
    // obtain info on how message parts appear inside the body element
    BindingOutput bindOutput = bindOperation.getBindingOutput();
    javax.wsdl.extensions.soap.SOAPBody soapBody = (javax.wsdl.extensions.soap.SOAPBody) WsdlUtil.getExtension(bindOutput.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_BODY);
    // exclude the use of encodings
    if (SoapBindConstants.ENCODED_USE.equals(soapBody.getUse())) {
      throw new BpelException("encoded use not supported");
    }
    // obtain the definitions of parts which appear inside the body
    Message message = bindOperation.getOperation().getOutput().getMessage();
    Collection parts = getParts(message, soapBody.getParts());
    // create the output message data
    Map outputParts = new HashMap();
    // fill in part values
    Iterator partIt = parts.iterator();
    switch (parts.size()) {
    case 0:
      break;
    case 1: {
      Part part = (Part) partIt.next();
      /*
       * the contents of a composite body can be defined using a type; in this
       * usage, only one part may be specified
       */
      if (part.getTypeName() != null) {
        // create part accessor in output message
        String partName = part.getName();
        Element outputPartAccessor = XmlUtil.createElement(null, partName);
        outputParts.put(partName, outputPartAccessor);
        // copy body to output part directly
        XmlUtil.copy(outputPartAccessor, envelope.getBody());
      }
      else {
        readDocumentPart(part, outputParts, envelope);
      }
      break;
    }
    default:
      while (partIt.hasNext()) {
        Part part = (Part) partIt.next();
        if (part.getTypeName() != null) {
          throw new BpelException(
              "only one part may be specified if a type defines the contents of a document-style body");
        }
        readDocumentPart(part, outputParts, envelope);
      }
    }
    return outputParts;
  }

  private static void readDocumentPart(Part part, Map outputParts,
      SOAPEnvelope envelope) throws SOAPException {
    // get part element from soap body
    QName elementName = part.getElementName();
    SOAPElement bodyPartElem = XmlUtil.getElement(envelope.getBody(),
        elementName.getNamespaceURI(),
        elementName.getLocalPart());
    // create part element under output message
    Element outputPartElem = XmlUtil.createElement(elementName);
    outputParts.put(part.getName(), outputPartElem);
    // copy body part to output part
    XmlUtil.copy(outputPartElem, bodyPartElem);
  }

  public FaultInstance readFault(String operation, SOAPEnvelope envelope)
      throws SOAPException {
    SOAPElement detail = XmlUtil.getElement(envelope.getBody().getFault(),
        "detail");
    if (detail == null) {
      throw new BpelException("soap fault does not include a detail element");
    }
    BindingOperation bindOperation = binding.getBindingOperation(operation,
        null,
        null);
    // look for a wsdl binding fault matching the contents of the soap fault
    // detail element
    Iterator faultIt = bindOperation.getOperation()
        .getFaults()
        .values()
        .iterator();
    while (faultIt.hasNext()) {
      Fault fault = (Fault) faultIt.next();
      // part definition
      Message message = fault.getMessage();
      Map parts = message.getParts();
      if (parts.size() != 1) {
        throw new BpelException("fault message must have a single part");
      }
      Part part = (Part) parts.values().iterator().next();
      QName elementName = part.getElementName();
      if (elementName == null) {
        throw new BpelException("fault message parts must reference an element");
      }
      // locate the element referenced by the part under the soap detail element
      SOAPElement detailPartValue = XmlUtil.getElement(detail,
          elementName.getNamespaceURI(),
          elementName.getLocalPart());
      if (detailPartValue != null) {
        // obtain info on the contents of the soap detail element
        BindingFault bindFault = bindOperation.getBindingFault(fault.getName());
        SOAPFault soapFault = (SOAPFault) WsdlUtil.getExtension(bindFault.getExtensibilityElements(),
            SOAPConstants.Q_ELEM_SOAP_FAULT);
        // exclude the use of encodings
        if (SoapBindConstants.ENCODED_USE.equals(soapFault.getUse())) {
          throw new BpelException("encoded use not supported");
        }
        // create the fault message
        MessageValue messageValue = new MessageValue(getMessageType(message));
        // create part element under fault message
        messageValue.setPart(part.getName(), detailPartValue);
        /*
         * a WSDL fault is identified by a qualified name formed by the target
         * namespace of the corresponding portType and the fault name local to
         * the operation
         */
        QName faultName = new QName(getBinding().getPortType()
            .getQName()
            .getNamespaceURI(), fault.getName());
        return new FaultInstance(faultName, messageValue);
      }
    }
    throw new BpelException(
        "no wsdl fault matches the contents of the soap detail element: "
            + detail);
  }

  private static MessageType getMessageType(Message message) {
    MessageType messageType;
    // see if there is a persistence context
    JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
    if (jbpmContext != null) {
      // load the message type from the database
      IntegrationControl integrationControl = IntegrationControl.getInstance(jbpmContext.getJbpmConfiguration());
      messageType = integrationControl.findProcessDefinition(jbpmContext)
          .getImports()
          .getMessageType(message.getQName());
    }
    else {
      // create a transient message type for test purposes
      messageType = new MessageType(message);
    }
    return messageType;
  }

  public String toString() {
    return new ToStringBuilder(this).append("address", address)
        .append("binding", binding.getQName())
        .toString();
  }
}
