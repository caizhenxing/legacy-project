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

import org.jbpm.graph.def.Transition;

import org.jbpm.bpel.db.AbstractDbTestCase;

public class SequenceDbTest extends AbstractDbTestCase {
  
  public void testActivities() {
    BpelDefinition processDefinition = new BpelDefinition();
    Sequence structured = new Sequence("parent");
    processDefinition.addNode(structured);
    Activity first = new Empty("first");
    Activity second = new Empty("second");
    structured.addNode(first);
    structured.addNode(second);
    
    processDefinition = saveAndReload(processDefinition);

    structured = (Sequence) processDefinition.getNode("parent");
    assertEquals(2, structured.getNodes().size());
    first = (Activity) structured.getNode("first");
    second = (Activity) structured.getNode("second");    
    assertEquals(structured, first.getCompositeActivity());
    assertEquals(structured, second.getCompositeActivity());
  }
  
  public void testDelimiters() {
    BpelDefinition processDefinition = new BpelDefinition();
    Sequence structured = new Sequence("parent");
    processDefinition.addNode(structured);
    
    processDefinition = saveAndReload(processDefinition);

    structured = (Sequence) processDefinition.getNode("parent");
    Activity start = structured.getBegin();
    Activity end = structured.getEnd();
    
    assertNotNull(start);
    assertNotNull(end);
    assertEquals(structured, start.getCompositeActivity());
    assertEquals(structured, end.getCompositeActivity());
  }  
  
  public void testConnections() {
    BpelDefinition processDefinition = new BpelDefinition();
    Sequence structured = new Sequence("parent");
    processDefinition.addNode(structured);
    Activity first = new Empty("first");
    Activity last = new Empty("second");
    structured.addNode(first);
    structured.addNode(last);

    processDefinition = saveAndReload(processDefinition);

    structured = (Sequence) processDefinition.getNode("parent");
    Activity start = structured.getBegin();
    Activity end = structured.getEnd();
    first = (Activity) structured.getNode("first");
    last = (Activity) structured.getNode("second"); 
    Transition from = (Transition) start.getLeavingTransitions().get(0);
    Transition to = (Transition) end.getArrivingTransitions().iterator().next();
    
    assertEquals(from, first.getDefaultArrivingTransition());
    assertEquals(first.getDefaultLeavingTransition(), last.getDefaultArrivingTransition());
    assertEquals(to, last.getDefaultLeavingTransition());
  }
}
