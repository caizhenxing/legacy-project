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

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Import;
import org.jbpm.jpdl.par.ProcessArchive;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class DefinitionDescArchiveParserTest extends TestCase {

  DefDescriptorArchiveParser ddArchiveParse = new DefDescriptorArchiveParser();
  
  public DefinitionDescArchiveParserTest(String name) {
    super(name);
  }

  public void testReadFromArchive() throws Exception {
    ProcessArchive archive = createProcessArchive("archiveSample.zip");
    // parse par descriptor
    BpelDefinition processDefinition = (BpelDefinition) ddArchiveParse.readFromArchive(archive, null);
    assertTrue(archive.getProblems().isEmpty());
    // test location
    assertEquals("processSample.bpel", processDefinition.getLocation());
    // test import count
    List imports = processDefinition.getImports().getImports();
    assertNull(imports);
  }
  
  public void testReadFromArchive_1_1() throws Exception {
    ProcessArchive archive = createProcessArchive("archiveSample-1_1.zip");
    // parse par descriptor 
    BpelDefinition processDefinition = (BpelDefinition) ddArchiveParse.readFromArchive(archive, null);
    assertTrue(archive.getProblems().isEmpty());
    // process location
    assertEquals("bpel/processSample-1_1.bpel", processDefinition.getLocation());
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
