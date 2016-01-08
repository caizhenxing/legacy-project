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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jbpm.bpel.sublang.def.JoinCondition;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * Represents a BPEL structured activity.
 * @see "WS-BPEL 2.0 &sect;12"
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class StructuredActivity extends CompositeActivity {

  protected List nodes = new ArrayList();
  protected Begin begin = new Begin(this);
  protected End end = new End(this);

  public StructuredActivity() {
  }

  public StructuredActivity(String name) {
    super(name);
  }

  // behaviour methods
  // /////////////////////////////////////////////////////////////////////////////

  public void enter(ExecutionContext exeContext) {
    begin.enter(exeContext);
  }

  public void execute(ExecutionContext exeContext) {
    begin.leave(exeContext, begin.getDefaultLeavingTransition());
  }

  public void leave(ExecutionContext exeContext) {
    end.leave(exeContext);
  }

  public void skip(ExecutionContext exeContext) {
    setNegativeLinkStatus(exeContext.getToken());
    end.leave(exeContext, getDefaultLeavingTransition());
  }

  public void setNegativeLinkStatus(Token token) {
    Iterator activityIt = getNodes().iterator();
    while (activityIt.hasNext()) {
      ((Activity) activityIt.next()).setNegativeLinkStatus(token);
    }
    end.setNegativeLinkStatus(token);
  }

  // structure delimiters
  // /////////////////////////////////////////////////////////////////////////////

  public Begin getBegin() {
    return begin;
  }

  public End getEnd() {
    return end;
  }

  // children management
  // /////////////////////////////////////////////////////////////////////////////

  protected void attachChild(Activity activity) {
    addImplicitTransitions(activity);
    nodes.add(activity);
    activity.compositeActivity = this;
    nodesMap = null;
  }

  protected void detachChild(Activity activity) {
    removeImplicitTransitions(activity);
    if (nodes.remove(activity)) {
      activity.compositeActivity = null;
      nodesMap = null;
    }
  }

  // javadoc description in NodeCollection
  public Node addNode(Node node) {
    if (!(node instanceof Activity)) {
      throw new BpelException(
          "can't add a non-bpel activity to a composite activity");
    }
    attachChild((Activity) node);
    return node;
  }

  // javadoc description in NodeCollection
  public Node removeNode(Node node) {
    if (!(node instanceof Activity) || !nodes.contains(node)) return null;
    detachChild((Activity) node);
    return node;
  }

  // javadoc description in NodeCollection
  public void reorderNode(int oldIndex, int newIndex) {
    Object reorderedObject = nodes.remove(oldIndex);
    nodes.add(newIndex, reorderedObject);
  }

  // javadoc description in NodeCollection
  public List getNodes() {
    return nodes;
  }

  protected void addImplicitTransitions(Activity activity) {
    // the default behavior is to connect the activity to the structure
    // delimiters
    begin.connect(activity);
    activity.connect(end);
  }

  protected void removeImplicitTransitions(Activity activity) {
    // the default behavior is to remove the activity incoming and outgoing
    // transitions
    begin.disconnect(activity);
    activity.disconnect(end);
  }

  // bpel activity standard attributes and elements
  // /////////////////////////////////////////////////////////////////////////////

  public JoinCondition getJoinCondition() {
    return begin.getJoinCondition();
  }

  public void setJoinCondition(JoinCondition joinCondition) {
    begin.setJoinCondition(joinCondition);
  }

  public Collection getSources() {
    return end.getSources();
  }

  public void addSource(LinkDefinition link) {
    end.addSource(link);
  }

  public LinkDefinition getSource(String linkName) {
    return end.getSource(linkName);
  }

  public LinkDefinition getTarget(String linkName) {
    return begin.getTarget(linkName);
  }

  public Collection getTargets() {
    return begin.getTargets();
  }

  public void addTarget(LinkDefinition link) {
    begin.addTarget(link);
  }

  // node properties
  // /////////////////////////////////////////////////////////////////////////////

  public Transition getDefaultArrivingTransition() {
    return begin.getDefaultArrivingTransition();
  }

  public Transition getDefaultLeavingTransition() {
    return end.getDefaultLeavingTransition();
  }

  public Transition addArrivingTransition(Transition transition) {
    begin.addArrivingTransition(transition);
    return transition;
  }

  public Transition addLeavingTransition(Transition transition) {
    end.addLeavingTransition(transition);
    return transition;
  }

  /**
   * Handles the incoming flow of execution of structured activities.
   */
  public static class Begin extends Activity {

    private static final long serialVersionUID = 1L;

    Begin() {
    }

    public Begin(StructuredActivity activity) {
      super(activity.getName());
      compositeActivity = activity;
    }

    public void leave(ExecutionContext context) {
      getCompositeActivity().execute(context);
    }

    public void skip(ExecutionContext context) {
      getCompositeActivity().skip(context);
    }
  }

  /**
   * Handles the outgoing flow of execution of structured activities.
   */
  public static class End extends Activity {

    private static final long serialVersionUID = 1L;

    End() {
    }

    public End(StructuredActivity activity) {
      super(activity.getName());
      compositeActivity = activity;
    }

    public void execute(ExecutionContext context) {
      getCompositeActivity().leave(context);
    }
  }
}