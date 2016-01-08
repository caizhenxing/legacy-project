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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.wsdl.Definition;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.rpc.soap.SOAPFaultException;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.jbpm.bpel.app.AppDescriptor;
import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.integration.SystemPropertiesTestHelper;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.jms.IntegrationConstants;
import org.jbpm.bpel.integration.jms.IntegrationControl;
import org.jbpm.bpel.integration.jms.IntegrationControlHelper;
import org.jbpm.bpel.integration.jms.PartnerLinkEntry;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.wsdl.xml.WsdlConstants;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.WsdlConverterTest;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.w3c.dom.Element;

import com.ibm.wsdl.Constants;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.6 $ $Date: 2006/09/11 09:32:37 $
 */
public class SoapHandlerTest extends AbstractDbTestCase {

  private SoapHandler soapHandler = new SoapHandler(PARTNER_LINK_HANDLE);
  private long plinkId;
  private IntegrationControl integrationControl;

  private static final String WSDL_TEXT = "<definitions targetNamespace='"
      + BpelConstants.NS_EXAMPLES
      + "'"
      + " xmlns:tns='"
      + BpelConstants.NS_EXAMPLES
      + "'"
      + " xmlns:xsd='"
      + BpelConstants.NS_XML_SCHEMA
      + "'"
      + " xmlns:bpws='"
      + BpelConstants.NS_BPWS_1_1
      + "'"
      + " xmlns:plnk='"
      + WsdlConstants.NS_PLNK_1_1
      + "'"
      + " xmlns='"
      + Constants.NS_URI_WSDL
      + "'>"
      + "  <message name='request'>"
      + "    <part name='simplePart' type='xsd:string'/>"
      + "    <part name='elementPart' element='tns:surpriseElement'/>"
      + "  </message>"
      + "  <message name='response'>"
      + "    <part name='intPart' type='xsd:int'/>"
      + "    <part name='complexPart' type='tns:complexType'/>"
      + "  </message>"
      + "  <message name='failure'>"
      + "    <part name='faultPart' element='tns:faultElement'/>"
      + "  </message>"
      + "  <portType name='pt'>"
      + "    <operation name='op'>"
      + "      <input message='tns:request'/>"
      + "      <output message='tns:response'/>"
      + "      <fault name='flt' message='tns:failure'/>"
      + "    </operation>"
      + "  </portType>"
      + "  <plnk:partnerLinkType name='plt'>"
      + "    <plnk:role name='r1'>"
      + "      <plnk:portType name='tns:pt'/>"
      + "    </plnk:role>"
      + "  </plnk:partnerLinkType>"
      + "  <bpws:property name='nameProperty' type='xsd:string'/>"
      + "  <bpws:property name='idProperty' type='xsd:int'/>"
      + "  <bpws:propertyAlias propertyName='tns:nameProperty' messageType='tns:request' part='elementPart'"
      + "    query='/tns:surpriseElement/c/@name' />"
      + "  <bpws:propertyAlias propertyName='tns:idProperty' messageType='tns:request' part='elementPart'"
      + "    query='/tns:surpriseElement/e' />" + "</definitions>";
  private static final String REQUEST_ENV_TEXT = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>"
      + "<soapenv:Body>"
      + "<foo:op xmlns:foo='urn:someURI'>"
      + "  <simplePart>wazabi</simplePart>"
      + "  <elementPart>"
      + "    <tns:surpriseElement xmlns:tns='"
      + BpelConstants.NS_EXAMPLES
      + "'>"
      + "      <b on='true'>true</b>"
      + "      <c name='venus'/>"
      + "      <d amount='20'/>"
      + "      <e>30</e>"
      + "    </tns:surpriseElement>"
      + "  </elementPart>"
      + "</foo:op>"
      + "</soapenv:Body>" + "</soapenv:Envelope>";
  private static final String PARTNER_LINK_HANDLE = "pl";

