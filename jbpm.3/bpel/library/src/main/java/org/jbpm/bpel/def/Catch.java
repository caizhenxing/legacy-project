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

import javax.xml.namespace.QName;

import org.jbpm.bpel.exe.FaultInstance;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantú
 * @version $Revision: 1.5 $ $Date: 2006/08/21 01:05:59 $
 */
public class Catch extends ScopeHandler {

  private QName faultName;
  private VariableDefinition faultVariable;

  private static final long serialVersionUID = 1L;

  public void execute(ExecutionContext exeContext) {
    if (faultVariable != null) {
      Token token = exeContext.getToken();
      if (faultVariable != getCompositeActivity().findVariable(faultVariable.getName())) {
        // initialize local variable
        initFaultVariable(token);
      }
      else {
        // set variable in enclosing scope
        setFaultVariable(token);
      }
    }
    super.execute(exeContext);
  }

  // CompositeActivity override
  // //////////////////////////////////////////////////////////

  public VariableDefinition findVariable(String varName) {
    return faultVariable != null && faultVariable.getName().equals(varName) ? faultVariable
        : super.findVariable(varName);
  }

  protected void initFaultVariable(Token token) {
    // retrieve thrown fault from scope instance
    FaultInstance faultInstance = Scope.getInstance(token).getFaultInstance();
    MessageValue messageData = faultInstance.getMessageValue();
    // initialize fault variable with fault data
    Object faultData;
    if (messageData != null) {
      if (messageData.getType().equals(faultVariable.getType())) {
        // message data / message variable
        faultData = messageData;
      }
      else {
        // message data / element variable
        faultData = messageData.getParts().values().iterator().next();
      }
    }
    else {
      // element data / element variable
      faultData = faultInstance.getElementValue();
    }
    faultVariable.createInstance(token, faultData);
  }

  protected void setFaultVariable(Token token) {
    FaultInstance faultInstance = Scope.getInstance(token).getFaultInstance();
    faultVariable.setValue(token, faultInstance.getMessageValue());
  }

  // fault handler properties
  // ////////////////////////////////////////////////////////////

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
}