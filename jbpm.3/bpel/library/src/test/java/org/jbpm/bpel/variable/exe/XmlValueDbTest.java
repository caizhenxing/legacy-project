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
package org.jbpm.bpel.variable.exe;

import org.w3c.dom.Element;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.def.VariableType;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.graph.exe.ProcessInstance;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class XmlValueDbTest extends AbstractDbTestCase {

  private ProcessInstance processInstance;
  
  private static final String VARIABLE_NAME = "vehicle";
  
  protected XmlValueDbTest() {}

  public void setUp() throws Exception {
    // set up db stuff
    super.setUp();
    // process, create after opening the jbpm context
    BpelDefinition processDefinition = new BpelDefinition();
    // schema type
    VariableType type = getVariableType(processDefinition.getImports());
    // variable
    VariableDefinition variable = new VariableDefinition();
    variable.setName(VARIABLE_NAME);
    variable.setType(type);
    processDefinition.getGlobalScope().addVariable(variable);
    // persist process
    graphSession.saveProcessDefinition(processDefinition);
    // process instance
    processInstance = saveAndReload(processDefinition.createProcessInstance());
  }

  public void testCreateValue() {
    // get the variable placeholder in the newly created process instance
    Element elementValue = (Element) getVariableDefinition().getValueForAssign(processInstance.getRootToken());
    // check the placeholder has the nil mark
    assertEquals("true", elementValue.getAttributeNS(BpelConstants.NS_XML_SCHEMA_INSTANCE, BpelConstants.ATTR_NIL));
  }

  public void testSetValue() {
    // get the variable placeholder in the newly created process instance
    // assign a value to the variable 
    Element variableValue = (Element) getVariableDefinition().getValueForAssign(processInstance.getRootToken());
    update(variableValue);
    // check the value has been updated in memory
    assertUpdate(variableValue);
    // now save the process instance
    processInstance = saveAndReload(processInstance);
    // check the value has been updated in the database
    variableValue = (Element) getVariableDefinition().getValue(processInstance.getRootToken());
    assertUpdate(variableValue);
  }
  
  protected abstract VariableType getVariableType(ImportsDefinition imports);

  protected abstract void update(Element variableValue);

  protected abstract void assertUpdate(Element variableValue);

  private VariableDefinition getVariableDefinition() {
    BpelDefinition processDefinition = (BpelDefinition) processInstance.getProcessDefinition();
    return processDefinition.getGlobalScope().getVariable(VARIABLE_NAME);
  }
}