  public void setUp() throws Exception {
    // set up db stuff
    super.setUp();
    // create bpel definition
    BpelDefinition process = new BpelDefinition("testProcess");
    // read wsdl
    Definition def = WsdlUtil.getFactory().newWSDLReader().readWSDL(null,
        WsdlConverterTest.transform(WSDL_TEXT));
    ImportsDefinition imports = process.getImports();
    imports.addImport(WsdlUtil.createImport(def));
    BpelReader.getInstance().registerPropertyAliases(imports);

    // partner link type
    PartnerLinkType partnerLinkType = imports.getPartnerLinkType(new QName(
        BpelConstants.NS_EXAMPLES, "plt"));

    // partner link
    PartnerLinkDefinition partnerLink = new PartnerLinkDefinition();
    partnerLink.setName(PARTNER_LINK_HANDLE);
    partnerLink.setPartnerLinkType(partnerLinkType);
    partnerLink.setMyRole(partnerLinkType.getFirstRole());
    process.getGlobalScope().addPartnerLink(partnerLink);

    // deploy process definition and commit changes
    jbpmContext.deployProcessDefinition(process);
    // save plink generated id
    plinkId = partnerLink.getId();

    // create application descriptor
    AppDescriptor appDescriptor = new AppDescriptor();
    appDescriptor.setName(process.getName());

    // link jms administered objects
    InitialContext initialContext = new InitialContext();
    initialContext.bind(PARTNER_LINK_HANDLE, new LinkRef("queue/testQueue"));
    initialContext.bind(IntegrationControl.CONNECTION_FACTORY_NAME,
        new LinkRef("ConnectionFactory"));

    // configure relation context
    integrationControl = IntegrationControl.getInstance(jbpmConfiguration);
    integrationControl.setAppDescriptor(appDescriptor);
    // bind port entries and lookup destinations
    IntegrationControlHelper.setUp(integrationControl, jbpmContext);

    // unlink jms administered objects
    initialContext.unbind(PARTNER_LINK_HANDLE);
    initialContext.unbind(IntegrationControl.CONNECTION_FACTORY_NAME);
    initialContext.close();
  }

  public void tearDown() throws Exception {
    // unbind port entries
    IntegrationControlHelper.tearDown(IntegrationControl.getInstance(jbpmConfiguration));
    // tear down db stuff
    super.tearDown();
  }

  public void testSendRequest() throws Exception {
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null,
        new ByteArrayInputStream(REQUEST_ENV_TEXT.getBytes()));
    SOAPMessageContext soapContext = new SOAPMessageContextImpl(soapMessage);

    Connection connection = integrationControl.getJmsConnection();
    Session session = connection.createSession(false,
        Session.CLIENT_ACKNOWLEDGE);
    soapHandler.sendRequest(soapContext, session, jbpmContext);

    PartnerLinkEntry entry = integrationControl.getPartnerLinkEntry(PARTNER_LINK_HANDLE);
    MessageConsumer consumer = session.createConsumer(entry.getDestination());
    connection.start();
    ObjectMessage message = (ObjectMessage) consumer.receiveNoWait();
    message.acknowledge();
    session.close();

    Map inputParts = (Map) message.getObject();
    // simple part
    Element simplePart = (Element) inputParts.get("simplePart");
    assertEquals("wazabi", XmlUtil.getStringValue(simplePart));
    // element part
    Element elementPart = (Element) inputParts.get("elementPart");
    assertEquals(BpelConstants.NS_EXAMPLES, elementPart.getNamespaceURI());
    assertEquals("surpriseElement", elementPart.getLocalName());

