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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.collection.PersistentMap;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:11 $
 */
public class OutstandingRequest {

  private String replyQueueName;
  private String correlationID;

  private transient Queue replyQueue;

  private static final Log log = LogFactory.getLog(OutstandingRequest.class);

  public OutstandingRequest(Queue replyQueue, String correlationID)
      throws JMSException {
    this.replyQueue = replyQueue;
    this.correlationID = correlationID;
    replyQueueName = replyQueue.getQueueName();
  }

  public void sendReply(Map parts, QName faultName, Session jmsSession)
      throws JMSException {
    MessageProducer producer = null;
    try {
      if (replyQueue == null) {
        replyQueue = jmsSession.createQueue(replyQueueName);
      }
      producer = jmsSession.createProducer(replyQueue);
      if (parts instanceof PersistentMap) {
        parts = new HashMap(parts);
      }
      Message responseMsg = jmsSession.createObjectMessage((Serializable) parts);
      responseMsg.setJMSCorrelationID(correlationID);
      // set the fault name, if any
      if (faultName != null) {
        responseMsg.setStringProperty(IntegrationConstants.FAULT_NAME_PROP,
            faultName.toString());
      }
      // send the response
      producer.send(responseMsg);
      log.debug("sent response: "
          + RequestListener.messageToString(responseMsg));
    }
    finally {
      if (producer != null) {
        try {
          producer.close();
        }
        catch (JMSException e) {
          log.warn("could not close jms producer", e);
        }
      }
    }
  }

  public String toString() {
    return new ToStringBuilder(this).append("replyQueue", replyQueueName)
        .append("correlationID", correlationID)
        .toString();
  }

  static class Key {

    private final long partnerLinkId;
    private final String operationName;
    private final String messageExchange;

    Key(long partnerLinkId, String operation, String messageExchange) {
      this.partnerLinkId = partnerLinkId;
      this.operationName = operation;
      this.messageExchange = messageExchange;
    }

    public long getPartnerLinkId() {
      return partnerLinkId;
    }

    public String getOperationName() {
      return operationName;
    }

    public String getMessageExchange() {
      return messageExchange;
    }

    public boolean equals(Object obj) {
      if (!(obj instanceof Key)) return false;

      Key that = (Key) obj;
      return partnerLinkId == that.partnerLinkId
          && operationName.equals(that.operationName)
          && (messageExchange != null ? messageExchange.equals(that.messageExchange)
              : that.messageExchange == null);
    }

    public int hashCode() {
      return new HashCodeBuilder(239, 23).append(partnerLinkId)
          .append(operationName)
          .append(messageExchange)
          .toHashCode();
    }

    public String toString() {
      return new ToStringBuilder(this).append("partnerLinkId", partnerLinkId)
          .append("operationName", operationName)
          .append("messageExchange", messageExchange)
          .toString();
    }
  }
}
