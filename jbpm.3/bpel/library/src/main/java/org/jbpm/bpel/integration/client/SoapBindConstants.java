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

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:06:10 $
 */
public class SoapBindConstants {

  // transports
  public static final String HTTP_TRANSPORT_URI = "http://schemas.xmlsoap.org/soap/http";

  // styles
  public static final String RPC_STYLE = "rpc";
  public static final String DOCUMENT_STYLE = "document";

  // uses
  public static final String LITERAL_USE = "literal";
  public static final String ENCODED_USE = "encoded";

  // action
  public static final String SOAP_ACTION_HEADER = "SOAPAction";

  // suppress default constructor, ensuring non-instantiability
  private SoapBindConstants() {
  }
}
