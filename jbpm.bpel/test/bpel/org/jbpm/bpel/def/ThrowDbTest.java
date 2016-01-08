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

import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.variable.def.VariableDefinition;

/**
 * @author Juan Cantu
 * @version $Revision: 1.8 $ $Date: 2006/08/22 04:13:10 $
 */
public class ThrowDbTest extends AbstractDbTestCase {
  
  public void testFaultVariable() {
    BpelDefinition processDefinition = new BpelDefinition();
    VariableDefinition var = new VariableDefinition();
    var.setName("v");
    //the schema type is created when queried for the first time
    SchemaType type = processDefinition.getImports().getSchemaType(new QName("aType"));
    var.setType(type);
    processDefinition.getGlobalScope().addVariable(var);
    
    Throw throwActivity = new Throw("child");
    throwActivity.setFaultVariable(var);
    processDefinition.addNode(throwActivity);
    
    processDefinition = saveAndReload(processDefinition);

    throwActivity = (Throw) processDefinition.getNode("child");
    assertEquals( processDefinition.getGlobalScope().getVariable("v"), throwActivity.getFaultVariable() );
  }
  
  public void testFaultName() {
    BpelDefinition processDefinition = new BpelDefinition();
    Throw throwActivity = new Throw("child");
    throwActivity.setFaultName(new QName(null, "catastrophicException"));
    processDefinition.addNode(throwActivity);
    
    processDefinition = saveAndReload(processDefinition);

    throwActivity = (Throw) processDefinition.getNode("child");
    assertNotNull( throwActivity.getFaultName() );
  }
}
