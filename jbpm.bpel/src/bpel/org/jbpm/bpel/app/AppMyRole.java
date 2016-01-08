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
package org.jbpm.bpel.app;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/08/29 23:02:43 $
 */
public class AppMyRole {

  private String handle;

  public String getHandle() {
    return handle;
  }

  public void setHandle(String portInfoName) {
    this.handle = portInfoName;
  }
}
