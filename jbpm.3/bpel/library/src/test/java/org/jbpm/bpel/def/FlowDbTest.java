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
import org.jbpm.bpel.sublang.def.Expression;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class FlowDbTest extends AbstractDbTestCase {
  
  public void testLinks() {
    BpelDefinition processDefinition = new BpelDefinition();
    Flow flow = new Flow("parent");
    processDefinition.addNode(flow);
    Activity activity = new Empty("linked");
    flow.addNode(activity);
    LinkDefinition a = new LinkDefinition("a");
    Expression expression = new Expression();
    a.setTransitionCondition(expression);
    LinkDefinition b = new LinkDefinition("b");
    activity.addTarget(a);
    activity.addSource(b);
    flow.addLink(a);
    flow.addLink(b);
    
    processDefinition = saveAndReload(processDefinition);

    flow = (Flow) processDefinition.getNode("parent");
    assertEquals(2, flow.getLinks().size());

    a = flow.getLink("a");
    assertNotNull( a.getTransitionCondition() );
    assertEquals("linked", a.getTarget().getName());
    
    b= flow.getLink("b");
    assertNull( b.getTransitionCondition() );
    assertEquals("linked", b.getSource().getName());
  }
  
  public void testConnections() {
    BpelDefinition processDefinition = new BpelDefinition();
    
    Flow flow = new Flow("parent");
    processDefinition.addNode(flow);
    Activity activity = new Empty("activity");
    flow.addNode(activity);
    
    processDefinition = saveAndReload(processDefinition);

    flow = (Flow) processDefinition.getNode("parent");
 
    Activity start = flow.getBegin();
    Activity end = flow.getEnd();
    
    activity = (Activity) flow.getNode("activity");
    
    assertTrue(start.getLeavingTransitions().contains(activity.getDefaultArrivingTransition()));
    assertTrue(end.getArrivingTransitions().contains(activity.getDefaultLeavingTransition()));
  }    
}