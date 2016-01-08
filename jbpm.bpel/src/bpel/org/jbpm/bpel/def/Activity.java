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

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang.ClassUtils;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.exe.LinkInstance;
import org.jbpm.bpel.sublang.def.JoinCondition;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.graph.def.Event;
import org.jbpm.graph.def.GraphElement;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * Represents a BPEL activity.
 * @see "WS-BPEL 2.0 &sect;11.1, &sect;11.2"
 * @author Juan Cantú
 * @version $Revision: 1.24 $ $Date: 2006/09/13 07:56:57 $
 */
public abstract class Activity extends Node {

  private Boolean suppressJoinFailure;
  private JoinCondition joinCondition;
  private Collection sources;
  private Collection targets;
  protected CompositeActivity compositeActivity;

  public Activity() {
  }

  public Activity(String name) {
    super(name);
  }

  public void accept(BpelVisitor visitor) {
    // nothing is done by default behavior
  }

  // jbpm override
  // /////////////////////////////////////////////////////////////////////////////

  /**
   * {@inheritDoc} Prevents the activity from being executed if the activity has
   * target links and they are not yet solved.
   */
  public void enter(ExecutionContext executionContext) {
    Token token = executionContext.getToken();

    // update the runtime context information
    token.setNode(this);

    // fire the enter-node event for this node
    fireEvent(Event.EVENTTYPE_NODE_ENTER, executionContext);

    // keep track of node entrance in the token, so that a node-log can be
    // generated at node leave time.
    token.setNodeEnter(new Date());

    // remove the transition references from the runtime context
    executionContext.setTransition(null);
    executionContext.setTransitionSource(null);

    boolean hasTargets = targets != null && !targets.isEmpty();

    if (hasTargets) {
      for (Iterator it = targets.iterator(); it.hasNext();) {
        LinkDefinition link = (LinkDefinition) it.next();
        LinkInstance linkInstance = link.getInstance(token);
        // set the current token as the target token of all link instances
        linkInstance.setTargetToken(token);
      }
    }

    try {
      // if execution of this node is not dependent on links
      if (!hasTargets || (linksDetermined(token) && continueExecution(token))) {
        // execute the node
        execute(executionContext);
      }
      // else wait for the targets completion before continuing the execution
    }
    catch (RuntimeException e) {
      raiseException(e, executionContext);
    }
  }

  public void terminate(ExecutionContext context) {
    // must be overriden by subclasses when some logic has to be performed
    // upon termination
  }

  public void linkDetermined(Token targetToken) {
    // ignore the notification until every link is determined
    if (!linksDetermined(targetToken))
      return;

    try {
      if (!this.equals(targetToken.getNode())) {
        // if the token is not the target node, we are dealing with a previously
        // "canceled" path
        // (by a pick, switch, etch)
        for (Iterator it = sources.iterator(); it.hasNext();) {
          // set a negative link status to its outgoing links
          ((LinkDefinition) it.next()).getInstance(targetToken)
              .statusDetermined(false);
        }
      }
      else if (continueExecution(targetToken)) {
        // the token is located at the target node. Reactivate the execution
        execute(new ExecutionContext(targetToken));
      }
    }
    catch (RuntimeException e) {
      raiseException(e, new ExecutionContext(targetToken));
    }
  }

  /**
   * {@inheritDoc} If the activity has source targets their status is solved
   * before carrying on with the process execution.
   */
  public void leave(ExecutionContext executionContext) {
    BpelFaultException sourcesFault = null;
    if (sources != null) {
      for (Iterator it = sources.iterator(); it.hasNext();) {
        LinkDefinition aSource = (LinkDefinition) it.next();
        // TODO what happens if a transition condition fail?
        // wait for issue 169 resolution to decide wheter the rest of
        // the links have to be solved or not and which fault has to be thrown
        try {
          aSource.determineStatus(executionContext);
          // the status resolution could have caused an exception and a scope
          // cancelation
          if (executionContext.getToken().hasEnded())
            return;
        }
        catch (BpelFaultException fault) {
          sourcesFault = fault;
        }
      }
    }
    if (sourcesFault != null)
      throw sourcesFault;

    Transition leavingTransition = getDefaultLeavingTransition();
    if (leavingTransition != null) {
      // the execution token is passed
      leave(executionContext, leavingTransition);
    }
    else {
      Token token = executionContext.getToken();
      token.setNode(this);
      // fire the leave-node event for this node
      fireEvent(Event.EVENTTYPE_NODE_LEAVE, executionContext);
      // complete the scope instance associated with the token
      token.end(false);
      Scope.getInstance(token).completed();
    }
  }

