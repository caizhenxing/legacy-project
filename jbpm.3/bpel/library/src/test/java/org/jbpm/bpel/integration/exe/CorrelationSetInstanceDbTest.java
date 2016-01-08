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
package org.jbpm.bpel.integration.exe;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.exe.CorrelationSetInstance;
import org.jbpm.bpel.wsdl.impl.PropertyImpl;
import org.jbpm.bpel.xml.BpelConstants;

public class CorrelationSetInstanceDbTest extends AbstractDbTestCase {
  
  public void testDefinition() {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    // correlation set
    CorrelationSetDefinition correlationDefinition = new CorrelationSetDefinition();
    correlationDefinition.setName("cset");
    processDefinition.getGlobalScope().addCorrelationSet(correlationDefinition);
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    Token token = processInstance.getRootToken();
    // correlation set
    CorrelationSetInstance correlationInstance = correlationDefinition.createInstance(token);
    
    processInstance = saveAndReload(processInstance);

    correlationDefinition = 
      ((BpelDefinition) processInstance.getProcessDefinition()).getGlobalScope().getCorrelationSet("cset");
    correlationInstance = correlationDefinition.getInstance(processInstance.getRootToken());
    assertEquals(correlationDefinition, correlationInstance.getDefinition());
  }  
  
  public void testProperties() throws Exception {
    BpelDefinition processDefinition = new BpelDefinition("definition");
    // read property
    QName Q_ARTIST_PROP = new QName(BpelConstants.NS_EXAMPLES, "tns:artistProperty");
    PropertyImpl property = new PropertyImpl();
    property.setQName(Q_ARTIST_PROP);
    processDefinition.getImports().addProperty(property);
    // correlation set
    CorrelationSetDefinition correlationDefinition = new CorrelationSetDefinition();
    correlationDefinition.setName("cset");

    correlationDefinition.addProperty(property);
    processDefinition.getGlobalScope().addCorrelationSet(correlationDefinition);
    graphSession.saveProcessDefinition(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    Token token = processInstance.getRootToken();
    // correlation set
    CorrelationSetInstance correlationInstance = correlationDefinition.createInstance(token);
    HashMap propertyValues = new HashMap();

    propertyValues.put(Q_ARTIST_PROP, "Clash, The");
    correlationInstance.initialize(propertyValues);
    
    processInstance = saveAndReload(processInstance);

    correlationDefinition = 
      ((BpelDefinition) processInstance.getProcessDefinition()).getGlobalScope().getCorrelationSet("cset");
    correlationInstance = correlationDefinition.getInstance(processInstance.getRootToken());
    assertEquals("Clash, The", correlationInstance.getProperty(Q_ARTIST_PROP));
  }  
}
