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

import java.util.Collection;

import junit.framework.TestCase;

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.integration.def.Receiver;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class PickDefTest extends TestCase {
  Pick pick;
  Activity first;
  Activity second;
  
  public void setUp() {
    pick = new Pick();
    first = new Empty("first");
    second = new Empty("second");
  }
  
  public void testAddNode() {    
    pick.addNode(first);
    assertConnected(first);
    pick.addNode(second);
    assertConnected(second);
    assertEquals(2, pick.getOnMessages().size());
  }
  
  public void testRemoveNode() {
    pick.addNode(first);
    pick.addNode(second);
    pick.setOnAlarm(first, new Alarm());
    pick.setOnMessage(second, new Receiver());
    
    pick.removeNode(first);
    assertDisconnected(first);
    
    pick.removeNode(second);
    assertDisconnected(second);
    
    assertEquals(0, pick.getBegin().getLeavingTransitions().size());
    assertEquals(0, pick.getEnd().getArrivingTransitions().size());
    
    assertEquals(0, pick.getNodes().size());
    assertEquals(0, pick.getOnMessages().size());
    assertEquals(0, pick.getOnAlarms().size());
  }
  
  public void testOnAlarm() {
    Alarm firstAlarm = new Alarm();
    Alarm secondAlarm = new Alarm();
    pick.addNode(first);
    pick.addNode(second);
    pick.setOnAlarm(first, firstAlarm);
    pick.setOnAlarm(second, secondAlarm);

    assertEquals(first, pick.getOnAlarm(firstAlarm));
    assertEquals(second, pick.getOnAlarm(secondAlarm));
  }
  
  public void testOnMessage() {
    Receiver firstReceiver = new Receiver();
    Receiver secondReceiver = new Receiver();
    pick.addNode(first);
    pick.addNode(second);
    pick.setOnMessage(first, firstReceiver);
    pick.setOnMessage(second, secondReceiver);

    assertEquals(first, pick.getOnMessage(firstReceiver));
    assertEquals(second, pick.getOnMessage(secondReceiver));
  }
  
  public void testOnMessageOverride() {   
    Receiver receiver = new Receiver();
    pick.addNode(first);
    pick.addNode(second);
    
    pick.setOnAlarm(first, new Alarm());
    pick.setOnMessage(first, receiver);

    assertEquals(first, pick.getOnMessage(receiver));
  }
  
  public void testOnAlarmOverride() {   
    Alarm alarm = new Alarm();
    pick.addNode(first);
    pick.addNode(second);
    
    pick.setOnMessage(first, new Receiver());
    pick.setOnAlarm(first, alarm);

    assertEquals(first, pick.getOnAlarm(alarm));
  }
  
  private void assertConnected(Activity activity) {
    Collection transitions = pick.getBegin().getLeavingTransitions();
    assertTrue( transitions.contains( activity.getDefaultArrivingTransition() ) );
    
    transitions = pick.getEnd().getArrivingTransitions();
    assertTrue( transitions.contains(activity.getDefaultLeavingTransition()) );
  }
  
  private void assertDisconnected(Activity activity) {
    //validate that removed activity doesn't have incoming / outgoing transitions
    assertEquals(0, activity.getArrivingTransitions().size());
    assertEquals(0, activity.getLeavingTransitions().size());
  }
 
}
