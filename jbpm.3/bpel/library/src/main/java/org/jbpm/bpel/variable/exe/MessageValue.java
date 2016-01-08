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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.wsdl.Part;
import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Element;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class MessageValue {

  long id;
  private MessageType type;
  private Map parts;

  MessageValue() {
  }

  public MessageValue(MessageType type) {
    this.type = type;
  }

  public MessageType getType() {
    return type;
  }

  public Element getPart(String partName) {
    // get the part value
    Element part = (Element) getParts().get(partName);
    if (part == null) {
      // verify the wsdl message defines the part
      Part wsdlPart = type.getMessage().getPart(partName);
      if (wsdlPart == null) {
        // undefined part
        throw new BpelException("Undefined part: " + partName);
      }
      // uninitialized part
      throw new BpelFaultException(BpelConstants.FAULT_UNINITIALIZED_VARIABLE);
    }
    return part;
  }

  public Element getPartForAssign(String partName) {
    Map parts = getParts();
    Element part = (Element) parts.get(partName);
    if (part == null) {
      // verify the wsdl message defines the part
      Part wsdlPart = type.getMessage().getPart(partName);
      if (wsdlPart == null) {
        throw new BpelException("undefined part: " + partName);
      }
      // determine the qualified name of the part element
      QName elementName = wsdlPart.getElementName();
      if (elementName != null) {
        // element part: use the element name
        part = XmlUtil.createElement(elementName);
      }
      else {
        // typed part: pick an arbitrary name (our choice is the part name)
        part = XmlUtil.createElement(null, partName);
      }
      // add the new part
      parts.put(partName, part);
    }
    return part;
  }

  public void setPart(String partName, Object value) {
    Element part = getPartForAssign(partName);
    XmlUtil.setObjectValue(part, value);
  }

  public Map getParts() {
    if (parts == null) {
      parts = new HashMap();
    }
    return parts;
  }

  public void setParts(Map newParts) {
    // ensure the parts map is empty
    Map parts = this.parts;
    if (parts == null) {
      this.parts = parts = new HashMap();
    }
    else {
      parts.clear();
    }
    // make a shallow copy of the parts
    Map wsdlParts = type.getMessage().getParts();
    Iterator partEntryIt = newParts.entrySet().iterator();
    while (partEntryIt.hasNext()) {
      Map.Entry partEntry = (Map.Entry) partEntryIt.next();
      Object partName = partEntry.getKey();
      // ensure the part is defined
      if (!wsdlParts.containsKey(partName)) {
        throw new BpelException("Undefined part: " + partName);
      }
      parts.put(partName, partEntry.getValue());
    }
  }

  public boolean isInitialized() {
    Map wsdlParts = type.getMessage().getParts();
    return wsdlParts == null || wsdlParts.isEmpty() ? true // always consider a
                                                            // message with no
                                                            // defined parts as
                                                            // initialized
        : parts != null && !parts.isEmpty(); // at least one part must have a
                                              // value
  }

  public String toString() {
    return new ToStringBuilder(this).append("type", type)
        .append("parts", parts)
        .append("id", id)
        .toString();
  }
}
