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

import javax.xml.namespace.QName;

import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.While;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class WhileExeTest extends AbstractExeTestCase {
  
  VariableDefinition condition;
  While whileBlock;
  Receive receive;
  
  public void setUp() throws Exception {
    super.setUp();
    
    condition = new VariableDefinition();
    condition.setName("condition");
    condition.setType(new SchemaType(new QName(BpelConstants.NS_XML_SCHEMA, "boolean")));
    scope.addVariable(condition);
    
    final String xml = 
      "<while>" +
      " <condition>$condition</condition>" +
      " <receive name='receive' partnerLink='aPartner' operation='o'/>" +    
      "</while>";
    
    whileBlock = (While) readActivity(xml, false);
    receive = (Receive) whileBlock.getNode("receive");
    
    plugInner(whileBlock);
  }
  
  public void testFalseCondition() {
    Token normalPath = prepareInner();
    condition.setValue(normalPath, Boolean.FALSE);
    firstActivity.leave(new ExecutionContext(normalPath));
    
    assertReceiveDisabled(normalPath, receive);
    assertCompleted(normalPath);
  }
  
  public void testTrueCondition() {
    Token normalPath = prepareInner();
    condition.setValue(normalPath, Boolean.TRUE);
    firstActivity.leave(new ExecutionContext(normalPath));
    //execute receive again, test that token returns to the same activity.
    assertReceiveAndAdvance(normalPath, receive, receive);
    //set condition to false and finish cycle
    condition.setValue(normalPath, Boolean.FALSE);
    assertReceiveAndComplete(normalPath, receive);
  }
}
