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
package org.jbpm.bpel.app;

import org.apache.commons.lang.enums.Enum;

import org.jbpm.bpel.integration.exe.EndpointReference;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class AppPartnerRole {

  private Initiate initiate;
  private EndpointReference endpointReference;

  public Initiate getInitiate() {
    return initiate;
  }

  public void setInitiate(Initiate initiate) {
    this.initiate = initiate;
  }

  public EndpointReference getEndpointReference() {
    return endpointReference;
  }

  public void setEndpointReference(EndpointReference endpointReference) {
    this.endpointReference = endpointReference;
  }

  public static final class Initiate extends Enum {

    public static final Initiate STATIC = new Initiate("static");
    public static final Initiate PUSH = new Initiate("push");
    public static final Initiate PULL = new Initiate("pull");

    private static final long serialVersionUID = 1L;

    /**
     * Enumeration constructor.
     * @param name the desired textual representation.
     */
    private Initiate(String name) {
      super(name);
    }

    /**
     * Gets an enumeration object by name.
     * @param name a string that identifies one element
     * @return the appropiate enumeration object, or <code>null</code> if the
     *         object does not exist
     */
    public static Initiate valueOf(String name) {
      return name != null ? (Initiate) getEnum(Initiate.class, name)
          : Initiate.STATIC;
    }
  }
}
