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
package org.jbpm.bpel.sublang.def;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Empty;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class JoinConditionDbTest extends SnippetDbTest {

  protected Snippet createSnippet(BpelDefinition processDefinition) {
    JoinCondition joinCondition = new JoinCondition();
    
    Activity activity = new Empty();
    activity.setJoinCondition(joinCondition);
    
    processDefinition.getGlobalScope().setRoot(activity);

    return joinCondition;
  }

  protected Snippet getSnippet(BpelDefinition processDefinition) {
    Activity activity = processDefinition.getGlobalScope().getRoot();
    return activity.getJoinCondition();
  }
}