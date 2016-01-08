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

import org.jbpm.graph.exe.ExecutionContext;

import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.def.Invoker;
import org.jbpm.bpel.integration.def.Receiver;

/**
 * Invokes an operation on a web service provided by a partner.
 * @see "WS-BPEL 2.0 &sect;6.5 &sect;11.3"
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class Invoke extends Activity {

  private Invoker invoker;

  private static final long serialVersionUID = 1L;

  public Invoke() {
  }

  public Invoke(String name) {
    super(name);
  }

  public void execute(ExecutionContext exeContext) {
    IntegrationService integrationService = Receiver.getIntegrationService(exeContext.getJbpmContext());
    integrationService.invoke(invoker, exeContext.getToken());
    leave(exeContext);
  }

  public Invoker getInvoker() {
    return invoker;
  }

  public void setInvoker(Invoker invoker) {
    this.invoker = invoker;
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}
