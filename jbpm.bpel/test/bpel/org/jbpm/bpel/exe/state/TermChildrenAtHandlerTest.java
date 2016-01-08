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
 * @version $Revision: 1.5 $ $Date: 2006/08/22 04:13:11 $
 */
public class TermChildrenAtHandlerTest extends AbstractStateTestCase {

  public ScopeState getState() {
    return TerminatingState.TERMINATING_CHILDREN_AT_HANDLER;
  }
  
  public void testChildrenTerminated() {
    scopeInstance.getState().childrenTerminated(scopeInstance);
    
    assertEquals( EndedState.TERMINATED, scopeInstance.getState() );
  }  
  
  public void testChildrenTerminatedAtScope() {
    scopeInstance.getState().childrenTerminated(scopeInstance);
    
    assertEquals( EndedState.TERMINATED, scopeInstance.getState() );
    assertTrue( parent.childTerminated );
  } 
  
  public void testChildrenCompensated() {
    try { ((HandlingState)scopeInstance.getState()).childrenCompensated(scopeInstance); }
    catch(IllegalStateException e) { return; }
    fail("childrenCompensated can't be invoked at this state");
  }
}
