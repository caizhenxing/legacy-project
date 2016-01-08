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
package org.jbpm.bpel.db.type;

import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.wsdl.impl.PropertyImpl;
import org.jbpm.bpel.xml.BpelConstants;

public class QNameTypeDbTest extends AbstractDbTestCase {
  
  BpelDefinition process;
  
  static final QName PROPERTY_NAME = new QName(BpelConstants.NS_EXAMPLES, "p");
  
  public void setUp() throws Exception {
    super.setUp();
    // process, create after opening jbpm context
    process = new BpelDefinition();
  }

  public void testLocalPart() {
    QName qname = new QName(null, "someLocalName");
    setQName(qname);

    process = saveAndReload(process);

    assertEquals("someLocalName", getQName().getLocalPart());
  }
  
  public void testNamespaceURI() {
    QName qname = new QName("aNamespace", "someLocalName");
    setQName(qname);

    process = saveAndReload(process);

    assertEquals("aNamespace", getQName().getNamespaceURI());
    assertEquals("someLocalName", getQName().getLocalPart());    
  }
  
  public void testNullQName() {
    setQName(null);
    
    process = saveAndReload(process);

    assertNull(getQName());
  }
  
  private void setQName(QName qname) {
    PropertyImpl property = new PropertyImpl();
    property.setQName(PROPERTY_NAME);
    property.setType(qname);
    process.getImports().addProperty(property);
  }
  
  private QName getQName() {
    return process.getImports().getProperty(PROPERTY_NAME).getType();
  }
  
}
