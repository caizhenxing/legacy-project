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
public class CompImplicitHandlerTest extends AbstractStateTestCase {
  
  public ScopeState getState() {
    return CompensatingState.COMPENSATING_IMPLICITLY;
  }
  
  public void testChildrenCompensated() {
    scopeInstance.setCompensationListener(parent);
    ((HandlingState)scopeInstance.getState()).childrenCompensated(scopeInstance);
    
    assertEquals( EndedState.COMPENSATED, scopeInstance.getState() );
    assertTrue(parent.childCompensated);
  }  
  
  public void testChildrenCompensatedAtScope() {
    scopeInstance.setCompensationListener(parent);
    ((HandlingState)scopeInstance.getState()).childrenCompensated(scopeInstance);
    
    assertEquals( EndedState.COMPENSATED, scopeInstance.getState() );
    assertTrue( parent.childCompensated );
  } 
  
  public void testFaulted() {
    scopeInstance.faulted(null);
    assertEquals(EndedState.FAULTED, scopeInstance.getState());
  }
  
  public void testFaultedAtScope() {
    scopeInstance.faulted(null);
    assertEquals(EndedState.FAULTED, scopeInstance.getState());
    assertFalse(parent.childCompensated);
    assertTrue(parent.childFaulted);
  }
}
