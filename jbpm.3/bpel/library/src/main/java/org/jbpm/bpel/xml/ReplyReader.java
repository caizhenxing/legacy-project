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
package org.jbpm.bpel.xml;

import javax.wsdl.Fault;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.Reply;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Replier;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.PartnerLinkType.Role;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Juan Cantú
 * @version $Revision: 1.5 $ $Date: 2006/08/21 01:05:59 $
 */
public class ReplyReader extends ActivityReader {

  public Activity createActivity() {
    return new Reply();
  }

  public void readSpecificProperties(Element activityElem, Activity activity) {
    Reply reply = (Reply) activity;
    reply.setReplier(readReplier(activityElem, reply.getCompositeActivity()));
  }

  public Replier readReplier(Element replierElem, CompositeActivity parent) {
    Replier replier = new Replier();
    // partner link
    String partnerLinkName = replierElem.getAttribute(BpelConstants.ATTR_PARTNER_LINK);
    PartnerLinkDefinition partnerLink = parent.findPartnerLink(partnerLinkName);
    if (partnerLink == null) {
      bpelReader.getProblemHandler().add(new ParseProblem(
          "partner link not found", replierElem));
      return replier;
    }
    replier.setPartnerLink(partnerLink);
    // port type
    Role myRole = partnerLink.getMyRole();
    // BPEL-181 detect absence of my role
    if (myRole == null) {
      bpelReader.getProblemHandler().add(new ParseProblem(
          "partner link does not indicate my role", replierElem));
      return replier;
    }
    PortType portType = bpelReader.getMessageActivityPortType(replierElem,
        partnerLink.getMyRole());
    // operation
    Operation operation = bpelReader.getMessageActivityOperation(replierElem,
        portType);
    if (!OperationType.REQUEST_RESPONSE.equals(operation.getStyle())) {
      throw new BpelException("cannot reply one-way operation", replierElem);
    }
    replier.setOperation(operation);
    Message outgoingMessage;
    // fault name
    String faultPrefixedName = XmlUtil.getAttribute(replierElem,
        BpelConstants.ATTR_FAULT_NAME);
    if (faultPrefixedName != null) {
      QName faultName = XmlUtil.getQName(faultPrefixedName, replierElem);
      replier.setFaultName(faultName);
      Fault fault = operation.getFault(faultName.getLocalPart());
      if (fault == null) {
        throw new BpelException("fault not found", replierElem);
      }
      outgoingMessage = fault.getMessage();
    }
    else {
      outgoingMessage = operation.getOutput().getMessage();
    }
    // variable
    VariableDefinition variable = bpelReader.getMessageActivityVariable(replierElem,
        BpelConstants.ATTR_VARIABLE,
        parent,
        outgoingMessage);
    replier.setVariable(variable);
    /*
     * message exchange: if the attribute is not specified then its value is
     * taken to be empty BPEL-74: map the empty message exchange to null instead
     * of the empty string
     */
    replier.setMessageExchange(XmlUtil.getAttribute(replierElem,
        BpelConstants.ATTR_MESSAGE_EXCHANGE));
    // correlations
    Element correlationsElement = XmlUtil.getElement(replierElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CORRELATIONS);
    if (correlationsElement != null) {
      replier.setCorrelations(bpelReader.readCorrelations(correlationsElement,
          parent,
          variable));
    }

    return replier;
  }
}
