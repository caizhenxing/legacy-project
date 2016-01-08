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
import java.util.List;

import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * The &lt;if&gt; activity provides conditional behavior.
 * @see "WS-BPEL 2.0 &sect;11.2"
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public class If extends StructuredActivity {

  private List conditions = new ArrayList();

  private static final long serialVersionUID = 1L;

  public If() {
  }

  public If(String name) {
    super(name);
  }

  public void execute(ExecutionContext exeContext) {
    Token token = exeContext.getToken();

    // iterate over the conditions to select a branch
    Activity selectedBranch = null;
    for (int i = 0, n = conditions.size(); i < n; i++) {
      Expression condition = (Expression) conditions.get(i);
      Activity branch = (Activity) nodes.get(i);
      // if no branch has been selected, evaluate the condition
      if (selectedBranch == null
          && DatatypeUtil.toBoolean(condition.getEvaluator().evaluate(token))) {
        // select the transition when the condition holds true
        selectedBranch = branch;
      }
      else {
        // eliminate path
        branch.setNegativeLinkStatus(token);
      }
    }
    Activity elseActivity = getElse();
    if (elseActivity != null) {
      // no branch has been selected?
      if (selectedBranch == null) {
        // go for the default activity
        selectedBranch = elseActivity;
      }
      else {
        // eliminate path
        elseActivity.setNegativeLinkStatus(token);
      }
    }
    else if (selectedBranch == null) {
      // no default branch, proceed directly to the end
      selectedBranch = end;
    }
    getBegin().leave(exeContext, selectedBranch.getDefaultArrivingTransition());
  }

  // children management
  // /////////////////////////////////////////////////////////////////////////////

  protected void attachChild(Activity activity) {
    int conditionCount = conditions.size();
    // is there a default activity?
    if (nodes.size() > conditionCount) {
      // remove the default activity temporarily
      Object otherwise = nodes.remove(conditionCount);
      // attach the new activity
      super.attachChild(activity);
      // move the default activity to the end of the list
      nodes.add(otherwise);
    }
    else {
      // attach the new activity
      super.attachChild(activity);
    }
    /*
     * put a null placeholder in the conditions setCondition() and
     * setOtherwise() will associate conditions and define the default activity
     */
    conditions.add(null);
  }

  protected void detachChild(Activity activity) {
    int activityIndex = nodes.indexOf(activity);
    // if the activity is conditional, remove the associated condition
    if (activityIndex < conditions.size()) {
      conditions.remove(activityIndex);
    }
    super.detachChild(activity);
  }

  // javadoc description in NodeCollection
  public void reorderNode(int oldIndex, int newIndex) {
    super.reorderNode(oldIndex, newIndex);
    /*
     * activities move together with their associated conditions; when trading
     * places with the default activity, the condition remains in the same place
     */
    int otherwiseIndex = conditions.size();
    if (oldIndex < otherwiseIndex && newIndex < otherwiseIndex) {
      Object condition = conditions.remove(oldIndex);
      conditions.add(newIndex, condition);
    }
  }

  // If getters and setters
  // /////////////////////////////////////////////////////////////////////////////

  public Expression getCondition(Activity activity) {
    int index = nodes.indexOf(activity);
    if (index == -1) {
      throw new BpelException(
          "cannot get the condition of a non-member activity");
    }
    return index < conditions.size() ? (Expression) conditions.get(index)
        : null;
  }

  public void setCondition(Activity activity, Expression condition) {
    int index = nodes.indexOf(activity);
    if (index == -1) {
      throw new BpelException(
          "cannot set a condition for a non-member activity");
    }
    // is the given activity conditional?
    if (index < conditions.size()) {
      // match the condition with the activity
      conditions.set(index, condition);
    }
    else {
      // make the default activity conditional
      conditions.add(condition);
    }
  }

  public Activity getElse() {
    int conditionCount = conditions.size();
    return nodes.size() > conditionCount ? (Activity) nodes.get(conditionCount)
        : null;
  }

  public void setElse(Activity otherwise) {
    int index = nodes.indexOf(otherwise);
    if (index == -1) {
      throw new BpelException("cannot set a non-member activity as otherwise");
    }
    // is the given activity conditional?
    int conditionCount = conditions.size();
    if (index < conditionCount) {
      // is there a default activity?
      if (nodes.size() > conditionCount) {
        // remove the old default activity
        Activity oldOtherwise = (Activity) nodes.get(conditionCount);
        detachChild(oldOtherwise);
      }
      // move the activity to the tail of the list
      nodes.remove(index);
      nodes.add(otherwise);
      // drop the condition
      conditions.remove(index);
    }
    // else the activity is already the default
  }

  public List getBranches() {
    return nodes.subList(0, conditions.size());
  }

  // test purposes
  List getConditions() {
    return conditions;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}