  /**
   * is the {@link CompositeActivity} or the {@link BpelDefinition} in which
   * this activity is contained.
   */
  public GraphElement getParent() {
    return getCompositeActivity();
  }

  public ProcessDefinition getProcessDefinition() {
    return getCompositeActivity().getProcessDefinition();
  }

  // behaviour methods
  // /////////////////////////////////////////////////////////////////////////////

  /**
   * Called when the execution of an activity wants to be skipped. If the
   * activity has source links, they are set to a negative status before
   * carrying on with the process execution.
   */
  public void skip(ExecutionContext context) {
    setNegativeLinkStatus(context.getToken());
    leave(context, getDefaultLeavingTransition());
  }

  protected void setNegativeLinkStatus(Token token) {
    boolean targetsDetermined = true;
    if (targets != null) {
      for (Iterator it = getTargets().iterator(); it.hasNext();) {
        LinkDefinition link = (LinkDefinition) it.next();
        LinkInstance linkInstance = link.getInstance(token);
        linkInstance.setTargetToken(token);
        if (linkInstance.getStatus() == null) {
          targetsDetermined = false;
        }
      }
    }
    if (targetsDetermined && sources != null) {
      // set a negative link status to the activity sources once all the targets
      // have been determined
      for (Iterator it = getSources().iterator(); it.hasNext();) {
        LinkDefinition link = (LinkDefinition) it.next();
        link.getInstance(token).statusDetermined(false);
      }
    }
  }

  /**
   * Checks if the activity is ready to continue its execution. This depends on:
   * a) having determined the status of all the target links. b) lacking of a
   * join condition or having a positive result of its evaluation When this
   * evaluation is negative and suppressJoinFailure=yes, this operation will
   * skip the activity execution and carry on with the remaining process flow
   * before returning the result.
   * @see "WS-BPEL 2.0 &sect;12.5.2"
   */
  protected boolean continueExecution(Token token) {
    // evaluate condition for the activity
    if (evaluateJoinCondition(token))
      return true;

    if (suppressJoinFailure()) {
      ExecutionContext context = new ExecutionContext(token);
      skip(context);
      return false;
    }
    throw new BpelFaultException(BpelConstants.FAULT_JOIN_FAILURE);
  }

  private boolean linksDetermined(Token token) {
    for (Iterator it = getTargets().iterator(); it.hasNext();) {
      LinkDefinition link = (LinkDefinition) it.next();
      if (link.getInstance(token).getStatus() == null) {
        return false;
      }
    }

    return true;
  }

  private boolean evaluateJoinCondition(Token token) {
    JoinCondition condition = getJoinCondition();
    if (condition != null) {
      return DatatypeUtil.toBoolean(condition.getEvaluator().evaluate(token));
    }
    /*
     * if the explicit join condition is missing, the implicit condition
     * requires the status of at least one incoming link target be positive
     */
    Iterator targetIt = getTargets().iterator();
    while (targetIt.hasNext()) {
      LinkInstance linkInstance = ((LinkDefinition) targetIt.next()).getInstance(token);
      if (linkInstance.getStatus() == Boolean.TRUE)
        return true;
    }
    return false;
  }

  // standard attributes and elements
  // /////////////////////////////////////////////////////////////////////////////

  public JoinCondition getJoinCondition() {
    return joinCondition;
  }

  public void setJoinCondition(JoinCondition joinCondition) {
    this.joinCondition = joinCondition;
  }

  public Boolean getSuppressJoinFailure() {
    return suppressJoinFailure;
  }

  public void setSuppressJoinFailure(Boolean suppressJoinFailure) {
    this.suppressJoinFailure = suppressJoinFailure;
  }

  public boolean suppressJoinFailure() {
    Boolean suppress = getSuppressJoinFailure();
    return suppress != null ? suppress.booleanValue()
        : getCompositeActivity().suppressJoinFailure();
  }

  public Collection getTargets() {
    return targets;
  }

  /**
   * creates a bidirection relation between this activity and the given target
   * link.
   * @throws IllegalArgumentException if target is null.
   */
  public void addTarget(LinkDefinition link) {
    if (link == null)
      throw new IllegalArgumentException(
          "can't add a null target to an activity");
    if (targets == null)
      targets = new HashSet();
    targets.add(link);
    link.target = this;
  }

