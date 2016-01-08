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
package org.jbpm.bpel.integration.exe.wsdl;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;

import com.ibm.wsdl.Constants;

import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.bpel.integration.exe.EndpointReferenceFactory;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:11 $
 */
public class WsdlEndpointReferenceFactory extends EndpointReferenceFactory {

  /** {@inheritDoc} */
  public EndpointReference createEndpointReference() {
    return new WsdlEndpointReference();
  }

  /** {@inheritDoc} */
  public boolean acceptsReference(QName endpointRefName, String refScheme) {
    return (StringUtils.isEmpty(refScheme) || refScheme.equals(Constants.NS_URI_WSDL))
        && endpointRefName.equals(Constants.Q_ELEM_SERVICE);
  }
}
