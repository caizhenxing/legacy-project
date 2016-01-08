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
import javax.wsdl.Part;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import junit.framework.TestCase;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.exe.FaultInstance;
import org.jbpm.bpel.variable.def.ElementType;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.XmlUtil;

import com.ibm.wsdl.MessageImpl;
import com.ibm.wsdl.PartImpl;

/**
 * @author Juan Cantu
 * @version $Revision: 1.11 $ $Date: 2006/08/23 08:00:34 $
 */
public class ScopeDefTest extends TestCase {

  Scope scope;
  BpelFaultException fault;
  Catch catchName = new Catch();
  Catch catchMessage = new Catch();
  Catch catchNameMessage = new Catch();
  Catch catchElement = new Catch();
  Catch catchNameElement = new Catch();
  ScopeHandler catchAll = new ScopeHandler();

  static final QName NON_MATCHING_NAME = new QName(BpelConstants.NS_EXAMPLES, "unexpected");
  
  public void setUp() {
    BpelDefinition processDefinition = new BpelDefinition();
    ImportsDefinition imports = processDefinition.getImports();
    
    scope = new Scope();
    scope.installFaultExceptionHandler();
    processDefinition.getGlobalScope().setRoot(scope);
    
    QName faultName = new QName(BpelConstants.NS_EXAMPLES, "aFault");
    
    catchName.setFaultName(faultName);
    scope.addCatch(catchName);
     
    QName elementName = new QName(BpelConstants.NS_EXAMPLES, "anElement");
    
    VariableDefinition elementVariable = new VariableDefinition();
    elementVariable.setName("anElementVariable");
    elementVariable.setType(imports.getElementType(elementName));
    
    catchElement.setFaultVariable(elementVariable);
    scope.addCatch(catchElement);
    
    /* handlers with faultName have priority over handlers without faultName in the case of a fault
     * with a matching faultName; add handler with faultName later to verify this */
    catchNameElement.setFaultName(faultName);
    catchNameElement.setFaultVariable(elementVariable);
    scope.addCatch(catchNameElement);
    
    /* handlers with a faultVariable of message type have priority over handlers with a faultVariable
     * of type element in the case of a fault with a matching message that contains a single part
     * defined by a matching element; add handlers with a message type later to verify this */ 
    Part part = new PartImpl();
    part.setName("aPart");
    part.setElementName(elementName);

    QName messageName = new QName(BpelConstants.NS_EXAMPLES, "aMessage");
    Message message = new MessageImpl();
    message.setQName(messageName);
    message.addPart(part);
    imports.addMessage(message);
    
    VariableDefinition messageVariable = new VariableDefinition();
    messageVariable.setName("aMessageVariable");
    messageVariable.setType(imports.getMessageType(messageName));

    catchMessage.setFaultVariable(messageVariable);
    scope.addCatch(catchMessage);
    
    /* handlers with faultName have priority over handlers without faultName in the case of a fault
     * with a matching faultName; add handler with faultName later to verify this */
    catchNameMessage.setFaultName(faultName);
    catchNameMessage.setFaultVariable(messageVariable);
    scope.addCatch(catchNameMessage);
  }
  
  /* In the case of faults thrown with no associated data: */
  
  /* If there is a catch activity with a matching faultName value that does not specify a faultVariable 
   * attribute then the fault is passed to the identified catch activity*/
  public void test_faultNoData_catchName() {
    assertSame( catchName, scope.getFaultHandler(new FaultInstance(catchName.getFaultName())) );
  }
  
  /* Otherwise if there is a catchAll handler then the fault is passed to the catchAll handler*/
  public void test_faultNoData_catchAll() {
    scope.setHandler(Scope.CATCH_ALL, catchAll);
    assertSame( catchAll, scope.getFaultHandler(new FaultInstance(NON_MATCHING_NAME)) );
  }
  
  /* Otherwise the fault is thrown to the immediately enclosing scope*/
  public void test_faultNoData_noCatch() {
    assertNull( scope.getFaultHandler(new FaultInstance(NON_MATCHING_NAME)) );
  }
  
  /* In the case of faults thrown with associated data: */

  /* If there is a catch activity with a matching faultName value that has a faultVariable or 
   * faultMessageType whose type matches the type of the fault data then the fault is passed to the
   * identified catch activity */
  public void test_faultMessage_catchNameMessage() {
    VariableDefinition variable = catchNameMessage.getFaultVariable();
    FaultInstance faultInstance = new FaultInstance(catchNameMessage.getFaultName(),
        (MessageValue) variable.getType().createValue(variable));
    assertSame( catchNameMessage, scope.getFaultHandler(faultInstance) );
  }
  
  public void test_faultElement_catchNameElement() {
    VariableDefinition variable = catchNameElement.getFaultVariable();
    FaultInstance faultInstance = new FaultInstance(catchNameElement.getFaultName(), 
        (Element) variable.getType().createValue(variable));
    assertSame(catchNameElement, scope.getFaultHandler(faultInstance));
  }
  
