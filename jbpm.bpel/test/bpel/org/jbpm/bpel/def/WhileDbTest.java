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
 * @version $Revision: 1.6 $ $Date: 2006/08/22 04:13:10 $
 */
public class WhileDbTest extends AbstractDbTestCase {
  
  public void testCondition() {
    // setup the persistent objects
    BpelDefinition processDefinition = new BpelDefinition();
    // while
    While structure = new While("parent");
    processDefinition.addNode(structure);
    // condition
    Expression condition = new Expression();
    condition.setText("false()");
    structure.setCondition(condition);
    /// save the ojbects and load them back    
    processDefinition = saveAndReload(processDefinition);
    
    structure = (While) processDefinition.getNode("parent");
    assertEquals("false()", structure.getCondition().getText());
  }
  
  public void testConnections() {
    BpelDefinition processDefinition = new BpelDefinition();
    While structure = new While("parent");
    processDefinition.addNode(structure);
    Activity activity = new Empty("activity");
    structure.addNode(activity);
    
    processDefinition = saveAndReload(processDefinition);

    structure = (While) processDefinition.getNode("parent");
    Activity start = structure.getBegin();
    Activity end = structure.getEnd();
    Activity loopNode = structure.getLoopNode();
    activity = (Activity) structure.getNode("activity");
    
    assertNotNull(loopNode);
    assertEquals(structure, loopNode.getCompositeActivity());
    
    assertEquals( start.getDefaultLeavingTransition().getTo(), loopNode );
    assertEquals( end.getDefaultArrivingTransition().getFrom(), loopNode );
    
    assertTrue( loopNode.getArrivingTransitions().contains(activity.getDefaultLeavingTransition()) );
    assertTrue( loopNode.getLeavingTransitions().contains(activity.getDefaultArrivingTransition()) );
    assertTrue( loopNode.getArrivingTransitions().contains(activity.getDefaultLeavingTransition()) );
  }
}