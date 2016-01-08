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

import java.util.HashSet;
import java.util.Set;

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
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class VariableAccessTest extends TestCase {
  
  private Token token;
  private MessageValue messageValue;
  private Set namespaces;
  private Element partValue;
  
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
  private static final String ELEM_PART_TEXT = 
    "<tns:surpriseElement xmlns:tns='http://jbpm.org/bpel/examples'>" +
    "  <b on=\"true\">true</b>" +
    "  <c name=\"venus\"/>" +
    "  <d amount=\"20\"/>" +
    "  <e>30</e>" +
    "</tns:surpriseElement>";
  
  protected void setUp() throws Exception {
    // process and context
    BpelDefinition processDefinition = new BpelDefinition();
    token = new ProcessInstance(processDefinition).getRootToken();
    // wsdl description
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    ImportsDefinition imports = processDefinition.getImports();
    imports.addImport(WsdlUtil.createImport(def));
    BpelReader.getInstance().registerPropertyAliases(imports);
    // namespace declarations
    namespaces = new HashSet();
    namespaces.add(new Namespace("tns", BpelConstants.NS_EXAMPLES));
    // the bpel 1.1 namespace is required to access variables through the function getVariableData
    namespaces.add(new Namespace("bpws11", BpelConstants.NS_BPWS_1_1)); 
    // variable definition
    VariableDefinition messageDefinition = new VariableDefinition();
    messageDefinition.setName("msg");
    messageDefinition.setType(imports.getMessageType(new QName(BpelConstants.NS_EXAMPLES, "request")));
    // declare the variable in the global scope 
    Scope scope = processDefinition.getGlobalScope();
    scope.addVariable(messageDefinition);
    // provide a way to the variable definition
    Empty activity = new Empty();
    scope.addNode(activity);
    token.setNode(activity);
    // variable instance
    messageDefinition.createInstance(token);
    messageValue = (MessageValue) messageDefinition.getValueForAssign(token);
    // initialize the element part
    partValue = XmlUtil.parseElement(ELEM_PART_TEXT);
    messageValue.setPart("elementPart", partValue); 
  }
  
  public void testMessageSimplePart() {
    messageValue.setPart("simplePart", "hola");
    Element simplePart = (Element) eval("$msg.simplePart");
    assertNull(simplePart.getNamespaceURI());
    assertEquals("simplePart", simplePart.getLocalName());
    assertEquals("hola", XmlUtil.getStringValue(simplePart));
  }
  
  public void testMessageElementPart() {
    Element elementPart = (Element) eval("$msg.elementPart");
    assertEquals(BpelConstants.NS_EXAMPLES, elementPart.getNamespaceURI());
    assertEquals("surpriseElement", elementPart.getLocalName());
  }
  
  public void testMessageAttributeLocation() throws Exception {
    Attr nameAttr = (Attr) eval("$msg.elementPart/c/@name");
    assertEquals("venus", nameAttr.getValue());
  }
  
  public void testMessageElementLocation() throws Exception {
    Element e = (Element) eval("$msg.elementPart/e");
    assertEquals("30", XmlUtil.getStringValue(e));
  }
  
  public void testFunctionSimplePart() {
    messageValue.setPart("simplePart", "hola");
    Element simplePart = (Element) eval("bpws11:getVariableData('msg', 'simplePart')");
    assertNull(simplePart.getNamespaceURI());
    assertEquals("simplePart", simplePart.getLocalName());
    assertEquals("hola", XmlUtil.getStringValue(simplePart));
  }
  
  public void testFunctionElementPart() {
    Element elementPart = (Element) eval("bpws11:getVariableData('msg', 'elementPart')");
    assertEquals(BpelConstants.NS_EXAMPLES, elementPart.getNamespaceURI());
    assertEquals("surpriseElement", elementPart.getLocalName());
  }
  
  public void testFunctionAttributeLocation() throws Exception {
    Attr nameAttr = (Attr) eval("bpws11:getVariableData('msg', 'elementPart', '/tns:surpriseElement/c/@name')");
    assertEquals("venus", nameAttr.getValue());
  }
  
  public void testFunctionElementLocation() throws Exception {
    Element e = (Element) eval("bpws11:getVariableData('msg', 'elementPart', '/tns:surpriseElement/e')");
    assertEquals("30", XmlUtil.getStringValue(e));
  }
  
  public void testFunctionAttributeProperty() throws Exception {
    assertEquals("venus", eval("bpws11:getVariableProperty('msg', 'tns:nameProperty')"));
  }
  
  public void testFunctionElementProperty() throws Exception {
    assertEquals("30", eval("bpws11:getVariableProperty('msg', 'tns:idProperty')"));
  }
  
  private Object eval(String text) {
    Expression expr = new Expression();
    expr.setText(text);
    expr.setNamespaces(namespaces);
    return expr.getEvaluator().evaluate(token);
  }
}