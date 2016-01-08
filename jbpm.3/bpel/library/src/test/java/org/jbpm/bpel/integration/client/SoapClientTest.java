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
package org.jbpm.bpel.integration.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.exe.FaultInstance;
import org.jbpm.bpel.integration.SystemPropertiesTestHelper;
import org.jbpm.bpel.integration.client.SoapClient;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:06:10 $
 */
public class SoapClientTest extends TestCase {

  static final String NS_TRANSLATOR = "http://example.com/translator";
  static final String NS_TYPES = "http://example.com/translator/types";
  static final QName TRANSLATOR_SERVICE = new QName(NS_TRANSLATOR, "translatorService");
  static final String TEXT_WSDL_LOCATION = "http://localhost:8080/translator/text?wsdl";
  static final String DOC_WSDL_LOCATION = "http://localhost:8080/translator/document?wsdl";

  public SoapClientTest(String name) {
    super(name);
  }

  public void testCall_document_requestResponse() throws Exception {
    String requestText =
      "<sns:translationRequest targetLanguage='es' xmlns:sns='" + NS_TYPES + "'>" +
      " <sns:document>" +
      "  <head title='letter' language='en'/>" +
      "  <body>" +
      "   <paragraph>hi</paragraph>" +
      "   <paragraph>bye</paragraph>" +
      "  </body>" +
      " </sns:document>" +
      "</sns:translationRequest>";

    SoapClient soapClient = createClient(DOC_WSDL_LOCATION, TRANSLATOR_SERVICE, "documentTranslatorPort");
    Map inputParts = Collections.singletonMap("translationRequest", XmlUtil.parseElement(requestText));
    Map outputParts = soapClient.call("translate", inputParts);

    Element docElem = (Element) outputParts.get("document");

    Element headElem = XmlUtil.getElement(docElem, "head");
    assertEquals("carta", headElem.getAttribute("title"));
    assertEquals("es", headElem.getAttribute("language"));

    Element bodyElem = XmlUtil.getElement(docElem, "body");
    Iterator paragraphElems = XmlUtil.getElements(bodyElem, null, "paragraph");
    assertEquals("hola", XmlUtil.getStringValue((Element) paragraphElems.next()));
    assertEquals("adi\u00f3s", XmlUtil.getStringValue((Element) paragraphElems.next()));
    assertFalse(paragraphElems.hasNext());
  }

  public void testCall_document_requestFault() throws Exception {
    String requestText =
      "<sns:translationRequest targetLanguage='es' xmlns:sns='" + NS_TYPES + "'>" +
      " <sns:document>" +
      "  <head title='letter' language='en'/>" +
      "  <body>" +
      "   <paragraph>hi</paragraph>" +
      "   <paragraph>wawa</paragraph>" +
      "  </body>" +
      " </sns:document>" +
      "</sns:translationRequest>";

    SoapClient soapClient = createClient(DOC_WSDL_LOCATION, TRANSLATOR_SERVICE, "documentTranslatorPort");
    Map inputParts = Collections.singletonMap("translationRequest", XmlUtil.parseElement(requestText));
    try {
      soapClient.call("translate", inputParts);
      fail("call should have thrown a fault");
    }
    catch (BpelFaultException e) {
      FaultInstance faultInstance = e.getFaultInstance();
      assertEquals(new QName(NS_TRANSLATOR, "textNotTranslatable"), faultInstance.getName());
      
      MessageValue message = faultInstance.getMessageValue();
      assertEquals(new QName(NS_TRANSLATOR, "textNotTranslatableFault"), message.getType().getName());

      Element detailPart = message.getPart("detail");
      assertEquals(NS_TYPES, detailPart.getNamespaceURI());
      assertEquals("textNotTranslatable", detailPart.getLocalName());
      
      Element textElem = XmlUtil.getElement(detailPart, "text");
      assertEquals("wawa", XmlUtil.getStringValue(textElem));
    }
  }

