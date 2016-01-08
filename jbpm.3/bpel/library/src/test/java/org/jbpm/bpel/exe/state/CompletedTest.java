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

import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.ScopeHandler;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class CompletedTest extends AbstractStateTestCase {

  public ScopeState getState() {
    return EndedState.COMPLETED;
  }  
  
  public void testCompensate() {
    scopeInstance.compensate(parent);
    
    assertTrue( parent.childCompensated );
    assertFalse( handlerLog.executed );
    //compensation won't work since persistence is disabled. 
    //assertEquals( CompensatingState.COMPENSATING_IMPLICITLY, scope.getState() );
    //assertChildrenCompensated();
    assertEquals( EndedState.COMPENSATED, scopeInstance.getState() );
  }
  
  public void testCompensateWithHandler() {
    ScopeHandler hdlr = new ScopeHandler();
    hdlr.setActivity(handlerLog);
    scope.setHandler(Scope.COMPENSATION, hdlr);
    
    scopeInstance.compensate(scopeInstance.getParent());
    assertTrue( handlerLog.executed );
    assertEquals( CompensatingState.COMPENSATING_EXPLICITLY, scopeInstance.getState() );
  } 
  
}
