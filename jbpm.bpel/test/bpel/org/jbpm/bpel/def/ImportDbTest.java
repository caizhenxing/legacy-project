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
package org.jbpm.bpel.def;

import org.jbpm.bpel.db.AbstractDbTestCase;

public class ImportDbTest extends AbstractDbTestCase {
  
  public void testLocation() {
    BpelDefinition processDefinition = new BpelDefinition();
    Import anImport = new Import();
    anImport.setLocation("aLocation");
    processDefinition.getImports().addImport(anImport);

    processDefinition = saveAndReload(processDefinition);

    anImport = (Import)processDefinition.getImports().getImports().get(0);
    assertEquals( "aLocation", anImport.getLocation() );
  }
 
  public void testNamespace() {
    BpelDefinition processDefinition = new BpelDefinition();
    Import anImport = new Import();
    anImport.setNamespace("http://www.enoughisenough.org");
    processDefinition.getImports().addImport(anImport);
    
    processDefinition = saveAndReload(processDefinition);

    anImport = (Import)processDefinition.getImports().getImports().get(0);
    assertEquals( "http://www.enoughisenough.org", anImport.getNamespace() );
  }
  
  public void testWsdlType() {
    BpelDefinition processDefinition = new BpelDefinition();
    Import anImport = new Import();
    anImport.setType(Import.Type.WSDL);
    processDefinition.getImports().addImport(anImport);
    
    processDefinition = saveAndReload(processDefinition);

    anImport = (Import)processDefinition.getImports().getImports().get(0);
    assertEquals( Import.Type.WSDL, anImport.getType() );
  }  
  
  public void testSchemaType() {
    BpelDefinition processDefinition = new BpelDefinition();
    Import anImport = new Import();
    anImport.setType(Import.Type.XML_SCHEMA);
    processDefinition.getImports().addImport(anImport);
    
    processDefinition = saveAndReload(processDefinition);

    anImport = (Import)processDefinition.getImports().getImports().get(0);
    assertEquals( Import.Type.XML_SCHEMA, anImport.getType() );
  }  
}