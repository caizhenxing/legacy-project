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
package org.jbpm.bpel.integration.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.Part;
import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.handler.Handler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.rpc.soap.SOAPFaultException;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.bpel.db.IntegrationSession;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.jms.IntegrationConstants;
import org.jbpm.bpel.integration.jms.IntegrationControl;
import org.jbpm.bpel.integration.jms.RequestListener;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class SoapHandler implements Handler {

  private QName[] headers;
  private PortEntry portEntry;
  private long responseTimeout;
  private long oneWayTimeout;

  private Destination destination;
  private Connection jmsConnection;

  /** Name of the port. */
  public static final String PORT_NAME_PARAM = "portName";
  /** Time to wait for response messages, in milliseconds. */
  public static final String RESPONSE_TIMEOUT_PARAM = "responseTimeout";
  /** Time to expire one-way messages, in milliseconds. */
  public static final String ONE_WAY_TIMEOUT_PARAM = "oneWayTimeout";

  /** The fault code for client-side faults */
  public static final QName CLIENT_FAULTCODE = new QName(
      SOAPConstants.URI_NS_SOAP_ENVELOPE, "Client");
  /** The fault code for server-side faults */
  public static final QName SERVER_FAULTCODE = new QName(
      SOAPConstants.URI_NS_SOAP_ENVELOPE, "Server");

  /** The fault string for response timeouts. */
  public static final String TIMEOUT_FAULTSTRING = "The service is not in "
      + "an appropiate state for the requested operation";

  /** The fault string for faults returned by the business process. */
  public static final String BUSINESS_FAULTSTRING = "Business logic fault";

  /** Message context property for the message */
  static final String MESSAGE_PROP = "message";
  /** Message context property for the SOAP fault */
  static final String FAULT_PROP = "fault";

  private static final Log log = LogFactory.getLog(SoapHandler.class);

  /** {@inheritDoc} */
  public void init(HandlerInfo handlerInfo) throws JAXRPCException {
    // save headers
    headers = handlerInfo.getHeaders();
    // get the configuration
    Map handlerConfig = handlerInfo.getHandlerConfig();
    // port name parameter
    String portName = (String) handlerConfig.get(PORT_NAME_PARAM);
    if (portName == null) {
      throw new JAXRPCException("Parameter '" + PORT_NAME_PARAM
          + "' is mandatory in the handler configuration");
    }
    // response timeout parameter
    String receiveTimeoutText = (String) handlerConfig.get(RESPONSE_TIMEOUT_PARAM);
    if (receiveTimeoutText != null) {
      try {
        responseTimeout = Long.parseLong(receiveTimeoutText);
      }
      catch (NumberFormatException e) {
        throw new JAXRPCException("Parameter '" + RESPONSE_TIMEOUT_PARAM
            + "' does not contain a parsable long", e);
      }
    }
    // one-way timeout parameter
    String oneWayTimeoutText = (String) handlerConfig.get(ONE_WAY_TIMEOUT_PARAM);
    if (oneWayTimeoutText != null) {
      try {
        oneWayTimeout = Long.parseLong(oneWayTimeoutText);
      }
      catch (NumberFormatException e) {
        throw new JAXRPCException("Parameter '" + ONE_WAY_TIMEOUT_PARAM
            + "' does not contain a parsable long", e);
      }
    }
    JbpmContext jbpmContext = JbpmConfiguration.getInstance()
        .createJbpmContext();
    try {
      setUp(portName, jbpmContext);
    }
    catch (Exception e) {
      jbpmContext.setRollbackOnly();
      throw new JAXRPCException("could not set up port provider", e);
    }
    finally {
      jbpmContext.close();
    }
  }

  void setUp(String portName, JbpmContext jbpmContext) throws NamingException,
      JMSException {
    InitialContext initialContext = new InitialContext();
    try {
      // retrieve the port entry
      IntegrationControl integrationControl = IntegrationControl.getInstance(jbpmContext.getJbpmConfiguration());
      Context processContext = integrationControl.getProcessContext(initialContext,
          jbpmContext);
      portEntry = (PortEntry) processContext.lookup(portName);
      log.debug("retrieved port entry: " + portName);
      // retrieve the jms administered objects
      Context jmsContext = IntegrationControl.getJmsContext(initialContext);
      destination = (Destination) jmsContext.lookup(portName);
      ConnectionFactory jmsConnectionFactory = (ConnectionFactory) jmsContext.lookup(IntegrationConstants.CONNECTION_FACTORY_NAME);
      // create a jms connection
      jmsConnection = jmsConnectionFactory.createConnection();
      jmsConnection.start();
    }
    finally {
      initialContext.close();
    }
  }

  /** {@inheritDoc} */
  public void destroy() {
    if (jmsConnection != null) {
      try {
        jmsConnection.close();
      }
      catch (JMSException e) {
        log.warn("could not close jms connection", e);
      }
    }
  }

  /** {@inheritDoc} */
  public QName[] getHeaders() {
    return headers;
  }

  /** {@inheritDoc} */
  public boolean handleRequest(MessageContext context) throws JAXRPCException,
      SOAPFaultException {
    if (!(context instanceof SOAPMessageContext)) return true;

    SOAPMessageContext soapContext = (SOAPMessageContext) context;
    JbpmContext jbpmContext = JbpmConfiguration.getInstance()
        .createJbpmContext();
    try {
      Session jmsSession = jmsConnection.createSession(false,
          Session.CLIENT_ACKNOWLEDGE);
      try {
        TemporaryQueue replyTo = sendRequest(soapContext,
            jmsSession,
            jbpmContext);
        if (replyTo != null) {
          try {
            receiveResponse(soapContext, jmsSession, replyTo);
          }
          finally {
            replyTo.delete();
          }
        }
      }
      finally {
        jmsSession.close();
      }
    }
    /*
     * NO need to set jbpm context as rollback only for any exception, since
     * operations in try-block only read definitions from database
     */
    catch (SOAPFaultException e) {
      log.debug("request caused a fault", e);
      soapContext.setProperty(FAULT_PROP, e);
    }
    catch (SOAPException e) {
      log.debug("incoming soap message carries invalid content", e);
      soapContext.setProperty(FAULT_PROP, new SOAPFaultException(
          CLIENT_FAULTCODE, e.getMessage(), null, null));
    }
    catch (JMSException e) {
      throw new JAXRPCException("message delivery failed", e);
    }
    finally {
      jbpmContext.close();
    }
    return true;
  }

  /** {@inheritDoc} */
  public boolean handleResponse(MessageContext context) throws JAXRPCException {
    if (!(context instanceof SOAPMessageContext) ||
    // no operation name -> no response required
        !context.containsProperty(IntegrationConstants.OPERATION_NAME_PROP))
      return true;

    SOAPMessageContext soapContext = (SOAPMessageContext) context;
    JbpmContext jbpmContext = JbpmConfiguration.getInstance()
        .createJbpmContext();
    try {
      if (soapContext.containsProperty(FAULT_PROP))
        writeFault(soapContext);
      else
        writeResponse(soapContext, jbpmContext);
    }
    /*
     * NO need to set jbpm context as rollback only for any other exception,
     * since operations in try-block only read definitions from database
     */
    catch (SOAPException e) {
      throw new JAXRPCException("could not compose outbound soap message", e);
    }
    finally {
      jbpmContext.close();
    }
    return true;
  }

  /** {@inheritDoc} */
  public boolean handleFault(MessageContext context) throws JAXRPCException {
    return true;
  }

  public PortEntry getPortEntry() {
    return portEntry;
  }

  public long getResponseTimeout() {
    return responseTimeout;
  }

  public long getOneWayTimeout() {
    return oneWayTimeout;
  }

  protected Destination getDestination() {
    return destination;
  }

  protected Connection getJmsConnection() {
    return jmsConnection;
  }

  protected TemporaryQueue sendRequest(SOAPMessageContext soapContext,
      Session jmsSession, JbpmContext jbpmContext) throws SOAPException,
      JMSException {
    // create a jms message to carry the incoming message
    ObjectMessage jmsRequest = jmsSession.createObjectMessage();

    // stamp the partner relationship on behalf of which this request comes
    long partnerLinkId = portEntry.getPartnerLinkId();
    jmsRequest.setLongProperty(IntegrationConstants.PARTNER_LINK_ID_PROP,
        partnerLinkId);

    /*
     * determine the operation to perform - the operation wrapper should be the
     * soap body's only child
     */
    SOAPBody soapBody = soapContext.getMessage().getSOAPBody();
    SOAPElement operationWrapper = XmlUtil.getElement(soapBody);
    String operationName = operationWrapper.getLocalName();
    // stamp the operation name
    jmsRequest.setStringProperty(IntegrationConstants.OPERATION_NAME_PROP,
        operationName);

    // get the operation definition
    PartnerLinkDefinition partnerLink = IntegrationSession.getInstance(jbpmContext)
        .loadPartnerLinkDefinition(partnerLinkId);
    Operation operation = partnerLink.getMyRole()
        .getPortType()
        .getOperation(operationName, null, null);
    log.debug("received request: partnerLink=" + partnerLink + ", operation="
        + operationName);

    // extract message content
    javax.wsdl.Message wsdlRequest = operation.getInput().getMessage();
    Map requestParts = getRequestParts(operationWrapper, wsdlRequest);
    jmsRequest.setObject((Serializable) requestParts);

    // fill message properties
    BpelDefinition process = (BpelDefinition) jbpmContext.getGraphSession()
        .findProcessDefinition(portEntry.getProcessName(),
            portEntry.getProcessVersion());
    MessageType requestType = process.getImports()
        .getMessageType(wsdlRequest.getQName());
    fillCorrelationProperties(requestParts,
        jmsRequest,
        requestType.getPropertyAliases());

    // set up producer
    MessageProducer producer = jmsSession.createProducer(destination);

    try {
      // arrange a reply destination, if the operation is request/response
      TemporaryQueue replyTo = null;
      if (operation.getStyle().equals(OperationType.REQUEST_RESPONSE)) {
        replyTo = jmsSession.createTemporaryQueue();
        jmsRequest.setJMSReplyTo(replyTo);
        // have jms discard request message if response timeout expires
        producer.setTimeToLive(responseTimeout);
        // remember operation name for handling response
        soapContext.setProperty(IntegrationConstants.OPERATION_NAME_PROP,
            operationName);
      }
      else {
        producer.setTimeToLive(oneWayTimeout);
      }

      // send request message
      producer.send(jmsRequest);
      log.debug("sent request: " + RequestListener.messageToString(jmsRequest));

      return replyTo;
    }
    finally {
      // release producer resources
      producer.close();
    }
  }

  private static Map getRequestParts(SOAPElement operationWrapper,
      javax.wsdl.Message requestMessage) throws JMSException, SOAPException {
    HashMap inputParts = new HashMap();
    Name nilName = SOAPFactory.newInstance().createName(BpelConstants.ATTR_NIL,
        null,
        BpelConstants.NS_XML_SCHEMA_INSTANCE);

    // iterate through input message parts
    Iterator partIt = requestMessage.getParts().values().iterator();
    while (partIt.hasNext()) {
      Part requestPart = (Part) partIt.next();
      // get part accessor from operation wrapper
      String partName = requestPart.getName();
      SOAPElement partAccessor = XmlUtil.getElement(operationWrapper, partName);

      // BPEL-68 check for xsi:nil
      String nilValue = partAccessor.getAttributeValue(nilName);
      if (nilValue == null
          || DatatypeUtil.parseBoolean(nilValue) != Boolean.TRUE) {
        SOAPElement partValue = getPartValue(partAccessor, requestPart);
        // create a dom element with the same name as the operation part
        Element inputPart = XmlUtil.createElement(partValue.getNamespaceURI(),
            partValue.getLocalName());
        // add part to input message
        inputParts.put(partName, inputPart);
        // copy operation part to input part
        XmlUtil.copy(inputPart, partValue);
      }
      // else: do not copy the operation part
    }
    return inputParts;
  }

  private static SOAPElement getPartValue(SOAPElement partAccessor,
      Part requestPart) {
    SOAPElement partValue;
    // check referenced schema definition
    QName elementName = requestPart.getElementName();
    if (elementName != null) {
      // an element defines the part, take that element out of the part accessor
      partValue = XmlUtil.getElement(partAccessor,
          elementName.getNamespaceURI(),
          elementName.getLocalPart());
    }
    else {
      // a schema type defines the part, the part accessor is of that type
      partValue = partAccessor;
    }
    return partValue;
  }

  /**
   * Gets the values of message properties from the request message parts and
   * sets them in the property fields of the JMS message.
   * @param requestParts the parts extracted from the request SOAP message
   * @param jmsRequest the JMS message whose properties will be set
   * @param propertyAliases the property aliases associated with the request
   *          message type
   * @throws JMSException
   */
  private static void fillCorrelationProperties(Map requestParts,
      ObjectMessage jmsRequest, Map propertyAliases) throws JMSException {
    // easy way out: no property aliases
    if (propertyAliases == null) return;
    // iterate through the property aliases associated with the message type
    Iterator aliasEntryIt = propertyAliases.entrySet().iterator();
    while (aliasEntryIt.hasNext()) {
      Entry aliasEntry = (Entry) aliasEntryIt.next();
      QName propertyName = (QName) aliasEntry.getKey();
      PropertyAlias alias = (PropertyAlias) aliasEntry.getValue();
      // get part accessor from operation wrapper
      String partName = alias.getPart();
      Object value = requestParts.get(partName);
      if (value == null) {
        log.debug("message part is missing, cannot get property: property="
            + propertyName + ", part=" + partName);
        continue;
      }
      // evaluate the query against the part value, if any
      Query query = alias.getQuery();
      if (query != null) {
        try {
          value = query.getEvaluator().evaluate((Element) value);
        }
        catch (BpelFaultException e) {
          // selection failures may arise from missing locations, don't set
          // those properties
          log.debug("could not select message location, cannot get property: property="
              + propertyName
              + ", part="
              + partName
              + ", query="
              + query.getText(),
              e);
          continue;
        }
      }
      // set the value in a jms message property field
      jmsRequest.setObjectProperty(propertyName.getLocalPart(),
          value instanceof Node ? XmlUtil.getStringValue((Node) value) : value);
    }
  }

  protected void receiveResponse(SOAPMessageContext soapContext,
      Session jmsSession, TemporaryQueue replyTo) throws JMSException,
      SOAPFaultException {
    // set up consumer
    MessageConsumer consumer = jmsSession.createConsumer(replyTo);
    try {
      // receive response message
      log.debug("listening for response: " + replyTo.getQueueName());
      ObjectMessage jmsResponse = (ObjectMessage) consumer.receive(responseTimeout);
      // did a message arrive in time?
      if (jmsResponse != null) {
        log.debug("received response: "
            + RequestListener.messageToString(jmsResponse));
        // keep the response element in the message context
        Map outputParts = (Map) jmsResponse.getObject();
        soapContext.setProperty(MESSAGE_PROP, outputParts);
        String faultName = jmsResponse.getStringProperty(IntegrationConstants.FAULT_NAME_PROP);
        jmsResponse.acknowledge();
        // is the outcome a fault?
        if (faultName != null) {
          throw new SOAPFaultException(CLIENT_FAULTCODE, BUSINESS_FAULTSTRING,
              null, null);
        }
      }
      else {
        log.debug("response timeout expired: " + replyTo.getQueueName());
        throw new SOAPFaultException(SERVER_FAULTCODE, TIMEOUT_FAULTSTRING,
            null, null);
      }
    }
    finally {
      // release consumer resources
      consumer.close();
    }
  }

  protected void writeResponse(SOAPMessageContext soapContext,
      JbpmContext jbpmContext) throws SOAPException {
    SOAPMessage message = soapContext.getMessage();
    SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();

    // remove existing body, it might be immutable
    SOAPBody body = envelope.getBody();
    Name responseName = ((SOAPElement) body.getChildElements().next()).getElementName();
    body.detachNode();
    // re-create body
    body = envelope.addBody();
    SOAPBodyElement operationWrapper = body.addBodyElement(responseName);

    // fill response element with message parts
    Map outputParts = (Map) soapContext.getProperty(MESSAGE_PROP);

    // retrieve partner link definition
    PartnerLinkDefinition partnerLink = IntegrationSession.getInstance(jbpmContext)
        .loadPartnerLinkDefinition(portEntry.getPartnerLinkId());
    // get current operation
    String operationName = (String) soapContext.getProperty(IntegrationConstants.OPERATION_NAME_PROP);
    Operation operation = partnerLink.getMyRole()
        .getPortType()
        .getOperation(operationName, null, null);

    // iterate through the output parts
    Iterator partIt = operation.getOutput()
        .getMessage()
        .getParts()
        .values()
        .iterator();
    while (partIt.hasNext()) {
      Part part = (Part) partIt.next();
      writeResponsePart(part, outputParts, operationWrapper);
    }
  }

  private static void writeResponsePart(Part part, Map responseParts,
      SOAPElement operationWrapper) throws SOAPException {
    String partName = part.getName();
    // get part from response data
    Element responsePart = (Element) responseParts.get(partName);
    // create part accessor under parts wrapper
    SOAPElement partAccessor = operationWrapper.addChildElement(partName);
    // check the referenced schema definition
    if (part.getElementName() != null) {
      // an element defines the part, write that element as a child of the part
      // accessor
      XmlUtil.copyChildElement(partAccessor, responsePart);
    }
    else {
      // a schema type defines the part, write on the part accessor itself
      XmlUtil.copy(partAccessor, responsePart);
    }
  }

  protected void writeFault(SOAPMessageContext soapContext)
      throws SOAPException {
    SOAPEnvelope envelope = soapContext.getMessage()
        .getSOAPPart()
        .getEnvelope();
    SOAPBody body = envelope.getBody();
    // remove existing body, since it might be immutable
    body.detachNode();
    // re-create body
    body = envelope.addBody();
    // soapenv:Fault
    SOAPFault fault = body.addFault();
    // faultcode
    SOAPFaultException faultException = (SOAPFaultException) soapContext.getProperty(FAULT_PROP);
    QName codeName = faultException.getFaultCode();
    String codeNamespace = codeName.getNamespaceURI();
    if (codeNamespace.length() > 0) {
      String codePrefix = XmlUtil.getPrefix(codeNamespace, fault);
      if (codePrefix == null) {
        codePrefix = "faultCodeNS";
        // TODO declare namespace?
        // fault.addNamespaceDeclaration(codePrefix, codeNamespace);
      }
      fault.setFaultCode(envelope.createName(codeName.getLocalPart(),
          codePrefix,
          codeNamespace));
    }
    else {
      fault.setFaultCode(codeName.getLocalPart());
    }
    // faultstring
    fault.setFaultString(faultException.getFaultString());
    // detail
    Map faultParts = (Map) soapContext.getProperty(MESSAGE_PROP);
    if (faultParts != null) {
      // create detail element
      Detail detail = fault.addDetail();
      // iterate through the fault message parts
      Iterator faultPartIt = faultParts.values().iterator();
      while (faultPartIt.hasNext()) {
        Element faultPart = (Element) faultPartIt.next();
        // create detail entry
        Name detailEntryName;
        String faultPartNamespace = faultPart.getNamespaceURI();
        if (faultPartNamespace.length() > 0) {
          detailEntryName = envelope.createName(faultPart.getLocalName(),
              XmlUtil.getPrefix(faultPartNamespace, faultPart),
              faultPartNamespace);
        }
        else {
          detailEntryName = envelope.createName(faultPart.getLocalName());
        }
        DetailEntry detailEntry = detail.addDetailEntry(detailEntryName);
        // copy fault part to detail entry
        XmlUtil.copy(detailEntry, faultPart);
      }
    }
  }
}