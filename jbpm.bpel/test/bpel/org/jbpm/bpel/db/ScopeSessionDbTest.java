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
package org.jbpm.bpel.db;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.bpel.exe.state.EndedState;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/* Tests the ScopesSession facade. Proves that the next child to compensate of a scope instance 
 * will always be a nested scope instance at any level with a COMPLETED state and the latest 
 * completion date */
public class ScopeSessionDbTest extends AbstractDbTestCase {
  
  /*
   * rootScope
   * |
   * |-- A
   * |   |
   * |   |-- A.1
   * |   |   |
   * |   |   |-- A.1.1
   * |   |
   * |   |-- A.2
   * |
   * |
   * |-- B
   *     |
   *     |-- B.1
   **/
  
  ProcessInstance processInstance;
  ScopeInstance siRoot;
  ScopeInstance siA;
  ScopeInstance siA1;
  ScopeInstance siA2;
  ScopeInstance siA11;
  ScopeInstance siB;
  ScopeInstance siB1;
  
  ScopeSession scopeSession;
  
  public void setUp() throws Exception {
    super.setUp();

    // define the process
    BpelDefinition processDefinition = new BpelDefinition("definition");
    Scope globalScope = processDefinition.getGlobalScope();
    
    Sequence rootSequence = new Sequence();
    globalScope.addNode(rootSequence);
    
    Scope scopeA = new Scope("A");
    rootSequence.addNode(scopeA);
    
    Sequence sequenceA = new Sequence();
    scopeA.addNode(sequenceA);
    
    Scope scopeA1 = new Scope("A.1");
    sequenceA.addNode(scopeA1);
    
    Scope scopeA11 = new Scope("A.1.1");
    scopeA1.addNode(scopeA11);
    
    Scope scopeA2 = new Scope("A.2");
    sequenceA.addNode(scopeA2);
    
    Scope scopeB = new Scope("B");
    rootSequence.addNode(scopeB);
    
    Scope scopeB1 = new Scope("B.1");
    scopeB.addNode(scopeB1);
    
    // save the definition
    graphSession.saveProcessDefinition(processDefinition);
    
    // create a process instance
    processInstance = new ProcessInstance(processDefinition);
    Token root = processInstance.getRootToken();
    siRoot = globalScope.createInstance(root); 
    
    // create scope instances
    Token tokenA = new Token(root, "A");
    tokenA.setNode(scopeA);
    siA = scopeA.createInstance(tokenA);
    
    Token tokenA1 = new Token(tokenA, "A.1");
    tokenA1.setNode(scopeA1);
    siA1 = scopeA1.createInstance(tokenA1);
    
    Token tokenA2 = new Token(tokenA, "A.2");
    new Token(tokenA, "implicit a");
    tokenA2.setNode(scopeA2);
    siA2 = scopeA2.createInstance(tokenA2);
    
    Token tokenA11 = new Token(tokenA1, "A.1.1");
    new Token(tokenA1, "implicit a1");
    tokenA11.setNode(scopeA11);
    siA11 = scopeA11.createInstance(tokenA11);
    
    Token tokenB = new Token(root, "B");
    tokenB.setNode(scopeB);
    siB = scopeB.createInstance(tokenB);
    
    Token tokenB1 = new Token(tokenB, "B.1");
    tokenB1.setNode(scopeB1);
    siB1 = scopeB1.createInstance(tokenB1);
  }
  
  public void testNextChildToCompensate() throws Exception {
    //completed scopes will be A.2, B.1 and A.1.1 (in that order)
    siA2.setState(EndedState.COMPLETED);
    siA2.getToken().end();
    Thread.sleep(50);
    siB1.setState(EndedState.COMPLETED);
    siB1.getToken().end();
    Thread.sleep(50);
    siA11.setState(EndedState.COMPLETED);
    siA11.getToken().end();
    //save the process instance
    processInstance = saveAndReload(processInstance);
    assertEquals("A.2", scopeSession.nextChildToCompensate(siRoot).getDefinition().getName());
    assertEquals("A.2", scopeSession.nextChildToCompensate(siA).getDefinition().getName());
    assertEquals("B.1", scopeSession.nextChildToCompensate(siB).getDefinition().getName());
    assertEquals("A.1.1", scopeSession.nextChildToCompensate(siA1).getDefinition().getName());
    assertNull( scopeSession.nextChildToCompensate(siA11) );
    assertNull( scopeSession.nextChildToCompensate(siA2) );
    assertNull( scopeSession.nextChildToCompensate(siB1) );
  }
  
  public void testNextScopeToCompensate() throws Exception {
    //completed scopes will be  A.2 and B.1 and A.1. A.1.1 is already compensated
    siA11.setState(EndedState.COMPENSATED);
    siA11.getToken().end();
    Thread.sleep(50);
    siA2.setState(EndedState.COMPLETED);
    siA2.getToken().end();
    Thread.sleep(50);
    siB1.setState(EndedState.COMPLETED);
    siB1.getToken().end();
    Thread.sleep(50);
    siA1.setState(EndedState.COMPLETED);
    siA1.getToken().end();
    //save the process instance
    processInstance = saveAndReload(processInstance);
    assertEquals("A.2", scopeSession.nextScopeToCompensate(processInstance, siA.getDefinition()).getDefinition().getName());
    assertEquals("B.1", scopeSession.nextScopeToCompensate(processInstance, siB.getDefinition()).getDefinition().getName());
    assertEquals("A.1", scopeSession.nextScopeToCompensate(processInstance, siA1.getDefinition()).getDefinition().getName());
    assertEquals("B.1", scopeSession.nextScopeToCompensate(processInstance, siB1.getDefinition()).getDefinition().getName());
    assertEquals("A.2", scopeSession.nextScopeToCompensate(processInstance, siA2.getDefinition()).getDefinition().getName());
    assertNull(scopeSession.nextScopeToCompensate(processInstance, siA11.getDefinition()));
  }

  public ProcessInstance saveAndReload(ProcessInstance pi) {
    pi = super.saveAndReload(pi);
    scopeSession = ScopeSession.getInstance(jbpmContext);
    return pi;
  }
}
