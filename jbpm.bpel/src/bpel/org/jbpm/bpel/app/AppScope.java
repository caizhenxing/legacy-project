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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/22 04:13:10 $
 */
public class AppScope {

  private String name;
  private Map partnerLinks = new HashMap();
  private List scopes = new ArrayList();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map getPartnerLinks() {
    return partnerLinks;
  }

  public void addPartnerLink(AppPartnerLink partnerLink) {
    partnerLinks.put(partnerLink.getName(), partnerLink);
  }

  public List getScopes() {
    return scopes;
  }

  public void addScope(AppScope scope) {
    scopes.add(scope);
  }

  public void accept(AppDescriptorVisitor visitor) {
    visitor.visit(this);
  }
}
