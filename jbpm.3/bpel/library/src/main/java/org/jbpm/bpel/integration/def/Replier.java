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
import javax.xml.namespace.QName;

import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class Replier implements Serializable {

  private static final long serialVersionUID = 1L;

  long id;
  private PartnerLinkDefinition partnerLink;
  private Operation operation;
  private VariableDefinition variable;
  private QName faultName;
  private String messageExchange;
  private Correlations correlations;

  public PartnerLinkDefinition getPartnerLink() {
    return partnerLink;
  }

  public void setPartnerLink(PartnerLinkDefinition partnerLink) {
    this.partnerLink = partnerLink;
  }

  public Operation getOperation() {
    return operation;
  }

  public void setOperation(Operation operationName) {
    this.operation = operationName;
  }

  public VariableDefinition getVariable() {
    return variable;
  }

  public void setVariable(VariableDefinition variable) {
    this.variable = variable;
  }

  public QName getFaultName() {
    return faultName;
  }

  public void setFaultName(QName faultName) {
    this.faultName = faultName;
  }

  public String getMessageExchange() {
    return messageExchange;
  }

  public void setMessageExchange(String messageExchange) {
    this.messageExchange = messageExchange;
  }

  public Correlations getCorrelations() {
    return correlations;
  }

  public void setCorrelations(Correlations correlations) {
    this.correlations = correlations;
  }

  public Map writeMessage(Token token) {
    MessageValue messageValue = (MessageValue) variable.getValue(token);
    // ensure the correlation constraint
    if (correlations != null)
      correlations.ensureConstraint(messageValue, token);
    // extract the message to reply with
    return messageValue.getParts();
  }
}
