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

import java.util.List;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.If;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class SwitchReaderTest extends AbstractReaderTestCase {
  
  public void testCases() throws Exception {
    String xml = 
      "<switch>" +
      "	<case><condition>$condition1</condition><empty name='case1'/></case>" +
      "	<case><condition>$condition2</condition><empty name='case2'/></case>" +
      "</switch>";
    If ifStruct = (If) readActivity(xml);
    List branches = ifStruct.getBranches();
    assertEquals(2, branches.size());
    
    Activity branch1 = (Activity) branches.get(0);
    assertNotNull(branch1);
    assertEquals("case1", branch1.getName());
    assertEquals(branch1.getCompositeActivity(), ifStruct);
    assertEquals("$condition1", ifStruct.getCondition(branch1).getText());
    
    Activity branch2 = (Activity) branches.get(1);
    assertEquals("case2", branch2.getName());
    assertNotNull(branch2);
    assertEquals(branch2.getCompositeActivity(), ifStruct);
    assertEquals("$condition2", ifStruct.getCondition(branch2).getText());
  }
  
  public void testOtherwise() throws Exception {
    String xml = 
      "<switch>" +
      "	<case><condition>$condition1</condition><empty name='case1'/></case>" +
      "	<otherwise><empty name='o'/></otherwise>" +
      "</switch>";
    If ifStruct = (If) readActivity(xml);
    Activity elseActivity = ifStruct.getElse();
    assertNotNull(elseActivity);
    assertEquals("o", elseActivity.getName());
    assertEquals(elseActivity.getCompositeActivity(), ifStruct);
  }
  
  public void testOtherwiseDefault() throws Exception {
    String xml = 
      "<switch>" +
      "	<case><condition>$condition1</condition><empty name='case1'/></case>" +
      "</switch>";
    If ifStruct = (If) readActivity(xml);
    Activity elseActivity = ifStruct.getElse();
    assertNull(elseActivity);
  }
}