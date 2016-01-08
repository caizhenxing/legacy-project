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

import java.io.Serializable;

import org.apache.commons.lang.enums.Enum;

import com.ibm.wsdl.Constants;

import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class Import implements Serializable {

  private static final long serialVersionUID = 1L;

  long id;
  private String namespace;
  private String location;
  private Type type;

  private transient Object document;

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Object getDocument() {
    return document;
  }

  public void setDocument(Object document) {
    this.document = document;
  }

  public static class Type extends Enum {

    private static final long serialVersionUID = 1L;

    public static Type WSDL = new Type(Constants.NS_URI_WSDL);
    public static Type XML_SCHEMA = new Type(BpelConstants.NS_XML_SCHEMA);

    private Type(String name) {
      super(name);
    }

    public static Type valueOf(String name) {
      return (Type) Enum.getEnum(Type.class, name);
    }
  }
}
