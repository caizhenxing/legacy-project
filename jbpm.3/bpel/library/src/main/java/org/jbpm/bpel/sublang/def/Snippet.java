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
package org.jbpm.bpel.sublang.def;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jbpm.bpel.def.Namespace;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
abstract class Snippet implements Serializable {

  long id;
  private String text;
  private String language;
  private Set namespaces;

  private static final long serialVersionUID = 1L;

  protected Snippet() {
  }

  /**
   * Gets the lexical representation of this expression.
   * @return the text form
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the lexical representation of this expression.
   * @param text the text form
   */
  public void setText(String text) {
    this.text = text;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Set getNamespaces() {
    return namespaces;
  }

  public void setNamespaces(Set namespaces) {
    this.namespaces = namespaces;
  }

  public void setNamespaces(Map namespaceMap) {
    HashSet namespaces = new HashSet();
    Iterator namespaceEntryIt = namespaceMap.entrySet().iterator();
    while (namespaceEntryIt.hasNext()) {
      Entry namespaceEntry = (Entry) namespaceEntryIt.next();
      namespaces.add(new Namespace((String) namespaceEntry.getKey(),
          (String) namespaceEntry.getValue()));
    }
    setNamespaces(namespaces);
  }
}