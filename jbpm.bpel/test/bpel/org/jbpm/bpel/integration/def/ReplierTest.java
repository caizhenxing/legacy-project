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
package org.jbpm.bpel.integration.def;

import java.io.StringReader;
import java.util.Map;

import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.wsdl.Definition;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import org.jbpm.bpel.app.AppDescriptor;
import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Reply;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.def.Replier;
import org.jbpm.bpel.integration.exe.CorrelationSetInstance;
import org.jbpm.bpel.integration.jms.OutstandingRequest;
import org.jbpm.bpel.integration.jms.IntegrationConstants;
import org.jbpm.bpel.integration.jms.IntegrationControl;
import org.jbpm.bpel.integration.jms.IntegrationControlHelper;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.6 $ $Date: 2006/09/11 09:32:37 $
 */
public class ReplierTest extends AbstractDbTestCase {

  private BpelDefinition process;
  private Token token;

  private IntegrationControl integrationControl;
  private Queue portQueue;

  private static final String WSDL_TEXT =
    "<definitions targetNamespace='http://jbpm.org/wsdl'" +
    " xmlns:tns='http://jbpm.org/wsdl'" +
    " xmlns:sns='http://jbpm.org/xsd'" +
    " xmlns:xsd='http://www.w3.org/2001/XMLSchema'" +
    " xmlns:bpws='http://schemas.xmlsoap.org/ws/2004/03/business-process/'" +
    " xmlns:plnk='http://schemas.xmlsoap.org/ws/2004/03/partner-link/'" +
    " xmlns='http://schemas.xmlsoap.org/wsdl/'>" +
    "  <message name='request'>" +
    "    <part name='simplePart' type='xsd:string'/>" +
    "    <part name='elementPart' element='sns:surpriseElement'/>" +
    "  </message>" +
    "  <message name='response'>" +
    "    <part name='intPart' type='xsd:int'/>" +
    "    <part name='complexPart' type='sns:complexType'/>" +
    "  </message>" +
    "  <message name='failure'>" +
    "    <part name='faultPart' element='sns:faultElement'/>" +
    "  </message>" +
    "  <portType name='pt'>" +
    "    <operation name='op'>" +
    "      <input message='tns:request'/>" +
    "      <output message='tns:response'/>" +
    "      <fault name='flt' message='tns:failure'/>" +
    "    </operation>" +
    "  </portType>" +
    "  <plnk:partnerLinkType name='plt'>" +
    "    <plnk:role name='r1' portType='tns:pt'/>" +
    "  </plnk:partnerLinkType>" +
    "  <bpws:property name='nameProperty' type='xsd:string'/>" +
    "  <bpws:property name='idProperty' type='xsd:int'/>" +
    "  <bpws:propertyAlias propertyName='tns:nameProperty' messageType='tns:request' part='elementPart'>" +
    "    <bpws:query>c/@name</bpws:query>" +
    "  </bpws:propertyAlias>" +
    "  <bpws:propertyAlias propertyName='tns:idProperty' messageType='tns:request' part='elementPart'>" +
    "    <bpws:query>e</bpws:query>" +
    "  </bpws:propertyAlias>" +
    "  <bpws:propertyAlias propertyName='tns:nameProperty' messageType='tns:response' part='complexPart'>" +
    "    <bpws:query>c/@name</bpws:query>" +
    "  </bpws:propertyAlias>" +
    "  <bpws:propertyAlias propertyName='tns:idProperty' messageType='tns:response' part='intPart' />" +
    "</definitions>";
  private static final String BPEL_TEXT =
    "<process name='testProcess' xmlns:def='http://jbpm.org/wsdl'" +
    " xmlns='http://schemas.xmlsoap.org/ws/2004/03/business-process/'>" +
    " <partnerLinks>" +
    "  <partnerLink name='pl' partnerLinkType='def:plt' myRole='r1'/>" +
    " </partnerLinks>" +
    " <variables>" +
    "  <variable name='req' messageType='def:request'/>" +
    "  <variable name='rsp' messageType='def:response'/>" +
    "  <variable name='flt' messageType='def:failure'/>" +
    " </variables>" +
    " <correlationSets>" +
    "  <correlationSet name='csId' properties='def:idProperty'/>" +
    "  <correlationSet name='csName' properties='def:nameProperty'/>" +
    " </correlationSets>" +
    " <sequence>" +
    "  <receive name='rec' partnerLink='pl' operation='op' variable='req'" +
    "   messageExchange='swing' createInstance='yes'>" +
    "   <correlations>" +
    "    <correlation set='csId' initiate='yes'/>" +
    "   </correlations>" +
    "  </receive>" +
    "  <reply name='rep-out' partnerLink='pl' operation='op' variable='rsp' " +
    "   messageExchange='swing'>" +
    "   <correlations>" +
    "    <correlation set='csId'/>" +
    "    <correlation set='csName' initiate='yes'/>" +
    "   </correlations>" +
    "  </reply>" +
    "  <reply name='rep-flt' partnerLink='pl' operation='op' variable='flt'" +
    "   messageExchange='swing' faultName='def:flt'/>" +
    " </sequence>" +
    "</process>";
  private static final String NS_DEF = "http://jbpm.org/wsdl";
  private static final String PARTNER_LINK_HANDLE = "pl";

