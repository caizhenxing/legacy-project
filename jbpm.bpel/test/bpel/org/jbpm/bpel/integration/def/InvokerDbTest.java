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

import javax.wsdl.Operation;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.Invoke;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.Invoker;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.impl.OperationImpl;
import org.jbpm.bpel.wsdl.impl.PartnerLinkTypeImpl;
import org.jbpm.bpel.wsdl.impl.PortTypeImpl;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:16 $
 */
public class InvokerDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition;
  Invoker invoker;
  
  public void setUp() throws Exception {
    super.setUp();
    processDefinition = new BpelDefinition();
    Invoke invoke = new Invoke("child");
    invoker = new Invoker();
    invoke.setInvoker(invoker);
    processDefinition.addNode(invoke);
  }
  
  public void testInCorrelations() {
    invoker.setInCorrelations(new Correlations());
    
    processDefinition = saveAndReload(processDefinition); 
    invoker = getInvoker();
    
    assertNotNull( invoker.getInCorrelations() );
  }
  
  public void testOutCorrelations() {
    invoker.setOutCorrelations(new Correlations());
    
    processDefinition = saveAndReload(processDefinition); 
    invoker = getInvoker();
    
    assertNotNull( invoker.getOutCorrelations() );
  }
  
  public void testOperation() { 
    //prepare operation
    PartnerLinkType plinkType = new PartnerLinkTypeImpl();
    PartnerLinkTypeImpl.RoleImpl role = new PartnerLinkTypeImpl.RoleImpl();
    plinkType.setFirstRole(role);   
    PortTypeImpl portType = new PortTypeImpl();
    role.setPortType(portType);
    Operation operation = new OperationImpl();
    portType.addOperation(operation);
    ImportsDefinition imports = processDefinition.getImports();
    imports.addPartnerLinkType(plinkType);
    PartnerLinkDefinition plinkDefinition = new PartnerLinkDefinition();
    plinkDefinition.setName("pl");
    plinkDefinition.setPartnerLinkType(plinkType);
    processDefinition.getGlobalScope().addPartnerLink(plinkDefinition);    
    
    //set operation
    invoker.setOperation(operation);
    
    processDefinition = saveAndReload(processDefinition); 
    invoker = getInvoker();

    assertNotNull(invoker.getOperation());
  }
  
  public void testPartnerLink() {   
    PartnerLinkDefinition pl = new PartnerLinkDefinition();
    pl.setName("pl");
    processDefinition.getGlobalScope().addPartnerLink(pl);
    invoker.setPartnerLink(pl);
    
    processDefinition = saveAndReload(processDefinition); 
    invoker = getInvoker();

    assertNotNull( invoker.getPartnerLink() );
  }
  
  public void testInputVariable() {   
    VariableDefinition variable = new VariableDefinition();
    variable.setName("v");
    processDefinition.getGlobalScope().addVariable(variable);
    invoker.setInputVariable(variable);
    
    processDefinition = saveAndReload(processDefinition); 

    assertNotNull( getInvoker().getInputVariable() );
  }
  
  public void testOutputVariable() {   
    VariableDefinition variable = new VariableDefinition();
    variable.setName("v");
    processDefinition.getGlobalScope().addVariable(variable);
    invoker.setOutputVariable(variable);
    
    processDefinition = saveAndReload(processDefinition); 

    assertNotNull( getInvoker().getOutputVariable() );
  }
  
  private Invoker getInvoker() {
    Invoke invoke  = ((Invoke) processDefinition.getNode("child"));
    return invoke.getInvoker();
  }
}
