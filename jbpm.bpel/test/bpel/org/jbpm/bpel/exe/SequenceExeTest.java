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

import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Sequence;

/**
 * @author Juan Cantú
 * @version $Revision: 1.5 $ $Date: 2006/08/22 04:13:10 $
 */
public class SequenceExeTest extends AbstractExeTestCase {
  Sequence sequence;
  Receive receiveA;
  Receive receiveB;
  Receive receiveC;
  
  public void initActivities() {
    receiveA = (Receive) sequence.getNode("a");
    receiveB = (Receive) sequence.getNode("b");
    receiveC = (Receive) sequence.getNode("c");
  }
  
  public void testInitialSequence() throws Exception {
    String xml = 
    	"<sequence>" +
        "	<receive name='a' createInstance='yes' partnerLink='aPartner' operation='o'/>" +
        "	<receive name='b' partnerLink='aPartner' operation='o'/>" +
        "	<receive name='c' partnerLink='aPartner' operation='o'/>" +
    	"</sequence>";
    
    sequence = (Sequence) readActivity(xml, true);
    plugOuter(sequence);
    initActivities();
    Token token = executeOuter(receiveA.getReceiver());
    assertReceiveAndAdvance(token, receiveB, receiveC);
    assertReceiveAndComplete(token, receiveC);
  }
  
  public void testNestedSequence() throws Exception { 
    String xml = 
    	"<sequence>" +
        "	<receive name='a' partnerLink='aPartner' operation='o'/>" +
        "	<receive name='b' partnerLink='aPartner' operation='o'/>" +
        "	<receive name='c' partnerLink='aPartner' operation='o'/>" +
    	"</sequence>";
    
    sequence = (Sequence) readActivity(xml, false);
    plugInner(sequence);
    initActivities();
    Token token = executeInner();
    assertReceiveAndAdvance(token, receiveA, receiveB);
    assertReceiveAndAdvance(token, receiveB, receiveC);
    assertReceiveAndComplete(token, receiveC);
  }  
}
