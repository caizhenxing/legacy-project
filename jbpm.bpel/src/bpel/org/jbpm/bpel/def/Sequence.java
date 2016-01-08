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

import org.jbpm.graph.def.Transition;

/**
 * A sequence activity contains one or more activities that are performed
 * sequentially, in the order in which they are listed within the <sequence>
 * element.
 * @see "WS-BPEL 2.0 &sect;12.1"
 * @author Juan Cantú
 * @version $Revision: 1.6 $ $Date: 2006/08/22 04:13:10 $
 */
public class Sequence extends StructuredActivity {

  private static final long serialVersionUID = 1L;

  public Sequence() {
    super();
  }

  public Sequence(String name) {
    super(name);
  }

  public boolean isChildInitial(Activity activity) {
    return activity.equals(getNodes().get(0));
  }

  // children management
  // /////////////////////////////////////////////////////////////////////////////

  protected void addImplicitTransitions(Activity activity) {
    if (nodes == null || nodes.size() == 0) {
      // connect the sequence start to the new one
      begin.connect(activity);
    }
    else {
      // connect the last child of the sequence with the new one
      Activity last = (Activity) nodes.get(nodes.size() - 1);
      Transition leavingTransition = last.getDefaultLeavingTransition();
      end.removeArrivingTransition(leavingTransition);
      activity.addArrivingTransition(leavingTransition);
    }

    // connect the new node with the sequence end
    activity.connect(end);
  }

  protected void removeImplicitTransitions(Activity activity) {
    Transition leaving = activity.getDefaultLeavingTransition();
    Transition arriving = activity.getDefaultArrivingTransition();

    Activity successor = (Activity) leaving.getTo();

    activity.removeArrivingTransition(arriving);
    activity.removeLeavingTransition(leaving);

    successor.removeArrivingTransition(leaving);
    successor.addArrivingTransition(arriving);
  }

  // javadoc description in NodeCollection
  public void reorderNode(int oldIndex, int newIndex) {
    if (nodes != null) {
      // remove the node from old position and remove its incoming and outgoing
      // connections
      Activity reorderedObject = (Activity) nodes.remove(oldIndex);
      removeImplicitTransitions(reorderedObject);

      // add the node to the new position and connect to its new predecessors
      // and successors
      Activity predecessor = (newIndex == 0 ? begin
          : (Activity) nodes.get(newIndex - 1));
      Activity successor = (newIndex == nodes.size() ? end
          : (Activity) nodes.get(newIndex));

      Transition leaving = predecessor.getDefaultLeavingTransition();
      successor.removeArrivingTransition(leaving);
      reorderedObject.addArrivingTransition(leaving);
      reorderedObject.connect(successor);

      nodes.add(newIndex, reorderedObject);
    }
    else {
      throw new IndexOutOfBoundsException("no collection present");
    }
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}