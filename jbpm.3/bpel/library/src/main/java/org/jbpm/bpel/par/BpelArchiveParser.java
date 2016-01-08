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
package org.jbpm.bpel.par;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.ProblemCollector;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.par.ProcessArchive;
import org.jbpm.jpdl.par.ProcessArchiveParser;
import org.jbpm.jpdl.xml.Problem;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class BpelArchiveParser implements ProcessArchiveParser {

  private static final long serialVersionUID = 1L;

  /** {@inheritDoc} */
  public ProcessDefinition readFromArchive(ProcessArchive archive,
      ProcessDefinition definition) {
    // read bpel document only if process archive is error free
    if (Problem.containsProblemsOfLevel(archive.getProblems(),
        Problem.LEVEL_ERROR)) return definition;

    // get a bpel reader
    BpelReader reader = BpelReader.getInstance();
    BpelDefinition bpelDefinition = (BpelDefinition) definition;
    // set up problem collector
    ProblemCollector problemCollector = new ProblemCollector(
        bpelDefinition.getLocation());
    reader.setProblemHandler(problemCollector);
    // read process definition
    reader.read(bpelDefinition, archive);
    // move any problems from bpel reader to process archive
    archive.getProblems().addAll(problemCollector.getProblems());
    return bpelDefinition;
  }
}
