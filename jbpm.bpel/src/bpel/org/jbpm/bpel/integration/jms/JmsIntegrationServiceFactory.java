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

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/25 08:38:34 $
 */
public class JmsIntegrationServiceFactory implements ServiceFactory {

  // injected fields, see jbpm.cfg.xml
  protected String defaultDestinationName;
  protected String defaultConnectionFactoryName;

  private Destination defaultDestination;
  private ConnectionFactory defaultConnectionFactory;

  private Map integrationControls = new IdentityHashMap();

  private static final Log log = LogFactory.getLog(JmsIntegrationServiceFactory.class);
  private static final long serialVersionUID = 1L;

  public Service openService() {
    return new JmsIntegrationService(getIntegrationControl());
  }

  public void close() {
    Iterator integrationControlIt = integrationControls.values().iterator();
    while (integrationControlIt.hasNext()) {
      IntegrationControl integrationControl = (IntegrationControl) integrationControlIt.next();
      try {
        integrationControl.disableInboundMessageActivities();
      }
      catch (JMSException e) {
        log.debug("could not close integration control", e);
      }
    }
  }

  public IntegrationControl getIntegrationControl() {
    ClassLoader contextClassLoader = Thread.currentThread()
        .getContextClassLoader();
    IntegrationControl integrationControl = (IntegrationControl) integrationControls.get(contextClassLoader);
    if (integrationControl == null) {
      log.debug("creating integration control: contextClassLoader="
          + contextClassLoader);
      integrationControl = new IntegrationControl(this);
      integrationControls.put(contextClassLoader, integrationControl);
    }
    return integrationControl;
  }

  public ConnectionFactory getDefaultConnectionFactory() {
    if (defaultConnectionFactory == null && defaultConnectionFactoryName != null) {
      try {
        defaultConnectionFactory = createDefaultConnectionFactory();
      }
      catch (Exception e) {
        log.debug("could not create default connection", e);
      }
    }
    return defaultConnectionFactory;
  }

  protected ConnectionFactory createDefaultConnectionFactory() throws NamingException {
    InitialContext initialContext = new InitialContext();
    try {
      return (ConnectionFactory) initialContext.lookup(defaultConnectionFactoryName);
    }
    finally {
      initialContext.close();
    }
  }

  public Destination getDefaultDestination() {
    if (defaultDestination == null && defaultDestinationName != null) {
      try {
        defaultDestination = createDefaultDestination();
      }
      catch (Exception e) {
        log.debug("could not create default destination", e);
      }
    }
    return defaultDestination;
  }

  private Destination createDefaultDestination() throws NamingException {
    InitialContext initialContext = new InitialContext();
    try {
      return (Destination) initialContext.lookup(defaultDestinationName);
    }
    finally {
      initialContext.close();
    }
  }
}
