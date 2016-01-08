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

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.bpel.xml.util.Duration;
import org.jbpm.graph.exe.Token;
import org.jbpm.scheduler.exe.Timer;

/**
 * @author Juan Cantu
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class AlarmExeTest extends AbstractDbTestCase {
  
  private Alarm alarm = new Alarm();
  private Token token = new Token();

  public void setUp() throws Exception {
    super.setUp();
    
    session.save(alarm);
    session.save(token);    
  }

  public void testCreateTimer() {
    Expression until = new Expression();
    until.setText("'2101-04-07'");
    
    Expression repeatEvery = new Expression();
    repeatEvery.setText("'P3D'");
    
    alarm.setUntil(until);
    alarm.setRepeatEvery(repeatEvery);
    
    alarm.createTimer(token, Alarm.getSchedulerService(jbpmContext));
    
    List timers = findTimersByToken(token);
    assertEquals(1, timers.size());
    
    Timer timer = (Timer) timers.get(0);
    assertSame(alarm, timer.getAction());
    
    Date dueDate = DatatypeUtil.parseDateTime("2101-04-07").getTime();
    assertEquals(dueDate, timer.getDueDate());
    
    String repeat = (Duration.parseDuration("P3D").getTimeInMillis() / 1000L) + " seconds";
    assertEquals(repeat, timer.getRepeat());
  }

  public void testCancelTimer() {
    Expression forExpr = new Expression();
    forExpr.setText("'PT1H'");
    
    alarm.setFor(forExpr);
    
    alarm.createTimer(token, Alarm.getSchedulerService(jbpmContext));
    
    List timers = findTimersByToken(token);
    assertEquals(1, timers.size());
    
    Timer timer = (Timer) timers.get(0);
    assertSame(alarm, timer.getAction());
    
    alarm.cancelTimer(token, Alarm.getSchedulerService(jbpmContext));
    
    timers = findTimersByToken(token);
    assertTrue(timers.isEmpty());
  }
  
  private List findTimersByToken(Token token) {
    return session.createCriteria(Timer.class)
    .add(Restrictions.eq("token", token))
    .list();
  }
}
