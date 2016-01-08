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
package org.jbpm.bpel.def;

import java.io.Serializable;
import java.util.Collections;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import com.ibm.wsdl.util.xml.DOMUtils;

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.def.Assign.Copy;
import org.jbpm.bpel.def.assign.FromElement;
import org.jbpm.bpel.def.assign.FromExpression;
import org.jbpm.bpel.def.assign.FromPartnerLink;
import org.jbpm.bpel.def.assign.FromProperty;
import org.jbpm.bpel.def.assign.FromText;
import org.jbpm.bpel.def.assign.FromVariable;
import org.jbpm.bpel.def.assign.ToExpression;
import org.jbpm.bpel.def.assign.ToPartnerLink;
import org.jbpm.bpel.def.assign.ToProperty;
import org.jbpm.bpel.def.assign.ToVariable;
import org.jbpm.bpel.def.assign.FromPartnerLink.Reference;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.wsdl.impl.PropertyImpl;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Juan Cantu
 * @version $Revision: 1.21 $ $Date: 2006/08/22 04:13:10 $
 */
public class AssignDbTest extends AbstractDbTestCase {

  private Assign assign; 
  
  private static final String ACTIVITY_NAME = "assign";
  
  public void setUp() throws Exception {
    super.setUp();
    // assign
    assign = new Assign(ACTIVITY_NAME);
    assign.setCopies(Collections.singletonList(new Copy()));
    // process, create after opening the jbpm context
    new BpelDefinition("process").getGlobalScope().setRoot(assign);
  }
  
  public void testFromVariable() {
    // prepare persistent objects
    // variable
    VariableDefinition variable = new VariableDefinition();
    variable.setName("RFQ");
    assign.getBpelDefinition().getGlobalScope().addVariable(variable);
    // query
    Query query = new Query();
    query.setText("/description");
    // from
    FromVariable from = new FromVariable();
    from.setVariable(variable);
    from.setPart("item");
    from.setQuery(query);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setFrom(from);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable fromId = session.getIdentifier(copy.getFrom());
    from = (FromVariable) session.load(FromVariable.class, fromId);
    
    // verify the retrieved objects
    // variable
    assertEquals(variable.getName(), from.getVariable().getName());
    // part
    assertEquals("item", from.getPart()); 
    // query
    assertEquals("/description", from.getQuery().getText());
  }  
  
  public void testFromProperty() throws Exception {
    // prepare persistent objects
    BpelDefinition process = assign.getBpelDefinition();
    // variable
    VariableDefinition variable = new VariableDefinition();
    variable.setName("RFQ");
    process.getGlobalScope().addVariable(variable);
    // property
    Property property = new PropertyImpl();
    property.setQName(new QName("providerId"));
    process.getImports().addProperty(property);
    // from
    FromProperty from = new FromProperty();
    from.setVariable(variable);
    from.setProperty(property);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setFrom(from);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable fromId = session.getIdentifier(copy.getFrom());
    from = (FromProperty) session.load(FromProperty.class, fromId);
    
    // verify the retrieved objects
    // variable
    assertEquals(variable.getName(), from.getVariable().getName());
    // property
    assertEquals(property.getQName(), from.getProperty().getQName());
  }

  public void testFromPartnerLink() throws Exception {
    // prepare persistent objects
    // partner link
    PartnerLinkDefinition partnerLink = new PartnerLinkDefinition();
    partnerLink.setName("flightScheduleService");
    assign.getBpelDefinition().getGlobalScope().addPartnerLink(partnerLink);
    // from
    FromPartnerLink from = new FromPartnerLink();
    from.setPartnerLink(partnerLink);
    from.setEndpointReference(Reference.PARTNER_ROLE);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setFrom(from);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable fromId = session.getIdentifier(copy.getFrom());
    from = (FromPartnerLink) session.load(FromPartnerLink.class, fromId);
    
    // verify the retrieved objects
    assertEquals("flightScheduleService", from.getPartnerLink().getName());
    assertEquals(Reference.PARTNER_ROLE, from.getEndpointReference());
  }

  public void testFromExpression() {
    // prepare persistent objects
    // expression
    Expression expression = new Expression();
    expression.setText("$rock");
    // from
    FromExpression from = new FromExpression();
    from.setExpression(expression);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setFrom(from);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable fromId = session.getIdentifier(copy.getFrom());
    from = (FromExpression) session.load(FromExpression.class, fromId);
    
    // verify the retrieved objects
    assertEquals("$rock", from.getExpression().getText());
  }  
  
