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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ScopeHandler extends CompositeActivity implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Scope scope;
  private Activity activity;
  private transient List nodes = null;

  public ScopeHandler() {
  }

  public CompositeActivity getCompositeActivity() {
    return scope;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void execute(ExecutionContext context) {
    activity.enter(context);
  }

  // children management
  // /////////////////////////////////////////////////////////////////////////////

  // javadoc description in NodeCollection
  public Node addNode(Node node) {
    activity = (Activity) node;
    activity.compositeActivity = this;
    return activity;
  }

  // javadoc description in NodeCollection
  public Node removeNode(Node node) {
    if (activity.equals(node)) {
      activity.compositeActivity = null;
      activity = null;
    }

    return node;
  }

  // javadoc description in NodeCollection
  public void reorderNode(int oldIndex, int newIndex) {
    // nothing to do
  }

  // javadoc description in NodeCollection
  public List getNodes() {
    if (nodes == null && activity != null) {
      nodes = new ArrayList();
      nodes.add(activity);
    }
    else if (!nodes.contains(activity)) {
      nodes.set(0, activity);
    }

    return nodes;
  }
}