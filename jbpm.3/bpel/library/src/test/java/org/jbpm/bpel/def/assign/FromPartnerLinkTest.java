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
package org.jbpm.bpel.def.assign;

import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import com.ibm.wsdl.PortTypeImpl;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.assign.FromPartnerLink.Reference;
import org.jbpm.bpel.exe.MockIntegrationService;
import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.bpel.integration.exe.wsa.WsaEndpointReference;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.PartnerLinkType.Role;
import org.jbpm.bpel.wsdl.impl.PartnerLinkTypeImpl;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import junit.framework.TestCase;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class FromPartnerLinkTest extends TestCase {
  
  private FromPartnerLink from = new FromPartnerLink();
  private Token token;
  private JbpmContext jbpmContext;
  
  private static JbpmConfiguration jbpmConfiguration = 
    JbpmConfiguration.parseResource("org/jbpm/bpel/exe/test.jbpm.cfg.xml");

  public FromPartnerLinkTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    /* the bpel definition uses the jbpm configuration, so create a context before the definition
     * to avoid loading another configuration from the default resource */    
    jbpmContext = jbpmConfiguration.createJbpmContext();
    // process and token
    BpelDefinition pd = new BpelDefinition();
    token = new ProcessInstance(pd).getRootToken();
    Scope scope = pd.getGlobalScope();
    // port type 1
    PortType portType1 = new PortTypeImpl();
    portType1.setQName(new QName("pt1"));
    // port type 2
    PortType portType2 = new PortTypeImpl();
    portType2.setQName(new QName("pt2"));
    // partner link type
    PartnerLinkType partnerLinkType = new PartnerLinkTypeImpl();
    partnerLinkType.setQName(new QName("plt"));
    // role 1
    Role role1 = partnerLinkType.createRole();
    role1.setName("r1");
    role1.setPortType(portType1);
    partnerLinkType.setFirstRole(role1);
    // role 2
    Role role2 = partnerLinkType.createRole();
    role2.setName("r2");
    role2.setPortType(portType2);
    partnerLinkType.setSecondRole(role2);
    // partner link
    PartnerLinkDefinition partnerLink = new PartnerLinkDefinition();
    partnerLink.setName("pl1");
    partnerLink.setPartnerLinkType(partnerLinkType);
    partnerLink.setMyRole(role1);
    partnerLink.setPartnerRole(role2);
    scope.addPartnerLink(partnerLink);
    // from
    from.setPartnerLink(partnerLink);
    // initialize scope
    scope.initScopeData(token);
    // provide a way to the partner link definition
    Empty activity = new Empty();
    scope.addNode(activity);
    token.setNode(activity);
  }

  protected void tearDown() throws Exception {
    jbpmContext.close();
  }

  public void testExtract_partnerRole() {
    // role ref
    from.setEndpointReference(Reference.PARTNER_ROLE);
    // endpoint ref
    EndpointReference endpointRef = new WsaEndpointReference();
    from.getPartnerLink().getInstance(token).setPartnerReference(endpointRef);
    // verify extraction
    assertSame(endpointRef, from.extract(token));
  }

  public void testExtract_myRole() {
    // role ref
    from.setEndpointReference(Reference.MY_ROLE);
    // endpoint ref
    EndpointReference endpointRef = new WsaEndpointReference();
    MockIntegrationService relationService = (MockIntegrationService) jbpmContext.getServices().getService(IntegrationService.SERVICE_NAME);
    relationService.setMyReference(from.getPartnerLink(), endpointRef);
    // verify extraction
    assertSame(endpointRef, from.extract(token));
  }
}
