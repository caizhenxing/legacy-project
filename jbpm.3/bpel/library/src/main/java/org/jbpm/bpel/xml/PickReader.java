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

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Pick;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class PickReader extends ActivityReader {

  public Activity createActivity() {
    return new Pick();
  }

  public void readSpecificProperties(Element pickElem, Activity activity) {
    Pick pick = (Pick) activity;
    boolean initial = pick.isInitial();

    boolean createInstance = bpelReader.readTBoolean(pickElem.getAttributeNode(BpelConstants.ATTR_CREATE_INSTANCE),
        Boolean.FALSE)
        .booleanValue();

    if (!initial && createInstance) {
      bpelReader.getProblemHandler().add(new ParseProblem(
          "pick must be initial in order to create instances", pickElem));
    }

    pick.setCreateInstance(createInstance);

    Iterator messageElemIt = XmlUtil.getElements(pickElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_ON_MESSAGE);
    while (messageElemIt.hasNext()) {
      Element eventElement = (Element) messageElemIt.next();
      Element activityElement = bpelReader.getActivityElement(eventElement);
      Activity child = bpelReader.readActivity(activityElement, pick);
      Receiver receiver = bpelReader.readReceiver(eventElement, pick);
      pick.setOnMessage(child, receiver);
    }

    Iterator alarmElemIt = XmlUtil.getElements(pickElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_ON_ALARM);
    if (!initial) {
      while (alarmElemIt.hasNext()) {
        Element eventElement = (Element) alarmElemIt.next();
        Element activityElement = bpelReader.getActivityElement(eventElement);
        Activity child = bpelReader.readActivity(activityElement, pick);
        Alarm alarm = bpelReader.readAlarm(eventElement, pick);
        pick.setOnAlarm(child, alarm);
      }
    }
    else if (alarmElemIt.hasNext()) {
      bpelReader.getProblemHandler().add(new ParseProblem(
          "pick must not be initial in order to handle alarms", pickElem));
    }
  }
}
