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
import org.jbpm.bpel.sublang.def.Expression;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.5 $ $Date: 2006/09/12 06:48:12 $
 */
public class OnAlarmDbTest extends AbstractHandlerDbTestCase {

  protected ScopeHandler create(BpelDefinition process) {
    OnAlarm onAlarm = new OnAlarm();
    process.getGlobalScope().addOnAlarm(onAlarm);
    return onAlarm;
  }

  protected ScopeHandler get(BpelDefinition process) {
    return (ScopeHandler) process.getGlobalScope().getOnAlarms().iterator().next();
  }
  
  public void testAlarm() {
    // script
    String deadline = "'1982-10-04'";
    Expression script = new Expression();
    script.setText(deadline);
    // alarm
    Alarm alarm = new Alarm();
    alarm.setUntil(script);
    // handler
    OnAlarm onAlarm = (OnAlarm) scopeHandler;
    onAlarm.setAlarm(alarm);
    
    // save objects and load them back
    BpelDefinition process = saveAndReload(onAlarm.getBpelDefinition());
    onAlarm = (OnAlarm) get(process);
    
    // verify retrieved objects
    assertEquals(deadline, onAlarm.getAlarm().getUntil().getText());
  }

}
