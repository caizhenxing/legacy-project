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
package org.jbpm.bpel.wsdl.impl;

import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.Output;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class OperationImplDbTest extends AbstractDbTestCase {
  
  BpelDefinition process;
  Message message;
  Operation operation;
  
  static final QName MESSAGE_NAME = new QName("msg");
  static final QName PORT_TYPE_NAME = new QName("pt");
  static final String OPERATION_NAME = "op";
  
  public void setUp() throws Exception {
    super.setUp();
    // message
    message = new MessageImpl();
    message.setQName(MESSAGE_NAME);
    // operation
    operation = new OperationImpl();
    operation.setName(OPERATION_NAME);
    // port type
    PortType portType = new PortTypeImpl();
    portType.setQName(PORT_TYPE_NAME);
    portType.addOperation(operation);
    // process 
    process = new BpelDefinition();
    ImportsDefinition imports = process.getImports();
    imports.addMessage(message);
    imports.addPortType(portType);
  }

  public void testName() {
    // save the objects and load them back
    process = saveAndReload(process); 
    operation = getOperation(process);
    // verify the retrieved objects
    assertEquals(OPERATION_NAME, operation.getName());
  }

  public void testInput() {
    // set up the persistent objects
    Input input = new InputImpl();
    input.setName("userSession");
    input.setMessage(message);
    operation.setInput(input);
    // save the objects and load them back
    process = saveAndReload(process); 
    operation = getOperation(process);
    input = operation.getInput();
    // verify the retrieved objects
    assertEquals("userSession", input.getName());
    assertEquals(MESSAGE_NAME, input.getMessage().getQName());    
  }
  
  public void testOutput() {
    // set up the persistent objects
    Output output = new OutputImpl();
    output.setName("balance");
    output.setMessage(message);
    operation.setOutput(output);
    // save the objects and load them back
    process = saveAndReload(process); 
    operation = getOperation(process);
    output = operation.getOutput();
    // verify the retrieved objects
    assertEquals("balance", output.getName());
    assertEquals(MESSAGE_NAME, output.getMessage().getQName());
  }
  
  public void testFault() {
    // set up the persistent objects
    Fault fault = new FaultImpl();
    fault.setName("errorNumber");
    fault.setMessage(message);
    operation.addFault(fault);
    // save the objects and load them back
    process = saveAndReload(process); 
    operation = getOperation(process);
    fault = operation.getFault("errorNumber");
    // verify the retrieved objects
    assertEquals("errorNumber", fault.getName());
    assertEquals(MESSAGE_NAME, fault.getMessage().getQName());    
  }
  
  public void testStyle() {
    // set up the persistent objects
    operation.setStyle(OperationType.REQUEST_RESPONSE);
    // save the objects and load them back
    process = saveAndReload(process); 
    operation = getOperation(process);
    // verify the retrieved objects
    assertEquals(OperationType.REQUEST_RESPONSE, operation.getStyle());
  }
  
  static Operation getOperation(BpelDefinition process) {
    return process.getImports().getPortType(PORT_TYPE_NAME).getOperation(OPERATION_NAME, null, null);
  }
}
