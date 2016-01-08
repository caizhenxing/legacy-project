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

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.jbpm.bpel.tutorial.atm.types.TErrorCode;
import org.jbpm.bpel.tutorial.atm.types.TOperationError;
import org.jbpm.bpel.tutorial.atm.types.TStatus;

/**
 * @author Juan Cantu
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:06:10 $
 */
public class ClientTest extends TestCase {

  private AtmFrontEnd atmFrontEnd;

  public ClientTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    InitialContext ctx = getInitialContext();
    // JNDI name of service interface (in application-client.xml)
    String serviceRefName = "service/ATM";
    // lookup service interface in environment context
    AtmFrontEndService service = (AtmFrontEndService) ctx.lookup("java:comp/env/" + serviceRefName);
    // obtain dynamic proxy for web service port
    atmFrontEnd = service.getAtmRelationPort();
  }

  protected InitialContext getInitialContext() throws NamingException {
    // prepare environment
    Properties env = new Properties();
    // JNDI name of client environment context (in jboss-client.xml)
    env.setProperty("j2ee.clientName", "atm-client");
    // initial context contains property above, plus those in jndi.properties 
    return new InitialContext(env);
  }
  
  public void testConnect() throws Exception {
    // connect to bank
    int ticketNumber = atmFrontEnd.connect();
    assertTrue(ticketNumber > 0);
  
    // check atm is connected
    TStatus status = atmFrontEnd.status(ticketNumber);
    assertSame(TStatus.connected, status);
    
    // disconnect from bank
    atmFrontEnd.disconnect(ticketNumber);    
  }
  
  public void testLogOn() throws Exception {
    // connect to bank
    int ticketNumber = atmFrontEnd.connect();
    
    // begin customer session
    final String customerName = "grover";
    atmFrontEnd.logOn(ticketNumber, customerName);

    // check customer is logged on
    TStatus status = atmFrontEnd.status(ticketNumber);
    assertSame(TStatus.logged, status);
    
    // end customer session
    atmFrontEnd.logOff(customerName);

    // disconnect from bank
    atmFrontEnd.disconnect(ticketNumber);
  }

  public void testDeposit() throws Exception {
    // connect to bank
    int ticketNumber = atmFrontEnd.connect();
    
    // begin customer session
    final String customerName = "ernie";
    atmFrontEnd.logOn(ticketNumber, customerName);

    // get current balance
    double previousBalance = atmFrontEnd.getBalance(customerName);

    // deposit some funds
    double newBalance = atmFrontEnd.deposit(customerName, 10);
    // check the new balance is correct
    assertEquals(previousBalance + 10, newBalance, 0);

    // end customer session
    atmFrontEnd.logOff(customerName);

    // disconnect from bank
    atmFrontEnd.disconnect(ticketNumber);
  }
  
  public void testWithdrawUnderBalance() throws Exception {
    // connect to bank
    int ticketNumber = atmFrontEnd.connect();
    
    // begin customer session
    final String customerName = "ernie";
    atmFrontEnd.logOn(ticketNumber, customerName);

    // get current balance
    double previousBalance = atmFrontEnd.getBalance(customerName);
    
    // withdraw some funds
    try {
      double newBalance = atmFrontEnd.withdraw(customerName, 10);
      // check new balance is correct
      assertEquals(previousBalance - 10, newBalance, 0);
    }
    catch (TOperationError e) {
      fail("withdraw under balance should be allowed");
    }
    
    // end customer session
    atmFrontEnd.logOff(customerName);

    // disconnect from bank
    atmFrontEnd.disconnect(ticketNumber);    
  }
  
  public void testWithdrawOverBalance() throws Exception {
    // connect to bank
    int ticketNumber = atmFrontEnd.connect();
    
    // begin customer session
    final String customerName = "bert";
    atmFrontEnd.logOn(ticketNumber, customerName);

    // get current balance
    double previousBalance = atmFrontEnd.getBalance(customerName);    
    
    // try to withdraw an amount greater than current balance
    try {
      atmFrontEnd.withdraw(customerName, previousBalance + 1);
      fail("withdraw over balance should be rejected");
    }
    catch (TOperationError e) {
      assertEquals(TErrorCode.not_enough_funds, e.getCode());
      // check account balance has not changed
      assertEquals(previousBalance, atmFrontEnd.getBalance(customerName), 0);
    }
    
    // end customer session
    atmFrontEnd.logOff(customerName);

    // disconnect from bank
    atmFrontEnd.disconnect(ticketNumber);
  }
}