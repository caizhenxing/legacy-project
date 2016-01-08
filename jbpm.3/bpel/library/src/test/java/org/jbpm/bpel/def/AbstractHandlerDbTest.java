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

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class AbstractHandlerDbTest extends AbstractDbTestCase {
  
  protected ScopeHandler scopeHandler;

  public void setUp() throws Exception {
    // prepare db stuff
    super.setUp();
    // process, create after opening the jbpm context
    scopeHandler = create(new BpelDefinition("process"));
  }

  public void testActivity() {
    // prepare persistent objects
    // activity
    Empty activity = new Empty("empty");
    // handler
    scopeHandler.setActivity(activity);
    
    // save objects and load them back
    BpelDefinition process = saveAndReload(scopeHandler.getBpelDefinition());
    scopeHandler = get(process);
    
    // verify the retrieved objects
    assertEquals("empty", scopeHandler.getActivity().getName());
  }
  
  public void testScope() {    
    // save objects and load them back
    BpelDefinition process = saveAndReload(scopeHandler.getBpelDefinition());
    scopeHandler = get(process);
    
    // verify the retrieved objects
    assertSame(process.getGlobalScope(), scopeHandler.getCompositeActivity());
  }

  protected abstract ScopeHandler create(BpelDefinition process);

  protected abstract ScopeHandler get(BpelDefinition process);

}
