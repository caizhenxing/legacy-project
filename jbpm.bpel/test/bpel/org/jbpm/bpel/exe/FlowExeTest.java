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
package org.jbpm.bpel.exe;

import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.def.Flow;
import org.jbpm.bpel.def.Receive;

/**
 * @author Juan Cantú
 * @version $Revision: 1.8 $ $Date: 2006/08/22 04:13:10 $
 */
public class FlowExeTest extends AbstractExeTestCase {
  Flow flow;
  Receive receiveA;
  Receive receiveB;
  Receive receiveC;
  
  String xml = 
  	"<flow>" +
      "	<receive name='a' partnerLink='aPartner' operation='o'/>" +
      "	<receive name='b' partnerLink='aPartner' operation='o'/>" +
      "	<receive name='c' partnerLink='aPartner' operation='o'/>" +
  	"</flow>";
  
  public void testInitialFirstScenario() throws Exception {
    flow = (Flow) readActivity(xml, true);
    plugOuter(flow);
    validateInitial("a", "b", "c");
  }
  
  public void testInitialSecondScenario() throws Exception {
    flow = (Flow) readActivity(xml, true);
    plugOuter(flow);
    validateInitial("b", "a", "c");
  }
  
  public void testInitialThirdScenario() throws Exception {
    flow =  (Flow) readActivity(xml, true);
    plugOuter(flow);  
    validateInitial("c", "a", "b");
  }
  
  public void testNestedFirstScenario() throws Exception {
    flow = (Flow) readActivity(xml, false);
    plugInner(flow);
    validateNested("a", "b", "c");
  }  
  
  public void testNestedSecondScenario() throws Exception {
    flow = (Flow) readActivity(xml, false);
    plugInner(flow); 
    validateNested("b", "a", "c");
  }  
  
  public void testNestedThirdScenario() throws Exception {
    flow = (Flow) readActivity(xml, false);
    plugInner(flow);
    validateNested("c", "a", "b");
  }  
  
  public void initActivities(String a, String b, String c, boolean initial) {
    receiveA = (Receive) flow.getNode(a);
    if(initial) receiveA.setCreateInstance(true);
    receiveB = (Receive) flow.getNode(b);
    receiveC = (Receive) flow.getNode(c);
  }
  
  public void validateInitial(String a, String b, String c) {
    initActivities(a, b, c, true);
    Token parent = executeOuter(receiveA.getReceiver());
    
    Token tokenA = parent.getChild(receiveA.getName());
    //tokenA must be waiting at the flow end for tokenB and tokenC
    assertSame( flow.getEnd() , tokenA.getNode() );
    
    //tokenB and tokenC where created upon activation. They must be
    //waiting in their respective receive nodes.
    Token tokenB = parent.getChild(receiveB.getName());
    Token tokenC = parent.getChild(receiveC.getName());
    
    //tokenB is triggered. It must move to the flow's end.
    assertReceiveAndAdvance(tokenB, receiveB, flow.getEnd());

    //tokenC is triggered. The process instance is completed.
    assertReceiveAndComplete(tokenC, receiveC);
  }
  
  public void validateNested(String a, String b, String c) {
    initActivities(a, b, c, false);
    Token startToken = executeInner();
    
    //tokenA, tokenB and tokenC where created upon activation. They must be
    //waiting in their respective receive nodes
    Token tokenA = startToken.getChild(a);
    Token tokenB = startToken.getChild(b);
    Token tokenC = startToken.getChild(c);
  
    //tokenA is triggered. It must move to the flow's end.
    assertReceiveAndAdvance(tokenA, receiveA, flow.getEnd());
    
    //tokenB is triggered. It must move to the flow's end.
    assertReceiveAndAdvance(tokenB, receiveB, flow.getEnd());

    //tokenC is triggered. The process instance is completed.
    assertReceiveAndComplete(tokenC, receiveC);
  }
}
