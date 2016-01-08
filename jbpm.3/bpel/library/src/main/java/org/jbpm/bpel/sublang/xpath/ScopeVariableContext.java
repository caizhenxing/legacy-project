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

import org.jaxen.UnresolvableException;
import org.jaxen.VariableContext;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
class ScopeVariableContext implements VariableContext {

  private Token token;

  public ScopeVariableContext(Token token) {
    this.token = token;
  }

  public Token getToken() {
    return token;
  }

  public VariableDefinition getVariableDefinition(String variableName) {
    Activity activity = (Activity) token.getNode();
    return activity.getCompositeActivity().findVariable(variableName);
  }

  public Object getVariableValue(String namespaceURI, String prefix,
      String localName) throws UnresolvableException {
    if (namespaceURI != null) {
      throw new UnresolvableException("variable not found: " + prefix + ':'
          + localName);
    }
    Object value;
    // look for a dot in the variable name, indicating a message part access
    int dotIndex = localName.indexOf('.');
    if (dotIndex == -1) {
      /*
       * dotless name, variable is described by a schema type or an element -
       * find variable definition
       */
      VariableDefinition definition = getVariableDefinition(localName);
      if (definition == null) {
        throw new UnresolvableException("variable not found: " + localName);
      }
      // extract variable value
      value = definition.getValue(token);
      // prevent direct access to a message variable
      if (value instanceof MessageValue) {
        throw new UnresolvableException("illegal access to message variable: "
            + localName);
      }
    }
    else {
      /*
       * dotted name, variable is characterized by a messsage - find variable
       * definition
       */
      String messageName = localName.substring(0, dotIndex);
      VariableDefinition definition = getVariableDefinition(messageName);
      if (definition == null) {
        throw new UnresolvableException("variable not found: " + messageName);
      }
      // extract variable value
      value = definition.getValue(token);
      // prevent access to a non-existent part
      if (!(value instanceof MessageValue)) {
        throw new UnresolvableException(
            "non-message variable does not have part: " + localName);
      }
      MessageValue messageValue = (MessageValue) value;
      // retrieve part value
      String partName = localName.substring(dotIndex + 1);
      value = messageValue.getPart(partName);
    }
    return value;
  }
}
