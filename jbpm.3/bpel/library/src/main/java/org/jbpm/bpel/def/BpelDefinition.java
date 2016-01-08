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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jbpm.JbpmConfiguration;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.graph.def.Event;
import org.jbpm.graph.def.GraphElement;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.module.def.ModuleDefinition;
import org.jbpm.util.ClassLoaderUtil;

/**
 * Represents a BPEL process.
 * @see "WS-BPEL 2.0 &sect;6.2"
 * @author Juan Cantú
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class BpelDefinition extends ProcessDefinition {

  private String targetNamespace;
  private String queryLanguage;
  private String expressionLanguage;
  private boolean abstractProcess;
  private boolean enableInstanceCompensation;

  private String location;
  private Set namespaces = new HashSet();

  private static List moduleClasses = new ArrayList();

  private static final Log log = LogFactory.getLog(BpelDefinition.class);
  private static final long serialVersionUID = 1L;

  public BpelDefinition() {
    this(null);
  }

  public BpelDefinition(String name) {
    super(name);
    initModules();
    addNode(new GlobalScope());
  }

  protected void initModules() {
    // walk through registered module classes
    for (int i = 0, n = moduleClasses.size(); i < n; i++) {
      Class moduleClass = (Class) moduleClasses.get(i);
      try {
        ModuleDefinition module = (ModuleDefinition) moduleClass.newInstance();
        addDefinition(module);
      }
      catch (Exception e) {
        log.warn("could not initialize module: " + moduleClass);
      }
    }
  }

  public ProcessInstance createProcessInstance() {
    ProcessInstance processInstance = super.createProcessInstance();
    Token rootToken = processInstance.getRootToken();
    // the root token receives the global scope data
    getGlobalScope().initScopeData(rootToken);
    /*
     * BPEL-89: fire process start event > ProcessInstance constructor does not
     * fire start event, because BPEL processes do not have a start node >
     * global scope data must be available to event handlers, if any
     */
    processDefinition.fireEvent(Event.EVENTTYPE_PROCESS_START,
        new ExecutionContext(rootToken));
    return processInstance;
  }

  public void messageReceived(Receiver receiver, Token rootToken) {
    // enable global events
    ScopeInstance globalScopeInstance = Scope.getInstance(rootToken);
    globalScopeInstance.enableEvents();
    // start execution
    new ProcessInstanceStarter(globalScopeInstance.getActivityToken(), receiver).visit(this);
  }

  // property getters and setters
  // ///////////////////////////////////////////////////////////////

  public String getTargetNamespace() {
    return targetNamespace;
  }

  public void setTargetNamespace(String targetNamespace) {
    this.targetNamespace = targetNamespace;
  }

  public String getQueryLanguage() {
    return queryLanguage;
  }

  public void setQueryLanguage(String queryLanguage) {
    this.queryLanguage = queryLanguage;
  }

  public String getExpressionLanguage() {
    return expressionLanguage;
  }

  public void setExpressionLanguage(String expressionLanguage) {
    this.expressionLanguage = expressionLanguage;
  }

  public boolean isAbstractProcess() {
    return abstractProcess;
  }

  public void setAbstractProcess(boolean abstractProcess) {
    this.abstractProcess = abstractProcess;
  }

  public boolean getEnableInstanceCompensation() {
    return enableInstanceCompensation;
  }

  public void setEnableInstanceCompensation(boolean enableInstanceCompensation) {
    this.enableInstanceCompensation = enableInstanceCompensation;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Set getNamespaces() {
    return namespaces;
  }

  public Namespace getNamespace(String prefix, String URI) {
    Iterator namespaceIt = namespaces.iterator();
    while (namespaceIt.hasNext()) {
      Namespace namespace = (Namespace) namespaceIt.next();
      if (prefix.equals(namespace.getPrefix())
          && URI.equals(namespace.getURI())) {
        return namespace;
      }
    }
    return null;
  }

  public Namespace addNamespace(Namespace namespace) {
    if (!namespaces.add(namespace)) {
      // namespace already exists, get the original one
      namespace = getNamespace(namespace.getPrefix(), namespace.getURI());
    }
    return namespace;
  }

  public Set addNamespaces(Set namespaces) {
    HashSet internNamespaces = new HashSet();
    Iterator namespaceIt = namespaces.iterator();
    while (namespaceIt.hasNext()) {
      Namespace namespace = (Namespace) namespaceIt.next();
      Namespace internNamespace = addNamespace(namespace);
      internNamespaces.add(internNamespace);
    }
    return internNamespaces;
  }

  public Namespace addNamespace(String prefix, String URI) {
    Namespace namespace = getNamespace(prefix, URI);
    if (namespace == null) {
      namespace = new Namespace(prefix, URI);
      namespaces.add(namespace);
    }
    return namespace;
  }

  public Set addNamespaces(Map namespaceMap) {
    HashSet internNamespaces = new HashSet();
    Iterator namespaceEntryIt = namespaceMap.entrySet().iterator();
    while (namespaceEntryIt.hasNext()) {
      Entry namespaceEntry = (Entry) namespaceEntryIt.next();
      Namespace internNamespace = addNamespace((String) namespaceEntry.getKey(),
          (String) namespaceEntry.getValue());
      internNamespaces.add(internNamespace);
    }
    return internNamespaces;
  }

  public ImportsDefinition getImports() {
    return (ImportsDefinition) getDefinition(ImportsDefinition.class);
  }

  public Scope getGlobalScope() {
    return (Scope) getNodes().get(0);
  }

  public static void registerModuleClass(Class moduleClass) {
    if (ModuleDefinition.class.isAssignableFrom(moduleClass)) {
      moduleClasses.add(moduleClass);
      log.debug("registered module class: " + moduleClass.getName());
    }
    else {
      log.warn("not a module: " + moduleClass);
    }
  }

  private static void readModuleClasses() {
    // load file from the classpath
    String resource = JbpmConfiguration.Configs.getString("resource.default.modules");
    InputStream resourceStream = ClassLoaderUtil.getStream(resource);
    // easy way out: no modules to add
    if (resourceStream == null) return;

    // parse properties document
    Properties modulesProperties = new Properties();
    try {
      modulesProperties.load(resourceStream);
      resourceStream.close();
    }
    catch (IOException e) {
      log.warn("could not load modules properties: resource=" + resource);
    }

    Iterator moduleNameIt = modulesProperties.keySet().iterator();
    while (moduleNameIt.hasNext()) {
      String moduleName = (String) moduleNameIt.next();
      Class moduleClass = ClassLoaderUtil.loadClass(moduleName);
      registerModuleClass(moduleClass);
    }
  }

  static {
    readModuleClasses();
  }

  public static class GlobalScope extends Scope {

    private static final long serialVersionUID = 1L;

    public GlobalScope() {
      setImplicit(true);
      setSuppressJoinFailure(Boolean.FALSE);
    }

    public boolean isChildInitial(Activity activity) {
      return activity.equals(root);
    }

    /**
     * is the {@link CompositeActivity} or the {@link BpelDefinition} in which
     * this activity is contained.
     */
    public GraphElement getParent() {
      return processDefinition;
    }

    public ProcessDefinition getProcessDefinition() {
      return processDefinition;
    }

    public boolean suppressJoinFailure() {
      return getSuppressJoinFailure().booleanValue();
    }
  }
}
