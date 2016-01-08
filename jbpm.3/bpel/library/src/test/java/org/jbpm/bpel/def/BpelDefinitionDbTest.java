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
package org.jbpm.bpel.def;

import org.jbpm.bpel.db.AbstractDbTestCase;

public class BpelDefinitionDbTest extends AbstractDbTestCase {
  
  public void testScope() {
    BpelDefinition processDefinition = new BpelDefinition("name");
    
    processDefinition = saveAndReload(processDefinition);

    assertNotNull( processDefinition.getGlobalScope() );
  } 
  
  public void testImports() {
    BpelDefinition processDefinition = new BpelDefinition();
    
    processDefinition = saveAndReload(processDefinition);

    assertNotNull( processDefinition.getImports() );
  }
  
  public void testAbstractProcess() {
    BpelDefinition processDefinition = new BpelDefinition();
    processDefinition.setAbstractProcess(true);
    
    processDefinition = saveAndReload(processDefinition);

    assertEquals(true, processDefinition.isAbstractProcess());
  } 
  
  public void testEnableInstanceCompensation() {
    BpelDefinition processDefinition = new BpelDefinition();
    processDefinition.setEnableInstanceCompensation(true);
    
    processDefinition = saveAndReload(processDefinition);

    assertEquals(true ,processDefinition.getEnableInstanceCompensation());
  } 
  
  public void testExpressionLanguage() {
    BpelDefinition processDefinition = new BpelDefinition();
    processDefinition.setExpressionLanguage("xpath");
    
    processDefinition = saveAndReload(processDefinition);

    assertEquals("xpath" ,processDefinition.getExpressionLanguage());
  } 
  
  public void testQueryLanguage() {
    BpelDefinition processDefinition = new BpelDefinition();
    processDefinition.setQueryLanguage("java");
    
    processDefinition = saveAndReload(processDefinition);

    assertEquals("java" ,processDefinition.getQueryLanguage());
  } 
}
