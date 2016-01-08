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

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.wsdl.util.xml.DOMUtils;

import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Juan Cantú
 * @version $Revision: 1.20 $ $Date: 2006/08/22 04:13:10 $
 */
public class BpelConverterTest extends TestCase {
  
  public void testMultipleNamespacePrefixes_noPrefix() throws Exception {
    String xml = 
      "<process xmlns:rats='http://rodents.net' xmlns:mice='http://rodents.net'" +
      " xmlns:bpel='" + BpelConstants.NS_BPWS_1_1 + "'" +
      " xmlns='" + BpelConstants.NS_BPWS_1_1 + "'>" +
      " <variables>" +
      "  <variable name='rat' element='rats:rat'/>" +
      "  <variable name='mouse' element='mice:mouse'/>" +
      " </variables>" +
      "</process>";
    Element process = transformNoWrap(xml);
    // variables
    Element variables = XmlUtil.getElement(process, BpelConstants.NS_BPWS, BpelConstants.ELEM_VARIABLES);
    Iterator variableIt = XmlUtil.getElements(variables, BpelConstants.NS_BPWS, BpelConstants.ELEM_VARIABLE);
    // rat
    Element variable = (Element) variableIt.next();
    QName element = XmlUtil.getQName(variable.getAttribute(BpelConstants.ATTR_ELEMENT), variable);
    assertEquals("rat", element.getLocalPart());
    assertEquals("http://rodents.net", element.getNamespaceURI());
    // mouse
    variable = (Element) variableIt.next();
    element = XmlUtil.getQName(variable.getAttribute(BpelConstants.ATTR_ELEMENT), variable);
    assertEquals("mouse", element.getLocalPart());
    assertEquals("http://rodents.net", element.getNamespaceURI());    
  }
  
  public void testMultipleNamespacePrefixes_bpelPrefix() throws Exception {
    String xml = 
      "<bpel:process xmlns:rats='http://rodents.net' xmlns:mice='http://rodents.net' " +
      " xmlns:bpel='" + BpelConstants.NS_BPWS_1_1 + "'" +
      " xmlns=''>" +
      " <bpel:variables>" +
      "  <bpel:variable name='rat' element='rats:rat'/>" +
      "  <bpel:variable name='mouse' element='mice:mouse'/>" +
      " </bpel:variables>" +
      "</bpel:process>";
    Element process = transformNoWrap(xml);
    // variables
    Element variables = XmlUtil.getElement(process, BpelConstants.NS_BPWS, BpelConstants.ELEM_VARIABLES);
    Iterator variableIt = XmlUtil.getElements(variables, BpelConstants.NS_BPWS, BpelConstants.ELEM_VARIABLE);
    // rat
    Element variable = (Element) variableIt.next();
    QName element = XmlUtil.getQName(variable.getAttribute(BpelConstants.ATTR_ELEMENT), variable);
    assertEquals("rat", element.getLocalPart());
    assertEquals("http://rodents.net", element.getNamespaceURI());
    // mouse
    variable = (Element) variableIt.next();
    element = XmlUtil.getQName(variable.getAttribute(BpelConstants.ATTR_ELEMENT), variable);
    assertEquals("mouse", element.getLocalPart());
    assertEquals("http://rodents.net", element.getNamespaceURI());
  }
  
  /////////////////////// Renamed Elements
  
  public void testProcessDefaultQueryLanguage() throws Exception {
    String xml = "<process queryLanguage='http://www.w3.org/TR/1999/REC-xpath-19991116'/>";
    Element scope = transform(xml);
    assertEquals("urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0", XmlUtil.getAttribute(scope, "queryLanguage"));
  }    

  public void testProcessDefaultExpressionLanguage() throws Exception {
    String xml = "<process expressionLanguage='http://www.w3.org/TR/1999/REC-xpath-19991116'/>";
    Element scope = transform(xml);
    assertEquals("urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0", XmlUtil.getAttribute(scope, "expressionLanguage"));
  }
  
  public void testScopeVariableAccessSerializable() throws Exception {
    String xml = "<scope variableAccessSerializable='yes'/>";
    Element scope = transform(xml);
    assertNull( XmlUtil.getAttribute(scope, "variableAccessSerializable"));
    assertEquals("yes", XmlUtil.getAttribute(scope, "isolated"));
  }  
  
