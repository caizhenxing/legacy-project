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
package org.jbpm.bpel.wsdl.impl;

import javax.wsdl.Definition;
import javax.xml.namespace.QName;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Import;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.wsdl.util.WsdlUtil;

/**
 * @author Juan Cantu
 * @version $Revision: 1.7 $ $Date: 2006/08/22 04:13:11 $
 */
public class MessageImplDbTest extends AbstractDbTestCase {
  
  BpelDefinition processDefinition;
  
  public void testQName() {    
    processDefinition = new BpelDefinition();
    MessageImpl message = new MessageImpl();
    QName qname = new QName("namespace", "anyname");
    message.setQName(qname);
    Definition definition = WsdlUtil.getFactory().newDefinition();
    definition.addMessage(message);
    Import anImport = new Import();
    anImport.setType(Import.Type.WSDL);
    anImport.setNamespace("namespace");
    anImport.setDocument(definition);
    processDefinition.getImports().addImport(anImport);
    //the message type is created when queried for the first time
    MessageType type = processDefinition.getImports().getMessageType(qname);
    
    processDefinition = saveAndReload(processDefinition); 

    type = processDefinition.getImports().getMessageType(qname);
    assertNotNull( type.getMessage().getQName() );
  }
  
  public void testPart() {    
    processDefinition = new BpelDefinition();
    MessageImpl message = new MessageImpl();
    QName qname = new QName("namespace", "anyname");
    message.setQName(qname);
    PartImpl part = new PartImpl();
    part.setName("partName");
    part.setElementName(new QName(null, "anElementName"));
    part.setTypeName(new QName(null, "aType"));
    message.addPart(part);
    Definition definition = WsdlUtil.getFactory().newDefinition();
    definition.addMessage(message);
    Import anImport = new Import();
    anImport.setType(Import.Type.WSDL);
    anImport.setNamespace("namespace");
    anImport.setDocument(definition);
    processDefinition.getImports().addImport(anImport);
    //the message type is created when queried for the first time
    MessageType type = processDefinition.getImports().getMessageType(qname);

    processDefinition = saveAndReload(processDefinition); 
    
    type = processDefinition.getImports().getMessageType(qname);
    part = (PartImpl) type.getMessage().getPart("partName");
    assertEquals("partName", part.getName() );
    assertNotNull( part.getElementName() );
    assertNotNull( part.getTypeName() );    
  }
}
