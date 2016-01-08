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
package org.jbpm.bpel.xml;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import org.jbpm.jpdl.xml.Problem;

public class ErrorListenerAdapter implements ErrorListener {

  private ProblemHandler handler;
  private boolean hasErrors;

  public ErrorListenerAdapter(ProblemHandler handler) {
    this.handler = handler;
    hasErrors = false;
  }

  public void error(TransformerException te) {
    Problem problem = new Problem(Problem.LEVEL_ERROR, te.getMessage(),
        te.getException());
    SourceLocator locator = te.getLocator();
    if (locator != null) readLocation(problem, locator);
    handler.add(problem);
    hasErrors = true;
  }

  public void fatalError(TransformerException te) {
    Problem problem = new Problem(Problem.LEVEL_FATAL, te.getMessage(),
        te.getException());
    SourceLocator locator = te.getLocator();
    if (locator != null) readLocation(problem, locator);
    handler.add(problem);
    hasErrors = true;
  }

  public void warning(TransformerException te) {
    Problem problem = new Problem(Problem.LEVEL_WARNING, te.getMessage(),
        te.getException());
    SourceLocator locator = te.getLocator();
    if (locator != null) readLocation(problem, locator);
    handler.add(problem);
  }

  private void readLocation(Problem problem, SourceLocator locator) {
    problem.setLine(new Integer(locator.getLineNumber()));
    String resource = locator.getPublicId();
    if (resource == null) {
      resource = locator.getSystemId();
    }
    problem.setResource(resource);
  }

  public boolean hasErrors() {
    return hasErrors;
  }
}