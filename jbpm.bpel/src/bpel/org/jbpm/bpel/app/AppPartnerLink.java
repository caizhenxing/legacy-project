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
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class AppPartnerLink {

  private String name;
  private AppPartnerRole partnerRole;
  private AppMyRole myRole;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AppMyRole getMyRole() {
    return myRole;
  }

  public void setMyRole(AppMyRole myRole) {
    this.myRole = myRole;
  }

  public AppPartnerRole getPartnerRole() {
    return partnerRole;
  }

  public void setPartnerRole(AppPartnerRole partnerRole) {
    this.partnerRole = partnerRole;
  }

  public void accept(AppDescriptorVisitor visitor) {
    visitor.visit(this);
  }
}
