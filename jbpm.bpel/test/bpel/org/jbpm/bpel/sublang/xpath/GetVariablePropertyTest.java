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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.sublang.xpath.GetVariablePropertyFunction;
import org.jbpm.bpel.sublang.xpath.ScopeVariableContext;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class GetVariablePropertyTest extends TestCase {
  
  private MessageValue messageValue;
  private Context context;

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
  
  public GetVariablePropertyTest(String name) {
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
    BpelReader.getInstance().registerPropertyAliases(imports);
    // variable definition
    MessageType type = imports.getMessageType(new QName(BpelConstants.NS_EXAMPLES, "request"));
    VariableDefinition messageVariable = new VariableDefinition();
    messageVariable.setName("msg");
    messageVariable.setType(type); 
    scope.addVariable(messageVariable);
    // initialize variables
    scope.initScopeData(token); 
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

  public void testEvaluateAttribute() throws FunctionCallException {
    Element elementPart = messageValue.getPartForAssign("elementPart");
    Document doc = elementPart.getOwnerDocument();
    Element elemC = (Element) elementPart.appendChild(doc.createElementNS(null, "c"));
    elemC.setAttributeNS(null, "name", "wazabi");
    assertEquals("wazabi", GetVariablePropertyFunction.evaluate("msg", "tns:nameProperty", context)); 
  }
  
  public void testEvaluateElement() throws FunctionCallException {
    Element elementPart = messageValue.getPartForAssign("elementPart");
    Document doc = elementPart.getOwnerDocument();
    Element elemE = (Element) elementPart.appendChild(doc.createElementNS(null, "e"));
    elemE.appendChild(doc.createTextNode("30"));
    assertEquals("30", GetVariablePropertyFunction.evaluate("msg", "tns:idProperty", context));
  }
}
