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


/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class FaultChildrenAtHandlerTest extends AbstractStateTestCase {

  public ScopeState getState() {
    return FaultingState.TERMINATING_CHILDREN_AT_HANDLER;
  }
  
  public void testChildrenTerminated() {
    scopeInstance.getState().childrenTerminated(scopeInstance);
    
    assertEquals( EndedState.FAULTED, scopeInstance.getState() );
  }  
  
  public void testChildrenTerminatedAtScope() {
    scopeInstance.getState().childrenTerminated(scopeInstance);
    
    assertEquals( EndedState.FAULTED, scopeInstance.getState() );
    assertTrue( parent.childFaulted );
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
