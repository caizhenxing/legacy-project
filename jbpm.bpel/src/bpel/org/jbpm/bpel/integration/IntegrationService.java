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
package org.jbpm.bpel.integration;

import java.util.List;

import org.jbpm.bpel.integration.def.Invoker;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.def.Replier;
import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.graph.exe.Token;
import org.jbpm.svc.Service;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public interface IntegrationService extends Service {

  public static final String SERVICE_NAME = "integration";

  public void receive(Receiver receiver, Token token);

  public void receive(List receivers, Token token);

  public void endReception(Receiver receiver, Token token);

  public void endReception(List receivers, Token token);

  public void reply(Replier replier, Token token);

  public void invoke(Invoker invoker, Token token);

  public EndpointReference getMyReference(PartnerLinkDefinition partnerLink);
}