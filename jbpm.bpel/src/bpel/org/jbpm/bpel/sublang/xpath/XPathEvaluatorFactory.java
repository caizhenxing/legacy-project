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

import java.util.Set;

import org.jaxen.JaxenException;
import org.jaxen.XPathSyntaxException;
import org.jaxen.expr.AdditiveExpr;
import org.jaxen.expr.BinaryExpr;
import org.jaxen.expr.EqualityExpr;
import org.jaxen.expr.Expr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.LogicalExpr;
import org.jaxen.expr.MultiplicativeExpr;
import org.jaxen.expr.RelationalExpr;
import org.jaxen.expr.UnaryExpr;
import org.jaxen.expr.UnionExpr;
import org.jaxen.expr.VisitorSupport;

import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.sublang.def.JoinCondition;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.sublang.exe.EvaluatorFactory;
import org.jbpm.bpel.sublang.exe.ExpressionEvaluator;
import org.jbpm.bpel.sublang.exe.QueryEvaluator;
import org.jbpm.bpel.xml.BpelException;

/**
 * A factory for expressions and queries written in XPath 1.0.
 * @author Alejandro Guízar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class XPathEvaluatorFactory extends EvaluatorFactory {

  private static final XPathEvaluatorFactory instance = new XPathEvaluatorFactory();

  public static EvaluatorFactory getInstance() {
    return instance;
  }

  public ExpressionEvaluator createEvaluator(Expression expression) {
    // parse text and create evaluator corresponding to expression class
    String text = expression.getText();
    XPathExpressionEvaluator evaluator;
    try {
      evaluator = expression instanceof JoinCondition ? XPathEvaluatorFactory.createJoinConditionEvaluator(text)
          : XPathEvaluatorFactory.createExpressionEvaluator(text);
    }
    catch (JaxenException e) {
      throw new BpelException("could not create evaluator: expression="
          + expression, e);
    }

    // set namespace declarations
    Set namespaces = expression.getNamespaces();
    if (namespaces != null) {
      evaluator.setNamespaceContext(new SetNamespaceContext(namespaces));
    }

    return evaluator;
  }

  public QueryEvaluator createEvaluator(Query query) {
    // parse text and create evaluator
    XPathQueryEvaluator evaluator;
    try {
      evaluator = new XPathQueryEvaluator(query.getText());
    }
    catch (JaxenException e) {
      throw new BpelException("could not create evaluator: query=" + query, e);
    }

    // set namespace declarations
    Set namespaces = query.getNamespaces();
    if (namespaces != null) {
      evaluator.setNamespaceContext(new SetNamespaceContext(namespaces));
    }

    return evaluator;
  }

  public static XPathExpressionEvaluator createExpressionEvaluator(String text)
      throws JaxenException {
    XPathExpressionEvaluator expression = new XPathExpressionEvaluator(text);
    // expressions are general xpath expressions, excluding the direct usage of
    // location paths
    if (!new XPathEvaluatorFactory.ExprValidator().validate(expression.getRootExpr())) {
      throw new XPathSyntaxException(text, 0,
          "illegal access to root/context node");
    }
    return expression;
  }

  public static XPathQueryEvaluator createQueryEvaluator(String text)
      throws JaxenException {
    XPathQueryEvaluator aliasQuery = new XPathQueryEvaluator(text);
    Expr rootExpr = aliasQuery.getRootExpr();
    if (!(rootExpr instanceof LocationPath)) {
      throw new XPathSyntaxException(text, 0, "not a location path");
    }
    return aliasQuery;
  }

  public static XPathJoinConditionEvaluator createJoinConditionEvaluator(
      String text) throws JaxenException {
    XPathJoinConditionEvaluator joinCondition = new XPathJoinConditionEvaluator(
        text);
    if (!new ExprValidator().validate(joinCondition.getRootExpr())) {
      throw new XPathSyntaxException(text, 0,
          "illegal access to root/context node");
    }
    return joinCondition;
  }

  private static class ExprValidator extends VisitorSupport {

    private boolean valid;

    public boolean validate(Expr expr) {
      valid = true;
      expr.accept(this);
      return valid;
    }

    public void visit(LogicalExpr expr) {
      propagate(expr);
    }

    public void visit(EqualityExpr expr) {
      propagate(expr);
    }

    public void visit(RelationalExpr expr) {
      propagate(expr);
    }

    public void visit(AdditiveExpr expr) {
      propagate(expr);
    }

    public void visit(MultiplicativeExpr expr) {
      propagate(expr);
    }

    public void visit(UnaryExpr expr) {
      expr.getExpr().accept(this);
    }

    public void visit(UnionExpr expr) {
      propagate(expr);
    }

    public void visit(LocationPath locationPath) {
      valid = false;
    }

    private void propagate(BinaryExpr expr) {
      expr.getLHS().accept(this);
      expr.getRHS().accept(this);
    }
  }
}