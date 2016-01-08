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

import java.util.Iterator;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.If;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * Encapsulates the logic to create and connect process elements that make up
 * the <i>if</i> structure.
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class IfReader extends ActivityReader {

  protected Activity createActivity() {
    return new If();
  }

  protected void readSpecificProperties(Element ifElem, Activity activity) {
    If ifStruct = (If) activity;

    // read the first conditional branch
    readBranch(ifElem, ifStruct);

    // read the remaining conditional branches (elseIf)
    Iterator elseifElemIt = XmlUtil.getElements(ifElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_ELSEIF);
    while (elseifElemIt.hasNext()) {
      Element elseifElem = (Element) elseifElemIt.next();
      readBranch(elseifElem, ifStruct);
    }

    // read the default branch
    Element elseElem = XmlUtil.getElement(ifElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_ELSE);
    if (elseElem != null) {
      Element activityElem = bpelReader.getActivityElement(elseElem);
      Activity elseActivity = bpelReader.readActivity(activityElem, ifStruct);
      ifStruct.setElse(elseActivity);
    }
  }

  protected void readBranch(Element branchElem, If ifStruct) {
    // activity
    Element activityElem = bpelReader.getActivityElement(branchElem);
    Activity activity = bpelReader.readActivity(activityElem, ifStruct);

    // condition
    Element conditionElem = XmlUtil.getElement(branchElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CONDITION);
    Expression condition = bpelReader.readExpression(conditionElem, ifStruct);

    ifStruct.setCondition(activity, condition);
  }
}
