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
package org.jbpm.bpel.def.assign;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.Assign.To;
import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.bpel.integration.exe.PartnerLinkInstance;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantú
 * @version $Revision: 1.6 $ $Date: 2006/08/22 04:13:11 $
 */
public class ToPartnerLink extends To {

  private PartnerLinkDefinition partnerLink;

  private static final long serialVersionUID = 1L;

  public PartnerLinkDefinition getPartnerLink() {
    return partnerLink;
  }

  public void setPartnerLink(PartnerLinkDefinition partnerLink) {
    this.partnerLink = partnerLink;
  }

  public void assign(Token token, Object value) {
    EndpointReference reference;
    if (value instanceof EndpointReference) {
      reference = (EndpointReference) value;
    }
    else if (value instanceof Element) {
      reference = EndpointReference.readServiceRef((Element) value);
    }
    else {
      throw new BpelFaultException(BpelConstants.FAULT_MISMATCHED_ASSIGNMENT);
    }
    PartnerLinkInstance instance = partnerLink.getInstance(token);
    instance.setPartnerReference(reference);
  }

}
