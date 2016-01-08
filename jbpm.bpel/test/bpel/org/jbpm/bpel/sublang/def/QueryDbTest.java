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

import java.util.Collections;

import org.jbpm.bpel.def.Assign;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Assign.Copy;
import org.jbpm.bpel.def.assign.FromVariable;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/09/12 06:48:12 $
 */
public class QueryDbTest extends SnippetDbTestCase {

  protected Snippet createSnippet(BpelDefinition processDefinition) {
    Query query = new Query();
    
    FromVariable from = new FromVariable();
    from.setQuery(query);
    
    Copy copy = new Copy();
    copy.setFrom(from);
    
    Assign assign = new Assign();
    assign.setCopies(Collections.singletonList(copy));
    
    processDefinition.getGlobalScope().setRoot(assign);
    
    return query;
  }

  protected Snippet getSnippet(BpelDefinition processDefinition) {
    Assign assign = (Assign) session.load(Assign.class, 
        session.getIdentifier(processDefinition.getGlobalScope().getRoot()));
    Copy copy = (Copy) assign.getCopies().get(0);
    FromVariable from = (FromVariable) session.load(FromVariable.class, 
        session.getIdentifier(copy.getFrom()));
    return from.getQuery();
  }
}