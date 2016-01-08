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

import java.io.Serializable;

import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.integration.exe.EndpointReference;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/12 06:48:12 $
 */
public abstract class EndpointReferenceDbTestCase extends AbstractDbTestCase {
  
  public void testScheme() {
    EndpointReference reference = createReference();
    String scheme = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
    reference.setScheme(scheme);
    reference = saveAndReload(reference);
    
    assertEquals(scheme, reference.getScheme());
  }

  public void testPortTypeName() {
    EndpointReference reference = createReference();
    QName portTypeName = new QName("urn:pizzas:pt", "pizzasPT");
    reference.setPortTypeName(portTypeName);
    reference = saveAndReload(reference);
    
    assertEquals(portTypeName, reference.getPortTypeName());
  }
  
  protected abstract EndpointReference createReference();
  
  protected EndpointReference saveAndReload(EndpointReference reference) {
    Serializable id = session.save(reference);
    newTransaction();
    return (EndpointReference) session.load(reference.getClass(), id);
  }
}
