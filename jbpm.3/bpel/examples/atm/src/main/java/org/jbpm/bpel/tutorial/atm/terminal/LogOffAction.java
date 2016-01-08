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
import java.rmi.RemoteException;
import java.util.Map;

import javax.swing.AbstractAction;

import org.jbpm.bpel.tutorial.atm.AtmFrontEnd;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class LogOffAction extends AbstractAction {

  private static final long serialVersionUID = 1L;

  public LogOffAction() {
    putValue(NAME, "Log Off");
  }

  /** {@inheritDoc} */
  public void actionPerformed(ActionEvent event) {
    Map context = AtmTerminal.getContext();
    AtmPanel atmPanel = (AtmPanel) context.get(AtmTerminal.PANEL);
    AtmFrontEnd atmFrontEnd = (AtmFrontEnd) context.get(AtmTerminal.FRONT_END);
    
    try {
      // log off customer
      String customerName = (String) context.get(AtmTerminal.CUSTOMER);
      atmFrontEnd.logOff(customerName);      
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
    
    // update atm panel
    atmPanel.setMessage("Welcome!\nPlease log on, so we can begin");
    atmPanel.clearActions();
    atmPanel.addAction(new LogOnAction());
    atmPanel.addAction(new DisconnectAction());
    atmPanel.setStatus("connected");
  }
}
