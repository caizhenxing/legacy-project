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

import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.exe.state.ActiveState;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantú
 * @version $Revision: 1.6 $ $Date: 2006/08/22 04:13:10 $
 */
public class EventInstance extends ScopeInstance {

  private static final long serialVersionUID = 1L;

  protected EventInstance() {
    super();
  }

  protected EventInstance(Scope scope, Token token) {
    super(scope, token);
  }

  public void notifyCompletion() {
    // all the children are completed.
    ScopeInstance parent = getParent();

    if (parent.getState().equals(ActiveState.EVENTS_PENDING)
        && !parent.hasPendingEvents()) {
      // notify completion to parent if its waiting for events
      parent.completed();
    }
  }

  public static ScopeInstance create(Scope scope, Token token) {
    EventInstance instance = new EventInstance(scope, token);
    // normal processing is initial state
    instance.setState(ActiveState.NORMAL_PROCESSING);
    token.getProcessInstance()
        .getContextInstance()
        .createVariable(Scope.VARIABLE_NAME, instance, token);
    return instance;
  }
}