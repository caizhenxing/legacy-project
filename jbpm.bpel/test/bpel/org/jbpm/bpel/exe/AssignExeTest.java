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

import org.jbpm.bpel.def.Assign;
import org.jbpm.bpel.def.Assign.Copy;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public class AssignExeTest extends AbstractExeTestCase {
  
  private Assign assign;
  private VariableDefinition var = new VariableDefinition();
  private Token normalPath;
  
  static final String ASSIGN_TEXT = 
    "<assign>" +
    " <copy>" +
    "  <from><literal>1</literal></from>" +
    "  <to variable='var' part='p1'/>" +
    " </copy>" +
    " <copy>" +
    "  <from>2</from>" +
    "  <to variable='var' part='p1'/>" +
    " </copy>" +
    "</assign>";
  
  protected void setUp() throws Exception {
    super.setUp();
    
    var.setName("var");
    var.setType(pd.getImports().getMessageType(
        new QName(BpelConstants.NS_EXAMPLES, "msg")));
    scope.addVariable(var);
    
    assign = (Assign) readActivity(ASSIGN_TEXT, false);
    plugInner(assign);
    
    normalPath = prepareInner();
  }

  public void testFromLiteralToVariable() {
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.copyValue(normalPath);
    
    MessageValue value = (MessageValue) var.getValue(normalPath);
    assertEquals("1", XmlUtil.getStringValue(value.getPart("p1")));
  }
  
  public void testFromExpressionToVariable() {
    Copy copy = (Copy) assign.getCopies().get(1);
    copy.copyValue(normalPath);
    
    MessageValue value = (MessageValue) var.getValue(normalPath);
    assertEquals("2", XmlUtil.getStringValue(value.getPart("p1")));
  }
}
