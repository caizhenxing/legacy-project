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
package org.jbpm.bpel.xml.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.wsdl.Constants;

import org.jbpm.bpel.wsdl.xml.WsdlConstants;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class LocalEntityResolver implements EntityResolver {

  private static final Log log = LogFactory.getLog(LocalEntityResolver.class);

  private static Map entityRegistry = new Hashtable();

  static {
    registerEntity(BpelConstants.NS_VENDOR + "/bpel_definition_1_0.xsd",
        "bpel_definition_1_0.xsd");
    registerEntity(BpelConstants.NS_VENDOR + "/bpel_application_1_0.xsd",
        "bpel_application_1_0.xsd");
    registerEntity(Constants.NS_URI_WSDL, "wsdl.xsd");
    registerEntity("http://www.w3.org/2001/xml.xsd", "namespace.xsd");
    registerEntity(BpelConstants.NS_BPWS, "bpel_2_0.xsd");
    registerEntity(WsdlConstants.NS_PLNK, "plink_2_0.xsd");
    registerEntity(BpelConstants.NS_BPWS_1_1, "bpel_1_1.xsd");
    registerEntity(WsdlConstants.NS_PLNK_1_1, "plink_1_1.xsd");
    registerEntity("-//W3C//DTD XMLSCHEMA 200102//EN", "XMLSchema.dtd");
    registerEntity("datatypes", "datatypes.dtd");
  }

  /**
   * Register the mapping of an entity's public id to its local file name.
   * @param publicId the public ID of the entity
   * @param fileName the entity local file name
   */
  public static void registerEntity(String publicId, String fileName) {
    entityRegistry.put(publicId, fileName);
  }

  /**
   * Returns entity inputSource.
   * 
   * @param publicId the public ID of the entity
   * @param systemId the system ID of the entity
   * @return InputSource of entity
   */
  public InputSource resolveEntity(String publicId, String systemId)
      throws SAXException, IOException {
    if (publicId == null && systemId == null) return null;
    InputSource inputSource = null;
    String entityFileName = getLocalEntityName(publicId, systemId);
    if (entityFileName != null) {
      try {
        URL url = LocalEntityResolver.class.getResource(entityFileName);
        InputStream inputStream = null;
        if (url != null) {
          if (log.isTraceEnabled())
            log.trace(entityFileName + " maps to URL: " + url);
          try {
            inputStream = url.openStream();
          }
          catch (IOException e) {
            log.debug("Failed to open url stream", e);
          }
        }
        if (inputStream != null) {
          inputSource = new InputSource(inputStream);
        }
      }
      catch (Exception e) {
        log.error("Cannot load local entity: " + entityFileName, e);
      }
    }
    return inputSource;
  }

  /**
   * Get the local entity name by looking it up in the local registry. The
   * publicID is used first. If not found, the entity is searched with the
   * systemID.
   * @param publicId the public ID of the entity, might be null
   * @param systemId the system ID of the entity, might be null
   * @return the local filename
   */
  private String getLocalEntityName(String publicId, String systemId) {
    String filename = null;
    if (publicId != null) filename = (String) entityRegistry.get(publicId);
    if (filename == null && systemId != null)
      filename = (String) entityRegistry.get(systemId);
    if (filename == null && systemId != null) {
      try {
        URL url = new URL(systemId);
        String path = url.getPath();
        int slash = path.lastIndexOf('/');
        filename = path.substring(slash + 1);
      }
      catch (MalformedURLException ignored) {
        log.trace("SystemId is not a url: " + systemId, ignored);
        return null;
      }
    }
    if (!entityRegistry.values().contains(filename)) {
      log.warn("Entity is not registered, publicId=" + publicId + " systemId="
          + systemId);
    }
    return filename;
  }
}