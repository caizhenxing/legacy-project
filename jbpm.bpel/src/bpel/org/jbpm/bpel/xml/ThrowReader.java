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

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Throw;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Juan Cantú
 * @version $Revision: 1.11 $ $Date: 2006/08/22 04:13:10 $
 */
public class ThrowReader extends ActivityReader {

  protected Activity createActivity() {
    return new Throw();
  }

  protected void readSpecificProperties(Element activityElem, Activity activity) {
    Throw throwActivity = (Throw) activity;

    // fault name attribute - required
    String faultPrefixedName = XmlUtil.getAttribute(activityElem,
        BpelConstants.ATTR_FAULT_NAME);
    QName faultName = XmlUtil.getQName(faultPrefixedName, activityElem);
    throwActivity.setFaultName(faultName);

    // fault variable - optional
    String variableName = XmlUtil.getAttribute(activityElem,
        BpelConstants.ATTR_FAULT_VARIABLE);
    if (variableName != null) {
      VariableDefinition faultVariable = throwActivity.getCompositeActivity()
          .findVariable(variableName);
      if (faultVariable == null) {
        bpelReader.getProblemHandler().add(new ParseProblem(
            "variable not found", activityElem));
      }
      else if (faultVariable.getType() instanceof SchemaType) {
        bpelReader.getProblemHandler()
            .add(new ParseProblem(
                "fault variable must be either wsdl message or element; schema type is not allowed",
                activityElem));
      }
      else {
        throwActivity.setFaultVariable(faultVariable);
      }
    }
  }
}
