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

/**
 * The exit activity is used to immediately terminate the behavior of a business
 * process instance
 * @see "WS-BPEL 2.0 &sect;14.6"
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public class Exit extends Activity {

  private static final long serialVersionUID = 1L;

  public void execute(ExecutionContext context) {
    // TODO Auto-generated method stub
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}
