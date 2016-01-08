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
package org.jbpm.bpel.exe.state;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:11 $
 */
public class StateTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("scope state tests"); 

    suite.addTestSuite(CompletedTest.class);
    
    suite.addTestSuite(NormalProcessingTest.class);
    suite.addTestSuite(EventsPendingTest.class);
    
    suite.addTestSuite(TermChildrenTest.class);
    suite.addTestSuite(TermImplicitHandlerTest.class);
    suite.addTestSuite(TermExplicitHandlerTest.class);
    suite.addTestSuite(TermChildrenAtHandlerTest.class);
    
    suite.addTestSuite(FaultChildrenTest.class);
    suite.addTestSuite(FaultImplicitHandlerTest.class);
    suite.addTestSuite(FaultExplicitHandlerTest.class);
    suite.addTestSuite(FaultChildrenAtHandlerTest.class);
    
    suite.addTestSuite(CompImplicitHandlerTest.class);
    suite.addTestSuite(CompExplicitHandlerTest.class);
    suite.addTestSuite(CompChildrenAtHandlerTest.class);
    
    return suite;
  }  
  
}
