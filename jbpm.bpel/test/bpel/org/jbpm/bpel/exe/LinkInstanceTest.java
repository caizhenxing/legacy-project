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

import junit.framework.TestCase;

import org.jbpm.context.def.ContextDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.LinkDefinition;

/**
 * @author Juan Cantú
 * @version $Revision: 1.6 $ $Date: 2006/08/22 04:13:10 $
 */
public class LinkInstanceTest extends TestCase {
  
  LinkDefinition link;
  Activity activity;
  Activity target;
  Token token;
  String linkName = "testLink";
  
  public void setUp() {
    ProcessDefinition pd = new ProcessDefinition();
    pd.addDefinition(new ContextDefinition());
    ProcessInstance pi = new ProcessInstance(pd);
    token = pi.getRootToken();
    activity = new Empty("node");
    target = new Empty("target");
    link = new LinkDefinition(linkName);
    link.createInstance(token);
  }

  public void testExecuteTrue() throws Exception {
    target.addTarget(link);
    link.setTransitionCondition(ActivityExeTest.TRUE_EXPRESSION);
    link.determineStatus(new ExecutionContext(token));
    
    LinkInstance instance = link.getInstance(token);

    assertEquals( Boolean.TRUE, instance.getStatus() );
  }

  public void testExecuteFalse() throws Exception {
    target.addTarget(link);
    link.setTransitionCondition(ActivityExeTest.FALSE_EXPRESSION);
    link.determineStatus(new ExecutionContext(token));

    LinkInstance instance = link.getInstance(token);

    assertEquals( Boolean.FALSE, instance.getStatus() );
  }
}
