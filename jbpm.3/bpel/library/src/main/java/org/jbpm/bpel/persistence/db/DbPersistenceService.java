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
package org.jbpm.bpel.persistence.db;

import org.jbpm.bpel.db.IntegrationSession;
import org.jbpm.bpel.db.ScopeSession;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class DbPersistenceService extends
    org.jbpm.persistence.db.DbPersistenceService {

  private IntegrationSession integrationSession;
  private ScopeSession scopeSession;

  private static final long serialVersionUID = 1L;

  public DbPersistenceService(DbPersistenceServiceFactory factory) {
    super(factory);
  }

  public IntegrationSession getIntegrationSession() {
    if (integrationSession == null) {
      integrationSession = new IntegrationSession(getSession());
    }
    return integrationSession;
  }

  public ScopeSession getScopeSession() {
    if (scopeSession == null) {
      scopeSession = new ScopeSession(getSession());
    }
    return scopeSession;
  }
}