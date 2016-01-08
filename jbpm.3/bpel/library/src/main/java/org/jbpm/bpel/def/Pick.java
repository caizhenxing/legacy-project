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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.alarm.TimeDrivenActivity;
import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.def.InboundMessageActivity;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.scheduler.SchedulerService;

/**
 * The pick activity awaits the occurrence of one of a set of events and then
 * performs the activity associated with the event that occurred.
 * @see "WS-BPEL 2.0 &sect;12.4"
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class Pick extends StructuredActivity implements TimeDrivenActivity,
    InboundMessageActivity {

  private static final long serialVersionUID = 1L;

  private boolean createInstance;
  private List onMessages = new ArrayList();
  private List onAlarms = new ArrayList();

  public Pick() {
  }

  public Pick(String name) {
    super(name);
  }

  // behaviour methods
  // ///////////////////////////////////////////////////////////////////////////

  public void execute(ExecutionContext exeContext) {
    Token token = exeContext.getToken();
    JbpmContext jbpmContext = exeContext.getJbpmContext();
    // prepare message receivers
    IntegrationService integrationService = Receiver.getIntegrationService(jbpmContext);
    integrationService.receive(onMessages, token);
    // prepare alarms
    SchedulerService schedulerService = Alarm.getSchedulerService(jbpmContext);
    for (int i = 0, n = onAlarms.size(); i < n; i++) {
      Alarm alarm = (Alarm) onAlarms.get(i);
      alarm.createTimer(token, schedulerService);
    }
  }

  public void terminate(ExecutionContext exeContext) {
    Token token = exeContext.getToken();
    JbpmContext jbpmContext = exeContext.getJbpmContext();
    // end message receivers and alarms
    endReceivers(token, jbpmContext);
    endAlarms(token, jbpmContext, null);
  }

  public void messageReceived(Receiver receiver, Token token) {
    JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
    // end message receivers and alarms
    endReceivers(token, jbpmContext);
    endAlarms(token, jbpmContext, null);
    // execute the picked activity
    pickPath(new ExecutionContext(token), getOnMessage(receiver));
  }

  public void alarmFired(Alarm alarm, Token token) {
    JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
    // end message receivers and alarms
    endReceivers(token, jbpmContext);
    endAlarms(token, jbpmContext, alarm);
    // execute the picked activity
    pickPath(new ExecutionContext(token), getOnAlarm(alarm));
  }

  protected void endReceivers(Token token, JbpmContext jbpmContext) {
    IntegrationService integrationService = Receiver.getIntegrationService(jbpmContext);
    integrationService.endReception(onMessages, token);
  }

  protected void endAlarms(Token token, JbpmContext jbpmContext,
      Alarm originator) {
    SchedulerService schedulerService = Alarm.getSchedulerService(jbpmContext);
    for (int i = 0, n = onAlarms.size(); i < n; i++) {
      Alarm alarm = (Alarm) onAlarms.get(i);
      if (alarm != originator) {
        alarm.cancelTimer(token, schedulerService);
      }
    }
  }

  protected void pickPath(ExecutionContext context, Activity activity) {
    for (Iterator it = nodes.iterator(); it.hasNext();) {
      Activity anActivity = (Activity) it.next();
      if (!activity.equals(anActivity)) {
        anActivity.setNegativeLinkStatus(context.getToken());
      }
    }

    Transition transition = activity.getDefaultArrivingTransition();
    getBegin().leave(context, transition);
  }

  // children management
  // /////////////////////////////////////////////////////////////////////////////

  public void attachChild(Activity activity) {
    super.attachChild(activity);
    onMessages.add(null);
    int alarms = onAlarms.size();

    // if alarms exist, move the activity at the last receptor index
    if (alarms > 0) {
      int receptorIndex = onMessages.size() - 1;
      nodes.remove(receptorIndex + alarms);
      nodes.add(receptorIndex, activity);
    }
  }

  public void detachChild(Activity activity) {
    int index = nodes.indexOf(activity);
    if (index < onMessages.size()) {
      onMessages.remove(index);
    }
    else {
      onAlarms.remove(index - onMessages.size());
    }
    super.detachChild(activity);
  }

  // event properties
  // /////////////////////////////////////////////////////////////////////////////

  public List getOnMessages() {
    return onMessages;
  }

  public Activity getOnMessage(Receiver receiver) {
    return (Activity) nodes.get(onMessages.indexOf(receiver));
  }

  public void setOnMessage(Activity activity, Receiver receiver) {
    int activityIndex = nodes.indexOf(activity);
    if (activityIndex == -1) {
      throw new BpelException(
          "Cannot set a message receiver for a non-member activity: "
              + activity);
    }
    // match the positions of the receiver and the activity
    if (activityIndex < onMessages.size()) {
      onMessages.set(activityIndex, receiver);
    }
    else {
      onAlarms.remove(activityIndex - onMessages.size());
      nodes.remove(activityIndex);
      nodes.add(onMessages.size(), activity);
      onMessages.add(receiver);
    }
    // mantain the bidirectional association
    receiver.setInboundMessageActivity(this);
  }

  public List getOnAlarms() {
    return onAlarms;
  }

  public Activity getOnAlarm(Alarm alarm) {
    return (Activity) nodes.get(onMessages.size() + onAlarms.indexOf(alarm));
  }

  public void setOnAlarm(Activity activity, Alarm alarm) {
    int activityIndex = nodes.indexOf(activity);
    if (activityIndex == -1) {
      throw new BpelException("Cannot set an alarm for a non-member activity: "
          + activity);
    }
    // match the positions of the alarm and the activity
    if (activityIndex >= onMessages.size()) {
      int alarmIndex = activityIndex - onMessages.size();
      onAlarms.set(alarmIndex, alarm);
    }
    else {
      onMessages.remove(activityIndex);
      onAlarms.add(alarm);
      nodes.remove(activityIndex);
      nodes.add(activity);
    }
    // mantain the bidirectional association
    alarm.setTimeDrivenActivity(this);
  }

  // Pick properties
  // ///////////////////////////////////////////////////////////////////////////

  public boolean isCreateInstance() {
    return createInstance;
  }

  public void setCreateInstance(boolean createInstance) {
    this.createInstance = createInstance;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}