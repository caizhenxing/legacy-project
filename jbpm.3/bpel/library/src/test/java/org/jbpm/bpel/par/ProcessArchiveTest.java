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

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.wsdl.Definition;

import junit.framework.TestCase;

import org.jbpm.jpdl.par.ProcessArchive;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Import;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ProcessArchiveTest extends TestCase {

  public ProcessArchiveTest(String name) {
    super(name);
  }

  public void testParseProcessDefinition() throws Exception {
    // parse definition
    ProcessArchive archive = createProcessArchive("archiveSample.zip");
    BpelDefinition processDefinition = (BpelDefinition) archive.parseProcessDefinition();
    // no problems
    assertTrue(archive.getProblems().isEmpty());
    // bpel location
    assertEquals("processSample.bpel", processDefinition.getLocation());
    // process name 
    assertEquals("sampleProcess", processDefinition.getName());
    // import count
    List imports = processDefinition.getImports().getImports();
    assertEquals(1, imports.size());
    // wsdl location
    Import imp = (Import) imports.get(0);
    assertEquals("wsdl/partnerLinkTypeSample.wsdl", imp.getLocation());
    // wsdl target namespace
    Definition definition = (Definition) imp.getDocument();
    assertEquals("http://manufacturing.org/wsdl/purchase", definition.getTargetNamespace());
  }
  
  public void testParseProcessDefinition_1_1() throws Exception {
    // parse definition
    ProcessArchive archive = createProcessArchive("archiveSample-1_1.zip");
    BpelDefinition processDefinition = (BpelDefinition) archive.parseProcessDefinition();
    // no problems
    assertTrue(archive.getProblems().isEmpty());
    // location
    assertEquals("bpel/processSample-1_1.bpel", processDefinition.getLocation());
    // process name 
    assertEquals("sampleProcess", processDefinition.getName());
    // import count
    List imports = processDefinition.getImports().getImports();
    assertEquals(1, imports.size());
    // wsdl location
    Import imp = (Import) imports.get(0);
    assertEquals("wsdl/partnerLinkTypeSample-1_1.wsdl", imp.getLocation());
    // wsdl target namespace
    Definition definition = (Definition) imp.getDocument();
    assertEquals("http://manufacturing.org/wsdl/purchase", definition.getTargetNamespace());
  }
  
  private ProcessArchive createProcessArchive(String resourceName) throws Exception {
    InputStream archiveStream = getClass().getResourceAsStream(resourceName);
    ProcessArchive archive = new ProcessArchive(new ZipInputStream(archiveStream));
    archiveStream.close();
    return archive;
  }
}
