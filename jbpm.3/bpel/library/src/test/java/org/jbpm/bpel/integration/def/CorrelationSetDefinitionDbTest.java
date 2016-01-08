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
package org.jbpm.bpel.integration.def;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.wsdl.impl.PropertyImpl;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class CorrelationSetDefinitionDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition;
  CorrelationSetDefinition csDefinition;
  
  public void setUp() throws Exception {
    super.setUp();
    processDefinition = new BpelDefinition();
    csDefinition = new CorrelationSetDefinition();
    csDefinition.setName("cs");
    processDefinition.getGlobalScope().addCorrelationSet(csDefinition);
  }
  
  public void testName() {
    processDefinition = saveAndReload(processDefinition); 
    assertEquals("cs", getCorrelationSetDefinition().getName());
  }
  
  public void testProperties() {
    Set properties = new HashSet();
    PropertyImpl property = new PropertyImpl();
    processDefinition.getImports().addProperty(property);
    property.setQName(new QName("aQName"));
    property.setType(new QName("aType"));
    
    properties.add(property);
    csDefinition.setProperties(properties);
    
    processDefinition = saveAndReload(processDefinition); 
    
    assertNotNull(getCorrelationSetDefinition().getProperties());
  }
  
  private CorrelationSetDefinition getCorrelationSetDefinition() {
    return processDefinition.getGlobalScope().getCorrelationSet("cs");
  }
}
