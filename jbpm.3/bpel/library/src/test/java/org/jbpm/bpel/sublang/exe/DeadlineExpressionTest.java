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
package org.jbpm.bpel.sublang.exe;

import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.variable.def.ElementType;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class DeadlineExpressionTest extends TestCase {
  
  private Token token;

  private static final String ELEM_TEXT = 
    "<a>" +
    "  <b date=\"1981-03-19+06:00\"/>" +
    "  <c>2002-10-10T14:35:10.567Z</c>" +
    "</a>";
  
  public DeadlineExpressionTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    // process and token
    BpelDefinition pd = new BpelDefinition();
    token = new ProcessInstance(pd).getRootToken();
    Scope scope = pd.getGlobalScope();
    // element variable
    Element elem1 = XmlUtil.parseElement(ELEM_TEXT);
    VariableDefinition var1 = new VariableDefinition();
    var1.setName("var1");
    var1.setType(new ElementType(new QName(elem1.getNamespaceURI(), elem1.getLocalName())));
    scope.addVariable(var1);
    // simple variable
    VariableDefinition simple = new VariableDefinition();
    simple.setName("simple");
    simple.setType(new SchemaType(new QName(BpelConstants.NS_XML_SCHEMA, "dateTime")));
    scope.addVariable(simple);
    // initialize variables
    scope.initScopeData(token);
    var1.setValue(token, elem1);
    simple.setValue(token, "1999-12-31T23:59:45");
    // provide a way to the variable definition
    Empty activity = new Empty();
    scope.addNode(activity);
    token.setNode(activity);
  }
  
  public void testSimpleExtraction() {
    // expected value
    Calendar calendar = Calendar.getInstance();
    calendar.setLenient(false);
    calendar.clear();
    calendar.set(1999, Calendar.DECEMBER, 31, 23, 59, 45);
    // assertion 
    assertEquals(calendar, eval("$simple"));
  }
  
  public void testAttributeExtraction() {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+6"));
    calendar.setLenient(false);
    calendar.clear();
    calendar.set(1981, Calendar.MARCH, 19);
    assertEquals(calendar, eval("$var1/b/@date"));
  }
  
  public void testContentExtraction() {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+0")); 
    calendar.setLenient(false);
    calendar.set(2002, Calendar.OCTOBER, 10, 14, 35, 10);
    calendar.set(Calendar.MILLISECOND, 567);
    assertEquals(calendar, eval("$var1/c"));
  }
  
  private Calendar eval(String text) {
    Expression expr = new Expression();
    expr.setText(text);
    return DatatypeUtil.toDateTime(expr.getEvaluator().evaluate(token));
  }
}