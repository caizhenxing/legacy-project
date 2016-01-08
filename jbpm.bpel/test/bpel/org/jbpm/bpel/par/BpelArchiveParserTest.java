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
import org.jbpm.bpel.xml.ArchiveWsdlLocator;
import org.jbpm.bpel.xml.BpelReader;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:11 $
 */
public class BpelArchiveParserTest extends TestCase {
  
  private BpelArchiveParser bpelParser = new BpelArchiveParser();
  private BpelDefinition processDefinition = new BpelDefinition();
  
  public BpelArchiveParserTest(String name) {
    super(name);
  }

  public void testReadFromArchive() throws Exception {
    ProcessArchive archive = createProcessArchive("archiveSample.zip");
    // parse process definition
    processDefinition.setLocation("processSample.bpel");
    bpelParser.readFromArchive(archive, processDefinition);
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
  
  public void testReadFromArchive_1_1() throws Exception {
    ProcessArchive archive = createProcessArchive("archiveSample-1_1.zip");
    // add wsdl document
    Import imp = new Import();
    imp.setNamespace("http://manufacturing.org/wsdl/purchase");
    imp.setLocation("wsdl/partnerLinkTypeSample-1_1.wsdl");
    imp.setType(Import.Type.WSDL);
    BpelReader.getInstance().readWsdlDocument(imp, new ArchiveWsdlLocator("", archive));
    processDefinition.getImports().addImport(imp);
    // parse process definition
    processDefinition.setLocation("bpel/processSample-1_1.bpel");
    bpelParser.readFromArchive(archive, processDefinition);
    // process name 
    assertEquals("sampleProcess", processDefinition.getName());
    // import count
    List imports = processDefinition.getImports().getImports();
    assertEquals(1, imports.size());
  }
  
  private ProcessArchive createProcessArchive(String resourceName) throws Exception {
    InputStream archiveStream = getClass().getResourceAsStream(resourceName);
    ProcessArchive archive = new ProcessArchive(new ZipInputStream(archiveStream));
    archiveStream.close();
    return archive;
  }
}
