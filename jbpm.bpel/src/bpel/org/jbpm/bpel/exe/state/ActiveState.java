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

import org.jbpm.bpel.exe.ScopeInstance;

/**
 * @author Juan Cantú
 * @version $Revision: 1.8 $ $Date: 2006/08/22 04:13:11 $
 */
public abstract class ActiveState extends ScopeState {

  public static final ActiveState NORMAL_PROCESSING = new ActiveState(
      "normalProcessing", 0) {

    private static final long serialVersionUID = 1L;

    public void completed(ScopeInstance scope) {
      scope.disableEvents();
      if (!scope.hasPendingEvents()) {
        // set completed if no events left
        enterCompleted(scope);
      }
      else {
        // otherwise set a events pending state and wait for their completion
        scope.setState(EVENTS_PENDING);
      }
    }
  };

  public static final ActiveState EVENTS_PENDING = new ActiveState(
      "eventsPending", 1) {

    private static final long serialVersionUID = 1L;

    public void completed(ScopeInstance scope) {
      // assert scope.getToken().hasEnded() : "no tokens should remain " +
      // "when a context with pending events completes";
      enterCompleted(scope);
    }
  };

  /**
   * Constructs an active state identified by the given name.
   * @param name
   */
  protected ActiveState(String name, int code) {
    super(name, code);
  }

  public void terminate(ScopeInstance scope) {
    TerminatingState.enterTerminating(scope);
  }

  public void faulted(ScopeInstance scope) {
    FaultingState.enterFaulting(scope);
  }

  protected void enterCompleted(ScopeInstance scope) {
    // set completed state
    scope.setState(EndedState.COMPLETED);
    scope.notifyCompletion();
  }
}
