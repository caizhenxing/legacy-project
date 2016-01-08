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

import javax.xml.namespace.QName;

import org.jbpm.bpel.integration.exe.SoapEndpointReference;


public abstract class SoapEndpointReferenceDbTest extends EndpointReferenceDbTest {

  public void testAddress() {
    SoapEndpointReference reference = (SoapEndpointReference) createReference();
    String address = "http://example.com/pizzaShop/pizzas";
    reference.setAddress(address);
    reference = (SoapEndpointReference) saveAndReload(reference);
    
    assertEquals(address, reference.getAddress());
  }

  public void testServiceName() {
    SoapEndpointReference reference = (SoapEndpointReference) createReference();
    QName serviceName = new QName("urn:pizzas:srv", "pizzaService");
    reference.setServiceName(serviceName);
    reference = (SoapEndpointReference) saveAndReload(reference);
    
    assertEquals(serviceName, reference.getServiceName());
  }

  public void testPortName() {
    SoapEndpointReference reference = (SoapEndpointReference) createReference();
    String portName = "pizzaPort";
    reference.setPortName(portName);
    reference = (SoapEndpointReference) saveAndReload(reference);
    
    assertEquals(portName, reference.getPortName());
  }
}