  public void setUp() throws Exception {
    // set up db
    super.setUp();    
    
    // create process definition
    process = new BpelDefinition();
    BpelReader bpelReader = BpelReader.getInstance();
    // read wsdl
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    ImportsDefinition imports = process.getImports();
    imports.addImport(WsdlUtil.createImport(def));
    bpelReader.registerPropertyAliases(imports);
    // read bpel
    bpelReader.read(process, new InputSource(new StringReader(BPEL_TEXT)));
    
    // deploy process definition and commit changes
    jbpmContext.deployProcessDefinition(process);
    newTransaction();
    
    // create process instance
    ProcessInstance pi = process.createProcessInstance();
    token = pi.getRootToken();
    
    // app descriptor
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
    IntegrationControlHelper.setUp(integrationControl, jbpmContext);
    
    // unlink jms administered objects
    initialContext.unbind(PARTNER_LINK_HANDLE);
    initialContext.unbind(IntegrationControl.CONNECTION_FACTORY_NAME);
    initialContext.close();    
    
    // retrieve the partner link destination
    PartnerLinkDefinition partnerLink = process.getGlobalScope().getPartnerLink(PARTNER_LINK_HANDLE);
    portQueue = (Queue) integrationControl.getPartnerLinkEntry(partnerLink).getDestination();
  }

  public void tearDown() throws Exception {
    // unbind port entries
    IntegrationControlHelper.tearDown(integrationControl);
    // tear down db
    super.tearDown();
  }
  
  public void testReply_response() throws Exception {
    // get the replier
    Sequence seq = (Sequence) process.getGlobalScope().getRoot();
    Replier replier = ((Reply) seq.getNode("rep-out")).getReplier();
    // init message variable
    String complexPartValue =
      "<complexPart>" +
      " <c name='venus'/>" +
      " <e>30</e>" +
      "</complexPart>";
    MessageValue responseValue = (MessageValue) replier.getVariable().getValueForAssign(token);
    responseValue.setPart("intPart", "30");
    responseValue.setPart("complexPart", XmlUtil.parseElement(complexPartValue));
    // init correlation set
    CorrelationSetInstance idSet = replier.getCorrelations().getCorrelation("csId").getSet().getInstance(token);
    idSet.initialize(responseValue);
    // create outstanding request
    OutstandingRequest request = new OutstandingRequest(portQueue, null);
    Receiver receiver = ((Receive) seq.getNode("rec")).getReceiver();
    integrationControl.addOutstandingRequest(receiver, token, request);

    // send reply
    Receiver.getIntegrationService(jbpmContext).reply(replier, token);
    // response content
    Map outputParts = (Map) receiveResponse().getObject();
    // simple part
    Element intPart = (Element) outputParts.get("intPart");
    assertNull(intPart.getNamespaceURI());
    assertEquals("intPart", intPart.getLocalName());
    assertEquals("30", XmlUtil.getStringValue(intPart));
    // complex part
    Element complexPart = (Element) outputParts.get("complexPart");
    assertEquals(null, complexPart.getNamespaceURI());
    assertEquals("complexPart", complexPart.getLocalName());
    assertNotNull(XmlUtil.getElement(complexPart, "c"));
    assertNotNull(XmlUtil.getElement(complexPart, "e"));
    // correlation sets
    Correlations correlations = replier.getCorrelations();
    // id set
    idSet = correlations.getCorrelation("csId").getSet().getInstance(token);
    Map properties = idSet.getProperties();
    assertEquals(1, properties.size());
    assertEquals("30", properties.get(new QName(NS_DEF, "idProperty")));
    // name set
    idSet = correlations.getCorrelation("csName").getSet().getInstance(token);
    properties = idSet.getProperties();
    assertEquals(1, properties.size());
    assertEquals("venus", properties.get(new QName(NS_DEF, "nameProperty")));
  }
  
  public void testReply_fault() throws Exception {
    // get the replier
    Sequence seq = (Sequence) process.getGlobalScope().getRoot();
    Replier replier = ((Reply) seq.getNode("rep-flt")).getReplier();
    // init message variable
    String faultPartValue =
      "<sns:faultElement xmlns:sns='http://jbpm.org/xsd'>" +
      " <code>100</code>" +
      " <description>unknown problem</description>" +
      "</sns:faultElement>";      
    MessageValue faultValue = (MessageValue) replier.getVariable().getValueForAssign(token);
    faultValue.setPart("faultPart", faultPartValue);
    // create outstanding request
    OutstandingRequest request = new OutstandingRequest(portQueue, null);
    Receiver receiver = ((Receive) seq.getNode("rec")).getReceiver();
    integrationControl.addOutstandingRequest(receiver, token, request);

    // send reply
    Receiver.getIntegrationService(jbpmContext).reply(replier, token);
    // response content
    ObjectMessage response = receiveResponse();
    Map outputParts = (Map) response.getObject();
    // fault part
    Element faultPart = (Element) outputParts.get("faultPart");
    assertEquals("http://jbpm.org/xsd", faultPart.getNamespaceURI());
    assertEquals("faultElement", faultPart.getLocalName());
    // fault name
    assertEquals(new QName("http://jbpm.org/wsdl", "flt").toString(), response.getStringProperty(IntegrationConstants.FAULT_NAME_PROP));
  }

  private ObjectMessage receiveResponse() throws Exception {
    Session jmsSession = integrationControl.getJmsConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
    try {
      MessageConsumer consumer = jmsSession.createConsumer(portQueue);
      return (ObjectMessage) consumer.receiveNoWait();
    }
    finally {
      jmsSession.close();
    }
  }
}
