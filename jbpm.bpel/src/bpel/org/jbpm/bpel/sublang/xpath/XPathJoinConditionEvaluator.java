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

import java.util.Iterator;

import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.FunctionContext;
import org.jaxen.JaxenException;
import org.jaxen.SimpleVariableContext;
import org.jaxen.XPathFunctionContext;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.LinkDefinition;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.graph.exe.Token;

/**
 * Evaluator for expressions in join conditions. Only link status can be used
 * within join conditions and only join conditions can make use of link status.
 * @see "WS-BPEL 2.0 &sect;12.5.1"
 * @author Juan Cantú
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
class XPathJoinConditionEvaluator extends XPathExpressionEvaluator {

  private static final long serialVersionUID = 1L;

  public static final FunctionContext FUNCTION_LIBRARY;

  XPathJoinConditionEvaluator(String text) throws JaxenException {
    super(text);
  }

  protected FunctionContext createFunctionContext() {
    return FUNCTION_LIBRARY;
  }

  /**
   * Gets a context for expression evaluation. The context for a join condition
   * is:
   * <ul>
   * <li>the variable bindings are the links targeting the current node of the
   * given token</li>
   * <li>the function context is {@link #FUNCTION_LIBRARY}</li>
   * <li>the namespace declarations are taken from the snippet</li>
   * </ul>
   * @param node an instance of {@link Token}
   */
  protected Context getContext(Object node) {
    SimpleVariableContext variableContext = new SimpleVariableContext();
    Token token = (Token) node;
    Iterator targetIt = ((Activity) token.getNode()).getTargets().iterator();
    while (targetIt.hasNext()) {
      LinkDefinition target = (LinkDefinition) targetIt.next();
      variableContext.setVariableValue(target.getName(),
          target.getInstance(token).getStatus());
    }

    ContextSupport support = new ContextSupport(getNamespaceContext(),
        getFunctionContext(), variableContext, getNavigator());
    return new Context(support);
  }

  static {
    XPathFunctionContext functionContext = new XPathFunctionContext();
    FUNCTION_LIBRARY = functionContext;
    // WS-BPEL 2.0 library
    functionContext.registerFunction(BpelConstants.NS_BPWS_1_1,
        "getLinkStatus",
        new GetLinkStatusFunction());
  }
}
