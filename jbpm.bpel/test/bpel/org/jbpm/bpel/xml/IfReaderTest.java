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
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class IfReaderTest extends AbstractReaderTestCase {
  
  public void testBranches() throws Exception {
    String xml = 
      "<if>" +
      "	<condition>$condition1</condition>" +
      " <empty name='case1'/>" +
      "	<elseif>" +
      "  <condition>$condition2</condition>" +
      "  <empty name='case2'/>" +
      " </elseif>" +
      "</if>";
    If ifStruct = (If) readActivity(xml);
    List cases = ifStruct.getBranches();
    assertEquals(2, cases.size());
    
    Activity case1 = (Activity) cases.get(0);
    assertNotNull(case1);
    assertEquals("case1", case1.getName());
    assertEquals(case1.getCompositeActivity(), ifStruct);
    assertEquals("$condition1", ifStruct.getCondition(case1).getText());
    
    Activity case2 = (Activity) cases.get(1);
    assertEquals("case2", case2.getName());
    assertNotNull(case2);
    assertEquals(case2.getCompositeActivity(), ifStruct);
    assertEquals("$condition2", ifStruct.getCondition(case2).getText());
  }
  
  public void testElse() throws Exception {
    String xml = 
      "<if>" +
      "	<condition>$condition1</condition>" +
      " <empty name='case1'/>" +
      " <else>" +
      "	 <empty name='o'/>" +
      " </else>" +
      "</if>";
    If ifStruct = (If) readActivity(xml);
    Activity otherwise = ifStruct.getElse();
    assertNotNull(otherwise);
    assertEquals("o", otherwise.getName());
    assertEquals(otherwise.getCompositeActivity(), ifStruct);
  }
  
  public void testElseDefault() throws Exception {
    String xml = 
      "<if>" +
      "	<condition>$condition1</condition>" +
      " <empty name='case1'/>" +
      "</if>";
    If ifStruct = (If) readActivity(xml);
    Activity elseActivity = ifStruct.getElse();
    assertNull(elseActivity);
  }
}