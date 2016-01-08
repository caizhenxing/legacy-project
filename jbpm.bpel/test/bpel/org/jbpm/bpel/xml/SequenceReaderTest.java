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

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Sequence;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class SequenceReaderTest extends AbstractReaderTestCase {
  
  public void testActivities() throws Exception {
    String xml = 
      "<sequence>" +
      "	<empty name='first'/>" +
      "	<empty name='second'/>" +
      "</sequence>";
    Sequence sequence = (Sequence) readActivity(xml);
    Activity first = (Activity) sequence.getNode("first");
    assertNotNull(first);
    assertEquals(sequence, first.getCompositeActivity());
    Activity second = (Activity) sequence.getNode("second");
    assertNotNull(second);
    assertEquals(sequence, second.getCompositeActivity());
  } 
}
