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
package org.jbpm.bpel.def;

import java.io.Serializable;

/**
 * Binding between a namespace uri and a specific prefix.
 * @author Alejandro Guizar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class Namespace implements Serializable {

  long id;
  private String uri;
  private String prefix;

  private static final long serialVersionUID = 1L;

  Namespace() {
  }

  public Namespace(String prefix, String uri) {
    this.uri = uri;
    this.prefix = prefix;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getURI() {
    return uri;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof Namespace)) return false;
    Namespace that = (Namespace) obj;
    return uri.equals(that.uri) && prefix.equals(that.prefix);
  }

  public int hashCode() {
    return uri.hashCode() ^ prefix.hashCode();
  }

  public String toString() {
    return "Namespace(uri=" + uri + ", prefix=" + prefix + ")";
  }
}
