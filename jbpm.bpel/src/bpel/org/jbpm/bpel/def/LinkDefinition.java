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

import java.io.Serializable;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.exe.LinkInstance;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * Links enable the expression of synchronization dependencies between
 * activities that are nested directly or indirectly within a flow activity.
 * @see "WS-BPEL 2.0 &sect;12.5.1"
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public class LinkDefinition implements Serializable {

  long id;
  private String name;
  protected Activity target;
  protected Activity source;
  private Expression transitionCondition;

  private static final long serialVersionUID = 1L;

  public LinkDefinition() {
  }

  public LinkDefinition(String name) {
    setName(name);
  }

  public void determineStatus(ExecutionContext executionContext) {
    Token token = executionContext.getToken();
    LinkInstance linkInstance = getInstance(token);

    if (transitionCondition == null) {
      linkInstance.statusDetermined(true);
    }
    else {
      try {
        // uses the source variable context to evaluate the transition condition
        linkInstance.statusDetermined(DatatypeUtil.toBoolean(transitionCondition.getEvaluator()
            .evaluate(token)));
      }
      catch (BpelFaultException f) {
        // set a negative link status before throwing the exception
        linkInstance.setStatus(Boolean.FALSE);
        throw f;
      }
    }
  }

  public Expression getTransitionCondition() {
    return transitionCondition;
  }

  public void setTransitionCondition(Expression transitionCondition) {
    this.transitionCondition = transitionCondition;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Activity getTarget() {
    return target;
  }

  public Activity getSource() {
    return source;
  }

  public LinkInstance getInstance(Token token) {
    ContextInstance ci = token.getProcessInstance().getContextInstance();
    return (LinkInstance) ci.getVariable(name, token);
  }

  public LinkInstance createInstance(Token token) {
    ContextInstance ci = token.getProcessInstance().getContextInstance();
    LinkInstance linkInstance = new LinkInstance(this);
    ci.createVariable(name, linkInstance, token);
    return linkInstance;
  }
}