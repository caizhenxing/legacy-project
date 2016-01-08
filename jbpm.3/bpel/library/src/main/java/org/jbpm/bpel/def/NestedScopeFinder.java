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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jbpm.graph.def.NodeCollection;

class NestedScopeFinder extends BpelVisitorSupport {

  Collection scopes = new ArrayList();

  protected void propagate(NodeCollection nodeCollection) {
    List nodes = nodeCollection.getNodes();
    if (nodes != null) {
      Iterator nodeIt = nodes.iterator();
      while (nodeIt.hasNext()) {
        Activity activity = (Activity) nodeIt.next();
        if (activity instanceof Scope) scopes.add(activity);
        activity.accept(this);
      }
    }
  }

  public Collection getScopes() {
    return scopes;
  }
}