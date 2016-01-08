/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.graph.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.instantiation.Delegation;
import org.jbpm.jpdl.el.impl.JbpmExpressionEvaluator;
import org.jbpm.jpdl.xml.JpdlXmlReader;
import org.jbpm.jpdl.xml.Parsable;

/**
 * decision node.
 */
public class Decision extends Node implements Parsable {
  
  static final String NEWLINE = System.getProperty("line.separator");
  static final String DECISION_CONDITION_RESULT = "decision_condition_result";
  static final long serialVersionUID = 1L;

  List decisionConditions = null;
  Delegation decisionDelegation = null;
  String decisionExpression = null;

  public Decision() {
  }

  public Decision(String name) {
    super(name);
  }

  public void read(Element decisionElement, JpdlXmlReader jpdlReader) {

    String expression = decisionElement.attributeValue("expression");
    Element decisionHandlerElement = decisionElement.element("handler");

    if (expression!=null) {
      decisionExpression = expression;

    } else if (decisionHandlerElement!=null) {
      decisionDelegation = new Delegation();
      decisionDelegation.read(decisionHandlerElement, jpdlReader);
    }

    Iterator iter = decisionElement.elementIterator("transition");
    while (iter.hasNext()) {
      Element transitionElement = (Element) iter.next();
      Element conditionElement = transitionElement.element("condition");
      if (conditionElement!=null) {
        String conditionExpression = getConditionExpression(conditionElement);
        if (conditionExpression!=null) {
          String transitionName = transitionElement.attributeValue("name");
          if (decisionConditions==null) {
            decisionConditions = new ArrayList();
          }
          decisionConditions.add(new DecisionCondition(transitionName, conditionExpression));
        }
      }
    }
  }

  String getConditionExpression(Element conditionElement) {
    String expression = conditionElement.attributeValue("expression");
    if (expression!=null) {
      return expression;
    } else {
      return conditionElement.getText();
    }
  }

  public void execute(ExecutionContext executionContext) {
    String transitionName = null;
    
    try {
      if (decisionDelegation!=null) {
        DecisionHandler decisionHandler = (DecisionHandler) decisionDelegation.instantiate();
        transitionName = decisionHandler.decide(executionContext);
        
      } else if (decisionExpression!=null) {
        Object result = JbpmExpressionEvaluator.evaluate(decisionExpression, executionContext);
        if (result==null) {
          throw new JbpmException("decision expression '"+decisionExpression+"' returned null");
        }
        transitionName = result.toString();
        
      } else {
        
        Iterator iter = decisionConditions.iterator();
        while (iter.hasNext() && (transitionName==null)) {
          DecisionCondition decisionCondition = (DecisionCondition) iter.next();
          Object result = JbpmExpressionEvaluator.evaluate(decisionCondition.getExpression(), executionContext);
          if (Boolean.TRUE.equals(result)) {
            transitionName = decisionCondition.getTransitionName();
          }
        }
        
        if (transitionName==null) {
          getDefaultLeavingTransition().getName();
        }
      }
    } catch (Exception exception) {
      raiseException(exception, executionContext);
    }

    Transition transition = null;
    if (transitionName!=null) {
      log.debug("selected transition name '"+transitionName+"'");
      transition = getLeavingTransition(transitionName);
    } else {
      log.debug("no transition name selected: taking default leaving transition");
      transition = getDefaultLeavingTransition();
    }

    if (transition==null) {
      throw new JbpmException("decision '"+name+"' selected non existing transition '"+transitionName+"'" );
    }
    executionContext.leaveNode(transition);
  }

  /*
  String createDecisionExpression() {
    
    // String decision_condition_result = null;
    // if (condition1) {
    //   decision_condition_result = transitionName1;
    // } else if (condition2) {
    //   decision_condition_result = transitionName2;
    // }

    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("String selectedTransitionName = null; "+NEWLINE);
    
    boolean isFirst = true;
    Iterator iter = decisionConditions.iterator();
    while (iter.hasNext()) {
      DecisionCondition decisionCondition = (DecisionCondition) iter.next();
      
      if (isFirst) {
        isFirst = false;
      } else {
        stringBuffer.append(" else ");
      }
      
      stringBuffer.append("if (");
      stringBuffer.append(decisionCondition.expression);
      stringBuffer.append(") {");
      stringBuffer.append(NEWLINE);
      stringBuffer.append("  ");
      stringBuffer.append(DECISION_CONDITION_RESULT);
      stringBuffer.append(" = \"");
      stringBuffer.append(decisionCondition.transitionName);
      stringBuffer.append("\";");
      stringBuffer.append(NEWLINE);
      stringBuffer.append("}");
    }
    
    return stringBuffer.toString();
  }
  */

  public List getDecisionConditions() {
    return decisionConditions;
  }
  
  private static Log log = LogFactory.getLog(Decision.class);
}
