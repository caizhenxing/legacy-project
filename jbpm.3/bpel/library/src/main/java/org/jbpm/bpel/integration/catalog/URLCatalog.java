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
package org.jbpm.bpel.integration.catalog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.bpel.wsdl.util.WsdlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:06:10 $
 */
public class URLCatalog implements ServiceCatalog {

  private String contextURL;
  private List locations = new ArrayList();
  private Definition[] definitions;

  private static final Log log = LogFactory.getLog(URLCatalog.class);

  public String getContextURL() {
    return contextURL;
  }

  public void setContextURL(String contextURL) {
    this.contextURL = contextURL;
  }

  public void addLocation(String location) {
    locations.add(location);
  }

  public List getLocations() {
    return locations;
  }

  protected synchronized Definition[] getDefinitions() {
    if (definitions == null) {
      WSDLReader reader = WsdlUtil.getFactory().newWSDLReader();
      int n = locations.size();
      definitions = new Definition[n];
      for (int i = 0; i < n; i++) {
        String location = (String) locations.get(i);
        Definition definition;
        try {
          definition = reader.readWSDL(contextURL, location);
        }
        catch (WSDLException e) {
          log.debug("ignoring definition read failure: " + e.getMessage());
          definition = WsdlUtil.getFactory().newDefinition();
        }
        definitions[i] = definition;
      }
    }
    return definitions;
  }

  public List lookupServices(QName portTypeName) {
    List services = new ArrayList();
    Definition[] definitions = getDefinitions();
    for (int i = 0, n = definitions.length; i < n; i++) {
      Definition def = definitions[i];
      Iterator serviceIt = def.getServices().values().iterator();
      while (serviceIt.hasNext()) {
        Service service = (Service) serviceIt.next();
        if (serviceImplementsPortType(service, portTypeName)) {
          services.add(service);
          log.debug("found candidate service: name=" + service.getQName()
              + ", definitionURI=" + def.getDocumentBaseURI() + ", portType="
              + portTypeName);
        }
      }
    }
    return services;
  }

  protected static boolean serviceImplementsPortType(Service service,
      QName portTypeName) {
    Iterator portIt = service.getPorts().values().iterator();
    while (portIt.hasNext()) {
      Port port = (Port) portIt.next();
      if (port.getBinding().getPortType().getQName().equals(portTypeName)) {
        return true;
      }
    }
    return false;
  }

  public Service lookupService(QName serviceName) {
    Service service = null;
    String namespace = serviceName.getNamespaceURI();
    Definition[] definitions = getDefinitions();
    for (int i = 0, n = definitions.length; i < n; i++) {
      Definition def = definitions[i];
      if (namespace.equals(def.getTargetNamespace())) {
        service = def.getService(serviceName);
        if (service != null) break;
      }
    }
    return service;
  }
}
