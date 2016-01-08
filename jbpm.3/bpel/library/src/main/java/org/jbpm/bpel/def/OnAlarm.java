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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.alarm.TimeDrivenActivity;
import org.jbpm.bpel.exe.EventInstance;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.bpel.exe.state.ActiveState;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class OnAlarm extends ScopeHandler implements TimeDrivenActivity {

  public OnAlarm() {
    super();
  }

  private static final Log log = LogFactory.getLog(OnAlarm.class);
  private static final long serialVersionUID = 1L;

  private Alarm alarm;

  public void alarmFired(Alarm alarm, Token token) {
    ScopeInstance scopeInstance = Scope.getInstance(token);
    if (!scopeInstance.getState().equals(ActiveState.NORMAL_PROCESSING)) {
      log.debug("scope is no longer in normal processing mode, ignoring: alarm="
          + alarm + ", scope=" + scope);
      return;
    }
    Token child = scopeInstance.createEventToken();
    // event instance
    EventInstance.create(scope, child);
    execute(new ExecutionContext(child));
  }

  public Alarm getAlarm() {
    return alarm;
  }

  public void setAlarm(Alarm alarm) {
    this.alarm = alarm;
    alarm.setTimeDrivenActivity(this);
  }
}
