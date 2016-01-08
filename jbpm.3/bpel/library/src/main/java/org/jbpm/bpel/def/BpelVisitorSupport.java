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

import java.util.Iterator;
import java.util.List;

import org.jbpm.graph.def.NodeCollection;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class BpelVisitorSupport implements BpelVisitor {

  /** {@inheritDoc} */
  public void visit(BpelDefinition process) {
    propagate(process);
  }

  /** {@inheritDoc} */
  public void visit(Empty empty) {
  }

  /** {@inheritDoc} */
  public void visit(Receive receive) {
  }

  /** {@inheritDoc} */
  public void visit(Reply reply) {
  }

  /** {@inheritDoc} */
  public void visit(Invoke invoke) {
  }

  /** {@inheritDoc} */
  public void visit(Assign assign) {
  }

  /** {@inheritDoc} */
  public void visit(Throw throwActivity) {
  }

  /** {@inheritDoc} */
  public void visit(Exit exit) {
  }

  /** {@inheritDoc} */
  public void visit(Wait wait) {
  }

  /** {@inheritDoc} */
  public void visit(Sequence sequence) {
    propagate(sequence);
  }

  /** {@inheritDoc} */
  public void visit(If ifActivity) {
    propagate(ifActivity);
  }

  /** {@inheritDoc} */
  public void visit(While whileActivity) {
    propagate(whileActivity);
  }

  /** {@inheritDoc} */
  public void visit(Pick pick) {
    propagate(pick);
  }

  /** {@inheritDoc} */
  public void visit(Flow flow) {
    propagate(flow);
  }

  /** {@inheritDoc} */
  public void visit(Scope scope) {
    propagate(scope);
  }

  /** {@inheritDoc} */
  public void visit(Compensate compensate) {
  }

  /** {@inheritDoc} */
  public void visit(Rethrow rethrow) {
  }

  /** {@inheritDoc} */
  public void visit(Validate validate) {
  }

  protected void propagate(NodeCollection nodeCollection) {
    List nodes = nodeCollection.getNodes();
    if (nodes != null) {
      Iterator nodeIt = nodes.iterator();
      while (nodeIt.hasNext()) {
        Activity activity = (Activity) nodeIt.next();
        activity.accept(this);
      }
    }
  }
}
