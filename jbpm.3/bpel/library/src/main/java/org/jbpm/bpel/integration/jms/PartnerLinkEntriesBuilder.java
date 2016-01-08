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
import org.jbpm.bpel.app.AppPartnerRole.Initiate;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.BpelVisitorSupport;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.server.PortEntry;
import org.jbpm.bpel.xml.BpelException;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
class PartnerLinkEntriesBuilder extends BpelVisitorSupport {

  private final Map scopeConfigurations;
  private final Context lookupContext;
  private final Context bindContext;

  private String processName;
  private int processVersion;

  private Map partnerLinkEntries = new HashMap();

  private static final Log log = LogFactory.getLog(PartnerLinkEntriesBuilder.class);

  PartnerLinkEntriesBuilder(Map scopeConfigurations, Context lookupContext,
      Context bindContext) {
    this.scopeConfigurations = scopeConfigurations;
    this.lookupContext = lookupContext;
    this.bindContext = bindContext;
  }

  public Map getPartnerLinkEntries() {
    return partnerLinkEntries;
  }

  public void visit(BpelDefinition process) {
    processName = process.getName();
    processVersion = process.getVersion();
    visit(process.getGlobalScope());
  }

  public void visit(Scope scope) {
    Map appPartnerLinks;
    // extract partner link descriptors and the default destination name
    AppScope appScope = (AppScope) scopeConfigurations.get(scope);
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
      PartnerLinkDefinition partnerLink = (PartnerLinkDefinition) partnerLinkIt.next();
      AppPartnerLink appPartnerLink = (AppPartnerLink) appPartnerLinks.get(partnerLink.getName());
      visit(partnerLink, appPartnerLink);
    }
    propagate(scope);
  }

  protected void visit(PartnerLinkDefinition partnerLink,
      AppPartnerLink appPartnerLink) {
    PartnerLinkEntry entry = new PartnerLinkEntry();
    // the default port info name is the partner link name
    entry.setPortName(partnerLink.getName());
    // the default initiate mode is push
    entry.setPartnerInitiate(Initiate.PUSH);

    // the partner link descriptor may override defaults
    if (appPartnerLink != null) {
      AppMyRole myRole = appPartnerLink.getMyRole();
      if (myRole != null) {
        if (partnerLink.getMyRole() == null) {
          throw new BpelException("partner link has no process role: "
              + partnerLink.getName());
        }
        // override bind name
        String portNameAlt = myRole.getBindName();
        if (portNameAlt != null) entry.setPortName(portNameAlt);
      }
      AppPartnerRole partnerRole = appPartnerLink.getPartnerRole();
      if (partnerRole != null) {
        if (partnerLink.getPartnerRole() == null) {
          throw new BpelException("partner link has no partner role: "
              + partnerLink.getName());
        }
        entry.setPartnerInitiate(partnerRole.getInitiate());
        entry.setPartnerReference(partnerRole.getEndpointReference());
      }
    }
    if (partnerLink.getMyRole() != null) {
      String bindName = entry.getPortName();
      // bind port entry
      bindPortEntry(bindName, partnerLink.getId());
      // retrieve destination
      entry.setDestination(lookupDestination(bindName));
    }
    // register relation entry
    partnerLinkEntries.put(new Long(partnerLink.getId()), entry);
  }

  protected Destination lookupDestination(String destinationName) {
    try {
      Destination destination = (Destination) lookupContext.lookup(destinationName);
      log.debug("retrieved jms destination: " + destinationName);
      return destination;
    }
    catch (NamingException e) {
      throw new BpelException("could not retrieve jms destination: "
          + destinationName, e);
    }
  }

  protected void bindPortEntry(String bindName, long partnerLinkId) {
    PortEntry entry = new PortEntry();
    // fill out required information
    entry.setProcessName(processName);
    entry.setProcessVersion(processVersion);
    entry.setPartnerLinkId(partnerLinkId);
    // bind entry in the naming context
    try {
      bindContext.bind(bindName, entry);
      log.debug("bound port entry: " + bindName);
    }
    catch (NamingException e) {
      throw new BpelException("could not bind port entry: " + bindName, e);
    }
  }
}