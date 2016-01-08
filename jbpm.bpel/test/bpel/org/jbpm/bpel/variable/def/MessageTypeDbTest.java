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
package org.jbpm.bpel.variable.def;

import javax.wsdl.Definition;
import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelReader;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class MessageTypeDbTest extends AbstractDbTestCase {
  
  BpelDefinition process;
  
  private static final String WSDL_TEXT = 
    "<definitions targetNamespace='http://jbpm.org/bpel/examples'" +
    " xmlns:tns='http://jbpm.org/bpel/examples'" +
    " xmlns:xsd='http://www.w3.org/2001/XMLSchema'" +
    " xmlns:bpws='http://schemas.xmlsoap.org/ws/2004/03/business-process/'" +
    " xmlns='http://schemas.xmlsoap.org/wsdl/'>" +
    "  <message name='request'>" +
    "    <part name='simplePart' type='xsd:string'/>" +
    "    <part name='elementPart' element='tns:surpriseElement'/>" +
    "  </message>" +
    "  <bpws:property name='nameProperty' type='xsd:string'/>" +
    "  <bpws:property name='idProperty' type='xsd:int'/>" +
    "  <bpws:propertyAlias propertyName='tns:nameProperty' messageType='tns:request' part='elementPart'>" +
    "    <bpws:query>c/@name</bpws:query>" +
    "  </bpws:propertyAlias>" +
    "  <bpws:propertyAlias propertyName='tns:idProperty' messageType='tns:request' part='elementPart'>" +
    "    <bpws:query>e</bpws:query>" +
    "  </bpws:propertyAlias>" +
    "</definitions>";
  private static final QName MESSAGE_NAME = new QName(BpelConstants.NS_EXAMPLES, "request");
  
  public void setUp() throws Exception {
    super.setUp();
    // process, create after opening jbpm context
    process = new BpelDefinition();
    // read wsdl
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    ImportsDefinition imports = process.getImports();
    imports.addImport(WsdlUtil.createImport(def));
    BpelReader.getInstance().registerPropertyAliases(imports);
  }

  /*
   * Test method for 'org.jbpm.bpel.data.def.MessageType.getName()'
   */
  public void testGetName() {
    // save objects and load them back
    process = saveAndReload(process);
    MessageType type = process.getImports().getMessageType(MESSAGE_NAME);
    
    // verify retrieved object
    assertEquals(MESSAGE_NAME, type.getName());
  }

  /*
   * Test method for 'org.jbpm.bpel.data.def.MessageType.getMessage()'
   */
  public void testGetMessage() {
    // save objects and load them back
    process = saveAndReload(process);
    MessageType type = process.getImports().getMessageType(MESSAGE_NAME);
    
    // verify retrieved object
    assertEquals(2, type.getMessage().getParts().size());
  }

  /*
   * Test method for 'org.jbpm.bpel.data.def.VariableType.getPropertyAliases()'
   */
  public void testGetPropertyAliases() {
    // save objects and load them back
    process = saveAndReload(process);
    MessageType type = process.getImports().getMessageType(MESSAGE_NAME);    
    
    // verify retrieved object
    assertEquals(2, type.getPropertyAliases().size());
  }
}
