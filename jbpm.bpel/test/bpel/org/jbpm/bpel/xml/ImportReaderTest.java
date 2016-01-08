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

import java.util.Map;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.wsdl.PropertyAlias;

/**
 * @author Juan Cantu
 * @version $Revision: 1.14 $ $Date: 2006/08/22 04:13:10 $
 */
public class ImportReaderTest extends AbstractReaderTestCase {
  
  static final String NS_TNS = "http://manufacturing.org/wsdl/purchase";
  
  public void testImportPartnerLinkType() throws Exception {
    String xml = 
      "<process>" +
      " <import namespace='http://manufacturing.org/wsdl/purchase' location='partnerLinkTypeSample.wsdl'" +
      " importType='http://schemas.xmlsoap.org/wsdl/'/>" +
      " <receive partnerLink='aPartner' operation='o'/>" +
      "</process>";
    readImports( xml );
    assertNotNull(pd.getImports().getPartnerLinkType(new QName(NS_TNS, "schedulingLT")));
  }
  
  public void testImportPortType() throws Exception {
    String xml = 
      "<process>" +
      " <import namespace='http://manufacturing.org/wsdl/purchase' location='partnerLinkTypeSample.wsdl'" +
      " importType='http://schemas.xmlsoap.org/wsdl/'/>" +
      " <receive partnerLink='aPartner' operation='o'/>" +
      "</process>";
    readImports( xml );
    assertNotNull(pd.getImports().getPortType(new QName(NS_TNS, "schedulingCallbackPT")));
  }
  
  public void testImportMessage() throws Exception {
    String xml = 
      "<process>" +
      " <import namespace='http://manufacturing.org/wsdl/purchase' location='partnerLinkTypeSample.wsdl'" +
      " importType='http://schemas.xmlsoap.org/wsdl/'/>" +
      " <receive partnerLink='aPartner' operation='o'/>" +
      "</process>";
    readImports( xml );
    assertNotNull(pd.getImports().getMessageType(new QName(NS_TNS, "scheduleMessage")));
  }
  
  public void testImportProperty() throws Exception {
    String xml = 
      "<process>" +
      " <import namespace='http://manufacturing.org/wsdl/purchase' location='propertyAliasSample.wsdl'" +
      " importType='http://schemas.xmlsoap.org/wsdl/'/>" +
      " <receive partnerLink='aPartner' operation='o'/>" +
      "</process>";
    readImports( xml );
    assertNotNull(pd.getImports().getProperty(new QName(NS_TNS, "orderNumber")));
  }
  
  public void testImportPropertyAlias() throws Exception {
    String xml = 
      "<process>" +
      " <import namespace='http://manufacturing.org/wsdl/purchase' location='propertyAliasSample.wsdl'" +
      " importType='http://schemas.xmlsoap.org/wsdl/'/>" +
      " <receive partnerLink='aPartner' operation='o'/>" +
      "</process>";
    readImports( xml );
    MessageType messageType = pd.getImports().getMessageType(new QName(NS_TNS, "POMessage"));
    Map propertyAliases = messageType.getPropertyAliases();
    assertEquals(2, propertyAliases.size());
    // alias for orderNumber property
    QName propertyName = new QName(NS_TNS, "orderNumber");
    PropertyAlias alias = (PropertyAlias) propertyAliases.get(propertyName);
    assertEquals(propertyName, alias.getProperty().getQName());
    // alias for invoiceId property
    propertyName = new QName(NS_TNS, "invoiceId");
    alias = (PropertyAlias) propertyAliases.get(propertyName);
    assertEquals(propertyName, alias.getProperty().getQName());
  }  
  
  private void readImports(String xml) throws Exception {
    Element bpelElem = parseAsBpelElement(xml);
    ImportsDefinition imports = pd.getImports();
    String classURI = ImportReaderTest.class.getResource("processSample.bpel").toString();
    ImportWsdlLocator locator = new ImportWsdlLocator(classURI);
    reader.readImports(bpelElem, imports, locator);
    BpelReader.getInstance().registerPropertyAliases(imports);
  }  
}
