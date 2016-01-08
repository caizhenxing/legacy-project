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

import javax.xml.namespace.QName;

import org.jbpm.bpel.def.Catch;
import org.jbpm.bpel.exe.FaultInstance;

/**
 * @author Juan Cantu
 * @version $Revision: 1.7 $ $Date: 2006/08/22 04:13:11 $
 */
public class FaultChildrenTest extends AbstractStateTestCase {
  
  public ScopeState getState() {
    return FaultingState.TERMINATING_CHILDREN;
  }
  
  public void testChildrenTerminated() {
    scopeInstance.setFaultInstance(new FaultInstance());
    
    scopeInstance.getState().childrenTerminated(scopeInstance);
   
    //assertEquals(FaultingState.FAULTING_IMPLICITLY, scope.getState());
    //has to be terminated since compensation is disabled when persistence is not available
    assertEquals(EndedState.FAULTED, scopeInstance.getState());
  }
  
  public void testChildrenTerminatedWithHandler() {
    QName faultName = new QName("aFault");
    Catch catcher = new Catch();
    catcher.setFaultName(faultName);
    catcher.setActivity(handlerLog);
    scope.addCatch(catcher);
    FaultInstance faultInstance = new FaultInstance(faultName);
    scopeInstance.setFaultInstance(faultInstance);
    
    scopeInstance.getState().childrenTerminated(scopeInstance);
    
    assertEquals(FaultingState.FAULTING_EXPLICITLY, scopeInstance.getState());
    assertTrue(this.handlerLog.executed);
  }
  
  public void testChildrenCompensated() {
    try { ((HandlingState)scopeInstance.getState()).childrenCompensated(scopeInstance); }
    catch(IllegalStateException e) { return; }
    fail("compensate can't be invoked at this state");
  }
  
  public void testTerminate() {
    ScopeState oldState = scopeInstance.getState();
    scopeInstance.terminate();
    assertEquals(oldState, scopeInstance.getState());
  }   
}
