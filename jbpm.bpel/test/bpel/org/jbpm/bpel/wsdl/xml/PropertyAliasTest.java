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
package org.jbpm.bpel.wsdl.xml;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.wsdl.Definition;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.jbpm.bpel.def.Namespace;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Alejandro Gu�zar
 * @version $Revision: 1.16 $ $Date: 2006/08/22 04:13:16 $
 */
public class PropertyAliasTest extends TestCase {

  private Definition definition;
  private Set namespaces;
  
  private static final String NS_COR = "http://example.com/supplyCorrelation.wsdl";
  private static final String NS_PUR = "http://manufacturing.org/wsdl/purchase";

  protected void setUp() throws Exception {
    definition = WsdlUtil.readResource(getClass(), "propertyAliasSample.wsdl");
    
    namespaces = new HashSet();
    namespaces.add(new Namespace("xsd", BpelConstants.NS_XML_SCHEMA));
    namespaces.add(new Namespace("bpws", BpelConstants.NS_BPWS));
    namespaces.add(new Namespace("cor", NS_COR));
    namespaces.add(new Namespace("def", NS_PUR));
    namespaces.add(new Namespace("tns", "http://example.com/supplyMessages.wsdl"));
    namespaces.add(new Namespace("po", "http://example.com/po.xsd"));
    namespaces.add(new Namespace("xml", "http://www.w3.org/XML/1998/namespace"));
  }

  public void testUnmarshall() {
    Iterator aliases = WsdlUtil.getExtensions(definition.getExtensibilityElements(), WsdlConstants.Q_PROPERTY_ALIAS);

    // customer ID alias    
    PropertyAlias alias = (PropertyAlias) aliases.next();
    assertEquals(new QName(NS_COR, "customerID"), alias.getProperty().getQName());
    assertEquals(new QName(NS_PUR, "POMessage"), alias.getMessage().getQName());
    assertEquals("purchaseOrder", alias.getPart());
    // query
    Query query = alias.getQuery();
    assertEquals("CID", query.getText());
    assertEquals(namespaces, query.getNamespaces());

    // vendor ID alias
    alias = (PropertyAlias) aliases.next();
    assertEquals(new QName(NS_COR, "vendorID"), alias.getProperty().getQName());
    assertEquals(new QName(NS_PUR, "InvMessage"), alias.getMessage().getQName());
    assertEquals("IVC", alias.getPart());
    // query
    query = alias.getQuery();
    assertEquals("http://www.w3.org/TR/1999/REC-xpath-19991116", query.getLanguage());
    assertEquals("po:Invoice/VID", query.getText());
    assertEquals(namespaces, query.getNamespaces());
    
    // order number alias
    alias = (PropertyAlias) aliases.next();
    assertEquals(new QName(NS_COR, "orderNumber"), alias.getProperty().getQName());
    assertEquals(new QName(NS_PUR, "POMessage"), alias.getMessage().getQName());
    assertEquals("purchaseOrder", alias.getPart());
    assertNull(alias.getQuery());
  }

  public void testMarshall() throws Exception {
    definition = WsdlUtil.writeAndRead(definition);
    // test things work the same
    testUnmarshall();
  }
}