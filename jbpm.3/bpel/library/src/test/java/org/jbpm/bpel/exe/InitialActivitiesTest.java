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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.def.*;
import org.jbpm.bpel.integration.def.Receiver;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class InitialActivitiesTest extends AbstractExeTestCase {
  private List receivers;
  private Receive receiveR;
  private Receive sequenceR;
  private Pick pick;
  private Receive flowR1;
  private Receive flowR2;
  
  String xml = 
  	"<flow>" +
      "	<receive name='a' partnerLink='aPartner' operation='o'/>" +
      " <pick name='b'>" +
      "		<onMessage partnerLink='aPartner' operation='o'>" +
      " 		<empty/>" +
      " 	</onMessage>" +
      "		<onMessage partnerLink='aPartner' operation='o'>" +
      " 		<empty/>" +
      " 	</onMessage>" +      
      " </pick>" +
      " <sequence name='sequence'>" +
      "		<receive name='c' partnerLink='aPartner' operation='o'/>" +
      "		<empty/>" +
      " </sequence>" +      
      " <flow name='flow'>" +
      "		<receive name='d' partnerLink='aPartner' operation='o'/>" +
      "		<receive name='e' partnerLink='aPartner' operation='o'/>" +
      " </flow>" +      
  	"</flow>";
  
  public void setUp() throws Exception {
    super.setUp();
    
    StructuredActivity root = (StructuredActivity) readActivity(xml, true);
    plugOuter(root);
    
    //Create a list with all the receivers that can trigger new instances
    receivers = new ArrayList();
    receiveR = ((Receive) root.getNode("a"));
    receivers.add(receiveR.getReceiver());

    pick = ((Pick) root.getNode("b"));
    receivers.addAll(pick.getOnMessages());
    
    Sequence sequence = (Sequence) root.getNode("sequence"); 
    sequenceR = ((Receive)sequence.getNode("c"));
    receivers.add(sequenceR.getReceiver());
    
    Flow flow = ((Flow) root.getNode("flow"));
    flowR1 = ((Receive)flow.getNode("d"));
    receivers.add(flowR1.getReceiver());
    flowR2 = ((Receive)flow.getNode("e"));
    receivers.add(flowR2.getReceiver());
  }  
  
  public void testReceiveReceiver() {
    receiveR.setCreateInstance(true);
    validateOtherReceivers(receiveR.getReceiver());
  }
  
  public void testSequenceReceiver() {
    sequenceR.setCreateInstance(true);
    validateOtherReceivers(sequenceR.getReceiver());
  }
  
  public void testPickFirstReceiver() {
    pick.setCreateInstance(true);
    Iterator it = pick.getOnMessages().iterator();
    Receiver receiver = (Receiver) it.next();
    receivers.remove(it.next());
    validateOtherReceivers( receiver );
  }
  
  public void testPickSecondReceiver() {
    pick.setCreateInstance(true);
    Iterator it = pick.getOnMessages().iterator();
    receivers.remove(it.next());
    validateOtherReceivers((Receiver) it.next());
  }
  
  public void testFlowFirstReceiver() {
    flowR1.setCreateInstance(true);
    validateOtherReceivers(flowR1.getReceiver());
  }
  
  public void testFlowSecondReceiver() {
    flowR2.setCreateInstance(true);
    validateOtherReceivers(flowR2.getReceiver());
  }
  
  //validate for every receiver that when a new instance is triggered, 
  //a token is created for the rest of the receivers.
  public void validateOtherReceivers(Receiver trigger) {    
    Token parent = executeOuter(trigger).getProcessInstance().getRootToken();
      
    for(Iterator it = receivers.iterator(); it.hasNext();) {
      Receiver otherReceiver = (Receiver) it.next(); 
      
      if(!otherReceiver.equals(trigger)) {
        Object activity = otherReceiver.getInboundMessageActivity();
        Activity node = 
          activity instanceof Pick ? ((Pick)activity).getBegin() : (Activity)activity;
        Token token = (Token)parent.getChildrenAtNode(node).iterator().next();
        assertReceiverEnabled( token, otherReceiver );
      }
    }
  }
}
