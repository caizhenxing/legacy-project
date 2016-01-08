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
package org.jbpm.bpel.integration.exe.wsa;

import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.jbpm.bpel.integration.exe.SoapEndpointReference;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:06:10 $
 */
public class WsaEndpointReference extends SoapEndpointReference {

  /** {@inheritDoc} */
  protected void readEndpointRef(Element endpointRefElem) {
    // address
    Element addressElem = XmlUtil.getElement(endpointRefElem,
        WsaConstants.NS_ADDRESSING,
        WsaConstants.ELEM_ADDRESS);
    setAddress((String) XmlUtil.getStringValue(addressElem));
    // port type
    Element portTypeElem = XmlUtil.getElement(endpointRefElem,
        WsaConstants.NS_ADDRESSING,
        WsaConstants.ELEM_PORT_TYPE);
    if (portTypeElem != null) {
      String portTypeValue = (String) XmlUtil.getStringValue(portTypeElem);
      setPortTypeName(XmlUtil.getQName(portTypeValue, portTypeElem));
    }
    // service name
    Element serviceElem = XmlUtil.getElement(endpointRefElem,
        WsaConstants.NS_ADDRESSING,
        WsaConstants.ELEM_SERVICE_NAME);
    if (serviceElem != null) {
      String serviceValue = (String) XmlUtil.getStringValue(serviceElem);
      setServiceName(XmlUtil.getQName(serviceValue, serviceElem));
      setPortName(XmlUtil.getAttribute(serviceElem, WsaConstants.ATTR_PORT_NAME));
    }
  }

  /** {@inheritDoc} */
  protected Element writeEndpointRef(Document nodeFactory) {
    Element endpointRefElem = nodeFactory.createElementNS(WsaConstants.NS_ADDRESSING,
        WsaConstants.ELEM_ENDPOINT_REFERENCE);
    // namespace declaration
    XmlUtil.addNamespaceDeclaration(endpointRefElem, WsaConstants.NS_ADDRESSING);
    // address
    Element addressElem = nodeFactory.createElementNS(WsaConstants.NS_ADDRESSING,
        WsaConstants.ELEM_ADDRESS);
    endpointRefElem.appendChild(addressElem);
    XmlUtil.setStringValue(addressElem, getAddress());
    // port type
    QName portTypeName = getPortTypeName();
    if (portTypeName != null) {
      Element portTypeElem = nodeFactory.createElementNS(WsaConstants.NS_ADDRESSING,
          WsaConstants.ELEM_PORT_TYPE);
      endpointRefElem.appendChild(portTypeElem);
      XmlUtil.setQNameValue(portTypeElem, portTypeName);
    }
    // service name
    QName serviceName = getServiceName();
    if (serviceName != null) {
      Element serviceElem = nodeFactory.createElementNS(WsaConstants.NS_ADDRESSING,
          WsaConstants.ELEM_SERVICE_NAME);
      endpointRefElem.appendChild(serviceElem);
      XmlUtil.setQNameValue(serviceElem, serviceName);
      // port name
      String portName = getPortName();
      if (portName != null) {
        serviceElem.setAttribute(WsaConstants.ATTR_PORT_NAME, portName);
      }
    }
    return endpointRefElem;
  }
}