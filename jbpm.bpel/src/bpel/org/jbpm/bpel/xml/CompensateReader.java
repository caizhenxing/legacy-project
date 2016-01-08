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
import org.jbpm.bpel.def.Compensate;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.Scope;

import com.ibm.wsdl.util.xml.DOMUtils;

/**
 * @author Juan Cantú
 * @version $Revision: 1.9 $ $Date: 2006/08/22 04:13:10 $
 */
public class CompensateReader extends ActivityReader {

  public Activity createActivity() {
    return new Compensate();
  }

  public void readSpecificProperties(Element activityElem, Activity activity) {
    Compensate compensate = (Compensate) activity;
    CompositeActivity parent = compensate.getCompositeActivity();

    String scopeName = DOMUtils.getAttribute(activityElem,
        BpelConstants.ATTR_SCOPE);

    if (scopeName != null) {
      Scope scope = (Scope) getScope(parent).findActivity(scopeName,
          Scope.class);
      if (scope == null) {
        bpelReader.getProblemHandler().add(new ParseProblem("scope not found",
            activityElem));
        return;
      }
      compensate.setScope(scope);
    }
  }

  private Scope getScope(CompositeActivity compositeActivity) {
    while (true) {
      if (compositeActivity instanceof Scope) return (Scope) compositeActivity;
      compositeActivity = compositeActivity.getCompositeActivity();
    }
  }
}
