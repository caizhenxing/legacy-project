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
package org.jbpm.bpel.tutorial.atm.terminal;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.AbstractAction;

import org.jbpm.bpel.tutorial.atm.AtmFrontEnd;
import org.jbpm.bpel.tutorial.atm.AtmFrontEndService;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ConnectAction extends AbstractAction {
  
  private static final long serialVersionUID = 1L;
    
  public ConnectAction() {
    putValue(NAME, "Connect");  
  }
  
  /** {@inheritDoc} */
  public void actionPerformed(ActionEvent event) {
    Map context = AtmTerminal.getContext();
    AtmPanel atmPanel = (AtmPanel) context.get(AtmTerminal.PANEL);
    
    InitialContext ctx = null;
    try {
      ctx = getInitialContext();
      // JNDI name of service interface (in application-client.xml)
      String serviceRefName = "service/ATM";
      // lookup service interface in environment context
      AtmFrontEndService service = (AtmFrontEndService) ctx.lookup("java:comp/env/" + serviceRefName);
      // obtain dynamic proxy for web service port
      AtmFrontEnd atmFrontEnd = service.getAtmRelationPort();
      context.put(AtmTerminal.FRONT_END, atmFrontEnd);
      
      // connect to atm service
      int ticketNo = atmFrontEnd.connect();
      context.put(AtmTerminal.TICKET, new Integer(ticketNo));
      
      // update atm panel
      atmPanel.setMessage("Please log on,\nso we can begin");
      atmPanel.clearActions();
      atmPanel.addAction(new LogOnAction());
      atmPanel.addAction(new DisconnectAction());
      atmPanel.setStatus(atmFrontEnd.status(ticketNo).getValue());   
    }
    catch (Exception e) {
      atmPanel.setMessage("Sorry,\nI am experiencing technical problems.\nPlease use another terminal.");
      atmPanel.clearActions();
      e.printStackTrace();
    }
    finally {
      if (ctx != null) {
        try {
          ctx.close();
        }
        catch (NamingException e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  protected InitialContext getInitialContext() throws NamingException {
    // prepare environment
    Properties env = new Properties();
    // JNDI name of client environment context (in jboss-client.xml)
    env.setProperty("j2ee.clientName", "atm-client");
    // initial context contains property above, plus those in jndi.properties 
    return new InitialContext(env);
  }
}
