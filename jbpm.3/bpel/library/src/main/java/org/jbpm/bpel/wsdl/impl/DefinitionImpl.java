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
package org.jbpm.bpel.wsdl.impl;

import javax.wsdl.*;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class DefinitionImpl extends com.ibm.wsdl.DefinitionImpl {

  private static final long serialVersionUID = 1L;

  public Message createMessage() {
    return new MessageImpl();
  }

  public Part createPart() {
    return new PartImpl();
  }

  public PortType createPortType() {
    return new PortTypeImpl();
  }

  public Operation createOperation() {
    return new OperationImpl();
  }

  public Input createInput() {
    return new InputImpl();
  }

  public Output createOutput() {
    return new OutputImpl();
  }

  public Fault createFault() {
    return new FaultImpl();
  }
}
