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
package org.jbpm.bpel.integration.exe;

import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.jbpm.bpel.integration.catalog.ServiceCatalog;
import org.jbpm.bpel.integration.catalog.URLCatalog;
import org.jbpm.bpel.integration.exe.SoapEndpointReference;

public abstract class SoapEndpointReferenceTest extends TestCase {
  
  private ServiceCatalog catalog;

  public SoapEndpointReferenceTest(String name) {
    super(name);
  }
  
  protected abstract SoapEndpointReference getReference();

  protected void setUp() throws Exception {
    URLCatalog urlCatalog = new URLCatalog();
    urlCatalog.addLocation(getResource("atm.wsdl"));
    urlCatalog.addLocation(getResource("translator.wsdl"));
    this.catalog = urlCatalog;
  }

  public void testSelectPort_portType() {
    QName portTypeName = new QName("http://example.com/translator", "textTranslator");
    
    SoapEndpointReference reference = getReference();
    reference.setPortTypeName(portTypeName);
    Port port = reference.selectPort(catalog);
    
    assertEquals(portTypeName, port.getBinding().getPortType().getQName());
  }
  
  public void testSelectPort_portType_address() {
    QName portTypeName = new QName("http://example.com/translator", "textTranslator");
    String address = "http://mirror.example.com/translator/text";
    
    SoapEndpointReference reference = getReference();
    reference.setPortTypeName(portTypeName);
    reference.setAddress(address);
    Port port = reference.selectPort(catalog);
    
    assertEquals(portTypeName, port.getBinding().getPortType().getQName());
    assertEquals(address, SoapEndpointReference.getSoapAddress(port));
  }

  public void testSelectPort_service() {
    QName portTypeName = new QName("urn:samples:ATMService", "atm");
    QName serviceName = new QName("urn:samples:ATMService", "atmService");
    
    SoapEndpointReference reference = getReference();
    reference.setPortTypeName(portTypeName);
    reference.setServiceName(serviceName);
    Port port = reference.selectPort(catalog);
    
    assertEquals(portTypeName, port.getBinding().getPortType().getQName());
    assertTrue(hasPort(serviceName, port));
  }
  
  public void testSelectPort_service_address() {
    QName portTypeName = new QName("http://example.com/translator", "textTranslator");
    QName serviceName = new QName("http://example.com/translator", "translatorServiceMirror");
    String address = "http://mirror.example.com/translator/text";
    
    SoapEndpointReference reference = getReference();
    reference.setPortTypeName(portTypeName);
    reference.setServiceName(serviceName);
    reference.setAddress(address);
    Port port = reference.selectPort(catalog);
    
    assertEquals(portTypeName, port.getBinding().getPortType().getQName());
    assertTrue(hasPort(serviceName, port));
    assertEquals(address, SoapEndpointReference.getSoapAddress(port));
  }
  
  public void testSelectPort_service_port() {
    QName portTypeName = new QName("http://example.com/translator", "documentTranslator");
    QName serviceName = new QName("http://example.com/translator", "translatorService");
    String portName = "documentTranslatorPort";
    
    SoapEndpointReference reference = getReference();
    reference.setPortTypeName(portTypeName);
    reference.setServiceName(serviceName);
    reference.setPortName(portName);
    Port port = reference.selectPort(catalog);
    
    assertEquals(portTypeName, port.getBinding().getPortType().getQName());
    assertTrue(hasPort(serviceName, port));
    assertEquals(portName, port.getName());
  }

  private boolean hasPort(QName serviceName, Port port) {
    Service service = catalog.lookupService(serviceName);
    return service.getPorts().containsKey(port.getName());
  }
  
  private static String getResource(String name) {
    return SoapEndpointReferenceTest.class.getResource(name).toString();
  }
}
