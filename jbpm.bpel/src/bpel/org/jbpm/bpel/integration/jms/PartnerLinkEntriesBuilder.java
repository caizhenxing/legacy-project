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
package org.jbpm.bpel.integration.jms;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.bpel.app.AppMyRole;
import org.jbpm.bpel.app.AppPartnerLink;
import org.jbpm.bpel.app.AppPartnerRole;
import org.jbpm.bpel.app.AppScope;
import org.jbpm.bpel.app.AppPartnerRole.InitiateMode;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.BpelVisitorSupport;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.xml.BpelException;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/08/29 23:02:43 $
 */
class PartnerLinkEntriesBuilder extends BpelVisitorSupport {

  private final Map scopeDescriptors;
  private final Context jmsContext;
  private final Destination defaultDestination;

  private Map partnerLinkEntriesById = new HashMap();
  private Map partnerLinkEntriesByHandle = new HashMap();

  private static final Log log = LogFactory.getLog(PartnerLinkEntriesBuilder.class);

  PartnerLinkEntriesBuilder(Map scopeDescriptors, Context jmsContext,
      Destination defaultDestination) {
    this.scopeDescriptors = scopeDescriptors;
    this.jmsContext = jmsContext;
    this.defaultDestination = defaultDestination;
  }

  public Map getPartnerLinkEntriesById() {
    return partnerLinkEntriesById;
  }

  public Map getPartnerLinkEntriesByHandle() {
    return partnerLinkEntriesByHandle;
  }

  public void visit(BpelDefinition process) {
    visit(process.getGlobalScope());
  }

  public void visit(Scope scope) {
    Map appPartnerLinks;
    // extract partner link descriptors and the default destination name
    AppScope appScope = (AppScope) scopeDescriptors.get(scope);
    if (appScope != null) {
      // take partner link descriptors
      appPartnerLinks = appScope.getPartnerLinks();
    }
    else {
      // there is no scope descriptor, so there are no partner link descriptors
      appPartnerLinks = Collections.EMPTY_MAP;
    }
    Iterator partnerLinkIt = scope.getPartnerLinks().values().iterator();
    while (partnerLinkIt.hasNext()) {
      PartnerLinkDefinition definition = (PartnerLinkDefinition) partnerLinkIt.next();
      AppPartnerLink descriptor = (AppPartnerLink) appPartnerLinks.get(definition.getName());

      PartnerLinkEntry entry = buildEntry(definition, descriptor);
      partnerLinkEntriesById.put(new Long(entry.getId()), entry);
      partnerLinkEntriesByHandle.put(entry.getHandle(), entry);
    }
    propagate(scope);
  }

  protected PartnerLinkEntry buildEntry(PartnerLinkDefinition definition,
      AppPartnerLink descriptor) {
    PartnerLinkEntry entry = new PartnerLinkEntry();
    entry.setId(definition.getId());

    // the default handle is the partner link name
    entry.setHandle(definition.getName());
    // the default initiate mode is pull a reference from the catalog
    entry.setInitiateMode(InitiateMode.PULL);

    // the partner link descriptor may override the defaults
    if (descriptor != null) {
      AppMyRole myRole = descriptor.getMyRole();
      if (myRole != null) {
        if (definition.getMyRole() == null) {
          throw new BpelException("partner link has no my role: "
              + definition.getName());
        }
        // override handle
        String handle = myRole.getHandle();
        if (handle != null) entry.setHandle(handle);
      }

      AppPartnerRole partnerRole = descriptor.getPartnerRole();
      if (partnerRole != null) {
        if (definition.getPartnerRole() == null) {
          throw new BpelException("partner link has no partner role: "
              + definition.getName());
        }
        entry.setPartnerReference(partnerRole.getEndpointReference());
        // override initiate mode
        entry.setInitiateMode(partnerRole.getInitiateMode());
      }
    }

    if (definition.getMyRole() != null) {
      // establish a destination
      entry.setDestination(getDestination(entry.getHandle()));
    }

    return entry;
  }

  protected Destination getDestination(String partnerLinkHandle) {
    Destination destination;
    try {
      destination = (Destination) jmsContext.lookup(partnerLinkHandle);
      log.debug("retrieved jms destination: " + partnerLinkHandle);
    }
    catch (NamingException e) {
      if (defaultDestination == null) {
        throw new BpelException("could not retrieve jms destination: "
            + partnerLinkHandle, e);
      }
      destination = defaultDestination;
    }
    return destination;
  }
}