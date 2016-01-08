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
import java.util.Set;

import javax.wsdl.Definition;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

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
 * @version $Revision: 1.14 $ $Date: 2006/08/22 04:13:10 $
 */
public class ToVariableTest extends TestCase {

  private VariableDefinition simpleVariable;
  private VariableDefinition messageVariable;
  private ToVariable to = new ToVariable();
  
  private Token token;
  private MessageValue messageValue;
  
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
  private static final String ELEM_SURPRISE = "surpriseElement";
  
  public ToVariableTest(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    // process and token
    BpelDefinition pd = new BpelDefinition();
    token = new ProcessInstance(pd).getRootToken();
    Scope scope = pd.getGlobalScope();
    // message type
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
    messageValue = (MessageValue) messageVariable.getValueForAssign(token);
    // provide a way to the variable definition
    Empty activity = new Empty();
    scope.addNode(activity);
    token.setNode(activity);    
  }
  
  public void testAssignVariable() {
    // to
    to.setVariable(simpleVariable);
    // instance data
    to.assign(token, "frack");
    // assertions
    Element simpleValue = (Element) simpleVariable.getValue(token);
    assertEquals("frack", XmlUtil.getStringValue(simpleValue));
  }
  
  public void testAssignPart() {
    // to
    to.setVariable(messageVariable);
    to.setPart("elementPart");
    // instance data
    Element sourceElem = XmlUtil.createElement("urn:foo", "sourceElement");
    sourceElem.appendChild(sourceElem.getOwnerDocument().createElementNS(BpelConstants.NS_EXAMPLES, ELEM_SURPRISE));
    to.assign(token, sourceElem);
    // assertions
    Element part = messageValue.getPart("elementPart");
    assertEquals(BpelConstants.NS_EXAMPLES, part.getNamespaceURI());
    assertEquals(ELEM_SURPRISE, part.getLocalName());
  }
  
  public void testAssignQuery() {
    // namespace declarations
    Set namespaces = Collections.singleton(new Namespace("tns", BpelConstants.NS_EXAMPLES));
    // query
    Query query = new Query();
    query.setText("/tns:surpriseElement/e");
    query.setNamespaces(namespaces);
    // to
    to.setVariable(messageVariable);
    to.setPart("elementPart");
    to.setQuery(query);
    // instance data
    to.assign(token, "1981");
    // assertions
    Element part = messageValue.getPart("elementPart");
    assertEquals("1981", XmlUtil.getStringValue(XmlUtil.getElement(part, "e")));
  }
}