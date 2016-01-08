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

import org.w3c.dom.Element;

import com.ibm.wsdl.util.xml.XPathUtils;

public class BpelException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private Element location;

  public BpelException(String message, Element location) {
    this(message);
    this.location = location;
  }

  public BpelException(String message, Exception e) {
    super(message, e);
  }

  public BpelException(String message) {
    super(message);
  }

  public Element getLocation() {
    return location;
  }

  public String toString() {
    String output = super.toString();
    return (location != null) ? output + " ["
        + XPathUtils.getXPathExprFromNode(location) + "]" : output;
  }
}