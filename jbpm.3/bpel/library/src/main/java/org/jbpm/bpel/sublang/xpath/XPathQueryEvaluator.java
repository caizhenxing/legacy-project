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
import java.util.List;

import org.jaxen.Context;
import org.jaxen.FunctionContext;
import org.jaxen.JaxenException;
import org.jaxen.XPathFunctionContext;
import org.jaxen.expr.LocationPath;
import org.w3c.dom.Node;

import org.jbpm.bpel.sublang.exe.QueryEvaluator;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * Default evaluator for queries.
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
class XPathQueryEvaluator extends XPathEvaluator implements QueryEvaluator {

  private static final long serialVersionUID = 1L;

  XPathQueryEvaluator(String text) throws JaxenException {
    super(text);
  }

  protected FunctionContext createFunctionContext() {
    return XPathFunctionContext.getInstance();
  }

  protected Context getContext(Object node) {
    Context context = new Context(getContextSupport());
    context.setNodeSet(Collections.singletonList(node));
    return context;
  }

  public Object evaluate(Node contextNode) {
    try {
      List nodeset = selectNodes(contextNode);
      return getSingleNode(nodeset);
    }
    catch (JaxenException e) {
      throw new BpelException("could not select node", e);
    }
  }

  public void assign(Node contextNode, Object value) {
    try {
      List nodes = selectOrCreateNodes((LocationPath) getRootExpr(),
          getContext(contextNode));
      XmlUtil.setObjectValue(getSingleNode(nodes), value);
    }
    catch (JaxenException e) {
      throw new BpelException("could not select node", e);
    }
  }
}