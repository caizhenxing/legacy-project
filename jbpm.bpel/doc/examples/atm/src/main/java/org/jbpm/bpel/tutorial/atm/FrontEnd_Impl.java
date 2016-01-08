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
package org.jbpm.bpel.tutorial.atm;

import java.rmi.RemoteException;

/*
 * Method bodies in this class were intentionally left empty. The BPEL process
 * {urn:samples:atm}FrontEnd specifies the behavior instead.
 */

/**
 * FrontEnd endpoint implementation bean.
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/11 09:32:37 $
 */
public class FrontEnd_Impl implements FrontEnd {

  public int connect() throws RemoteException {
    // Auto-generated method stub
    return 0;
  }

  public double deposit(String customerName, double amount)
      throws RemoteException {
    // Auto-generated method stub
    return 0;
  }

  public void disconnect(int ticketNo) throws RemoteException {
    // Auto-generated method stub

  }

  public double getBalance(String customerName) throws RemoteException {
    // Auto-generated method stub
    return 0;
  }

  public void logOff(String customerName) throws RemoteException {
    // Auto-generated method stub

  }

  public void logOn(int ticketNo, String customerName)
      throws UnauthorizedAccess, RemoteException {
    // Auto-generated method stub

  }

  public String status(int ticketNo) throws RemoteException {
    // Auto-generated method stub
    return null;
  }

  public double withdraw(String customerName, double amount)
      throws InsufficientFunds, RemoteException {
    // Auto-generated method stub
    return 0;
  }
}