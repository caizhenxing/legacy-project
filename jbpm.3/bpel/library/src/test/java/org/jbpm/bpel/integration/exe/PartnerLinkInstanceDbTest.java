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

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.bpel.integration.exe.PartnerLinkInstance;
import org.jbpm.bpel.integration.exe.wsa.WsaEndpointReference;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.impl.PartnerLinkTypeImpl;
import org.jbpm.bpel.wsdl.impl.PortTypeImpl;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

public class PartnerLinkInstanceDbTest extends AbstractDbTestCase {
  
  public void testDefinition() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    // partner link definition
    PartnerLinkDefinition plinkDefinition = new PartnerLinkDefinition();
    plinkDefinition.setName("plink");
    processDefinition.getGlobalScope().addPartnerLink(plinkDefinition);
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    Token token = processInstance.getRootToken();
    // partner link instance
    PartnerLinkInstance plinkInstance = plinkDefinition.createInstance(token);
    
    processInstance = saveAndReload(processInstance);

    plinkDefinition = 
      ((BpelDefinition) processInstance.getProcessDefinition()).getGlobalScope().getPartnerLink("plink");
    plinkInstance = plinkDefinition.getInstance(processInstance.getRootToken());
    assertEquals(plinkDefinition, plinkInstance.getDefinition());
  }  
  
  public void testEndpointReference() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    // partner link definition
    PartnerLinkType plinkType = new PartnerLinkTypeImpl();
    PartnerLinkTypeImpl.RoleImpl role = new PartnerLinkTypeImpl.RoleImpl();
    plinkType.setFirstRole(role);   
    PortTypeImpl portType = new PortTypeImpl();
    role.setPortType(portType);
    processDefinition.getImports().addPartnerLinkType(plinkType);
    PartnerLinkDefinition plinkDefinition = new PartnerLinkDefinition();
    plinkDefinition.setName("plink");
    plinkDefinition.setPartnerLinkType(plinkType);
    plinkDefinition.setPartnerRole(role);
    processDefinition.getGlobalScope().addPartnerLink(plinkDefinition);
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    Token token = processInstance.getRootToken();
    // partner link instance
    PartnerLinkInstance plinkInstance = plinkDefinition.createInstance(token);
    EndpointReference endpointRef = new WsaEndpointReference();
    endpointRef.setScheme("someReferenceScheme");
    QName portTypeName = plinkDefinition.getPartnerRole().getPortType().getQName();
    endpointRef.setPortTypeName(portTypeName);
    plinkInstance.setPartnerReference(endpointRef);
    
    processInstance = saveAndReload(processInstance);

    plinkDefinition = 
      ((BpelDefinition) processInstance.getProcessDefinition()).getGlobalScope().getPartnerLink("plink");
    plinkInstance = plinkDefinition.getInstance(processInstance.getRootToken());
    endpointRef = plinkInstance.getPartnerReference();
    assertNotNull(endpointRef);
    assertEquals("someReferenceScheme", endpointRef.getScheme());
    assertSame(portTypeName, endpointRef.getPortTypeName());
    //TODO test endpointReference
  }    
  
}
