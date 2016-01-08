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

import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class JmsIntegrationServiceFactory implements ServiceFactory {

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
        integrationControl.close();
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
      integrationControl = new IntegrationControl();
      integrationControls.put(contextClassLoader, integrationControl);
    }
    return integrationControl;
  }
}
