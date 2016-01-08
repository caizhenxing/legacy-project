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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.*;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.variable.def.VariableDefinition;

/**
 * @author Juan Cantú
 * @version $Revision: 1.10 $ $Date: 2006/08/22 04:13:10 $
 */
public class ScopeReaderTest extends AbstractReaderTestCase {

  public void setUp() throws Exception {
    super.setUp();
    initMessageProperties();
  }
  
  public void testActivity() throws Exception {
    String xml = "<scope><empty/></scope>";
    Scope localScope = (Scope) readActivity( xml );
    Activity root = localScope.getRoot();
    assertNotNull(root);
    assertEquals(localScope, root.getCompositeActivity());
  }
  
  public void testScope() throws Exception {
    String xml = "<scope><empty/></scope>";
    Scope localScope = (Scope) readActivity( xml );
    Activity root = localScope.getRoot();
    assertEquals( localScope,  root.getScope() );
  }  
  
  public void testIsolated() throws Exception {
    String xml = "<scope isolated='yes'><empty/></scope>";
    Scope localScope = (Scope) readActivity( xml );
    assertTrue( localScope.isIsolated() );
  }  
  
  public void testImplicit() throws Exception {
    String xml = "<scope isolated='yes'><empty/></scope>";
    Scope localScope = (Scope) readActivity( xml );
    assertFalse( localScope.isImplicit() );
    assertTrue( localScope.getScope().isImplicit() );
  } 
  
  public void testVariables() throws Exception {
    String xml =
     "<scope>" +
     "  <variables>" +
     "    <variable name='v'  type='simple'/>" +
     "    </variables>" +     
     "  <empty/>" +
     "</scope>";
    loadScope( xml );
    VariableDefinition variable = scope.getVariable("v");
    assertNotNull(variable);
  }     
  
  public void testPartnerLinks() throws Exception {
    initMessageProperties();    
    String xml = 
      "<scope xmlns:tns='http://manufacturing.org/wsdl/purchase'>" +
      " <partnerLinks xmlns:vendor='http://jbpm.org'>" +
      "   <partnerLink name='aPartner' partnerLinkType='tns:aPartnerLinkType'" +
      "    partnerRole='role1' myRole='role2'/>" +    
      " </partnerLinks>" +
      " <receive partnerLink='aPartner' operation='o' variable='iv'/>" +
      "</scope>";
    
    loadScope( xml );
    PartnerLinkDefinition pl = scope.getPartnerLink("aPartner");
    assertNotNull( pl );
  }  
  
  public void testCorrelationSets() throws Exception {
    String xml = 
      "<pd xmlns:def='http://manufacturing.org/wsdl/purchase'>" +
      " <correlationSets>" +
      " <correlationSet name='cs1' properties='def:p1 def:p2'/>" +
      " <correlationSet name='cs2' properties='def:p3'/>" +
      " </correlationSets>" +
      " <receive partnerLink='aPartner' operation='o' variable='iv'/>" +
      "</pd>";
    loadScope( xml );
    
    CorrelationSetDefinition cs = scope.getCorrelationSet("cs1");
    Set properties = cs.getProperties();
    assertEquals(2, properties.size());
    assertTrue(properties.contains(p1));
    assertTrue(properties.contains(p2));
    
    CorrelationSetDefinition cs2 = scope.getCorrelationSet("cs2");
    properties = cs2.getProperties();
    assertEquals(1, properties.size());
    assertTrue(properties.contains(p3));
  }
  
  public void testCatches() throws Exception { 
    QName wsdlFaultType = new QName(NS_TNS, "aQName");
    String xml =
    "<pd>" +
    " <receive partnerLink='aPartner' operation='o' variable='iv'/>" +
    "   <faultHandlers xmlns:def='http://manufacturing.org/wsdl/purchase'>" +
    "     <catch faultName='firstFault'><empty name='firstFaultActivity'/></catch>" +
    "     <catch faultName='secondFault' faultVariable='fv' faultMessageType='def:aQName'><empty/></catch>" +
    "     <catch faultName='thirdFault' faultMessageType='def:aQName'><empty/></catch>" +
    "     <catch faultVariable='fourthFault' faultElement='xmlType'><empty/></catch>" +
    "   </faultHandlers>" +    
    "</pd>";
    
    loadScope( xml );
    
    //test first fault
    QName firstFaultName = new QName("firstFault");
    Iterator handlerIt = scope.getFaultHandlers().iterator();
    Catch firstCatch = (Catch) handlerIt.next();

    assertEquals( firstFaultName, firstCatch.getFaultName() );
    assertNull( firstCatch.getFaultVariable() );
    
    //test second fault
    QName secondFaultName = new QName("secondFault");
    Catch secondCatch = (Catch) handlerIt.next();
    assertEquals( secondFaultName, secondCatch.getFaultName() );
    assertEquals( wsdlFaultType, secondCatch.getFaultVariable().getType().getName() );    
  }
  
