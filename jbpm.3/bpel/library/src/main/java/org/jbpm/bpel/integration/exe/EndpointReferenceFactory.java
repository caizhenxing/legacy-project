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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import org.jbpm.JbpmConfiguration;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.util.ClassLoaderUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class EndpointReferenceFactory {

  public static final String RESOURCE_ENDPOINT_FACTORIES = "resource.endpoint.factories";

  private static final Log log = LogFactory.getLog(EndpointReferenceFactory.class);
  private static final List factories = new ArrayList();

  protected EndpointReferenceFactory() {
  }

  public abstract EndpointReference createEndpointReference();

  public abstract boolean acceptsReference(QName endpointRefName,
      String refScheme);

  public static EndpointReferenceFactory getInstance(QName endpointRefName,
      String refScheme) {
    for (int i = 0, n = factories.size(); i < n; i++) {
      EndpointReferenceFactory factory = (EndpointReferenceFactory) factories.get(i);
      if (factory.acceptsReference(endpointRefName, refScheme)) {
        return factory;
      }
    }
    return null;
  }

  public static void registerInstance(EndpointReferenceFactory instance) {
    factories.add(instance);
    log.debug("registered endpoint factory: " + instance.getClass().getName());
  }

  private static void readFactories() {
    // load file from the classpath
    String resourceName = JbpmConfiguration.Configs.getString(RESOURCE_ENDPOINT_FACTORIES);
    InputStream resourceStream = ClassLoaderUtil.getStream(resourceName);
    if (resourceStream == null) {
      throw new BpelException("endpoint factories resource not found: "
          + resourceName);
    }
    Element factoriesElem;
    try {
      // parse xml document
      factoriesElem = XmlUtil.getDocumentBuilder()
          .parse(resourceStream)
          .getDocumentElement();
      resourceStream.close();
    }
    catch (Exception e) {
      throw new BpelException("could not read endpoint factories document", e);
    }
    // walk through endpointFactory elements
    Iterator factoryElemIt = XmlUtil.getElements(factoriesElem,
        null,
        "endpointFactory");
    while (factoryElemIt.hasNext()) {
      Element factoryElem = (Element) factoryElemIt.next();
      // instantiate factory class
      String className = factoryElem.getAttribute("class");
      try {
        Class factoryClass = ClassLoaderUtil.loadClass(className);
        EndpointReferenceFactory factory = (EndpointReferenceFactory) factoryClass.newInstance();
        // register instance
        registerInstance(factory);
      }
      catch (Exception e) {
        log.warn("could not register endpoint factory: " + className, e);
      }
    }
  }

  static {
    readFactories();
  }
}
