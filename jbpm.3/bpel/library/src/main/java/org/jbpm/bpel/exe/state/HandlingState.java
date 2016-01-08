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
package org.jbpm.bpel.exe.state;

import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.db.ScopeSession;
import org.jbpm.bpel.def.ScopeHandler;
import org.jbpm.bpel.exe.ScopeInstance;

/**
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class HandlingState extends ScopeState {

  // private static Log handlerLog = LogFactory.getLog(HandlingState.class);
  protected HandlingState(String name, int code) {
    super(name, code);
  }

  protected void handleImplicitly(ScopeInstance scope) {
    // handlerLog.debug("handling implicitly");
    ScopeSession scopeSession = ScopeSession.getInstance(JbpmContext.getCurrentJbpmContext());
    ScopeInstance child = scopeSession.nextChildToCompensate(scope);
    if (child == null) {
      scope.getState().childrenCompensated(scope);
    }
    else {
      child.compensate(scope);
    }
  }

  protected void handleExplicitly(ScopeInstance scope, ScopeHandler handler) {
    // handlerLog.debug("handling explicitly");
    Token handlerToken = scope.createHandlerToken();
    handler.execute(new ExecutionContext(handlerToken));
  }

  protected void enterFaulted(ScopeInstance scope) {
    scope.setState(EndedState.FAULTED);
    // end the faulted line of execution
    scope.getToken().end();
    ScopeInstance parent = scope.getParent();

    if (parent != null) {
      scope.getParent().faulted(scope.getFaultInstance());
    }

    // handlerLog.debug("unable to handle fault" scope.getFault());
  }
}
