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
package org.jbpm.bpel.wsdl.impl;

import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.wsdl.Property;

/**
 * @author Juan Cantu
 * @version $Revision: 1.6 $ $Date: 2006/08/22 04:13:11 $
 */
public class PropertyImplDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition;
  PropertyImpl property;
  
  public void setUp() throws Exception {
    super.setUp();
    processDefinition = new BpelDefinition();
    property = new PropertyImpl();
    property.setQName(new QName("aQName"));
    property.setElementType(new QName("aElementType"));
    processDefinition.getImports().addProperty(property);
  }
  
  public void testQName() {
    processDefinition = saveAndReload(processDefinition); 
    
    assertNotNull( getProperty().getQName() );
  }
  
  public void testElementType() {
    processDefinition = saveAndReload(processDefinition); 
    
    assertNotNull( getProperty().getElementType() );
  }
  
  public void testType() {
    property.setType(new QName("aType"));
    
    processDefinition = saveAndReload(processDefinition); 
    
    assertNotNull( getProperty().getType() );
  }
  
  private Property getProperty() {
    return processDefinition.getImports().getProperty(new QName("aQName"));
  }
}
