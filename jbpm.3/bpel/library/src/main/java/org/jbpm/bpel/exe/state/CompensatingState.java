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

import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.ScopeHandler;
import org.jbpm.bpel.exe.ScopeInstance;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class CompensatingState extends HandlingState {

  private static final long serialVersionUID = 1L;

  public static final CompensatingState COMPENSATING_IMPLICITLY = new CompensatingState(
      "compensatingImplicitly", 10) {

    private static final long serialVersionUID = 1L;

    public void faulted(ScopeInstance scope) {
      enterFaulted(scope);
    }

    public void childrenCompensated(ScopeInstance scope) {
      enterCompensated(scope);
    }
  };

  public static final CompensatingState COMPENSATING_EXPLICITLY = new CompensatingState(
      "compensatingExplicitly", 11) {

    private static final long serialVersionUID = 1L;

    public void completed(ScopeInstance scope) {
      enterCompensated(scope);
    }

    public void faulted(ScopeInstance scope) {
      scope.setState(TERMINATING_CHILDREN_AT_HANDLER);
      scope.cancelChildren();
    }
  };

  public static final CompensatingState TERMINATING_CHILDREN_AT_HANDLER = new CompensatingState(
      "compensatingTerminatingHandler", 12) {

    private static final long serialVersionUID = 1L;

    public void childrenTerminated(ScopeInstance scope) {
      enterFaulted(scope);
    }
  };

  protected CompensatingState(String name, int code) {
    super(name, code);
  }

  public static void enterCompensating(ScopeInstance scope) {
    ScopeHandler handler = scope.getDefinition().getHandler(Scope.COMPENSATION);

    if (handler != null) {
      scope.setState(COMPENSATING_EXPLICITLY);
      COMPENSATING_EXPLICITLY.handleExplicitly(scope, handler);
    }
    else {
      scope.setState(COMPENSATING_IMPLICITLY);
      COMPENSATING_IMPLICITLY.handleImplicitly(scope);
    }
  }

  protected void enterCompensated(ScopeInstance scope) {
    scope.setState(EndedState.COMPENSATED);
    scope.getCompensationListener().scopeCompensated(scope);
  }
}
