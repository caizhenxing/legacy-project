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
package org.jbpm.bpel.def;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.db.ScopeSession;
import org.jbpm.bpel.exe.CompensationListener;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * The <compensate> activity is used to invoke compensation on an inner scope
 * that has already completed normally. This construct can be invoked only from
 * within a fault handler or another compensation handler.
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class Compensate extends Activity implements CompensationListener {

  private static final long serialVersionUID = 1L;

  private Scope scope;

  public Compensate() {
  }

  public Compensate(String name) {
    super(name);
  }

  public void execute(ExecutionContext exeContext) {
    if (scope == null) {
      ScopeInstance scopeInstance = Scope.getInstance(exeContext.getToken());
      scopeInstance.compensate(this);
      return;
    }

    ScopeSession scopeSession = ScopeSession.getInstance(exeContext.getJbpmContext());
    ScopeInstance scopeInstance = scopeSession.nextScopeToCompensate(exeContext.getProcessInstance(),
        scope);
    if (scopeInstance != null) {
      scopeInstance.compensate(this);
    }
    else {
      leave(exeContext);
    }
  }

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }

  public void scopeCompensated(ScopeInstance scopeInstance) {
    if (scope != null) {
      ScopeSession scopeSession = ScopeSession.getInstance(JbpmContext.getCurrentJbpmContext());
      ScopeInstance nextInstance = scopeSession.nextScopeToCompensate(scopeInstance.getToken()
          .getProcessInstance(),
          scope);
      if (nextInstance != null) {
        // If a completed scope instance is found, perform its compensation
        nextInstance.compensate(this);
        return;
      }
    }
    // continue the execution of the compensation handler
    Token compensateToken = (Token) scopeInstance.getHandlerToken()
        .getChildrenAtNode(this)
        .get(0);
    leave(new ExecutionContext(compensateToken));
  }
}