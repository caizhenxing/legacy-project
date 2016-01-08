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
package org.jbpm.bpel.integration.exe;

import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.jbpm.bpel.integration.def.PartnerLinkDefinition;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class PartnerLinkInstance {

  private static final long serialVersionUID = 1L;

  long id;
  private PartnerLinkDefinition definition;
  private EndpointReference partnerReference;

  public PartnerLinkInstance() {
  }

  public PartnerLinkDefinition getDefinition() {
    return definition;
  }

  public void setDefinition(PartnerLinkDefinition definition) {
    this.definition = definition;
  }

  public EndpointReference getPartnerReference() {
    return partnerReference;
  }

  public void setPartnerReference(EndpointReference partnerReference) {
    QName portTypeName = definition.getPartnerRole().getPortType().getQName();
    QName portTypeRef = partnerReference.getPortTypeName();
    // if there is no port type reference
    if (portTypeRef == null) {
      // take it from the partner link definition
      partnerReference.setPortTypeName(portTypeName);
    }
    // otherwise verify it matches the partner link definition
    else if (!portTypeRef.equals(portTypeName)) {
      throw new RuntimeException("port type mismatch: expected=" + portTypeName
          + ", found=" + portTypeRef);
    }
    this.partnerReference = partnerReference;
  }

  public long getId() {
    return id;
  }

  public String toString() {
    return new ToStringBuilder(this).append("name", definition.getName())
        .append("id", id)
        .toString();
  }
}
