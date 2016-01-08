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

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.integration.jms.IntegrationControl;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/08/25 08:40:32 $
 */
public class IntegrationControlHelper {

  public static void setUp(IntegrationControl integrationControl, JbpmContext jbpmContext) 
  throws NamingException, JMSException {
    InitialContext initialContext = new InitialContext();
    try {
      integrationControl.createPartnerLinkEntries(initialContext, 
          integrationControl.findProcessDefinition(jbpmContext));
      integrationControl.createJmsConnection(initialContext);
    }
    finally {
      initialContext.close();
    }
    integrationControl.getJmsConnection().start();
  }
  
  public static void tearDown(IntegrationControl integrationControl) 
  throws JMSException {
    integrationControl.closeJmsConnection();
    integrationControl.reset();
  }
}
