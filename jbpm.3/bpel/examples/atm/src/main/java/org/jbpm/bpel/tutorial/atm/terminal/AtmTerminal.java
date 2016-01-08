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

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class AtmTerminal {
  
  private static Map context = new HashMap();
  
  public static final String PANEL = "panel";
  public static final String FRONT_END = "frontEnd";
  public static final String TICKET = "ticket";
  public static final String CUSTOMER = "customer";
    
  public static Map getContext() {
    return context;
  }
  
  public static void main(String[] args) {
    // set native system look and feel
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
      System.err.println("could not set native system look and feel: " + e);
    }
    // configure atm panel
    AtmPanel atmPanel = new AtmPanel();
    atmPanel.setMessage("This terminal is disconnected.\nPlease connect it or use a different one.");
    atmPanel.setStatus("disconnected");
    atmPanel.addAction(new ConnectAction());
    getContext().put(AtmTerminal.PANEL, atmPanel);
    // display panel in a frame
    JFrame mainFrame = new JFrame("ATM");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.getContentPane().add(atmPanel);
    mainFrame.pack();
    mainFrame.setVisible(true);
  }
}
