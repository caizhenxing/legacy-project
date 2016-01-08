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

import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Wait;

/**
 * @author Juan Cantú
 * @version $Revision: 1.12 $ $Date: 2006/08/22 04:13:10 $
 */
public class WaitReader extends ActivityReader {

  protected Activity createActivity() {
    return new Wait();
  }

  protected void readSpecificProperties(Element activityElem, Activity activity) {
    Wait wait = (Wait) activity;
    Alarm alarm = bpelReader.readAlarm(activityElem,
        wait.getCompositeActivity());
    wait.setAlarm(alarm);
  }
}