  public void testCall_document_oneWay() throws Exception {
    String clientName = createClientName();
    String requestText =
      "<sns:quotationRequest clientName='" + clientName + "' targetLanguage='es' " +
      " xmlns:sns='" + NS_TYPES + "'>" +
      " <sns:document>" +
      "  <head title='letter' language='en'/>" +
      "  <body>" +
      "   <paragraph>hi</paragraph>" +
      "   <paragraph>bye</paragraph>" +
      "  </body>" +
      " </sns:document>" +
      "</sns:quotationRequest>";

    SoapClient soapClient = createClient(DOC_WSDL_LOCATION, TRANSLATOR_SERVICE, "documentTranslatorPort");
    Map inputParts = Collections.singletonMap("quotationRequest", XmlUtil.parseElement(requestText));
    soapClient.callOneWay("quoteTranslation", inputParts);

    requestText =
      "<sns:statusRequest clientName='" + clientName + "' xmlns:sns='" + NS_TYPES + "' />";
    inputParts = Collections.singletonMap("statusRequest", XmlUtil.parseElement(requestText));
    // quote is an one-way operation, so the status change might not be reflected immediately
    Thread.sleep(500);
    Map outputParts = soapClient.call("getQuotationStatus", inputParts);

    Element statusElem = (Element) outputParts.get("statusResponse");
    assertEquals("received", statusElem.getAttribute("status"));
  }

  public void testCall_rpc_requestResponse() throws Exception {
    Map inputParts = new HashMap();
    addRpcPart(inputParts, "text", "hi");
    addRpcPart(inputParts, "sourceLanguage", "en");
    addRpcPart(inputParts, "targetLanguage", "es");
    SoapClient soapClient = createClient(TEXT_WSDL_LOCATION, TRANSLATOR_SERVICE, "textTranslatorPort");
    Map outputParts = soapClient.call("translate", inputParts);

    Element textAccessor = (Element) outputParts.get("translatedText");
    assertEquals("hola", XmlUtil.getStringValue(textAccessor));
  }
  
  private static void addRpcPart(Map parts, String partName, String value) {
    Element part = XmlUtil.createElement(null, partName);
    XmlUtil.setStringValue(part, value);
    parts.put(partName, part);
  }

  public void testCall_rpc_requestFault() throws Exception {
    Map inputParts = new HashMap();
    addRpcPart(inputParts, "text", "hi");
    addRpcPart(inputParts, "sourceLanguage", "en");
    addRpcPart(inputParts, "targetLanguage", "ja");
    SoapClient soapClient = createClient(TEXT_WSDL_LOCATION, TRANSLATOR_SERVICE, "textTranslatorPort");
    try {
      soapClient.call("translate", inputParts);
      fail("call should have thrown a fault");
    }
    catch (BpelFaultException e) {
      // check returned fault
      FaultInstance faultInstance = e.getFaultInstance();
      // name
      assertEquals(new QName(NS_TRANSLATOR, "dictionaryNotAvailable"), faultInstance.getName());
      // data type
      MessageValue message = faultInstance.getMessageValue();
      assertEquals(new QName(NS_TRANSLATOR, "dictionaryNotAvailableFault"), message.getType().getName());
      // data content
      Element detailPart = message.getPart("detail");
      assertEquals("http://example.com/translator/types", detailPart.getNamespaceURI());
      assertEquals("dictionaryNotAvailable", detailPart.getLocalName());
    }
  }

  public void testCall_rpc_oneWay() throws Exception {
    String clientName = createClientName();
    Map inputParts = new HashMap();
    addRpcPart(inputParts, "clientName", clientName);
    addRpcPart(inputParts, "text", "hi");
    addRpcPart(inputParts, "sourceLanguage", "en");
    addRpcPart(inputParts, "targetLanguage", "ja");
    SoapClient soapClient = createClient(TEXT_WSDL_LOCATION, TRANSLATOR_SERVICE, "textTranslatorPort");
    soapClient.callOneWay("quoteTranslation", inputParts);

    inputParts.clear();
    addRpcPart(inputParts, "clientName", clientName);
    /* quote is an one-way operation, so the status change might not be reflected immediately */
    Thread.sleep(500);
    Map outputParts = soapClient.call("getQuotationStatus", inputParts);

    Element statusAccessor = (Element) outputParts.get("status");
    assertEquals("received", XmlUtil.getStringValue(statusAccessor));
  }

  private static SoapClient createClient(String wsdlLocation, QName serviceName, String portName)
  throws WSDLException {
    // read wsdl
    Definition def = WsdlUtil.getFactory().newWSDLReader().readWSDL(wsdlLocation);
    Service service = def.getService(serviceName);
    Port port = service.getPort(portName);

    // configure client
    return new SoapClient(port);
  }

  private static String createClientName() {
    return "client" + new Random().nextInt(100000);
  }

  static {
    SystemPropertiesTestHelper.initializeProperties();
  }
}