    assertEquals(plinkId,
        message.getLongProperty(IntegrationConstants.PARTNER_LINK_ID_PROP));
    assertEquals("op",
        message.getStringProperty(IntegrationConstants.OPERATION_NAME_PROP));
    assertEquals("venus", message.getStringProperty("nameProperty"));
    assertEquals("30", message.getStringProperty("idProperty"));
  }

  public void testSendRequest_nil() throws Exception {
    String requestText = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>"
        + "<soapenv:Body>"
        + " <foo:op xmlns:foo='urn:someURI' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>"
        + "  <simplePart xsi:nil='false'>wazabi</simplePart>"
        + "  <elementPart xsi:nil='1' />"
        + " </foo:op>"
        + "</soapenv:Body>"
        + "</soapenv:Envelope>";

    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null,
        new ByteArrayInputStream(requestText.getBytes()));
    SOAPMessageContext soapContext = new SOAPMessageContextImpl(soapMessage);

    Connection connection = integrationControl.getJmsConnection();
    Session session = connection.createSession(false,
        Session.CLIENT_ACKNOWLEDGE);
    soapHandler.sendRequest(soapContext, session, jbpmContext);

    PartnerLinkEntry entry = integrationControl.getPartnerLinkEntry(PARTNER_LINK_HANDLE);
    MessageConsumer consumer = session.createConsumer(entry.getDestination());
    connection.start();
    ObjectMessage message = (ObjectMessage) consumer.receiveNoWait();
    message.acknowledge();
    session.close();

    Map inputParts = (Map) message.getObject();
    // simple part
    Element simplePart = (Element) inputParts.get("simplePart");
    // value is not affected by a false xsi:nil value
    assertEquals("wazabi", XmlUtil.getStringValue(simplePart));

    // element part
    Element elementPart = (Element) inputParts.get("elementPart");
    assertNull(elementPart);
  }

  public void testReceiveResponse() throws Exception {
    // prepare messaging objects
    Connection connection = integrationControl.getJmsConnection();
    Session session = connection.createSession(false,
        Session.CLIENT_ACKNOWLEDGE);
    TemporaryQueue replyTo = session.createTemporaryQueue();
    MessageProducer producer = session.createProducer(replyTo);
    // create parts map
    Map outputParts = createOutputParts();
    // send message to queue
    producer.send(session.createObjectMessage((Serializable) outputParts));

    // prepare soap objects
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null,
        new ByteArrayInputStream(REQUEST_ENV_TEXT.getBytes()));
    SOAPMessageContext soapContext = new SOAPMessageContextImpl(soapMessage);
    // receive the above message
    soapHandler.receiveResponse(soapContext, session, replyTo);
    session.close();

    // verify the content
    outputParts = (HashMap) soapContext.getProperty(SoapHandler.MESSAGE_PROP);

    // simple part
    Element intPart = (Element) outputParts.get("intPart");
    assertEquals("intPart", intPart.getLocalName());
    assertNull(intPart.getNamespaceURI());
    // value
    assertEquals("2020", XmlUtil.getStringValue(intPart));

    // complex part
    Element complexPart = (Element) outputParts.get("complexPart");
    assertEquals("complexPart", complexPart.getLocalName());
    assertNull(complexPart.getNamespaceURI());
    // attributes
    assertEquals("hi", complexPart.getAttribute("attributeOne"));
    assertEquals("ho", complexPart.getAttribute("attributeTwo"));
    // child elements
    Element one = XmlUtil.getElement(complexPart, "urn:uriOne", "elementOne");
    assertEquals("ram", XmlUtil.getStringValue(one));
    Element two = XmlUtil.getElement(complexPart, "urn:uriTwo", "elementTwo");
    assertEquals("ones", XmlUtil.getStringValue(two));
  }

  public void testWriteResponse_saaj() throws Exception {
    SOAPMessage soapMessage = writeResponse();

    // soap body
    SOAPBody bodyElem = soapMessage.getSOAPBody();
    // operation wrapper
    SOAPBodyElement operationElem = (SOAPBodyElement) XmlUtil.getElement(
        bodyElem, BpelConstants.NS_EXAMPLES, "opResponse");

    // simple part
    SOAPElement intPart = XmlUtil.getElement(operationElem, "intPart");
    // value
    assertEquals("2020", intPart.getValue());

    // complex part
    SOAPElement complexPart = XmlUtil.getElement(operationElem, "complexPart");
    // attributes
    assertEquals("hi", complexPart.getAttribute("attributeOne"));
    assertEquals("ho", complexPart.getAttribute("attributeTwo"));
    // child elements
    SOAPElement one = XmlUtil.getElement(complexPart, "urn:uriOne",
        "elementOne");
    assertEquals("ram", one.getValue());
    SOAPElement two = XmlUtil.getElement(complexPart, "urn:uriTwo",
        "elementTwo");
    assertEquals("ones", two.getValue());
  }

  public void testWriteResponse_xml() throws Exception {
    SOAPMessage soapMessage = writeResponse();

    Element envelopeElem = writeAndRead(soapMessage);
    // soap body
    Element bodyElem = XmlUtil.getElement(envelopeElem,
        SOAPConstants.URI_NS_SOAP_ENVELOPE, "Body");
    // operation wrapper
    Element operationElem = XmlUtil.getElement(bodyElem,
        BpelConstants.NS_EXAMPLES, "opResponse");

    // simple part
    Element intPart = XmlUtil.getElement(operationElem, "intPart");
    // value
    assertEquals("2020", XmlUtil.getStringValue(intPart));

    // complex part
    Element complexPart = XmlUtil.getElement(operationElem, "complexPart");
    // attributes
    assertEquals("hi", complexPart.getAttribute("attributeOne"));
    assertEquals("ho", complexPart.getAttribute("attributeTwo"));
    // child elements
    Element one = XmlUtil.getElement(complexPart, "urn:uriOne", "elementOne");
    assertEquals("ram", XmlUtil.getStringValue(one));
    Element two = XmlUtil.getElement(complexPart, "urn:uriTwo", "elementTwo");
    assertEquals("ones", XmlUtil.getStringValue(two));
  }

  private SOAPMessage writeResponse() throws Exception {
    // soap message
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
    SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
    SOAPBody body = envelope.getBody();
    Name responseName = envelope.createName("opResponse", "tns",
        BpelConstants.NS_EXAMPLES);
    body.addBodyElement(responseName);
    // create parts map
    Map outputParts = createOutputParts();
    // soap context
    SOAPMessageContext soapContext = new SOAPMessageContextImpl();
    soapContext.setMessage(soapMessage);
    soapContext.setProperty(SoapHandler.MESSAGE_PROP, outputParts);
    soapContext.setProperty(IntegrationConstants.OPERATION_NAME_PROP, "op");
    // process the above message parts
    soapHandler.writeResponse(soapContext, jbpmContext);
    return soapMessage;
  }

  public void testWriteFault_saaj() throws Exception {
    SOAPMessage soapMessage = writeFault();

    SOAPBody body = soapMessage.getSOAPBody();
    // SOAPFault fault = body.getFault();
    SOAPElement fault = XmlUtil.getElement(body,
        SOAPConstants.URI_NS_SOAP_ENVELOPE, "Fault");

    testFaultCode(fault);
    testFaultString(fault);
    testDetail(fault);
  }

  private static void testFaultCode(SOAPFault fault) {
    Name faultCode = fault.getFaultCodeAsName();
    assertEquals(SoapHandler.CLIENT_FAULTCODE.getLocalPart(),
        faultCode.getLocalName());
    assertEquals(SoapHandler.CLIENT_FAULTCODE.getNamespaceURI(),
        faultCode.getURI());
  }

  private static void testFaultCode(SOAPElement fault) {
    SOAPElement faultcodeElem = XmlUtil.getElement(fault, "faultcode");
    String codeNamespace = SoapHandler.CLIENT_FAULTCODE.getNamespaceURI();
    assertEquals(XmlUtil.getPrefix(codeNamespace, faultcodeElem) + ':'
        + SoapHandler.CLIENT_FAULTCODE.getLocalPart(), faultcodeElem.getValue());
  }

  private static void testFaultString(SOAPFault fault) {
    assertEquals(SoapHandler.BUSINESS_FAULTSTRING, fault.getFaultString());
  }

  private static void testFaultString(SOAPElement fault) {
    SOAPElement faultstringElem = XmlUtil.getElement(fault, "faultstring");
    assertEquals(SoapHandler.BUSINESS_FAULTSTRING, faultstringElem.getValue());
  }

  private static void testDetail(SOAPFault fault) {
    Detail detail = fault.getDetail();
    DetailEntry detailEntry = (DetailEntry) detail.getDetailEntries().next();
    testDetailEntry(detailEntry);
  }

  private void testDetail(SOAPElement fault) {
    SOAPElement detail = XmlUtil.getElement(fault, "detail");
    SOAPElement detailEntry = XmlUtil.getElement(detail);
    testDetailEntry(detailEntry);
  }

  private static void testDetailEntry(SOAPElement detailEntry) {
    SOAPElement code = XmlUtil.getElement(detailEntry, "code");
    assertEquals("100", XmlUtil.getStringValue(code));

    SOAPElement description = XmlUtil.getElement(detailEntry, "description");
    assertEquals("unknown problem", XmlUtil.getStringValue(description));
  }

  public void testWriteFault_xml() throws Exception {
    SOAPMessage soapMessage = writeFault();

    Element envelope = writeAndRead(soapMessage);
    Element body = XmlUtil.getElement(envelope,
        SOAPConstants.URI_NS_SOAP_ENVELOPE, "Body");
    Element fault = XmlUtil.getElement(body,
        SOAPConstants.URI_NS_SOAP_ENVELOPE, "Fault");

    Element faultcode = XmlUtil.getElement(fault, "faultcode");
    assertEquals(XmlUtil.getPrefix(
        SoapHandler.CLIENT_FAULTCODE.getNamespaceURI(), faultcode)
        + ':' + SoapHandler.CLIENT_FAULTCODE.getLocalPart(),
        XmlUtil.getStringValue(faultcode));

    Element faultstring = XmlUtil.getElement(fault, "faultstring");
    assertEquals(SoapHandler.BUSINESS_FAULTSTRING,
        XmlUtil.getStringValue(faultstring));

    Element detail = XmlUtil.getElement(fault, "detail");
    Element faultElement = XmlUtil.getElement(detail,
        BpelConstants.NS_EXAMPLES, "faultElement");

    Element code = XmlUtil.getElement(faultElement, "code");
    assertEquals("100", XmlUtil.getStringValue(code));

    Element description = XmlUtil.getElement(faultElement, "description");
    assertEquals("unknown problem", XmlUtil.getStringValue(description));
  }

  private SOAPMessage writeFault() throws Exception {
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();

    SOAPMessageContext soapContext = new SOAPMessageContextImpl();
    soapContext.setMessage(soapMessage);
    // operation
    soapContext.setProperty(IntegrationConstants.OPERATION_NAME_PROP, "op");
    // fault exception
    SOAPFaultException faultException = new SOAPFaultException(
        SoapHandler.CLIENT_FAULTCODE, SoapHandler.BUSINESS_FAULTSTRING, null,
        null);
    soapContext.setProperty(SoapHandler.FAULT_PROP, faultException);
    // message
    Element faultElem = XmlUtil.parseElement("<tns:faultElement xmlns:tns='http://jbpm.org/bpel/examples'>"
        + " <code>100</code>"
        + " <description>unknown problem</description>"
        + "</tns:faultElement>");
    Map faultParts = Collections.singletonMap("faultPart", faultElem);
    soapContext.setProperty(SoapHandler.MESSAGE_PROP, faultParts);

    soapHandler.writeFault(soapContext);
    return soapMessage;
  }

  private static Map createOutputParts() throws Exception {
    // create content
    // simple
    Element simpleElem = XmlUtil.parseElement("<intPart>2020</intPart>");
    // complex
    Element complexElem = XmlUtil.parseElement("<complexPart attributeOne='hi' attributeTwo='ho'>"
        + " <ns1:elementOne xmlns:ns1='urn:uriOne'>ram</ns1:elementOne>"
        + " <ns2:elementTwo xmlns:ns2='urn:uriTwo'>ones</ns2:elementTwo>"
        + "</complexPart>");
    // populate parts map
    Map outputParts = new HashMap();
    outputParts.put("intPart", simpleElem);
    outputParts.put("complexPart", complexElem);
    return outputParts;
  }

  private static Element writeAndRead(SOAPMessage soapMessage) throws Exception {
    ByteArrayOutputStream sink = new ByteArrayOutputStream();
    soapMessage.writeTo(sink);
    sink.writeTo(System.out);
    return XmlUtil.parseElement(sink.toString());
  }

  static {
    SystemPropertiesTestHelper.initializeProperties();
  }
}
