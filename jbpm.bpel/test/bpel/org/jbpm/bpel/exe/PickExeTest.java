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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.LinkDefinition;
import org.jbpm.bpel.def.Pick;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.integration.def.Receiver;

/**
 * @author Juan Cantú
 * @version $Revision: 1.19 $ $Date: 2006/08/22 04:13:10 $
 */
public class PickExeTest extends AbstractExeTestCase {

  Pick pick;
  Set activities;
  static final String initialXml = 
  	"<pick createInstance='yes'>" +
      "	<onMessage partnerLink='aPartner' operation='o'>" +
      "	<receive name='a' partnerLink='aPartner' operation='o'/>" +
      "	</onMessage>" +
      "	<onMessage partnerLink='aPartner' operation='o'>" +
      "	<receive name='b' partnerLink='aPartner' operation='o'/>" +
      "	</onMessage>" +
  	"</pick>";
    
  public void initActivities() {    
    activities = new HashSet();
    activities.addAll(pick.getNodes());
    
    Token root = pi.getRootToken();
    for(Iterator it = activities.iterator(); it.hasNext();) {
      Activity activity = ((Activity)it.next());
      TestLink link = new TestLink(activity.getName()); 
      activity.addSource(link);
      link.createInstance(root);
    }
  }
  
  public void testInitialFirstReceiver() throws Exception {
    pick = (Pick) readActivity(initialXml, true);
    plugOuter(pick);
    initActivities();
    
    Receiver firstReceiver = (Receiver) pick.getOnMessages().get(0);
    validateInitial(firstReceiver);
  }
  
  public void testInitialSecondReceiver() throws Exception {
    pick = (Pick) readActivity(initialXml, true);
    plugOuter(pick);
    initActivities();
    
    Receiver secondReceiver = (Receiver) pick.getOnMessages().get(0);
    validateInitial(secondReceiver);
  }
  
  private void validateInitial(Receiver receiver) {
    Token token = executeOuter(receiver);
    Receive pickedReceive = (Receive)pick.getOnMessage(receiver);
    assertReceiveAndComplete(token, pickedReceive);
    assertReceiversDisabled(token, receiver);
    assertLinksDetermined(token, pickedReceive);
  }
  
  static final String xml =
    "<pick>" +
    "	<onMessage partnerLink='aPartner' operation='o'>" +
    "	 <receive name='a' partnerLink='aPartner' operation='o'/>" +
    "	</onMessage>" +
    "	<onMessage partnerLink='aPartner' operation='o'>" +
    "	 <receive name='b' partnerLink='aPartner' operation='o'/>" +
    "	</onMessage>" +
    "	<onAlarm>" +
    "  <for>$f</for>" +
    "	 <receive name='c' partnerLink='aPartner' operation='o'/>" +
    "	</onAlarm>" +
    "	<onAlarm>" +
    "  <until>$u</until>" +
    "	 <receive name='d' partnerLink='aPartner' operation='o'/>" +
    "	</onAlarm>" +
    "</pick>";
  
  public void testInnerFirstReceiver() throws Exception {
    pick = (Pick) readActivity(xml, false);
    plugInner(pick);
    initActivities();
    
    Receiver firstReceiver = (Receiver) pick.getOnMessages().get(0); 
    validateInner(firstReceiver);
  }
  
  public void testInnerSecondReceiver() throws Exception {
    pick = (Pick) readActivity(xml, false);
  	plugInner(pick);
    initActivities();
    
    Receiver secondReceiver = (Receiver) pick.getOnMessages().get(1);
    validateInner(secondReceiver);
  }
  
  public void testInnerFirstAlarm() throws Exception {
    pick = (Pick) readActivity(xml, false);
    plugInner(pick);
    initActivities();
    
    Alarm alarm0 = (Alarm) pick.getOnAlarms().get(0);
    validateInner(alarm0);
  }
  
  public void testInnerSecondAlarm() throws Exception {
    pick = (Pick) readActivity(xml, false);
  	plugInner(pick);
    initActivities();
    
    Alarm alarm1 = (Alarm) pick.getOnAlarms().get(1);
    validateInner(alarm1);    
  }
  
  private void validateInner(Receiver receiver) {
    Token token = executeInner();
    Receive pickedReceive = (Receive) pick.getOnMessage(receiver);
    assertEquals(pick.getBegin(), token.getNode());
    pick.messageReceived(receiver, token);
    assertSame( pickedReceive , token.getNode() );

    assertReceiveAndComplete(token, pickedReceive);
    assertReceiversDisabled(token, receiver);
    assertAlarmsDisabled(token, null);
    assertLinksDetermined(token, pickedReceive);
  }
  
  private void validateInner(Alarm alarm) {
    Token token = executeInner();
    assertEquals(pick.getBegin(), token.getNode());
    alarm.getTimeDrivenActivity().alarmFired(alarm, token);
    Receive pickedReceive = (Receive) pick.getOnAlarm(alarm);

    assertReceiveAndComplete(token, pickedReceive);
    assertReceiversDisabled(token, null);
    assertAlarmsDisabled(token, alarm);
    assertLinksDetermined(token, pickedReceive);
  }
  
  private void assertAlarmsDisabled(Token token, Alarm alarm) {
    for (Iterator it = pick.getOnAlarms().iterator(); it.hasNext();) {
      Alarm other = (Alarm) it.next();
      if (other != alarm) assertAlarmDisabled(token, other);
    }
  }
  
  private void assertReceiversDisabled(Token token, Receiver event) {
    for (Iterator it = pick.getOnMessages().iterator(); it.hasNext();) {
      Receiver other = (Receiver) it.next();
      if (other != event) assertReceiverDisabled(token, other);
    }
  }
  
  private void assertLinksDetermined(Token token, Activity eventActivity) {
    for(Iterator it = activities.iterator(); it.hasNext();) {
      Activity activity = ((Activity)it.next());
      LinkDefinition source = activity.getSource(activity.getName());
      Boolean reached = source.getInstance(token).getStatus();
      
      if(activity.equals(eventActivity)) {
        assertNotNull( reached );
      }
      else {
        assertEquals( Boolean.FALSE, reached );  
      }
    }
  }
}