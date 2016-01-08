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

import java.util.List;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import org.jbpm.bpel.integration.catalog.URLCatalog;
import org.jbpm.bpel.xml.util.XmlUtil;

public class URLCatalogReaderTest extends TestCase {
  
  private URLCatalogReader reader = new URLCatalogReader();
  
  private static final String CATALOG_TEXT = 
    "<urlCatalog xmlns='" + BpelConstants.NS_VENDOR + "'>" +
    " <wsdl location='partnerLinkTypeSample.wsdl' />" +    
    " <wsdl location='propertyAliasSample.wsdl' />" +
    "</urlCatalog>";

  public URLCatalogReaderTest(String name) {
    super(name);
  }

  public void testRead() throws Exception {
    Element catalogElem = XmlUtil.parseElement(CATALOG_TEXT);
    String contextURI = getClass().getResource(".").toString();
    URLCatalog catalog = (URLCatalog) reader.read(catalogElem, contextURI);
    List locations = catalog.getLocations();
    assertEquals(2, locations.size());
    // first definition
    String location = (String) locations.get(0);
    assertEquals("partnerLinkTypeSample.wsdl", location);
    // second definition
    location = (String) locations.get(1);
    assertEquals("propertyAliasSample.wsdl", location);
  }
}
