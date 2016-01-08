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

import org.jbpm.JbpmContext;
import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.def.InboundMessageActivity;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * A business process provides services to its partners through receive
 * activities.
 * @see "WS-BPEL 2.0 &sect;11.4"
 * @author Juan Cantú
 * @version $Revision: 1.18 $ $Date: 2006/08/22 04:13:10 $
 */
public class Receive extends Activity implements InboundMessageActivity {

  private static final long serialVersionUID = 1L;

  private boolean createInstance;
  private Receiver receiver;

  public Receive() {
  }

  public Receive(String name) {
    super(name);
  }

  public void execute(ExecutionContext exeContext) {
    IntegrationService integrationService = Receiver.getIntegrationService(exeContext.getJbpmContext());
    integrationService.receive(receiver, exeContext.getToken());
  }

  public void terminate(ExecutionContext exeContext) {
    IntegrationService integrationService = Receiver.getIntegrationService(exeContext.getJbpmContext());
    integrationService.endReception(receiver, exeContext.getToken());
  }

  public void messageReceived(Receiver receiver, Token token) {
    // close the receiver
    IntegrationService integrationService = Receiver.getIntegrationService(JbpmContext.getCurrentJbpmContext());
    integrationService.endReception(receiver, token);
    // execute the next activity
    leave(new ExecutionContext(token));
  }

  public Receiver getReceiver() {
    return receiver;
  }

  public void setReceiver(Receiver receiver) {
    this.receiver = receiver;
    receiver.setInboundMessageActivity(this);
  }

  public boolean isCreateInstance() {
    return createInstance;
  }

  public void setCreateInstance(boolean createInstance) {
    this.createInstance = createInstance;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}