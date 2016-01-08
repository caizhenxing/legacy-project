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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.jpdl.xml.Problem;

public class DefaultProblemHandler implements ProblemHandler {

  private static final Log log = LogFactory.getLog(ProblemHandler.class);

  public void add(Problem problem) {
    String detail = problem.toString();
    // remove the problem level prefix
    detail = detail.substring(detail.indexOf(']') + 1);
    Throwable exception = problem.getException();
    switch (problem.getLevel()) {
    case Problem.LEVEL_WARNING:
      log.warn(detail, exception);
      break;
    case Problem.LEVEL_ERROR:
      log.error(detail, exception);
      break;
    case Problem.LEVEL_FATAL:
      log.fatal(detail, exception);
      break;
    }
  }
}
