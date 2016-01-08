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

import org.jbpm.bpel.sublang.def.Expression;

/**
 * @author Juan Cantu
 * @version $Revision: 1.5 $ $Date: 2006/08/22 04:13:10 $
 */
public class WhileDefTest extends TestCase {
  While whileBlock;
  Activity child;
  Activity otherChild;
  
  public void setUp() {
    whileBlock = new While();
    child = new Empty("first");
    otherChild = new Empty("second");
  }
  
  public void testAddNode() {    
    whileBlock.addNode(child);
    assertConnected(child);
    assertEquals(whileBlock, child.getCompositeActivity());
  }
  
  public void testAddNodeOverride() {
    whileBlock.addNode(child);
    whileBlock.addNode(otherChild);
    assertConnected(otherChild);
    assertDisconnected(child);
  }
  
  public void testRemoveNode() {
    whileBlock.addNode(child);
    whileBlock.removeNode(child);
    assertDisconnected(child);
    
    assertEquals(0, whileBlock.getNodes().size());
  }
  
  public void testCondition() {
    Expression expression = new Expression();
    whileBlock.setCondition(expression);
    assertEquals(expression, whileBlock.getCondition());
  }
  
  private void assertConnected(Activity activity) {
    Collection transitions = whileBlock.getLoopNode().getLeavingTransitions();
    assertTrue( transitions.contains( activity.getDefaultArrivingTransition() ) );
    
    transitions = whileBlock.getLoopNode().getArrivingTransitions();
    assertTrue( transitions.contains(activity.getDefaultLeavingTransition()) );
    
    assertEquals(whileBlock, activity.getCompositeActivity());
    assertEquals(1, whileBlock.getNodes().size());
  }
  
  private void assertDisconnected(Activity activity) {
    //validate that removed activity doesn't have incoming / outgoing transitions
    assertEquals(0, activity.getArrivingTransitions().size());
    assertEquals(0, activity.getLeavingTransitions().size());
    assertEquals(null, activity.getCompositeActivity());
  }
 
}
