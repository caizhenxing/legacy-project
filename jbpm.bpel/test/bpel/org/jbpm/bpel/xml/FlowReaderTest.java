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
import org.jbpm.bpel.def.Flow;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class FlowReaderTest extends AbstractReaderTestCase {
  
  public void testLinks() throws Exception {
    String xml = 
      "<flow>" +
      "	<empty name='first'/>" +
      "	<empty name='second'/>" +
      " <links>" +
      " 	<link name='l1'/>" +
      " 	<link name='l2'/>" +
      " </links>" +
      "</flow>";
    
    Flow flow = (Flow) readActivity(xml);
    assertEquals("l1", flow.getLink("l1").getName());
    assertEquals("l2", flow.getLink("l2").getName());
  }
  
  public void testLinksDefault() throws Exception {
    String xml = 
      "<flow>" +
      "	<empty name='first'/>" +
      "	<empty name='second'/>" +
      "</flow>";
    
    Flow flow = (Flow) readActivity(xml);
    assertEquals(0, flow.getLinks().size());
  }  
  
  public void testActivities() throws Exception {
    String xml = 
      "<flow>" +
      "	<empty name='first'/>" +
      "	<empty name='second'/>" +
      "</flow>";
    
    Flow flow = (Flow) readActivity(xml);
    Activity first = (Activity) flow.getNode("first");
    assertNotNull(first);
    assertEquals(flow, first.getCompositeActivity());
    Activity second = (Activity) flow.getNode("second");
    assertNotNull(second);
    assertEquals(flow, second.getCompositeActivity());
  }
}
