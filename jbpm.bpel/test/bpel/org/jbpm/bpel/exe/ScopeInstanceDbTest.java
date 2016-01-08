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

import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Compensate;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.exe.state.EndedState;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantu
 * @version $Revision: 1.7 $ $Date: 2006/08/22 04:13:10 $
 */
public class ScopeInstanceDbTest extends AbstractDbTestCase {
  
  public void testToken() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    processDefinition.getGlobalScope()
    .createInstance(processInstance.getRootToken());
    
    processInstance = saveAndReload(processInstance);
    ScopeInstance scopeInstance = Scope.getInstance(processInstance.getRootToken());
    assertEquals(processInstance.getRootToken(), scopeInstance.getToken());
  }  
  
  public void testFaultInstance() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    QName faultName = new QName(BpelConstants.NS_EXAMPLES, "fault");
    processDefinition.getGlobalScope()
    .createInstance(processInstance.getRootToken())
    .setFaultInstance(new FaultInstance(faultName));
    
    processInstance = saveAndReload(processInstance);
    ScopeInstance scopeInstance = Scope.getInstance(processInstance.getRootToken());
    assertEquals(faultName, scopeInstance.getFaultInstance().getName());
  } 
  
  public void testCompensateCompensationListener() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    Scope globalScope = processDefinition.getGlobalScope();
    Compensate compensateActivity = new Compensate();
    globalScope.setRoot(compensateActivity);
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    globalScope.createInstance(processInstance.getRootToken())
    .setCompensationListener(compensateActivity);
    
    processInstance = saveAndReload(processInstance);
    ScopeInstance scopeInstance = Scope.getInstance(processInstance.getRootToken());
    assertNotNull(scopeInstance.getCompensationListener());
  } 
  
  public void testScopeInstanceCompensationListener() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    Scope globalScope = processDefinition.getGlobalScope();
    Scope localScope = new Scope();
    globalScope.setRoot(localScope);
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    Token rootToken = processInstance.getRootToken();
    ScopeInstance compensator = globalScope.createInstance(rootToken);
    
    Token child = new Token(rootToken, "child");
    child.setNode(localScope);
    localScope.createInstance(child).setCompensationListener(compensator);
    
    processInstance = saveAndReload(processInstance);

    rootToken = processInstance.getRootToken();
    ScopeInstance globalScopeInstance = Scope.getInstance(rootToken);
    assertSame(globalScopeInstance,  Scope.getInstance(rootToken.getChild("child")).getCompensationListener());
  } 
  
  public void testScopeState() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    processDefinition.getGlobalScope()
    .createInstance(processInstance.getRootToken())
    .setState(EndedState.COMPLETED);
    
    processInstance = saveAndReload(processInstance);

    ScopeInstance scopeInstance = Scope.getInstance(processInstance.getRootToken());
    assertEquals(EndedState.COMPLETED, scopeInstance.getState());
  }
  
  public void testEventState() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    EventInstance.create(processDefinition.getGlobalScope(), processInstance.getRootToken())
    .setState(EndedState.TERMINATED);
    
    processInstance = saveAndReload(processInstance);

    ScopeInstance event = Scope.getInstance(processInstance.getRootToken());
    assertEquals(EndedState.TERMINATED, event.getState());
  }    
}
