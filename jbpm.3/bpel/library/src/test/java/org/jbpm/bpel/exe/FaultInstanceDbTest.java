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

import javax.wsdl.Message;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.impl.MessageImpl;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.ProcessInstance;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class FaultInstanceDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition = new BpelDefinition("definition");
  
  public void testName() {
    // save process definition
    graphSession.saveProcessDefinition(processDefinition);
    // create process instance
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    // create fault data
    QName faultName = new QName(BpelConstants.NS_EXAMPLES, "flt");
    // set global scope fault
    processDefinition.getGlobalScope()
    .createInstance(processInstance.getRootToken())
    .setFaultInstance(new FaultInstance(faultName));
    
    processInstance = saveAndReload(processInstance);

    ScopeInstance scope = Scope.getInstance(processInstance.getRootToken());
    assertEquals(faultName, scope.getFaultInstance().getName());
  } 
  
  public void testMessageData() {
    // create message definition
    QName messageName = new QName(BpelConstants.NS_EXAMPLES, "msg"); 
    Message message = new MessageImpl();
    message.setQName(messageName);
    // register message in the imports module
    ImportsDefinition imports = processDefinition.getImports(); 
    imports.addMessage(message);
    // retrieve message type
    MessageType type = imports.getMessageType(messageName); 
    // save process definition
    graphSession.saveProcessDefinition(processDefinition);
    // create process instance
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    // set global scope fault
    processDefinition.getGlobalScope()
    .createInstance(processInstance.getRootToken())
    .setFaultInstance(new FaultInstance(null, new MessageValue(type)));
    
    processInstance = saveAndReload(processInstance);

    type = Scope.getInstance(processInstance.getRootToken())
    .getFaultInstance()
    .getMessageValue()
    .getType();
    assertEquals(messageName, type.getName());
  } 
  
  public void testElementData() {
    // save process definition
    graphSession.saveProcessDefinition(processDefinition);
    // create process instance
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    // element value
    Element elementData = XmlUtil.createElement(BpelConstants.NS_EXAMPLES, "elm");
    // set scope fault
    FaultInstance faultInstance = new FaultInstance(null, elementData);
    processDefinition.getGlobalScope()
    .createInstance(processInstance.getRootToken())
    .setFaultInstance(faultInstance);
    
    processInstance = saveAndReload(processInstance);

    elementData = Scope.getInstance(processInstance.getRootToken())
    .getFaultInstance()
    .getElementValue();
    assertEquals(BpelConstants.NS_EXAMPLES, elementData.getNamespaceURI());
    assertEquals("elm", elementData.getLocalName());
  } 
}