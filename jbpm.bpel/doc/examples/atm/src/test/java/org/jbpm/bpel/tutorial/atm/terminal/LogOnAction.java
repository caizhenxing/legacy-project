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
import org.jbpm.bpel.tutorial.atm.UnauthorizedAccess;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/11 09:32:36 $
 */
public class LogOnAction extends AbstractAction {

  private static final long serialVersionUID = 1L;

  public LogOnAction() {
    putValue(NAME, "Log On");
  }

  /** {@inheritDoc} */
  public void actionPerformed(ActionEvent event) {
    Map context = AtmTerminal.getContext();
    AtmPanel atmPanel = (AtmPanel) context.get(AtmTerminal.PANEL);
    
    // capture customer name
    String customerName = JOptionPane.showInputDialog(atmPanel, "Name on card:");
    if (customerName == null) return;
    context.put(AtmTerminal.CUSTOMER, customerName);
    
    try {
      // log on customer
      FrontEnd atmFrontEnd = (FrontEnd) context.get(AtmTerminal.FRONT_END);
      int ticketNo = ((Integer) context.get(AtmTerminal.TICKET)).intValue();
      atmFrontEnd.logOn(ticketNo, customerName);
      
      // update atm panel
      atmPanel.setMessage("Hi, " + customerName + ". How can I help you today?");
      atmPanel.clearActions();
      atmPanel.addAction(new GetBalanceAction());
      atmPanel.addAction(new DepositAction());
      atmPanel.addAction(new WithdrawAction());
      atmPanel.addAction(new LogOffAction());
      atmPanel.setStatus(atmFrontEnd.status(ticketNo));
    }
    catch (UnauthorizedAccess e) {
      atmPanel.setMessage("I could not fulfill your request.\n" +
          "You are not authorized to use this ATM.");
      e.printStackTrace();
    }
    catch (RemoteException e) {
      atmPanel.setMessage("The connection with the bank failed.\n" +
          "Please log on again.");
      e.printStackTrace();
    }
  }
}
