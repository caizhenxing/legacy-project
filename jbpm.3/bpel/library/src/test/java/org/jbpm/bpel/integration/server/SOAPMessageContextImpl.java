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
package org.jbpm.bpel.integration.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
class SOAPMessageContextImpl implements SOAPMessageContext {
  
  private Map properties = new HashMap();
  private SOAPMessage message;
      
  public SOAPMessageContextImpl() {
  }
  
  public SOAPMessageContextImpl(SOAPMessage message) {
    this.message = message;
  }
  
  /** {@inheritDoc} */
  public boolean containsProperty(String name) {
    return properties.containsKey(name);
  }

  /** {@inheritDoc} */
  public Object getProperty(String name) {
    return properties.get(name);
  }

  /** {@inheritDoc} */
  public Iterator getPropertyNames() {
    return properties.keySet().iterator();
  }

  /** {@inheritDoc} */
  public void removeProperty(String name) {
    properties.remove(name);
  }

  /** {@inheritDoc} */
  public void setProperty(String name, Object value) {
    properties.put(name, value);
  }
  
  /** {@inheritDoc} */
  public SOAPMessage getMessage() {
    return message;
  }

  /** {@inheritDoc} */
  public void setMessage(SOAPMessage message) {
    this.message = message;
  }

  /** {@inheritDoc} */
  public String[] getRoles() {
    // TODO Auto-generated method stub
    return null;
  }
}