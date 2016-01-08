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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * The assign activity is used to copy data from one variable to another, as
 * well as to construct and insert new data using expressions.
 * @see "WS-BPEL 2.0 &sect;9.3"
 * @author Alejandro Guízar
 * @version $Revision: 1.5 $ $Date: 2006/08/22 04:13:10 $
 */
public class Assign extends Activity {

  static final Log log = LogFactory.getLog(Assign.class);

  private static final long serialVersionUID = 1L;

  private List copies;

  public Assign() {
    super();
  }

  public Assign(String name) {
    super(name);
  }

  public void execute(ExecutionContext context) {
    Token token = context.getToken();

    for (int i = 0, n = copies.size(); i < n; i++) {
      ((Copy) copies.get(i)).copyValue(token);
    }
    leave(context);
  }

  public List getCopies() {
    return copies;
  }

  public void setCopies(List copies) {
    this.copies = copies;
  }

  public static class Copy implements Serializable {

    long id;
    private From from;
    private To to;

    private static final long serialVersionUID = 1L;

    public From getFrom() {
      return from;
    }

    public void setFrom(From from) {
      this.from = from;
    }

    public To getTo() {
      return to;
    }

    public void setTo(To to) {
      this.to = to;
    }

    public void copyValue(Token token) {
      Object value = from.extract(token);
      log.debug("copying: token=" + token + ", value=" + value);
      to.assign(token, value);
    }
  }

  public static abstract class From implements Serializable {

    long id;

    public abstract Object extract(Token token);
  }

  public static abstract class To implements Serializable {

    long id;

    public abstract void assign(Token token, Object value);
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}
