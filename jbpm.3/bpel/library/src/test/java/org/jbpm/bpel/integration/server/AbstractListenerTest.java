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

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
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
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.jms.IntegrationConstants;
import org.jbpm.bpel.integration.jms.IntegrationControl;
import org.jbpm.bpel.integration.jms.IntegrationControlHelper;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Event;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.instantiation.Delegation;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class AbstractListenerTest extends AbstractDbTestCase {

  protected BpelDefinition process;
  protected Receiver receiver;
  protected IntegrationControl integrationControl;

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
    "  <portType name='pt'>" +
    "    <operation name='op'>" +
    "      <input message='tns:request'/>" +
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
    "</definitions>";
  private static final String BPEL_TEXT = 
    "<process name='testProcess' xmlns:def='http://jbpm.org/wsdl' xmlns:vendor='http://jbpm.org'" +
    " xmlns='http://schemas.xmlsoap.org/ws/2004/03/business-process/'>" +
    " <partnerLinks>" +
    "  <partnerLink name='pl' partnerLinkType='def:plt' myRole='r1'/>" +
    " </partnerLinks>" +
    " <variables>" +
    "  <variable name='req' messageType='def:request'/>" +
    " </variables>" +
    " <correlationSets>" +
    "  <correlationSet name='csId' properties='def:idProperty'/>" +
    "  <correlationSet name='csName' properties='def:nameProperty'/>" +
    " </correlationSets>" +
    " <receive partnerLink='pl' operation='op' variable='req' messageExchange='swing'" +
    "  createInstance='yes'>" +
    "  <correlations>" +
    "   <correlation set='csId' initiate='join'/>" +
    "   <correlation set='csName' initiate='yes'/>'" +
    "  </correlations>" +
    " </receive>" +
    "</process>";
  private static final String NS_DEF = "http://jbpm.org/wsdl";
  
  protected static final QName ID_PROP = new QName(NS_DEF, "idProperty");
  protected static final String ID_VALUE = "30";
  
  private static final String PARTNER_LINK_NAME = "pl";
  
  static final Object lock = ID_VALUE;
  static Thread listenerThread;
  
  public void setUp() throws Exception {
    // set up db
    super.setUp();
    
    // create process definition
    process = new BpelDefinition();
    // read wsdl
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    process.getImports().addImport(WsdlUtil.createImport(def));
    // read bpel
    BpelReader.getInstance().read(process, new InputSource(new StringReader(BPEL_TEXT)));
    
    // get the receiver
    Receive receive = (Receive) process.getGlobalScope().getRoot();
    receiver = receive.getReceiver();
    // intercept reception activity termination
    Event event = new Event(Event.EVENTTYPE_NODE_LEAVE);
    event.addAction(new Action(new Delegation(ReceptionVerifier.class.getName())));
    receive.addEvent(event);
    
    // deploy process definition and commit changes
    jbpmContext.deployProcessDefinition(process);
    newTransaction();
    
    // create application descriptor
    AppDescriptor appDescriptor = new AppDescriptor();
    appDescriptor.setName(process.getName());
    
    // bind partner link destination
    new InitialContext().bind(PARTNER_LINK_NAME, new LinkRef("queue/testQueue"));
    
    // configure relation service factory
    integrationControl = IntegrationControl.getInstance(jbpmConfiguration);
    integrationControl.setAppDescriptor(appDescriptor);
    IntegrationControlHelper.setUp(integrationControl, jbpmContext);
  }
  
  public void tearDown() throws Exception {
    // wait until message is delivered
    if (listenerThread != null) {
      listenerThread.join();
    }
    // finalize relation service factory
    IntegrationControlHelper.tearDown(integrationControl, jbpmContext);
    // unbind partner link destination
    new InitialContext().unbind(PARTNER_LINK_NAME);
    // tear down db
    super.tearDown();
  }
  
  public void testReceiveBeforeSend() throws Exception {
    // prepare the listener for reception
    openListener();
    // now send the request
    sendRequest();
    // wait until the reception is verified
    waitForReception();
  }
  
  public void testReceiveAfterSend() throws Exception {
    // send the request
    sendRequest();
    // now prepare the listener for reception
    openListener();
    // wait until the reception is verified
    waitForReception();
  }

  public void testClose() throws Exception {
    // prepare the listener for reception, then close it
    openListener();
    closeListener();
    // send request - the listener should not receive it
    sendRequest();
    // reopen the listener - it should now receive the message
    openListener();
    // wait until the reception is verified
    waitForReception();
  }
  
  protected abstract void openListener() throws Exception;
  
  protected abstract void closeListener() throws Exception;

  protected void sendRequest() throws Exception {
    // get connection and destination
    PartnerLinkDefinition partnerLink = receiver.getPartnerLink();
    Destination destination = integrationControl.getPartnerLinkEntry(partnerLink).getDestination();
    Session session = null;
    try {
      // create session and producer
      session = integrationControl.getJmsConnection().createSession(false, Session.CLIENT_ACKNOWLEDGE);
      MessageProducer producer = session.createProducer(destination);
      // create and fill a message
      HashMap inputParts = new HashMap();
      // simple part
      Element simpleValue = XmlUtil.parseElement("<simplePart>wazabi</simplePart>");
      inputParts.put("simplePart", simpleValue);
      // element part
      Element elementValue = XmlUtil.parseElement(
          "<sns:surpriseElement xmlns:sns='http://jbpm.org/xsd'>" +
          " <b on=\"true\">true</b>" + 
          " <c name=\"venus\"/>" +
          " <d amount=\"20\"/>" +
          " <e>30</e>" +
          "</sns:surpriseElement>");
      inputParts.put("elementPart", elementValue);
      Message message = session.createObjectMessage(inputParts);
      // set a reply destination so that the outstanding request is created
      message.setJMSReplyTo(destination);
      // set properties
      message.setLongProperty(IntegrationConstants.PARTNER_LINK_ID_PROP, partnerLink.getId());
      message.setStringProperty(IntegrationConstants.OPERATION_NAME_PROP, receiver.getOperation().getName());
      message.setStringProperty(ID_PROP.getLocalPart(), ID_VALUE);
      // send message
      producer.send(message);
    }
    finally {
      if (session != null) session.close();
    }
  }
  
  private void waitForReception() {
    newTransaction();
    synchronized (lock) {
      try {
        lock.wait();
      }
      catch (InterruptedException e) {}
    }
  }

  public static class ReceptionVerifier implements ActionHandler {

    private static final long serialVersionUID = 1L;

    public void execute(ExecutionContext exeContext) throws Exception {
      Token token = exeContext.getToken();
      Receive receive = (Receive) token.getNode();
      Receiver receiver = receive.getReceiver();
      
      // variable
      VariableDefinition variable = receiver.getVariable();
      MessageValue messageValue = (MessageValue) variable.getValue(token);
      // simple part
      assertNotNull(messageValue.getPart("simplePart"));
      // element part
      assertNotNull(messageValue.getPart("elementPart"));
      // correlation sets
      Correlations correlations = receiver.getCorrelations();
      // id cset
      CorrelationSetDefinition set = correlations.getCorrelation("csId").getSet();
      Map properties = set.getInstance(token).getProperties();
      assertEquals(1, properties.size());
      assertEquals(ID_VALUE, properties.get(ID_PROP));
      // name cset
      set = correlations.getCorrelation("csName").getSet();
      properties = set.getInstance(token).getProperties();
      assertEquals(1, properties.size());
      assertEquals("venus", properties.get(new QName(NS_DEF, "nameProperty")));
      
      listenerThread = Thread.currentThread();
      synchronized (lock) {
        lock.notify();
      }
    }
  }
}
