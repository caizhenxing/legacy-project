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

import java.util.Iterator;
import java.util.List;

import javax.wsdl.Binding;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;

import com.ibm.wsdl.extensions.soap.SOAPConstants;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.bpel.integration.catalog.ServiceCatalog;
import org.jbpm.bpel.wsdl.util.WsdlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:11 $
 */
public abstract class SoapEndpointReference extends EndpointReference {

  private String address;
  private QName serviceName;
  private String portName;

  private static final Log log = LogFactory.getLog(SoapEndpointReference.class);

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  public void setServiceName(QName serviceName) {
    this.serviceName = serviceName;
  }

  public QName getServiceName() {
    return serviceName;
  }

  public void setPortName(String portName) {
    this.portName = portName;
  }

  public String getPortName() {
    return portName;
  }

  /** {@inheritDoc} */
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this);
    // address; required
    builder.append("address", address);
    // scheme
    String scheme = getScheme();
    if (scheme != null) builder.append("scheme", scheme);
    // port type
    QName portTypeName = getPortTypeName();
    if (portTypeName != null) builder.append("portType", portTypeName);
    // service
    if (serviceName != null) {
      builder.append("service", serviceName);
      // port; meaningful only with a service name
      if (portName != null) builder.append("port", portName);
    }
    return builder.toString();
  }

  public Port selectPort(ServiceCatalog catalog) {
    Port port;
    if (serviceName == null) {
      List services = catalog.lookupServices(getPortTypeName());
      port = selectPort(services);
    }
    else {
      Service service = catalog.lookupService(serviceName);
      port = selectPort(service);
    }
    return port;
  }

  protected Port selectPort(List services) {
    QName portTypeName = getPortTypeName();
    Port selectedPort = null;
    // iterate the available services
    Iterator serviceIt = services.iterator();
    serviceLoop: while (serviceIt.hasNext()) {
      Service service = (Service) serviceIt.next();
      // iterate the available ports
      Iterator portIt = service.getPorts().values().iterator();
      while (portIt.hasNext()) {
        Port port = (Port) portIt.next();
        Binding binding = port.getBinding();
        /***********************************************************************
         * does this port implement the required type *and uses a soap binding?
         */
        if (binding.getPortType().getQName().equals(portTypeName)
            && isSoapBinding(binding)) {
          String portAddress = getSoapAddress(port);
          log.debug("found candidate port: name=" + port.getName()
              + ", address=" + portAddress + ", service=" + service.getQName()
              + ", portType=" + portTypeName);
          // does the port address match the reference address?
          if (address != null && address.equals(portAddress)) {
            // exact match, use the port and stop the search
            selectedPort = port;
            break serviceLoop;
          }
          else if (selectedPort == null) {
            // non-exact match, use the port if no other candidate exists
            selectedPort = port;
          }
        }
      }
    }
    if (selectedPort == null) {
      throw new RuntimeException("no port implements the required port type: "
          + "portType=" + portTypeName);
    }
    return selectedPort;
  }

  protected Port selectPort(Service service) {
    if (service == null) {
      throw new RuntimeException("service not found: service=" + serviceName);
    }
    QName portTypeName = getPortTypeName();
    Port port;
    if (portName != null) {
      // the reference designates a specific port, go for it
      port = service.getPort(portName);
      if (port == null) {
        throw new RuntimeException("port not found: service=" + serviceName
            + ", port=" + portName);
      }
      Binding binding = port.getBinding();
      if (!portTypeName.equals(binding.getPortType().getQName())) {
        throw new RuntimeException(
            "port does not implement the required port type: " + "service="
                + serviceName + ", port=" + portName + ", portType="
                + portTypeName);
      }
      // does this port use a soap binding?
      if (!isSoapBinding(binding)) {
        throw new RuntimeException("non-soap ports not supported: "
            + "service=" + serviceName + ", port=" + portName + ", portType="
            + portTypeName);
      }
    }
    else {
      port = null;
      // iterate the available ports
      Iterator portIt = service.getPorts().values().iterator();
      while (portIt.hasNext()) {
        Port aPort = (Port) portIt.next();
        Binding binding = aPort.getBinding();
        /***********************************************************************
         * does this port implement the required port type *and uses a soap
         * binding?
         */
        if (binding.getPortType().getQName().equals(portTypeName)
            && isSoapBinding(binding)) {
          // does the port's address match the referenced address?
          if (address != null && address.equals(getSoapAddress(aPort))) {
            // exact match, use the port and stop the search
            port = aPort;
            break;
          }
          else if (port == null) {
            // non-exact match, use the port if no other candidate exists
            port = aPort;
          }
        }
      }
      if (port == null) {
        throw new RuntimeException(
            "no port implements the required port type and "
                + "uses a soap binding: service=" + serviceName + ", portType="
                + portTypeName);
      }
    }
    return port;
  }

  protected static boolean isSoapBinding(Binding binding) {
    return WsdlUtil.getExtension(binding.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_BINDING) != null;
  }

  protected static String getSoapAddress(Port port) {
    SOAPAddress soapAddress = (SOAPAddress) WsdlUtil.getExtension(port.getExtensibilityElements(),
        SOAPConstants.Q_ELEM_SOAP_ADDRESS);
    return soapAddress != null ? soapAddress.getLocationURI() : null;
  }
}
