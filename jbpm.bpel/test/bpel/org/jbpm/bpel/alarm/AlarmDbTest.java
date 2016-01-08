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
package org.jbpm.bpel.alarm;

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Wait;
import org.jbpm.bpel.sublang.def.Expression;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:16 $
 */
public class AlarmDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition;
  Alarm alarm;
  
  public void setUp() throws Exception {
    super.setUp();
    processDefinition = new BpelDefinition();
    Wait wait = new Wait("parent");
    alarm = new Alarm();
    wait.setAlarm(alarm);
    processDefinition.addNode(wait);
  }
  
  public void testRepeatEvery() {    
    Expression cyclicExpr = new Expression();
    alarm.setRepeatEvery(cyclicExpr);
    
    processDefinition = saveAndReload(processDefinition); 
    alarm = getAlarm();

    assertNotNull( alarm.getRepeatEvery() );
  }
  
  public void testUntil() {    
    Expression deadlineExpr = new Expression();
    alarm.setUntil(deadlineExpr);
    
    processDefinition = saveAndReload(processDefinition); 
    alarm = getAlarm();

    assertNotNull( alarm.getUntil() );
  }
  
  public void testFor() {    
    Expression durationExpr = new Expression();
    alarm.setFor(durationExpr);
    
    processDefinition = saveAndReload(processDefinition); 
    alarm = getAlarm();

    assertNotNull( alarm.getFor() );
  }
  
  public void testListener() {
    processDefinition = saveAndReload(processDefinition); 
    alarm = getAlarm();
    
    assertNotNull( alarm.getTimeDrivenActivity() );
  }
  
  private Alarm getAlarm() {
    Wait wait  = ((Wait) processDefinition.getNode("parent"));
    return wait.getAlarm();
  }
}