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
package org.jbpm.bpel.xml;

import org.jbpm.bpel.def.Compensate;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.ScopeHandler;
import org.jbpm.bpel.def.Sequence;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class CompensateReaderTest extends AbstractReaderTestCase {
  
  public void testScope() throws Exception {
    Scope aScope = new Scope("aScope");
    scope.addNode(aScope);
    ScopeHandler handler = new ScopeHandler();
    scope.setHandler(Scope.COMPENSATION, handler);

    
    String xml = "<compensate scope='aScope'/>";
    Compensate compensate = 
      (Compensate) readActivity(parseAsBpelElement(xml), handler);
    
    assertEquals( aScope , compensate.getScope());
  }
  
  public void testNestedScope() throws Exception {
    Sequence sequence = new Sequence("aSequence");
    Scope aScope = new Scope("aScope");
    sequence.addNode(aScope);
    scope.addNode(sequence);
    ScopeHandler handler = new ScopeHandler();
    scope.setHandler(Scope.COMPENSATION, handler);
    
    String xml = "<compensate scope='aScope'/>";
    Compensate compensate = 
      (Compensate) readActivity(parseAsBpelElement(xml), handler);
    
    assertEquals( aScope , compensate.getScope());
  }
  
  public void testScopeDefault() throws Exception {
    String xml = "<compensate/>";
    Compensate compensate = (Compensate) readActivity(xml);
    assertEquals( null , compensate.getScope());
  }
}
