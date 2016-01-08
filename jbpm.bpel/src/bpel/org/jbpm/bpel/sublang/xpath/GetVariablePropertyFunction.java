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

import javax.xml.namespace.QName;

import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.NamespaceContext;
import org.jaxen.Navigator;
import org.jaxen.function.StringFunction;

import org.jbpm.bpel.variable.def.VariableDefinition;

/**
 * BPEL library function to extract global property values from variables.
 * <p>
 * <code><i>any</i> bpws:getVariableData(<i>string</i> variableName,
 * <i>string</i> propertyName?)</code>
 * </p>
 * @see "WS-BPEL 2.0 &sect;9.1, 14.1"
 * @author Alejandro Guízar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
class GetVariablePropertyFunction implements Function {

  public Object call(Context context, List args) throws FunctionCallException {
    if (args.size() != 2) {
      throw new FunctionCallException(
          "getVariableProperty() requires two arguments");
    }
    return evaluate(args.get(0), args.get(1), context);
  }

  public static Object evaluate(Object variableArg, Object propertyArg,
      Context context) throws FunctionCallException {
    // convert arguments to strings
    Navigator nav = context.getNavigator();
    ContextSupport sup = context.getContextSupport();
    // find variable definition
    String variableName = StringFunction.evaluate(variableArg, nav);
    ScopeVariableContext variableContext = (ScopeVariableContext) sup.getVariableContext();
    VariableDefinition variableDefinition = variableContext.getVariableDefinition(variableName);
    if (variableDefinition == null) {
      throw new FunctionCallException("variable not found: " + variableName);
    }
    // resolve property name
    String propertyName = StringFunction.evaluate(propertyArg, nav);
    NamespaceContext nsContext = sup.getNamespaceContext();
    QName propertyQName = toQName(propertyName, nsContext);
    // extract property value
    return variableDefinition.getPropertyValue(propertyQName,
        variableContext.getToken());
  }

  public static QName toQName(String prefixedName,
      NamespaceContext namespaceContext) {
    QName qualifiedName;
    int colonIndex = prefixedName.indexOf(':');
    if (colonIndex == -1) {
      qualifiedName = new QName(prefixedName);
    }
    else {
      String name = prefixedName.substring(colonIndex + 1);
      String prefix = prefixedName.substring(0, colonIndex);
      String uri = namespaceContext.translateNamespacePrefixToUri(prefix);
      qualifiedName = new QName(uri, name, prefix);
    }
    return qualifiedName;
  }
}
