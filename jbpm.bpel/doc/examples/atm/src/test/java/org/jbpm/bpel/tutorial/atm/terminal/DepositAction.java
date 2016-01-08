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
import javax.swing.JOptionPane;

import org.jbpm.bpel.tutorial.atm.FrontEnd;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/11 09:32:36 $
 */
public class DepositAction extends AbstractAction {

  private static final long serialVersionUID = 1L;

  public DepositAction() {
    putValue(NAME, "Deposit");
  }

  /** {@inheritDoc} */
  public void actionPerformed(ActionEvent event) {
    Map context = AtmTerminal.getContext();
    AtmPanel atmPanel = (AtmPanel) context.get(AtmTerminal.PANEL);

    // capture amount
    String amountText = JOptionPane.showInputDialog(atmPanel, "Fed amount:");
    if (amountText == null) return;

    try {
      // parse amount
      double amount = Double.parseDouble(amountText);

      // deposit funds to account
      FrontEnd atmFrontEnd = (FrontEnd) context.get(AtmTerminal.FRONT_END);
      String customerName = (String) context.get(AtmTerminal.CUSTOMER);
      double balance = atmFrontEnd.deposit(customerName, amount);

      // update atm panel
      atmPanel.setMessage("Your balance is $" + balance);
    }
    catch (NumberFormatException e) {
      atmPanel.setMessage("Please enter a valid amount.");
      e.printStackTrace();
    }
    catch (RemoteException e) {
      atmPanel.setMessage("The banking session was terminated.\n"
          + "Please log on again.");
      atmPanel.clearActions();
      atmPanel.addAction(new LogOnAction());
      atmPanel.addAction(new DisconnectAction());
      atmPanel.setStatus("connected");
      e.printStackTrace();
    }
  }
}
