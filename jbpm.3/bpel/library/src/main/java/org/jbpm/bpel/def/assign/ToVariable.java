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
package org.jbpm.bpel.def.assign;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.Assign.To;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class ToVariable extends To {

  private VariableDefinition variable;
  private String part;
  private Query query;

  private static final long serialVersionUID = 1L;

  public VariableDefinition getVariable() {
    return variable;
  }

  public void setVariable(VariableDefinition variable) {
    this.variable = variable;
  }

  public String getPart() {
    return part;
  }

  public void setPart(String part) {
    this.part = part;
  }

  public Query getQuery() {
    return query;
  }

  public void setQuery(Query query) {
    this.query = query;
  }

  public void assign(Token token, Object value) {
    if (part != null) {
      // get variable value in scope
      Object variableValue = variable.getValueForAssign(token);
      // prevent access to a non-existent part
      if (!(variableValue instanceof MessageValue)) {
        throw new BpelException("non-message variable does not have part: "
            + variable.getName());
      }
      MessageValue messageValue = (MessageValue) variableValue;
      if (query != null) {
        Element partValue = messageValue.getPartForAssign(part);
        query.getEvaluator().assign(partValue, value);
      }
      else {
        messageValue.setPart(part, value);
      }
    }
    else if (query != null) {
      // get variable value in scope
      Object variableValue = variable.getValueForAssign(token);
      // prevent direct query on a message variable
      if (variableValue instanceof MessageValue) {
        throw new BpelException("illegal query on message variable: "
            + variable.getName());
      }
      query.getEvaluator().assign((Element) variableValue, value);
    }
    else {
      // direct message assignment
      variable.setValue(token, value);
    }
  }
}
