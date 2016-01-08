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
package org.jbpm.bpel.variable.def;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.ClassUtils;
import org.w3c.dom.Node;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class VariableType implements Serializable {

  long id;
  private Map propertyAliases;

  protected VariableType() {
  }

  public abstract QName getName();

  public Map getPropertyAliases() {
    return propertyAliases;
  }

  public void setPropertyAliases(Map propertyAliases) {
    this.propertyAliases = propertyAliases;
  }

  public void addPropertyAlias(PropertyAlias alias) {
    if (propertyAliases == null) {
      propertyAliases = new HashMap();
    }
    propertyAliases.put(alias.getProperty().getQName(), alias);
  }

  public PropertyAlias getPropertyAlias(QName propertyName) {
    return propertyAliases != null ? (PropertyAlias) propertyAliases.get(propertyName)
        : null;
  }

  public abstract Object createValue(VariableDefinition definition);

  public abstract boolean isInitialized(Object variableValue);

  public abstract void setValue(Object currentValue, Object newValue);

  public Object getPropertyValue(QName propertyName, Object variableValue) {
    PropertyAlias alias = getPropertyAlias(propertyName);
    if (alias == null) {
      throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
    }
    Object result = evaluateProperty(alias, variableValue);
    return result instanceof Node ? XmlUtil.getStringValue((Node) result)
        : result;
  }

  public void setPropertyValue(QName propertyName, Object variableValue,
      Object propertyValue) {
    assignProperty(getPropertyAlias(propertyName), variableValue, propertyValue);
  }

  protected abstract Object evaluateProperty(PropertyAlias propertyAlias,
      Object variableValue);

  protected abstract void assignProperty(PropertyAlias propertyAlias,
      Object variableValue, Object propertyValue);

  public String toString() {
    return ClassUtils.getShortClassName(getClass()) + "(id=" + id + ")";
  }
}