  public void testFromLiteral_element() throws Exception {
    // prepare persistent objects
    // literal
    Element literal = XmlUtil.parseElement(
        "\n<types:operationFault xmlns:types='urn:samples:atm:types' xmlns=''>" +
        "\n  <code>100</code>" +
        "\n  <description>not enough funds</description>" +
        "\n</types:operationFault>");
    // from
    FromElement from = new FromElement();
    from.setLiteral(literal);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setFrom(from);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable fromId = session.getIdentifier(copy.getFrom());
    from = (FromElement) session.load(FromElement.class, fromId);
    
    // verify the retrieved objects
    literal = from.getLiteral();
    assertEquals("urn:samples:atm:types", literal.getNamespaceURI());
    assertEquals("operationFault", literal.getLocalName());
    
    Element codeElem = XmlUtil.getElement(literal);
    assertNull(codeElem.getNamespaceURI());
    assertEquals("code", codeElem.getLocalName());
    assertEquals("100", XmlUtil.getStringValue(codeElem));
    
    Element descElem = DOMUtils.getNextSiblingElement(codeElem);
    assertNull(descElem.getNamespaceURI());
    assertEquals("description", descElem.getLocalName());
    assertEquals("not enough funds", XmlUtil.getStringValue(descElem));
  }
  
  public void testFromLiteral_text() {
    // prepare persistent objects
    // literal
    String literal = "<<hello, friends & foes>>";
    // from
    FromText from = new FromText();
    from.setLiteral(literal);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setFrom(from);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable fromId = session.getIdentifier(copy.getFrom());
    from = (FromText) session.load(FromText.class, fromId);
    
    // verify the retrieved objects
    assertEquals(literal, from.getLiteral());
  }
  
  public void testToVariable() {
    // prepare persistent objects
    // variable
    VariableDefinition variable = new VariableDefinition();
    variable.setName("RFQ");
    assign.getBpelDefinition().getGlobalScope().addVariable(variable);
    // query
    Query query = new Query();
    query.setText("/description");
    // to
    ToVariable to = new ToVariable();
    to.setVariable(variable);
    to.setPart("item");
    to.setQuery(query);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setTo(to);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable toId = session.getIdentifier(copy.getTo());
    to = (ToVariable) session.load(ToVariable.class, toId);
    
    // verify the retrieved objects
    // variable
    assertEquals(variable.getName(), to.getVariable().getName());
    // part
    assertEquals("item", to.getPart()); 
    // query
    assertEquals("/description", to.getQuery().getText());
  }  
  
  public void testToProperty() throws Exception {
    // prepare persistent objects
    BpelDefinition process = assign.getBpelDefinition();
    // variable
    VariableDefinition variable = new VariableDefinition();
    variable.setName("RFQ");
    process.getGlobalScope().addVariable(variable);
    // property
    Property property = new PropertyImpl();
    property.setQName(new QName("providerId"));
    process.getImports().addProperty(property);
    // to
    ToProperty to = new ToProperty();
    to.setVariable(variable);
    to.setProperty(property);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setTo(to);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable toId = session.getIdentifier(copy.getTo());
    to = (ToProperty) session.load(ToProperty.class, toId);
    
    // verify the retrieved objects
    // variable
    assertEquals(variable.getName(), to.getVariable().getName());
    // property
    assertEquals(property.getQName(), to.getProperty().getQName());
  }

  public void testToPartnerLink() throws Exception {
    // prepare persistent objects
    // partner link
    PartnerLinkDefinition partnerLink = new PartnerLinkDefinition();
    partnerLink.setName("flightScheduleService");
    assign.getBpelDefinition().getGlobalScope().addPartnerLink(partnerLink);
    // to
    ToPartnerLink to = new ToPartnerLink();
    to.setPartnerLink(partnerLink);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setTo(to);
    
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable toId = session.getIdentifier(copy.getTo());
    to = (ToPartnerLink) session.load(ToPartnerLink.class, toId);
    
    assertEquals("flightScheduleService", to.getPartnerLink().getName());
  }
  
  public void testToExpression() {
    // prepare persistent objects
    // query
    Expression expression = new Expression();
    expression.setText("$rock");
    // to
    ToExpression to = new ToExpression();
    to.setExpression(expression);
    // copy
    Copy copy = (Copy) assign.getCopies().get(0);
    copy.setTo(to);
    
    // save objects and load them back
    saveAndReload();
    copy = (Copy) assign.getCopies().get(0);
    Serializable toId = session.getIdentifier(copy.getTo());
    to = (ToExpression) session.load(ToExpression.class, toId);
    
    assertEquals("$rock", to.getExpression().getText());
  }

  private void saveAndReload() {
    BpelDefinition process = saveAndReload(assign.getBpelDefinition());
    assign = (Assign) session.load(Assign.class, new Long(process.getGlobalScope().getRoot().getId()));
  }
}
