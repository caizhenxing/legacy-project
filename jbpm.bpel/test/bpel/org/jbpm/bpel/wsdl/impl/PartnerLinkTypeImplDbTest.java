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
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.impl.PartnerLinkTypeImpl.RoleImpl;

/**
 * @author Juan Cantu
 * @version $Revision: 1.6 $ $Date: 2006/08/22 04:13:11 $
 */
public class PartnerLinkTypeImplDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition;
  PartnerLinkTypeImpl plinkType;
  QName plinkTypeName = new QName("rfqPLT");
  
  public void setUp() throws Exception {
    super.setUp();
    processDefinition = new BpelDefinition();
    plinkType = new PartnerLinkTypeImpl();
    plinkType.setQName(plinkTypeName);
    processDefinition.getImports().addPartnerLinkType(plinkType);
  }
  
  public void testQName() {
    processDefinition = saveAndReload(processDefinition); 
    
    assertNotNull( getPartnerLinkType().getQName());
  }

  public void testFirstRole() {
    PartnerLinkTypeImpl.RoleImpl role = new PartnerLinkTypeImpl.RoleImpl();
    plinkType.setFirstRole(role);    
    role.setName("first");
    
    processDefinition = saveAndReload(processDefinition); 
    
    assertEquals("first", getPartnerLinkType().getFirstRole().getName());
  }

  public void testSecondRole() {
    PartnerLinkTypeImpl.RoleImpl role = new PartnerLinkTypeImpl.RoleImpl();
    role.setName("second");
    plinkType.setSecondRole(role);    
    
    processDefinition = saveAndReload(processDefinition); 
    
    role = (RoleImpl) getPartnerLinkType().getSecondRole();
    assertEquals("second", role.getName());
  }
  
  private PartnerLinkType getPartnerLinkType() {
    return processDefinition.getImports().getPartnerLinkType(plinkTypeName);
  }
}
