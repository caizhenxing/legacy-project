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

import javax.wsdl.Message;
import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.wsdl.impl.MessageImpl;
import org.jbpm.bpel.wsdl.impl.PropertyAliasImpl;
import org.jbpm.bpel.wsdl.impl.PropertyImpl;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class XmlTypeDbTest extends AbstractDbTestCase {

  BpelDefinition process;
  SchemaType type;
  
  static final QName TYPE_NAME = new QName(BpelConstants.NS_EXAMPLES, "st");
  
  public void setUp() throws Exception {
    super.setUp();
    // process, create after opening jbpm context
    process = new BpelDefinition();
    // variable type
    type = process.getImports().getSchemaType(TYPE_NAME);
  }

  public void testName() {
    // save objects and load them back
    process = saveAndReload(process);
    type = process.getImports().getSchemaType(TYPE_NAME);
    
    // verify retrieved objects
    assertEquals(TYPE_NAME, type.getName());
  }

  public void testPropertyAliases() {
    // prepare persistent objects
    // property
    final QName propertyName = new QName(BpelConstants.NS_EXAMPLES, "p");
    Property property = new PropertyImpl();
    property.setQName(propertyName);
    process.getImports().addProperty(property);
    // alias
    PropertyAlias alias = new PropertyAliasImpl();
    alias.setProperty(property);
    alias.setType(TYPE_NAME);
    type.addPropertyAlias(alias);

    // save objects and load them back
    process = saveAndReload(process);
    type = process.getImports().getSchemaType(TYPE_NAME);
    
    // verify retrieved objects
    assertEquals(TYPE_NAME, type.getPropertyAlias(propertyName).getType());
  }
  
  public void testCreateProcessInstance() {
    VariableDefinition schemaVar = new VariableDefinition();
    schemaVar.setName("sv");
    schemaVar.setType(type);
    Scope globalScope = process.getGlobalScope();
    globalScope.addVariable(schemaVar);
    
    QName messageName = new QName("md");
    Message messageDef = new MessageImpl();
    messageDef.setQName(messageName);
    ImportsDefinition imports = process.getImports();
    imports.addMessage(messageDef);
    
    VariableDefinition messageVar = new VariableDefinition();
    messageVar.setName("mv");
    messageVar.setType(imports.getMessageType(messageName));
    globalScope.addVariable(messageVar);
    
    // save objects and load them back
    process = saveAndReload(process);
    
    // verify retrieved object
    process.createProcessInstance();
  }
}