  /* Otherwise if the fault data is a WSDL message type where the message contains a single part defined 
   * by an element and there exists a catch activity with a matching faultName value that has a 
   * faultVariable whose type matches the type of the element used to define the part then the fault 
   * is passed to the identified catch activity */
  public void test_faultMessage_catchNameElement() {
    Part part = new PartImpl();
    part.setName("aPart");
    part.setElementName(catchNameElement.getFaultVariable().getType().getName());

    Message message = new MessageImpl();
    message.setQName(NON_MATCHING_NAME);
    message.addPart(part);
    
    MessageType messageType = new MessageType(message);

    FaultInstance faultInstance = new FaultInstance(catchNameMessage.getFaultName(), new MessageValue(messageType));
    assertSame( catchNameElement, scope.getFaultHandler(faultInstance) );
  }
  
  /* If there is a catch activity with a matching faultName value that does not specify a faultVariable 
   * or faultMessageType value then the fault is passed to the identified catch activity */
  public void test_faultMessage_catchName() {
    FaultInstance faultInstance = new FaultInstance(catchName.getFaultName(), XmlUtil.createElement(NON_MATCHING_NAME));
    assertSame(catchName, scope.getFaultHandler(faultInstance));
  }
  
  /* Otherwise if there is a catch activity without a faultName attribute that has a faultVariable or 
   * faultMessageType whose type matches the type of the fault data then the fault is passed to the
   * identified catch activity */
  public void test_faultMessage_catchMessage() {
    MessageType type = (MessageType) catchMessage.getFaultVariable().getType();
    FaultInstance faultInstance = new FaultInstance(NON_MATCHING_NAME, new MessageValue(type));
    assertSame(catchMessage, scope.getFaultHandler(faultInstance));
  }
  
  public void test_faultElement_catchElement() {
    ElementType type = (ElementType) catchElement.getFaultVariable().getType();
    FaultInstance faultInstance = new FaultInstance(NON_MATCHING_NAME, XmlUtil.createElement(type.getName()));
    assertSame(catchElement, scope.getFaultHandler(faultInstance));
  }
  
  /* Otherwise if the fault data is a WSDL message type where the message contains a single part defined 
   * by an element and there exists a catch activity without a faultName attribute that has a 
   * faultVariable whose type matches the type of the element used to define the part then the fault is 
   * passed to the identified catch activity */
  public void test_faultMessage_catchElement() {
    // element part
    Part part = new PartImpl();
    part.setName("aPart");
    part.setElementName(catchElement.getFaultVariable().getType().getName());
    // message
    Message message = new MessageImpl();
    message.setQName(NON_MATCHING_NAME);
    message.addPart(part);
    // type
    ImportsDefinition imports = scope.getBpelDefinition().getImports();
    imports.addMessage(message);
    MessageType type = imports.getMessageType(NON_MATCHING_NAME);
    // fault
    FaultInstance faultInstance = new FaultInstance(NON_MATCHING_NAME, new MessageValue(type));
    assertSame( catchElement, scope.getFaultHandler(faultInstance) );
  }
  
  /* Otherwise if there is a catchAll handler then the fault is passed to the catchAll handler */
  public void test_faultMessage_catchAll() {
    // message
    Message message = new MessageImpl();
    message.setQName(NON_MATCHING_NAME);
    // type
    MessageType type = new MessageType(message);
    // fault
    FaultInstance faultInstance = new FaultInstance(NON_MATCHING_NAME, new MessageValue(type));
    scope.setHandler(Scope.CATCH_ALL, catchAll);
    assertSame( catchAll, scope.getFaultHandler(faultInstance) );
  }
  
  public void test_faultElement_catchAll() {
    FaultInstance faultInstance = new FaultInstance(NON_MATCHING_NAME, XmlUtil.createElement(NON_MATCHING_NAME));
    scope.setHandler(Scope.CATCH_ALL, catchAll);
    assertSame(catchAll, scope.getFaultHandler(faultInstance));
  }
  
  /* Otherwise, the fault will be handled by the default fault handler */
  public void test_faultMessage_noCatch() {
    // message
    Message message = new MessageImpl();
    message.setQName(NON_MATCHING_NAME);
    // type
    MessageType type = new MessageType(message);
    // fault
    FaultInstance faultInstance = new FaultInstance(NON_MATCHING_NAME, new MessageValue(type));
    assertNull( scope.getFaultHandler(faultInstance) );
  }
  
  public void test_faultElement_noCatch() {
    FaultInstance faultInstance = new FaultInstance(NON_MATCHING_NAME, XmlUtil.createElement(NON_MATCHING_NAME));
    assertNull(scope.getFaultHandler(faultInstance));
  }
}