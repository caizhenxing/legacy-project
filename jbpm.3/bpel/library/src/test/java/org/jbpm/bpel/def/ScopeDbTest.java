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
package org.jbpm.bpel.def;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.Scope.FaultActionHandler;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ExceptionHandler;

/**
 * @author Juan Cantu
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class ScopeDbTest extends AbstractDbTestCase {
  
  public void testActivity() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = new Scope("scope");
    processDefinition.getGlobalScope().setRoot(scope);
    scope.addNode(new Empty("e"));
    
    processDefinition = saveAndReload(processDefinition);
    scope = (Scope) session.load(Scope.class, 
        new Long(processDefinition.getGlobalScope().getRoot().getId()));
    
    assertEquals(scope, scope.getRoot().getCompositeActivity() );
  }
  
  public void testFaultExceptionHandler() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = new Scope("scope");
    scope.installFaultExceptionHandler();
    processDefinition.addNode(scope);
    
    processDefinition = saveAndReload(processDefinition);

    scope = (Scope) processDefinition.getNode("scope");
    ExceptionHandler handler = (ExceptionHandler) scope.getExceptionHandlers().get(0);
    Action action = (Action) handler.getActions().get(0);
    assertEquals(FaultActionHandler.class.getName(), action.getActionDelegation().getClassName());
  }
  
  public void testIsolated() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = new Scope("scope");
    scope.setIsolated(true);
    processDefinition.addNode(scope);

    processDefinition = saveAndReload(processDefinition);

    scope = (Scope) processDefinition.getNode("scope");
    assertTrue( scope.isIsolated() );
  }
  
  public void testFaultHandlers() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    Catch catcher = new Catch();
    catcher.setActivity( new Empty("activity") );
    scope.addCatch( catcher );
    
    processDefinition = saveAndReload(processDefinition);

    catcher = (Catch) processDefinition.getGlobalScope().getFaultHandlers().get(0);
    assertEquals(catcher.getCompositeActivity(), processDefinition.getGlobalScope());
    assertNotNull( catcher.getActivity() );
  }
  
  public void testTerminationHandler() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    ScopeHandler handler = new ScopeHandler();
    handler.setActivity( new Empty("activity") );
    scope.setHandler( Scope.TERMINATION , handler );
    
    processDefinition = saveAndReload(processDefinition);

    handler = processDefinition.getGlobalScope().getHandler(Scope.TERMINATION);
    assertEquals(handler.getCompositeActivity(), processDefinition.getGlobalScope());
    assertNotNull( handler.getActivity() );
  } 
  
  public void testCompensationHandler() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    ScopeHandler handler = new ScopeHandler();
    handler.setActivity( new Empty("activity") );
    scope.setHandler( Scope.COMPENSATION, handler );
    
    processDefinition = saveAndReload(processDefinition);

    handler = processDefinition.getGlobalScope().getHandler(Scope.COMPENSATION);
    assertEquals(handler.getCompositeActivity(), processDefinition.getGlobalScope());
    assertNotNull( handler.getActivity() );
  } 
  
  public void testCatchAllHandler() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    ScopeHandler handler = new ScopeHandler();
    handler.setActivity( new Empty("activity") );
    scope.setHandler( Scope.CATCH_ALL, handler );
    
    processDefinition = saveAndReload(processDefinition);

    handler = processDefinition.getGlobalScope().getHandler(Scope.CATCH_ALL);
    assertEquals(handler.getCompositeActivity(), processDefinition.getGlobalScope());
    assertNotNull( handler.getActivity() );
  }   
  
  public void testOnEvent() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    OnEvent onEvent = new OnEvent();
    Empty eventActivity = new Empty("activity");
    onEvent.setActivity( eventActivity );
    scope.addOnEvent(onEvent);
    
    processDefinition = saveAndReload(processDefinition);

    onEvent = (OnEvent) processDefinition.getGlobalScope().getOnEvents().iterator().next();
    assertEquals(onEvent.getCompositeActivity(), processDefinition.getGlobalScope());
    assertNotNull( onEvent.getActivity() );
  } 
  
  public void testOnAlarm() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    OnAlarm onAlarm = new OnAlarm();
    Empty eventActivity = new Empty("activity");
    onAlarm.setActivity( eventActivity );
    scope.addOnAlarm(onAlarm);
    
    processDefinition = saveAndReload(processDefinition);

    onAlarm = (OnAlarm) processDefinition.getGlobalScope().getOnAlarms().iterator().next();
    assertEquals(onAlarm.getCompositeActivity(), processDefinition.getGlobalScope());
    assertNotNull( onAlarm.getActivity() );
  } 
  
  public void testVariableDefinitions() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    VariableDefinition variable = new VariableDefinition();
    variable.setName("aVar");
    scope.addVariable(variable);
    
    processDefinition = saveAndReload(processDefinition);

    assertNotNull( scope.getVariable("aVar") );
  } 
  
  public void testPartnerLinkDefinitions() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    PartnerLinkDefinition plink = new PartnerLinkDefinition();
    plink.setName("pl");
    scope.addPartnerLink(plink);
    
    processDefinition = saveAndReload(processDefinition);

    assertNotNull( scope.getPartnerLink("pl") );
  } 
  
  public void testCorrelationSetDefinitions() {
    BpelDefinition processDefinition = new BpelDefinition();
    Scope scope = processDefinition.getGlobalScope();
    CorrelationSetDefinition cs = new CorrelationSetDefinition();
    cs.setName("cs");
    scope.addCorrelationSet(cs);
    
    processDefinition = saveAndReload(processDefinition);

    assertNotNull( scope.getCorrelationSet("cs") );
  }       
  
  public void testOnEventActivity() {
    OnEvent onEvent = new OnEvent();
    onEvent.setActivity(new Empty("handlerActivity"));
    
    Scope scope = new Scope("scope");
    scope.setRoot(new Empty("root"));
    scope.addOnEvent(onEvent);

    BpelDefinition processDefinition = new BpelDefinition();
    processDefinition.getGlobalScope().setRoot(scope);
    
    processDefinition = saveAndReload(processDefinition);
    scope = (Scope) session.load(Scope.class, 
        new Long(processDefinition.getGlobalScope().getRoot().getId()));
    
    assertSame( scope, scope.getRoot().getCompositeActivity() );
    onEvent = (OnEvent) scope.getOnEvents().iterator().next(); 
    assertEquals("handlerActivity", onEvent.getActivity().getName());
  }  
}
