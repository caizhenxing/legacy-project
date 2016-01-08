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
package org.jbpm.bpel.integration.jms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.BpelVisitorSupport;
import org.jbpm.bpel.def.Pick;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.xml.BpelException;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
class StartListenersBuilder extends BpelVisitorSupport {

  private final IntegrationControl integrationControl;
  private List startListeners = new ArrayList();

  public StartListenersBuilder(IntegrationControl integrationControl) {
    this.integrationControl = integrationControl;
  }

  public List getStartListeners() {
    return startListeners;
  }

  public void visit(Receive receive) {
    if (receive.isCreateInstance()) {
      createListener(receive.getBpelDefinition(), receive.getReceiver());
    }
  }

  public void visit(Pick pick) {
    if (pick.isCreateInstance()) {
      Iterator onMessageIt = pick.getOnMessages().iterator();
      while (onMessageIt.hasNext()) {
        createListener(pick.getBpelDefinition(), (Receiver) onMessageIt.next());
      }
    }
  }

  public void visit(Sequence sequence) {
    // visit only the first activity
    ((Activity) sequence.getNodes().get(0)).accept(this);
  }

  private void createListener(BpelDefinition process, Receiver receiver) {
    try {
      StartListener listener = new StartListener(process, receiver,
          integrationControl);
      startListeners.add(listener);
    }
    catch (JMSException e) {
      throw new BpelException("could not open start listener", e);
    }
  }
}
