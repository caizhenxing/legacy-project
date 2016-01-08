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
package org.jbpm.bpel.xml;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.Validate;
import org.jbpm.bpel.variable.def.VariableDefinition;

/**
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class ValidateReader extends ActivityReader {

  protected Activity createActivity() {
    return new Validate();
  }

  protected void readSpecificProperties(Element activityElem, Activity activity) {
    Validate validate = (Validate) activity;
    CompositeActivity parent = activity.getCompositeActivity();
    // properties
    String[] variableNames = activityElem.getAttribute(BpelConstants.ATTR_VARIABLES)
        .split("\\s");
    for (int v = 0; v < variableNames.length; v++) {
      String variableName = variableNames[v];
      VariableDefinition variable = parent.findVariable(variableName);
      if (variable == null) {
        throw new BpelException("variable not found: " + variableName,
            activityElem);
      }
      validate.addVariable(variable);
    }
  }
}
