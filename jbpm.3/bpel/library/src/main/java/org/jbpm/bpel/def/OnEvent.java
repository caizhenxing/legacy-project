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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.bpel.exe.EventInstance;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.bpel.exe.state.ActiveState;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.InboundMessageActivity;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantu
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class OnEvent extends ScopeHandler implements InboundMessageActivity {

  private Receiver receiver;
  private VariableDefinition variable;
  private Map correlationSets;

  private static final Log log = LogFactory.getLog(OnEvent.class);
  private static final long serialVersionUID = 1L;

  public void messageReceived(Receiver receiver, Token token) {
    ScopeInstance scopeInstance = Scope.getInstance(token);
    if (!scopeInstance.getState().equals(ActiveState.NORMAL_PROCESSING)) {
      log.debug("message refused, scope no longer in normal processing state: "
          + "token=" + token + ", scope=" + scopeInstance);
      return;
    }
    Token child = scopeInstance.createEventToken();
    // create the event instance
    EventInstance.create(scope, child);
    // if this handler has a local variable definition, create the instance
    if (variable != null) {
      variable.createInstance(token);
    }
    // if this handler has local correlation sets, instantiate them
    if (correlationSets != null) {
      Iterator correlationSetIt = correlationSets.values().iterator();
      while (correlationSetIt.hasNext()) {
        ((CorrelationSetDefinition) correlationSetIt.next()).createInstance(token);
      }
    }
    // execute the event activity
    execute(new ExecutionContext(child));
  }

  // CompositeActivity override
  // //////////////////////////////////////////////////////////////

  /** {@inheritDoc} */
  public VariableDefinition findVariable(String varName) {
    return variable != null && variable.getName().equals(varName) ? variable
        : super.findVariable(varName);
  }

  /** {@inheritDoc} */
  public CorrelationSetDefinition findCorrelationSet(String csName) {
    CorrelationSetDefinition cs = getCorrelationSet(csName);
    return cs != null ? cs : super.findCorrelationSet(csName);
  }

  // message handler properties
  // /////////////////////////////////////////////////////////////

  public void addCorrelationSet(CorrelationSetDefinition correlation) {
    if (correlationSets == null) {
      correlationSets = new HashMap();
    }
    correlationSets.put(correlation.getName(), correlation);
  }

  /**
   * Gets the correlation set with the given name
   * @param correlationSetName the correlation set name
   * @return a correlation set whose name matches the argument
   */
  public CorrelationSetDefinition getCorrelationSet(String correlationSetName) {
    return correlationSets != null ? (CorrelationSetDefinition) correlationSets.get(correlationSetName)
        : null;
  }

  public void setCorrelationSets(Map correlationSets) {
    this.correlationSets = correlationSets;
  }

  public Receiver getReceiver() {
    return receiver;
  }

  public void setReceiver(Receiver receiver) {
    this.receiver = receiver;
    receiver.setInboundMessageActivity(this);
  }

  public VariableDefinition getVariableDefinition() {
    return variable;
  }

  public void setVariableDefinition(VariableDefinition variable) {
    this.variable = variable;
  }
}
