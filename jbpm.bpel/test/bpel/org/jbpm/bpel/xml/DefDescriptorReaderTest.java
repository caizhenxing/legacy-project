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

import java.io.StringReader;

import junit.framework.TestCase;

import org.xml.sax.InputSource;

import org.jbpm.bpel.def.Import;
import org.jbpm.bpel.par.DefDescriptor;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class DefDescriptorReaderTest extends TestCase {
  
  private DefDescriptorReader reader = DefDescriptorReader.getInstance();
  
  public void testReadUri() {
    ProblemCollector problems = new ProblemCollector();
    reader.setProblemHandler(problems);
    
    String locationUri = getClass().getResource("bpelDefinitionSample.xml").toString();
    DefDescriptor defDescriptor = new DefDescriptor();
    reader.read(defDescriptor, new InputSource(locationUri));
    
    assertTrue(problems.getProblems().isEmpty());
  }
  
  public void testDefinitionLocation() throws Exception {
    // read descriptor
    String text = "<bpelDefinition location='hello.bpel' xmlns='http://jbpm.org/bpel'/>";
    DefDescriptor defDescriptor = new DefDescriptor();
    reader.read(defDescriptor, new InputSource( new StringReader(text) ));
    // assertions
    assertEquals("hello.bpel", defDescriptor.getLocation());	
  }

  public void testDefinitionImports() throws Exception {
    // read descriptor
    String text = 
      "<bpelDefinition xmlns='http://jbpm.org/bpel'>" +
      "  <imports>" +
      "    <wsdl />" +
      "    <schema />" +
      "  </imports>" +
      "</bpelDefinition>";
    DefDescriptor defDescriptor = new DefDescriptor();
    reader.read(defDescriptor, new InputSource( new StringReader(text) ));
    // assertions
    assertEquals(2, defDescriptor.getImports().size());
  }

  public void testImportNamespace() throws Exception {
    // read descriptor
    String text = "<wsdl namespace='http://jbpm.org/bpel/examples'/>";
    Import imp = reader.readImport(XmlUtil.parseElement(text));
    // assertions
    assertEquals(BpelConstants.NS_EXAMPLES, imp.getNamespace());
  }

  public void testImportLocation() throws Exception {
    // read descriptor
    String text = "<wsdl location='hello.wsdl'/>";
    Import imp = reader.readImport(XmlUtil.parseElement(text));
    // assertions
    assertEquals("hello.wsdl", imp.getLocation());
  }
  
  public void testImportTypeWsdl() throws Exception {
    // read descriptor
    String text = "<wsdl />";
    Import imp = reader.readImport(XmlUtil.parseElement(text));
    // assertions
    assertEquals(Import.Type.WSDL, imp.getType());
  }
  
  public void testImportTypeXmlSchema() throws Exception {
    // read descriptor
    String text = "<schema />";
    Import imp = reader.readImport(XmlUtil.parseElement(text));
    // assertions
    assertEquals(Import.Type.XML_SCHEMA, imp.getType());
  }
}
