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

import org.jbpm.bpel.def.Assign.From;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:11 $
 */
public class FromVariable extends From {

  private VariableDefinition variable;
  private String part;
  private Query query;

  private static final long serialVersionUID = 1L;

  /** {@inheritDoc} */
  public Object extract(Token token) {
    // variable
    Object value = variable.getValue(token);
    // part
    if (part != null) {
      // prevent access to a non-existent part
      if (!(value instanceof MessageValue)) {
        throw new BpelException("non-message variable does not have part: "
            + variable.getName());
      }
      value = ((MessageValue) value).getPart(part);
    }
    // query
    if (query != null) {
      // prevent direct query on a message variable
      if (value instanceof MessageValue) {
        throw new BpelException("illegal query on message variable: "
            + variable.getName());
      }
      value = query.getEvaluator().evaluate((Element) value);
    }
    return value;
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

  public VariableDefinition getVariable() {
    return variable;
  }

  public void setVariable(VariableDefinition variable) {
    this.variable = variable;
  }
}
