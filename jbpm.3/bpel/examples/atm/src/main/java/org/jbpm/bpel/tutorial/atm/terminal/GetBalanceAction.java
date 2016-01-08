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
public class GetBalanceAction extends AbstractAction {

  private static final long serialVersionUID = 1L;

  public GetBalanceAction() {
    putValue(NAME, "Get Balance");
  }

  /** {@inheritDoc} */
  public void actionPerformed(ActionEvent event) {
    Map context = AtmTerminal.getContext();
    AtmPanel atmPanel = (AtmPanel) context.get(AtmTerminal.PANEL);
    
    try {
      // get account balance
      AtmFrontEnd atmFrontEnd = (AtmFrontEnd) context.get(AtmTerminal.FRONT_END);
      String customerName = (String) context.get(AtmTerminal.CUSTOMER);
      double balance = atmFrontEnd.getBalance(customerName);
      
      // update atm panel
      atmPanel.setMessage("Your balance is $" + balance);
    }
    catch (RemoteException e) {
      atmPanel.setMessage("Sorry,\nthe banking session was terminated.\nPlease log on again.");
      atmPanel.clearActions();
      atmPanel.addAction(new LogOnAction());
      atmPanel.addAction(new DisconnectAction());
      atmPanel.setStatus("connected");
      e.printStackTrace();
    }
  }
}
