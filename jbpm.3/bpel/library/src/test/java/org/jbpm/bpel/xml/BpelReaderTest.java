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
package org.jbpm.bpel.xml;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Catch;
import org.jbpm.bpel.def.Import;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.variable.def.ElementType;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.def.VariableType;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.wsdl.impl.PropertyAliasImpl;
import org.jbpm.graph.def.Node;

/**
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class BpelReaderTest extends AbstractReaderTestCase {
  
  public void setUp() throws Exception {
    super.setUp();
    initMessageProperties();
  }

  public void testReadUrl() throws Exception {
    BpelDefinition pd = new BpelDefinition();
    // read bpel
    ProblemCollector collector = new ProblemCollector();
    reader.setProblemHandler(collector);
    String resourceURI = getClass().getResource("processSample.bpel").toString();
    reader.read(pd, new InputSource(resourceURI));
    // assertions
    assertTrue(collector.getProblems().isEmpty());
    assertNull(pd.getStartState());
    Node node = (Node) pd.getNodes().get(0);
    assertNotNull(node);
    assertEquals(pd, node.getProcessDefinition());
  }

  
  public void testReadUrlLegacy() throws Exception {
    BpelDefinition pd = new BpelDefinition();
    // add wsdl document
    Import imp = new Import();
    imp.setNamespace("http://manufacturing.org/wsdl/purchase");
    imp.setLocation(getClass().getResource("partnerLinkTypeSample-1_1.wsdl").toString());
    imp.setType(Import.Type.WSDL);
    reader.readWsdlDocument(imp, new ImportWsdlLocator(""));
    pd.getImports().addImport(imp);
    // read bpel
    ProblemCollector problems = new ProblemCollector();
    reader.setProblemHandler(problems);
    InputSource input = 
      new InputSource(getClass().getResource("processSample-1_1.bpel").toString());
    reader.read(pd, input);
    // assertions
    assertTrue(problems.getProblems().isEmpty());
    assertNull(pd.getStartState());
    Node node = (Node) pd.getNodes().get(0);
    assertNotNull(node);
    assertEquals(pd, node.getProcessDefinition());
  }
  
  public void testQueryLanguage() throws Exception {
    String xml = "<pd queryLanguage='ql'>" +
        "<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertEquals( "ql", pd.getQueryLanguage() );
  }
  
  public void testExpressionLanguage() throws Exception {
    String xml = "<pd expressionLanguage='el'>" +
        "<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertEquals( "el", pd.getExpressionLanguage() );
  }
  
  public void testSuppressJoinFailureYes() throws Exception {
    String xml = "<pd suppressJoinFailure='yes'>" +
        "<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertTrue( pd.getGlobalScope().getSuppressJoinFailure().booleanValue() );
  }
  
  public void testSuppressJoinFailureNo() throws Exception {
    String xml = "<pd suppressJoinFailure='no'>" +
        "<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertFalse( pd.getGlobalScope().getSuppressJoinFailure().booleanValue() );
  }
  
  public void testSuppressJoinFailureDefault() throws Exception {
    String xml = "<pd>" +
    		"<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertFalse( pd.getGlobalScope().getSuppressJoinFailure().booleanValue() );
  }
  
  public void testAbstractProcessYes() throws Exception {
    String xml = "<pd abstractProcess='yes'>" +
    		"<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertTrue( pd.isAbstractProcess() );
  }
  
  public void testAbstractProcessNo() throws Exception {
    String xml = "<pd abstractProcess='no'>" +
    		"<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertFalse( pd.isAbstractProcess() );
  }
  
  public void testAbstractProcessDefault() throws Exception {
    String xml = "<pd>" +
    		"<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertFalse( pd.isAbstractProcess() );
  }
  
  public void testEnableInstanceCompensationYes() throws Exception {
    String xml = "<pd enableInstanceCompensation='yes'>" +
    		"<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertTrue( pd.getEnableInstanceCompensation() );
  }
  
  public void testEnableInstanceCompensationNo() throws Exception {
    String xml = "<pd enableInstanceCompensation='no'>" +
    		"<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertFalse( pd.getEnableInstanceCompensation() );
  }
  
  public void testEnableInstanceCompensationDefault() throws Exception {
    String xml = "<pd>" +
    		"<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    assertFalse( pd.getEnableInstanceCompensation() );
  }   

  public void testScopeDefinition() throws Exception {
    String xml = "<pd>" +
    "<receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    List processNodes = pd.getNodes();
    assertEquals(1, processNodes.size());
    Scope scope = pd.getGlobalScope();
    assertEquals(processNodes.get(0), scope);
    assertEquals(pd, scope.getProcessDefinition());
    assertEquals(1, scope.getNodes().size());
  }
  
  public void testCatchMessageVariable() throws Exception {
    String xml =
      "<pd xmlns:tns='http://manufacturing.org/wsdl/purchase'>" +
      " <variables>" +
      "  <variable name='v' messageType='tns:aQName'/>" +
      " </variables>" +
      " <faultHandlers>" +
      "  <catch faultVariable='v' faultMessageType='tns:aQName'>" +
      "   <empty/>" +
      "  </catch>" +
      " </faultHandlers>" +
      " <empty/>" +
      "</pd>";
    readProcess(xml);
    Scope globalScope = pd.getGlobalScope();
    // fault handler exists
    List faultHandlers = globalScope.getFaultHandlers();
    assertEquals(1, faultHandlers.size());
    // fault handler has local variable
    Catch catcher = (Catch) faultHandlers.get(0);
    assertNotSame(globalScope.getVariable("v"), catcher.getFaultVariable());
  }
  
  public void testCatchElementVariable() throws Exception {
    String xml =
      "<pd>" +
      " <faultHandlers xmlns:sns='http://manufacturing.org/xsd/purchase'>" +
      "  <catch faultVariable='e' faultElement='sns:purchase'>" +
      "   <empty/>" +
      "  </catch>" +
      " </faultHandlers>" +
      " <empty/>" +
      "</pd>";
    readProcess(xml);
    Scope globalScope = pd.getGlobalScope();
    // fault handler exists
    List faultHandlers = globalScope.getFaultHandlers();
    assertEquals(1, faultHandlers.size());
    // fault handler has local variable
    Catch catcher = (Catch) faultHandlers.get(0);
    assertEquals("e", catcher.getFaultVariable().getName());
  }
  
  public void testCatch11MessageVariable() throws Exception {
    String xml =
      "<pd>" +
      " <variables xmlns:tns='http://manufacturing.org/wsdl/purchase'>" +
      "  <variable name='v' messageType='tns:aQName'/>" +
      " </variables>" +
      " <faultHandlers>" +
      "  <catch faultVariable='v'>" +
      "   <empty/>" +
      "  </catch>" +
      " </faultHandlers>" +
      " <empty/>" +
      "</pd>";
    readProcess(xml);
    Scope globalScope = pd.getGlobalScope();
    // fault handler exists
    List faultHandlers = globalScope.getFaultHandlers();
    assertEquals(1, faultHandlers.size());
    // fault handler references variable from enclosing scope
    Catch catcher = (Catch) faultHandlers.get(0);
    assertSame(globalScope.getVariable("v"), catcher.getFaultVariable());
  }
  
  public void testCatch11NonMessageVariable() throws Exception {
    ProblemCollector collector = installCollector();
    String xml =
      "<pd>" +
      " <variables xmlns:xsd='http://www.w3.org/2001/XMLSchema'>" +
      "  <variable name='v' type='xsd:string'/>" +
      " </variables>" +      
      " <faultHandlers>" +
      "  <catch faultVariable='v'>" +
      "   <empty/>" +
      "  </catch>" +
      " </faultHandlers>" +
      " <empty/>" +
      "</pd>";
    readProcess(xml);
    assertFalse("variable is not a message", collector.getProblems().isEmpty());
  }
  
  public void testCatch11VariableNotFound() throws Exception {
    ProblemCollector collector = installCollector();
    String xml =
      "<pd>" +
      " <faultHandlers>" +
      "  <catch faultVariable='v'>" +
      "   <empty/>" +
      "  </catch>" +
      " </faultHandlers>" +
      " <empty/>" +
      "</pd>";
    readProcess(xml);
    assertFalse("variable does not exist", collector.getProblems().isEmpty());
  }

  public void testActivity() throws Exception {
    String xml = "<pd><receive partnerLink='aPartner' operation='o' variable='iv'/></pd>";
    readProcess( xml );
    Activity root = pd.getGlobalScope().getRoot();
    assertTrue(root instanceof Receive);
    assertSame(pd, root.getProcessDefinition());
  }
  
  // Receiver
  /////////////////////////////////////////////////////////////////////////////  
  
  public void testReceiverPartnerLink() throws Exception {
    String xml = "<rcvr partnerLink='aPartner' operation='o' variable='iv'/>";    
    Receiver receiver = readReceiver( xml );
    assertEquals(pLink, receiver.getPartnerLink() );    
  }  
  
  public void testReceiverPortType() throws Exception {
    ProblemCollector collector = installCollector();
    String xml = "<receive partnerLink='aPartner' portType='tns:mpt' operation='o' variable='iv'" +
        " xmlns:tns='http://manufacturing.org/wsdl/purchase'/>";
    readReceiver(xml);
    assertTrue(collector.getProblems().isEmpty());
  }

  public void testReceiverPortTypeDefault() throws Exception {
    ProblemCollector collector = installCollector();
    String xml = "<rcvr partnerLink='aPartner' operation='o' variable='iv'/>";
    readReceiver(xml);
    assertTrue(collector.getProblems().isEmpty());
  }
  
  public void testReceiverPortTypeNotFound() throws Exception {
    ProblemCollector collector = installCollector();
    String xml = "<rcvr partnerLink='aPartner' portType='invalidPT' operation='o' variable='iv'/>";
    readReceiver(xml);
    assertFalse("portType does not match myRole",
        collector.getProblems().isEmpty());
  }
    
  public void testReceiverOperation() throws Exception {
    String xml = "<rcvr partnerLink='aPartner' operation='o' variable='iv'/>";
    Receiver receiver = readReceiver(xml);
    assertEquals("o", receiver.getOperation().getName());
  }      
  
  public void testReceiverVariable() throws Exception {  
    String xml = "<rcvr partnerLink='aPartner' operation='o' variable='iv'/>";
    Receiver receiver = readReceiver( xml );
    assertSame(messageVariable, receiver.getVariable());
  }   
  
  public void testReceiverCorrelations() throws Exception {
    // correlation set
    CorrelationSetDefinition corr = new CorrelationSetDefinition();
    corr.setName("corr");
    corr.setProperties(Collections.singleton(p1));
    scope.addCorrelationSet(corr);
    // alias
    MessageType messageType = (MessageType) messageVariable.getType();
    PropertyAlias alias = new PropertyAliasImpl();
    alias.setMessage(messageType.getMessage());
    alias.setProperty(p1);
    alias.setPart("p");
    messageType.addPropertyAlias(alias);
    
    String xml = 
      "<rcvr partnerLink='aPartner' operation='o' variable='iv'>" +
      "   <correlations>" +
      "     <correlation set='corr'/> " +
      "   </correlations>" +      
      "</rcvr>";
    Receiver receiver = readReceiver( xml );
    
    assertNotNull( receiver.getCorrelations() );
  }       
  
  // Alarm
  /////////////////////////////////////////////////////////////////////////////  

  public void testAlarmFor() throws Exception {
    String xml = 
      "<alrm>" +
      " <for>$f</for>" +
      "</alrm>";
    
    Alarm alarm = readAlarm( xml );
    assertEquals( "$f", alarm.getFor().getText() );
  }

  public void testAlarmUntil() throws Exception {
    String xml = 
      "<alrm>" +
      " <until>$u</until>" +
      "</alrm>";
    
    Alarm alarm = readAlarm( xml );
    assertEquals( "$u", alarm.getUntil().getText() );
  }

  public void testAlarmRepeat() throws Exception {
    String xml = 
      "<alrm>" +
      " <for>$f</for>" +
      " <repeatEvery>$r</repeatEvery>" +
      "</alrm>";
    
    Alarm alarm = readAlarm( xml );
    assertEquals( "$f", alarm.getFor().getText() );
    assertEquals( "$r", alarm.getRepeatEvery().getText() );
  }

  public void testAlarmNoExpression() throws Exception {
    ProblemCollector collector = installCollector();
    String xml = "<alrm/>";
    readAlarm( xml );
    assertFalse("alarm parsing must fail when no expression is specified", 
        collector.getProblems().isEmpty());
  }

  // Variables
  /////////////////////////////////////////////////////////////////////////////  
  
  public void testVariableName() throws Exception {
    String xml =
     "<variables>" +
     " <variable name='v'  type='simple'/>" +
     "</variables>";

    Map variables = readVariables( xml );
    VariableDefinition variable = (VariableDefinition) variables.get("v");
    assertEquals("v", variable.getName());
  }  
  
  public void testVariableNameNoType() throws Exception {
    String xml =
     "<variables>" +
     " <variable name='v'/>" +
     "</variables>";
    ProblemCollector collector = new ProblemCollector();
    reader.setProblemHandler(collector);
    readVariables(xml);
    assertEquals(1, collector.getProblems().size());
  } 
  
  public void testVariableType() throws Exception {
    String xml =
     "<variables>" +
     " <variable name='v' type='simple'/>" +
     "</variables>";
    Map variables = readVariables( xml );
    VariableDefinition variable = (VariableDefinition) variables.get("v");
    assertEquals(new QName("simple"), variable.getType().getName());
  }  
  
  public void testVariableMessageType() throws Exception {
    String xml =
     "<variables xmlns:tns='http://manufacturing.org/wsdl/purchase'>" +
     " <variable name='v' messageType='tns:aQName'/>" +
     "</variables>";
    Map variables = readVariables( xml );
    VariableDefinition variable = (VariableDefinition) variables.get("v");
    VariableType type = variable.getType();
    assertTrue(type instanceof MessageType);
    assertEquals(new QName("http://manufacturing.org/wsdl/purchase", "aQName"), type.getName());
  }  
  
  public void testVariableSimpleType() throws Exception {
    String xml =
     "<variables>" +
     " <variable name='v' type='simple'/>" +
     "</variables>";
    Map variables = readVariables( xml );
    VariableDefinition variable = (VariableDefinition) variables.get("v");
    VariableType type = variable.getType();
    assertTrue(type instanceof SchemaType);
    assertEquals(new QName("simple"), variable.getType().getName());
  }  
  
  public void testVariableElement() throws Exception {
    String xml =
     "<variables>" +
     " <variable name='v' element='element'/>" +
     "</variables>";
    Map variables = readVariables( xml );
    VariableDefinition variable = (VariableDefinition) variables.get("v");
    VariableType type = variable.getType();
    assertTrue(type instanceof ElementType);
    assertEquals(new QName("element"), variable.getType().getName());
  }
  
  private void readProcess(String xml) throws Exception {
    Element element = parseAsBpelElement(xml);
    reader.readTopLevelAttributes(element, pd);
    reader.readScope(element, pd.getGlobalScope());
  }
  
  private Map readVariables(String xml) throws Exception {
    Element element = parseAsBpelElement(xml);
    return reader.readVariables(element, scope);
  }
  
  private Receiver readReceiver(String xml) throws Exception {
    Element element = parseAsBpelElement(xml);
    return reader.readReceiver(element, scope);
  }
  
  private Alarm readAlarm(String xml) throws Exception {
    Element element = parseAsBpelElement(xml);
    return reader.readAlarm(element, scope);
  }
  
  private ProblemCollector installCollector() {
    ProblemCollector pc = new ProblemCollector();
    reader.setProblemHandler(pc);
    return pc;
  }
}
