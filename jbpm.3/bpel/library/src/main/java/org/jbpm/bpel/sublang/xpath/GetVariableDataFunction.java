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

import java.util.List;

import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;
import org.jaxen.function.StringFunction;
import org.w3c.dom.Element;

import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;

/**
 * BPEL library function to extract arbitrary values from variables.
 * <p>
 * <code><i>any</i> bpws:getVariableData(<i>string</i> variableName,
 * <i>string</i> partName?, <i>string</i> locationPath?)</code>
 * </p>
 * @see "WS-BPEL 2.0 &sect;14.1"
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
class GetVariableDataFunction implements Function {

  public Object call(Context context, List args) throws FunctionCallException {
    Object value;

    switch (args.size()) {
    case 1:
      value = evaluate(args.get(0), context);
      break;
    case 2:
      value = evaluate(args.get(0), args.get(1), context);
      break;
    case 3:
      value = evaluate(args.get(0), args.get(1), args.get(2), context);
      break;
    default:
      throw new FunctionCallException(
          "getVariableData() requires one to three arguments");
    }
    return value;
  }

  public static Object evaluate(Object variableArg, Context context)
      throws FunctionCallException {
    // convert argument to string
    String variableName = StringFunction.evaluate(variableArg,
        context.getNavigator());
    // find variable definition
    ScopeVariableContext variableContext = (ScopeVariableContext) context.getContextSupport()
        .getVariableContext();
    VariableDefinition variableDefinition = variableContext.getVariableDefinition(variableName);
    if (variableDefinition == null) {
      throw new FunctionCallException("variable not found: " + variableName);
    }
    // retrieve the value
    Object value = variableDefinition.getValue(variableContext.getToken());
    if (value instanceof MessageValue) {
      throw new FunctionCallException("illegal access to message variable: "
          + variableName);
    }
    return value;
  }

  public static Object evaluate(Object variableArg, Object partArg,
      Context context) throws FunctionCallException {
    // convert argument to string
    Navigator nav = context.getNavigator();
    String variableName = StringFunction.evaluate(variableArg, nav);
    // find variable definition
    ScopeVariableContext variableContext = (ScopeVariableContext) context.getContextSupport()
        .getVariableContext();
    VariableDefinition variableDefinition = variableContext.getVariableDefinition(variableName);
    if (variableDefinition == null) {
      throw new FunctionCallException("variable not found: " + variableName);
    }
    // extract variable value
    Object value = variableDefinition.getValue(variableContext.getToken());
    if (!(value instanceof MessageValue)) {
      throw new FunctionCallException("variable is not a message: "
          + variableName);
    }
    // now retrieve the part value
    String partName = StringFunction.evaluate(partArg, nav);
    return ((MessageValue) value).getPart(partName);
  }

  public static Object evaluate(Object variableArg, Object partArg,
      Object locationArg, Context context) throws FunctionCallException {
    // convert arguments to strings
    Navigator navigator = context.getNavigator();
    String variableName = StringFunction.evaluate(variableArg, navigator);
    // find variable definition
    ContextSupport support = context.getContextSupport();
    ScopeVariableContext variableContext = (ScopeVariableContext) support.getVariableContext();
    VariableDefinition variableDefinition = variableContext.getVariableDefinition(variableName);
    if (variableDefinition == null) {
      throw new FunctionCallException("variable not found: " + variableName);
    }
    // extract variable value
    Object value = variableDefinition.getValue(variableContext.getToken());
    if (!(value instanceof MessageValue)) {
      throw new FunctionCallException(
          "non-message variable does not have part: " + variableName);
    }
    // now retrieve the part value
    String partName = StringFunction.evaluate(partArg, navigator);
    Element part = ((MessageValue) value).getPart(partName);
    // then evaluate the location string
    String locationString = StringFunction.evaluate(locationArg, navigator);
    try {
      XPathQueryEvaluator locationEvaluator = XPathEvaluatorFactory.createQueryEvaluator(locationString);
      locationEvaluator.setNamespaceContext(support.getNamespaceContext());
      /*
       * in BPEL 1.1, the context node is 'the root of the document fragment
       * representing the entire part'
       */
      return locationEvaluator.evaluate(part.getOwnerDocument());
    }
    catch (JaxenException e) {
      throw new FunctionCallException("could not parse query", e);
    }
  }
}