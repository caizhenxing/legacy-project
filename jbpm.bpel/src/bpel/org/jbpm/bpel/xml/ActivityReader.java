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

import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.Flow;
import org.jbpm.bpel.def.LinkDefinition;
import org.jbpm.bpel.def.Pick;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.jpdl.xml.Problem;

/**
 * @author Juan Cantú
 * @version $Revision: 1.14 $ $Date: 2006/08/22 04:13:10 $
 */
public abstract class ActivityReader {

  protected BpelReader bpelReader;

  /**
   * Loads the activity properties from the given DOM element
   */
  public Activity read(Element element, CompositeActivity parent) {
    // create new instance
    Activity activity = createActivity();

    // read standard properties
    readStandardProperties(element, activity, parent);

    // read specific properties
    try {
      readSpecificProperties(element, activity);
    }
    catch (BpelException e) {
      /*
       * exceptions that ocurr inside activity readers are caught at this point
       * and transformed in errors. This allows continuing with the parsing of
       * the rest of the bpel document.
       */
      bpelReader.getProblemHandler().add(new Problem(Problem.LEVEL_ERROR,
          "bpel activity is invalid.", e));
    }

    return activity;
  }

  /**
   * creates an new instance of the bpel activity represented by the parser
   */
  protected abstract Activity createActivity();

  protected void readStandardProperties(Element activityElem,
      Activity activity, CompositeActivity parent) {
    activity.setName(XmlUtil.getAttribute(activityElem, BpelConstants.ATTR_NAME));

    parent.addNode(activity);

    // read activity links
    readSources(activityElem, activity);
    readTargets(activityElem, activity);

    // when initial, validate that the activity either is a "start activity" or
    // has a control flow dependency
    if (activity.isInitial()) {
      Collection targets = activity.getTargets();
      if (!(activity instanceof Sequence || activity instanceof Flow
          || activity instanceof Pick || activity instanceof Receive)
          && (targets == null || targets.isEmpty())) {
        bpelReader.getProblemHandler().add(new ParseProblem(
            "activity can't be initial", activityElem));
      }
    }

    // suppress join failure
    Attr suppressAttr = activityElem.getAttributeNode(BpelConstants.ATTR_SUPPRESS_JOIN_FAILURE);
    activity.setSuppressJoinFailure(bpelReader.readTBoolean(suppressAttr, null));
  }

  /**
   * subclasses override this method to load the activity-specific properties
   * from the given DOM element
   */
  protected void readSpecificProperties(Element activityElem, Activity activity) {
    // nothing done
  }

  protected void readSources(Element activityElem, Activity activity) {
    Element sourcesElem = XmlUtil.getElement(activityElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_SOURCES);

    if (sourcesElem != null) {
      CompositeActivity parent = activity.getCompositeActivity();
      Iterator sourceElemIt = XmlUtil.getElements(sourcesElem,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_SOURCE);
      while (sourceElemIt.hasNext()) {
        Element sourceElem = (Element) sourceElemIt.next();
        String linkName = sourceElem.getAttribute(BpelConstants.ATTR_LINK_NAME);
        LinkDefinition link = parent.findLink(linkName);
        Element transitionElem = XmlUtil.getElement(sourceElem,
            BpelConstants.NS_BPWS,
            BpelConstants.ELEM_TRANSITION_CONDITION);
        if (transitionElem != null) {
          link.setTransitionCondition(bpelReader.readExpression(transitionElem,
              parent));
        }
        activity.addSource(link);
      }
    }
  }

  protected void readTargets(Element activityElem, Activity activity) {
    Element targetsElem = XmlUtil.getElement(activityElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_TARGETS);
    if (targetsElem != null) {
      CompositeActivity parent = activity.getCompositeActivity();
      // targets
      Iterator targetElemIt = XmlUtil.getElements(targetsElem,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_TARGET);
      while (targetElemIt.hasNext()) {
        Element targetElem = (Element) targetElemIt.next();
        String linkName = targetElem.getAttribute(BpelConstants.ATTR_LINK_NAME);
        activity.addTarget(parent.findLink(linkName));
      }
      // join condition
      Element conditionElem = XmlUtil.getElement(targetsElem,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_JOIN_CONDITION);
      if (conditionElem != null) {
        activity.setJoinCondition(bpelReader.readJoinCondition(conditionElem,
            parent));
      }
    }
  }

  public BpelReader getBpelReader() {
    return bpelReader;
  }

  public void setBpelReader(BpelReader bpelReader) {
    this.bpelReader = bpelReader;
  }
}