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
import org.jbpm.bpel.def.While;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class RepeatUntilReaderTest extends AbstractReaderTestCase {
  
  public void testActivity() throws Exception {
    String xml = 
      "<repeatUntil>" +
      " <empty name='whileActivity'/>" +
      "<condition>$c</condition>" +
      "</repeatUntil>";
    While whileBlock = (While) readActivity(xml);
    //the while activity must evaluate its condition after running its child
    assertFalse(whileBlock.getEvaluateFirst());
    Activity activity = (Activity) whileBlock.getNode("whileActivity");
    assertNotNull(activity);
    assertEquals(whileBlock, activity.getCompositeActivity());
  }
  
  public void testCondition() throws Exception {
    String xml = 
      "<repeatUntil>" +
      " <empty name='first'/>" +
      "<condition>$c</condition>" +      
      "</repeatUntil>";
    While whileBlock = (While) readActivity(xml);
    assertEquals("$c", whileBlock.getCondition().getText());
  }
}
