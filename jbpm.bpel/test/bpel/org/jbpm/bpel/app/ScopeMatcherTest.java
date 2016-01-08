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
package org.jbpm.bpel.app;

import java.io.StringReader;
import java.util.Map;

import junit.framework.TestCase;

import org.xml.sax.InputSource;

import org.jbpm.bpel.app.AppScope;
import org.jbpm.bpel.app.AppDescriptor;
import org.jbpm.bpel.app.ScopeMatcher;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.xml.AppDescriptorReader;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.BpelReader;

/**
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:16 $
 */
public class ScopeMatcherTest extends TestCase {

  private ScopeMatcher matcher;
  private AppDescriptorReader adReader = AppDescriptorReader.getInstance();
  private BpelDefinition process;
  
  public void setUp() throws BpelException {
    process = new BpelDefinition();
    InputSource input = new InputSource(getClass().getResource("definition.xml").toString());
    BpelReader.getInstance().read(process, input);
    matcher = new ScopeMatcher();
  }
  
  public void testMatchProcessConfig() throws Exception {
    String text = 
      "<bpelApplication xmlns='http://jbpm.org/bpel'>" +      
      "<partnerLinks>" +
      "<partnerLink name='schedulingPL'>" +
      "<myRole destination='queue/scheduling'/>" +
      "</partnerLink>" +
      "</partnerLinks>" +      
      "</bpelApplication>";

    AppDescriptor adApplication = new AppDescriptor(); 
    adReader.read(adApplication, new InputSource( new StringReader(text)) );
    Map configurations = matcher.match(process, adApplication);
    AppScope adScope = (AppScope) configurations.get(process.getGlobalScope());
    assertSame(adApplication, configurations.get(process.getGlobalScope()));
    assertEquals(1, adScope.getPartnerLinks().size());
  }
  
  public void testMatchScopeConfig() throws Exception {
    String text = 
      "<bpelApplication xmlns='http://jbpm.org/bpel'>" +  
      " <scopes>" +
      "  <scope name='s1'>" +
      "   <scopes>" +      
      "    <scope name='s1.1' destination='s1Destination'/>" +  
      "   </scopes>" +      
      "  </scope>" +
      " </scopes>" +
      "</bpelApplication>";

    AppDescriptor application = new AppDescriptor();
    adReader.read(application, new InputSource( new StringReader(text)) );
    Scope s1Def = (Scope) ((Sequence)process.getGlobalScope().getRoot()).getNodes().get(1);
    AppScope s1Config = (AppScope) application.getScopes().iterator().next();
    Scope s11Def = (Scope) s1Def.getRoot();
    AppScope s11Config = (AppScope) s1Config.getScopes().iterator().next();
    
    Map configurations = matcher.match(process, application);
    
    assertSame(s1Config, configurations.get(s1Def));
    assertSame(s11Config, configurations.get(s11Def));    
  }
  
  public void testMatchUnnamedScopeConfig() throws Exception {
    String text = 
      "<bpelApplication xmlns='http://jbpm.org/bpel'>" +  
      " <scopes>" +
      "  <scope destination='s1Destination'/>" + 
      " </scopes>" +      
      "</bpelApplication>";

    AppDescriptor application = new AppDescriptor();
    adReader.read(application, new InputSource( new StringReader(text)) );
    Scope s1Def = (Scope) ((Sequence)process.getGlobalScope().getRoot()).getNodes().get(2);
    AppScope s1Config = (AppScope) application.getScopes().iterator().next();
    Map configurations = matcher.match(process, application);
    
    assertSame(s1Config, configurations.get(s1Def));
  }
  
  public void testConflictingScopeConfig() throws Exception {
    String text = 
      "<bpelApplication xmlns='http://jbpm.org/bpel'>" +  
      " <scopes>" +
      "  <scope name='conflictingName' destination='s1Destination'/>" + 
      " </scopes>" +      
      "</bpelApplication>";

    AppDescriptor application = new AppDescriptor();
    adReader.read(application, new InputSource( new StringReader(text)) );
    try { 
      matcher.match(process, application);
      fail("conflicting name resolution must fail");
    }
    catch (RuntimeException e) {
      // this exception is expected
    }
  }
}
