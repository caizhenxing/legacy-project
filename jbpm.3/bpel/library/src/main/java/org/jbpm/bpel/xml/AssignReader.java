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

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ibm.wsdl.util.xml.DOMUtils;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Assign;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.Assign.From;
import org.jbpm.bpel.def.Assign.To;
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
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.def.VariableType;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.jpdl.xml.Problem;

/**
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class AssignReader extends ActivityReader {

  public Activity createActivity() {
    return new Assign();
  }

  public void readSpecificProperties(Element activityElem, Activity activity) {
    Assign assign = (Assign) activity;

    NodeList copyList = activityElem.getElementsByTagNameNS(BpelConstants.NS_BPWS,
        BpelConstants.ELEM_COPY);
    int copyCount = copyList.getLength();

    List copies = new ArrayList(copyCount);

    for (int i = 0; i < copyCount; i++) {
      Element copyElem = (Element) copyList.item(i);
      CompositeActivity parent = assign.getCompositeActivity();
      // from-spec
      Element fromElem = XmlUtil.getElement(copyElem,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_FROM);
      Assign.From from = readFrom(fromElem, parent);
      // to-spec
      Element toElem = XmlUtil.getElement(copyElem,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_TO);
      Assign.To to = readTo(toElem, parent);
      // copy
      Assign.Copy copy = new Assign.Copy();
      copy.setFrom(from);
      copy.setTo(to);
      copies.add(copy);
    }

    assign.setCopies(copies);
  }

  public From readFrom(Element fromElem, CompositeActivity parent) {
    From from;
    if (fromElem.hasAttribute(BpelConstants.ATTR_VARIABLE)) {
      if (fromElem.hasAttribute(BpelConstants.ATTR_PROPERTY)) {
        from = readFromProperty(fromElem, parent);
      }
      else {
        from = readFromVariable(fromElem, parent);
      }
    }
    else if (fromElem.hasAttribute(BpelConstants.ATTR_PARTNER_LINK)) {
      from = readFromPartnerLink(fromElem, parent);
    }
    else if (XmlUtil.getElement(fromElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_LITERAL) != null) {
      from = readFromLiteral(fromElem);
    }
    else {
      from = readFromExpression(fromElem, parent);
    }
    return from;
  }

  public To readTo(Element toElem, CompositeActivity parent) {
    To to = null;
    if (toElem.hasAttribute(BpelConstants.ATTR_VARIABLE)) {
      if (toElem.hasAttribute(BpelConstants.ATTR_PROPERTY)) {
        to = readToProperty(toElem, parent);
      }
      else {
        to = readToVariable(toElem, parent);
      }
    }
    else if (toElem.hasAttribute(BpelConstants.ATTR_PARTNER_LINK)) {
      to = readToPartnerLink(toElem, parent);
    }
    else {
      to = readToExpression(toElem, parent);
    }
    return to;
  }

  protected From readFromVariable(Element fromElem, CompositeActivity parent) {
    // variable
    VariableDefinition variable = readVariable(fromElem, parent);
    // from
    FromVariable from = new FromVariable();
    from.setVariable(variable);
    from.setPart(readPart(fromElem, variable));
    from.setQuery(readQuery(fromElem, parent));
    return from;
  }

  protected From readFromProperty(Element fromElem, CompositeActivity parent) {
    FromProperty from = new FromProperty();
    from.setVariable(readVariable(fromElem, parent));
    from.setProperty(readProperty(parent, fromElem));
    return from;
  }

  protected From readFromPartnerLink(Element fromElem, CompositeActivity parent) {
    // endpoint reference
    String referenceValue = fromElem.getAttribute(BpelConstants.ATTR_ENDPOINT_REFERENCE);
    Reference reference = Reference.valueOf(referenceValue);
    // from
    FromPartnerLink from = new FromPartnerLink();
    from.setPartnerLink(readPartnerLink(fromElem, parent));
    from.setEndpointReference(reference);
    return from;
  }

  protected From readFromLiteral(Element fromElem) {
    Element literalElem = XmlUtil.getElement(fromElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_LITERAL);
    Element firstChildElement = XmlUtil.getElement(literalElem);
    if (firstChildElement == null) {
      // no child elements, take the text
      String childText = XmlUtil.getStringValue(literalElem);
      FromText from = new FromText();
      from.setLiteral(childText);
      return from;
    }
    else if (DOMUtils.getNextSiblingElement(firstChildElement) == null) {
      // only one child element, take it
      FromElement from = new FromElement();
      from.setLiteral((Element) firstChildElement);
      return from;
    }
    bpelReader.getProblemHandler().add(new ParseProblem(
        "more than one child element present", fromElem));
    return null;
  }

  protected From readFromExpression(Element fromElem, CompositeActivity parent) {
    FromExpression from = new FromExpression();
    from.setExpression(bpelReader.readExpression(fromElem, parent));
    return from;
  }

  protected To readToVariable(Element toElem, CompositeActivity parent) {
    // variable
    VariableDefinition variable = readVariable(toElem, parent);
    // from
    ToVariable to = new ToVariable();
    to.setVariable(variable);
    to.setPart(readPart(toElem, variable));
    to.setQuery(readQuery(toElem, parent));
    return to;
  }

  protected To readToProperty(Element toElem, CompositeActivity parent) {
    ToProperty to = new ToProperty();
    to.setVariable(readVariable(toElem, parent));
    to.setProperty(readProperty(parent, toElem));
    return to;
  }

  protected To readToPartnerLink(Element toElem, CompositeActivity parent) {
    ToPartnerLink to = new ToPartnerLink();
    to.setPartnerLink(readPartnerLink(toElem, parent));
    return to;
  }

  protected To readToExpression(Element toElem, CompositeActivity parent) {
    ToExpression to = new ToExpression();
    to.setExpression(bpelReader.readExpression(toElem, parent));
    return to;
  }

  protected VariableDefinition readVariable(Element contextElem,
      CompositeActivity parent) {
    String variableName = XmlUtil.getAttribute(contextElem,
        BpelConstants.ATTR_VARIABLE);
    VariableDefinition variable = parent.findVariable(variableName);
    if (variable == null) {
      bpelReader.getProblemHandler().add(new ParseProblem("variable not found",
          contextElem));
    }
    return variable;
  }

  protected String readPart(Element contextElem, VariableDefinition variable) {
    String partName = XmlUtil.getAttribute(contextElem, BpelConstants.ATTR_PART);
    if (partName != null && variable != null) {
      VariableType type = variable.getType();
      // prevent access to a non-existent part
      if (type instanceof MessageType) {
        MessageType messageType = (MessageType) type;
        if (!messageType.getMessage().getParts().containsKey(partName)) {
          bpelReader.getProblemHandler().add(new ParseProblem("part not found",
              contextElem));
        }
      }
      else {
        bpelReader.getProblemHandler().add(new ParseProblem(
            "non-message variable has no parts", contextElem));
      }
    }
    return partName;
  }

  protected Query readQuery(Element contextElem, CompositeActivity parent) {
    String queryString = XmlUtil.getAttribute(contextElem,
        BpelConstants.ATTR_QUERY);
    // easy way out: no query string
    if (queryString == null) return null;
    // configure and parse query
    Query query = new Query();
    query.setText(queryString);
    query.setNamespaces(parent.getBpelDefinition()
        .addNamespaces(XmlUtil.getNamespaces(contextElem)));
    try {
      query.parse();
    }
    catch (BpelException e) {
      bpelReader.getProblemHandler().add(new ParseProblem(Problem.LEVEL_ERROR,
          "could not parse script", e, contextElem));
    }
    return query;
  }

  protected Property readProperty(CompositeActivity parent, Element contextElem) {
    String propertyName = XmlUtil.getAttribute(contextElem,
        BpelConstants.ATTR_PROPERTY);
    QName propertyQName = XmlUtil.getQName(propertyName, contextElem);
    Property property = parent.getBpelDefinition()
        .getImports()
        .getProperty(propertyQName);
    if (property == null) {
      bpelReader.getProblemHandler().add(new ParseProblem("property not found",
          contextElem));
    }
    return property;
  }

  protected PartnerLinkDefinition readPartnerLink(Element contextElem,
      CompositeActivity parent) {
    String partnerLinkName = XmlUtil.getAttribute(contextElem,
        BpelConstants.ATTR_PARTNER_LINK);
    PartnerLinkDefinition partnerLink = parent.findPartnerLink(partnerLinkName);
    if (partnerLink == null) {
      bpelReader.getProblemHandler().add(new ParseProblem(
          "partner link not found", contextElem));
    }
    return partnerLink;
  }
}
