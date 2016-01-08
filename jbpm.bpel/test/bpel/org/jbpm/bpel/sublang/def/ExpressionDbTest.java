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

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.While;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/09/12 06:48:12 $
 */
public class ExpressionDbTest extends SnippetDbTestCase {

  protected Snippet createSnippet(BpelDefinition processDefinition) {
    Expression condition = new Expression();
    
    While whileStruct = new While();
    whileStruct.setCondition(condition);
    
    processDefinition.getGlobalScope().setRoot(whileStruct);

    return condition;
  }

  protected Snippet getSnippet(BpelDefinition processDefinition) {
    While whileStruct = (While) session.load(While.class, 
        session.getIdentifier(processDefinition.getGlobalScope().getRoot()));
    return whileStruct.getCondition();
  }
}