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
package org.jbpm.bpel.sublang.xpath;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.FunctionContext;
import org.jaxen.JaxenException;
import org.jaxen.XPathFunctionContext;
import org.jaxen.expr.Expr;
import org.jaxen.expr.FilterExpr;
import org.jaxen.expr.FunctionCallExpr;
import org.jaxen.expr.LiteralExpr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.NumberExpr;
import org.jaxen.expr.PathExpr;
import org.jaxen.expr.Predicate;
import org.jaxen.expr.PredicateSet;
import org.jaxen.expr.VariableReferenceExpr;
import org.jaxen.expr.VisitorSupport;
import org.w3c.dom.Node;

import org.jbpm.bpel.sublang.exe.ExpressionEvaluator;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.Token;

/**
 * Default evaluator for expressions. When XPath 1.0 is used as an expression
 * language, there is no context node available.
 * @see "WS-BPEL 2.0 &sect;8.2.4"
 * @author Alejandro Guízar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class XPathExpressionEvaluator extends XPathEvaluator implements
    ExpressionEvaluator {

  public static final FunctionContext FUNCTION_LIBRARY;

  private static final long serialVersionUID = 1L;

  protected XPathExpressionEvaluator(String text) throws JaxenException {
    super(text);
  }

  /**
   * Evaluates this expression. The context of XPath evaluation is:
   * <ul>
   * <li>the context node, position and size are unspecified</li>
   * <li>the function library, namespace declaration and variable bindings are
   * obtained by a call to {@link #getContext(Object)} with the given
   * <code>contextInfo</code> as argument</li>
   * <ul>
   */
  public Object evaluate(Token contextInfo) {
    Object result = null;
    try {
      List nodeset = selectNodes(contextInfo);
      if (nodeset != null) {
        switch (nodeset.size()) {
        case 0:
          break;
        case 1:
          result = nodeset.get(0);
          break;
        default:
          result = nodeset;
        }
      }
    }
    catch (JaxenException e) {
      throw new BpelException("could not select nodes", e);
    }
    return result;
  }

  public void assign(Token contextInfo, Object value) {
    Expr rootExpr = getRootExpr();
    Context context = getContext(contextInfo);
    // direct variable assignment?
    if (rootExpr instanceof VariableReferenceExpr) {
      assignVariable((VariableReferenceExpr) rootExpr, context, value);
    }
    else {
      new ExprAssigner().assign(rootExpr, context, value);
    }
  }

  protected void assignVariable(VariableReferenceExpr rootExpr,
      Context context, Object value) {
    String variableName = rootExpr.getVariableName();
    ScopeVariableContext variableContext = (ScopeVariableContext) context.getContextSupport()
        .getVariableContext();
    // look for a dot in the variable name, indicating a message part access
    int dotIndex = variableName.indexOf('.');
    if (dotIndex == -1) {
      /*
       * dotless name, a schema type or element describes the variable - find
       * variable definition
       */
      VariableDefinition variableDefinition = variableContext.getVariableDefinition(variableName);
      if (variableDefinition == null) {
        throw new BpelException("variable not found: " + variableName);
      }
      Object valueForAssign = variableDefinition.getValueForAssign(variableContext.getToken());
      // prevent direct access to a message variable
      if (valueForAssign instanceof MessageValue) {
        throw new BpelException("illegal access to message variable: "
            + variableName);
      }
      // assign variable value
      variableDefinition.getType().setValue(valueForAssign, value);
    }
    else {
      /*
       * dotted name, a message describes the variable - find variable
       * definition
       */
      String messageName = variableName.substring(0, dotIndex);
      VariableDefinition variableDefinition = variableContext.getVariableDefinition(messageName);
      if (variableDefinition == null) {
        throw new BpelException("variable not found: " + messageName);
      }
      Object valueForAssign = variableDefinition.getValueForAssign(variableContext.getToken());
      // prevent access to a non-existent part
      if (!(valueForAssign instanceof MessageValue)) {
        throw new BpelException("non-message variable does not have part: "
            + variableName);
      }
      // extract initialized message value
      MessageValue messageValue = (MessageValue) valueForAssign;
      // assign part value
      String partName = variableName.substring(dotIndex + 1);
      messageValue.setPart(partName, value);
    }
  }

  protected FunctionContext createFunctionContext() {
    return FUNCTION_LIBRARY;
  }

  /**
   * Gets a context for XPath evaluation. The context for an expression
   * expression is:
   * <ul>
   * <li>the variable bindings are the variables in the scope of the given
   * token</li>
   * <li>the function library is {@link #FUNCTION_LIBRARY}</li>
   * <li>the namespace declarations are taken from the snippet</li>
   * </ul>
   * @param node an instance of {@link Token}
   */
  protected Context getContext(Object node) {
    ContextSupport support = new ContextSupport(getNamespaceContext(),
        getFunctionContext(), new ScopeVariableContext((Token) node),
        getNavigator());
    return new Context(support);
  }

  private class ExprAssigner extends VisitorSupport {

    private Context context;
    private Object result;

    public void assign(Expr pathExpr, Context context, Object value) {
      this.context = context;
      pathExpr.accept(this);
      Node node = result instanceof List ? getSingleNode((List) result)
          : (Node) result;
      XmlUtil.setObjectValue(node, value);
    }

    public void visit(PathExpr pathExpr) {
      pathExpr.getFilterExpr().accept(this);
      visit(pathExpr.getLocationPath());
    }

    public void visit(FilterExpr filterExpr) {
      filterExpr.getExpr().accept(this);
      // result is a node set so far?
      if (result instanceof List) {
        result = evaluatePredicates(filterExpr.getPredicateSet(),
            (List) result,
            context.getContextSupport());
      }
    }

    public void visit(LocationPath locationPath) {
      context.setNodeSet(result instanceof List ? (List) result
          : Collections.singletonList(result));
      try {
        result = selectOrCreateNodes(locationPath, context);
      }
      catch (JaxenException e) {
        throw new BpelException("could not select (or create) nodes", e);
      }
    }

    public void visit(VariableReferenceExpr varExpr) {
      String variableName = varExpr.getVariableName();
      ScopeVariableContext variableContext = (ScopeVariableContext) context.getContextSupport()
          .getVariableContext();
      // look for a dot in the variable name, indicating a message part access
      int dotIndex = variableName.indexOf('.');
      if (dotIndex == -1) {
        /*
         * dotless name, a schema type or element describes the variable - find
         * variable definition
         */
        VariableDefinition variableDefinition = variableContext.getVariableDefinition(variableName);
        if (variableDefinition == null) {
          throw new BpelException("variable not found: " + variableName);
        }
        // variable value for assign
        result = variableDefinition.getValueForAssign(variableContext.getToken());
      }
      else {
        /*
         * dotted name, a message describes the variable - find variable
         * definition
         */
        String messageName = variableName.substring(0, dotIndex);
        VariableDefinition definition = variableContext.getVariableDefinition(messageName);
        if (definition == null) {
          throw new BpelException("variable not found: " + messageName);
        }
        // prevent access to a non-existent part
        if (!(definition.getType() instanceof MessageType)) {
          throw new BpelException("non-message variable does not have part: "
              + variableName);
        }
        // extract initialized message value
        MessageValue messageValue = (MessageValue) definition.getValueForAssign(variableContext.getToken());
        // part value for assign
        String partName = variableName.substring(dotIndex + 1);
        result = messageValue.getPartForAssign(partName);
      }
    }

    public void visit(LiteralExpr literalExpr) {
      try {
        result = literalExpr.evaluate(context);
      }
      catch (JaxenException e) {
        throw new BpelException("could not evaluate literal expression", e);
      }
    }

    public void visit(NumberExpr numberExpr) {
      try {
        result = numberExpr.evaluate(context);
      }
      catch (JaxenException e) {
        throw new BpelException("could not evaluate number expression", e);
      }
    }

    public void visit(FunctionCallExpr callExpr) {
      try {
        result = callExpr.evaluate(context);
      }
      catch (JaxenException e) {
        throw new BpelException("could not evaluate call expression", e);
      }
    }

    private List evaluatePredicates(PredicateSet predicateSet, List nodes,
        ContextSupport support) {
      List predicates = predicateSet.getPredicates();
      if (!predicates.isEmpty()) {
        try {
          Iterator predicateIt = predicateSet.getPredicates().iterator();
          while (predicateIt.hasNext()) {
            nodes = predicateSet.applyPredicate((Predicate) predicateIt.next(),
                nodes,
                support);
          }
        }
        catch (JaxenException e) {
          throw new BpelException("could not apply predicate", e);
        }
      }
      return nodes;
    }
  }

  static {
    XPathFunctionContext functionContext = new XPathFunctionContext();
    FUNCTION_LIBRARY = functionContext;
    // BPEL library
    functionContext.registerFunction(BpelConstants.NS_BPWS_1_1,
        "getVariableData",
        new GetVariableDataFunction());
    functionContext.registerFunction(BpelConstants.NS_BPWS_1_1,
        "getVariableProperty",
        new GetVariablePropertyFunction());
    functionContext.registerFunction(BpelConstants.NS_BPWS,
        "getVariableProperty",
        new GetVariablePropertyFunction());
  }
}
