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
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.Text;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.jaxen.dom.DocumentNavigator;
import org.jaxen.function.StringFunction;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.wsdl.util.xml.DOMUtils;

import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class XmlUtil {

  private static ThreadLocal documentBuilderLocal = new ThreadLocal() {

    protected Object initialValue() {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      factory.setValidating(true);
      factory.setCoalescing(true);
      factory.setIgnoringElementContentWhitespace(true);
      factory.setIgnoringComments(true);
      try {
        // Pick XML Schema as the schema language
        factory.setAttribute(JAXP_SCHEMA_LANGUAGE, BpelConstants.NS_XML_SCHEMA);
      }
      catch (IllegalArgumentException e) {
        log.fatal("JAXP implementation does not support XML Schema validation, "
            + "BPEL reader will not work properly",
            e);
        throw new AssertionError(e);
      }
      try {
        // Do full type checking
        factory.setAttribute("http://apache.org/xml/features/validation/schema-full-checking",
            Boolean.TRUE);
        // Only do schema validation if a schema is specified as a namespace
        factory.setAttribute("http://apache.org/xml/features/validation/dynamic",
            Boolean.TRUE);
      }
      catch (IllegalArgumentException e) {
        log.warn("JAXP implementation is not Xerces, cannot enable dynamic schema validation, "
            + "XML documents without schema location will not parse.");
      }
      try {
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        documentBuilder.setEntityResolver(new LocalEntityResolver());
        return documentBuilder;
      }
      catch (ParserConfigurationException e) {
        throw new RuntimeException("could not create document builder", e);
      }
    }
  };

  private static ThreadLocal transformerFactoryLocal = new ThreadLocal() {

    protected Object initialValue() {
      return TransformerFactory.newInstance();
    }
  };

  private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  private static final Log log = LogFactory.getLog(XmlUtil.class);

  private XmlUtil() {
    // Suppress default constructor, ensuring non-instantiability
  }

  /**
   * Gets the first child element of the given node with the specified local
   * name and a <code>null</code> or empty namespace URI.
   * @param parent the parent node to examine
   * @param localName the local name of the desired child element
   * @return the corresponding child element, or <code>null</code> if there is
   *         no match
   */
  public static Element getElement(Node parent, String localName) {
    return getElement(parent, null, localName);
  }

  /**
   * Gets the first child element of the given SOAP element with the specified
   * local name and a <code>null</code> or empty namespace URI. This overload
   * is necessary as method {@link Node#getNextSibling()} does not behave as
   * expected under some SAAJ implementations.
   * @param parent the parent SOAP element to examine
   * @param localName the local name of the desired child element
   * @return the corresponding child element, or <code>null</code> if there is
   *         no match
   */
  public static SOAPElement getElement(SOAPElement parent, String localName) {
    return getElement(parent, null, localName);
  }

  /**
   * Gets the first child element of the given node with the specified namespace
   * URI and local name.
   * @param parent the parent node to examine
   * @param namespaceURI the namespace URI of the desired child element; if
   *          <code>null</code>, only elements with a <code>null</code> or
   *          empty namespace URI will be considered
   * @param localName the local name of the desired child element
   * @return the corresponding child element, or <code>null</code> if there is
   *         no match
   */
  public static Element getElement(Node parent, String namespaceURI,
      String localName) {
    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (QNameElementPredicate.evaluate(child, namespaceURI, localName)) {
        return (Element) child;
      }
    }
    return null;
  }

  /**
   * Gets the first child element of the given SOAP element with the specified
   * namespace URI and local name. This overload is necessary as method
   * {@link Node#getNextSibling()} does not behave as expected under some SAAJ
   * implementations.
   * @param parent the parent node to examine
   * @param namespaceURI the namespace URI of the desired child element; if
   *          <code>null</code>, only elements with a <code>null</code> or
   *          empty namespace URI will be considered
   * @param localName the local name of the desired child element
   * @return the corresponding child element, or <code>null</code> if there is
   *         no match
   */
  public static SOAPElement getElement(SOAPElement parent, String namespaceURI,
      String localName) {
    Iterator childIt = parent.getChildElements();
    while (childIt.hasNext()) {
      javax.xml.soap.Node child = (javax.xml.soap.Node) childIt.next();
      if (QNameElementPredicate.evaluate(child, namespaceURI, localName)) {
        return (SOAPElement) child;
      }
    }
    return null;
  }

  /**
   * Gets the first child element of the given node.
   * @param parent the parent node to examine
   * @return the corresponding child element, or <code>null</code> if there is
   *         no match
   */
  public static Element getElement(Node parent) {
    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        return (Element) child;
      }
    }
    return null;
  }

  /**
   * Gets the first child element of the given SOAP element.
   * @param parent the parent SOAP element to examine
   * @return the corresponding child element, or <code>null</code> if there is
   *         no match
   */
  public static SOAPElement getElement(SOAPElement parent) {
    Iterator childIt = parent.getChildElements();
    while (childIt.hasNext()) {
      Object child = childIt.next();
      if (child instanceof SOAPElement) {
        return (SOAPElement) child;
      }
    }
    return null;
  }

  /**
   * Gets an iterator over the child elements of the given node with the
   * specified namespace URI and any local name.
   * @param parent the parent node to iterate
   * @param namespaceURI the namespace URI of the desired child elements; if
   *          <code>null</code>, only elements with a <code>null</code> or
   *          empty namespace URI will be iterated
   * @return an {@link Element} iterator, empty if there is no match
   */
  public static Iterator getElements(Node parent, String namespaceURI) {
    return IteratorUtils.filteredIterator(new NodeIterator(parent),
        new NamespaceElementPredicate(namespaceURI));
  }

  /**
   * Gets an iterator over the child elements of the given node with the
   * specified namespace URI and local name.
   * @param parent the parent node to iterate
   * @param namespaceURI the namespace URI of the desired child elements; if
   *          <code>null</code>, only elements with a <code>null</code> or
   *          empty namespace URI will be iterated
   * @param localName the local name of the desired child elements
   * @return an {@link Element} iterator, empty if there is no match
   */
  public static Iterator getElements(Node parent, String namespaceURI,
      String localName) {
    return IteratorUtils.filteredIterator(new NodeIterator(parent),
        new QNameElementPredicate(namespaceURI, localName));
  }

  /**
   * Gets an attribute value of the given element.
   * @param ownerElem the element that owns the attribute
   * @param attrName the name of the attribute to retrieve
   * @return the attribute value as a string, or <code>null</code> if that
   *         attribute does not have a specified value
   */
  public static String getAttribute(Element ownerElem, String attrName) {
    Attr attribute = ownerElem.getAttributeNode(attrName);
    return attribute != null ? attribute.getValue() : null;
  }

  /**
   * Resolves a qualified name from a prefixed name appearing in the context of
   * the given node.
   * @param prefixedName a name that may contain a colon character
   * @param contextNode the node to search for namespace declarations
   * @return a qualified name whose fields are set as follows:
   *         <ul>
   *         <li><i>localPart</i> substring of the name after the first colon</li>
   *         <li><i>prefix</i> substring of the name before the first colon</li>
   *         <li><i>namespaceURI</i> namespace associated with the prefix
   *         above</li>
   *         </ul>
   */
  public static QName getQName(String prefixedName, Node contextNode) {
    String prefix;
    String namespaceURI;
    String localPart;

    int index = prefixedName.indexOf(':');
    if (index == -1) {
      localPart = prefixedName;
      prefix = "";
      namespaceURI = "";
    }
    else {
      localPart = prefixedName.substring(index + 1);
      prefix = prefixedName.substring(0, index);
      namespaceURI = getNamespaceURI(prefix, contextNode);
    }
    return new QName(namespaceURI, localPart, prefix);
  }

  public static String getStringValue(Object value) {
    return DatatypeUtil.toString(value);
  }

  public static void setObjectValue(Node node, Object value) {
    switch (node.getNodeType()) {
    case Node.ELEMENT_NODE:
      setObjectValue((Element) node, value);
      break;
    case Node.DOCUMENT_NODE:
      setObjectValue(((Document) node).getDocumentElement(), value);
      break;
    default:
      // replace content
      node.setNodeValue(getStringValue(value));
    }
  }

  public static void setObjectValue(Element elem, Object value) {
    if (value instanceof Node) {
      if (value instanceof SOAPElement) {
        // replace element
        copy(elem, (SOAPElement) value);
      }
      else if (value instanceof Element) {
        // replace element
        copy(elem, (Element) value);
      }
      else if (value instanceof Document) {
        // replace element
        copy(elem, ((Document) value).getDocumentElement());
      }
      else {
        // replace content
        setStringValue(elem, ((Node) value).getNodeValue());
      }
    }
    else if (value instanceof EndpointReference) {
      // replace element
      ((EndpointReference) value).writeServiceRef(elem);
    }
    else {
      // replace content
      setStringValue(elem, StringFunction.evaluate(value,
          DocumentNavigator.getInstance()));
    }
  }

  public static void setStringValue(Element elem, String value) {
    // remove xsi:nil
    elem.removeAttributeNS(BpelConstants.NS_XML_SCHEMA_INSTANCE,
        BpelConstants.ATTR_NIL);
    // save first child
    Node firstChild = elem.getFirstChild();
    // if first child is text, reuse it
    if (firstChild instanceof org.w3c.dom.Text) {
      firstChild.setNodeValue(value);
    }
    // otherwise, just create new text
    else {
      firstChild = elem.getOwnerDocument().createTextNode(value);
    }
    // remove all children
    removeChildNodes(elem);
    // append text
    elem.appendChild(firstChild);
  }

  static final String QUALIFIED_VALUE_PREFIX = "valueNS";

  public static void setQNameValue(Element elem, QName value) {
    String namespace = value.getNamespaceURI();
    String prefixedValue;
    if (namespace.length() > 0) {
      String prefix = XmlUtil.getPrefix(namespace, elem);
      if (prefix == null) {
        prefix = generatePrefix(elem, QUALIFIED_VALUE_PREFIX);
        addNamespaceDeclaration(elem, namespace, prefix);
      }
      prefixedValue = prefix + ':' + value.getLocalPart();
    }
    else {
      prefixedValue = value.getLocalPart();
    }
    XmlUtil.setStringValue(elem, prefixedValue);
  }

  public static void copy(Element target, Element source) {
    // attributes
    removeAttributes(target);
    copyAttributes(target, source);
    // all namespaces
    copyVisibleNamespaces(target, source);
    // child nodes
    removeChildNodes(target);
    copyChildNodes(target, source);
  }

  public static void copyVisibleNamespaces(final Element target, Element source) {
    // copy namespaces declared at source element
    copyNamespaces(target, source);
    // go up the element hierarchy
    for (Node parent = source.getParentNode(); parent instanceof Element; parent = parent.getParentNode()) {
      copyNamespaces(target, (Element) parent);
    }
  }

  public static void copyNamespaces(final Element target, Element source) {
    // easy way out: no attributes
    if (!source.hasAttributes()) return;
    final boolean traceEnabled = log.isTraceEnabled();
    // traverse attributes to discover namespace declarations
    NamedNodeMap attributes = source.getAttributes();
    for (int i = 0, n = attributes.getLength(); i < n; i++) {
      Node attribute = attributes.item(i);
      // is attribute a namespace declaration?
      if (!BpelConstants.NS_XMLNS.equals(attribute.getNamespaceURI()))
        continue;
      // namespace declaration format xmlns:prefix="namespaceURI" |
      // xmlns="defaultNamespaceURI"
      String namespaceURI = attribute.getNodeValue();
      String prefix = attribute.getLocalName();
      // default namespace declaration?
      if ("xmlns".equals(prefix)) {
        // BPEL-195: prevent addition matching visible declaration at target
        if ("".equals(getPrefix(namespaceURI, target))) continue;
        addNamespaceDeclaration(target, namespaceURI);
        if (traceEnabled)
          log.trace("added default namespace declaration: " + namespaceURI);
      }
      else {
        // BPEL-195: prevent addition matching visible declaration at target
        if (prefix.equals(getPrefix(namespaceURI, target))) continue;
        addNamespaceDeclaration(target, namespaceURI, prefix);
        if (traceEnabled)
          log.trace("added namespace declaration: " + prefix + "->"
              + namespaceURI);
      }
    }
  }

  public static void copyAttributes(Element target, Element source) {
    // easy way out: no attributes
    if (!source.hasAttributes()) return;
    // traverse attributes
    NamedNodeMap attributes = source.getAttributes();
    for (int i = 0, n = attributes.getLength(); i < n; i++) {
      Node attribute = attributes.item(i);
      String namespaceURI = attribute.getNamespaceURI();
      // isn't the attribute a namespace declaration?
      if (!BpelConstants.NS_XMLNS.equals(namespaceURI)) {
        target.setAttributeNS(namespaceURI,
            attribute.getNodeName(),
            attribute.getNodeValue());
      }
    }
  }

  public static void copyChildNodes(Element target, Element source) {
    Document nodeFactory = target.getOwnerDocument();
    for (Node child = source.getFirstChild(); child != null; child = child.getNextSibling()) {
      target.appendChild(nodeFactory.importNode(child, true));
    }
  }

  public static void copy(Element target, SOAPElement source) {
    // attributes
    removeAttributes(target);
    copyAttributes(target, source);
    // all namespaces
    copyVisibleNamespaces(target, source);
    // child nodes
    removeChildNodes(target);
    copyChildNodes(target, source);
    if (log.isTraceEnabled())
      log.trace("copied element: " + source.getNodeName());
  }

  public static void copyVisibleNamespaces(Element target, SOAPElement source) {
    copyNamespaces(target, source, source.getVisibleNamespacePrefixes());
  }

  public static void copyNamespaces(Element target, SOAPElement source) {
    copyNamespaces(target, source, source.getNamespacePrefixes());
  }

  private static void copyNamespaces(Element target, SOAPElement source,
      Iterator prefixIt) {
    final boolean traceEnabled = log.isTraceEnabled();
    // namespace declarations appear as attributes in the target element
    while (prefixIt.hasNext()) {
      String prefix = (String) prefixIt.next();
      String namespaceURI = source.getNamespaceURI(prefix);
      // BPEL-195: prevent addition matching visible declaration at target
      if (prefix.equals(getPrefix(namespaceURI, target))) continue;
      addNamespaceDeclaration(target, namespaceURI, prefix);
      if (traceEnabled)
        log.trace("added namespace declaration: " + prefix + "->"
            + namespaceURI);
    }
  }

  public static void copyAttributes(Element target, SOAPElement source) {
    // easy way out: no attributes to copy
    if (!source.hasAttributes()) return;
    final boolean traceEnabled = log.isTraceEnabled();
    // traverse attributes
    Iterator attrNameIt = source.getAllAttributes();
    while (attrNameIt.hasNext()) {
      Name attrName = (Name) attrNameIt.next();
      String namespaceURI = attrName.getURI();
      String value = source.getAttributeValue(attrName);
      if (StringUtils.isEmpty(namespaceURI)) {
        String localName = attrName.getLocalName();
        target.setAttribute(localName, value);
        if (traceEnabled) log.trace("set attribute: " + localName);
      }
      else {
        String qualifiedName = attrName.getQualifiedName();
        target.setAttributeNS(namespaceURI, qualifiedName, value);
        if (traceEnabled) log.trace("set attribute: " + qualifiedName);
      }
    }
  }

  public static void copyChildNodes(Element target, SOAPElement source) {
    // easy way out: no child nodes to copy
    if (!source.hasChildNodes()) return;
    final boolean traceEnabled = log.isTraceEnabled();
    // traverse child nodes
    Iterator childIt = source.getChildElements();
    while (childIt.hasNext()) {
      Object child = childIt.next();
      if (child instanceof SOAPElement) {
        copyChildElement(target, (SOAPElement) child);
      }
      else if (child instanceof Text) {
        Text childTextNode = (Text) child;
        String text = childTextNode.getValue();
        target.appendChild(target.getOwnerDocument().createTextNode(text));
        if (traceEnabled) log.trace("added text node: " + text);
      }
      else {
        log.debug("discarding child node: " + child);
      }
    }
  }

  private static void copyChildElement(Element parent, SOAPElement source) {
    String namespaceURI = source.getNamespaceURI();
    String name = source.getNodeName();
    // create a child DOM element with the same name
    Element target = parent.getOwnerDocument().createElementNS(namespaceURI,
        name);
    parent.appendChild(target);
    if (log.isTraceEnabled())
      log.trace("appended child element: {" + namespaceURI + '}' + name);
    // namespaces
    copyNamespaces(target, source);
    // attributes
    copyAttributes(target, source);
    // child nodes
    copyChildNodes(target, source);
  }

  public static void copy(SOAPElement target, Element source)
      throws SOAPException {
    // attributes
    removeAttributes(target);
    copyAttributes(target, source);
    // all namespaces
    removeNamespaces(target);
    copyVisibleNamespaces(target, source);
    // child nodes
    target.removeContents();
    copyChildNodes(target, source);
    if (log.isTraceEnabled())
      log.trace("copied element: " + target.getNodeName());
  }

  public static void copyVisibleNamespaces(SOAPElement target, Element source)
      throws SOAPException {
    // copy the namespaces declared at the source element
    copyNamespaces(target, source);
    // go up the element hierarchy
    for (Node parent = source.getParentNode(); parent instanceof Element; parent = parent.getParentNode()) {
      copyNamespaces(target, (Element) parent);
    }
  }

  static final String DEFAULT_NAMESPACE_PREFIX = "defaultNS";

  public static void copyNamespaces(SOAPElement target, Element source)
      throws SOAPException {
    // easy way out: no attributes
    if (!source.hasAttributes()) return;
    final boolean traceEnabled = log.isTraceEnabled();
    // traverse attributes to discover namespace declarations
    NamedNodeMap attributes = source.getAttributes();
    for (int i = 0, n = attributes.getLength(); i < n; i++) {
      Node attribute = attributes.item(i);
      // is attribute a namespace declaration?
      if (!BpelConstants.NS_XMLNS.equals(attribute.getNamespaceURI()))
        continue;
      // namespace declaration format xmlns:prefix="namespaceURI" |
      // xmlns="defaultNamespaceURI"
      String namespaceURI = attribute.getNodeValue();
      String prefix = attribute.getLocalName();
      // non-default namespace declaration?
      if (!"xmlns".equals(prefix)) {
        // BPEL-195: prevent addition matching visible declaration at target
        if (namespaceURI.equals(target.getNamespaceURI(prefix))) continue;
        target.addNamespaceDeclaration(prefix, namespaceURI);
        if (traceEnabled)
          log.trace("added namespace declaration: " + prefix + "->"
              + namespaceURI);
      }
      // non-empty default namespace declaration
      else if (namespaceURI.length() > 0) {
        prefix = generatePrefix(source, DEFAULT_NAMESPACE_PREFIX);
        target.addNamespaceDeclaration(prefix, namespaceURI);
        if (traceEnabled)
          log.trace("reassigned default namespace declaration: " + prefix
              + "->" + namespaceURI);
      }
    }
  }

  public static void copyAttributes(SOAPElement target, Element source) {
    // easy way out: no attributes
    if (!source.hasAttributes()) return;
    final boolean traceEnabled = log.isTraceEnabled();
    // traverse attributes
    NamedNodeMap attributes = source.getAttributes();
    for (int i = 0, n = attributes.getLength(); i < n; i++) {
      Node attribute = attributes.item(i);
      String namespaceURI = attribute.getNamespaceURI();
      // isn't the attribute a namespace declaration?
      if (!BpelConstants.NS_XMLNS.equals(namespaceURI)) {
        String name = attribute.getNodeName();
        String value = attribute.getNodeValue();
        if (namespaceURI == null) {
          /*
           * use the DOM level 1 method as some SAAJ implementations complain
           * when presented a null namespace URI
           */
          target.setAttribute(name, value);
        }
        else {
          target.setAttributeNS(namespaceURI, name, value);
        }
        if (traceEnabled) log.trace("set attribute: " + name);
      }
    }
  }

  public static void copyChildNodes(SOAPElement target, Element source)
      throws SOAPException {
    // easy way out: no child nodes
    if (!source.hasChildNodes()) return;
    final boolean traceEnabled = log.isTraceEnabled();
    // traverse child nodes
    for (Node child = source.getFirstChild(); child != null; child = child.getNextSibling()) {
      switch (child.getNodeType()) {
      case Node.ELEMENT_NODE: {
        copyChildElement(target, (Element) child);
        break;
      }
      case Node.TEXT_NODE:
      case Node.CDATA_SECTION_NODE: {
        String text = child.getNodeValue();
        // drop whitespace-only text nodes
        if (!StringUtils.isWhitespace(text)) {
          target.addTextNode(text);
          if (traceEnabled) log.trace("added text node: " + text);
        }
        break;
      }
      default:
        log.debug("discarding child node: " + child);
      }
    }
  }

  public static void copyChildElement(SOAPElement parent, Element source)
      throws SOAPException {
    String localName = source.getLocalName();
    String prefix = source.getPrefix();
    String namespaceURI = source.getNamespaceURI();

    SOAPElement target;
    // no prefix?
    if (StringUtils.isEmpty(prefix)) {
      /*
       * element used a non-prefixed name, distinguish between no namespace and
       * default namespace
       */
      if (StringUtils.isEmpty(namespaceURI)) {
        // no namespace
        target = parent.addChildElement(localName);
        if (log.isTraceEnabled())
          log.trace("added child element: " + localName);
      }
      else {
        // default namespace, look for existing prefix at target
        prefix = getPrefix(namespaceURI, parent);
        // no prefix for that namespace?
        if (prefix == null) {
          prefix = generatePrefix(source, DEFAULT_NAMESPACE_PREFIX);
        }
        // BPEL-195 source maps prefix to another URI?
        else if (!namespaceURI.equals(source.getAttributeNS(BpelConstants.NS_XMLNS,
            prefix))) {
          prefix = generatePrefix(source, prefix);
        }
        target = parent.addChildElement(localName, prefix, namespaceURI);
        if (log.isTraceEnabled())
          log.trace("added child element: {" + namespaceURI + '}' + prefix
              + ':' + localName);
      }
    }
    else {
      target = parent.addChildElement(localName, prefix, namespaceURI);
      if (log.isTraceEnabled())
        log.trace("added child element: {" + namespaceURI + '}' + prefix + ':'
            + localName);
    }
    // namespaces
    copyNamespaces(target, source);
    // attributes
    copyAttributes(target, source);
    // child nodes
    copyChildNodes(target, source);
  }

  public static Node appendForeignChild(Node node, Node foreignChild) {
    return node.appendChild(node.getOwnerDocument().importNode(foreignChild,
        true));
  }

  public static void addNamespaceDeclaration(Element elem, String namespaceURI) {
    elem.setAttributeNS(BpelConstants.NS_XMLNS, "xmlns", namespaceURI);
  }

  public static void addNamespaceDeclaration(Element elem, String namespaceURI,
      String prefix) {
    elem.setAttributeNS(BpelConstants.NS_XMLNS, "xmlns:" + prefix, namespaceURI);
  }

  public static void removeAttributes(Element elem) {
    if (elem.hasAttributes()) {
      NamedNodeMap attributeMap = elem.getAttributes();
      // since node maps are live, we must hold them in a separate container
      int n = attributeMap.getLength();
      Attr[] attributes = new Attr[n];
      for (int i = 0; i < n; i++) {
        attributes[i] = (Attr) attributeMap.item(i);
      }
      // now remove each attribute from the element
      for (int i = 0; i < n; i++) {
        elem.removeAttributeNode(attributes[i]);
      }
    }
  }

  public static void removeChildNodes(Node node) {
    for (Node current = node.getFirstChild(), next; current != null; current = next) {
      next = current.getNextSibling();
      node.removeChild(current);
    }
  }

  public static void removeAttributes(SOAPElement elem) {
    if (elem.hasAttributes()) {
      Iterator attrNameIt = elem.getAllAttributes();
      while (attrNameIt.hasNext()) {
        Name attrName = (Name) attrNameIt.next();
        elem.removeAttribute(attrName);
      }
    }
  }

  public static void removeNamespaces(SOAPElement elem) {
    Iterator prefixIt = elem.getNamespacePrefixes();
    while (prefixIt.hasNext()) {
      String prefix = (String) prefixIt.next();
      elem.removeNamespaceDeclaration(prefix);
    }
  }

  public static Document createDocument() {
    return getDocumentBuilder().newDocument();
  }

  public static Element createElement(QName name) {
    String prefix = name.getPrefix();
    String localName = name.getLocalPart();
    String prefixedName = prefix.length() > 0 ? prefix + ':' + localName
        : localName;
    return createElement(name.getNamespaceURI(), prefixedName);
  }

  public static Element createElement(String namespaceURI, String prefixedName) {
    Document doc = createDocument();
    Element elem = doc.createElementNS(namespaceURI, prefixedName);
    // some TrAX implementations do not perform namespace fixup, declare
    // namespace
    if (namespaceURI != null) {
      String prefix = elem.getPrefix();
      if (prefix != null)
        addNamespaceDeclaration(elem, namespaceURI, prefix);
      else
        addNamespaceDeclaration(elem, namespaceURI);
    }
    doc.appendChild(elem);
    return elem;
  }

  /**
   * Parses the XML document contained in the given string into a DOM document.
   * @param text a string containing the document to parse
   * @return a new DOM document representing the XML content
   * @throws SAXException if any parse errors occur
   */
  public static Element parseElement(String text) throws SAXException {
    try {
      return getDocumentBuilder().parse(new InputSource(new StringReader(text)))
          .getDocumentElement();
    }
    catch (IOException e) {
      throw new RuntimeException("IO error parsing a *string*", e);
    }
  }

  /**
   * Gets a validating document builder local to the current thread.
   * @return a thread-local document builder
   */
  public static DocumentBuilder getDocumentBuilder() {
    return (DocumentBuilder) documentBuilderLocal.get();
  }

  public static Map getNamespaces(Element elem) {
    HashMap namespaces = new HashMap();
    try {
      Navigator nav = DocumentNavigator.getInstance();
      Iterator namespaceAxis = nav.getNamespaceAxisIterator(elem);
      while (namespaceAxis.hasNext()) {
        Object namespace = namespaceAxis.next();
        String prefix = nav.getNamespacePrefix(namespace);
        // exclude default namespaces and overriden namespaces
        if (!StringUtils.isEmpty(prefix) && !namespaces.containsKey(prefix)) {
          String uri = nav.getNamespaceStringValue(namespace);
          namespaces.put(prefix, uri);
        }
      }
    }
    catch (UnsupportedAxisException e) {
      log.fatal("Jaxen DOM navigator does not support the namespace axis, "
          + "cannot determine namespace declarations of DOM elements", e);
      throw new AssertionError(e);
    }
    return namespaces;
  }

  /**
   * Retrieves the prefix associated with a namespace URI in the given context
   * node.
   * @param namespaceURI the namespace whose prefix is required
   * @param contextNode the node where to search for namespace declarations
   * @return the prefix associated with the namespace URI; the empty string
   *         indicates the default namespace, while <code>null</code>
   *         indicates no association
   */
  public static String getPrefix(String namespaceURI, Node contextNode) {
    switch (contextNode.getNodeType()) {
    case Node.ATTRIBUTE_NODE:
      contextNode = ((Attr) contextNode).getOwnerElement();
      break;
    case Node.ELEMENT_NODE:
      break;
    default:
      contextNode = contextNode.getParentNode();
    }

    while (contextNode != null
        && contextNode.getNodeType() == Node.ELEMENT_NODE) {
      Element contextElem = (Element) contextNode;
      NamedNodeMap attributes = contextElem.getAttributes();

      for (int i = 0, n = attributes.getLength(); i < n; i++) {
        Node attr = attributes.item(i);
        if (namespaceURI.equals(attr.getNodeValue())) {
          String prefix = attr.getPrefix();
          if ("xmlns".equals(prefix)) {
            return attr.getLocalName();
          }
          else if (prefix == null && "xmlns".equals(attr.getLocalName())) {
            return "";
          }
        }
      }
      contextNode = contextNode.getParentNode();
    }
    return null;
  }

  public static String getPrefix(String namespaceURI, SOAPElement contextElem) {
    Iterator prefixIt = contextElem.getVisibleNamespacePrefixes();
    while (prefixIt.hasNext()) {
      String prefix = (String) prefixIt.next();
      if (namespaceURI.equals(contextElem.getNamespaceURI(prefix))) {
        return prefix;
      }
    }
    return null;
  }

  public static String getNamespaceURI(String prefix, Node contextNode) {
    return DOMUtils.getNamespaceURIFromPrefix(contextNode, prefix);
  }

  private static String generatePrefix(Element contextElem, String baseText) {
    // check possible collision with namespace declarations
    if (!contextElem.hasAttributeNS(BpelConstants.NS_XMLNS, baseText))
      return baseText;

    // collision detected, append natural numbers
    StringBuffer prefixBuffer = new StringBuffer(baseText);
    int baseLength = baseText.length();
    for (int i = 1; i < Integer.MAX_VALUE; i++) {
      // append natural number to base text
      String prefix = prefixBuffer.append(i).toString();
      // check possible collision with namespace declarations
      if (!contextElem.hasAttributeNS(BpelConstants.NS_XMLNS, prefix))
        return prefix;
      // remove appended number
      prefixBuffer.setLength(baseLength);
    }
    throw new RuntimeException("could not generate prefix");
  }

  /**
   * Evaluates the given XPath expression with the specified context node and
   * namespace declarations.
   * @param text the textual representation of an XPath expression
   * @param context the context node
   * @param namespaces the namespace declarations: prefix -> namespace URI
   * @return the result value: a {@link java.lang.String},
   *         {@link java.lang.Double}, {@link Boolean} or nodeset; in the last
   *         case, the object returned depends on the set size: <table>
   *         <tr>
   *         <th>size</th>
   *         <th>object</th>
   *         </tr>
   *         <tr>
   *         <td>0</td>
   *         <td><code>null</code></td>
   *         </tr>
   *         <tr>
   *         <td>1</td>
   *         <td>a {@link Node}</td>
   *         </tr>
   *         <tr>
   *         <td>2+</td>
   *         <td>a {@link List} of nodes</td>
   *         </tr>
   *         </table>
   */
  public static Object evaluateXPath(String text, Node context, Map namespaces) {
    try {
      XPath xpath = new DOMXPath(text);
      if (namespaces != null) {
        xpath.setNamespaceContext(new SimpleNamespaceContext(namespaces));
      }
      Object result = xpath.evaluate(context);
      if (result instanceof List) {
        List nodeset = (List) result;
        switch (nodeset.size()) {
        case 0:
          result = null;
          break;
        case 1:
          result = nodeset.get(0);
          break;
        }
      }
      return result;
    }
    catch (JaxenException e) {
      throw new RuntimeException("could not evaluate XPath", e);
    }
  }

  public static TransformerFactory getTransformerFactory() {
    return (TransformerFactory) transformerFactoryLocal.get();
  }

  public static Templates createTemplates(URL templateURL) {
    Templates templates = null;
    try {
      InputStream templateStream = templateURL.openStream();
      templates = getTransformerFactory().newTemplates(new StreamSource(
          templateStream));
      templateStream.close();
    }
    catch (Exception e) {
      log.error("unable to read templates: " + templateURL, e);
    }
    return templates;
  }

  private static class NamespaceElementPredicate implements Predicate {

    private final String namespaceURI;

    NamespaceElementPredicate(String namespaceURI) {
      this.namespaceURI = namespaceURI;
    }

    public boolean evaluate(Object arg) {
      return evaluate((Node) arg, namespaceURI);
    }

    static boolean evaluate(Node node, String namespaceURI) {
      String nodeNamespaceURI = node.getNamespaceURI();
      return node.getNodeType() == Node.ELEMENT_NODE
          && (namespaceURI != null ? namespaceURI.equals(nodeNamespaceURI)
              : nodeNamespaceURI == null || nodeNamespaceURI.length() == 0);
    }
  }

  private static class QNameElementPredicate implements Predicate {

    private final String namespaceURI;
    private final String localName;

    QNameElementPredicate(String namespaceURI, String localName) {
      this.namespaceURI = namespaceURI;
      this.localName = localName;
    }

    public boolean evaluate(Object arg) {
      return evaluate((Node) arg, namespaceURI, localName);
    }

    static boolean evaluate(Node node, String namespaceURI, String localName) {
      return NamespaceElementPredicate.evaluate(node, namespaceURI)
          && localName.equals(node.getLocalName());
    }
  }
}
