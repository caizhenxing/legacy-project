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

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.jbpm.JbpmContext;
import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.db.ScopeSession;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.OnAlarm;
import org.jbpm.bpel.def.OnEvent;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.exe.ScopeIterator.ScopeFilter;
import org.jbpm.bpel.exe.state.ActiveState;
import org.jbpm.bpel.exe.state.EndedState;
import org.jbpm.bpel.exe.state.HandlingState;
import org.jbpm.bpel.exe.state.ScopeState;
import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.scheduler.SchedulerService;

/**
 * @author Juan Cantú
 * @version $Revision: 1.6 $ $Date: 2006/08/21 01:05:59 $
 */
public class ScopeInstance implements Serializable, CompensationListener {

  long id;
  private Scope definition;
  private ScopeState state;
  private Token token;
  private FaultInstance faultInstance;
  private CompensationListener compensationListener;

  static final String ACTIVITY_TOKEN = "normalFlow";
  static final String HANDLER_TOKEN = "handlerFlow";
  static final String EVENT_TOKEN = "eventFlow";
  static final String EVENTS_TOKEN = "events";

  public static final ScopeFilter CHILDREN_TO_CANCEL = new ScopeFilter(
      new Class[] { ActiveState.class, HandlingState.class });

  public static final ScopeFilter CHILDREN_TO_COMPENSATE = new ScopeFilter(
      new Class[] { EndedState.COMPLETED.getClass() });

  private static final long serialVersionUID = 1L;

  protected ScopeInstance() {
  }

  public ScopeInstance(Scope definition, Token token) {
    this.definition = definition;
    this.token = token;
    // normal processing is initial state
    setState(ActiveState.NORMAL_PROCESSING);
    // the execution context will wrap a token exclusive for the activity
    new Token(token, ACTIVITY_TOKEN);
  }

  // signals
  // ///////////////////////////////////////////////////////////////////////////

  public void faulted(FaultInstance faultInstance) {
    this.faultInstance = faultInstance;
    state.faulted(this);
  }

  public void completed() {
    state.completed(this);
  }

  public void terminate() {
    state.terminate(this);
  }

  public void compensate(CompensationListener compensationListener) {
    this.compensationListener = compensationListener;
    state.compensate(this);
  }

  public void exit() {
    token.end();
    performExit();
  }

  public void scopeCompensated(ScopeInstance child) {
    ScopeSession scopeSession = ScopeSession.getInstance(JbpmContext.getCurrentJbpmContext());
    ScopeInstance nextChild = scopeSession.nextChildToCompensate(this);
    if (nextChild == null) {
      state.childrenCompensated(this);
    }
    else {
      nextChild.compensate(this);
    }
  }

  public void scopeTerminated(ScopeInstance child) {
    Iterator it = new ScopeIterator(token, CHILDREN_TO_CANCEL);
    if (!it.hasNext()) {
      state.childrenTerminated(this);
    }
  }

  // behavior methods
  // ///////////////////////////////////////////////////////////////////////////

  public void enableEvents() {
    Scope scope = getDefinition();
    Collection onEvents = scope.getOnEvents();
    Collection onAlarms = scope.getOnAlarms();

    // easy way out: no events to enable
    if (onEvents.isEmpty() && onAlarms.isEmpty()) return;

    // eventToken is the context for event handlers
    Token eventToken = new Token(token, EVENTS_TOKEN);
    eventToken.setNode(scope);

    JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

    // enable message events
    IntegrationService relationService = Receiver.getIntegrationService(jbpmContext);
    Iterator onEventIt = onEvents.iterator();
    while (onEventIt.hasNext()) {
      OnEvent onEvent = (OnEvent) onEventIt.next();
      relationService.receive(onEvent.getReceiver(), eventToken);
    }

    // enable alarm events
    SchedulerService schedulerService = Alarm.getSchedulerService(jbpmContext);
    Iterator alarmHandlerIt = onAlarms.iterator();
    while (alarmHandlerIt.hasNext()) {
      OnAlarm alarmHandler = (OnAlarm) alarmHandlerIt.next();
      alarmHandler.getAlarm().createTimer(eventToken, schedulerService);
    }
  }

  public void disableEvents() {
    // disable events
    Token eventsToken = token.getChild(EVENTS_TOKEN);
    // easy way out: no events to disable
    if (eventsToken == null) return;

    Scope scope = getDefinition();
    JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();

    // disable message events
    IntegrationService integrationService = Receiver.getIntegrationService(jbpmContext);
    Iterator onEventIt = scope.getOnEvents().iterator();
    while (onEventIt.hasNext()) {
      OnEvent onEvent = (OnEvent) onEventIt.next();
      integrationService.endReception(onEvent.getReceiver(), eventsToken);
    }

    // disable alarm events
    SchedulerService schedulerService = Alarm.getSchedulerService(jbpmContext);
    Iterator onAlarmIt = scope.getOnAlarms().iterator();
    while (onAlarmIt.hasNext()) {
      OnAlarm onAlarm = (OnAlarm) onAlarmIt.next();
      onAlarm.getAlarm().cancelTimer(eventsToken, schedulerService);
    }
  }

