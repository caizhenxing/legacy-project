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
package org.jbpm.bpel.exe;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.wsdl.Definition;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.wsdl.Constants;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.LinkDefinition;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.exe.state.EndedState;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.wsdl.xml.WsdlConstants;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.scheduler.SchedulerService;

/**
 * @author Juan Cantú
 * @version $Revision: 1.27 $ $Date: 2006/08/22 04:13:10 $
 */
public abstract class AbstractExeTestCase extends TestCase {

  protected BpelReader reader;
  
  protected BpelDefinition pd;
  protected Receive firstActivity;
  protected TestScope scope;
  protected ProcessInstance pi;
  
  private JbpmContext jbpmContext;
  
  private static final String WSDL_TEXT =
    "<definitions targetNamespace='" + BpelConstants.NS_EXAMPLES + "'" +
    " xmlns:tns='" + BpelConstants.NS_EXAMPLES + "'" +
    " xmlns:plt='" + WsdlConstants.NS_PLNK + "'" +
    " xmlns:xsd='" + BpelConstants.NS_XML_SCHEMA + "'" +
    " xmlns='" + Constants.NS_URI_WSDL + "'>" +
    " <message name='msg'>" +
    "  <part name='p1' type='xsd:int' />" +
    " </message>" +
    " <portType name='ppt'>" +
    "  <operation name='o'>" +
    "   <input message='tns:msg' />" +
    "  </operation>" +
    " </portType>" +
    " <portType name='mpt'>" +
    "  <operation name='o'>" +
    "   <input message='tns:msg' />" +
    "  </operation>" +
    " </portType>" +
    " <plt:partnerLinkType name='plt'>" +
    "  <plt:role name='role1' portType='tns:ppt' />" +
    "  <plt:role name='role2' portType='tns:mpt' />" +
    " </plt:partnerLinkType>" +
    "</definitions>";

  protected void setUp() throws Exception {
    JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance("org/jbpm/bpel/exe/test.jbpm.cfg.xml");
    /* the reader accesses the jbpm configuration, so create a context before 
     * creating the reader to avoid loading another configuration from the default resource */
    jbpmContext = jbpmConfiguration.createJbpmContext();
    
    reader = new BpelReader() {
      
      public Receiver readReceiver(Element receiverElem, CompositeActivity parent) {
        return new TestReceiver();
      }

      public Alarm readAlarm(Element alarmElem, CompositeActivity parent) {
        return new TestAlarm();
      }
    };
    
    pd = new BpelDefinition("pd");
    pd.getNodes().clear();
    scope = new TestScope();
    scope.installFaultExceptionHandler();
    pd.addNode(scope);
    
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    PartnerLinkType plt = WsdlUtil.getPartnerLinkType(def, new QName(BpelConstants.NS_EXAMPLES, "plt"));
    
    pd.getImports().addImport(WsdlUtil.createImport(def));
    
    PartnerLinkDefinition pl = new PartnerLinkDefinition();
    pl.setName("aPartner");
    pl.setPartnerLinkType(plt);
    pl.setMyRole(plt.getSecondRole());
    
    scope.addPartnerLink(pl);
    pi = new ProcessInstance(pd);
  }
  
  //////////////////// activity read methods
  
  protected void tearDown() throws Exception {
    // close the jbpm context
    jbpmContext.close();
  }

  protected Activity readActivity(String xml, boolean isInitial) throws Exception {
    String textToParse = 
      "<parent xmlns='"  + BpelConstants.NS_BPWS  + "'>" + xml + "</parent>";
    Element element = (Element) XmlUtil.parseElement(textToParse).getChildNodes().item(0);
    return readActivity(element, isInitial);
  }
  
  protected Activity readActivity(URL xmlFile, boolean isInitial) throws Exception {
    InputStream xmlStream = xmlFile.openStream();
    Document document = XmlUtil.getDocumentBuilder().parse(xmlStream);
    xmlStream.close();
    return readActivity(document.getDocumentElement(), isInitial);
  }
  
  protected Activity readActivity(Element element, boolean isInitial) {
    scope.initial = isInitial;
    Activity activity = reader.readActivity(element, scope);
    return activity;
  }
  
  //////////////////// load activity into the definition
  
  protected void plugInner(Activity activity) {
    Sequence sequence = new Sequence("rootSequence");
    scope.addNode(sequence);

    firstActivity = new Receive();
    firstActivity.setReceiver(new TestReceiver());
    firstActivity.setName("start");
    firstActivity.connect(activity);
    activity.connect(sequence.getEnd());
  }
  
