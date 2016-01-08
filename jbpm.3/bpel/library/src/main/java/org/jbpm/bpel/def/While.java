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

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.sublang.exe.ExpressionEvaluator;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * The while activity supports repeated performance of a specified iterative
 * activity.
 * @see "WS-BPEL 2.0 &sect;12.3"
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class While extends StructuredActivity {

  private static final long serialVersionUID = 1L;

  private Expression condition;
  private LoopNode loopNode = new LoopNode(this);

  public While() {
    super();
    setEvaluateFirst(true);
    loopNode.connect(end);
  }

  public While(String name) {
    super(name);
    setEvaluateFirst(true);
    loopNode.connect(end);
  }

  // children management
  // /////////////////////////////////////////////////////////////////////////////

  protected void attachChild(Activity activity) {
    if (nodes != null && nodes.size() > 0) {
      detachChild((Activity) nodes.get(0));
    }
    super.attachChild(activity);
  }

  protected void addImplicitTransitions(Activity activity) {
    if (!getEvaluateFirst()) begin.connect(activity);

    // Connect loop node to the activity
    loopNode.connect(activity);
    activity.connect(loopNode);
  }

  protected void removeImplicitTransitions(Activity activity) {
    if (!getEvaluateFirst()) begin.disconnect(activity);

    // Disconnect loop node to the activity
    loopNode.disconnect(activity);
    activity.disconnect(loopNode);
  }

  // while properties
  // /////////////////////////////////////////////////////////////////////////////

  public boolean getEvaluateFirst() {
    return begin.isConnected(loopNode);
  }

  public void setEvaluateFirst(boolean evaluateFirst) {
    if (evaluateFirst == getEvaluateFirst()) return;

    if (evaluateFirst) {
      begin.connect(loopNode);
    }
    else {
      begin.disconnect(loopNode);
    }
  }

  public Expression getCondition() {
    return condition;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public LoopNode getLoopNode() {
    return loopNode;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }

  /**
   * Implements the loop behavior for the while activity
   */
  public static class LoopNode extends Activity {

    private static ThreadLocal backtrackLocal = new ThreadLocal() {

      protected Object initialValue() {
        return new Hashtable();
      }
    };

    private static final long serialVersionUID = 1L;

    LoopNode() {
    }

    public LoopNode(While structure) {
      super(structure.getName());
      compositeActivity = structure;
    }

    public void execute(ExecutionContext context) {
      if (getMark() == Boolean.FALSE) {
        setMark(Boolean.TRUE);
        return;
      }
      While structure = (While) getCompositeActivity();
      ExpressionEvaluator conditionEvaluator = structure.getCondition()
          .getEvaluator();
      Token token = context.getToken();
      List transitions = getLeavingTransitions();
      for (;;) {
        // evaluate condition
        if (DatatypeUtil.toBoolean(conditionEvaluator.evaluate(token))) {
          setMark(Boolean.FALSE);
          // leave over loop transition
          leave(context, (Transition) transitions.get(1));
          Boolean mark = removeMark();
          if (mark == Boolean.FALSE) break;
        }
        else {
          // leave over break transition
          leave(context, (Transition) transitions.get(0));
          break;
        }
      }
    }

    private static Map getBacktrackMarks() {
      return (Map) backtrackLocal.get();
    }

    private Boolean getMark() {
      return (Boolean) getBacktrackMarks().get(this);
    }

    private void setMark(Boolean backtrack) {
      getBacktrackMarks().put(this, backtrack);
    }

    private Boolean removeMark() {
      return (Boolean) getBacktrackMarks().remove(this);
    }
  }
}