  public boolean hasPendingEvents() {
    Token eventToken = token.getChild(EVENTS_TOKEN);
    if (eventToken == null) return false;

    ScopeIterator it = new ScopeIterator(eventToken);

    while (it.hasNext()) {
      ScopeInstance child = (ScopeInstance) it.next();
      if (!child.getState().equals(EndedState.COMPLETED)) return true;
    }
    return false;
  }

  public void cancelChildren() {
    Token activityToken = token.getChild(ACTIVITY_TOKEN);
    if (!activityToken.hasEnded()) {
      // terminate the current flow
      terminateToken(activityToken);
      // current flow is the normal flow. Check if some event tokens need to be
      // cancelled.
      Token eventsToken = token.getChild(EVENTS_TOKEN);
      if (eventsToken != null && !eventsToken.hasEnded()) {
        // stop listening for events
        disableEvents();
        // terminate active event tokens
        Iterator eventTokenIt = eventsToken.getChildren().values().iterator();
        while (eventTokenIt.hasNext()) {
          Token eventToken = (Token) eventTokenIt.next();
          if (!eventToken.hasEnded()) terminateToken(eventToken);
        }
        // end events token
        eventsToken.end(false);
      }
    }
    else {
      // the current flow is the handler flow
      Token handlerToken = token.getChild(HANDLER_TOKEN);
      if (handlerToken != null && !handlerToken.hasEnded())
        terminateToken(handlerToken);
    }
    // notify children termination if there are no child scope instances being
    // canceled
    Iterator it = new ScopeIterator(token, CHILDREN_TO_CANCEL);
    if (!it.hasNext()) state.childrenTerminated(this);
  }

  private void terminateToken(Token token) {
    // cancel atomic activities
    Activity activity = ((Activity) token.getNode());
    if (activity != null) {
      activity.terminate(new ExecutionContext(token));
    }
    // end children token (do not verify parent termination)
    token.end(false);
  }

  private void performExit() {
    setState(EndedState.EXITED);
    ScopeIterator it = new ScopeIterator(token);
    while (it.hasNext()) {
      ScopeInstance child = (ScopeInstance) it.next();
      child.performExit();
    }
  }

  public void notifyCompletion() {
    // notify target node
    ScopeInstance parent = getParent();
    if (parent != null) {
      // TODO multiple-scope behavior
      Token parentToken = token.getParent();
      token.setAbleToReactivateParent(false);
      parentToken.getNode().leave(new ExecutionContext(parentToken));
    }
    else {
      // complete scope token (child tokens will not end it implicitly)
      token.end();
    }
  }

  // token methods
  // ///////////////////////////////////////////////////////////////////////////

  public Token getActivityToken() {
    return token.getChild(ACTIVITY_TOKEN);
  }

  public Token createEventToken() {
    Token eventsToken = token.getChild(EVENTS_TOKEN);
    Map eventTokens = eventsToken.getChildren();
    return new Token(eventsToken, EVENT_TOKEN
        + (eventTokens != null ? eventTokens.size() : 0));
  }

  public Token createHandlerToken() {
    if (getHandlerToken() != null) {
      throw new AssertionError("Scope instance already has a handler flow");
    }
    return new Token(token, HANDLER_TOKEN);
  }

  public Token getHandlerToken() {
    return token.getChild(HANDLER_TOKEN);
  }

  public Map getEventTokens() {
    return token.getChild(EVENTS_TOKEN).getChildren();
  }

  public Token getEventToken(int index) {
    return token.getChild(EVENTS_TOKEN).getChild(EVENT_TOKEN + index);
  }

  // getters and setters
  // ///////////////////////////////////////////////////////////////////////////

  public ScopeState getState() {
    return state;
  }

  public void setState(ScopeState state) {
    this.state = state;
  }

  public FaultInstance getFaultInstance() {
    return faultInstance;
  }

  public void setFaultInstance(FaultInstance faultInstance) {
    this.faultInstance = faultInstance;
  }

  public CompensationListener getCompensationListener() {
    return compensationListener;
  }

  public void setCompensationListener(CompensationListener compensationListener) {
    this.compensationListener = compensationListener;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

  public Scope getDefinition() {
    return definition;
  }

  // operations helper methods
  // ///////////////////////////////////////////////////////////////////////////

  public ScopeInstance getParent() {
    Token parentToken = token.getParent();
    return parentToken != null ? Scope.getInstance(parentToken) : null;
  }

  public String toString() {
    return new ToStringBuilder(this).append("name", getDefinition().getName())
        .append("id", id)
        .toString();
  }
}