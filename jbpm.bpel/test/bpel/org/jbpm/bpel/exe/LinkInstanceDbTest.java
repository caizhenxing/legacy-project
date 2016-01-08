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

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Flow;
import org.jbpm.bpel.def.LinkDefinition;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.8 $ $Date: 2006/08/22 04:13:10 $
 */
public class LinkInstanceDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition;
  LinkDefinition link;
  
  public void setUp() throws Exception {
    super.setUp();
    processDefinition = new BpelDefinition("definition");
    Flow flow = new Flow("flow");
    link = new LinkDefinition("link");
    flow.addLink(link);
    processDefinition.getGlobalScope().setRoot(flow);
    graphSession.saveProcessDefinition(processDefinition);
  }
  
  private LinkDefinition getLink() {
    return ((Flow)processDefinition.getGlobalScope().getRoot()).getLink("link");
  }
  
  public void testDefinition() {
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    link.createInstance(processInstance.getRootToken());
    
    processInstance = saveAndReload(processInstance);
    
    link = getLink();
    LinkInstance linkInstance = link.getInstance(processInstance.getRootToken());
    assertEquals("link", linkInstance.getDefinition().getName());
  }
  
  public void testStatus() {
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    Token token = processInstance.getRootToken();
    link.createInstance(token).setStatus(Boolean.TRUE);
    
    processInstance = saveAndReload(processInstance);

    LinkInstance linkInstance = getLink().getInstance(processInstance.getRootToken());
    assertEquals(Boolean.TRUE, linkInstance.getStatus());
  }

  public void testTargetToken() {
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    Token token = processInstance.getRootToken();
    link.createInstance(token).setTargetToken(token);
    
    processInstance = saveAndReload(processInstance);

    LinkInstance linkInstance = getLink().getInstance(processInstance.getRootToken());
    assertEquals(processInstance.getRootToken(), linkInstance.getTargetToken());
  }  
}
