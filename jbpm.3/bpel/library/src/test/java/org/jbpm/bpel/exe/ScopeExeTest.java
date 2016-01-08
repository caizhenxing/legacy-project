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

import java.util.Iterator;

import com.ibm.wsdl.MessageImpl;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Catch;
import org.jbpm.bpel.def.OnEvent;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.exe.state.ActiveState;
import org.jbpm.bpel.exe.state.EndedState;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class ScopeExeTest extends AbstractExeTestCase {

  Scope scopeActivity;

  public void testGetDefinition() throws Exception {
    String xml = 
    "<scope name='1'>" +
    " <scope name='1.1'>" +
    "   <scope name='1.1.1'>" +
    "     <empty/>" +     
    "   </scope>" + 
    " </scope>" +   
    "</scope>";
    scopeActivity = (Scope) readActivity(xml, false);
    plugInner(scopeActivity);
    Token token = executeInner();
    ScopeInstance s1 = Scope.getInstance((Token) token.getChildren().values().iterator().next());
    assertEquals("1", s1.getDefinition().getName());
    ScopeInstance s11 = (ScopeInstance) new ScopeIterator(s1.getToken()).next();
    assertEquals("1.1", s11.getDefinition().getName());
    ScopeInstance s111 = (ScopeInstance) new ScopeIterator(s11.getToken()).next();
    assertEquals("1.1.1", s111.getDefinition().getName());
  }
  
  public void testNormalExecution() throws Exception {
    String xml = 
      "<scope>" +
      " <receive partnerLink='aPartner' operation='o' />" +
      "</scope>"; 
    scopeActivity = (Scope) readActivity(xml, false);
    Receive receive =(Receive) scopeActivity.getRoot();
    plugInner(scopeActivity);
    
    Token token = executeInner();
    assertEquals(scopeActivity, token.getNode());
    Token scopeToken = (Token) token.getChildren().values().iterator().next();
    Token normalFlowToken = (Token) scopeToken.getChildren().values().iterator().next();
    
    assertEquals(receive, normalFlowToken.getNode());
    
    ScopeInstance scopeInstance = Scope.getInstance(scopeToken);
    assertEquals(scopeToken, scopeInstance.getToken());
    assertNotNull(scopeInstance);
    assertEquals(scopeInstance.getState(), ActiveState.NORMAL_PROCESSING);
    
    assertReceiveAndComplete(normalFlowToken, receive);
    assertEquals( EndedState.COMPLETED, scopeInstance.getState() );
    
    //parent token advanced and completed
    assertCompleted( token );  
  }
  
  public void testEventsExecutionFirstScenario() throws Exception {
    String xml = 
      "<scope>" +  
      " <receive partnerLink='aPartner' operation='o' />" +
      "<eventHandlers>" +
      " <onEvent partnerLink='aPartner' operation='o' >" +       
      "   <receive partnerLink='aPartner' operation='o' />" + 
      " </onEvent>" +
      " <onEvent partnerLink='aPartner' operation='o' >" +       
      "   <receive partnerLink='aPartner' operation='o' />" + 
      " </onEvent>" +      
      "</eventHandlers>" +      
      "</scope>"; 
    scopeActivity = (Scope) readActivity(xml, false);
    plugInner(scopeActivity);

    Iterator eventsIt = scopeActivity.getOnEvents().iterator();
    OnEvent firstEvent = (OnEvent) eventsIt.next();
    OnEvent secondEvent = (OnEvent) eventsIt.next();
    
    Token token = executeInner();
    Token scopeToken = (Token) token.getChildren().values().iterator().next();
    ScopeInstance scopeInstance = Scope.getInstance(scopeToken);
    
    firstEvent.messageReceived(firstEvent.getReceiver(), scopeToken);
    // first event activity is completed
    Token firstEventToken = scopeInstance.getEventToken(0);
    assertEventAndComplete(firstEventToken, (Receive) firstEvent.getActivity());

    secondEvent.messageReceived(secondEvent.getReceiver(), scopeToken);
    
    //execute scope receive, scope must reach an events pending state
    Receive receive =(Receive) scopeActivity.getRoot();
    Token activityToken = scopeInstance.getActivityToken();
    receive.messageReceived(receive.getReceiver(), activityToken);
    assertEquals(ActiveState.EVENTS_PENDING, scopeInstance.getState());
    
    //a new message arrives to the first event. It must be rejected
    int beforeEventCount = scopeInstance.getEventTokens().size();
    firstEvent.messageReceived(firstEvent.getReceiver(), scopeToken);
    assertEquals(beforeEventCount, scopeInstance.getEventTokens().size());
    
    //second event activity is completed
    Token secondEventToken = scopeInstance.getEventToken(1);
    assertEventAndComplete(secondEventToken, (Receive) secondEvent.getActivity());
    
    //parent token advanced and completed
    assertEquals(EndedState.COMPLETED, scopeInstance.getState());
    assertCompleted( token ); 
  }  

  
  public void testEventsExecutionSecondScenario() throws Exception {
    String xml = 
      "<scope>" +  
      " <receive partnerLink='aPartner' operation='o' />" +
      "<eventHandlers>" +
      " <onEvent partnerLink='aPartner' operation='o' >" +       
      "   <receive partnerLink='aPartner' operation='o' />" + 
      " </onEvent>" +
      "</eventHandlers>" +      
      "</scope>"; 
    scopeActivity = (Scope) readActivity(xml, false);
    plugInner(scopeActivity);
    
    OnEvent firstEvent = (OnEvent) scopeActivity.getOnEvents().iterator().next();
    
    // set the handler variable
    VariableDefinition variable = new VariableDefinition();
    variable.setName("v");
    variable.setType(new MessageType(new MessageImpl()));
    firstEvent.setVariableDefinition(variable);
    
    Token token = executeInner();
    Token scopeToken = (Token) token.getChildren().values().iterator().next();
    ScopeInstance scopeInstance = Scope.getInstance(scopeToken);
    Token activityToken = scopeInstance.getActivityToken();
    
    firstEvent.messageReceived(firstEvent.getReceiver(), scopeToken);
    Token firstEventToken = scopeInstance.getEventToken(0);
    
    //first event activity is completed
    assertEventAndComplete(firstEventToken, (Receive)firstEvent.getActivity());
    
    //execute scope receive, scope must complete
    Receive receive =(Receive) scopeActivity.getRoot();
    receive.messageReceived(receive.getReceiver(), activityToken);

    //parent token advanced and completed
    assertEquals( EndedState.COMPLETED, scopeInstance.getState() );
    assertCompleted( token );
  }  
 
  public void testFaultWithoutHandler() throws Exception {
    String xml =
      "<scope>" +  
      "<sequence>" +
      "<scope>" +
      "  <empty/>" +     
      "</scope>" +            
      "<flow>" +    
      "  <throw faultName='someFault'/>" + 
      "  <receive name='uselessReceive' partnerLink='aPartner' operation='o'/>" +
      "</flow>" +  
      "</sequence>" +
      "</scope>"; 
    
    scopeActivity = (Scope) readActivity(xml, false);
    plugInner(scopeActivity);
    
    Token token = executeInner();
    Token scopeToken = (Token) token.getChildren().values().iterator().next();
    ScopeInstance scopeInstance = Scope.getInstance(scopeToken);
    
    ScopeIterator childrenIt = new ScopeIterator(scopeToken);
    assertTrue(childrenIt.hasNext());
    //compensation won't work since persistence is disabled. 
    //assertEquals(EndedState.COMPENSATED, ((ScopeInstance)childrenIt.next()).getState());
    assertEquals(EndedState.COMPLETED, ((ScopeInstance)childrenIt.next()).getState());
    
    //parent token advanced and completed abnormally
    assertEquals( EndedState.FAULTED, scopeInstance.getState() );
    assertTrue(token.hasEnded());
  }
  
  public void testFaultWithHandler() throws Exception {
    String xml =
      "<scope>" +  
      "  <sequence>" +
      "    <scope>" +
      "      <empty/>" +     
      "    </scope>" +        
      "    <throw faultName='someFault' />" + 
      "  </sequence>" +
      "  <faultHandlers>" +
      "   <catch faultName='someFault'>" +       
      "     <receive partnerLink='aPartner' operation='o'/>" + 
      "   </catch>" +
      "  </faultHandlers>" +      
      "</scope>"; 
    
    scopeActivity = (Scope) readActivity(xml, false);
    plugInner(scopeActivity);
    Catch catcher = (Catch) scopeActivity.getFaultHandlers().iterator().next();
    Receive handlerReceive = (Receive) catcher.getActivity();
    
    Token token = executeInner();
    assertNull( token.getEnd() );    
    Token scopeToken = (Token) token.getChildren().values().iterator().next();
    ScopeInstance scopeInstance = Scope.getInstance(scopeToken);
    Token activityToken = scopeInstance.getActivityToken();

    assertEquals(1, activityToken.getChildren().size());

    ScopeIterator childrenIt = new ScopeIterator(scopeToken);
    assertTrue(childrenIt.hasNext());
    assertEquals(EndedState.COMPLETED, ((ScopeInstance)childrenIt.next()).getState());
    assertFalse(childrenIt.hasNext());
    
    //fault receive is completed
    Token handlerToken = scopeInstance.getHandlerToken();
    assertNotNull(handlerToken);
    //TODO test that a fault variable instance is created inside the handler flow token
    handlerReceive.messageReceived(handlerReceive.getReceiver(), handlerToken);

    //parent token advanced and completed abnormally
    assertEquals( EndedState.COMPLETED_ABNORMALLY, scopeInstance.getState() );
    assertNotNull( token.getEnd() );    
  }
  
  protected void assertReceiveAndAdvance(Token token, Receive sourceNode, Activity targetNode) {
    Receive activity = (Receive) token.getNode();
    assertEquals(sourceNode, activity);
    activity.messageReceived(activity.getReceiver(), token);
    assertSame( targetNode , token.getNode() );
  }
  
  protected void assertEventAndComplete(Token token, Receive sourceNode) {
    Receive activity = (Receive) token.getNode();
    assertEquals(sourceNode, activity);
    activity.messageReceived(activity.getReceiver(), token);
    assertCompleted( token );
  }
}