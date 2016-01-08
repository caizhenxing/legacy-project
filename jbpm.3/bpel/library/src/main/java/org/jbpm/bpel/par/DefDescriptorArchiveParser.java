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

import java.io.ByteArrayInputStream;
import java.util.List;

import org.xml.sax.InputSource;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Import;
import org.jbpm.bpel.xml.ArchiveWsdlLocator;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.DefDescriptorReader;
import org.jbpm.bpel.xml.ProblemCollector;
import org.jbpm.bpel.xml.ProblemHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.par.ProcessArchive;
import org.jbpm.jpdl.par.ProcessArchiveParser;
import org.jbpm.jpdl.xml.Problem;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class DefDescriptorArchiveParser implements ProcessArchiveParser {

  public static final String DEFINITION_DESC_NAME = "META-INF/bpel-definition.xml";

  private static final long serialVersionUID = 1L;

  /** {@inheritDoc} */
  public ProcessDefinition readFromArchive(ProcessArchive archive,
      ProcessDefinition processDefinition) {
    BpelDefinition bpelDefinition = new BpelDefinition();
    // look for definition descriptor
    byte[] entry = archive.removeEntry(DEFINITION_DESC_NAME);
    if (entry != null) {
      ProblemCollector collector = new ProblemCollector(DEFINITION_DESC_NAME);
      DefDescriptor defDescriptor = readDescriptor(entry, collector);
      // create bpel definition
      bpelDefinition.setLocation(defDescriptor.getLocation());
      List imports = defDescriptor.getImports();
      if (!imports.isEmpty()) {
        // read imported documents
        readDocuments(imports, archive, collector);
        bpelDefinition.getImports().setImports(imports);
      }
      // pump the problems from the definition descriptor reader to the process
      // archive
      archive.getProblems().addAll(collector.getProblems());
    }
    else {
      Problem problem = new Problem(Problem.LEVEL_ERROR,
          "definition descriptor not found");
      archive.addProblem(problem);
    }
    return bpelDefinition;
  }

  protected DefDescriptor readDescriptor(byte[] entry,
      ProblemHandler problemHandler) {
    DefDescriptor defDescriptor = new DefDescriptor();
    DefDescriptorReader reader = DefDescriptorReader.getInstance();
    reader.setProblemHandler(problemHandler);
    reader.read(defDescriptor, new InputSource(new ByteArrayInputStream(entry)));
    return defDescriptor;
  }

  public void readDocuments(List imports, ProcessArchive archive,
      ProblemHandler problemHandler) {
    BpelReader bpelReader = BpelReader.getInstance();
    ArchiveWsdlLocator wsdlLocator = new ArchiveWsdlLocator("", archive);
    wsdlLocator.setProblemHandler(problemHandler);
    bpelReader.setProblemHandler(problemHandler);
    for (int i = 0, n = imports.size(); i < n; i++) {
      Import imp = (Import) imports.get(i);
      if (Import.Type.WSDL.equals(imp.getType())) {
        bpelReader.readWsdlDocument(imp, wsdlLocator);
      }
    }
  }
}
