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
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class IfDbTest extends AbstractDbTestCase {
  
  public void testCase() {
    BpelDefinition processDefinition = new BpelDefinition();
    If ifStruct = new If("parent");
    processDefinition.addNode(ifStruct);
    Activity activity = new Empty("activity");
    ifStruct.addNode(activity);
    Expression condition = new Expression();
    ifStruct.setCondition(activity, condition);
    
    processDefinition = saveAndReload(processDefinition);

    ifStruct = (If) processDefinition.getNode("parent");
    activity = (Activity) ifStruct.getNode("activity");
    assertNotNull(ifStruct.getCondition(activity));
  }
  
  public void testElse() {
    BpelDefinition processDefinition = new BpelDefinition();
    If ifStruct = new If("parent");
    processDefinition.addNode(ifStruct);
    Activity otherwise = new Empty("otherwise");
    ifStruct.addNode(otherwise);
    ifStruct.setElse(otherwise);
    
    processDefinition = saveAndReload(processDefinition);

    ifStruct = (If) processDefinition.getNode("parent");
    otherwise = ifStruct.getElse();
    assertNotNull( otherwise );
  }  
  
  public void testConnections() {
    BpelDefinition processDefinition = new BpelDefinition();
    If ifStruct = new If("parent");
    processDefinition.addNode(ifStruct);
    Activity activity = new Empty("activity");
    ifStruct.addNode(activity);
    Expression expression = new Expression();
    ifStruct.setCondition(activity, expression);
    
    processDefinition = saveAndReload(processDefinition);

    ifStruct = (If) processDefinition.getNode("parent");
    Activity start = ifStruct.getBegin();
    Activity end = ifStruct.getEnd();
    activity = (Activity) ifStruct.getNode("activity");
    assertTrue(start.getLeavingTransitions().contains(activity.getDefaultArrivingTransition()));
    assertTrue(end.getArrivingTransitions().contains(activity.getDefaultLeavingTransition()));
  }
}