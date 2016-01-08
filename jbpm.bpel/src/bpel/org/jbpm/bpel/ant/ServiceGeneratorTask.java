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
package org.jbpm.bpel.ant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.wsdl.util.ServiceGenerator;
import org.jbpm.bpel.xml.ProblemCollector;
import org.jbpm.jpdl.par.ProcessArchive;
import org.jbpm.jpdl.xml.Problem;

/**
 * Ant task that generates WSDL binding and service elements.
 * @author Alejandro Guizar
 * @version $Revision: 1.5 $ $Date: 2006/09/05 07:26:07 $
 */
public class ServiceGeneratorTask extends Task {

  private String processfile = null;
  private String servicefile = null;
  private String bindingfile = null;
  private String outputdir = null;

  public void execute() throws BuildException {
    ProcessArchive archive = createProcessArchive();
    BpelDefinition definition = (BpelDefinition) archive.parseProcessDefinition();
    if (Problem.containsProblemsOfLevel(archive.getProblems(),
        Problem.LEVEL_ERROR)) {
      throw new BuildException("process definition is invalid");
    }
    ServiceGenerator generator = new ServiceGenerator();
    if (outputdir == null) {
      throw new BuildException("no output directory specified");
    }
    generator.setOutputDirectory(outputdir);
    if (bindingfile != null) generator.setBindingFile(bindingfile);
    if (servicefile != null) generator.setServiceFile(servicefile);

    ProblemCollector collector = new ProblemCollector();
    generator.setProblemHandler(collector);
    try {
      generator.generatePortComponents(definition);
    }
    catch (RuntimeException e) {
      e.printStackTrace();
      throw new BuildException("could not generate service definition", e);
    }
    if (Problem.containsProblemsOfLevel(collector.getProblems(),
        Problem.LEVEL_ERROR)) {
      throw new BuildException("could not generate service definition");
    }
  }

  private ProcessArchive createProcessArchive() throws BuildException {
    try {
      InputStream processStream = new FileInputStream(processfile);
      ProcessArchive processArchive = new ProcessArchive(new ZipInputStream(
          processStream));
      processStream.close();
      return processArchive;
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new BuildException(
          "could not read process archive: " + processfile, e);
    }
  }

  public void setProcessfile(String processfile) {
    this.processfile = processfile;
  }

  public void setServicefile(String serviceFile) {
    this.servicefile = serviceFile;
  }

  public void setBindingfile(String bindingFile) {
    this.bindingfile = bindingFile;
  }

  public void setOutputdir(String outputdir) {
    this.outputdir = outputdir;
  }
}
