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

import org.jbpm.bpel.def.Catch;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.ScopeHandler;
import org.jbpm.bpel.exe.FaultInstance;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantú
 * @version $Revision: 1.11 $ $Date: 2006/08/22 04:13:11 $
 */
public class FaultingState extends HandlingState {

  private static final long serialVersionUID = 1L;

  public static final FaultingState TERMINATING_CHILDREN = new FaultingState(
      "faultingTerminatingChildren", 2) {

    private static final long serialVersionUID = 1L;

    public void childrenTerminated(ScopeInstance scope) {
      FaultInstance faultInstance = scope.getFaultInstance();
      ScopeHandler handler = scope.getDefinition()
          .getFaultHandler(faultInstance);
      if (handler != null) {
        scope.setState(FAULTING_EXPLICITLY);
        handleExplicitly(scope, handler);
      }
      else {
        /*
         * TODO If the fault occurs in (or is rethrown to) the global process
         * scope, and there is no matching fault handler for the fault at the
         * global level, the process terminates abnormally, as though an exit
         * activity had been performed.
         */
        scope.setState(FAULTING_IMPLICITLY);
        handleImplicitly(scope);
      }
    }
  };

  public static final FaultingState FAULTING_IMPLICITLY = new FaultingState(
      "faultingImplicitly", 3) {

    private static final long serialVersionUID = 1L;

    public void childrenCompensated(ScopeInstance scope) {
      enterFaulted(scope);
    }

    public void faulted(ScopeInstance scope) {
      enterFaulted(scope);
    }
  };

  public static final FaultingState FAULTING_EXPLICITLY = new FaultingState(
      "faultingExplicitly", 4) {

    private static final long serialVersionUID = 1L;

    public void completed(ScopeInstance scope) {
      scope.setState(EndedState.COMPLETED_ABNORMALLY);
      ScopeInstance parent = scope.getParent();

      if (parent != null) {
        Token parentToken = scope.getToken().getParent();
        // ((Scope)parentToken.getNode()).completed(parentToken);
        parentToken.signal();
      }
    }

    public void faulted(ScopeInstance scope) {
      scope.setState(TERMINATING_CHILDREN_AT_HANDLER);
      scope.cancelChildren();
    }
  };

  public static final FaultingState TERMINATING_CHILDREN_AT_HANDLER = new FaultingState(
      "faultingTerminatingHandler", 5) {

    private static final long serialVersionUID = 1L;

    public void childrenTerminated(ScopeInstance scope) {
      enterFaulted(scope);
    }
  };

  protected FaultingState(String name, int code) {
    super(name, code);
  }

  public void terminate(ScopeInstance scope) {
    // nothing is done, see enterFaulting.
  }

  protected void handleExplicitly(ScopeInstance scope, ScopeHandler handler) {
    // initialize the fault variable before executing the fault handler
    if (handler instanceof Catch) {
      VariableDefinition faultVariable = ((Catch) handler).getFaultVariable();
      if (faultVariable != null) {
        // retrieve the thrown fault from the scope instance
        Token token = scope.getToken();
        FaultInstance faultInstance = Scope.getInstance(token)
            .getFaultInstance();
        // initialize the handler variable with thrown data
        Object data;
        MessageValue message = faultInstance.getMessageValue();
        if (message != null) {
          if (message.getType().equals(faultVariable.getType())) {
            // message data / message variable
            data = message;
          }
          else {
            // message data / element variable
            data = message.getParts().values().iterator().next();
          }
        }
        else {
          // element data / element variable
          data = faultInstance.getElementValue();
        }
        faultVariable.createInstance(token, data);
      }
    }
    super.handleExplicitly(scope, handler);
  }

  public static void enterFaulting(ScopeInstance scope) {
    scope.setState(FaultingState.TERMINATING_CHILDREN);
    scope.cancelChildren();
  }
}