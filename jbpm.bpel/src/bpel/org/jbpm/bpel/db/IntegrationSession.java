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
package org.jbpm.bpel.db;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.exe.PartnerLinkInstance;
import org.jbpm.bpel.persistence.db.DbPersistenceService;
import org.jbpm.persistence.PersistenceService;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:16 $
 */
public class IntegrationSession {

  private Session session;

  public IntegrationSession(Session session) {
    this.session = session;
  }

  protected IntegrationSession() {
  }

  public PartnerLinkDefinition loadPartnerLinkDefinition(long id) {
    return (PartnerLinkDefinition) session.load(PartnerLinkDefinition.class,
        new Long(id));
  }

  public PartnerLinkInstance loadPartnerLinkInstance(long id) {
    return (PartnerLinkInstance) session.load(PartnerLinkInstance.class,
        new Long(id));
  }

  public Receiver loadReceiver(long id) {
    return (Receiver) session.load(Receiver.class, new Long(id));
  }

  public Collection findReceiveTokens(BpelDefinition processDefinition) {
    return session.getNamedQuery("IntegrationSession.findReceiveTokens")
        .setEntity("processDefinition", processDefinition)
        .list();
  }

  public Collection findPickTokens(BpelDefinition processDefinition) {
    List tokens = session.getNamedQuery("IntegrationSession.findPickTokens")
        .setEntity("processDefinition", processDefinition)
        .list();
    // discard duplicates caused by eager collection fetching
    return new HashSet(tokens);
  }

  public Collection findEventTokens(BpelDefinition processDefinition) {
    List tokens = session.getNamedQuery("IntegrationSession.findEventTokens")
        .setEntity("processDefinition", processDefinition)
        .list();
    // discard duplicates caused by eager collection fetching
    return new HashSet(tokens);
  }

  public static IntegrationSession getInstance(JbpmContext jbpmContext) {
    IntegrationSession integrationSession = null;
    PersistenceService persistenceService = jbpmContext.getServices()
        .getPersistenceService();
    if (persistenceService instanceof DbPersistenceService) {
      integrationSession = ((DbPersistenceService) persistenceService).getIntegrationSession();
    }
    return integrationSession;
  }
}
