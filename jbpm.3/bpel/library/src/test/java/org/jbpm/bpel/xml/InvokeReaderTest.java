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
import java.util.Map;

import org.jbpm.bpel.def.Invoke;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.variable.def.VariableDefinition;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class InvokeReaderTest extends AbstractReaderTestCase {
  
  VariableDefinition outputVariable;
  
  public void setUp() throws Exception {
    super.setUp();
    initMessageProperties();
    // output variable
    outputVariable = new VariableDefinition();
    outputVariable.setName("ov");
    outputVariable.setType(messageVariable.getType());
    scope.addVariable(outputVariable);
  }
  
  public void testScopedInvoke() throws Exception {
    String xml = 
      "<invoke partnerLink='aPartner' operation='o' inputVariable='iv'>" +
      "<compensationHandler><empty/></compensationHandler>" +
      "<faultHandlers><catchAll><empty/></catchAll></faultHandlers>" +      
      "</invoke>";
    Scope scope = (Scope) readActivity(xml);
    
    assertTrue( scope.isImplicit() );
    assertEquals( Invoke.class, scope.getRoot().getClass() );
    assertNotNull( scope.getHandler(Scope.COMPENSATION) );
    assertNotNull( scope.getHandler(Scope.CATCH_ALL) );
    
    assertEquals(pLink, ((Invoke)scope.getRoot()).getInvoker().getPartnerLink() );
  }
  
  // --- INVOKE ATTRIBUTES AND ELEMENTS ---
  public void testPartnerLink() throws Exception {
    String xml = "<invoke partnerLink='aPartner' operation='o' inputVariable='iv'/>";
    Invoke invoke = (Invoke) readActivity(xml);
    assertEquals(pLink, invoke.getInvoker().getPartnerLink() );
  }

  public void testPortType() {
    String xml = "<invoke partnerLink='aPartner' portType='def:ppt' operation='o' inputVariable='iv'" +
        " xmlns:def='http://manufacturing.org/wsdl/purchase'/>";
    try {
      readActivity(xml);
    }
    catch (Exception e) {
      fail(e.toString());
    }
  }

  public void testPortTypeDefault() {
    String xml = "<invoke partnerLink='aPartner' operation='o' inputVariable='iv'/>";
    try {
      readActivity(xml);
    }
    catch (Exception e) {
      fail(e.toString());
    }
  }
  
  public void testPortTypeNotFound() throws Exception {
    String xml = "<invoke partnerLink='aPartner' portType='invalidPT' operation='o' inputVariable='iv'/>";
    ProblemCollector collector = new ProblemCollector();
    reader.setProblemHandler(collector);
    readActivity(xml);
    assertFalse("invoke parse must fail when portType doesn't match partnerRole's portType",
        collector.getProblems().isEmpty());
  }
  
  public void testOperation() throws Exception {
    String xml = "<invoke partnerLink='aPartner' operation='o' inputVariable='iv'/>";
    Invoke invoke = (Invoke) readActivity(xml);
    assertEquals( "o", invoke.getInvoker().getOperation().getName() );
  }

  public void testInputVariableDefinition() throws Exception {
    String xml = 
      "<invoke partnerLink='aPartner' operation='o' inputVariable='iv'/>";
    Invoke invoke = (Invoke) readActivity(xml);
    assertSame(messageVariable, invoke.getInvoker().getInputVariable() );
  }
  
  public void testOutputVariableDefinition() throws Exception {
    String xml = 
      "<invoke partnerLink='aPartner' operation='o2' inputVariable='iv' outputVariable='ov'/>";
    Invoke invoke = (Invoke) readActivity(xml);
    assertSame(outputVariable, invoke.getInvoker().getOutputVariable() );
  }

  public void testOutputVariableDefinitionDefault() throws Exception {
    String xml = "<invoke partnerLink='aPartner' operation='o' inputVariable='iv'/>";
    Invoke invoke = (Invoke) readActivity(xml);
    assertNull( invoke.getInvoker().getOutputVariable() );
  }  
  
  public void testCorrelations() throws Exception {
    String xml = 
      "<invoke partnerLink='aPartner' operation='o2' inputVariable='iv' outputVariable='ov'>" +
      "	<correlations>" +
      "		<correlation set='in' initiate='yes' pattern='in'/> " +
      "		<correlation set='out' initiate='no' pattern='out'/> " +
      "		<correlation set='outin' pattern='out-in'/> " +
      "	</correlations>" +
      "</invoke>";
    
    CorrelationSetDefinition set = new CorrelationSetDefinition();
    set.setName("in");
    set.setProperties(Collections.EMPTY_SET);
    scope.addCorrelationSet(set);
    
    set = new CorrelationSetDefinition();
    set.setName("out");
    set.setProperties(Collections.EMPTY_SET);
    scope.addCorrelationSet(set);
    
    set = new CorrelationSetDefinition();
    set.setName("outin");
    set.setProperties(Collections.EMPTY_SET);
    scope.addCorrelationSet(set);
    
    Invoke invoke = (Invoke) readActivity(xml);
    
    Correlations correlations = invoke.getInvoker().getInCorrelations();
    Map correlationMap = correlations.getCorrelations(); 
    
    assertNotNull( correlationMap.get("in") );
    assertNotNull( correlationMap.get("outin") );
    
    correlations = invoke.getInvoker().getOutCorrelations();
    correlationMap = correlations.getCorrelations();

    assertNotNull( correlationMap.get("out") );
    assertNotNull( correlationMap.get("outin") );
  }
}
