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
package org.jbpm.bpel.integration.exe;

import javax.wsdl.Port;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.wsdl.util.xml.QNameUtils;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.integration.catalog.ServiceCatalog;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class EndpointReference {

  long id;

  private String scheme;
  private QName portTypeName;

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public QName getPortTypeName() {
    return portTypeName;
  }

  public void setPortTypeName(QName portTypeName) {
    this.portTypeName = portTypeName;
  }

  public static EndpointReference readServiceRef(Element referenceElem) {
    String scheme;
    Element endpointRefElem;
    // is the given element a service reference container?
    if (BpelConstants.NS_BPWS.equals(referenceElem.getNamespaceURI())
        && BpelConstants.ELEM_SERVICE_REF.equals(referenceElem.getLocalName())) {
      // read element following the schema of bpel:service-ref
      scheme = referenceElem.getAttribute(BpelConstants.ATTR_REFERENCE_SCHEME);
      endpointRefElem = XmlUtil.getElement(referenceElem);
    }
    else {
      // assume the given element is the actual endpoint reference value
      scheme = null;
      endpointRefElem = referenceElem;
    }
    // locate a factory that understands this reference
    QName endpointRefName = QNameUtils.newQName(endpointRefElem);
    EndpointReferenceFactory factory = EndpointReferenceFactory.getInstance(endpointRefName,
        scheme);
    if (factory == null) {
      throw new BpelFaultException(BpelConstants.FAULT_UNSUPPORTED_REFERENCE);
    }
    // produce the endpoint reference
    EndpointReference endpointRef = factory.createEndpointReference();
    endpointRef.setScheme(scheme);
    endpointRef.readEndpointRef(endpointRefElem);
    return endpointRef;
  }

  public void writeServiceRef(Element referenceElem) {
    // write the endpoint reference value
    Element endpointRefElem = writeEndpointRef(referenceElem.getOwnerDocument());
    // is the given element a service reference container?
    if (BpelConstants.NS_BPWS.equals(referenceElem.getNamespaceURI())
        && BpelConstants.ELEM_SERVICE_REF.equals(referenceElem.getLocalName())) {
      // clean the container element
      XmlUtil.removeAttributes(referenceElem);
      XmlUtil.removeChildNodes(referenceElem);
      // set reference scheme attribute
      if (!StringUtils.isEmpty(scheme)) {
        referenceElem.setAttribute(BpelConstants.ATTR_REFERENCE_SCHEME, scheme);
      }
      // add endpoint reference child element
      referenceElem.appendChild(endpointRefElem);
    }
    else {
      // copy the reference value directly to the given element
      XmlUtil.copy(referenceElem, endpointRefElem);
    }
  }

  public abstract Port selectPort(ServiceCatalog catalog);

  protected abstract void readEndpointRef(Element endpointRefElem);

  protected abstract Element writeEndpointRef(Document nodeFactory);
}
