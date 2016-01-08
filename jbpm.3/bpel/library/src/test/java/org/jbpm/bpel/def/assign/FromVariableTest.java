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

import java.util.HashSet;

import javax.wsdl.Definition;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.Namespace;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class FromVariableTest extends TestCase {
  
  private Token token;
  private MessageValue messageValue;
  private FromVariable from = new FromVariable();
  
  private VariableDefinition simpleVariable;
  private VariableDefinition messageVariable;
  
  private static final String WSDL_TEXT = 
    "<definitions targetNamespace='http://jbpm.org/bpel/examples'" +
    " xmlns:tns='http://jbpm.org/bpel/examples'" +
    " xmlns:xsd='http://www.w3.org/2001/XMLSchema'" +
    " xmlns='http://schemas.xmlsoap.org/wsdl/'>" +
    "  <message name='request'>" +
    "    <part name='simplePart' type='xsd:string'/>" +
    "    <part name='elementPart' element='tns:surpriseElement'/>" +
    "  </message>" +
    "</definitions>";
  private static final String ELEM_PART_TEXT = 
    "<tns:surpriseElement xmlns:tns='http://jbpm.org/bpel/examples'>" +
    "  <b on=\"true\">true</b>" +
    "  <c name=\"venus\"/>" +
    "  <d amount=\"20\"/>" +
    "  <e>30</e>" +
    "</tns:surpriseElement>";
  private static final String SIMPLE_TEXT = "sakar";

  public FromVariableTest(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    // process and token
    BpelDefinition pd = new BpelDefinition();
    token = new ProcessInstance(pd).getRootToken();
    Scope scope = pd.getGlobalScope();
    // wsdl description
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    ImportsDefinition imports = pd.getImports();
    imports.addImport(WsdlUtil.createImport(def));
    // message variable
    messageVariable = new VariableDefinition();
    messageVariable.setName("msg");
    messageVariable.setType(imports.getMessageType(new QName(BpelConstants.NS_EXAMPLES, "request")));
    scope.addVariable(messageVariable);
    // simple variable
    simpleVariable = new VariableDefinition();
    simpleVariable.setName("smp");
    simpleVariable.setType(new SchemaType(new QName(BpelConstants.NS_XML_SCHEMA, "string")));
    scope.addVariable(simpleVariable);
    // initialize variables
    scope.initScopeData(token);
    simpleVariable.setValue(token, SIMPLE_TEXT);
    messageValue = (MessageValue) messageVariable.getValueForAssign(token);
    // provide a way to the variable definition
    Empty activity = new Empty();
    scope.addNode(activity);
    token.setNode(activity);      
  }
  
  public void testExtractVariable_simple() throws Exception {
    // variable
    from.setVariable(simpleVariable);
    // assertion
    Element extractedValue = (Element) from.extract(token);
    assertEquals(SIMPLE_TEXT, XmlUtil.getStringValue(extractedValue));
  }
  
  public void testExtractVariable_message() throws Exception {
    // from
    from.setVariable(messageVariable);
    // instance data
    Element partValue = XmlUtil.parseElement(ELEM_PART_TEXT);
    messageValue.setPart("elementPart", partValue);    
    // assertion
    MessageValue extractedValue = (MessageValue) from.extract(token);
    assertSame(messageValue, extractedValue);
  }
  
  public void testExtractPart() throws Exception {
    // from
    from.setVariable(messageVariable);
    from.setPart("elementPart");
    // instance data
    Element partValue = XmlUtil.parseElement(ELEM_PART_TEXT);
    messageValue.setPart("elementPart", partValue);
    Element elementPart = messageValue.getPart("elementPart");
    // assertion
    assertSame(elementPart, from.extract(token));
  }
  
  public void testExtractQuery() throws Exception {
    // enclosing element
    HashSet namespaces = new HashSet();
    namespaces.add(new Namespace("bpws", BpelConstants.NS_BPWS));
    namespaces.add(new Namespace("tns", BpelConstants.NS_EXAMPLES));
    // query
    Query query = new Query();
    query.setText("c/@name");
    query.setNamespaces(namespaces);
    // from
    from.setVariable(messageVariable);
    from.setPart("elementPart");
    from.setQuery(query);
    // instance data
    Element partValue = XmlUtil.parseElement(ELEM_PART_TEXT);
    messageValue.setPart("elementPart", partValue);
    // assertion
    assertEquals("venus", XmlUtil.getStringValue((Attr) from.extract(token)));
  }
}
