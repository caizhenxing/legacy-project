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

import javax.wsdl.Message;
import javax.xml.namespace.QName;

import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.wsdl.xml.WsdlConstants;

/**
 * A property alias maps a global property to a location in a specific message
 * part. The property name becomes an alias for the message part and location.
 * @author Alejandro Guízar
 * @version $Revision: 1.8 $ $Date: 2006/08/22 04:13:10 $
 */
public class PropertyAliasImpl extends AbstractExtension implements
    PropertyAlias {

  private Property property;
  private Message message;
  private String part;
  private QName type;
  private QName element;
  private Query query;

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a property alias and sets its element type.
   */
  public PropertyAliasImpl() {
    setElementType(WsdlConstants.Q_PROPERTY_ALIAS);
  }

  /** {@inheritDoc} */
  public Property getProperty() {
    return property;
  }

  /** {@inheritDoc} */
  public void setProperty(Property property) {
    this.property = property;
  }

  /** {@inheritDoc} */
  public Message getMessage() {
    return message;
  }

  /** {@inheritDoc} */
  public void setMessage(Message message) {
    this.message = message;
  }

  /** {@inheritDoc} */
  public String getPart() {
    return part;
  }

  /** {@inheritDoc} */
  public void setPart(String part) {
    this.part = part;
  }

  /** {@inheritDoc} */
  public QName getType() {
    return type;
  }

  /** {@inheritDoc} */
  public void setType(QName type) {
    this.type = type;
  }

  /** {@inheritDoc} */
  public QName getElement() {
    return element;
  }

  /** {@inheritDoc} */
  public void setElement(QName element) {
    this.element = element;
  }

  /** {@inheritDoc} */
  public Query getQuery() {
    return query;
  }

  /** {@inheritDoc} */
  public void setQuery(Query query) {
    this.query = query;
  }
}
