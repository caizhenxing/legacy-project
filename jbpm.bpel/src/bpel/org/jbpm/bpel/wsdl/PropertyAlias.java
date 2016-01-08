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
package org.jbpm.bpel.wsdl;

import java.io.Serializable;

import javax.wsdl.Message;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;

import org.jbpm.bpel.sublang.def.Query;

/**
 * A property alias maps a global property to a location in a specific message
 * part. The property name becomes an alias for the message part and location.
 * @see "WS-BPEL 2.0 &sect;8.2"
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/09/12 08:21:47 $
 */
public interface PropertyAlias extends ExtensibilityElement, Serializable {

  /**
   * Gets the global property acting as alias.
   * @return the property associated with this alias
   */
  public Property getProperty();

  /**
   * Sets the property acting as alias.
   * @param property the property to associate with this alias
   */
  public void setProperty(Property property);

  /**
   * Gets the aliased WSDL message.
   * @return the message associated with this alias, if the
   *         <code>messageType</code> attribute occured in the property alias
   *         element; <code>null</code> otherwise
   */
  public Message getMessage();

  /**
   * Sets the aliased WSDL message
   * @param message the message to associate with this alias
   */
  public void setMessage(Message message);

  /**
   * Gets the name of the aliased message part.
   * @return the part name, if the <code>part</code> attribute occured in the
   *         property alias element; <code>null</code> otherwise
   */
  public String getPart();

  /**
   * Sets the name of the aliased message part.
   * @param part the part name to associate with this alias
   */
  public void setPart(String part);

  /**
   * Gets the name of the aliased XML schema type.
   * @return the schema type name, if the <code>type</code> attribute occured
   *         in the property alias element; <code>null</code> otherwise
   */
  public QName getType();

  /**
   * Sets the name of the aliased XML schema type.
   * @param type the schema type name to associate with this alias
   */
  public void setType(QName type);

  /**
   * Gets the name of the aliased XML schema element.
   * @return the schema element name, if the <code>element</code> attribute
   *         occured in the WSDL document; <code>null</code> otherwise
   */
  public QName getElement();

  /**
   * Sets the name of the aliased XML schema element.
   * @param element the schema element name to associate with this alias
   */
  public void setElement(QName element);

  /**
   * Gets the query of this property alias.
   * @return the query in place
   */
  public Query getQuery();

  /**
   * Sets the query of this property alias.
   * @param query the new query
   */
  public void setQuery(Query query);
}