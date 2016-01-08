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
package org.jbpm.bpel.variable.exe;

import org.w3c.dom.Element;

import org.jbpm.context.exe.JbpmTypeMatcher;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:16 $
 */
public class ElementMatcher implements JbpmTypeMatcher {

  private static final long serialVersionUID = 1L;

  /** {@inheritDoc} */
  public boolean matches(Class valueClass) {
    return Element.class.isAssignableFrom(valueClass);
  }
}
