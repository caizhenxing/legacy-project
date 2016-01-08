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
package org.jbpm.bpel.def.assign;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:10 $
 */
public class AssignTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("assignment tests");
    
    // from
    suite.addTestSuite(FromVariableTest.class);
    suite.addTestSuite(FromPropertyTest.class);
    suite.addTestSuite(FromPartnerLinkTest.class);
    suite.addTestSuite(FromExpressionTest.class);
    
    // to
    suite.addTestSuite(ToVariableTest.class);
    suite.addTestSuite(ToPropertyTest.class);
    suite.addTestSuite(ToExpressionTest.class);
    
    return suite;
  }
}
