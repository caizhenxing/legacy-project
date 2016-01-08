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
package org.jbpm.bpel.integration.def;

import java.io.Serializable;
import java.util.Map;

import javax.wsdl.Operation;

import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantu
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class Invoker implements Serializable {

  long id;
  private PartnerLinkDefinition partnerLink;
  private Operation operation;
  private VariableDefinition inputVariable;
  private VariableDefinition outputVariable;
  private Correlations inCorrelations;
  private Correlations outCorrelations;

  private static final long serialVersionUID = 1L;

  public PartnerLinkDefinition getPartnerLink() {
    return partnerLink;
  }

  public void setPartnerLink(PartnerLinkDefinition partnerLink) {
    this.partnerLink = partnerLink;
  }

  public Operation getOperation() {
    return operation;
  }

  public void setOperation(Operation operation) {
    this.operation = operation;
  }

  public VariableDefinition getInputVariable() {
    return inputVariable;
  }

  public void setInputVariable(VariableDefinition inputVariable) {
    this.inputVariable = inputVariable;
  }

  public VariableDefinition getOutputVariable() {
    return outputVariable;
  }

  public void setOutputVariable(VariableDefinition outputVariable) {
    this.outputVariable = outputVariable;
  }

  public Correlations getInCorrelations() {
    return inCorrelations;
  }

  public void setInCorrelations(Correlations correlations) {
    this.inCorrelations = correlations;
  }

  public Correlations getOutCorrelations() {
    return outCorrelations;
  }

  public void setOutCorrelations(Correlations outCorrelations) {
    this.outCorrelations = outCorrelations;
  }

  public Map writeMessage(Token token) {
    // get the input variable instance
    MessageValue messageValue = (MessageValue) inputVariable.getValue(token);
    // ensure the *outgoing* correlation constraint
    if (outCorrelations != null)
      outCorrelations.ensureConstraint(messageValue, token);
    // extract the outgoing data
    return messageValue.getParts();
  }

  public void readMessage(Map outputParts, Token token) {
    MessageValue messageValue = (MessageValue) outputVariable.getValueForAssign(token);
    // save the incoming data
    messageValue.setParts(outputParts);
    // ensure the *incoming* correlation constraint
    if (inCorrelations != null)
      inCorrelations.ensureConstraint(messageValue, token);
  }
}