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
public class TerminatingState extends HandlingState {

  private static final long serialVersionUID = 1L;

  public static final TerminatingState TERMINATING_CHILDREN = new TerminatingState(
      "terminatingTerminatingChildren", 6) {

    private static final long serialVersionUID = 1L;

    public void faulted(ScopeInstance scope) {
      /*
       * A fault at this point is ignored, it comes from a faulted notification
       * send from a child before it was canceled.
       */
    }

    public void childrenTerminated(ScopeInstance scope) {
      ScopeHandler handler = scope.getDefinition()
          .getHandler(Scope.TERMINATION);

      if (handler != null) {
        scope.setState(TERMINATING_EXPLICITLY);
        handleExplicitly(scope, handler);
      }
      else {
        scope.setState(TERMINATING_IMPLICITLY);
        handleImplicitly(scope);
      }
    }
  };

  public static final TerminatingState TERMINATING_EXPLICITLY = new TerminatingState(
      "terminatingExplicitly", 7) {

    private static final long serialVersionUID = 1L;

    public void faulted(ScopeInstance scope) {
      scope.setState(TERMINATING_CHILDREN_AT_HANDLER);
      scope.cancelChildren();
    }

    public void completed(ScopeInstance scope) {
      enterTerminated(scope);
    }
  };

  public static final TerminatingState TERMINATING_IMPLICITLY = new TerminatingState(
      "terminatingImplicitly", 8) {

    private static final long serialVersionUID = 1L;

    public void faulted(ScopeInstance scope) {
      enterTerminated(scope);
    }

    public void childrenCompensated(ScopeInstance scope) {
      enterTerminated(scope);
    }
  };

  public static final TerminatingState TERMINATING_CHILDREN_AT_HANDLER = new TerminatingState(
      "terminatingTerminatingHandler", 9) {

    private static final long serialVersionUID = 1L;

    public void childrenTerminated(ScopeInstance scope) {
      enterTerminated(scope);
    }
  };

  protected TerminatingState(String name, int code) {
    super(name, code);
  }

  public static void enterTerminating(ScopeInstance scope) {
    scope.setState(TERMINATING_CHILDREN);
    scope.cancelChildren();
  }

  protected void enterTerminated(ScopeInstance scope) {
    scope.setState(EndedState.TERMINATED);
    ScopeInstance parent = scope.getParent();

    if (parent != null) {
      parent.scopeTerminated(scope);
    }
  }
}