  public void testScopeOnMessage() throws Exception {
    String xml = "<scope><eventHandlers><onMessage/><onMessage/></eventHandlers></scope>";
    Element scope = transform(xml);
    Element events = XmlUtil.getElement(scope, BpelConstants.NS_BPWS, "eventHandlers");
    assertNotNull(events);
    assertEquals(2, events.getElementsByTagNameNS(BpelConstants.NS_BPWS, "onEvent").getLength()); 
  }
  
  public void testPickOnMessage() throws Exception {
    String xml = "<pick><onMessage/><onMessage/></pick>";
    Element pick = transform(xml);
    assertEquals(2, pick.getElementsByTagNameNS(BpelConstants.NS_BPWS, "onMessage").getLength());
  }
  
  public void testTerminate() throws Exception {
    String xml = "<terminate/>";
    Element exit = transform(xml);
    assertEquals("exit", exit.getLocalName());
    assertEquals(BpelConstants.NS_BPWS, exit.getNamespaceURI());
  }
  
  /////////////////////// Attributes that changed into elements
  
  public void testOnAlarmFor() throws Exception {
    String xml = "<onAlarm for='f'><empty/></onAlarm>";
    Element onAlarm = transform(xml);
    Element forElem = XmlUtil.getElement(onAlarm, BpelConstants.NS_BPWS, "for");
    assertNotNull(forElem);
    assertEquals("f", XmlUtil.getStringValue(forElem));
  }
  
  public void testOnAlarmUntil() throws Exception {
    String xml = "<onAlarm until='u'><empty/></onAlarm>";
    Element onAlarm = transform(xml);
    Element until = XmlUtil.getElement(onAlarm, BpelConstants.NS_BPWS, "until");
    assertNotNull(until);
    assertEquals("u", XmlUtil.getStringValue(until));    
  }
  
