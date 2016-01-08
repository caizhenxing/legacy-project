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

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:16 $
 */
public class WsaConstants {

  public static final String NS_ADDRESSING = "http://schemas.xmlsoap.org/ws/2004/08/addressing";

  public static final String ELEM_ENDPOINT_REFERENCE = "EndpointReference";
  public static final String ELEM_ADDRESS = "Address";
  public static final String ELEM_PORT_TYPE = "PortType";
  public static final String ELEM_SERVICE_NAME = "ServiceName";

  public static final String ATTR_PORT_NAME = "PortName";

  // suppress default constructor, ensuring non-instantiability
  private WsaConstants() {
  }
}
