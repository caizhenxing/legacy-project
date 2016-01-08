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

import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;

/**
 * A property definition creates a globally unique name and associates it with
 * an XML Schema simple type. The intent is not to create a new type, but to
 * create a name that has greater significance than the type itself.
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public interface Property extends ExtensibilityElement, Serializable {

  /**
   * Gets the name of this partner link type.
   * @return the name in place
   */
  public QName getQName();

  /**
   * Sets the name of this partner link type.
   * @param name the new name
   */
  public void setQName(QName name);

  /**
   * Gets the type of this property.
   * @return the type in place
   */
  public QName getType();

  /**
   * Sets the type of this property.
   * @param type the new type
   */
  public void setType(QName type);

  public boolean isUndefined();

  public void setUndefined(boolean undefined);
}