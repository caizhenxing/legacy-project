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

import java.util.Calendar;
import java.util.Date;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.bpel.xml.util.Duration;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.scheduler.SchedulerService;
import org.jbpm.scheduler.exe.Timer;

/**
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class Alarm extends Action {

  private Expression forExpr;
  private Expression until;
  private Expression repeatEvery;

  private TimeDrivenActivity timeDrivenActivity;

  private static final String ALARM_NAME = "a:";
  private static final long serialVersionUID = 1L;

  public Alarm() {
  }

  public void createTimer(Token token, SchedulerService scheduler) {
    Timer timer = new Timer(token);
    timer.setName(ALARM_NAME + getId());
    timer.setAction(this);

    // calculate due date
    Calendar dueDate = null;
    if (until != null) {
      // until evaluates to a date/dateTime
      dueDate = DatatypeUtil.toDateTime(until.getEvaluator().evaluate(token));
    }
    else if (forExpr != null) {
      // for evaluates to a duration
      Duration dueDuration = DatatypeUtil.toDuration(forExpr.getEvaluator()
          .evaluate(token));
      // add duration to present instant to give due date
      dueDate = Calendar.getInstance();
      dueDuration.addTo(dueDate);
    }
    else {
      throw new BpelException("neither until nor for specified for alarm: "
          + this);
    }
    long dueDateMs = dueDate.getTimeInMillis();
    timer.setDueDate(new Date(dueDateMs));

    // calculate repeat interval, if any
    if (repeatEvery != null) {
      /*
       * the jBPM scheduler supports only one duration unit, we use seconds
       * since it converts any other unit to seconds anyway repeatEvery
       * evaluates to a duration
       */
      Duration repeatDuration = DatatypeUtil.toDuration(repeatEvery.getEvaluator()
          .evaluate(token));
      // convert duration to milliseconds
      long repeatDurationMs = repeatDuration.getTimeInMillis();
      timer.setRepeat((repeatDurationMs / 1000) + " seconds");
    }
    scheduler.createTimer(timer);
  }

  public void execute(ExecutionContext exeContext) throws Exception {
    timeDrivenActivity.alarmFired(this, exeContext.getToken());
  }

  public void cancelTimer(Token token, SchedulerService scheduler) {
    scheduler.cancelTimersByName(ALARM_NAME + getId(), token);
  }

  public static SchedulerService getSchedulerService(JbpmContext jbpmContext) {
    return jbpmContext.getServices().getSchedulerService();
  }

  public Expression getFor() {
    return forExpr;
  }

  public void setFor(Expression forExpr) {
    this.forExpr = forExpr;
  }

  public Expression getUntil() {
    return until;
  }

  public void setUntil(Expression until) {
    this.until = until;
  }

  public Expression getRepeatEvery() {
    return repeatEvery;
  }

  public void setRepeatEvery(Expression repeatEvery) {
    this.repeatEvery = repeatEvery;
  }

  public TimeDrivenActivity getTimeDrivenActivity() {
    return timeDrivenActivity;
  }

  public void setTimeDrivenActivity(TimeDrivenActivity timeDrivenActivity) {
    this.timeDrivenActivity = timeDrivenActivity;
  }
}
