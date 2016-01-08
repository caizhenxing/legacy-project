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

import org.jbpm.bpel.integration.catalog.URLCatalogTest;
import org.jbpm.bpel.integration.exe.CorrelationSetTest;
import org.jbpm.bpel.integration.exe.wsa.WsaEndpointReferenceTest;
import org.jbpm.bpel.integration.exe.wsdl.WsdlEndpointReferenceTest;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:16 $
 */
public class IntegrationTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("integration tests");
    suite.addTestSuite(URLCatalogTest.class);
    suite.addTestSuite(CorrelationSetTest.class);
    suite.addTestSuite(WsaEndpointReferenceTest.class);
    suite.addTestSuite(WsdlEndpointReferenceTest.class);
    return suite;
  }
}
