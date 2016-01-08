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

import java.io.Serializable;

import org.jbpm.bpel.exe.ScopeInstance;

/**
 * @author Juan Cantú
 * @version $Revision: 1.5 $ $Date: 2006/08/22 04:13:11 $
 */
public abstract class ScopeState implements Serializable {

  private static final int STATE_COUNT = 19;
  private static final ScopeState[] states = new ScopeState[STATE_COUNT];

  private String name;
  private int code;

  /**
   * Constructs a scope state identified by the given name.
   * @param name
   */
  protected ScopeState(String name, int code) {
    if ((code < 0) && (code <= STATE_COUNT))
      throw new AssertionError("code out of bounds");
    if (states[code] != null) throw new AssertionError("code already in use");
    this.code = code;
    this.name = name;
    states[code] = this;
  }

  /**
   * Requests cancelation of the scope instance argument.
   * @param scope
   */
  public void terminate(ScopeInstance scope) {
    throw newStateException("cancel");
  }

  /**
   * Requests compensation of the scope instance argument.
   * @param scope
   */
  public void compensate(ScopeInstance scope) {
    throw newStateException("compensate");
  }

  /**
   * Notifies the completion of a scope instance inside an execution flow
   * @param scope
   */
  public void completed(ScopeInstance scope) {
    throw newStateException("completed");
  }

  /**
   * Notifies that a fault occured during the execution of a scope instance
   * @param scope
   */
  public void faulted(ScopeInstance scope) {
    throw newStateException("faulted");
  }

  /**
   * Notifies that the children of the given scope instance were terminated
   * @param scope
   */
  public void childrenTerminated(ScopeInstance scope) {
    throw newStateException("childrenTerminated");
  }

  /**
   * Notifies that the children of the given scope instance were compensated
   * @param scope
   */
  public void childrenCompensated(ScopeInstance scope) {
    throw newStateException("childrenCompensated");
  }

  /**
   * Creates an exception to signal that the given transition is illegal from
   * the current state.
   * @param transition the name of the illegal transition
   * @return a newly created exception
   */
  protected IllegalStateException newStateException(String transition) {
    return new IllegalStateException(toString() + " - transition=" + transition);
  }

  /** {@inheritDoc} */
  public String toString() {
    return "[" + name + "]";
  }

  /**
   * Returns an integer representation of this state.
   * @return an integer that identifies the state
   */
  public int toInt() {
    return code;
  }

  public static Object fromInt(int code) {
    return states[code];
  }
}
