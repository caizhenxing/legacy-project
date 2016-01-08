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
package org.jbpm.bpel.def.assign;

import java.util.Collections;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.Namespace;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.variable.def.ElementType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class FromExpressionTest extends TestCase {

  private FromExpression from = new FromExpression();
  private Token token;
  
  private static final String ELEM1_TEXT = 
    "<a>" +
    "  <b on=\"true\">true</b>" +
    "  <c name=\"venus\"/>" +
    "  <d amount=\"20\"/>" +
    "  <e>30</e>" +
    "</a>";
  private static final String ELEM2_TEXT =
    "<a>" +
    "  <b on=\"\"/>" +
    "  <c name=\"mars\"/>" +
    "  <d amount=\"30\"/>" +
    "  <e>40</e>" +
    "</a>";
  
  public FromExpressionTest(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    // process and token
    BpelDefinition pd = new BpelDefinition();
    token = new ProcessInstance(pd).getRootToken();
    Scope scope = pd.getGlobalScope();
    // element variable 1
    Element elem1 = XmlUtil.parseElement(ELEM1_TEXT);
    VariableDefinition var1 = new VariableDefinition();
    var1.setName("var1");
    var1.setType(new ElementType(new QName(elem1.getNamespaceURI(), elem1.getLocalName())));
    scope.addVariable(var1);
    // element variable 2
    Element elem2 = XmlUtil.parseElement(ELEM2_TEXT);
    VariableDefinition var2 = new VariableDefinition();
    var2.setName("var2");
    var2.setType(new ElementType(new QName(elem2.getNamespaceURI(), elem2.getLocalName())));
    scope.addVariable(var2);
    // initialize variables
    scope.initScopeData(token);
    var1.setValue(token, elem1);
    var2.setValue(token, elem2);
    // provide a way to the variable definition
    Empty activity = new Empty();
    scope.addNode(activity);
    token.setNode(activity);
  }

  public void testExtract() {
    // expression
    Expression expr = new Expression();
    expr.setText("concat(bpws:getVariableData('var1')/c/@name, 'and', bpws:getVariableData('var2')/c/@name)");
    expr.setNamespaces(Collections.singleton(new Namespace("bpws", BpelConstants.NS_BPWS_1_1)));
    // from
    from.setExpression(expr);
    // verify extraction
    assertEquals("venusandmars", from.extract(token));
  }
}
