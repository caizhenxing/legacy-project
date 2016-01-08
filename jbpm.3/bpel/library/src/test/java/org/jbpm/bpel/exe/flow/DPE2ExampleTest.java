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
package org.jbpm.bpel.exe.flow;

import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.def.Flow;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.exe.AbstractExeTestCase;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class DPE2ExampleTest extends AbstractExeTestCase {
  private Flow flow;
  private Receive a;
  private Receive b;
  private Receive c;
  private Receive x;
  private Receive y;
  
  /* In the flow, five receive activities A, B, C, X and Y are all concurrently enabled 
   * to start when the flow starts. A, B, and C are connected through links 
   * (with transition conditions with constant truth-value of "true") instead of putting 
   * them into a sequence. B cannot start until the status of its incoming links from X 
   * and Y is determined and the implicit join condition is evaluated. 
   * B and thus C will always be performed. Because the join condition is a disjunction 
   * and the transition condition of link AtoB is the constant "true", the join condition 
   * will always evaluate "true", independent from the values of X and Y targets*/
  
  public void initActivities() {
    a = (Receive) flow.getNode("A");
    b = (Receive) flow.getNode("B");
    c = (Receive) flow.getNode("C");
    x = (Receive) flow.getNode("X");
    y = (Receive) flow.getNode("Y");
  }
  
  public void testFail() throws Exception {
    flow = (Flow) readActivity(getClass().getResource("DPE2-Example.bpel"), false);
    flow.setSuppressJoinFailure(Boolean.FALSE);
    plugInner(flow);
    assertExecution();
  }
  
  public void testSuppress() throws Exception {
    flow = (Flow) readActivity(getClass().getResource("DPE2-Example.bpel"), false);
    flow.setSuppressJoinFailure(Boolean.TRUE);
    plugInner(flow);
    assertExecution();
  }
  
  public void testInitialFail() throws Exception {
    flow = (Flow) readActivity(getClass().getResource("DPE2-Example.bpel"), true);
    flow.setSuppressJoinFailure(Boolean.FALSE);
    plugOuter(flow);
    assertInitialExecution();
  }
  
  public void testInitialSuppress() throws Exception {
    flow = (Flow) readActivity(getClass().getResource("DPE2-Example.bpel"), true);
    flow.setSuppressJoinFailure(Boolean.FALSE);
    plugOuter(flow);
    assertInitialExecution();
  }
  
  public void assertExecution() throws Exception {
    initActivities();    
    Token startToken = executeInner();
    
    //tokens for activities a, b, c, x, y where created upon activation. They must be
    //waiting in their respective receive nodes
    Token tokenA = findToken(startToken, a);
    Token tokenB = findToken(startToken, b);
    Token tokenC = findToken(startToken, c);
    Token tokenX = findToken(startToken, x);
    Token tokenY = findToken(startToken, y);
    
    //activity a message is received, advances to b. 
    assertReceiveAndAdvance( tokenA, a, flow.getEnd() );
    //activity b is not executed due to its incoming sources 
    assertReceiveDisabled( tokenB, b );
    
    //activity x receives message and advances to the flow's end. 
    assertReceiveAndAdvance( tokenX, x, flow.getEnd() );
    //activity y receives message and advances to the flow's end. All the links of b are
    //determined.
    assertReceiveAndAdvance( tokenY, y, flow.getEnd() );
    //b is ready to advance.
    assertReceiveAndAdvance( tokenB, b, flow.getEnd() );
    
    //complete the flow execution
    assertReceiveAndComplete( tokenC, c );
  }  
  
  public void assertInitialExecution() throws Exception {
    initActivities();
    a.setCreateInstance(true);
    Token startToken = executeOuter(a.getReceiver());
    //first receive was started, it must be at flow's end.
    // TODO remove this search?
    // Token tokenA = findToken(startToken, flow.getEnd());
    
    //tokens for activities b, c, x, y where created upon activation. 
    Token tokenB = findToken(startToken, b);
    //activity b is not executed due to its unresolved targets 
    assertReceiveDisabled( tokenB, b );
    
    Token tokenC = findToken(startToken, c);
    Token tokenX = findToken(startToken, x);
    Token tokenY = findToken(startToken, y);
    
    //activity x receives message and advances to the flow's end. 
    assertReceiveAndAdvance( tokenX, x, flow.getEnd() );
    //activity y receives message and advances to the flow's end. All the links of b are
    //determined.
    assertReceiveAndAdvance( tokenY, y, flow.getEnd() );
    //b is ready to advance.
    assertReceiveAndAdvance( tokenB, b, flow.getEnd() );
    
    //complete the flow execution
    assertReceiveAndComplete( tokenC, c );
  }  
}
