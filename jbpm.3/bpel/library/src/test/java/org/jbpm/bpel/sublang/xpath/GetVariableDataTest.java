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
package org.jbpm.bpel.sublang.xpath;

import java.util.Map;

import javax.wsdl.Definition;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.FunctionCallException;
import org.jaxen.SimpleNamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.sublang.xpath.GetVariableDataFunction;
import org.jbpm.bpel.sublang.xpath.ScopeVariableContext;
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
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class GetVariableDataTest extends TestCase {

  private MessageValue messageValue;
  private Context context;
  
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
  
  public GetVariableDataTest(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    // process and token
    BpelDefinition pd = new BpelDefinition();
    Token token = new ProcessInstance(pd).getRootToken();
    Scope scope = pd.getGlobalScope();
    // wsdl description
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    ImportsDefinition imports = pd.getImports();
    imports.addImport(WsdlUtil.createImport(def));
    // variable definition
    VariableDefinition messageVariable = new VariableDefinition();
    messageVariable.setName("msg");
    messageVariable.setType(imports.getMessageType(new QName(BpelConstants.NS_EXAMPLES, "request")));
    scope.addVariable(messageVariable);
    // simple variable
    VariableDefinition simpleVariable = new VariableDefinition();
    simpleVariable.setName("simple");
    simpleVariable.setType(new SchemaType(new QName(BpelConstants.NS_XML_SCHEMA, "boolean"))); 
    scope.addVariable(simpleVariable);    
    // initialize variables
    scope.initScopeData(token);
    simpleVariable.setValue(token, Boolean.TRUE);
    messageValue = (MessageValue) messageVariable.getValueForAssign(token);
    // provide a way to the variable definition
    Empty activity = new Empty();
    scope.addNode(activity);
    token.setNode(activity);    
    // namespace declarations
    Map namespaceDeclarations = def.getNamespaces();
    namespaceDeclarations.remove("");
    // jaxen context
    ContextSupport sup = new ContextSupport();
    sup.setVariableContext(new ScopeVariableContext(token));
    sup.setNamespaceContext(new SimpleNamespaceContext(namespaceDeclarations));
    context = new Context(sup);
  }

  public void testEvaluateVariable() throws FunctionCallException {
    Element simpleValue = (Element) GetVariableDataFunction.evaluate("simple", context);
    assertEquals("true", XmlUtil.getStringValue(simpleValue));
  }

  public void testEvaluateSimplePart() throws FunctionCallException {
    Element simplePart = messageValue.getPartForAssign("simplePart");
    assertSame(simplePart, GetVariableDataFunction.evaluate("msg", "simplePart", context));
  }
  
  public void testEvaluateElementPart() throws FunctionCallException {
    Element elementPart = messageValue.getPartForAssign("elementPart");
    assertSame(elementPart, GetVariableDataFunction.evaluate("msg", "elementPart", context));
  }

  public void testEvaluateAttributeLocation() throws Exception {
    Element partValue = XmlUtil.parseElement(ELEM_PART_TEXT);
    messageValue.setPart("elementPart", partValue);
    Element c = XmlUtil.getElement(messageValue.getPart("elementPart"), "c");
    Attr nameAttr = c.getAttributeNode("name");
    Object data = GetVariableDataFunction.evaluate("msg", "elementPart", "/tns:surpriseElement/c/@name", context); 
    assertSame(nameAttr, data);
  }
  
  public void testEvaluateElementLocation() throws Exception {
    Element partValue = XmlUtil.parseElement(ELEM_PART_TEXT);
    messageValue.setPart("elementPart", partValue);
    Element e = XmlUtil.getElement(messageValue.getPart("elementPart"), "e");
    Object data = GetVariableDataFunction.evaluate("msg", "elementPart", "/tns:surpriseElement/e", context);
    assertSame(e, data);
  }
}