  public void testCatchAll() throws Exception {
    String xml =
      "<pd>" +
      " <receive partnerLink='aPartner' operation='o' variable='iv'/>" +
      " <faultHandlers><catchAll><empty name='catchAllActivity'/></catchAll></faultHandlers>" +
      "</pd>";
    loadScope( xml );
    ScopeHandler catchAll = scope.getHandler(Scope.CATCH_ALL);
    assertNotNull(catchAll);
    assertEquals("catchAllActivity", catchAll.getActivity().getName());
  }  
  
  public void testCompensationHandler() throws Exception {
    String xml =
      "<pd>" +
      " <receive partnerLink='aPartner' operation='o' variable='iv'/>" +
      " <compensationHandler><empty name='compActivity'/></compensationHandler>" +
      "</pd>";
    loadScope( xml );
    ScopeHandler compensation = scope.getHandler(Scope.COMPENSATION);
    assertNotNull(compensation);
    assertEquals("compActivity", compensation.getActivity().getName());
  }    
  
  public void testTerminationHandler() throws Exception {
    String xml =
      "<pd>" +
      " <receive partnerLink='aPartner' operation='o' variable='iv'/>" +
      " <terminationHandler><empty name='activity'/></terminationHandler>" +
      "</pd>";
    loadScope( xml );
    ScopeHandler termination = scope.getHandler(Scope.TERMINATION);
    assertNotNull(termination);
    assertEquals("activity", termination.getActivity().getName());
  }
  
  public void testOnAlarm() throws Exception {
    String xml = 
      "<pd>" +
      " <receive partnerLink='aPartner' operation='o' variable='iv'/>" +
      " <eventHandlers>" +
      "   <onAlarm><for>$f</for><empty name='oA1'/></onAlarm>" +
      " </eventHandlers>" +
      "</pd>";
    
    loadScope( xml );
    Collection onAlarms = scope.getOnAlarms();
    assertEquals(1, onAlarms.size());
    OnAlarm onAlarm = (OnAlarm) onAlarms.iterator().next();
    Object activity = onAlarm.getActivity();
    assertEquals( Empty.class, activity.getClass());
    assertEquals( "oA1", ((Empty)activity).getName());
    assertNotNull(onAlarm.getAlarm());
  }  
  
  public void testOnEvent() throws Exception {
    String xml = 
      "<pd>" +
      " <receive partnerLink='aPartner' operation='o' variable='iv'/>" +
      " <eventHandlers xmlns:tns='http://manufacturing.org/wsdl/purchase'>" +
      "   <onEvent partnerLink='aPartner' operation='o' variable='v' messageType='tns:aQName'>" +
      "     <correlationSets>" +
      "       <correlationSet name='cs1' properties='tns:p1 tns:p2'/>" +
      "     </correlationSets>" +      
      "     <empty name='oM1'/>" +
      "   </onEvent>" +
      " </eventHandlers>" +
      "</pd>";      
    loadScope( xml );
    Collection receivers = scope.getOnEvents();
    assertEquals(1, receivers.size());
    OnEvent onEvent = (OnEvent) receivers.iterator().next();
    Object activity = onEvent.getActivity();
    //activity
    assertEquals( Empty.class, activity.getClass());
    assertEquals( "oM1", ((Empty)activity).getName());
    //receiver
    assertNotNull(onEvent.getReceiver());
    //correlation set
    assertNotNull(onEvent.getCorrelationSet("cs1"));
    //variable
    assertEquals("v", onEvent.getVariableDefinition().getName());
  }    
  
  private void loadScope(String xml) throws Exception {
    Element element = parseAsBpelElement(xml);
    reader.readScope(element, scope);
  }
}