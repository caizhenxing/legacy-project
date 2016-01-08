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
import org.jbpm.bpel.def.Sequence;

/**
 * Encapsulates the logic to create and connect process elements that make up
 * the <i>sequence</i> structure.
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class SequenceReader extends ActivityReader {

  protected Activity createActivity() {
    return new Sequence();
  }

  protected void readSpecificProperties(Element activityElem, Activity activity) {
    Sequence sequence = (Sequence) activity;
    Iterator childActivityElemIt = bpelReader.getActivityElements(activityElem);
    while (childActivityElemIt.hasNext()) {
      Element childActivityElem = (Element) childActivityElemIt.next();
      bpelReader.readActivity(childActivityElem, sequence);
    }
  }
}
