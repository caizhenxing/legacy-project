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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.Scope;
import org.jbpm.graph.def.NodeCollection;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ScopeMatcher implements AppDescriptorVisitor {

  private Map scopeConfigurations;
  private Scope parent;

  /**
   * Matches a bpel definition with the given configuration
   * @param definition the bpel definition
   * @param configuration the bpel configuration to use
   * @return the resulting match map of scope definitions with their
   *         corresponding configuration
   */
  public Map match(BpelDefinition definition, AppDescriptor configuration) {
    scopeConfigurations = new HashMap();
    this.parent = definition.getGlobalScope();
    visit(configuration);
    return scopeConfigurations;
  }

  public void visit(AppDescriptor appDescriptor) {
    scopeConfigurations.put(parent, appDescriptor);
    propagate(appDescriptor);
  }

  public void visit(AppScope appScope) {
    Scope scope = findScope(appScope.getName(), parent);
    // TODO if not found throw error?
    if (scope != null) {
      scopeConfigurations.put(scope, appScope);

      Scope previousParent = parent;
      parent = scope;
      propagate(appScope);
      parent = previousParent;
    }
  }

  public void visit(AppPartnerLink appPartnerLink) {
  }

  public void propagate(AppScope config) {
    Iterator configIt = config.getScopes().iterator();

    while (configIt.hasNext()) {
      AppScope scopeConfig = (AppScope) configIt.next();
      scopeConfig.accept(this);
    }
  }

  private Scope findScope(String configName, NodeCollection parent) {
    Iterator nodesIt = parent.getNodes().iterator();

    Scope matchingScope = null;

    while (nodesIt.hasNext()) {
      Activity activity = (Activity) nodesIt.next();

      if (!(activity instanceof CompositeActivity)) continue;

      if (activity instanceof Scope
          && (configName == null && activity.getName() == null)
          || (configName != null && configName.equals(activity.getName()))) {
        if (matchingScope != null)
          throw new RuntimeException("conflicting name");
        matchingScope = (Scope) activity;
      }

      Scope scope = findScope(configName, ((NodeCollection) activity));
      if (scope != null) {
        if (matchingScope != null)
          throw new RuntimeException("conflicting name");
        matchingScope = scope;
      }
    }

    return matchingScope;
  }
}
