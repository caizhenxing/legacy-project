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
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.If;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * Encapsulates the logic to create and connect process elements that make up
 * the <i>switch</i> structure.
 * @author Juan Cantú
 * @version $Revision: 1.10 $ $Date: 2006/08/22 04:13:10 $
 */
public class SwitchReader extends ActivityReader {

  protected Activity createActivity() {
    return new If();
  }

  protected void readSpecificProperties(Element switchElem, Activity activity) {
    If switchBlock = (If) activity;
    CompositeActivity parent = switchBlock.getCompositeActivity();

    Iterator caseElemIt = XmlUtil.getElements(switchElem,
        BpelConstants.NS_BPWS,
        "case");
    // Load case clauses
    while (caseElemIt.hasNext()) {
      Element caseElem = (Element) caseElemIt.next();
      // activity
      Element activityElement = bpelReader.getActivityElement(caseElem);
      Activity child = bpelReader.readActivity(activityElement, switchBlock);

      // condition
      Element conditionElement = XmlUtil.getElement(caseElem,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_CONDITION);

      Expression condition = bpelReader.readExpression(conditionElement, parent);
      switchBlock.setCondition(child, condition);
    }

    // Load the otherwise clause. If it doesn't exist create an empty activity.
    Element otherwiseElem = XmlUtil.getElement(switchElem,
        BpelConstants.NS_BPWS,
        "otherwise");

    if (otherwiseElem != null) {
      Element childElement = bpelReader.getActivityElement(otherwiseElem);
      Activity otherwise = bpelReader.readActivity(childElement, switchBlock);
      switchBlock.setElse(otherwise);
    }
  }
}
