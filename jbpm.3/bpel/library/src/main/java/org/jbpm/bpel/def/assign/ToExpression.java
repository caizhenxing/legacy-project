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

import org.jbpm.bpel.def.Assign.To;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ToExpression extends To {

  private static final long serialVersionUID = 1L;
  private Expression expression;

  /** {@inheritDoc} */
  public void assign(Token token, Object value) {
    expression.getEvaluator().assign(token, value);
  }

  public Expression getExpression() {
    return expression;
  }

  public void setExpression(Expression query) {
    this.expression = query;
  }
}
