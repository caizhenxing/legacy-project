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
package org.jbpm.bpel.exe;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jbpm.bpel.exe.flow.DPE1ExampleTest;
import org.jbpm.bpel.exe.flow.DPE2ExampleTest;
import org.jbpm.bpel.exe.flow.FlowGraphExampleTest;
import org.jbpm.bpel.exe.flow.LinkScopingExampleTest;

/**
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class ActivityExeTests {
  
  public static Test suite() {
    TestSuite suite = new TestSuite("activity execution tests");
    
    //basic elements
    suite.addTestSuite(ActivityExeTest.class);
    suite.addTestSuite(StructuredActivityTest.class);
    suite.addTestSuite(LinkInstanceTest.class);
    
    //bpel composite activities
    suite.addTestSuite(ScopeExeTest.class);
    suite.addTestSuite(SequenceExeTest.class);
    suite.addTestSuite(WhileExeTest.class);
    suite.addTestSuite(WhileStackTest.class);
    suite.addTestSuite(PickExeTest.class);
    suite.addTestSuite(FlowExeTest.class);
    suite.addTestSuite(IfExeTest.class);
    suite.addTestSuite(InitialActivitiesTest.class);
    
    //graph related (BPEL spec 1.1 12.5.1 and 12.5.2)
    suite.addTestSuite(DPE1ExampleTest.class);
    suite.addTestSuite(DPE2ExampleTest.class);
    suite.addTestSuite(FlowGraphExampleTest.class);
    suite.addTestSuite(LinkScopingExampleTest.class);
    suite.addTestSuite(ControlDependencyTest.class);
    
    //basic activities
    suite.addTestSuite(AssignExeTest.class);
    
    return suite;
  }
}
