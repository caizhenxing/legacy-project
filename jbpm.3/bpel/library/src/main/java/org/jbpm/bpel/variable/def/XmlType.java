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
package org.jbpm.bpel.variable.def;

import javax.xml.namespace.QName;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.DatatypeUtil;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class XmlType extends VariableType {

  protected QName name;

  protected XmlType() {
  }

  /** {@inheritDoc} */
  public QName getName() {
    return name;
  }

  public void setName(QName name) {
    this.name = name;
  }

  public Object createValue(VariableDefinition definition) {
    // create an element value
    Element value = createElement(definition);
    // mark as uninitialized with xsi:nil
    value.setAttributeNS(BpelConstants.NS_XML_SCHEMA_INSTANCE, "xsi:"
        + BpelConstants.ATTR_NIL, "true");
    // some TrAX implementations do not perform namespace fixup, declare xsi
    // namespace
    XmlUtil.addNamespaceDeclaration(value,
        BpelConstants.NS_XML_SCHEMA_INSTANCE,
        "xsi");
    return value;
  }

  protected abstract Element createElement(VariableDefinition definition);

  public boolean isInitialized(Object variableValue) {
    Element elementValue = (Element) variableValue;
    // check for xsi:nil
    Attr nilAttr = elementValue.getAttributeNodeNS(BpelConstants.NS_XML_SCHEMA_INSTANCE,
        BpelConstants.ATTR_NIL);
    if (nilAttr != null) {
      if (DatatypeUtil.parseBoolean(nilAttr.getValue()) == Boolean.TRUE &&
      // check for child nodes or other attributes
          !elementValue.hasChildNodes()
          && !hasAttributesOtherThanNil(elementValue)) {
        return false;
      }
      // consider xsi:nil is bogus
      elementValue.removeAttributeNode(nilAttr);
    }
    return true;
  }

  private static boolean hasAttributesOtherThanNil(Element elem) {
    boolean result;
    NamedNodeMap attributeMap = elem.getAttributes();
    switch (attributeMap.getLength()) {
    case 0: // cannot happen, presence of xsi:nil already confirmed
      throw new AssertionError(attributeMap.getLength() > 0);
    case 1: // must be xsi:nil
      result = false;
      break;
    case 2: // second attribute might be xsi namespace declaration, which
            // doesn't count
      result = !elem.hasAttributeNS(BpelConstants.NS_XMLNS,
          XmlUtil.getPrefix(BpelConstants.NS_XML_SCHEMA_INSTANCE, elem));
      break;
    default:
      result = true;
    }
    return result;
  }

  public void setValue(Object currentValue, Object newValue) {
    XmlUtil.setObjectValue((Element) currentValue, newValue);
  }

  protected Object evaluateProperty(PropertyAlias propertyAlias,
      Object variableValue) {
    Query query = propertyAlias.getQuery();
    return query != null ?
    // evaluate the query using the variable value as context node
        query.getEvaluator().evaluate((Element) variableValue)
        // assume this is a simple type or an element with simple content
        : variableValue;
  }

  protected void assignProperty(PropertyAlias propertyAlias,
      Object variableValue, Object propertyValue) {
    Query query = propertyAlias.getQuery();
    Element elementValue = (Element) variableValue;
    if (query == null) {
      // assign the variable itself; assume this is a simple type or an element
      // with simple content
      XmlUtil.setObjectValue(elementValue, propertyValue);
    }
    else {
      // assign the query location using the variable value as context node
      query.getEvaluator().assign(elementValue, propertyValue);
    }
  }
}
