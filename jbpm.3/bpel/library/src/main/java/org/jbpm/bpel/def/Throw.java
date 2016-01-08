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

import javax.swing.text.Element;
import javax.xml.namespace.QName;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * The throw activity is used when a business process needs to signal an
 * internal fault explicitly.
 * @see "WS-BPEL 2.0 &sect;11.6"
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class Throw extends Activity {

  private static final long serialVersionUID = 1L;

  private QName faultName;
  private VariableDefinition faultVariable;

  public Throw() {
  }

  public Throw(String name) {
    super(name);
  }

  public void execute(ExecutionContext context) {
    BpelFaultException fault;

    if (faultVariable != null) {
      Object value = faultVariable.getValue(context.getToken());
      if (value instanceof MessageValue) {

      }
      else if (value instanceof Element) {

      }
      fault = new BpelFaultException(faultName);
    }
    else {
      fault = new BpelFaultException(faultName);
    }

    throw fault;
  }

  public QName getFaultName() {
    return faultName;
  }

  public void setFaultName(QName faultName) {
    this.faultName = faultName;
  }

  public VariableDefinition getFaultVariable() {
    return faultVariable;
  }

  public void setFaultVariable(VariableDefinition faultVariable) {
    this.faultVariable = faultVariable;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}
