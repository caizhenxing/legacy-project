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

import javax.wsdl.Definition;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.wsdl.PartnerLinkType.Role;
import org.jbpm.bpel.wsdl.util.WsdlUtil;

/**
 * @author Juan Cantu
 * @version $Revision: 1.14 $ $Date: 2006/08/22 04:13:10 $
 */
public class PartnerLinkReaderTest extends AbstractReaderTestCase {
  BpelReader reader;
  BpelDefinition pd;
  PortType partnerPortType;
  PortType myPortType;
  
  private static final String WSDL_TEXT = 
    "<definitions targetNamespace='http://manufacturing.org/wsdl/purchase'" +
    " xmlns:plnk='http://schemas.xmlsoap.org/ws/2004/03/partner-link/'" +
    " xmlns:bpws='http://schemas.xmlsoap.org/ws/2004/03/business-process/'" +
    " xmlns:xsd='http://www.w3.org/2001/XMLSchema'" +
    " xmlns:tns='http://manufacturing.org/wsdl/purchase'" +
    " xmlns='http://schemas.xmlsoap.org/wsdl/'>" +
    " <message name='aQName'/>" +
    " <portType name='ppt'>" +
    "  <operation name='o'>" +
    "   <input message='tns:aQName'/>" +
    "  </operation>" +
    " </portType>" +
    " <portType name='mpt'>" +
    "  <operation name='o'>" +
    "   <input message='tns:aQName'/>" +
    "  </operation>" +
    " </portType>" +
    " <plnk:partnerLinkType name='schedulingLT'>" +
    "  <plnk:role name='schedulingRequester' portType='tns:ppt'/>" +
    "  <plnk:role name='schedulingService' portType='tns:mpt'/>" +
    " </plnk:partnerLinkType>" +
    " <bpws:property name='p1' type='xsd:string'/>" +
    " <bpws:property name='p2' type='xsd:string'/>" +
    " <bpws:property name='p3' type='xsd:string'/>" +
    "</definitions>";
  static final String NS_TNS = "http://manufacturing.org/wsdl/purchase";
  
  public void setUp() throws Exception {
    reader = BpelReader.getInstance();
    pd = new BpelDefinition();
    // read wsdl
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    // port types
    partnerPortType = def.getPortType(new QName(NS_TNS, "ppt"));
    myPortType = def.getPortType(new QName(NS_TNS, "mpt"));
    // set up imports
    ImportsDefinition imports = pd.getImports();
    imports.addImport(WsdlUtil.createImport(def));
    
  }
  
  public void testMyRole() throws Exception {
    String xml = 
      "<partnerLinks xmlns:tns='http://manufacturing.org/wsdl/purchase'>" +
      "   <partnerLink name='pl' partnerLinkType='tns:schedulingLT' " +
      "    myRole='schedulingService' partnerRole='schedulingRequester'/>" +    
      " </partnerLinks>";
    PartnerLinkDefinition pl = parsePartnerLink( xml );
    Role myRole = pl.getMyRole();
    assertNotNull(myRole);
    assertEquals("schedulingService", myRole.getName());
    assertSame( myPortType, myRole.getPortType() );
  }

  public void testPartnerRole() throws Exception {
    String xml = 
      "<partnerLinks xmlns:tns='http://manufacturing.org/wsdl/purchase'>" +
      "   <partnerLink name='pl' partnerLinkType='tns:schedulingLT' " +
      "    myRole='schedulingService' partnerRole='schedulingRequester'/>" +    
      " </partnerLinks>";
    PartnerLinkDefinition pl = parsePartnerLink( xml );
    Role partnerRole = pl.getPartnerRole();
    assertNotNull(partnerRole);
    assertEquals("schedulingRequester", partnerRole.getName());
    assertSame(partnerPortType, partnerRole.getPortType());
  }
  
  private PartnerLinkDefinition parsePartnerLink(String xml) throws Exception {
    Element element = parseAsBpelElement(xml);
    return (PartnerLinkDefinition) reader.readPartnerLinks(element, pd.getGlobalScope()).get("pl"); 
  }
}
