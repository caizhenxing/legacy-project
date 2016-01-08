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

import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.def.Replier;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * A reply activity is used to send a response to a request previously accepted
 * through a receive activity.
 * @see "WS-BPEL 2.0 &sect;11.4"
 * @author Juan Cantú
 * @version $Revision: 1.11 $ $Date: 2006/08/22 04:13:10 $
 */
public class Reply extends Activity {

  private Replier replier;

  private static final long serialVersionUID = 1L;

  public Reply() {
  }

  public Reply(String name) {
    super(name);
  }

  public void execute(ExecutionContext exeContext) {
    IntegrationService integrationService = Receiver.getIntegrationService(exeContext.getJbpmContext());
    integrationService.reply(replier, exeContext.getToken());
    leave(exeContext);
  }

  public Replier getReplier() {
    return replier;
  }

  public void setReplier(Replier replier) {
    this.replier = replier;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}
