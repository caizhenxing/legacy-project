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
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.UnresolvableException;
import org.jaxen.VariableContext;
import org.jaxen.function.StringFunction;

/**
 * BPEL library function to indicate the status of a link.
 * <p>
 * <code><i>boolean</i> bpws:getLinkStatus(<i>string</i> linkName)</code>
 * </p>
 * @see "WS-BPEL 2.0 &sect;9.1, 12.5.1"
 * @author Juan Cantú
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
class GetLinkStatusFunction implements Function {

  public Object call(Context context, List args) throws FunctionCallException {
    if (args.size() != 1) {
      throw new FunctionCallException("getLinkStatus() requires one argument");
    }
    return evaluate(args.get(0), context);
  }

  public static Boolean evaluate(Object arg, Context context)
      throws FunctionCallException {
    String variableName = StringFunction.evaluate(arg, context.getNavigator());
    VariableContext variableContext = context.getContextSupport()
        .getVariableContext();
    try {
      return (Boolean) variableContext.getVariableValue(null,
          null,
          variableName);
    }
    catch (UnresolvableException e) {
      throw new FunctionCallException("variable not found: " + variableName);
    }
  }
}