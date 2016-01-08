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

import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.impl.PartnerLinkTypeImpl;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class PartnerLinkDefinitionDbTest extends AbstractDbTestCase {
  
  BpelDefinition process;
  PartnerLinkType partnerLinkType = new PartnerLinkTypeImpl();
  PartnerLinkDefinition partnerLink;
  
  static final QName TYPE_NAME = new QName(BpelConstants.NS_EXAMPLES, "plt");
  static final String PARTNER_LINK_NAME = "pl";
  
  public void setUp() throws Exception {
    super.setUp();
    // process, create after opening jbpm context
    process = new BpelDefinition();
    // partner link type
    partnerLinkType.setQName(TYPE_NAME);
    process.getImports().addPartnerLinkType(partnerLinkType);
    // partner link
    partnerLink = new PartnerLinkDefinition();
    partnerLink.setName(PARTNER_LINK_NAME);
    partnerLink.setPartnerLinkType(partnerLinkType);
    process.getGlobalScope().addPartnerLink(partnerLink);
  }
  
  public void testName() {
    process = saveAndReload(process); 
    assertEquals(PARTNER_LINK_NAME, getPartnerLinkDefinition().getName());
  }
  
  public void testPartnerLinkType() {
    process = saveAndReload(process); 
    assertEquals(TYPE_NAME, getPartnerLinkDefinition().getPartnerLinkType().getQName());
  }  
  
  public void testPartnerRole() {
    // role
    PartnerLinkType.Role partnerRole = new PartnerLinkTypeImpl.RoleImpl();
    partnerRole.setName("partner");
    // partner link type
    partnerLinkType.setSecondRole(partnerRole);
    // partner link
    partnerLink.setPartnerRole(partnerRole);
    
    process = saveAndReload(process); 
    
    assertEquals("partner", getPartnerLinkDefinition().getPartnerRole().getName());
  }
  
  public void testMyRole() {
    // role
    PartnerLinkType.Role myRole = new PartnerLinkTypeImpl.RoleImpl();
    myRole.setName("myself");
    // partner link type
    partnerLinkType.setSecondRole(myRole);
    // partner link
    partnerLink.setMyRole(myRole);
    
    process = saveAndReload(process); 
    
    assertEquals("myself", getPartnerLinkDefinition().getMyRole().getName());
  }  
  
  private PartnerLinkDefinition getPartnerLinkDefinition() {
    return process.getGlobalScope().getPartnerLink(PARTNER_LINK_NAME);
  }
}
