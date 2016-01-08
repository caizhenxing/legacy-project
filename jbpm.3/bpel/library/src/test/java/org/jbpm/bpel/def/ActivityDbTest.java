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
import org.jbpm.bpel.sublang.def.JoinCondition;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class ActivityDbTest extends AbstractDbTestCase {
  
  public void testBpelDefinition() {
    BpelDefinition processDefinition = new BpelDefinition("p");
    Activity activity = new Empty("activity");
    processDefinition.getGlobalScope().addNode(activity);

    processDefinition = saveAndReload(processDefinition);

    assertNotNull(processDefinition);
    activity = (Activity) processDefinition.getGlobalScope().getNode("activity");
    assertEquals( processDefinition, activity.getProcessDefinition() );
  }
  
  public void testCompositeActivity() {
    BpelDefinition processDefinition = new BpelDefinition();
    CompositeActivity parent = new Sequence("parent");
    processDefinition.addNode(parent);
    
    Activity activity = new Empty("activity");
    parent.addNode(activity);
    
    processDefinition = saveAndReload(processDefinition);

    parent = (CompositeActivity) processDefinition.getNode("parent");
    activity = (Activity) parent.getNode("activity");
    assertNotNull(activity);
    assertNotNull(parent);
    assertSame(activity, parent.getNode("activity"));
    assertSame(activity.getCompositeActivity(), parent);
  }
  
  public void testJoinCondition() {
    BpelDefinition processDefinition = new BpelDefinition();
    Activity activity = new Empty("activity");
    JoinCondition joinCondition = new JoinCondition();
    activity.setJoinCondition(joinCondition);
    processDefinition.addNode(activity);
    
    processDefinition = saveAndReload(processDefinition);

    assertNotNull( ((Activity)processDefinition.getNode("activity")).getJoinCondition() );
  }
  
  public void testSupressJoinFailure() {
    BpelDefinition processDefinition = new BpelDefinition();
    Activity activity = new Empty("activity");
    activity.setSuppressJoinFailure(Boolean.TRUE);
    processDefinition.addNode(activity);

    processDefinition = saveAndReload(processDefinition);

    assertEquals(Boolean.TRUE, ((Activity)processDefinition.getNode("activity")).getSuppressJoinFailure());
  }
  
  public void testSourcesAndTargets() {
    BpelDefinition processDefinition = new BpelDefinition();
    Activity a = new Empty("a");
    Activity b = new Empty("b");
    processDefinition.addNode(a);
    processDefinition.addNode(b);
    
    LinkDefinition link = new LinkDefinition("one");
    a.addSource(link);
    b.addTarget(link);
    
    link = new LinkDefinition("two");
    a.addSource(link);
    b.addTarget(link);
    
    link = new LinkDefinition("three");
    a.addSource(link);
    b.addTarget(link);

    processDefinition = saveAndReload(processDefinition);

    a = (Activity) processDefinition.getNode("a");
    b = (Activity) processDefinition.getNode("b");
    
    assertEquals(3, a.getSources().size());
    assertEquals(0, a.getTargets().size());
    
    assertEquals(0, b.getSources().size());
    assertEquals(3, b.getTargets().size());
    
    assertTrue(a.getSources().containsAll(b.getTargets()));
  }
}
