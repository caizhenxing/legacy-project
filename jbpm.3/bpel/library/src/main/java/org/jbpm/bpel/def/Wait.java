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

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.scheduler.SchedulerService;

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.alarm.TimeDrivenActivity;

/**
 * The wait activity allows a business process to specify a delay for a certain
 * period of time or until a certain deadline is reached
 * @see "WS-BPEL 2.0 &sect;11.7"
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class Wait extends Activity implements TimeDrivenActivity {

  private static final long serialVersionUID = 1L;

  private Alarm alarm;

  public Wait() {
  }

  public Wait(String name) {
    super(name);
  }

  public void execute(ExecutionContext exeContext) {
    SchedulerService scheduler = Alarm.getSchedulerService(exeContext.getJbpmContext());
    alarm.createTimer(exeContext.getToken(), scheduler);
  }

  public void terminate(ExecutionContext exeContext) {
    SchedulerService scheduler = Alarm.getSchedulerService(exeContext.getJbpmContext());
    alarm.cancelTimer(exeContext.getToken(), scheduler);
  }

  public void alarmFired(Alarm anAlarm, Token token) {
    leave(new ExecutionContext(token));
  }

  public Alarm getAlarm() {
    return alarm;
  }

  public void setAlarm(Alarm alarm) {
    this.alarm = alarm;
    alarm.setTimeDrivenActivity(this);
  }

  /** {@inheritDoc}* */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}
