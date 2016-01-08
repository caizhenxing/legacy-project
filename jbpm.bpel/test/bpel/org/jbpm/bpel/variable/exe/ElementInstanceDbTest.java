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
package org.jbpm.bpel.variable.exe;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;

/**
 * @author Juan Cantu
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class ElementInstanceDbTest extends AbstractDbTestCase {
  
  public void testValue() throws SAXException {    
    BpelDefinition processDefinition = new BpelDefinition(); 
    graphSession.saveProcessDefinition(processDefinition); 
    ProcessInstance processInstance = processDefinition.createProcessInstance();
    ContextInstance contextInstance = processInstance.getContextInstance();
    contextInstance.createVariable("item", XmlUtil.parseElement("<record artist='happy mondays'/>"));
    
    processInstance = saveAndReload(processInstance);
    
    Element item = (Element) processInstance.getContextInstance().getVariable("item");
    assertNull(item.getNamespaceURI());
    assertEquals("record", item.getLocalName());
    assertEquals("happy mondays", item.getAttribute("artist"));
  }  
}