  public void testWaitFor() throws Exception {
    String xml = "<wait for='f'/>";
    Element wait = transform(xml);
    Element forElem = XmlUtil.getElement(wait, BpelConstants.NS_BPWS, "for");
    assertEquals("f", XmlUtil.getStringValue(forElem));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS, forElem.getNamespaceURI());
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(forElem, "bpws"));
  }
  
  public void testWaitUntil() throws Exception {
    String xml = "<wait until='u'/>";
    Element wait = transform(xml);
    Element until = XmlUtil.getElement(wait, BpelConstants.NS_BPWS, "until");
    assertEquals("u", XmlUtil.getStringValue(until));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS, until.getNamespaceURI());
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(until, "bpws"));
  }
  
  public void testWhileCondition_noPrefix() throws Exception {
    String xml = "<while condition='c'><empty/></while>";
    Element whileElem = transform(xml);
    Element condition = XmlUtil.getElement(whileElem, BpelConstants.NS_BPWS, "condition");
    assertNotNull(condition);
    assertEquals("c", XmlUtil.getStringValue(condition));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(condition, "bpws"));
  }
  
  public void testWhileCondition_bpelPrefix() throws Exception {
    String xml = "<bpws:while condition='c'><bpws:empty/></bpws:while>";
    Element whileElem = transform(xml);
    Element condition = XmlUtil.getElement(whileElem, BpelConstants.NS_BPWS, "condition");
    assertNotNull(condition);
    assertEquals("c", XmlUtil.getStringValue(condition));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(condition, "bpws"));
  }
  
  public void testIf_noPrefix() throws Exception {
    String xml = "<switch><case condition='c'><empty name='a'/></case></switch>";
    Element ifElem = transform(xml);
    // condition
    Element condition = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "condition");
    assertEquals("c", XmlUtil.getStringValue(condition));
    // activity
    Element activity = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "empty");
    assertEquals("a", activity.getAttribute("name"));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(condition, "bpws"));
  }
  
  public void testIf_bpelPrefix() throws Exception {
    String xml = "<bpws:switch><bpws:case condition='c'><bpws:empty name='a'/></bpws:case></bpws:switch>";
    Element ifElem = transform(xml);
    // condition
    Element condition = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "condition");
    assertEquals("c", XmlUtil.getStringValue(condition));
    // activity
    Element activity = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "empty");
    assertEquals("a", activity.getAttribute("name"));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(condition, "bpws"));
  }
  
  public void testIf_elseif() throws Exception {
    String xml = 
      "<switch>" +
      " <case condition='c1'><empty name='a1'/></case>" +
      " <case condition='c2'><empty name='a2'/></case>" +
      "</switch>";
    Element ifElem = transform(xml);
    // first branch
    // condition
    Element condition = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "condition");
    assertEquals("c1", XmlUtil.getStringValue(condition));
    // activity
    Element activity = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "empty");
    assertEquals("a1", activity.getAttribute("name"));   
    // second branch
    Element elseif = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "elseif");
    // condition
    condition = XmlUtil.getElement(elseif, BpelConstants.NS_BPWS, "condition");
    assertEquals("c2", XmlUtil.getStringValue(condition));
    // activity
    activity = XmlUtil.getElement(elseif, BpelConstants.NS_BPWS, "empty");
    assertEquals("a2", activity.getAttribute("name"));
    // default branch
    Element elseElem = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "else");
    assertNull(elseElem);
  }
  
  public void testIf_else() throws Exception {
    String xml = 
      "<switch>" +
      " <case condition='c'><empty name='a1'/></case>" +
      " <otherwise><empty name='a2'/></otherwise>" +
      "</switch>";
    Element ifElem = transform(xml);
    // first branch
    // condition
    Element condition = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "condition");
    assertEquals("c", XmlUtil.getStringValue(condition));
    // activity
    Element activity = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "empty");
    assertEquals("a1", activity.getAttribute("name"));
    // default branch
    Element elseElem = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "else");
    activity = XmlUtil.getElement(elseElem, BpelConstants.NS_BPWS, "empty");
    assertEquals("a2", activity.getAttribute("name"));
  }
  
  public void testIf_elseif_else() throws Exception {
    String xml = 
      "<switch>" +
      " <case condition='c1'><empty name='a1'/></case>" +
      " <case condition='c2'><empty name='a2'/></case>" +
      " <otherwise><empty name='a3'/></otherwise>" +
      "</switch>";
    Element ifElem = transform(xml);
    // first branch
    // condition
    Element condition = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "condition");
    assertEquals("c1", XmlUtil.getStringValue(condition));
    // activity
    Element activity = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "empty");
    assertEquals("a1", activity.getAttribute("name"));   
    // second branch
    Element elseif = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "elseif");
    // condition
    condition = XmlUtil.getElement(elseif, BpelConstants.NS_BPWS, "condition");
    assertEquals("c2", XmlUtil.getStringValue(condition));
    // activity
    activity = XmlUtil.getElement(elseif, BpelConstants.NS_BPWS, "empty");
    assertEquals("a2", activity.getAttribute("name"));
    // default branch
    Element elseElem = XmlUtil.getElement(ifElem, BpelConstants.NS_BPWS, "else");
    activity = XmlUtil.getElement(elseElem, BpelConstants.NS_BPWS, "empty");
    assertEquals("a3", activity.getAttribute("name"));
  }

  /////////////////////// Copy
  
  public void testFromExpression_noPrefix() throws Exception {
    String xml = "<from expression=\"bpws:getVariableData('price')*0.15\" />";
    Element from = transform(xml);
    // expression
    assertEquals("bpws:getVariableData('price')*0.15", XmlUtil.getStringValue(from));
    assertNull(XmlUtil.getAttribute(from, "expression"));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS, from.getNamespaceURI());
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(from, "bpws"));
  } 
  
  public void testFromExpression_bpelPrefix() throws Exception {
    String xml = "<bpws:from expression=\"bpws:getVariableData('price')*0.15\" />";
    Element from = transform(xml);
    // expression
    assertEquals("bpws:getVariableData('price')*0.15", XmlUtil.getStringValue(from));
    assertNull(XmlUtil.getAttribute(from, "expression"));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS, from.getNamespaceURI());
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(from, "bpws"));
  }
  
  public void testFromVariable() throws Exception {
    String xml = "<from variable='v'/>";
    Element from = transform(xml);
    assertEquals("v", from.getAttribute("variable"));       
  }   
  
  public void testFromVariablePart() throws Exception {
    String xml = "<from variable='v' part='p'/>";
    Element from = transform(xml);
    assertEquals("v", from.getAttribute("variable"));
    assertEquals("p", from.getAttribute("part"));
  }    
  
  public void testFromVariableQuery() throws Exception {
    String xml = "<from variable='v' part='p' query='/p/q'/>";
    Element from = transform(xml);
    assertEquals("v", from.getAttribute("variable"));
    assertEquals("p", from.getAttribute("part"));
    assertEquals("/p/q", from.getAttribute("query"));
  }  
  
  
  // test for BPEL-167: literal values containing embedded text fail to deploy
  public void testFromLiteral_element() throws Exception {
    String xml = 
      "<from xmlns:pur='http://www.manufacture.com'>\n" +
      " <pur:order name='o1' pur:ref='r1'>\n" +
      "  <amount xmlns=''>10</amount>\n" +
      "  <pur:item>metal</pur:item>\n" +
      "  only the best metal!!" +
      " </pur:order>" +
      "</from>";
    Element from = transform(xml);
    assertEquals("http://www.manufacture.com", DOMUtils.getNamespaceURIFromPrefix(from, "pur"));
    Element literal = XmlUtil.getElement(from, BpelConstants.NS_BPWS, "literal");
    Element order = XmlUtil.getElement(literal, "http://www.manufacture.com", "order");
    // local attribute
    assertEquals("o1", order.getAttribute("name"));
    // qualified attribute
    assertEquals("r1", order.getAttributeNS("http://www.manufacture.com", "ref"));
    // local child element
    assertEquals("10", XmlUtil.getStringValue(XmlUtil.getElement(order, "amount")));
    // qualified child element
    assertEquals("metal", XmlUtil.getStringValue(XmlUtil.getElement(order, "http://www.manufacture.com", "item")));
    // child text node
    assertEquals("\n  only the best metal!! ", order.getChildNodes().item(4).getNodeValue());
  }
  
  public void testFromLiteral_text() throws Exception {
    String xml = 
      "<from>\n" +
      " free text!!!\n" +
      "</from>";
    Element from = transform(xml);
    Element literal = XmlUtil.getElement(from, BpelConstants.NS_BPWS, "literal");
    assertEquals("\n free text!!!\n", XmlUtil.getStringValue(literal));    
  }
  
  public void testToVariable() throws Exception {
    String xml = "<to variable='v'/>";
    Element to = transform(xml);
    assertEquals("v", to.getAttribute("variable"));
  }      
  
  public void testToVariablePart() throws Exception {
    String xml = "<to variable='v' part='p'/>";
    Element to = transform(xml);
    assertEquals("v", to.getAttribute("variable"));
    assertEquals("p", to.getAttribute("part"));
  }    
  
  public void testToVariableQuery() throws Exception {
    String xml = "<to variable='v' part='p' query='/p/q'/>";
    Element to = transform(xml);
    assertEquals("v", to.getAttribute("variable"));
    assertEquals("p", to.getAttribute("part"));
    assertEquals("/p/q", to.getAttribute("query"));
  }
  
  /////////////////////// Extensions
  
  public void testExtensionElement() throws Exception {
    String xml ="<empty><jbpm:extension xmlns:jbpm='http://jbpm.org/bpel'><jbpm:extensionElement/></jbpm:extension></empty>";
    Element activity = transform(xml);
    Element extension = XmlUtil.getElement(activity, BpelConstants.NS_VENDOR, "extension");
    assertNotNull(extension);
  }  
  
  public void testExtensionAttribute() throws Exception {
    String xml ="<empty xmlns:jbpm='http://jbpm.org/bpel' jbpm:extension='bla'/>";
    Element activity = transform(xml);
    assertNotNull(activity.getAttributeNS(BpelConstants.NS_VENDOR, "extension"));
  }
  
  /////////////////////// Activity source and targets
  
  public void testSources_noPrefix() throws Exception {
    String xml = "<empty><source linkName='s1' transitionCondition='tc'/><source linkName='s2'/></empty>";
    Element oldVersion = transform(xml);
    Element sources = XmlUtil.getElement(oldVersion, BpelConstants.NS_BPWS, "sources");
    assertNotNull(sources);
    NodeList sourceList = sources.getElementsByTagNameNS(BpelConstants.NS_BPWS, "source");
    assertEquals(2, sourceList.getLength());
    Element transitionCondition = XmlUtil.getElement(sourceList.item(0), BpelConstants.NS_BPWS, "transitionCondition");
    assertNotNull(transitionCondition);
    assertEquals("tc", XmlUtil.getStringValue(transitionCondition));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS, transitionCondition.getNamespaceURI());
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(transitionCondition, "bpws"));
  }
  
  public void testSources_bpelPrefix() throws Exception {
    String xml = 
      "<bpws:empty>" +
      " <bpws:source linkName='s1' transitionCondition='tc'/>" +
      " <bpws:source linkName='s2'/>" +
      "</bpws:empty>";
    Element oldVersion = transform(xml);
    Element sources = XmlUtil.getElement(oldVersion, BpelConstants.NS_BPWS, "sources");
    assertNotNull(sources);
    NodeList sourceList = sources.getElementsByTagNameNS(BpelConstants.NS_BPWS, "source");
    assertEquals(2, sourceList.getLength());
    Element transitionCondition = XmlUtil.getElement(sourceList.item(0), BpelConstants.NS_BPWS, "transitionCondition");
    assertNotNull(transitionCondition);
    assertEquals("tc", XmlUtil.getStringValue(transitionCondition));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS, transitionCondition.getNamespaceURI());
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(transitionCondition, "bpws"));
  }
 
  public void testTargets_noPrefix() throws Exception {
    String xml = "<empty joinCondition='jc'><target linkName='t1'/><target linkName='t2'/></empty>";
    Element oldVersion = transform(xml);
    Element targets = XmlUtil.getElement(oldVersion, BpelConstants.NS_BPWS, "targets");
    assertNotNull(targets);
    assertEquals(2, targets.getElementsByTagNameNS(BpelConstants.NS_BPWS, "target").getLength());
    Element joinCondition = XmlUtil.getElement(targets, BpelConstants.NS_BPWS, "joinCondition");
    assertNotNull(joinCondition);
    assertEquals("jc", XmlUtil.getStringValue(joinCondition));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS, joinCondition.getNamespaceURI());
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(joinCondition, "bpws"));
  }
  
  public void testTargets_bpelPrefix() throws Exception {
    String xml = 
      "<bpws:empty joinCondition='jc'>" +
      " <bpws:target linkName='t1'/>" +
      " <bpws:target linkName='t2'/>" +
      "</bpws:empty>";
    Element oldVersion = transform(xml);
    Element targets = XmlUtil.getElement(oldVersion, BpelConstants.NS_BPWS, "targets");
    assertNotNull(targets);
    assertEquals(2, targets.getElementsByTagNameNS(BpelConstants.NS_BPWS, "target").getLength());
    Element joinCondition = XmlUtil.getElement(targets, BpelConstants.NS_BPWS, "joinCondition");
    assertNotNull(joinCondition);
    assertEquals("jc", XmlUtil.getStringValue(joinCondition));
    // namespaces
    assertEquals(BpelConstants.NS_BPWS, joinCondition.getNamespaceURI());
    assertEquals(BpelConstants.NS_BPWS_1_1, DOMUtils.getNamespaceURIFromPrefix(joinCondition, "bpws"));
  }
  
  public static Element transform(String xmlText) throws TransformerException, SAXException {
    String wrappedText = 
      "<parent xmlns='"  + BpelConstants.NS_BPWS_1_1  + 
      "' xmlns:bpws='" + BpelConstants.NS_BPWS_1_1 + "'>" +
      xmlText + 
      "</parent>";
    return XmlUtil.getElement(transformNoWrap(wrappedText));
  }
  
  public static Element transformNoWrap(String xmlText) throws TransformerException, SAXException {
    StringWriter sink = new StringWriter();
    BpelReader.getBpelUpdateTemplates().newTransformer().transform(new StreamSource(new StringReader(xmlText)), new StreamResult(sink));
    String textResult = sink.toString();
    System.out.println(textResult);
    return XmlUtil.parseElement(textResult);
  }
}
