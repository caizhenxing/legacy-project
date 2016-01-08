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
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class IfDefTest extends TestCase {
  If ifStruct = new If();
  Activity first = new Empty("first");
  Activity second = new Empty("second");
  
  public void setUp() {
    ifStruct.addNode(first);
    ifStruct.addNode(second);
  }
  
  public void testAddNode() {    
    assertConnected(first);
    assertEquals(null, ifStruct.getCondition(first));
    assertConnected(second);
    assertEquals(null, ifStruct.getCondition(second));
  }
  
  public void testCondition() {
    Expression firstExpression = new Expression();
    Expression secondExpression = new Expression();
    
    ifStruct.setCondition(first, firstExpression);
    ifStruct.setCondition(second, secondExpression);
    
    assertEquals(firstExpression, ifStruct.getCondition(first));
    assertEquals(secondExpression, ifStruct.getCondition(second));
  }
  
  public void testRemoveNode() {
    //remove first, middle and last activities
    ifStruct.setElse(second);
    
    ifStruct.removeNode(first);
    assertDisconnected(first);
    
    ifStruct.removeNode(second);
    assertDisconnected(second);
    
    assertEquals(0, ifStruct.getBegin().getLeavingTransitions().size());
    assertEquals(0, ifStruct.getEnd().getArrivingTransitions().size());
    
    assertEquals(0, ifStruct.getNodes().size());
    assertEquals(0, ifStruct.getConditions().size());
  }
  
  public void testElse() {    
    ifStruct.setElse(first);
    assertEquals(first, ifStruct.getElse());
  }
  
  public void testElseOverride() {    
    ifStruct.setElse(second);
    assertEquals(1, ifStruct.getBranches().size());
    
    ifStruct.setElse(first);
    assertEquals(0, ifStruct.getBranches().size());
    
    assertEquals(first, ifStruct.getElse());
    assertEquals(ifStruct, first.getCompositeActivity());
  }
  
  public void testElseDefault() {    
    assertEquals(null, ifStruct.getElse());
  }
  
  public void testGetBranches() {
    assertEquals(2, ifStruct.getBranches().size());
  }
  
  public void testGetBranchesElse() {
    ifStruct.setElse(second);
    assertEquals(1, ifStruct.getBranches().size());
  }
  
  public void testReorderBranch() {
    Expression firstExpression = new Expression();
    Expression secondExpression = new Expression();
    
    ifStruct.setCondition(first, firstExpression);
    ifStruct.setCondition(second, secondExpression);
    
    ifStruct.reorderNode(1, 0);
    
    assertEquals(second, ifStruct.getNodes().get(0));
    assertEquals(firstExpression, ifStruct.getCondition(first));
    assertEquals(secondExpression, ifStruct.getCondition(second));
  }
  
  public void testReorderElse() {
    Expression firstExpression = new Expression();
    ifStruct.setCondition(first, firstExpression);
    ifStruct.setElse(second);
    
    ifStruct.reorderNode(1, 0);
    
    assertEquals(firstExpression, ifStruct.getCondition(second));
    assertEquals(first, ifStruct.getElse());
  }
  
  private void assertConnected(Activity activity) {
    Collection transitions = ifStruct.getBegin().getLeavingTransitions();
    assertTrue( transitions.contains( activity.getDefaultArrivingTransition() ) );
    
    transitions = ifStruct.getEnd().getArrivingTransitions();
    assertTrue( transitions.contains(activity.getDefaultLeavingTransition()) );
  }
  
  private void assertDisconnected(Activity activity) {
    //validate that removed activity doesn't have incoming / outgoing transitions
    assertEquals(0, activity.getArrivingTransitions().size());
    assertEquals(0, activity.getLeavingTransitions().size());
  }
 
}
