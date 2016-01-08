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

import java.io.PrintWriter;
import java.io.Serializable;

import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionDeserializer;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import com.ibm.wsdl.Constants;
import com.ibm.wsdl.util.xml.DOMUtils;
import com.ibm.wsdl.util.xml.XPathUtils;

import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.action.Script;

/**
 * Translates between <code>&lt;propertyAlias/&gt;</code> XML elements and
 * {@link PropertyAlias} instances.
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class PropertyAliasSerializer implements ExtensionSerializer,
    ExtensionDeserializer, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Deserializes a DOM element into a {@link PropertyAlias} instance.
   * @param parentType class object indicating where in the WSDL document this
   *          extensibility element was encountered
   * @param elementType the qname of the extensibility element
   * @param elem the extensibility element to deserialize.
   * @param def the definition this extensibility element was encountered in.
   * @param extReg the ExtensionRegistry to use (if needed again).
   * @return the deserialized instance.
   * @throws WSDLException if deserialization fails.
   */
  public ExtensibilityElement unmarshall(Class parentType, QName elementType,
      Element elem, Definition def, ExtensionRegistry extReg)
      throws WSDLException {
    /*
     * XXX wsdl4j 1.4 doesn't register namespaces declared in subelements of
     * wsdl:definitions to the Definition object, and the wsdl converter pushes
     * top-level bpel/plt declarations down to the bpel/plt extension elements
     */
    def.addNamespace(elem.getPrefix(), elem.getNamespaceURI());

    PropertyAlias alias = (PropertyAlias) extReg.createExtension(parentType,
        elementType);

    // property attribute - required
    String prefixedName = elem.getAttribute(WsdlConstants.ATTR_PROPERTY_NAME);
    QName qualifiedName = XmlUtil.getQName(prefixedName, elem);
    Property property = WsdlUtil.getProperty(def, qualifiedName);
    if (property == null) {
      property = (Property) extReg.createExtension(Definition.class,
          WsdlConstants.Q_PROPERTY);
      property.setQName(qualifiedName);
      def.addExtensibilityElement(property);
    }
    alias.setProperty(property);

    // messageType attribute
    prefixedName = XmlUtil.getAttribute(elem, WsdlConstants.ATTR_MESSAGE_TYPE);
    if (prefixedName != null) {
      qualifiedName = XmlUtil.getQName(prefixedName, elem);
      Message message = WsdlUtil.getMessage(def, qualifiedName);
      if (message == null) {
        message = def.createMessage();
        message.setQName(qualifiedName);
        def.addMessage(message);
      }
      alias.setMessage(message);
      // if the messageType attribute is present, the part attribute is also
      // required
      String part = XmlUtil.getAttribute(elem, WsdlConstants.ATTR_PART);
      if (part == null) {
        WSDLException e = new WSDLException(WSDLException.INVALID_WSDL,
            "part attribute missing");
        e.setLocation(XPathUtils.getXPathExprFromNode(elem));
        throw e;
      }
      alias.setPart(part);
    }
    else {
      // type attribute
      prefixedName = XmlUtil.getAttribute(elem, WsdlConstants.ATTR_TYPE);
      if (prefixedName != null) {
        qualifiedName = XmlUtil.getQName(prefixedName, elem);
        alias.setType(qualifiedName);
      }
      else {
        // element attribute
        prefixedName = XmlUtil.getAttribute(elem, WsdlConstants.ATTR_ELEMENT);
        if (prefixedName != null) {
          qualifiedName = XmlUtil.getQName(prefixedName, elem);
          alias.setElement(qualifiedName);
        }
      }
    }

    // wsdl:required attribute
    String required = DOMUtils.getAttributeNS(elem,
        Constants.NS_URI_WSDL,
        Constants.ATTR_REQUIRED);
    if (required != null) {
      alias.setRequired(DatatypeUtil.parseBoolean(required));
    }

    // query element
    Element queryElem = XmlUtil.getElement(elem,
        BpelConstants.NS_BPWS,
        WsdlConstants.ELEM_QUERY);
    if (queryElem != null) {
      alias.setQuery(unmarshallQuery(queryElem));
    }

    return alias;
  }

  /**
   * Serializes a {@link PropertyAlias} instance into the given
   * {@link PrintWriter}.
   * @param parentType class object indicating where in the WSDL document this
   *          extensibility element was encountered
   * @param elementType the qname of the extensibility element
   * @param extension the instance to serialize
   * @param pw the stream to write in
   * @param def the definition this extensibility element was encountered in
   * @param extReg the extension registry to use (if needed again)
   * @throws WSDLException if serialization fails
   */
  public void marshall(Class parentType, QName elementType,
      ExtensibilityElement extension, PrintWriter pw, Definition def,
      ExtensionRegistry extReg) throws WSDLException {
    if (extension == null) {
      return;
    }
    PropertyAlias alias = (PropertyAlias) extension;
    // open tag
    String tagName = DOMUtils.getQualifiedValue(BpelConstants.NS_BPWS,
        WsdlConstants.ELEM_PROPERTY_ALIAS,
        def);
    pw.print("  <" + tagName);
    // property attribute
    DOMUtils.printQualifiedAttribute(WsdlConstants.ATTR_PROPERTY_NAME,
        alias.getProperty().getQName(),
        def,
        pw);
    // message type attribute
    Message message = alias.getMessage();
    if (message != null) {
      DOMUtils.printQualifiedAttribute(WsdlConstants.ATTR_MESSAGE_TYPE,
          message.getQName(),
          def,
          pw);
      DOMUtils.printAttribute(WsdlConstants.ATTR_PART, alias.getPart(), pw);
    }
    else {
      // type attribute
      QName type = alias.getType();
      if (type != null) {
        DOMUtils.printQualifiedAttribute(WsdlConstants.ATTR_TYPE, type, def, pw);
      }
      else {
        // element attribute
        QName element = alias.getElement();
        if (element != null) {
          DOMUtils.printQualifiedAttribute(WsdlConstants.ATTR_ELEMENT,
              element,
              def,
              pw);
        }
      }
    }
    // wsdl:required attribute
    Boolean required = alias.getRequired();
    if (required != null) {
      DOMUtils.printQualifiedAttribute(Constants.Q_ATTR_REQUIRED,
          required.toString(),
          def,
          pw);
    }
    Query query = alias.getQuery();
    if (query != null) {
      pw.println('>');
      // query element
      marshallQuery(alias.getQuery(), pw, def);
      // close tag
      pw.println("  </" + tagName + '>');
    }
    else {
      pw.println("/>");
    }
  }

  /**
   * Deserializes a DOM element into a {@link Script} instance.
   * @param queryElem the element to deserialize
   * @return the deserialized instance
   */
  protected Query unmarshallQuery(Element queryElem) {
    Query query = new Query();
    // language attribute
    query.setLanguage(XmlUtil.getAttribute(queryElem,
        WsdlConstants.ATTR_QUERY_LANGUAGE));
    // text
    query.setText(DOMUtils.getChildCharacterData(queryElem));
    // namespace declarations
    query.setNamespaces(XmlUtil.getNamespaces(queryElem));
    return query;
  }

  /**
   * Serializes a {@link Query} instance into the given {@link PrintWriter}.
   * @param query the instance to serialize
   * @param pw the stream to write in
   * @param def the definition where the extensibility element appears
   * @throws WSDLException if serialization fails
   */
  protected void marshallQuery(Query query, PrintWriter pw, Definition def)
      throws WSDLException {
    // open tag
    String queryTag = DOMUtils.getQualifiedValue(BpelConstants.NS_BPWS,
        WsdlConstants.ELEM_QUERY,
        def);
    pw.print("    <" + queryTag);
    // language attribute
    DOMUtils.printAttribute(WsdlConstants.ATTR_QUERY_LANGUAGE,
        query.getLanguage(),
        pw);
    pw.print('>');
    // code cdata
    pw.print(DOMUtils.cleanString(query.getText()));
    // close tag
    pw.println("</" + queryTag + '>');
  }
}
