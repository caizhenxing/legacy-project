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

import org.jbpm.bpel.tutorial.atm.AtmFrontEnd;
import org.jbpm.bpel.tutorial.atm.types.TOperationError;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class WithdrawAction extends AbstractAction {

  private static final long serialVersionUID = 1L;

  public WithdrawAction() {
    putValue(NAME, "Withdraw");
  }

  /** {@inheritDoc} */
  public void actionPerformed(ActionEvent event) {
    Map context = AtmTerminal.getContext();
    AtmPanel atmPanel = (AtmPanel) context.get(AtmTerminal.PANEL);
    
    // capture amount
    String amountText = JOptionPane.showInputDialog(atmPanel, "Desired amount:");
    if (amountText == null) return;
    
    try {
      // parse amount
      double amount = Double.parseDouble(amountText);
      
      // withdraw funds from account
      AtmFrontEnd atmFrontEnd = (AtmFrontEnd) context.get(AtmTerminal.FRONT_END);
      String customerName = (String) context.get(AtmTerminal.CUSTOMER);
      double balance = atmFrontEnd.withdraw(customerName, amount);
      
      // update atm panel
      atmPanel.setMessage("Your new balance is $" + balance);
    }
    catch (NumberFormatException e) {
      atmPanel.setMessage("Please enter a valid amount.");
    }
    catch (TOperationError e) {
      atmPanel.setMessage("Sorry, I could not fulfill your request.\n(" + e.getCode() + ")");
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
