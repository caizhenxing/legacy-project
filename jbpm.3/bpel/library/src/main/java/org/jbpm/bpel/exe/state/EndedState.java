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
package org.jbpm.bpel.exe.state;

import org.jbpm.bpel.exe.ScopeInstance;

/**
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class EndedState extends ScopeState {

  private static final long serialVersionUID = 1L;

  public static final EndedState TERMINATED = new EndedState("terminated", 13);

  public static final EndedState COMPENSATED = new EndedState("compensated", 14);

  public static final EndedState COMPLETED = new EndedState("completed", 15) {

    private static final long serialVersionUID = 1L;

    public void compensate(ScopeInstance scope) {
      CompensatingState.enterCompensating(scope);
    }
  };

  public static final EndedState COMPLETED_ABNORMALLY = new EndedState(
      "completedAbnormally", 16);

  public static final EndedState FAULTED = new EndedState("faulted", 17);

  public static final EndedState EXITED = new EndedState("exited", 18);

  protected EndedState(String name, int code) {
    super(name, code);
  }
}
