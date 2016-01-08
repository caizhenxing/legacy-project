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

import javax.wsdl.Message;
import javax.xml.namespace.QName;

import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.impl.MessageImpl;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class CatchDbTest extends AbstractHandlerDbTest {

  protected ScopeHandler get(BpelDefinition process) {
    return (Catch) process.getGlobalScope().getFaultHandlers().get(0);
  }

  protected ScopeHandler create(BpelDefinition process) {
    Catch faultHandler = new Catch();
    process.getGlobalScope().addCatch(faultHandler);
    return faultHandler;
  }

  public void testFaultName() {
    // prepare persistent objects
    // fault name
    QName faultName = new QName(BpelConstants.NS_EXAMPLES, "fault");
    // handler
    Catch catcher = (Catch) scopeHandler;
    catcher.setFaultName(faultName);
    
    // save objects and load them back
    BpelDefinition process = saveAndReload(scopeHandler.getBpelDefinition());
    catcher = (Catch) get(process);
    
    // verify retrieved objects
    assertEquals(faultName, catcher.getFaultName());
  }
  
  public void testFaultVariable() {
    // prepare persistent objects
    BpelDefinition process = scopeHandler.getBpelDefinition();
    ImportsDefinition imports = process.getImports();
    // message
    QName messageName = new QName(BpelConstants.NS_EXAMPLES, "message");
    Message message = new MessageImpl();
    message.setQName(messageName);
    imports.addMessage(message);
    // fault variable
    String variableName = "var";
    VariableDefinition faultVariable = new VariableDefinition();
    faultVariable.setName(variableName);
    faultVariable.setType(imports.getMessageType(messageName));
    // handler
    Catch catcher = (Catch) scopeHandler;
    catcher.setFaultVariable(faultVariable);
    
    // save objects and load them back
    process = saveAndReload(process);
    catcher = (Catch) get(process);
    faultVariable = catcher.getFaultVariable();
    
    // verify retrieved objects
    assertEquals(variableName, faultVariable.getName());
    assertEquals(messageName, faultVariable.getType().getName());    
  }
}
