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
package org.jbpm.bpel.def;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.integration.def.Receiver;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class ReceiveDbTest extends AbstractDbTestCase {
  
  private Receive receive;
  
  private static final String ACTIVITY_NAME = "receive";
  
  public void setUp() throws Exception {
    // set up database stuff
    super.setUp();
    // receive
    receive = new Receive(ACTIVITY_NAME);
    // process, create after opening the jbpm context
    new BpelDefinition("process").getGlobalScope().addNode(receive);
  }

  public void testCreateInstance() {
    // prepare persistent objects
    // receive
    receive.setCreateInstance(true);
    
    // save objects and load them back
    saveAndReload();
    
    // verify the retrieved objects
    assertTrue(receive.isCreateInstance());
  }
  
  public void testReceiver() {
    // prepare persistent objects
    // receive    
    Receiver receiver = new Receiver();
    receive.setReceiver(receiver);
    
    // save objects and load them back
    saveAndReload();

    // verify the retrieved objects
    assertNotNull(receive.getReceiver());
  }
  
  private void saveAndReload() {
    BpelDefinition processDefinition = saveAndReload(receive.getBpelDefinition());
    receive = (Receive) session.load(Receive.class, 
        new Long(processDefinition.getGlobalScope().getNode(ACTIVITY_NAME).getId()));
  }
}
