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

import java.util.Collection;

import junit.framework.TestCase;

/**
 * @author Juan Cantu
 * @version $Revision: 1.4 $ $Date: 2006/08/22 04:13:10 $
 */
public class FlowDefTest extends TestCase {
  Flow flow;
  Activity first;
  Activity second;
  
  public void setUp() {
    flow = new Flow();
    first = new Empty("first");
    second = new Empty("second");
  }
  
  public void testAddNode() {    
    flow.addNode(first);
    assertConnected(first);
    flow.addNode(second);
    assertConnected(second);
  }
 
  public void testRemoveNode() {
    //remove first, middle and last activities
    flow.addNode(first);
    flow.addNode(second);
    
    flow.removeNode(first);
    assertDisconnected(first);
    flow.removeNode(second);
    assertDisconnected(second);
    
    assertEquals(0, flow.getBegin().getLeavingTransitions().size());
    assertEquals(0, flow.getEnd().getArrivingTransitions().size());
  }
  
  private void assertConnected(Activity activity) {
    Collection transitions = flow.getBegin().getLeavingTransitions();
    assertTrue( transitions.contains( activity.getDefaultArrivingTransition() ) );
    
    transitions = flow.getEnd().getArrivingTransitions();
    assertTrue( transitions.contains(activity.getDefaultLeavingTransition()) );
  }
  
  private void assertDisconnected(Activity activity) {
    //validate that removed activity doesn't have incoming / outgoing transitions
    assertEquals(0, activity.getArrivingTransitions().size());
    assertEquals(0, activity.getLeavingTransitions().size());
  }
 
}
