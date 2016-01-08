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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.wsdl.OperationType;
import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.bpel.db.IntegrationSession;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.svc.Services;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class RequestListener implements MessageListener {

  private final long receiverId;
  private final long tokenId;

  private MessageConsumer consumer;

  private static final Log log = LogFactory.getLog(RequestListener.class);

  public RequestListener(Receiver receiver, Token token,
      JmsIntegrationService integrationService) throws JMSException {
    // initialize fields
    this.receiverId = receiver.getId();
    // in case token is transient, assign an identifier to it
    long tokenId = token.getId();
    if (tokenId == 0L) {
      Services.assignId(token);
      tokenId = token.getId();
    }
    this.tokenId = tokenId;
    // format a selector including partner link, operation and correlation
    // properties
    String selector = buildSelector(receiver, token);
    // retrieve the destination associated with the partner link
    PartnerLinkDefinition partnerLink = receiver.getPartnerLink();
    PartnerLinkEntry entry = integrationService.getIntegrationControl()
        .getPartnerLinkEntry(partnerLink);
    Destination destination = entry.getDestination();
    // create a consumer for the previous destination
    consumer = integrationService.getJmsSession().createConsumer(destination,
        selector);
    log.debug("created request listener: receiver=" + receiver + ", token="
        + token + '#' + tokenId);
  }

  long getReceiverId() {
    return receiverId;
  }

  long getTokenId() {
    return tokenId;
  }

  public void open() throws JMSException {
    /*
     * jms could deliver a message immediately after setting this listener, so
     * make sure this listener is fully initialized at this point
     */
    consumer.setMessageListener(this);
    log.debug("opened request listener: receiver=" + receiverId + ", token="
        + tokenId);
  }

  /** {@inheritDoc} */
  public void onMessage(Message request) {
    if (!(request instanceof ObjectMessage)) {
      log.error("received non-object jms message: " + request);
      return;
    }
    // create a jbpm context
    JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      log.debug("received request: " + RequestListener.messageToString(request));
      // load the token and have it saved automatically
      Token token = jbpmContext.loadTokenForUpdate(tokenId);
      log.debug("loaded token: " + token + '#' + tokenId);
      // load receiver via Hibernate session
      Receiver receiver = IntegrationSession.getInstance(jbpmContext)
          .loadReceiver(receiverId);
      log.debug("loaded receiver: " + receiver);
      try {
        // file outstanding request, in case operation has output
        if (OperationType.REQUEST_RESPONSE.equals(receiver.getOperation()
            .getStyle())) {
          OutstandingRequest outRequest = new OutstandingRequest(
              (Queue) request.getJMSReplyTo(), request.getJMSMessageID());
          IntegrationControl.getInstance(jbpmConfiguration)
              .addOutstandingRequest(receiver, token, outRequest);
        }
        // read message parts into process variables
        Map inputParts = (Map) ((ObjectMessage) request).getObject();
        receiver.readMessage(inputParts, token);
        // pass control to activity
        receiver.getInboundMessageActivity().messageReceived(receiver, token);
      }
      catch (RuntimeException e) {
        log.debug("caught exception while passing control to activity, searching for handler",
            e);
        token.getNode().raiseException(e, new ExecutionContext(token));
      }
    }
    catch (Exception e) {
      log.error("could not resume process execution", e);
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
    consumer.close();
    log.debug("closed request listener: receiver= #" + receiverId
        + ", token= #" + tokenId);
  }

  protected String buildSelector(Receiver receiver, Token token) {
    StringBuffer selector = new StringBuffer();
    // partner link id
    selector.append(IntegrationConstants.PARTNER_LINK_ID_PROP)
        .append('=')
        .append(receiver.getPartnerLink().getId());
    // operation name
    selector.append(" AND ")
        .append(IntegrationConstants.OPERATION_NAME_PROP)
        .append("='")
        .append(receiver.getOperation().getName())
        .append('\'');
    // reception properties
    Correlations correlations = receiver.getCorrelations();
    // BPEL-90: avoid NPE when the receiver was defined with no correlations
    if (correlations != null) {
      // iterate over the property name-value pairs
      Iterator propertyEntryIt = correlations.getReceptionProperties(token)
          .entrySet()
          .iterator();
      while (propertyEntryIt.hasNext()) {
        Map.Entry propertyEntry = (Map.Entry) propertyEntryIt.next();
        QName propertyName = (QName) propertyEntry.getKey();
        // property value
        selector.append(" AND ")
            .append(propertyName.getLocalPart())
            .append("='")
            .append(propertyEntry.getValue())
            .append('\'');
      }
    }
    return selector.toString();
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

  public static String messageToString(Message message) throws JMSException {
    StringBuffer result = new StringBuffer();
    // ID & destination
    result.append("id=").append(message.getJMSMessageID());
    result.append(", destination=").append(message.getJMSDestination());
    // replyTo & correlationID
    Destination replyTo = message.getJMSReplyTo();
    if (replyTo != null) {
      result.append(", replyTo=").append(replyTo);
      result.append(", correlationId=").append(message.getJMSCorrelationID());
    }
    // properties
    Enumeration propertyNames = message.getPropertyNames();
    while (propertyNames.hasMoreElements()) {
      String propertyName = (String) propertyNames.nextElement();
      result.append(", ")
          .append(propertyName)
          .append('=')
          .append(message.getObjectProperty(propertyName));
    }
    return result.toString();
  }

  static class Key {

    private final long receiverId;
    private final long tokenId;

    Key(long receiverId, long tokenId) {
      this.receiverId = receiverId;
      this.tokenId = tokenId;
    }

    public long getReceiverId() {
      return receiverId;
    }

    public long getTokenId() {
      return tokenId;
    }

    public boolean equals(Object obj) {
      if (!(obj instanceof Key)) return false;

      Key that = (Key) obj;
      return receiverId == that.receiverId && tokenId == that.tokenId;
    }

    public int hashCode() {
      return new HashCodeBuilder(863, 5).append(receiverId)
          .append(tokenId)
          .toHashCode();
    }

    public String toString() {
      return new ToStringBuilder(this).append("receiverId", receiverId)
          .append("tokenId", tokenId)
          .toString();
    }
  }
}
