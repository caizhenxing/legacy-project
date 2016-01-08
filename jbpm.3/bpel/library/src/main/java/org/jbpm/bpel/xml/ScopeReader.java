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
package org.jbpm.bpel.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.Scope;

/**
 * @author Juan Cantú
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class ScopeReader extends ActivityReader {

  protected Activity createActivity() {
    return new Scope();
  }

  protected void readSpecificProperties(Element scopeElement, Activity activity) {
    Scope scope = (Scope) activity;

    Attr isolatedAttr = scopeElement.getAttributeNode(BpelConstants.ATTR_ISOLATED);
    boolean isolated = bpelReader.readTBoolean(isolatedAttr, Boolean.FALSE)
        .booleanValue();
    scope.setIsolated(isolated);
    bpelReader.readScope(scopeElement, scope);
  }
}
