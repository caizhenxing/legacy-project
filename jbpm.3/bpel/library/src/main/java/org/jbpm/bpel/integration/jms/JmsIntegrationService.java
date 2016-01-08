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
package org.jbpm.bpel.integration.jms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.client.SoapClient;
import org.jbpm.bpel.integration.def.Invoker;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.def.Replier;
import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.bpel.integration.exe.PartnerLinkInstance;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class JmsIntegrationService implements IntegrationService {

  private final IntegrationControl integrationControl;

  private Session jmsSession;
  private List requestListeners = new ArrayList();

  private static final Log log = LogFactory.getLog(JmsIntegrationService.class);
  private static final long serialVersionUID = 1L;

  JmsIntegrationService(IntegrationControl integrationControl) {
    this.integrationControl = integrationControl;
  }

  /** {@inheritDoc} */
  public void receive(Receiver receiver, Token token) {
    try {
      jmsSession = createJmsSession();
      createRequestListener(receiver, token);
    }
    catch (JMSException e) {
      throw new BpelException("could not create request listener", e);
    }
    finally {
      jmsSession = null;
    }
  }

  public void receive(List receivers, Token token) {
    try {
      jmsSession = createJmsSession();
      Iterator receiverIt = receivers.iterator();
      while (receiverIt.hasNext()) {
        Receiver receiver = (Receiver) receiverIt.next();
        createRequestListener(receiver, token);
      }
    }
    catch (JMSException e) {
      throw new BpelException("could not create request listeners", e);
    }
    finally {
      jmsSession = null;
    }
  }

  private Session createJmsSession() throws JMSException {
    return integrationControl.getJmsConnection().createSession(false,
        Session.CLIENT_ACKNOWLEDGE);
  }

  Session getJmsSession() {
    return jmsSession;
  }

  IntegrationControl getIntegrationControl() {
    return integrationControl;
  }

  private void createRequestListener(Receiver receiver, Token token)
      throws JMSException {
    RequestListener requestListener = new RequestListener(receiver, token, this);
    requestListeners.add(requestListener);
    integrationControl.addRequestListener(requestListener);
  }

  /** {@inheritDoc} */
  public void endReception(Receiver receiver, Token token) {
    closeRequestListener(receiver, token);
  }

  /** {@inheritDoc} */
  public void endReception(List receivers, Token token) {
    // close all request listeners
    Iterator receiverIt = receivers.iterator();
    while (receiverIt.hasNext()) {
      Receiver receiver = (Receiver) receiverIt.next();
      closeRequestListener(receiver, token);
    }
  }

  private void closeRequestListener(Receiver receiver, Token token) {
    RequestListener requestListener = integrationControl.removeRequestListener(receiver,
        token);
    // some competing thread might have removed the request listener already
    if (requestListener == null) return;

    requestListeners.remove(requestListener);
    try {
      requestListener.close();
    }
    catch (JMSException e) {
      log.warn("could not close request listener: " + requestListener);
    }
  }

  /** {@inheritDoc} */
  public void reply(Replier replier, Token token) {
    Map parts = replier.writeMessage(token);
    OutstandingRequest request = integrationControl.removeOutstandingRequest(replier,
        token);
    Session jmsSession = null;
    try {
      jmsSession = createJmsSession();
      request.sendReply(parts, replier.getFaultName(), jmsSession);
    }
    catch (JMSException e) {
      throw new BpelException("could not send reply", e);
    }
    finally {
      if (jmsSession != null) {
        try {
          jmsSession.close();
        }
        catch (JMSException e) {
          log.warn("could not close jms session", e);
        }
      }
    }
  }

  /** {@inheritDoc} */
  public void invoke(Invoker invoker, Token token) {
    // obtain a caller for the partner port
    PartnerLinkDefinition definition = invoker.getPartnerLink();
    PartnerLinkInstance instance = definition.getInstance(token);
    SoapClient partnerClient = integrationControl.getPartnerClient(instance);
    // retrieve the input data
    Map inputParts = invoker.writeMessage(token);
    // is this a request/response operation?
    Operation operation = invoker.getOperation();
    String operationName = operation.getName();
    log.debug("invoking: partnerLink=" + definition.getId() + ", operation="
        + operationName + ", input=" + inputParts);
    if (OperationType.REQUEST_RESPONSE.equals(operation.getStyle())) {
      // block for a response
      Map outputParts = partnerClient.call(operationName, inputParts);
      log.debug("received response: partnerLink=" + definition.getId()
          + ", operation=" + operationName + ", output=" + outputParts);
      // assign the output data
      invoker.readMessage(outputParts, token);
    }
    else {
      // fire and forget
      partnerClient.callOneWay(operationName, inputParts);
      log.debug("sent one-way message: partnerLink=" + definition.getId()
          + ", operation=" + operationName);
    }
  }

  public EndpointReference getMyReference(PartnerLinkDefinition partnerLink) {
    // TODO where to get my reference from?
    return null;
  }

  public void close() {
    try {
      openRequestListeners();
    }
    catch (JMSException e) {
      throw new BpelException("could not open subsequent request listeners", e);
    }
  }

  public void openRequestListeners() throws JMSException {
    for (int i = 0, n = requestListeners.size(); i < n; i++) {
      RequestListener requestListener = (RequestListener) requestListeners.get(i);
      requestListener.open();
    }
  }

  public static JmsIntegrationService get(JbpmContext jbpmContext) {
    return (JmsIntegrationService) jbpmContext.getServices()
        .getService(IntegrationService.SERVICE_NAME);
  }
}
