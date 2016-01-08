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

import java.util.*;

import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.NodeCollection;
import org.jbpm.graph.def.ProcessDefinition;

import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.variable.def.VariableDefinition;

/**
 * brings hierarchy into the activities of a bpel definition by creating a
 * parent-child relation between {@link Activity}s
 * @author Juan Cantú
 * @version $Revision: 1.14 $ $Date: 2006/08/22 04:13:10 $
 */
public abstract class CompositeActivity extends Activity implements
    NodeCollection {

  protected transient Map nodesMap = null;

  public CompositeActivity() {
  }

  public CompositeActivity(String name) {
    super(name);
  }

  public boolean isChildInitial(Activity activity) {
    return false;
  }

  // definition retrieval methods
  // //////////////////////////////////////////////////////////////////////

  public VariableDefinition findVariable(String varName) {
    CompositeActivity parent = getCompositeActivity();
    return parent != null ? parent.findVariable(varName) : null;
  }

  public CorrelationSetDefinition findCorrelationSet(String csName) {
    CompositeActivity parent = getCompositeActivity();
    return parent != null ? parent.findCorrelationSet(csName) : null;
  }

  public PartnerLinkDefinition findPartnerLink(String plinkName) {
    CompositeActivity parent = getCompositeActivity();
    return parent != null ? parent.findPartnerLink(plinkName) : null;
  }

  public LinkDefinition findLink(String linkName) {
    CompositeActivity parent = getCompositeActivity();
    return parent != null ? parent.findLink(linkName) : null;
  }

  // children management
  // /////////////////////////////////////////////////////////////////////////////

  public Activity findActivity(String childName, Class clazz) {
    // this routine doesn't search inside a scope event, fault, termination or
    // compensation handlers
    Activity activity = (Activity) getNode(childName);
    activity = (activity != null && (clazz == null || clazz.isAssignableFrom(activity.getClass()))) ? activity
        : null;
    if (activity == null) {
      for (Iterator it = getNodes().iterator(); it.hasNext();) {
        Object child = it.next();
        if (child instanceof CompositeActivity) {
          activity = ((CompositeActivity) child).findActivity(childName, clazz);
          if (activity != null) break;
        }
      }
    }
    return activity;
  }

  /** {@inheritDoc} */
  public Map getNodesMap() {
    List nodes = getNodes();
    if ((nodesMap == null) && (nodes != null)) {
      nodesMap = new HashMap();
      Iterator iter = nodes.iterator();
      while (iter.hasNext()) {
        Node node = (Node) iter.next();
        nodesMap.put(node.getName(), node);
      }
    }
    return nodesMap;
  }

  /** {@inheritDoc} */
  public String generateNodeName() {
    return ProcessDefinition.generateNodeName(getNodes());
  }

  /** {@inheritDoc} */
  public Node findNode(String hierarchicalName) {
    return ProcessDefinition.findNode(this, hierarchicalName);
  }

  /** {@inheritDoc} */
  public Node getNode(String name) {
    return (Node) getNodesMap().get(name);
  }

  /** {@inheritDoc} */
  public boolean hasNode(String name) {
    return getNodesMap().containsKey(name);
  }
}