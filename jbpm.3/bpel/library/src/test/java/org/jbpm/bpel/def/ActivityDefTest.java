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

import junit.framework.TestCase;

import org.jbpm.context.def.ContextDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ActivityDefTest extends TestCase {
  
  LinkDefinition link;
  Activity activity;
  String linkName = "testLink";
  
  public void setUp() {
    ProcessDefinition pd = new ProcessDefinition();
    pd.addDefinition(new ContextDefinition());
    activity = new Empty();
    link = new LinkDefinition(linkName);
  }
  
  public void testDefaultArrivingTransition() {
    Transition t = new Transition();
    activity.addArrivingTransition( t );
    assertEquals(t, activity.getDefaultArrivingTransition());
  }
  
  public void testSetSource() {
    activity.addSource(link);
    assertEquals(link, activity.getSources().iterator().next());
  }

  public void testSetTarget() {
    activity.addTarget(link);
    assertEquals(link.getTarget(), activity);
    assertTrue(activity.getTargets().contains(link));
  }
}
