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

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import org.jbpm.jpdl.xml.Problem;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public class ErrorHandlerAdapter implements ErrorHandler {

  private ProblemHandler handler;
  private boolean hasErrors;

  public ErrorHandlerAdapter(ProblemHandler handler) {
    this.handler = handler;
  }

  public void error(SAXParseException pe) {
    Problem problem = new Problem(Problem.LEVEL_ERROR, pe.getMessage(),
        pe.getException());
    problem.setLine(new Integer(pe.getLineNumber()));
    String resource = pe.getPublicId();
    if (resource == null) resource = pe.getSystemId();
    problem.setResource(resource);
    handler.add(problem);
    hasErrors = true;
  }

  public void fatalError(SAXParseException pe) {
    Problem problem = new Problem(Problem.LEVEL_FATAL, pe.getMessage(),
        pe.getException());
    problem.setLine(new Integer(pe.getLineNumber()));
    String resource = pe.getPublicId();
    if (resource == null) resource = pe.getSystemId();
    problem.setResource(resource);
    handler.add(problem);
    hasErrors = true;
  }

  public void warning(SAXParseException pe) {
    Problem problem = new Problem(Problem.LEVEL_WARNING, pe.getMessage(),
        pe.getException());
    problem.setLine(new Integer(pe.getLineNumber()));
    String resource = pe.getPublicId();
    if (resource == null) resource = pe.getSystemId();
    problem.setResource(resource);
    handler.add(problem);
  }

  public boolean hasErrors() {
    return hasErrors;
  }
}