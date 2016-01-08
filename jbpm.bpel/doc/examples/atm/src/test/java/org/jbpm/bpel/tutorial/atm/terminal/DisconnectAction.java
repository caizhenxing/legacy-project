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

import org.jbpm.bpel.tutorial.atm.FrontEnd;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/11 09:32:36 $
 */
public class DisconnectAction extends AbstractAction {

  private static final long serialVersionUID = 1L;

  public DisconnectAction() {
    putValue(NAME, "Disconnect");
  }

  /** {@inheritDoc} */
  public void actionPerformed(ActionEvent event) {
    Map context = AtmTerminal.getContext();
    AtmPanel atmPanel = (AtmPanel) context.get(AtmTerminal.PANEL);

    try {
      // disconnect from server
      FrontEnd atmFrontEnd = (FrontEnd) context.get(AtmTerminal.FRONT_END);
      int ticketNo = ((Integer) context.get(AtmTerminal.TICKET)).intValue();
      atmFrontEnd.disconnect(ticketNo);
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
    // update atm panel
    atmPanel.setMessage("This terminal is disconnected.\n"
        + "Please connect it or use another");
    atmPanel.clearActions();
    atmPanel.addAction(new ConnectAction());
    atmPanel.setStatus("disconnected");
  }
}
