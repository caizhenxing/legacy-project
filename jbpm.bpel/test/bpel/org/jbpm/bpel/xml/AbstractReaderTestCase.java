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
import javax.wsdl.Operation;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Juan Cantú
 * @version $Revision: 1.19 $ $Date: 2006/08/22 04:13:10 $
 */
public abstract class AbstractReaderTestCase extends TestCase {
  
  protected BpelReader reader;
  protected BpelDefinition pd;
  protected TestScope scope;
  protected PartnerLinkDefinition pLink;
  protected Operation operation;
  protected PortType partnerPortType;
  protected PortType myPortType;
  protected VariableDefinition messageVariable;
  protected Property p1, p2, p3;
  
  private static final String WSDL_TEXT = 
    "<definitions targetNamespace='http://manufacturing.org/wsdl/purchase'" +
    " xmlns:plnk='http://schemas.xmlsoap.org/ws/2004/03/partner-link/'" +
    " xmlns:bpws='http://schemas.xmlsoap.org/ws/2004/03/business-process/'" +
    " xmlns:xsd='http://www.w3.org/2001/XMLSchema'" +
    " xmlns:tns='http://manufacturing.org/wsdl/purchase'" +
    " xmlns='http://schemas.xmlsoap.org/wsdl/'>" +
    " <message name='aQName'>" +
    "  <part name='p' type='xsd:int' />" +
    " </message>" +
    " <portType name='ppt'>" +
    "  <operation name='o'>" +
    "   <input message='tns:aQName'/>" +
    "  </operation>" +
    "  <operation name='o2'>" +
    "   <input message='tns:aQName'/>" +
    "   <output message='tns:aQName'/>" +    
    "  </operation>" +
    " </portType>" +
    " <portType name='mpt'>" +
    "  <operation name='o'>" +
    "   <input message='tns:aQName'/>" +
    "   <output message='tns:aQName'/>" +
    "  </operation>" +
    " </portType>" +
    " <plnk:partnerLinkType name='aPartnerLinkType'>" +
    "  <plnk:role name='role1' portType='tns:ppt'/>" +
    "  <plnk:role name='role2' portType='tns:mpt'/>" +
    " </plnk:partnerLinkType>" +
    " <bpws:property name='p1' type='xsd:string'/>" +
    " <bpws:property name='p2' type='xsd:string'/>" +
    " <bpws:property name='p3' type='xsd:string'/>" +
    "</definitions>";
  static protected final String NS_TNS = "http://manufacturing.org/wsdl/purchase";
   
  public void setUp() throws Exception {
    reader = BpelReader.getInstance();
    pd = new BpelDefinition("testPD");
    pd.getNodes().clear();
    scope  = new TestScope();
    pd.addNode(scope);
  }
  
  protected void initMessageProperties() throws Exception {
    // read wsdl
    Definition def = WsdlUtil.readText(WSDL_TEXT);
    // set up imports
    ImportsDefinition imports = pd.getImports();
    imports.addImport(WsdlUtil.createImport(def));
    // partner link types & port types
    PartnerLinkType plinkType = imports.getPartnerLinkType(new QName(NS_TNS, "aPartnerLinkType"));
    partnerPortType = plinkType.getFirstRole().getPortType();
    myPortType = plinkType.getSecondRole().getPortType();
    operation = partnerPortType.getOperation("o", null, null);
    // message properties
    p1 = imports.getProperty(new QName(NS_TNS, "p1"));
    p2 = imports.getProperty(new QName(NS_TNS, "p2"));
    p3 = imports.getProperty(new QName(NS_TNS, "p3"));
    // message variable
    messageVariable = new VariableDefinition();
    messageVariable.setName("iv");
    messageVariable.setType(imports.getMessageType(new QName(NS_TNS, "aQName")));
    // partner link
    pLink = new PartnerLinkDefinition();
    pLink.setName("aPartner");
    pLink.setPartnerLinkType(plinkType);
    pLink.setMyRole(plinkType.getSecondRole());
    scope.addPartnerLink(pLink);
    scope.addVariable(messageVariable);
  } 
  
  protected Element parseAsBpelElement(String text) throws SAXException {
    String textToParse = 
      "<parent xmlns='"  + BpelConstants.NS_BPWS  + "'>" + text + "</parent>";
    return (Element) XmlUtil.parseElement(textToParse).getChildNodes().item(0);
  }
  
  protected Activity readActivity(String xml) throws SAXException {
    Element element = parseAsBpelElement(xml);
    return readActivity(element, scope);
  }

  protected Activity readActivity(Element element, CompositeActivity parent) {
    ActivityReader activityReader = reader.getActivityReader(element.getLocalName());
    Activity activity = activityReader.read(element, parent);
    return activity;
  }
  
  protected static class TestScope extends BpelDefinition.GlobalScope {

    public boolean initial = false;
    private static final long serialVersionUID = 1L;
    
    public boolean isChildInitial(Activity activity) {
      return initial;
    }
  }
}
