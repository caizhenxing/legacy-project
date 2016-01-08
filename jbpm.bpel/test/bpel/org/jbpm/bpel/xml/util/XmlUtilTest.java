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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;

import junit.framework.TestCase;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import org.jbpm.bpel.integration.SystemPropertiesTestHelper;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.11 $ $Date: 2006/08/29 23:02:43 $
 */
public class XmlUtilTest extends TestCase {

  public XmlUtilTest(String name) {
    super(name);
  }

  public void testRemoveAttributes_dom() throws Exception {
    String xml = "<lunch time='1200' produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + " xmlns:produce='urn:example:produce' xmlns:fish='urn:example:fish'/>";
    Element element = XmlUtil.parseElement(xml);
    // remove the attributes
    XmlUtil.removeAttributes(element);
    // verify remotion
    assertFalse(element.hasAttributes());
  }

  public void testRemoveAttributes_soap() throws Exception {
    String xml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>"
        + " <soap:Body xmlns:fish='urn:example:fish'>"
        + "  <lunch time='1200' produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "   xmlns:produce='urn:example:produce' />" + " </soap:Body>"
        + "</soap:Envelope>";
    ByteArrayInputStream sourceStream = new ByteArrayInputStream(xml.getBytes());
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null,
        sourceStream);
    SOAPElement element = XmlUtil.getElement(soapMessage.getSOAPBody(), "lunch");
    // remove the attributes
    XmlUtil.removeAttributes(element);
    // verify remotion with the dom & saaj apis
    assertFalse(element.hasAttribute("time"));
    assertFalse(element.hasAttributeNS("urn:example:produce", "lettuce"));
    assertFalse(element.hasAttributeNS("urn:example:fish", "fillet"));
    // namespaces should still be there
    // prefixed declaration
    assertEquals("produce", XmlUtil.getPrefix("urn:example:produce", element));
    // heir prefixed declaration
    assertEquals("fish", XmlUtil.getPrefix("urn:example:fish", element));
  }

  public void testRemoveChildNodes_dom() throws Exception {
    String xml = "<lunch xmlns:produce='urn:example:produce' xmlns='urn:example:meal'>"
        + " <time>1200</time>"
        + " <produce:lettuce>0.1lb</produce:lettuce>"
        + " <fish:fillet xmlns:fish='urn:example:fish'>0.25lb</fish:fillet>"
        + " <padding xmlns=''/>" + "</lunch>";
    Element element = XmlUtil.parseElement(xml);
    // remove the child nodes
    XmlUtil.removeChildNodes(element);
    // verify remotion
    assertFalse(element.hasChildNodes());
  }

  public void testRemoveChildNodes_soap() throws Exception {
    String xml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>"
        + " <soap:Body xmlns:fish='urn:example:fish'>"
        + "  <meal:lunch xmlns:produce='urn:example:produce'"
        + "   xmlns:meal='urn:example:meal'>" + "   <time>1200</time>"
        + "   <produce:lettuce>0.1lb</produce:lettuce>"
        + "   <fish:fillet xmlns:fish='urn:example:fish'>0.25lb</fish:fillet>"
        + "  </meal:lunch>" + " </soap:Body>" + "</soap:Envelope>";
    ByteArrayInputStream sourceStream = new ByteArrayInputStream(xml.getBytes());
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null,
        sourceStream);
    SOAPElement element = XmlUtil.getElement(soapMessage.getSOAPBody(),
        "urn:example:meal", "lunch");
    // remove the child nodes
    element.removeContents();
    // verify remotion
    assertFalse(element.getChildElements().hasNext());
  }

  public void testRemoveNamespaces_soap() throws Exception {
    String xml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>"
        + " <soap:Body xmlns:fish='urn:example:fish'>"
        + "  <lunch time='1200' produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "   xmlns:produce='urn:example:produce' />" + " </soap:Body>"
        + "</soap:Envelope>";
    ByteArrayInputStream sourceStream = new ByteArrayInputStream(xml.getBytes());
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null,
        sourceStream);
    SOAPElement element = XmlUtil.getElement(soapMessage.getSOAPBody(), "lunch");
    // remove namespaces
    XmlUtil.removeNamespaces(element);
    // verify remotion
    assertFalse(element.getNamespacePrefixes().hasNext());
    // attributes should still be there
    // qualified attributes
    assertEquals("0.1lb", element.getAttributeNS("urn:example:produce",
        "lettuce"));
    assertEquals("0.25lb", element.getAttributeNS("urn:example:fish", "fillet"));
    // local attribute
    assertEquals("1200", element.getAttribute("time"));
  }

  public void testCopyAttributes_domDom() throws Exception {
    String xml = "<lunch time='1200' produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + " xmlns:produce='urn:example:produce' xmlns:fish='urn:example:fish'/>";
    Element source = XmlUtil.parseElement(xml);
    Element target = XmlUtil.createElement(null, "detail");
    // perform the copy
    XmlUtil.copyAttributes(target, source);
    // qualified attributes
    assertEquals("0.1lb", target.getAttributeNS("urn:example:produce",
        "lettuce"));
    assertEquals("0.25lb", target.getAttributeNS("urn:example:fish", "fillet"));
    // local attribute
    assertEquals("1200", target.getAttribute("time"));
  }

  public void testCopyNamespaces_domDom() throws Exception {
    String xml = "<part xmlns:produce='urn:example:produce'>"
        + " <lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "  xmlns:fish='urn:example:fish' xmlns='urn:example:meal'/>"
        + "</part>";
    Element source = XmlUtil.getElement(XmlUtil.parseElement(xml),
        "urn:example:meal", "lunch");
    Element target = XmlUtil.createElement(null, "detail");

    // perform the copy
    XmlUtil.copyNamespaces(target, source);

    // prefixed declaration
    assertEquals("urn:example:fish", XmlUtil.getNamespaceURI("fish", target));
    // heir prefixed declaration
    assertNull(XmlUtil.getNamespaceURI("produce", target));
    // default declaration
    assertEquals("urn:example:meal", XmlUtil.getNamespaceURI(null, target));
  }

  public void testCopyVisibleNamespaces_domDom() throws Exception {
    String xml = "<part xmlns:produce='urn:example:produce'>"
        + " <lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "  xmlns:fish='urn:example:fish' xmlns='urn:example:meal'/>"
        + "</part>";
    Element source = XmlUtil.getElement(XmlUtil.parseElement(xml),
        "urn:example:meal", "lunch");
    Element target = XmlUtil.createElement(null, "detail");

    // perform the copy
    XmlUtil.copyVisibleNamespaces(target, source);

    // prefixed declaration
    assertEquals("urn:example:fish", XmlUtil.getNamespaceURI("fish", target));
    // heir prefixed declaration
    assertEquals("urn:example:produce", XmlUtil.getNamespaceURI("produce",
        target));
    // default declaration
    assertEquals("urn:example:meal", XmlUtil.getNamespaceURI(null, target));
  }

  public void testCopyVisibleNamespaces_domDom_targetMatch() throws Exception {
    String xml = "<part xmlns:produce='urn:example:produce'>"
        + " <lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "  xmlns:fish='urn:example:fish' xmlns='urn:example:meal'/>"
        + "</part>";
    Element source = XmlUtil.getElement(XmlUtil.parseElement(xml),
        "urn:example:meal", "lunch");

    String targetXml = "<detail xmlns:produce='urn:example:produce' xmlns='urn:example:meal'>"
        + " <other:target xmlns:other='urn:example:other'/>" + "</detail>";
    Element target = XmlUtil.getElement(XmlUtil.parseElement(targetXml),
        "urn:example:other", "target");

    // perform the copy
    XmlUtil.copyVisibleNamespaces(target, source);

    // prefixed declaration
    assertEquals("urn:example:fish", target.getAttributeNS(
        BpelConstants.NS_XMLNS, "fish"));
    // heir prefixed declaration - redundant
    assertNull(target.getAttributeNodeNS(BpelConstants.NS_XMLNS, "produce"));
    // default declaration - redundant
    assertNull(target.getAttributeNode("xmlns"));
  }

  public void testCopyChildNodes_domDom() throws Exception {
    String xml = "<lunch xmlns:produce='urn:example:produce'"
        + " xmlns='urn:example:meal'>" + " <time>1200</time>"
        + " <produce:lettuce>0.1lb</produce:lettuce>"
        + " <fish:fillet xmlns:fish='urn:example:fish'>0.25lb</fish:fillet>"
        + " <padding xmlns=''/>" + "</lunch>";
    Element source = XmlUtil.parseElement(xml);
    Element target = XmlUtil.createElement(null, "detail");
    // perform the copy
    XmlUtil.copyChildNodes(target, source);
    // qualified, prefixless element
    Element time = XmlUtil.getElement(target, "urn:example:meal", "time");
    assertNull("", time.getPrefix());
    // qualified, prefixed element
    Element lettuce = XmlUtil.getElement(target, "urn:example:produce",
        "lettuce");
    assertEquals("produce", lettuce.getPrefix());
    // heir qualified, prefixed element
    Element fillet = XmlUtil.getElement(target, "urn:example:fish", "fillet");
    assertEquals("fish", fillet.getPrefix());
    // local element
    Element padding = XmlUtil.getElement(target, "padding");
    assertNull(padding.getPrefix());
  }

  public void testCopyAttributes_soapDom() throws Exception {
    String xml = "<lunch time='1200' produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + " xmlns:produce='urn:example:produce' xmlns:fish='urn:example:fish'/>";
    Element source = XmlUtil.parseElement(xml);
    SOAPFactory soapFactory = SOAPFactory.newInstance();
    SOAPElement target = soapFactory.createElement("detail");
    // perform the copy
    XmlUtil.copyAttributes(target, source);
    // qualified attributes
    assertEquals("0.1lb", target.getAttributeValue(soapFactory.createName(
        "lettuce", null, "urn:example:produce")));
    assertEquals("0.25lb", target.getAttributeValue(soapFactory.createName(
        "fillet", null, "urn:example:fish")));
    // local attribute
    assertEquals("1200",
        target.getAttributeValue(soapFactory.createName("time")));
  }

  public void testCopyNamespaces_soapDom() throws Exception {
    String xml = "<part xmlns:produce='urn:example:produce'>"
        + " <lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "  xmlns:fish='urn:example:fish' xmlns='urn:example:meal'/>"
        + "</part>";
    Element source = XmlUtil.getElement(XmlUtil.parseElement(xml),
        "urn:example:meal", "lunch");
    SOAPFactory soapFactory = SOAPFactory.newInstance();
    SOAPElement target = soapFactory.createElement("detail");
    // perform the copy
    XmlUtil.copyNamespaces(target, source);
    // prefixed declaration
    assertEquals("urn:example:fish", target.getNamespaceURI("fish"));
    // heir prefixed declaration
    assertNull(target.getNamespaceURI("produce"));
    // default declaration (reassigned)
    assertEquals("urn:example:meal",
        target.getNamespaceURI(XmlUtil.DEFAULT_NAMESPACE_PREFIX));
  }

  public void testCopyVisibleNamespaces_soapDom() throws Exception {
    String xml = "<part xmlns:produce='urn:example:produce'>"
        + " <lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "  xmlns:fish='urn:example:fish' xmlns='urn:example:meal'/>"
        + "</part>";
    Element source = XmlUtil.getElement(XmlUtil.parseElement(xml),
        "urn:example:meal", "lunch");
    SOAPFactory soapFactory = SOAPFactory.newInstance();
    SOAPElement target = soapFactory.createElement("lunch");
    // perform the copy
    XmlUtil.copyVisibleNamespaces(target, source);
    // prefixed declaration
    assertEquals("urn:example:fish", target.getNamespaceURI("fish"));
    // heir prefixed declaration
    assertEquals("urn:example:produce", target.getNamespaceURI("produce"));
    // default declaration (reassigned)
    assertEquals("urn:example:meal",
        target.getNamespaceURI(XmlUtil.DEFAULT_NAMESPACE_PREFIX));
  }

  public void testCopyVisibleNamespaces_soapDom_targetMatch() throws Exception {
    String xml = "<part xmlns:produce='urn:example:produce'>"
        + " <lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "  xmlns:fish='urn:example:fish' xmlns='urn:example:meal'/>"
        + "</part>";
    Element source = XmlUtil.getElement(XmlUtil.parseElement(xml),
        "urn:example:meal", "lunch");

    String targetXml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE
        + "'>"
        + " <soap:Body>"
        + "  <other:Operation xmlns:produce='urn:example:produce' xmlns:meal='urn:example:meal'"
        + "   xmlns:other='urn:example:other'>" + "   <lunch />"
        + "  </other:Operation>" + " </soap:Body>" + "</soap:Envelope>";
    SOAPMessage soapMessage = toSOAPMessage(targetXml);
    SOAPElement operation = XmlUtil.getElement(soapMessage.getSOAPBody(),
        "urn:example:other", "Operation");
    SOAPElement target = XmlUtil.getElement(operation, "lunch");

    // in the WS4EE stack, target contains the *visible* namespace after parsing
    target.removeNamespaceDeclaration("produce");
    target.removeNamespaceDeclaration("meal");

    // perform the copy
    XmlUtil.copyVisibleNamespaces(target, source);
    List prefixes = IteratorUtils.toList(target.getNamespacePrefixes());

    // prefixed declaration
    assertTrue(prefixes.contains("fish"));
    assertEquals("urn:example:fish", target.getNamespaceURI("fish"));
    // heir prefixed declaration
    assertFalse(prefixes.contains("produce"));
    assertEquals("urn:example:produce", target.getNamespaceURI("produce"));
    // default declaration (reassigned)
    assertFalse(prefixes.contains("meal"));
    assertEquals("urn:example:meal", target.getNamespaceURI("meal"));
  }

  public void testCopyChildNodes_soapDom() throws Exception {
    String xml = "<lunch xmlns:produce='urn:example:produce'"
        + " xmlns='urn:example:meal'>" + " <time>1200</time>"
        + " <produce:lettuce>0.1lb</produce:lettuce>"
        + " <fish:fillet xmlns:fish='urn:example:fish'>0.25lb</fish:fillet>"
        + " <padding xmlns=''/>" + "</lunch>";
    Element source = XmlUtil.parseElement(xml);
    SOAPElement target = SOAPFactory.newInstance().createElement("detail");
    // perform the copy
    XmlUtil.copyChildNodes(target, source);
    // qualified, prefixless element
    SOAPElement time = XmlUtil.getElement(target, "urn:example:meal", "time");
    assertEquals(XmlUtil.DEFAULT_NAMESPACE_PREFIX, time.getPrefix());
    // qualified, prefixed element
    SOAPElement lettuce = XmlUtil.getElement(target, "urn:example:produce",
        "lettuce");
    assertEquals("produce", lettuce.getPrefix());
    // heir qualified, prefixed element
    SOAPElement fillet = XmlUtil.getElement(target, "urn:example:fish",
        "fillet");
    assertEquals("fish", fillet.getPrefix());
    // local element
    SOAPElement padding = XmlUtil.getElement(target, "padding");
    assertTrue(StringUtils.isEmpty(padding.getPrefix()));
    assertTrue(StringUtils.isEmpty(padding.getNamespaceURI()));
  }

  public void testCopyAttributes_domSoap() throws Exception {
    String xml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>"
        + " <soap:Body xmlns:fish='urn:example:fish'>"
        + "  <lunch time='1200' produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "   xmlns:produce='urn:example:produce' />" + " </soap:Body>"
        + "</soap:Envelope>";
    SOAPMessage soapMessage = toSOAPMessage(xml);
    SOAPElement source = XmlUtil.getElement(soapMessage.getSOAPBody(), "lunch");
    Element target = XmlUtil.createElement(null, "detail");
    // perform the copy
    XmlUtil.copyAttributes(target, source);
    // qualified attributes
    assertEquals("0.1lb", target.getAttributeNS("urn:example:produce",
        "lettuce"));
    assertEquals("0.25lb", target.getAttributeNS("urn:example:fish", "fillet"));
    // local attribute
    assertEquals("1200", target.getAttribute("time"));
  }

  public void testCopyNamespaces_domSoap() throws Exception {
    String xml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>"
        + " <soap:Body xmlns:produce='urn:example:produce'>"
        + "  <meal:lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "   xmlns:fish='urn:example:fish' xmlns:meal='urn:example:meal'/>"
        + " </soap:Body>" + "</soap:Envelope>";
    SOAPMessage soapMessage = toSOAPMessage(xml);
    SOAPElement source = XmlUtil.getElement(soapMessage.getSOAPBody(),
        "urn:example:meal", "lunch");
    Element target = XmlUtil.createElement(null, "detail");

    // perform the copy
    XmlUtil.copyNamespaces(target, source);

    // prefixed declaration
    assertEquals("urn:example:fish", XmlUtil.getNamespaceURI("fish", target));
    assertEquals("urn:example:meal", XmlUtil.getNamespaceURI("meal", target));
    // heir prefixed declaration
    assertNull(XmlUtil.getNamespaceURI("produce", target));
    assertNull(XmlUtil.getNamespaceURI("soap", target));
  }

  public void testCopyVisibleNamespaces_domSoap() throws Exception {
    String xml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>"
        + " <soap:Body xmlns:produce='urn:example:produce'>"
        + "  <meal:lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' xmlns=''"
        + "   xmlns:fish='urn:example:fish' xmlns:meal='urn:example:meal'/>"
        + " </soap:Body>" + "</soap:Envelope>";
    SOAPMessage soapMessage = toSOAPMessage(xml);
    SOAPElement source = XmlUtil.getElement(soapMessage.getSOAPBody(),
        "urn:example:meal", "lunch");
    Element target = XmlUtil.createElement(null, "detail");

    // perform the copy
    XmlUtil.copyVisibleNamespaces(target, source);

    // prefixed declaration
    assertEquals("urn:example:fish", XmlUtil.getNamespaceURI("fish", target));
    assertEquals("urn:example:meal", XmlUtil.getNamespaceURI("meal", target));
    // heir prefixed declaration
    assertEquals("urn:example:produce", XmlUtil.getNamespaceURI("produce",
        target));
    assertEquals(SOAPConstants.URI_NS_SOAP_ENVELOPE, XmlUtil.getNamespaceURI(
        "soap", target));
  }

  public void testCopyVisibleNamespaces_domSoap_targetMatch() throws Exception {
    String xml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>"
        + " <soap:Body xmlns:produce='urn:example:produce'>"
        + "  <meal:lunch produce:lettuce='0.1lb' fish:fillet='0.25lb' "
        + "   xmlns:fish='urn:example:fish' xmlns:meal='urn:example:meal'/>"
        + " </soap:Body>" + "</soap:Envelope>";
    SOAPMessage soapMessage = toSOAPMessage(xml);
    SOAPElement source = XmlUtil.getElement(soapMessage.getSOAPBody(),
        "urn:example:meal", "lunch");

    String targetXml = "<detail xmlns:produce='urn:example:produce'>"
        + " <other:target xmlns:other='urn:example:other'/>" + "</detail>";
    Element target = XmlUtil.getElement(XmlUtil.parseElement(targetXml),
        "urn:example:other", "target");

    // perform the copy
    XmlUtil.copyVisibleNamespaces(target, source);

    // prefixed declaration
    assertEquals("urn:example:fish", target.getAttributeNS(
        BpelConstants.NS_XMLNS, "fish"));
    assertEquals("urn:example:meal", target.getAttributeNS(
        BpelConstants.NS_XMLNS, "meal"));
    // heir prefixed declaration
    assertNull(target.getAttributeNodeNS(BpelConstants.NS_XMLNS, "produce"));
    assertEquals(SOAPConstants.URI_NS_SOAP_ENVELOPE, target.getAttributeNS(
        BpelConstants.NS_XMLNS, "soap"));
  }

  public void testCopyChildNodes_domSoap() throws Exception {
    String xml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>"
        + " <soap:Body xmlns:fish='urn:example:fish'>"
        + "  <meal:lunch xmlns:produce='urn:example:produce'"
        + "   xmlns:meal='urn:example:meal'>" + "   <time>1200</time>"
        + "   <produce:lettuce>0.1lb</produce:lettuce>"
        + "   <fish:fillet xmlns:fish='urn:example:fish'>0.25lb</fish:fillet>"
        + "  </meal:lunch>" + " </soap:Body>" + "</soap:Envelope>";
    SOAPMessage soapMessage = toSOAPMessage(xml);
    SOAPElement source = XmlUtil.getElement(soapMessage.getSOAPBody(),
        "urn:example:meal", "lunch");
    Element target = XmlUtil.createElement(null, "detail");
    // perform the copy
    XmlUtil.copyChildNodes(target, source);
    // local element
    Element time = XmlUtil.getElement(target, "time");
    assertNull(time.getPrefix());
    // qualified, prefixed element
    Element lettuce = XmlUtil.getElement(target, "urn:example:produce",
        "lettuce");
    assertEquals("produce", lettuce.getPrefix());
    // heir qualified, prefixed element
    Element fillet = XmlUtil.getElement(target, "urn:example:fish", "fillet");
    assertEquals("fish", fillet.getPrefix());
  }

  // BPEL-124: child elements in the default namespace are incorrectly losing
  // their namespaces
  public void testCopy_soapDom_qualifiedNoPrefix() throws Exception {
    String xml = "<ReverseAndConcatNames xmlns='http://my.namespace'>"
        + " <firstName>Martin</firstName>"
        + " <secondName>Steinle</secondName>" + "</ReverseAndConcatNames>";
    Element source = XmlUtil.parseElement(xml);
    SOAPElement target = SOAPFactory.newInstance().createElement("detail");

    // perform the copy
    XmlUtil.copy(target, source);

    assertEquals("http://my.namespace",
        target.getNamespaceURI(XmlUtil.DEFAULT_NAMESPACE_PREFIX));
    // qualified elements
    SOAPElement firstName = XmlUtil.getElement(target, "http://my.namespace",
        "firstName");
    assertEquals("Martin", firstName.getValue());
    SOAPElement secondName = XmlUtil.getElement(target, "http://my.namespace",
        "secondName");
    assertEquals("Steinle", secondName.getValue());
  }

  public void testCopy_soapDom_noOverride() throws Exception {
    String xml = "<part xmlns:produce='urn:example:produce'>"
        + " <lunch produce:lettuce='0.1lb' fish:fillet='0.25lb'"
        + "  xmlns:fish='urn:example:fish' xmlns='urn:example:meal'/>"
        + "</part>";
    Element source = XmlUtil.parseElement(xml);

    /*
     * here, notice the 'urn:example:meal' namespace (the default namespace in
     * the source) is mapped to prefix 'fish' which the source maps to namespace
     * 'urn:example:fish'
     */
    String targetXml = "<soap:Envelope xmlns:soap='"
        + SOAPConstants.URI_NS_SOAP_ENVELOPE + "'>" + " <soap:Body>"
        + "  <fish:Operation xmlns:fish='urn:example:meal'>" + "   <part />"
        + "  </fish:Operation>" + " </soap:Body>" + "</soap:Envelope>";
    SOAPMessage soapMessage = toSOAPMessage(targetXml);
    SOAPElement operation = XmlUtil.getElement(soapMessage.getSOAPBody(),
        "urn:example:meal", "Operation");
    SOAPElement part = XmlUtil.getElement(operation, "part");

    // perform the copy
    XmlUtil.copy(part, source);

/*    // write to memory sink
    ByteArrayOutputStream soapSink = new ByteArrayOutputStream();
    soapMessage.writeTo(soapSink);
    soapSink.writeTo(System.out);
    
    // read from memory source
    soapMessage = MessageFactory.newInstance().createMessage(null,
        new ByteArrayInputStream(soapSink.toByteArray()));
    
    // retrieve relevant elements
    operation = XmlUtil.getElement(soapMessage.getSOAPBody(),
        "urn:example:meal", "Operation");
    part = XmlUtil.getElement(operation, "part");*/
    
    SOAPElement lunch = XmlUtil.getElement(part, "urn:example:meal", "lunch");
    
    // prefixed declaration
    assertEquals("urn:example:fish", lunch.getNamespaceURI("fish"));
    // heir prefixed declaration
    assertEquals("urn:example:produce", lunch.getNamespaceURI("produce"));
    // default declaration (reassigned)
    assertEquals("urn:example:meal",
        lunch.getNamespaceURI(XmlUtil.DEFAULT_NAMESPACE_PREFIX));
  }

  public void testSetObjectValue_boolean() {
    Element elem = XmlUtil.createElement(null, "elem");
    XmlUtil.setObjectValue(elem, Boolean.TRUE);
    assertEquals("true", elem.getFirstChild().getNodeValue());
  }

  public void testSetObjectValue_string() {
    Element elem = XmlUtil.createElement(null, "elem");
    XmlUtil.setObjectValue(elem, "popcorn");
    assertEquals("popcorn", elem.getFirstChild().getNodeValue());
  }

  public void testSetObjectValue_number() {
    Element elem = XmlUtil.createElement(null, "elem");
    XmlUtil.setObjectValue(elem, new Double(Math.PI));
    assertEquals(Double.toString(Math.PI), elem.getFirstChild().getNodeValue());
  }

  public void testSetQNameValue() {
    Element elem = XmlUtil.createElement(null, "elem");
    QName value = new QName(BpelConstants.NS_EXAMPLES, "local");
    XmlUtil.setQNameValue(elem, value);

    String prefixedValue = XmlUtil.getStringValue(elem);
    int colonIndex = prefixedValue.indexOf(':');
    // local name
    assertEquals("local", prefixedValue.substring(colonIndex + 1));
    // namespace
    String prefix = prefixedValue.substring(0, colonIndex);
    assertEquals(BpelConstants.NS_EXAMPLES, XmlUtil.getNamespaceURI(prefix,
        elem));
  }

  private static SOAPMessage toSOAPMessage(String xmlString)
      throws IOException, SOAPException {
    ByteArrayInputStream sourceStream = new ByteArrayInputStream(
        xmlString.getBytes());
    return MessageFactory.newInstance().createMessage(null, sourceStream);
  }

  static {
    SystemPropertiesTestHelper.initializeProperties();
  }
}