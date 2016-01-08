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
package org.jbpm.bpel.sublang.xpath;

import junit.framework.TestCase;

import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.SimpleVariableContext;

import org.jbpm.bpel.def.LinkDefinition;
import org.jbpm.bpel.exe.LinkInstance;
import org.jbpm.bpel.sublang.xpath.GetLinkStatusFunction;
import org.jbpm.context.def.ContextDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantú
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class GetLinkStatusTest extends TestCase {

  private Context context;
  
  private LinkInstance positive;
  private LinkInstance negative;
  private LinkInstance unset;
  
  protected void setUp() throws Exception {
    // process and context
    ProcessDefinition pd = new ProcessDefinition();
    pd.addDefinition(new ContextDefinition());
    ProcessInstance pi = new ProcessInstance(pd);
    Token rootToken = pi.getRootToken();
    
    positive = new LinkDefinition("positive").createInstance(rootToken);
    positive.setStatus(Boolean.TRUE);

    negative = new LinkDefinition("negative").createInstance(rootToken);
    negative.setStatus(Boolean.FALSE);
    
    unset = new LinkDefinition("unset").createInstance(rootToken);
    
    // jaxen context
    ContextSupport sup = new ContextSupport();
    SimpleVariableContext simpleContext = new SimpleVariableContext();
    
    simpleContext.setVariableValue("positive", positive.getStatus());
    simpleContext.setVariableValue("negative", negative.getStatus());
    simpleContext.setVariableValue("unset", unset.getStatus());
    
    sup.setVariableContext(simpleContext);
    context = new Context(sup);
  }

  public void testEvaluatePositiveLink() throws Exception {
    assertSame(positive.getStatus(), GetLinkStatusFunction.evaluate("positive", context));
  }
  
  public void testEvaluateNegativeLink() throws Exception {
    assertSame(negative.getStatus(), GetLinkStatusFunction.evaluate("negative", context));
  }
  
  public void testEvaluateUnsetLink() throws Exception {
    assertSame(unset.getStatus(), GetLinkStatusFunction.evaluate("unset", context));
  }
}