  /**
   * removes the bidirection relation between this activity and the given target
   * link.
   * @throws IllegalArgumentException if target is null.
   */
  public void removeTarget(LinkDefinition link) {
    if (link == null)
      throw new IllegalArgumentException(
          "can't remove a null leavingTransition from an node");
    if (link != null) {
      if (targets.remove(link)) {
        link.target = null;
      }
    }
  }

  /**
   * retrieves a target by name.
   */
  public LinkDefinition getTarget(String linkName) {
    if (targets != null) {
      for (Iterator it = targets.iterator(); it.hasNext();) {
        LinkDefinition aTarget = (LinkDefinition) it.next();
        if (linkName.equals(aTarget.getName()))
          return aTarget;
      }
    }
    return null;
  }

  public Collection getSources() {
    return sources;
  }

  /**
   * creates a bidirection relation between this activity and the given source
   * link.
   * @throws IllegalArgumentException if source is null.
   */
  public void addSource(LinkDefinition link) {
    if (link == null)
      throw new IllegalArgumentException(
          "can't add a null source to an activity");
    if (sources == null)
      sources = new HashSet();
    sources.add(link);
    link.source = this;
  }

  /**
   * removes the bidirection relation between this activity and the given source
   * link.
   * @throws IllegalArgumentException if source is null.
   */
  public void removeSource(LinkDefinition link) {
    if (link == null)
      throw new IllegalArgumentException(
          "can't remove a null leavingTransition from an node");
    if (link != null) {
      if (sources.remove(link)) {
        link.source = null;
      }
    }
  }

  /**
   * retrieves a source by name.
   */
  public LinkDefinition getSource(String linkName) {
    for (Iterator it = sources.iterator(); it.hasNext();) {
      LinkDefinition aSource = (LinkDefinition) it.next();
      if (linkName.equals(aSource.getName()))
        return aSource;
    }
    return null;
  }

  // parent relationships
  // /////////////////////////////////////////////////////////////////////////////

  public BpelDefinition getBpelDefinition() {
    return (BpelDefinition) getProcessDefinition();
  }

  public Scope getScope() {
    return compositeActivity instanceof Scope ? (Scope) compositeActivity
        : compositeActivity.getScope();
  }

  public CompositeActivity getCompositeActivity() {
    return compositeActivity;
  }

  // utility methods
  // /////////////////////////////////////////////////////////////////////////////

  /**
   * This method mirrors the defaultLeavingTransition method to ease the access
   * of nodes with a single incoming transition. Its important to notice that
   * the implementation of the standard bpel activities only have one incoming
   * and one outgoing transition.
   */
  public Transition getDefaultArrivingTransition() {
    return (Transition) getArrivingTransitions().iterator().next();
  }

  /**
   * An activity is initial if its position in the given process guarantees that
   * there is no basic activity that logically precedes it in the behavior of
   * the process
   * @see "WS-BPEL 2.0 &sect;6.5"
   */
  public boolean isInitial() {
    CompositeActivity parent = getCompositeActivity();

    if (parent == null) {
      return true;
    }
    else if (parent.isInitial()) {
      return parent.isChildInitial(this);
    }
    else {
      return false;
    }
  }

  /**
   * Connects a node to another one by creating an implicit transition between
   * them.
   */
  public void connect(Node to) {
    // create an implicit transition
    Transition transition = new Transition();
    transition.setName(endName(this) + '-' + endName(to));
    transition.setProcessDefinition(processDefinition);

    addLeavingTransition(transition);
    to.addArrivingTransition(transition);
  }

  private static String endName(Node node) {
    String name = node.getName();
    return name != null ? name : ClassUtils.getShortClassName(node.getClass());
  }

  public boolean isConnected(Node to) {
    if (leavingTransitions != null && to.getArrivingTransitions() != null) {
      for (Iterator it = to.getArrivingTransitions().iterator(); it.hasNext();)
        if (leavingTransitions.contains(it.next()))
          return true;
    }

    return false;
  }

  /**
   * Disconnects a node from another one by retriving its implicit transition
   */
  public void disconnect(Node to) {
    Transition removedTransition = null;

    for (Iterator it = to.getArrivingTransitions().iterator(); it.hasNext();) {
      Transition arrivingTransition = (Transition) it.next();
      if (leavingTransitions.remove(arrivingTransition)) {
        removedTransition = arrivingTransition;
        break;
      }
    }

    if (removedTransition != null)
      to.removeArrivingTransition(removedTransition);
  }
}