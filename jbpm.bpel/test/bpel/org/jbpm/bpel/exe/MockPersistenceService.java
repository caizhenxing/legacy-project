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
package org.jbpm.bpel.exe;

import java.util.Collection;

import org.jbpm.bpel.db.ScopeSession;
import org.jbpm.bpel.persistence.db.DbPersistenceService;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;
import org.jbpm.svc.Service;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:10 $
 */
public class MockPersistenceService extends DbPersistenceService {

  private static final long serialVersionUID = 1L;
  
  public MockPersistenceService(DbPersistenceServiceFactory factory) {
    super(factory);
  }

  /** {@inheritDoc} */
  public ScopeSession getScopeSession() {
    return new MockScopeSession();
  }

  public void close() {
    // nothing to do here
  }
  
  public static class Factory extends DbPersistenceServiceFactory {

    private static final long serialVersionUID = 1L;

    public Service openService() {
      return new MockPersistenceService(this);
    }
  }
  
  private static class MockScopeSession extends ScopeSession {
    
    protected ScopeInstance findNextScopeToCompensate(ProcessInstance processInstance, Collection nestedScopes) {
      return null;
    }
  }  
}