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
package org.jbpm.bpel.integration.def;

import javax.wsdl.Operation;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.impl.OperationImpl;
import org.jbpm.bpel.wsdl.impl.PortTypeImpl;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:16 $
 */
public class ReceiverDbTest extends AbstractDbTestCase {
  
  BpelDefinition process;
  Receiver receiver = new Receiver();
  
  static final String RECEIVE_NAME = "r";
  
  public void setUp() throws Exception {
    super.setUp();
    // activity
    Receive receive = new Receive(RECEIVE_NAME);
    receive.setReceiver(receiver);
    // process, create after opening jbpm context
    process = new BpelDefinition();
    process.addNode(receive);
  }
  
  public void testCorrelations() {
    // prepare persistent objects
    receiver.setCorrelations(new Correlations());
    
    // save objects and load them back
    process = saveAndReload(process); 
    receiver = getReceiver(process);
    
    // verify retrieved objects
    assertNotNull( receiver.getCorrelations() );
  }
  
  public void testOperation() {    
    // prepare persistent objects
    // operation
    Operation operation = new OperationImpl();
    operation.setName("o");
    // port type
    PortType portType = new PortTypeImpl();
    portType.setQName(new QName("pt"));
    portType.addOperation(operation);
    // process
    process.getImports().addPortType(portType);
    // receiver
    receiver.setOperation(operation);

    // save objects and load them back
    process = saveAndReload(process); 
    receiver = getReceiver(process);
    
    // verify the retrieved objects
    assertEquals("o", receiver.getOperation().getName());
  }
  
  public void testPartnerLink() {
    // prepare persistent objects
    // partner link
    PartnerLinkDefinition partnerLink = new PartnerLinkDefinition();
    partnerLink.setName("pl");
    // process
    process.getGlobalScope().addPartnerLink(partnerLink);
    // receiver
    receiver.setPartnerLink(partnerLink);

    // save objects and load them back
    process = saveAndReload(process); 
    receiver = getReceiver(process);
    
    // verify the retrieved objects
    assertEquals("pl", receiver.getPartnerLink().getName());
  }
  
  public void testVariable() {
    // prepare persistent objects
    // variable
    VariableDefinition variable = new VariableDefinition();
    variable.setName("v");
    // process
    process.getGlobalScope().addVariable(variable);
    // receiver
    receiver.setVariable(variable);
    
    // save objects and load them back
    process = saveAndReload(process); 
    receiver = getReceiver(process);

    // verify the retrieved objects
    assertEquals("v", receiver.getVariable().getName());
  }
  
  public void testInboundMessageListener() {
    // save objects and load them back
    process = saveAndReload(process); 
    receiver = getReceiver(process);
    
    // verify the retrieved objects
    assertSame(process.getNode(RECEIVE_NAME), receiver.getInboundMessageActivity());
  }
  
  public void testMessageExchange() {
    // prepare persistent objects
    receiver.setMessageExchange("msgExchng");
    
    // save objects and load them back
    process = saveAndReload(process); 
    receiver = getReceiver(process);
    
    // verify the retrieved objects
    assertEquals("msgExchng", receiver.getMessageExchange() );
  }
  
  private static Receiver getReceiver(BpelDefinition process) {
    Receive receive  = (Receive) process.getNode(RECEIVE_NAME);
    return receive.getReceiver();
  }
}