  protected void plugOuter(Activity activity) {
    scope.addNode(activity);
  }
  
  //////////////////// execution methods
 
  protected Token prepareInner() {
    Token root = pi.getRootToken();
    scope.initScopeData(root);
    ScopeInstance scopeInstance = Scope.getInstance(root);
    return scopeInstance.getActivityToken();
  }
 
  protected Token executeInner() {
    Token normalPath = prepareInner();
    firstActivity.leave(new ExecutionContext(normalPath));
    return normalPath;
  }

  protected Token executeOuter( Receiver trigger ) {
    Token rootToken =  pi.getRootToken();
    scope.initScopeData(rootToken);
    // trigger process instance
    pd.messageReceived(trigger, rootToken);
    //return the first children of the scope token (normalFlowToken)
    return (Token) rootToken.getChildren().values().iterator().next();
  }
  
  //////////////////// execution assertions
  
  protected void assertReceiveDisabled(Token token, Receive receive) {
    assertReceiverDisabled(token, receive.getReceiver());
  }
  
  protected void assertReceiveEnabled(Token token, Receive receive) {
    assertReceiverEnabled(token, receive.getReceiver());
  }
  
  protected void assertReceiverDisabled(Token token, Receiver receiver) {
    assertFalse(isReceiving(token, receiver));
  }
  
  protected void assertReceiverEnabled(Token token, Receiver receiver) {
    assertTrue(isReceiving(token, receiver));
  }
  
  protected void assertAlarmEnabled(Token token, Alarm alarm) {
    assertTrue(isWaiting(token, alarm));
  }
  
  protected void assertAlarmDisabled(Token token, Alarm alarm) {
    assertFalse(isWaiting(token, alarm));
  }
  
  protected void assertReceiveAndAdvance(Token token, Receive sourceNode, Activity targetNode) {
    Receive activity = (Receive) token.getNode();
    assertSame(sourceNode, activity);
    activity.messageReceived(activity.getReceiver(), token);
    assertSame( targetNode , token.getNode() );
  }
  
  protected void assertReceiveAndComplete(Token token, Receive sourceNode) {
    Receive activity = (Receive) token.getNode();
    assertSame(sourceNode, activity);
    activity.messageReceived(activity.getReceiver(), token);
    assertCompleted(token);
  }
  
  protected void assertCompleted(Token token) {
    assertEquals(EndedState.COMPLETED, Scope.getInstance(token).getState());
    assertNotNull( token.getEnd() ); 
  }
  
  protected Token findToken(Token parent, Node node) {
    return (Token) parent.getChildrenAtNode(node).iterator().next();
  }
  
  static boolean isWaiting(Token token, Alarm alarm) {
    return token.getProcessInstance().getContextInstance().hasVariable(
        MockIntegrationService.getIdString(alarm), token);
  }
  
  static boolean isReceiving(Token token, Receiver receiver) {
    return token.getProcessInstance().getContextInstance().hasVariable(
        MockIntegrationService.getIdString(receiver), token);
  }
  
  private static class TestAlarm extends Alarm {

    private static final long serialVersionUID = 1L;

    public void cancelTimer(Token token, SchedulerService scheduler) {
      MockIntegrationService.deleteMark(this, token);
    }

    public void createTimer(Token token, SchedulerService scheduler) {
      MockIntegrationService.createMark(this, token);
    }
  }
  
  private static class TestReceiver extends Receiver {
    
    private static final long serialVersionUID = 1L;

    public long getId() {
      return hashCode();
    }

    public void readMessage(Map parts, Token token) {
      // nothing to do here
    }
  }

  static class TestScope extends BpelDefinition.GlobalScope {
    
    boolean initial = false;
    
    private static final long serialVersionUID = 1L;
    
    public boolean isChildInitial(Activity activity) {
      return initial;
    }
  }
  
  static class TestLink extends LinkDefinition {

    private static final long serialVersionUID = 1L;
    
    public TestLink(String name) {
      super(name);
    }
    
    public void setStatus(Token token) {
      LinkInstance mi = getInstance(token);
      mi.setStatus(Boolean.FALSE);
    }
    
    public void determineStatus(ExecutionContext context) {
      LinkInstance mi = getInstance(context.getToken());
      mi.setStatus(Boolean.TRUE);
    }
  }  
  
}
