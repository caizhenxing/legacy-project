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
import org.jbpm.bpel.def.Flow;
import org.jbpm.bpel.def.LinkDefinition;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * Encapsulates the logic to create and connect process elements that make up
 * the <i>flow</i> structure.
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class FlowReader extends ActivityReader {

  public Activity createActivity() {
    return new Flow();
  }

  public void readSpecificProperties(Element flowElem, Activity activity) {
    Flow flow = (Flow) activity;

    // load links
    Element linksElem = XmlUtil.getElement(flowElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_LINKS);
    if (linksElem != null) {
      Iterator linkElemIt = XmlUtil.getElements(linksElem,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_LINK);
      while (linkElemIt.hasNext()) {
        Element linkElem = (Element) linkElemIt.next();
        flow.addLink(new LinkDefinition(
            linkElem.getAttribute(BpelConstants.ATTR_NAME)));
      }
    }

    /* read children activities */
    Iterator activityElemIt = bpelReader.getActivityElements(flowElem);
    while (activityElemIt.hasNext()) {
      Element activityElem = (Element) activityElemIt.next();
      bpelReader.readActivity(activityElem, flow);
    }
  }
}
