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
package org.jbpm.bpel.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.jbpm.JbpmConfiguration;
import org.jbpm.bpel.app.AppDescriptor;
import org.jbpm.bpel.app.AppMyRole;
import org.jbpm.bpel.app.AppPartnerLink;
import org.jbpm.bpel.app.AppPartnerRole;
import org.jbpm.bpel.app.AppScope;
import org.jbpm.bpel.integration.catalog.CompositeCatalog;
import org.jbpm.bpel.integration.catalog.ServiceCatalog;
import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.jpdl.xml.Problem;
import org.jbpm.util.ClassLoaderUtil;

/**
 * @author Juan Cantú
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class AppDescriptorReader {

  private ProblemHandler problemHandler;

  public static final String RESOURCE_CATALOG_READERS = "resource.catalog.readers";

  private static final AppDescriptorReader instance = new AppDescriptorReader();
  private static final Map catalogReaders = new HashMap();
  private static final Log log = LogFactory.getLog(AppDescriptorReader.class);

  protected AppDescriptorReader() {
  }

  public void read(AppDescriptor appDescriptor, InputSource documentSource) {
    // get the thread-local parser
    DocumentBuilder builder = XmlUtil.getDocumentBuilder();
    // prepare custom error handling
    ErrorHandlerAdapter problemAdapter = new ErrorHandlerAdapter(
        getProblemHandler());
    builder.setErrorHandler(problemAdapter);
    try {
      // parse content
      Document descriptorDoc = builder.parse(documentSource);
      // proceed only if no parse errors arose
      if (!problemAdapter.hasErrors()) {
        Element descriptorElem = descriptorDoc.getDocumentElement();
        // version
        String version = XmlUtil.getAttribute(descriptorElem,
            BpelConstants.ATTR_VERSION);
        if (version != null)
          appDescriptor.setVersion(Integer.valueOf(version));
        // bindable subcontext
        appDescriptor.setBindSubcontext(XmlUtil.getAttribute(descriptorElem,
            BpelConstants.ATTR_BIND_SUBCONTEXT));
        // global scope attributes
        readScope(descriptorElem, appDescriptor);
        // service catalogs
        Element catalogsElem = XmlUtil.getElement(descriptorElem,
            BpelConstants.NS_VENDOR,
            BpelConstants.ELEM_SERVICE_CATALOGS);
        if (catalogsElem != null) {
          ServiceCatalog catalog = readServiceCatalogs(catalogsElem,
              documentSource.getSystemId());
          appDescriptor.setServiceCatalog(catalog);
        }
      }
    }
    catch (SAXException e) {
      getProblemHandler().add(new Problem(Problem.LEVEL_ERROR,
          "application descriptor contains invalid xml", e));
    }
    catch (IOException e) {
      getProblemHandler().add(new Problem(Problem.LEVEL_ERROR,
          "application descriptor is not readable", e));
    }
    finally {
      // reset error handling behavior
      builder.setErrorHandler(null);
    }
  }

  // configuration elements
  // //////////////////////////////////////////////////////////////

  public void readScope(Element scopeElem, AppScope scope) {
    // name - there can be unnamed scope descriptors
    scope.setName(XmlUtil.getAttribute(scopeElem, BpelConstants.ATTR_NAME));
    // partner links
    Element partnerLinksElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_VENDOR,
        BpelConstants.ELEM_PARTNER_LINKS);
    if (partnerLinksElem != null) readPartnerLinks(partnerLinksElem, scope);
    // subordinated scopes
    Element scopesElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_VENDOR,
        BpelConstants.ELEM_SCOPES);
    if (scopesElem != null) readScopes(scopesElem, scope);
  }

  public void readScopes(Element scopesElem, AppScope parentScope) {
    Iterator scopeElemIt = XmlUtil.getElements(scopesElem,
        BpelConstants.NS_VENDOR,
        BpelConstants.ELEM_SCOPE);
    while (scopeElemIt.hasNext()) {
      AppScope scope = new AppScope();
      readScope((Element) scopeElemIt.next(), scope);
      parentScope.addScope(scope);
    }
  }

  public void readPartnerLinks(Element plinksElem, AppScope scope) {
    Iterator plinkElemIt = XmlUtil.getElements(plinksElem,
        BpelConstants.NS_VENDOR,
        BpelConstants.ELEM_PARTNER_LINK);
    while (plinkElemIt.hasNext()) {
      AppPartnerLink partnerLink = readPartnerLink((Element) plinkElemIt.next());
      scope.addPartnerLink(partnerLink);
    }
  }

  public AppPartnerLink readPartnerLink(Element partnerLinkElem) {
    AppPartnerLink partnerLink = new AppPartnerLink();
    // name
    partnerLink.setName(partnerLinkElem.getAttribute(BpelConstants.ATTR_NAME));
    // my role
    Element myRoleElem = XmlUtil.getElement(partnerLinkElem,
        BpelConstants.NS_VENDOR,
        BpelConstants.ELEM_MY_ROLE);
    if (myRoleElem != null) {
      partnerLink.setMyRole(readMyRole(myRoleElem));
    }
    // partner role
    Element partnerRoleElem = XmlUtil.getElement(partnerLinkElem,
        BpelConstants.NS_VENDOR,
        BpelConstants.ELEM_PARTNER_ROLE);
    if (partnerRoleElem != null) {
      partnerLink.setPartnerRole(readPartnerRole(partnerRoleElem));
    }
    return partnerLink;
  }

  public AppMyRole readMyRole(Element myRoleElem) {
    AppMyRole myRole = new AppMyRole();
    // bind name
    myRole.setBindName(XmlUtil.getAttribute(myRoleElem,
        BpelConstants.ATTR_BIND_NAME));
    return myRole;
  }

  public AppPartnerRole readPartnerRole(Element partnerRoleElem) {
    AppPartnerRole partnerRole = new AppPartnerRole();
    // initiate
    String initiateValue = XmlUtil.getAttribute(partnerRoleElem,
        BpelConstants.ATTR_INITIATE);
    AppPartnerRole.Initiate initiate = AppPartnerRole.Initiate.valueOf(initiateValue);
    partnerRole.setInitiate(initiate);
    // service reference
    Element referenceElem = XmlUtil.getElement(partnerRoleElem);
    if (referenceElem != null) {
      if (AppPartnerRole.Initiate.STATIC.equals(initiate)) {
        EndpointReference endpointRef = EndpointReference.readServiceRef(referenceElem);
        partnerRole.setEndpointReference(endpointRef);
      }
      else {
        getProblemHandler().add(new ParseProblem(
            "not treating element as endpoint reference, since initiate mode is not static",
            referenceElem));
      }
    }
    else if (AppPartnerRole.Initiate.STATIC.equals(initiate)) {
      getProblemHandler().add(new ParseProblem("endpoint reference missing",
          partnerRoleElem));
    }
    return partnerRole;
  }

  public ServiceCatalog readServiceCatalogs(Element catalogsElem,
      String documentBaseURI) {
    ArrayList catalogs = new ArrayList();
    Iterator catalogElemIt = XmlUtil.getElements(catalogsElem,
        BpelConstants.NS_VENDOR);
    while (catalogElemIt.hasNext()) {
      Element catalogElem = (Element) catalogElemIt.next();
      ServiceCatalogReader catalogReader = getCatalogReader(catalogElem.getLocalName());
      if (catalogReader != null) {
        ServiceCatalog catalog = catalogReader.read(catalogElem,
            documentBaseURI);
        catalogs.add(catalog);
      }
      else {
        getProblemHandler().add(new ParseProblem(
            "unrecognized service catalog", catalogElem));
      }
    }
    ServiceCatalog resultingCatalog;
    switch (catalogs.size()) {
    case 0:
      resultingCatalog = null;
      break;
    case 1:
      resultingCatalog = (ServiceCatalog) catalogs.get(0);
      break;
    default:
      resultingCatalog = new CompositeCatalog(catalogs);
    }
    return resultingCatalog;
  }

  public ProblemHandler getProblemHandler() {
    if (problemHandler == null) {
      problemHandler = new DefaultProblemHandler();
    }
    return problemHandler;
  }

  public void setProblemHandler(ProblemHandler problemHandler) {
    this.problemHandler = problemHandler;
  }

  public static AppDescriptorReader getInstance() {
    return instance;
  }

  public static ServiceCatalogReader getCatalogReader(String name) {
    return (ServiceCatalogReader) catalogReaders.get(name);
  }

  public static void registerCatalogReader(String name,
      ServiceCatalogReader reader) {
    catalogReaders.put(name, reader);
    log.debug("registered catalog reader: name=" + name + ", class="
        + reader.getClass().getName());
  }

  private static void initCatalogReaders() {
    // load file from the classpath
    String resourceName = JbpmConfiguration.Configs.getString(RESOURCE_CATALOG_READERS);
    InputStream resourceStream = ClassLoaderUtil.getStream(resourceName);
    if (resourceStream == null) {
      throw new BpelException("catalog readers resource not found: "
          + resourceName);
    }
    Element readersElem;
    try {
      // parse xml document
      readersElem = XmlUtil.getDocumentBuilder()
          .parse(resourceStream)
          .getDocumentElement();
      resourceStream.close();
    }
    catch (Exception e) {
      throw new BpelException("could not parse catalog readers document", e);
    }
    // walk through catalogReader elements
    Iterator readerElemIt = XmlUtil.getElements(readersElem,
        null,
        "catalogReader");
    while (readerElemIt.hasNext()) {
      Element readerElem = (Element) readerElemIt.next();
      String name = readerElem.getAttribute("name");
      // instantiate factory class
      String className = readerElem.getAttribute("class");
      Class readerClass = ClassLoaderUtil.loadClass(className);
      try {
        ServiceCatalogReader reader = (ServiceCatalogReader) readerClass.newInstance();
        // register instance
        registerCatalogReader(name, reader);
      }
      catch (Exception e) {
        log.warn("could not register catalog reader: " + className, e);
      }
    }
  }

  static {
    initCatalogReaders();
  }
}