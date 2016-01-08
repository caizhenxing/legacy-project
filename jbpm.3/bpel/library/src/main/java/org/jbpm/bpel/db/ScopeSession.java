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

import org.hibernate.Session;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.bpel.persistence.db.DbPersistenceService;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.persistence.PersistenceService;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class ScopeSession {

  private Session session;

  public ScopeSession(Session session) {
    this.session = session;
  }

  protected ScopeSession() {
  }

  public ScopeInstance nextChildToCompensate(ScopeInstance instance) {
    Collection nestedScopes = instance.getDefinition().getNestedScopes();
    return nestedScopes.isEmpty() ? null
        : findNextScopeToCompensate(instance.getToken().getProcessInstance(),
            nestedScopes);
  }

  public ScopeInstance nextScopeToCompensate(ProcessInstance processInstance,
      Scope scope) {
    Collection nestedScopes = scope.getNestedScopes();
    nestedScopes.add(scope);
    return findNextScopeToCompensate(processInstance, nestedScopes);
  }

  protected ScopeInstance findNextScopeToCompensate(
      ProcessInstance processInstance, Collection nestedScopes) {
    return (ScopeInstance) session.getNamedQuery("ScopeSession.findNextScopeToCompensate")
        .setParameter("processInstance", processInstance)
        .setParameterList("nestedScopes", nestedScopes)
        .setMaxResults(1)
        .uniqueResult();
  }

  public static ScopeSession getInstance(JbpmContext jbpmContext) {
    ScopeSession scopeSession = null;
    PersistenceService persistenceService = jbpmContext.getServices()
        .getPersistenceService();
    if (persistenceService instanceof DbPersistenceService) {
      scopeSession = ((DbPersistenceService) persistenceService).getScopeSession();
    }
    return scopeSession;
  }
}