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

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.Session;
import javax.wsdl.OperationType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.bpel.db.IntegrationSession;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class StartListener implements MessageListener {

  private final long processId;
  private final long receiverId;

  private Session jmsSession;
  private MessageConsumer consumer;

  private static final Log log = LogFactory.getLog(StartListener.class);

  public StartListener(BpelDefinition process, Receiver receiver,
      IntegrationControl integrationControl) throws JMSException {
    receiverId = receiver.getId();
    processId = process.getId();
    // get the destination associated to the partner link
    PartnerLinkDefinition partnerLink = receiver.getPartnerLink();
    PartnerLinkEntry entry = integrationControl.getPartnerLinkEntry(partnerLink);
    Destination destination = entry.getDestination();
    // build the message selector
    String selector = buildSelector(receiver);
    jmsSession = integrationControl.getJmsConnection().createSession(false,
        Session.CLIENT_ACKNOWLEDGE);
    consumer = jmsSession.createConsumer(destination, selector);
    consumer.setMessageListener(this);
    log.debug("opened start listener: process=" + process + '#' + processId
        + ", receiver=" + receiver);
  }

  long getReceiverId() {
    return receiverId;
  }

  /** {@inheritDoc} */
  public void onMessage(Message request) {
    if (!(request instanceof ObjectMessage)) {
      log.error("received non-object jms message: " + request);
      return;
    }
    JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      log.debug("received request: " + RequestListener.messageToString(request));
      // load process definition
      BpelDefinition definition = (BpelDefinition) jbpmContext.getGraphSession()
          .loadProcessDefinition(processId);
      // instantiate the process
      ProcessInstance instance = definition.createProcessInstance();

      // load the message receiver
      Receiver trigger = IntegrationSession.getInstance(jbpmContext)
          .loadReceiver(receiverId);
      Token rootToken = instance.getRootToken();
      try {
        // file outstanding request, in case operation has output
        if (OperationType.REQUEST_RESPONSE.equals(trigger.getOperation()
            .getStyle())) {
          // encapsulate the fields needed to reply
          OutstandingRequest outRequest = new OutstandingRequest(
              (Queue) request.getJMSReplyTo(), request.getJMSMessageID());
          // register the request in the relation service
          IntegrationControl.getInstance(jbpmConfiguration)
              .addOutstandingRequest(trigger, rootToken, outRequest);
        }
        // read message parts into the process variables
        Map inputParts = (Map) ((ObjectMessage) request).getObject();
        trigger.readMessage(inputParts, rootToken);
        // pass control to the process
        definition.messageReceived(trigger, rootToken);
      }
      catch (RuntimeException e) {
        log.debug("caught exception while passing control to process, searching for handler",
            e);
        definition.getGlobalScope().raiseException(e,
            new ExecutionContext(rootToken));
      }
      // save changes to instance
      jbpmContext.save(instance);
    }
    catch (Exception e) {
      log.error("could not start process instance", e);
      jbpmContext.setRollbackOnly();
      return;
    }
    finally {
      try {
        // end transaction, close all services
        jbpmContext.close();
      }
      catch (RuntimeException e) {
        log.debug("could not close jbpm context", e);
      }
    }
    try {
      // acknowledge request after everything succeeds
      request.acknowledge();
    }
    catch (JMSException e) {
      log.debug("could not propagate execution to subsequent request listeners",
          e);
    }
  }

  public void close() throws JMSException {
    // close consumer first
    consumer.close();
    log.debug("closed start listener: process=" + processId + ", receiver="
        + receiverId);
    // now close session
    jmsSession.close();
  }

  protected String buildSelector(Receiver receiver) {
    // partner link id
    return IntegrationConstants.PARTNER_LINK_ID_PROP + '='
        + receiver.getPartnerLink().getId() +
        // operation name
        " AND " + IntegrationConstants.OPERATION_NAME_PROP + "='"
        + receiver.getOperation().getName() + '\'';
  }

  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this);
    try {
      builder.append("queue",
          ((QueueReceiver) consumer).getQueue().getQueueName())
          .append("selector", consumer.getMessageSelector());
    }
    catch (JMSException e) {
      log.debug("could not fill request listener fields", e);
    }
    return builder.toString();
  }
}
