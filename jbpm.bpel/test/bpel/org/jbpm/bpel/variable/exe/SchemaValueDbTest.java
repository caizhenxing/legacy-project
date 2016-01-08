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
package org.jbpm.bpel.variable.exe;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.variable.def.VariableType;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/09/12 06:48:12 $
 */
public class SchemaValueDbTest extends XmlValueDbTestCase {
  
  private static final QName TYPE_NAME = new QName(BpelConstants.NS_XML_SCHEMA, "string");
  private static final String CHILD_CDATA = "mike's bike";

  protected VariableType getVariableType(ImportsDefinition imports) {
    return imports.getSchemaType(TYPE_NAME);
  }

  protected void update(Element variableValue) {
    XmlUtil.setStringValue(variableValue, CHILD_CDATA);
  }

  protected void assertUpdate(Element elementValue) {
    assertFalse(elementValue.hasAttributeNS(BpelConstants.NS_XML_SCHEMA_INSTANCE, BpelConstants.ATTR_NIL));
    assertEquals(CHILD_CDATA, XmlUtil.getStringValue(elementValue));    
  }
}
