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
package org.jbpm.bpel.exe;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.variable.exe.MessageValue;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class FaultInstance {

  long id;

  private QName name;
  private MessageValue messageValue;
  private Element elementValue;

  public FaultInstance() {
  }

  public FaultInstance(QName name) {
    this.name = name;
  }

  public FaultInstance(QName name, MessageValue messageValue) {
    this.name = name;
    this.messageValue = messageValue;
  }

  public FaultInstance(QName name, Element elementValue) {
    this.name = name;
    this.elementValue = elementValue;
  }

  public QName getName() {
    return name;
  }

  public void setName(QName name) {
    this.name = name;
  }

  public Element getElementValue() {
    return elementValue;
  }

  public void setElementValue(Element elementValue) {
    this.elementValue = elementValue;
  }

  public MessageValue getMessageValue() {
    return messageValue;
  }

  public void setMessageValue(MessageValue messageValue) {
    this.messageValue = messageValue;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("FaultValue(name=").append(name);
    if (messageValue != null) {
      buf.append(", message=" + messageValue);
    }
    else if (elementValue != null) {
      buf.append(", element=" + elementValue);
    }
    return buf.append(')').toString();
  }
}
