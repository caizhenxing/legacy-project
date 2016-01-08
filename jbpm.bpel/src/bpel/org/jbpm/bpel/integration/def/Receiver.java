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

import org.apache.commons.lang.builder.ToStringBuilder;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/09/13 07:56:57 $
 */
public class Receiver implements Serializable {

  private long id;
  private PartnerLinkDefinition partnerLink;
  private Operation operation;
  private VariableDefinition variable;
  private String messageExchange;
  private Correlations correlations;

  private InboundMessageActivity inboundMessageActivity;

  private static final long serialVersionUID = 1L;

  public Receiver() {
  }

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

  public String getMessageExchange() {
    return messageExchange;
  }

  public void setMessageExchange(String messageExchange) {
    this.messageExchange = messageExchange;
  }

  public VariableDefinition getVariable() {
    return variable;
  }

  public void setVariable(VariableDefinition variable) {
    this.variable = variable;
  }

  public Correlations getCorrelations() {
    return correlations;
  }

  public void setCorrelations(Correlations correlations) {
    this.correlations = correlations;
  }

  public InboundMessageActivity getInboundMessageActivity() {
    return inboundMessageActivity;
  }

  public void setInboundMessageActivity(
      InboundMessageActivity inboundMessageListener) {
    this.inboundMessageActivity = inboundMessageListener;
  }

  public void readMessage(Map parts, Token token) {
    MessageValue messageValue = (MessageValue) variable.getValueForAssign(token);
    // save the received parts
    messageValue.setParts(parts);
    // ensure the correlation constraint
    if (correlations != null)
      correlations.ensureConstraint(messageValue, token);
  }

  public long getId() {
    return id;
  }

  public String toString() {
    return new ToStringBuilder(this).append("partnerLink", partnerLink.getName())
        .append("operation", operation.getName())
        .append("messageExchange", messageExchange)
        .append("activity", inboundMessageActivity)
        .append("id", id)
        .toString();
  }

  public static IntegrationService getIntegrationService(JbpmContext jbpmContext) {
    return (IntegrationService) jbpmContext.getServices().getService(
        IntegrationService.SERVICE_NAME);
  }
}
