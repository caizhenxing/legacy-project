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
package org.jbpm.bpel.integration.server;

import org.jbpm.bpel.integration.jms.StartListener;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class StartListenerTest extends AbstractListenerTest {
  
  private StartListener listener;
  
  private static final int RECEPTION_COUNT = 5;
  
  public void tearDown() throws Exception {
    // start listeners must be closed externally
    closeListener();
    super.tearDown();
  }

  public void testMultipleReception() throws Exception {
    openListener();
    // start listeners should process any number of requests
    for (int i = 0; i < RECEPTION_COUNT; i++) { 
      // send a request message
      sendRequest();
      // wait until reception is verified
      synchronized (lock) {
        lock.wait();
      }
    } 
  }
  
  protected void openListener() throws Exception {
    listener = new StartListener(process, receiver, integrationControl);
  }

  protected void closeListener() throws Exception {
    listener.close();
  }
}
