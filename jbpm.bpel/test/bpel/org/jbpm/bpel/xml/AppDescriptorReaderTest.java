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

import java.io.StringReader;

import junit.framework.TestCase;

import org.xml.sax.InputSource;

import org.jbpm.bpel.app.AppMyRole;
import org.jbpm.bpel.app.AppPartnerLink;
import org.jbpm.bpel.app.AppPartnerRole;
import org.jbpm.bpel.app.AppScope;
import org.jbpm.bpel.app.AppDescriptor;
import org.jbpm.bpel.integration.catalog.CompositeCatalog;
import org.jbpm.bpel.integration.catalog.ServiceCatalog;
import org.jbpm.bpel.integration.exe.wsa.WsaConstants;
import org.jbpm.bpel.xml.util.XmlUtil;

public class AppDescriptorReaderTest extends TestCase {

  AppDescriptorReader reader = AppDescriptorReader.getInstance();
  
  public void testReadURL() {
    ProblemCollector collector = new ProblemCollector();
    reader.setProblemHandler(collector);
    AppDescriptor application = new AppDescriptor();
    String locationURI = getClass().getResource("bpelApplicationSample.xml").toString();
    reader.read(application, new InputSource(locationURI));
    
    assertTrue(collector.getProblems().isEmpty());
  }
  
  public void testVersion() throws Exception {
    ProblemCollector collector = new ProblemCollector();
    reader.setProblemHandler(collector);
    String text = "<bpelApplication name='application' version='7'/>";
    AppDescriptor application = new AppDescriptor();
    reader.read(application, new InputSource(new StringReader(text)));
    
    assertEquals(7, application.getVersion().intValue());
    assertTrue(collector.getProblems().isEmpty());
  }  
  
  public void testApplicationServiceCatalogs() throws Exception {
    String text = 
      "<bpelApplication xmlns='" + BpelConstants.NS_VENDOR  + "'>" +
      " <serviceCatalogs>" +
      "  <urlCatalog />" +
      " </serviceCatalogs>" +
      "</bpelApplication>";
    AppDescriptor application = new AppDescriptor();
    reader.read(application, new InputSource(new StringReader(text)));
    
    assertNotNull(application.getServiceCatalog());
  }
  
  public void testScopeName() throws Exception {
    String text = "<scope name='rfpScope'/>";
    AppScope scope = new AppScope();
    reader.readScope(XmlUtil.parseElement(text), scope);
    
    assertEquals("rfpScope", scope.getName());
  }
  
  public void testScopePartnerLinks() throws Exception {
    String text = 
      "<scope xmlns='" + BpelConstants.NS_VENDOR  + "'>" +
      " <partnerLinks>" +
      "  <partnerLink name='pl1'/>" +
      "  <partnerLink name='pl2'/>" +
      " </partnerLinks>" +
      "</scope>";
    AppScope scope = new AppScope();
    reader.readScope(XmlUtil.parseElement(text), scope);

    assertEquals(2, scope.getPartnerLinks().size());
  }
  
  public void testScopeScopes() throws Exception {
    String text = 
      "<scope xmlns='" + BpelConstants.NS_VENDOR  + "'>" +
      " <scopes>" +
      "  <scope name='s1'/>" +
      "  <scope name='s2'/>" +
      " </scopes>" +
      "</scope>";
    AppScope scope = new AppScope();
    reader.readScope(XmlUtil.parseElement(text), scope);

    assertEquals(2, scope.getScopes().size());
  }
  
  public void testPartnerLinkName() throws Exception {
    String text = "<partnerLink name='schedulerPL'/>";
    AppPartnerLink partnerLink = reader.readPartnerLink(XmlUtil.parseElement(text));
    
    assertEquals("schedulerPL", partnerLink.getName());
  }
  
  public void testPartnerLinkMyRole() throws Exception {
    String text = 
      "<partnerLink xmlns='" + BpelConstants.NS_VENDOR  + "'>" +
      " <myRole/>" +
      "</partnerLink>";
    AppPartnerLink partnerLink = reader.readPartnerLink(XmlUtil.parseElement(text));
    
    assertNotNull(partnerLink.getMyRole());
  }
  
  public void testPartnerLinkPartnerRole() throws Exception {
    String text = 
      "<partnerLink xmlns='" + BpelConstants.NS_VENDOR  + "'>" +
      " <partnerRole/>" +
      "</partnerLink>";
    AppPartnerLink partnerLink = reader.readPartnerLink(XmlUtil.parseElement(text));
    
    assertNotNull(partnerLink.getPartnerRole());
  }
  
  public void testMyRoleHandle() throws Exception {
    String text = "<myRole handle='port-a'/>";
    AppMyRole myRole = reader.readMyRole(XmlUtil.parseElement(text));
    
    assertEquals("port-a", myRole.getHandle());
  }
  
  public void testPartnerRoleInitiate() throws Exception {
    String text = "<partnerRole initiate='pull'/>";
    AppPartnerRole partnerRole = reader.readPartnerRole(XmlUtil.parseElement(text));
    
    assertEquals(AppPartnerRole.InitiateMode.PULL, partnerRole.getInitiateMode());
  }
  
  public void testPartnerRoleServiceRef() throws Exception {
    String text = 
      "<partnerRole>" +
      " <bpel:service-ref xmlns:bpel='" + BpelConstants.NS_BPWS + "'>" +
      "  <wsa:EndpointReference xmlns:wsa='" + WsaConstants.NS_ADDRESSING + "'>" +
      "   <wsa:Address>urn:example</wsa:Address>" +
      "  </wsa:EndpointReference>" +
      " </bpel:service-ref>" +
      "</partnerRole>";
    AppPartnerRole partnerRole = reader.readPartnerRole(XmlUtil.parseElement(text));
    
    assertNotNull(partnerRole.getEndpointReference());
  }
  
  public void testSingleServiceCatalog() throws Exception {
    String text = 
      " <serviceCatalogs xmlns='" + BpelConstants.NS_VENDOR  + "'>" +
      "  <urlCatalog />" +
      " </serviceCatalogs>";
    ServiceCatalog catalog = reader.readServiceCatalogs(XmlUtil.parseElement(text), null);
    
    assertNotNull(catalog);
  }
  
  public void testCompositeServiceCatalog() throws Exception {
    String text = 
      " <serviceCatalogs xmlns='" + BpelConstants.NS_VENDOR  + "'>" +
      "  <urlCatalog />" +
      "  <urlCatalog />" +
      "  <urlCatalog />" +
      " </serviceCatalogs>";
    CompositeCatalog catalog = (CompositeCatalog) reader.readServiceCatalogs(XmlUtil.parseElement(text), null);
     
    assertEquals(3, catalog.getCatalogs().size());
  }
}
