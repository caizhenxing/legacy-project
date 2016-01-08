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

import javax.wsdl.PortType;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;

/**
 * A partner link type characterizes the conversational relationship between two
 * services. It does so by defining the <i>roles</i> played by each service in
 * the conversation, and specifying the port type provided to receive messages.
 * @see "WS-BPEL 2.0 &sect;7.1"
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public interface PartnerLinkType extends ExtensibilityElement, Serializable {

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
   * Gets the first role of this partner link type.
   * @return the first (mandatory) role in place
   */
  public Role getFirstRole();

  /**
   * Sets the first role of this partner link type.
   * @param firstRole the new first (mandatory) role
   */
  public void setFirstRole(Role firstRole);

  /**
   * Gets the second role of this partner link type.
   * @return the second (optional) role
   */
  public Role getSecondRole();

  /**
   * Sets the second role of this partner link type.
   * @param secondRole the new second (optional) role
   */
  public void setSecondRole(Role secondRole);

  /**
   * Creates a role.
   * @return the newly created role
   */
  public Role createRole();

  /**
   * The role played by each of the services in the conversation.
   */
  public static interface Role extends Serializable {

    /**
     * Gets the name of this role.
     * @return the name in place
     */
    public String getName();

    /**
     * Sets the name of this role.
     * @param name the new name
     */
    public void setName(String name);

    /**
     * Gets the port type of this role.
     * @return the port type in place
     */
    public PortType getPortType();

    /**
     * Sets the port type of this role.
     * @param portType the new port type
     */
    public void setPortType(PortType portType);
  }
}