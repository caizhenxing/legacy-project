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
package org.jbpm.bpel.exe;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import org.jbpm.bpel.def.Scope;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public class ScopeIterator implements Iterator {

  private Stack tokensToTraverse;
  private Object currentInstance;
  private ScopeFilter filter;

  public ScopeIterator(Token parent, ScopeFilter filter) {
    tokensToTraverse = new Stack();
    Map children = parent.getChildren();
    if (children != null && !children.isEmpty()) {
      tokensToTraverse.addAll(children.values());
      this.filter = filter;
      findNextInstance();
    }
  }

  public ScopeIterator(Token parent) {
    this(parent, null);
  }

  /** {@inheritDoc} */
  public void remove() {
    currentInstance = null;
    findNextInstance();
  }

  /** {@inheritDoc} */
  public boolean hasNext() {
    return currentInstance != null;
  }

  /** {@inheritDoc} */
  public Object next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }

    Object instanceToReturn = currentInstance;
    findNextInstance();
    return instanceToReturn;
  }

  public Object getInstance(Token token) {
    ContextInstance contextInstance = (ContextInstance) token.getProcessInstance()
        .getInstance(ContextInstance.class);
    return contextInstance.getLocalVariable(Scope.VARIABLE_NAME, token);
  }

  private void findNextInstance() {
    currentInstance = null;

    do {
      Object anInstance = !tokensToTraverse.isEmpty() ? getInstance(nextToken())
          : null;
      if (anInstance != null && (filter == null || filter.evaluate(anInstance))) {
        currentInstance = anInstance;
      }
    } while (currentInstance == null && !tokensToTraverse.isEmpty());
  }

  private Token nextToken() {
    if (tokensToTraverse.isEmpty()) {
      throw new NoSuchElementException();
    }

    Token nextVertex = (Token) tokensToTraverse.pop();
    // add children of the next token to the traverse collection
    Map children = nextVertex.getChildren();
    // vertex children are added if they don't have an instance themselves.
    // This causes that the iterator retrieves only instances of the immediate
    // nested
    // level
    if (getInstance(nextVertex) == null && children != null)
      tokensToTraverse.addAll(children.values());

    return nextVertex;
  }

  static class ScopeFilter {

    Class[] states;

    public ScopeFilter(Class[] states) {
      this.states = states;
    }

    public boolean evaluate(Object instance) {
      ScopeInstance scope = (ScopeInstance) instance;

      for (int i = 0; i < states.length; i++) {
        if (states[i].isInstance(scope.getState())) return true;
      }

      return false;
    }
  }
}
