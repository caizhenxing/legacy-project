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
package org.jbpm.bpel.exe;

import org.jbpm.bpel.def.*;

/**
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class StructuredActivityTest extends ActivityExeTest {
  Activity nestedNode;
  
  public void setUp() {
    super.setUp();
    
    StructuredActivity structure = (StructuredActivity) node;
    nestedNode = new Empty("nestedNode");
    structure.addNode(nestedNode);
    structure.getBegin().connect(nestedNode);
    nestedNode.connect(structure.getEnd());
    
    //add nested link
    LinkDefinition nestedLink = new LinkDefinition("nested");
    node.addSource(nestedLink);
    successor.addTarget(nestedLink);
    nestedLink.setTransitionCondition(TRUE_EXPRESSION);
    nestedLink.createInstance(token);
  }
  
  protected Activity createBpelActivity() {
    StructuredActivity structure = new Sequence();
    structure.setName("structuredNode");
    
    //an empty composite parent is set 
    //structure.setCompositeActivity(new CompositeActivity() {});
    
    return structure;
  }
  
  protected Activity getExeNode() {
    return ((StructuredActivity)node).getBegin();
  }   
  
  public void assertCompleted() {
    super.assertCompleted();
    assertEquals(Boolean.TRUE, defaultLink.getInstance(context.getToken()).getStatus());
  }
  
  public void assertNotCompleted() {
    super.assertNotCompleted();
    assertEquals(null, defaultLink.getInstance(context.getToken()).getStatus()); 
  }
  
  public void assertSkipped() {
    super.assertSkipped();
    assertEquals(Boolean.FALSE, defaultLink.getInstance(context.getToken()).getStatus()); 
  }  
  
}
