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
package org.jbpm.bpel.variable.def;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * Provides information related to an XML Schema type, simple or complex.
 * @see "WS-BPEL 2.0 &sect;9.2"
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/09/13 07:56:57 $
 */
public class SchemaType extends XmlType {

  private static final long serialVersionUID = 1L;

  SchemaType() {
  }

  public SchemaType(QName name) {
    setName(name);
  }

  /** {@inheritDoc} */
  protected Element createElement(VariableDefinition definition) {
    // schema type: pick an arbitrary name (our choice is the variable name)
    return XmlUtil.createElement(definition.getName());
  }
}
