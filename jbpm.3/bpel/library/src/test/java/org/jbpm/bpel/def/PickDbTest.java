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

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.graph.def.Transition;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class PickDbTest extends AbstractDbTestCase {

  BpelDefinition processDefinition;
  Pick pick = new Pick(PICK_NAME);
  Receiver receiver = new Receiver();
  
  static final String PICK_NAME = "p";
  static final String ONMESSAGE_NAME = "om";
  
  public void setUp() throws Exception {
    super.setUp();
    // onMessage
    Activity onMessageActivity = new Empty(ONMESSAGE_NAME);
    // pick
    pick.addNode(onMessageActivity);
    pick.setOnMessage(onMessageActivity, receiver);
    // process, create after opening jbpm context
    processDefinition = new BpelDefinition();
    processDefinition.addNode(pick);
  }

  public void testEvents() {
    // prepare persistent objects
    // onAlarm
    Activity onAlarmActivity = new Empty("oa");
    Alarm alarm = new Alarm();
    // pick
    pick.addNode(onAlarmActivity);
    pick.setOnAlarm(onAlarmActivity, alarm);
    
    // save objects and load them back
    processDefinition = saveAndReload(processDefinition);
    pick = (Pick) processDefinition.getNode(PICK_NAME);    
    receiver = (Receiver) pick.getOnMessages().get(0);
    alarm = (Alarm) pick.getOnAlarms().get(0);
    
    // verify retrieved objects    
    assertEquals(ONMESSAGE_NAME, pick.getOnMessage(receiver).getName());
    assertEquals("oa", pick.getOnAlarm(alarm).getName());    
  } 
  
  public void testConnections() {
    // save objects and load them back
    processDefinition = saveAndReload(processDefinition);
    pick = (Pick) processDefinition.getNode(PICK_NAME);
    Activity onMessageActivity = (Activity) pick.getNode(ONMESSAGE_NAME);
    Transition arrivingTransition = onMessageActivity.getDefaultArrivingTransition();
    Transition leavingTransition = onMessageActivity.getDefaultLeavingTransition();
    
    // verify retrieved objects
    assertTrue(pick.getBegin().getLeavingTransitions().contains(arrivingTransition));
    assertTrue(pick.getEnd().getArrivingTransitions().contains(leavingTransition));
  }
}