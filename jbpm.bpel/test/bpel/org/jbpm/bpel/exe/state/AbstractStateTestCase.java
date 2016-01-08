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
package org.jbpm.bpel.exe.state;

import java.util.Date;

import junit.framework.TestCase;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Flow;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.exe.FaultInstance;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantu
 * @version $Revision: 1.11 $ $Date: 2006/08/22 04:13:11 $
 */
public abstract class AbstractStateTestCase extends TestCase {
  
  Token root;
  ScopeInstance scopeInstance;
  TestScopeInstance parent;
  TestScopeInstance activeChild;
  TestScopeInstance handlingChild;
  TestScopeInstance completedChild;
  BpelDefinition pd;
  Scope scope;
  Scope parentScope;
  LogActivity scopeCompletionLog;
  LogActivity handlerLog;
  
  private JbpmContext jbpmContext;
  
  public void setUp() {
    JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance("org/jbpm/bpel/exe/test.jbpm.cfg.xml");
    jbpmContext = jbpmConfiguration.createJbpmContext();
    
    pd = new BpelDefinition("process");

    Sequence rootSequence = new Sequence();
    pd.getGlobalScope().setRoot(rootSequence);
    
    //parent
    parentScope = new Scope();
    parentScope.installFaultExceptionHandler();
    parentScope.setName("parent");
    rootSequence.addNode(parentScope);
    scopeCompletionLog = new LogActivity();
    rootSequence.addNode(scopeCompletionLog);
    
    //parent instance
    ProcessInstance pi = new ProcessInstance(pd);
    root = pi.getRootToken();
    parent = createTestInstance(parentScope, root);
    root.setNode(parentScope);
    
    //scope definition
    scope = new Scope();
    scope.installFaultExceptionHandler();
    scope.setName("scope");
    parentScope.addNode(scope);

    //scope instance
    Token scopeToken = new Token(root, "scope");
    scopeInstance = scope.createInstance(scopeToken);
    scopeInstance.setState( getState() );
    scopeToken.setNode(scope);

    //children definition - instances
    Flow flow = new Flow();
    scope.setRoot(flow);
    
    Token activityToken = scopeInstance.getActivityToken();
    activityToken.setNode(flow);
    
    Scope activeScope = new Scope("active");
    flow.addNode(activeScope);
    Token forkToken = new Token(activityToken, "1");
    forkToken.setNode(activeScope);
    forkToken.setAbleToReactivateParent(true);
    Token aChild = new Token(forkToken, "active");
    activeChild = createTestInstance(activeScope, aChild);
    activeChild.setState(ActiveState.NORMAL_PROCESSING);
    
    Scope handlingScope = new Scope("handling");
    flow.addNode(activeScope);
    forkToken = new Token(activityToken, "2");
    forkToken.setNode(handlingScope);
    forkToken.setAbleToReactivateParent(true);
    aChild = new Token(forkToken, "handling");
    handlingChild = createTestInstance(handlingScope, aChild);
    handlingChild.setState(FaultingState.FAULTING_EXPLICITLY);
    
    Scope completedScope = new Scope("completed");
    flow.addNode(completedScope);
    forkToken = new Token(activityToken, "3");
    forkToken.setNode(completedScope);
    forkToken.setAbleToReactivateParent(true);
    aChild = new Token(forkToken, "completed");
    completedChild = createTestInstance(completedScope, aChild);
    //completed token MUST be null
    completedChild.getToken().end();
    completedChild.setState(EndedState.COMPLETED);
    
    handlerLog = new LogActivity();
  }
  
  protected void tearDown() throws Exception {
    jbpmContext.close();
  }

  public abstract ScopeState getState();
  
  public void testExit() {
    scopeInstance.exit();
    
    assertEquals( EndedState.EXITED, scopeInstance.getState() );
    assertNotNull( scopeInstance.getToken().getEnd() );
  }
  
  public void testFaulted() {
    try { scopeInstance.faulted(null); }
    catch(IllegalStateException e) { return; }
    fail("faulted can't be invoked at this state");
  }
  
  public void testTerminate() {
    try { scopeInstance.terminate(); }
    catch(IllegalStateException e) { return; }
    fail("terminate can't be invoked at this state");
  }  
  
  public void testCompleted() {
    try { scopeInstance.completed(); }
    catch(IllegalStateException e) { return; }
    fail("completed can't be invoked at this state");
  }
  
  public void testCompensate() {
    try { scopeInstance.compensate(null); }
    catch(IllegalStateException e) { return; }
    fail("compensate can't be invoked at this state");
  }
  
  public void testChildrenTerminated() {
    try { scopeInstance.getState().childrenTerminated(scopeInstance); }
    catch(IllegalStateException e) { return; }
    fail("children terminated can't be invoked at this state");
  }
  
  /* compensation won't work since it depends on persistence 
  void assertChildrenCompensated() {
    assertNull(activeChild.compensated);
    assertNull(handlingChild.compensated);
    assertNotNull(completedChild.compensated);
  }
  */
  
  void assertChildrenTerminated() {
    assertTrue(activeChild.terminated);
    assertTrue(handlingChild.terminated);
    assertFalse(completedChild.terminated);
  }
  
  static TestScopeInstance createTestInstance(Scope scope, Token token) {
    TestScopeInstance instance = new TestScopeInstance(scope, token );
    ContextInstance contextInstance = (ContextInstance) token.getProcessInstance().getInstance(ContextInstance.class); 
    contextInstance.createVariable(Scope.VARIABLE_NAME, instance, token);
    return instance;
  }  
   
  static class TestScopeInstance extends ScopeInstance {
    
    boolean childCompensated = false;
    boolean childTerminated = false;
    boolean childFaulted = false;
    
    boolean terminated = false;
    Date compensated;
    
    private static final long serialVersionUID = 1L;
    
    public TestScopeInstance(Scope scope, Token token) {
      super(scope, token );
    }
    
    public void scopeCompensated(ScopeInstance child) {
      childCompensated = true;
    }
    
    public void scopeTerminated(ScopeInstance child) {
      childTerminated = true;
    }
    
    public void faulted( FaultInstance faultInstance ) {
      childFaulted = true;
    }
    
    public void terminate() {
      terminated = true;
    }
    
    public void compensate() {
      compensated = new Date();
    }    
  }
  
  static class LogActivity extends Activity {
    
    boolean executed = false;
    
    private static final long serialVersionUID = 1L;
    
    public void execute(ExecutionContext context) {
      executed = true;
    }
  }  
  
}
