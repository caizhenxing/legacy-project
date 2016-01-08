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
package org.jbpm.bpel.integration.def;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Empty;
import org.jbpm.bpel.def.Pick;
import org.jbpm.bpel.integration.def.Correlation;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.Receiver;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class CorrelationDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition;
  Correlation correlation;
  
  public void setUp() throws Exception {
    super.setUp();
    processDefinition = new BpelDefinition();
    Pick pick = new Pick("parent");
    Receiver receiver = new Receiver();
    
    Correlations correlations = new Correlations();
    correlation = new Correlation();
    CorrelationSetDefinition set = new CorrelationSetDefinition();
    set.setName("cs");    
    processDefinition.getGlobalScope().addCorrelationSet(set);
    correlation.setSet(set);
    correlations.addCorrelation(correlation);
    receiver.setCorrelations(correlations);
    
    Activity activity = new Empty("child");
    pick.addNode(activity);
    pick.setOnMessage(activity, receiver);
    processDefinition.addNode(pick);
  }
  
  public void testInitiateYes() {
    correlation.setInitiate(Correlation.Initiate.YES);
    
    processDefinition = saveAndReload(processDefinition); 
    correlation = getCorrelation();
    
    assertEquals( Correlation.Initiate.YES, correlation.getInitiate() );
  }
  
  public void testInitiateNo() {    
    correlation.setInitiate(Correlation.Initiate.NO);
    
    processDefinition = saveAndReload(processDefinition); 
    correlation = getCorrelation();
    
    assertEquals( Correlation.Initiate.NO, correlation.getInitiate() );
  }
  
  public void testInitiateRendezvous() {    
    correlation.setInitiate(Correlation.Initiate.JOIN);
    
    processDefinition = saveAndReload(processDefinition); 
    correlation = getCorrelation();
    
    assertEquals( Correlation.Initiate.JOIN, correlation.getInitiate() );
  }
  
  public void testSet() {    
    processDefinition = saveAndReload(processDefinition); 
    assertEquals("cs", getCorrelation().getSet().getName());
  }
  
  private Correlation getCorrelation() {
    Pick pick  = ((Pick) processDefinition.getNode("parent"));
    Receiver receptor = (Receiver) pick.getOnMessages().get(0);
    return (Correlation) receptor.getCorrelations().getCorrelations().values().iterator().next();
  }
}
