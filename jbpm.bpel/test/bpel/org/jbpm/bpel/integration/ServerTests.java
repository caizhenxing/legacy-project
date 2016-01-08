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
package org.jbpm.bpel.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jbpm.bpel.integration.client.SoapClientTest;
import org.jbpm.bpel.integration.def.InvokerTest;
import org.jbpm.bpel.integration.def.ReplierTest;
import org.jbpm.bpel.integration.jms.RequestListenerTest;
import org.jbpm.bpel.integration.jms.StartListenerTest;
import org.jbpm.bpel.integration.server.SoapHandlerTest;

/**
 * @author Alejandro Gu�zar
 * @version $Revision: 1.3 $ $Date: 2006/08/23 08:00:34 $
 */
public class ServerTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("server tests");
    suite.addTestSuite(SoapHandlerTest.class);
    suite.addTestSuite(RequestListenerTest.class);
    suite.addTestSuite(StartListenerTest.class);
    suite.addTestSuite(ReplierTest.class);
    suite.addTestSuite(SoapClientTest.class);
    suite.addTestSuite(InvokerTest.class);
    return suite;
  }
}
