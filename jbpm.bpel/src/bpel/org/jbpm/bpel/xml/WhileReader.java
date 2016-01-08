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
import org.jbpm.bpel.def.While;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * Encapsulates the logic to create and connect process elements that constitute
 * the <i>while</i> structure.
 * @author Juan Cantú
 * @version $Revision: 1.7 $ $Date: 2006/08/22 04:13:10 $
 */
public class WhileReader extends ActivityReader {

  protected Activity createActivity() {
    return new While();
  }

  protected void readSpecificProperties(Element activityElem, Activity activity) {
    While whileStruct = (While) activity;
    // get while condition
    Element conditionElement = XmlUtil.getElement(activityElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CONDITION);
    whileStruct.setCondition(bpelReader.readExpression(conditionElement,
        whileStruct.getCompositeActivity()));

    // Build activity
    Element childElement = bpelReader.getActivityElement(activityElem);
    bpelReader.readActivity(childElement, whileStruct);
  }
}
