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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
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
 * @version $Revision: 1.13 $ $Date: 2006/09/13 07:56:57 $
 */
public class While extends StructuredActivity {

  private static final long serialVersionUID = 1L;

  private Expression condition;
  private Loop loop = new Loop(this);

  public While() {
    super();
    setEvaluateFirst(true);
    loop.connect(end);
  }

  public While(String name) {
    super(name);
    setEvaluateFirst(true);
    loop.connect(end);
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
    if (!getEvaluateFirst())
      begin.connect(activity);

    // Connect loop node to the activity
    loop.connect(activity);
    activity.connect(loop);
  }

  protected void removeImplicitTransitions(Activity activity) {
    if (!getEvaluateFirst())
      begin.disconnect(activity);

    // Disconnect loop node to the activity
    loop.disconnect(activity);
    activity.disconnect(loop);
  }

  // while properties
  // /////////////////////////////////////////////////////////////////////////////

  public boolean getEvaluateFirst() {
    return begin.isConnected(loop);
  }

  public void setEvaluateFirst(boolean evaluateFirst) {
    if (evaluateFirst == getEvaluateFirst())
      return;

    if (evaluateFirst) {
      begin.connect(loop);
    }
    else {
      begin.disconnect(loop);
    }
  }

  public Expression getCondition() {
    return condition;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public Loop getLoopNode() {
    return loop;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }

  /**
   * Implements the loop behavior for the while activity
   */
  public static class Loop extends Activity {

    private static ThreadLocal entranceLocal = new ThreadLocal() {

      protected Object initialValue() {
        return new Hashtable();
      }
    };

    private Log log = LogFactory.getLog(Loop.class);
    private static final long serialVersionUID = 1L;
    
    private static final Boolean FIRST_ENTRANCE = Boolean.FALSE;
    private static final Boolean REENTRANCE = Boolean.TRUE;

    Loop() {
    }

    Loop(While structure) {
      super(structure.getName());
      compositeActivity = structure;
    }

    public void execute(ExecutionContext exeContext) {
      Token token = exeContext.getToken();
      if (getMark() == FIRST_ENTRANCE) {
        log.debug("reentrance detected, returning: " + this + ", " + token);
        setMark(REENTRANCE);
        return;
      }
      CompositeActivity composite = getCompositeActivity();
      While _while;
      if (composite instanceof While) {
        _while = (While) composite;
      }
      else {
        // reacquire proxy of the proper type
        Session hbSession = JbpmContext.getCurrentJbpmContext().getSession();
        _while = (While) hbSession.load(While.class,
            new Long(composite.getId()));
      }
      ExpressionEvaluator conditionEvaluator = _while.getCondition()
          .getEvaluator();
      List transitions = getLeavingTransitions();
      Boolean mark;
      do {
        mark = null;
        // evaluate condition
        if (DatatypeUtil.toBoolean(conditionEvaluator.evaluate(token))) {
          // set the first entrance mark
          setMark(FIRST_ENTRANCE);

          // leave over loop transition
          leave(exeContext, (Transition) transitions.get(1));
          
          // verify entrance mark
          mark = removeMark();
          
          if (log.isDebugEnabled()) {
            // first entrance mark intact, execution did not complete full cycle
            if (mark == FIRST_ENTRANCE)
              log.debug("wait state reached: " + this + ", " + token);
            // reentrance mark set, 
            else if (mark == REENTRANCE)
              log.debug("continue: " + this + ", " + token);
            else
              throw new IllegalStateException("entrance mark not found: " + this
                  + ", " + token);
          }
        }
        else {
          // leave over break transition
          leave(exeContext, (Transition) transitions.get(0));
          log.debug("break: " + this + ", " + token);
        }
      } while (mark == REENTRANCE);
    }

    private Boolean getMark() {
      return (Boolean) getEntranceMarks().get(this);
    }

    private void setMark(Boolean backtrack) {
      getEntranceMarks().put(this, backtrack);
    }

    private Boolean removeMark() {
      return (Boolean) getEntranceMarks().remove(this);
    }

    private static Map getEntranceMarks() {
      return (Map) entranceLocal.get();
    }
  }
}