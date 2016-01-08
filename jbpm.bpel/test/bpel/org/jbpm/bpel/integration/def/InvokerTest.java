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

import java.util.Map;
import java.util.Random;

import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import org.jbpm.bpel.app.AppDescriptor;
import org.jbpm.bpel.app.AppPartnerLink;
import org.jbpm.bpel.app.AppPartnerRole;
import org.jbpm.bpel.app.AppPartnerRole.InitiateMode;
import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Invoke;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.exe.FaultInstance;
import org.jbpm.bpel.integration.SystemPropertiesTestHelper;
import org.jbpm.bpel.integration.catalog.URLCatalog;
import org.jbpm.bpel.integration.def.Invoker;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.exe.CorrelationSetInstance;
import org.jbpm.bpel.integration.jms.IntegrationControl;
import org.jbpm.bpel.integration.jms.IntegrationControlHelper;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.6 $ $Date: 2006/09/11 09:32:37 $
 */
public class InvokerTest extends AbstractDbTestCase {

  private BpelDefinition process;
  private Token token;
  
  private IntegrationControl integrationControl;
  
  private static final String PARTNER_LINK_HANDLE = "translator";

  public void setUp() throws Exception {
    // set up db
    super.setUp();
    
    // create process definition
    process = new BpelDefinition();
    // read bpel
    String bpelURI = getClass().getResource("translatorClient.bpel").toExternalForm();
    BpelReader.getInstance().read(process, new InputSource(bpelURI));
    
    // deploy process definition and commit changes
    jbpmContext.deployProcessDefinition(process);
    newTransaction();

    // create process instance
    ProcessInstance pi = process.createProcessInstance();
    token = pi.getRootToken();

    // service catalog
    URLCatalog catalog = new URLCatalog();
    catalog.addLocation("http://localhost:8080/translator/text?wsdl");
    // partner role descriptor
    AppPartnerRole partnerRole = new AppPartnerRole();
    partnerRole.setInitiateMode(InitiateMode.PULL);
    // partner link descriptor
    AppPartnerLink partnerLink = new AppPartnerLink();
    partnerLink.setName("translator");
    partnerLink.setPartnerRole(partnerRole);
    // app descriptor
    AppDescriptor appDescriptor = new AppDescriptor();
    appDescriptor.setName(process.getName());
    appDescriptor.setServiceCatalog(catalog);
    appDescriptor.addPartnerLink(partnerLink);

    // link jms administered objects
    InitialContext initialContext = new InitialContext();
    initialContext.bind(PARTNER_LINK_HANDLE, new LinkRef("queue/testQueue"));
    initialContext.bind(IntegrationControl.CONNECTION_FACTORY_NAME,
        new LinkRef("ConnectionFactory"));
    
    // configure relation service factory
    integrationControl = IntegrationControl.getInstance(jbpmConfiguration);
    integrationControl.setAppDescriptor(appDescriptor);
    IntegrationControlHelper.setUp(integrationControl, jbpmContext);
    
    // unlink jms administered objects
    initialContext.unbind(PARTNER_LINK_HANDLE);
    initialContext.unbind(IntegrationControl.CONNECTION_FACTORY_NAME);
    initialContext.close();
  }

  public void tearDown() throws Exception {
    // finalize relation service factory
    IntegrationControlHelper.tearDown(integrationControl);
    // tear down db
    super.tearDown();
  }

  public void testInvoke_oneWay() throws Exception {
    // grab quote invoker
    Sequence seq = (Sequence) process.getGlobalScope().getRoot();
    Invoker quoteInvoker = ((Invoke) seq.getNode("quote")).getInvoker();
    // call quote operation
    String clientName = createClientName();
    invokeQuote(quoteInvoker, clientName);
    // check correlation set
    CorrelationSetInstance csi =
      quoteInvoker.getOutCorrelations().getCorrelation("client").getSet().getInstance(token);
    Map properties = csi.getProperties();
    assertEquals(1, properties.size());
    assertEquals(clientName, properties.get(new QName("http://example.org/translator/client", "clientName")));
  }

  public void testInvoke_requestResponse() throws Exception {
    // grab quote invoker
    Sequence seq = (Sequence) process.getGlobalScope().getRoot();
    Invoker quoteInvoker = ((Invoke) seq.getNode("quote")).getInvoker();
    // call quote operation
    String clientName = createClientName();
    invokeQuote(quoteInvoker, clientName);
    // grab status invoker
    Invoker statusInvoker = ((Invoke) seq.getNode("status")).getInvoker();
    // init message variable
    MessageValue messageValue = (MessageValue) statusInvoker.getInputVariable().getValueForAssign(token);
    messageValue.setPart("clientName", clientName);
    /* call status operation - quote is an one-way operation, so the status change
     * might not be reflected immediately */
    Thread.sleep(500);
    Receiver.getIntegrationService(jbpmContext).invoke(statusInvoker, token);
    // check output variable
    messageValue = (MessageValue) statusInvoker.getOutputVariable().getValue(token);
    assertEquals("received", XmlUtil.getStringValue(messageValue.getPart("status")));
  }

  private static String createClientName() {
    return "client" + new Random().nextInt(100000);
  }

  private void invokeQuote(Invoker quoteInvoker, String clientName) throws Exception {
    // init message variable
    MessageValue message = (MessageValue) quoteInvoker.getInputVariable().getValueForAssign(token);
    message.setPart("clientName", clientName);
    message.setPart("text", "hi");
    message.setPart("sourceLanguage", "en");
    message.setPart("targetLanguage", "es");
    // consume service
    Receiver.getIntegrationService(jbpmContext).invoke(quoteInvoker, token);
  }

  public void testInvoke_requestFault() throws Exception {
    // grab invoker
    Invoke invoke = (Invoke) ((Sequence) process.getGlobalScope().getRoot()).getNode("translate");
    Invoker invoker = invoke.getInvoker();
    // init message variable
    MessageValue message = (MessageValue) invoker.getInputVariable().getValueForAssign(token);
    message.setPart("text", "hi");
    message.setPart("sourceLanguage", "en");
    message.setPart("targetLanguage", "ja");
    // consume service
    try {
      Receiver.getIntegrationService(jbpmContext).invoke(invoker, token);
      fail("invocation should have thrown a fault");
    }
    catch (BpelFaultException e) {
      // check returned fault
      FaultInstance faultInstance = e.getFaultInstance();
      // name
      assertEquals(new QName("http://example.com/translator", "dictionaryNotAvailable"), faultInstance.getName());
      // data type
      message = faultInstance.getMessageValue();
      assertEquals(new QName("http://example.com/translator", "dictionaryNotAvailableFault"), message.getType().getName());
      // data content
      Element detailPart = message.getPart("detail");
      assertEquals("http://example.com/translator/types", detailPart.getNamespaceURI());
      assertEquals("dictionaryNotAvailable", detailPart.getLocalName());
    }
  }

  static {
    SystemPropertiesTestHelper.initializeProperties();
  }
}
