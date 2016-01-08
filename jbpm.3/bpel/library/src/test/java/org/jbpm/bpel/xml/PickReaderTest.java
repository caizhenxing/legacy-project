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
package org.jbpm.bpel.xml;

import java.util.Collections;
import java.util.List;

import javax.wsdl.Output;

import com.ibm.wsdl.OutputImpl;

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.Pick;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.variable.def.MessageType;

/**
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class PickReaderTest extends AbstractReaderTestCase {
  
  public void testCreateInstanceYes() throws Exception {
    initMessageProperties();
    String xml = 
      "<pick createInstance='yes'>" +
      "<onMessage partnerLink='aPartner' operation='o' variable='iv'>" +
      "	<empty name='oM1'/>" +
      "</onMessage>" +      
      "</pick>";
    
    scope.initial = true;
    Pick pick = (Pick) readActivity(xml);
    assertEquals(true, pick.isCreateInstance());
  }
  
  public void testCreateInstanceNo() throws Exception {
    String xml = 
    "<pick createInstance='no'>" +
    "<onAlarm><for>$f</for><empty/></onAlarm>" +
    "</pick>";
    
    Pick pick = (Pick) readActivity(xml);
    assertEquals(false, pick.isCreateInstance());
  }
  
  public void testCreateInstanceDefault() throws Exception {
    String xml = 
    "<pick>" +
    "<onAlarm><for>$f</for><empty/></onAlarm>" +
    "</pick>";
    
    Pick pick = (Pick) readActivity(xml);
    assertEquals(false, pick.isCreateInstance());
  }
  
  public void testOnAlarmFor() throws Exception {
    String xml = 
      "<pick>" +
      "<onAlarm>" +
      "<for>$f</for>" +
      "<empty name='oA1'/>" +
      "</onAlarm>" +
      "</pick>";
    Pick pick = (Pick) readActivity(xml);
    Alarm alarm = ((Alarm) pick.getOnAlarms().iterator().next());
    assertEquals( "$f", alarm.getFor().getText() );
  }
  
  public void testOnAlarmUntil() throws Exception {
    String xml = 
      "<pick>" +
      "<onAlarm>" +
      "<until>$u</until>" +
      "<empty name='oA1'/></onAlarm>" +
      "</pick>";
    
    Pick pick = (Pick) readActivity(xml);
    Alarm alarm = ((Alarm) pick.getOnAlarms().iterator().next());
    assertEquals( "$u", alarm.getUntil().getText() );
  }
  
  public void testOnAlarmRepeat() throws Exception {
    String xml = 
      "<pick>" +
      "<onAlarm>" +
      "<repeatEvery>$r</repeatEvery>" +      
      "<until>$u</until>" +
      "<empty name='oA1'/></onAlarm>" +
      "</pick>";
    
    Pick pick = (Pick) readActivity(xml);
    Alarm alarm = ((Alarm) pick.getOnAlarms().iterator().next());
    assertEquals( "$r", alarm.getRepeatEvery().getText() );
    assertEquals( "$u", alarm.getUntil().getText() );
  }
  
  public void testOnAlarmActivity() throws Exception {
    String xml = 
      "<pick>" +
      "<onAlarm><for>$f</for><empty name='oA1'/></onAlarm>" +
      "</pick>";
    
    Pick pick = (Pick) readActivity(xml);
    List alarms = pick.getOnAlarms();
    assertEquals(1, alarms.size());
    Activity activity = pick.getOnAlarm((Alarm) alarms.get(0));
    assertEquals( Empty.class, activity.getClass());
    assertEquals( "oA1", ((Empty)activity).getName());
  }    
  
  public void testOnMessageActivity() throws Exception {
    initMessageProperties();
    String xml = 
      "<pick>" +
      "<onMessage partnerLink='aPartner' operation='o' variable='iv'>" +
      "	<empty name='oM1'/>" +
      "</onMessage>" +
      "</pick>";
    
    Pick pick = (Pick) readActivity(xml);
    List receivers = pick.getOnMessages();
    assertEquals(1, receivers.size());
    Activity activity = pick.getOnMessage((Receiver) receivers.get(0));
    assertEquals( Empty.class, activity.getClass());
    assertEquals( "oM1", ((Empty)activity).getName());
  }    
  
  public void testOnMessagePartnerLink() throws Exception {
    initMessageProperties();
    String xml = 
      "<pick>" +
      "<onMessage partnerLink='aPartner' operation='o' variable='iv'>" +
      "	<empty name='oM1'/>" +
      "</onMessage>" +
      "</pick>";

    Pick pick = (Pick) readActivity(xml);
    Receiver receiver = (Receiver) pick.getOnMessages().iterator().next();
    assertEquals( pLink, receiver.getPartnerLink() );    
  }  
  
  public void testOnMessagePortType() throws Exception {
    initMessageProperties();
    String xml = 
      "<pick xmlns:def='http://manufacturing.org/wsdl/purchase'>" +
      "<onMessage partnerLink='aPartner' portType='def:mpt' operation='o' variable='iv'>" +
      "	<empty name='oM1'/>" +
      "</onMessage>" +
      "</pick>";
    
    try {
      Pick pick = (Pick) readActivity(xml);
      assertEquals(1, pick.getOnMessages().size());
    }
    catch (Exception e) {
      fail(e.toString());
    }
  }
  
  public void testOnMessagePortTypeDefault() throws Exception {
    initMessageProperties();
    String xml = 
      "<pick>" +
      "<onMessage partnerLink='aPartner' operation='o' variable='iv'>" +
      "	<empty name='oM1'/>" +
      "</onMessage>" +
      "</pick>";
    
    try {
      Pick pick = (Pick) readActivity(xml);
      assertEquals(1, pick.getOnMessages().size());
    }
    catch (Exception e) {
      fail(e.toString());
    }
  }  
  
  public void testOnMessageOperation() throws Exception {
    initMessageProperties();
    String xml = 
      "<pick>" +
      "<onMessage partnerLink='aPartner' operation='o' variable='iv'>" +
      "	<empty name='oM1'/>" +
      "</onMessage>" +
      "</pick>";
    Pick pick = (Pick) readActivity(xml);
    Receiver receiver = (Receiver) pick.getOnMessages().iterator().next();
    assertEquals( "o", receiver.getOperation().getName() );
  }      
  
  public void testOnMessageVariable() throws Exception {
    initMessageProperties();
    MessageType typeInfo = (MessageType) messageVariable.getType();
    Output output = new OutputImpl();
    output.setMessage(typeInfo.getMessage());
    operation.setOutput(output);    
    
    String xml = 
      "<pick>" +
      "<onMessage partnerLink='aPartner' operation='o' variable='iv'>" +
      "	<empty name='oM1'/>" +
      "</onMessage>" +
      "</pick>";

    Pick pick = (Pick) readActivity(xml);
    Receiver receiver = (Receiver) pick.getOnMessages().iterator().next();
    assertEquals(messageVariable, receiver.getVariable() );
  }   
  
  public void testOnMessageCorrelations() throws Exception {
    initMessageProperties();
    CorrelationSetDefinition set = new CorrelationSetDefinition();
    set.setName("corr");
    set.setProperties(Collections.EMPTY_SET);
    scope.addCorrelationSet(set);
    String xml = 
      "<pick>" +
      "<onMessage partnerLink='aPartner' operation='o' variable='iv'>" +
      "	<empty name='oM1'/>" +
      "	<correlations>" +
      "		<correlation set='corr'/> " +
      "	</correlations>" +      
      "</onMessage>" +
      "</pick>";
    Pick pick = (Pick) readActivity(xml);
    Receiver receiver = (Receiver) pick.getOnMessages().iterator().next();
    assertNotNull( receiver.getCorrelations() );
  }
}
