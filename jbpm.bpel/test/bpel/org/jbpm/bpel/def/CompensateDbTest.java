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

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public class CompensateDbTest extends AbstractDbTestCase {
  
  public void testScope() {        
    Scope scope = new Scope("parent");
    
    Compensate activity = new Compensate("activity");
    activity.setScope(scope);
    scope.setRoot(activity);
    
    BpelDefinition processDefinition = new BpelDefinition();
    processDefinition.addNode(scope);
    
    processDefinition = saveAndReload(processDefinition);

    scope = (Scope) processDefinition.getNode("parent");
    activity = (Compensate) session.load(Compensate.class, 
        new Long(scope.getNode("activity").getId()));
    assertSame(scope, activity.getScope());
  }
}
