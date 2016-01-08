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
package org.jbpm.bpel.integration.server;

import java.util.Collections;

import org.hibernate.LockMode;

import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.exe.CorrelationSetInstance;
import org.jbpm.bpel.integration.jms.JmsIntegrationService;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class RequestListenerTest extends AbstractListenerTest {
  
  private Token token;

  public void setUp() throws Exception {
    super.setUp();
    // create process instance
    ProcessInstance pi = process.createProcessInstance();
    // commit changes
    jbpmContext.save(pi);
    newTransaction();
    // reassociate the process instance with the new session
    jbpmContext.getSession().lock(pi, LockMode.READ);
    token = pi.getRootToken();
    token.setNode((Node) receiver.getInboundMessageActivity());
    // initiate correlation set
    CorrelationSetInstance csi = receiver.getCorrelations().getCorrelation("csId").getSet().getInstance(token);
    csi.initialize(Collections.singletonMap(ID_PROP, ID_VALUE));
  }

  protected void openListener() throws Exception {
    JmsIntegrationService integrationService = 
      (JmsIntegrationService) jbpmContext.getServices().getService(IntegrationService.SERVICE_NAME);
    integrationService.receive(receiver, token);
    integrationService.openRequestListeners();
  }

  protected void closeListener() throws Exception {
    Receiver.getIntegrationService(jbpmContext).endReception(receiver, token);
  }
}
