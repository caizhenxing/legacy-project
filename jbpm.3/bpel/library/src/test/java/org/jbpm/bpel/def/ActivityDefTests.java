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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class ActivityDefTests {
  
  public static Test suite() {
    TestSuite suite = new TestSuite("activity definition tests");

    //structured tests
    suite.addTestSuite(SequenceDefTest.class);
    suite.addTestSuite(WhileDefTest.class);
    suite.addTestSuite(PickDefTest.class);
    suite.addTestSuite(FlowDefTest.class);
    suite.addTestSuite(IfDefTest.class);
      
    //basic activities
    //TODO
    
    return suite;
  }
}
