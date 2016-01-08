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
import org.jbpm.bpel.def.Reply;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Replier;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.impl.OperationImpl;
import org.jbpm.bpel.wsdl.impl.PortTypeImpl;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class ReplierDbTest extends AbstractDbTestCase {
  
  BpelDefinition process;
  Replier replier = new Replier();
  
  public void setUp() throws Exception {
    super.setUp();
    // activity
    Reply reply = new Reply("reply");
    reply.setReplier(replier);
    // process, create after opening jbpm context
    process = new BpelDefinition();
    process.addNode(reply);
  }
  
  public void testCorrelations() {
    // prepare persistent objects
    replier.setCorrelations(new Correlations());
    
    // save objects and load them back
    process = saveAndReload(process); 
    replier = getReplier(process);
    
    // verify retrieved objects
    assertNotNull( replier.getCorrelations() );
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
    // replier
    replier.setOperation(operation);
    
    // save objects and load them back
    process = saveAndReload(process); 
    replier = getReplier(process);
    
    // verify the retrieved objects
    assertEquals("o", replier.getOperation().getName());
  }
  
  public void testFaultName() {
    // prepare persistent objects
    final QName FAULT_NAME = new QName("calamityFault");  
    replier.setFaultName(FAULT_NAME);
    
    // prepare persistent objects
    process = saveAndReload(process); 
    replier = getReplier(process);
    
    // verify the retrieved objects
    assertEquals(FAULT_NAME, replier.getFaultName());
  }
  
  public void testPartnerLink() {   
    // prepare persistent objects
    // partner link
    PartnerLinkDefinition partnerLink = new PartnerLinkDefinition();
    partnerLink.setName("pl");
    // process
    process.getGlobalScope().addPartnerLink(partnerLink);
    // replier
    replier.setPartnerLink(partnerLink);
    
    // save objects and load them back
    process = saveAndReload(process); 
    replier = getReplier(process);
    
    // verify the retrieved objects
    assertEquals("pl", replier.getPartnerLink().getName());

  }
  
  public void testVariable() {   
    // prepare persistent objects
    // variable
    VariableDefinition variable = new VariableDefinition();
    variable.setName("v");
    // process
    process.getGlobalScope().addVariable(variable);
    // replier
    replier.setVariable(variable);
    
    // save objects and load them back
    process = saveAndReload(process); 
    replier = getReplier(process); 

    // verify the retrieved objects
    assertEquals("v", replier.getVariable().getName());
  }
  
  public void testMessageExchange() {
    // prepare persistent objects
    replier.setMessageExchange("msgExchng");
    
    // save objects and load them back
    process = saveAndReload(process); 
    replier = getReplier(process);
    
    // verify the retrieved objects
    assertEquals("msgExchng", replier.getMessageExchange() );
  }
  
  private static Replier getReplier(BpelDefinition process) {
    Reply reply  = (Reply) process.getNode("reply");
    return reply.getReplier();
  }
}
