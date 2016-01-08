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
package org.jbpm.bpel.integration.jms;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class IntegrationConstants {

  /** JMS property for the partner link identifier, in the request message. */
  public static final String PARTNER_LINK_ID_PROP = "_$partnerLinkId";

  /** JMS property for the operation name, in the request message. */
  public static final String OPERATION_NAME_PROP = "_$operationName";

  /** JMS property for the fault qualified name, in the response message. */
  public static final String FAULT_NAME_PROP = "_$faultName";

  /** JNDI name of the JMS connection factory, relative to {@link #JMS_CONTEXT} */
  public static final String CONNECTION_FACTORY_NAME = "ConnectionFactory";
}
