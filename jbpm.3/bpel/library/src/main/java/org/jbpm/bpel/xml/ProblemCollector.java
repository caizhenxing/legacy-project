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

import java.util.ArrayList;
import java.util.List;

import org.jbpm.jpdl.xml.Problem;

public class ProblemCollector extends DefaultProblemHandler {

  private List problems = new ArrayList();
  private String resource;

  public ProblemCollector() {
  }

  public ProblemCollector(String resource) {
    this.resource = resource;
  }

  public void add(Problem problem) {
    if (problem.getResource() == null) {
      problem.setResource(resource);
    }
    super.add(problem);
    problems.add(problem);
  }

  public List getProblems() {
    return problems;
  }
}