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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Message;
import javax.xml.namespace.QName;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.lang.enums.Enum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.exe.FaultInstance;
import org.jbpm.bpel.exe.ScopeInstance;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.variable.def.ElementType;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.def.VariableType;
import org.jbpm.bpel.variable.exe.MessageValue;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ExceptionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.instantiation.Delegation;

/**
 * Represents a BPEL scope activity.
 * @see "WS-BPEL 2.0 &sect;13"
 * @author Juan Cantú
 * @version $Revision: 1.6 $ $Date: 2006/08/21 01:05:59 $
 */
public class Scope extends CompositeActivity {

  protected Activity root;

  private transient Collection nestedScopes = null;

  private Map variables = new HashMap();
  private Map partnerLinks = new HashMap();
  private Map correlationSets = new HashMap();

  private Map handlers = new HashMap();
  private List faultHandlers = new ArrayList();
  private Collection onEvents = new HashSet();
  private Collection onAlarms = new HashSet();

  private boolean isolated;
  private boolean implicit = false;

  public static final String VARIABLE_NAME = "s:instance";

  private static final long serialVersionUID = 1L;

  /**
   * Compensation handler type.
   */
  public static final HandlerType COMPENSATION = new HandlerType("compensation");
  /**
   * Non-discriminating fault handler type.
   */
  public static final HandlerType CATCH_ALL = new HandlerType("catchAll");
  /**
   * Forced termination handler type.
   */
  public static final HandlerType TERMINATION = new HandlerType("termination");
  /**
   * Qualified name of the forced termination fault.
   */
  public static final QName FAULT_FORCED_TERMINATION = new QName(
      BpelConstants.NS_BPWS, "forcedTermination");

  public Scope() {
  }

  public Scope(String name) {
    super(name);
  }

  public void installFaultExceptionHandler() {
    ExceptionHandler faultExceptionHandler = new ExceptionHandler();
    faultExceptionHandler.setExceptionClassName(BpelFaultException.class.getName());
    faultExceptionHandler.addAction(new Action(new Delegation(
        FaultActionHandler.class.getName())));
    this.addExceptionHandler(faultExceptionHandler);
  }

  // behaviour methods
  // /////////////////////////////////////////////////////////////////////////////

  public void execute(ExecutionContext context) {
    // TODO multiple-scope behavior
    Token scopeToken = new Token(context.getToken(), name);
    // FIXME scope initialization faults have to be caught by the parent scope
    initScopeData(scopeToken);
    ScopeInstance scopeInstance = Scope.getInstance(scopeToken);
    scopeInstance.enableEvents();
    Token activityToken = scopeInstance.getActivityToken();
    getRoot().enter(new ExecutionContext(activityToken));
  }

  public void terminate(ExecutionContext context) {
    Token scopeToken = context.getToken().getChild(name);
    ScopeInstance scopeInstance = Scope.getInstance(scopeToken);
    scopeInstance.terminate();
  }

  public void setNegativeLinkStatus(Token token) {
    getRoot().setNegativeLinkStatus(token);
    super.setNegativeLinkStatus(token);
  }

  public void complete(ScopeInstance parent) {
    // disableTriggers(parent);
    Token token = parent.getToken();
    token.getNode().leave(new ExecutionContext(token));
  }

  public void initScopeData(Token token) {
    // scope instance
    createInstance(token);
    // TODO variable initialization and bpws:scopeInitializationFault handling
    // variables
    Iterator variableIt = variables.values().iterator();
    while (variableIt.hasNext()) {
      ((VariableDefinition) variableIt.next()).createInstance(token);
    }
    // correlation sets
    Iterator correlationSetIt = correlationSets.values().iterator();
    while (correlationSetIt.hasNext()) {
      ((CorrelationSetDefinition) correlationSetIt.next()).createInstance(token);
    }
    // partner links
    Iterator partnerLinkIt = partnerLinks.values().iterator();
    while (partnerLinkIt.hasNext()) {
      ((PartnerLinkDefinition) partnerLinkIt.next()).createInstance(token);
    }
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }

  // composite activity override
  // /////////////////////////////////////////////////////////////////////////////

  public VariableDefinition findVariable(String varName) {
    VariableDefinition variable = getVariable(varName);
    return variable != null ? variable : super.findVariable(varName);
  }

  public CorrelationSetDefinition findCorrelationSet(String csName) {
    CorrelationSetDefinition cs = getCorrelationSet(csName);
    return cs != null ? cs : super.findCorrelationSet(csName);
  }

  public PartnerLinkDefinition findPartnerLink(String plinkName) {
    PartnerLinkDefinition plink = getPartnerLink(plinkName);
    return plink != null ? plink : super.findPartnerLink(plinkName);
  }

  // scope properties
  // /////////////////////////////////////////////////////////////////////////////

  public Activity getRoot() {
    return root;
  }

  public void setRoot(Activity root) {
    this.root = root;
    root.compositeActivity = this;
  }

  public void addVariable(VariableDefinition variable) {
    variables.put(variable.getName(), variable);
  }

  /**
   * Gets the variables defined in scope.
   * @return a map of &lt;name, variable&gt; entries
   */
  public Map getVariables() {
    return variables;
  }

  public void setVariables(Map variables) {
    this.variables = variables;
  }

  public VariableDefinition getVariable(String variableName) {
    return (VariableDefinition) variables.get(variableName);
  }

  public void addCorrelationSet(CorrelationSetDefinition correlation) {
    correlationSets.put(correlation.getName(), correlation);
  }

  /**
   * Gets the correlation set with the given name. Only checks correlation sets
   * defined in this scope.
   * @param correlationSetName the correlation set name
   * @return a correlation set whose name matches the argument
   */
  public CorrelationSetDefinition getCorrelationSet(String correlationSetName) {
    return (CorrelationSetDefinition) correlationSets.get(correlationSetName);
  }

  public void setCorrelationSets(Map correlationSets) {
    this.correlationSets = correlationSets;
  }

  public Map getPartnerLinks() {
    return partnerLinks;
  }

  public void setPartnerLinks(Map partnerLinks) {
    this.partnerLinks = partnerLinks;
  }

  public void addPartnerLink(PartnerLinkDefinition partnerLink) {
    partnerLinks.put(partnerLink.getName(), partnerLink);
  }

  public PartnerLinkDefinition getPartnerLink(String plinkName) {
    return (PartnerLinkDefinition) partnerLinks.get(plinkName);
  }

  public void addCatch(Catch catcher) {
    catcher.scope = this;
    faultHandlers.add(catcher);
  }

  /**
   * Selects a handler for the given fault. The handler is selected as follows.
   * <ul>
   * <li>in the case of faults thrown without associated data, select a handler
   * with a matching faultName and no faultVariable</li>
   * <li>in the case of faults thrown with associated data, select a handler
   * with a matching faultName and no faultVariable; if there is no such handler
   * then select a handler with a matching faultVariable and no faultName</li>
   * <li>if no handler has been selected, select the catchAll handler if it
   * exists</li>
   * </ul>
   * @param faultInstance the fault to handle
   * @return the selected fault handler, or <code>null</code> if no handler is
   *         able to catch the fault
   */
  public ScopeHandler getFaultHandler(FaultInstance faultInstance) {
    // get an iterator over fault handlers
    Iterator handlerIt = faultHandlers.iterator();
    // if a fault name is present, filter out handlers not matching the name
    QName name = faultInstance.getName();
    if (name != null) {
      handlerIt = new FilterIterator(handlerIt, new FaultNamePredicate(name));
    }
    // determine the type of fault data
    VariableType dataType;
    MessageValue messageData = faultInstance.getMessageValue();
    if (messageData != null) {
      dataType = messageData.getType();
    }
    else {
      Element elementData = faultInstance.getElementValue();
      if (elementData != null) {
        QName elementName = new QName(elementData.getNamespaceURI(),
            elementData.getLocalName());
        dataType = getBpelDefinition().getImports().getElementType(elementName);
      }
      else {
        dataType = null;
      }
    }
    // locate a suitable fault handler
    Catch selectedCatch = findCatch(handlerIt, dataType);
    return selectedCatch != null ? selectedCatch : getHandler(CATCH_ALL);
  }

  /**
   * Finds a handler for a fault thrown with associated data among the given
   * handlers. For each handler <i>h</i>:
   * <ul>
   * <li>if <i>h</i> has a faultVariable whose type matches the type of the
   * fault data then <i>h</i> is selected</li>
   * <li>otherwise if the type of fault data is a WSDL message which contains a
   * single part defined by an element, and <i>h</i> has a faultVariable whose
   * type matches the type of the element used to define the part then <i>h</i>
   * is selected</li>
   * <li>otherwise if <i>h</i> does not specify a faultVariable then <i>h</i>
   * is selected</li>
   * </ul>
   * @param dataType
   * @return the selected fault handler, or <code>null</code> if no handler is
   *         able to catch the fault
   */
  protected Catch findCatch(Iterator handlerIt, VariableType dataType) {
    Catch selectedCatch = null;
    while (handlerIt.hasNext()) {
      Catch catcher = (Catch) handlerIt.next();
      // look for a fault variable
      VariableDefinition variable = catcher.getFaultVariable();
      if (variable != null) {
        VariableType handlerType = variable.getType();
        if (handlerType.equals(dataType)) {
          /*
           * current handler matches exactly the fault type; it has the highest
           * priority, select it and stop the search
           */
          selectedCatch = catcher;
          break;
        }
        else if (dataType instanceof MessageType
            && handlerType instanceof ElementType) {
          // fault data is a WSDL message, and the handler has an element
          // variable
          Message message = ((MessageType) dataType).getMessage();
          QName elementName = WsdlUtil.getDocLitElementName(message);
          // do the handler element and the message part element match?
          if (handlerType.getName().equals(elementName)) {
            /*
             * current handler matches the element, select it but keep looking
             * for a exact type match
             */
            selectedCatch = catcher;
          }
        }
      }
      else if (selectedCatch == null) {
        /*
         * this handler does not define a variable, select it only if no other
         * handler (of higher priority) has been selected
         */
        selectedCatch = catcher;
        // if no fault data is present, we won't find a better match
        if (dataType == null) break;
      }
    }
    return selectedCatch;
  }

  public List getFaultHandlers() {
    return faultHandlers;
  }

  public Collection getOnEvents() {
    return onEvents;
  }

  public void addOnEvent(OnEvent onEvent) {
    onEvents.add(onEvent);
    onEvent.scope = this;
  }

  public Collection getOnAlarms() {
    return onAlarms;
  }

  public void addOnAlarm(OnAlarm onAlarm) {
    onAlarms.add(onAlarm);
    onAlarm.scope = this;
  }

  public void setHandler(HandlerType type, ScopeHandler handler) {
    ScopeHandler previousHandler = (ScopeHandler) handlers.put(type, handler);
    if (previousHandler != null) previousHandler.scope = null;
    handler.scope = this;
  }

  public ScopeHandler getHandler(HandlerType type) {
    return (ScopeHandler) handlers.get(type);
  }

  // local scope properties
  // //////////////////////////////////////////////////////////////////////

  public boolean isIsolated() {
    return isolated;
  }

  public void setIsolated(boolean isolated) {
    this.isolated = isolated;
  }

  public void setImplicit(boolean implicit) {
    this.implicit = implicit;
  }

  public boolean isImplicit() {
    return implicit;
  }

  /** Enumerates the handler types a scope can provide. */
  public static class HandlerType extends Enum {

    private static final long serialVersionUID = 1L;

    /**
     * Enumeration constructor.
     * @param name the desired textual representation.
     */
    HandlerType(String name) {
      super(name);
    }
  }

  // children management
  // /////////////////////////////////////////////////////////////////////////////

  /** {@inheritDoc} */
  public Node addNode(Node node) {
    setRoot((Activity) node);
    return node;
  }

  /** {@inheritDoc} */
  public Node removeNode(Node node) {
    if (root.equals(node)) {
      root.compositeActivity = null;
      root = null;
    }
    return node;
  }

  /** {@inheritDoc} */
  public void reorderNode(int oldIndex, int newIndex) {
    // nothing to do
  }

  /** {@inheritDoc} */
  public List getNodes() {
    return root != null ? Collections.singletonList(root) : null;
  }

  public Collection getNestedScopes() {
    if (nestedScopes == null) {
      NestedScopeFinder finder = new NestedScopeFinder();
      finder.visit(this);
      nestedScopes = finder.getScopes();
    }

    return nestedScopes;
  }

  public ScopeInstance createInstance(Token token) {
    ScopeInstance instance = new ScopeInstance(this, token);
    ContextInstance contextInstance = token.getProcessInstance()
        .getContextInstance();
    contextInstance.createVariable(VARIABLE_NAME, instance, token);
    return instance;
  }

  public static ScopeInstance getInstance(Token token) {
    ContextInstance contextInstance = token.getProcessInstance()
        .getContextInstance();
    return (ScopeInstance) contextInstance.getVariable(VARIABLE_NAME, token);
  }

  private static class FaultNamePredicate implements Predicate {

    private final QName faultName;

    FaultNamePredicate(QName faultName) {
      this.faultName = faultName;
    }

    public boolean evaluate(Object arg) {
      QName handlerFaultName = ((Catch) arg).getFaultName();
      return faultName != null ? faultName.equals(handlerFaultName)
          : handlerFaultName == null;
    }
  }

  /**
   * Handles exceptions containing BPEL faults by submitting the fault to the
   * nearest enclosing scope.
   * @author Alejandro Guizar
   * @version $Revision: 1.6 $ $Date: 2006/08/21 01:05:59 $
   */
  public static class FaultActionHandler implements ActionHandler {

    private static final Log log = LogFactory.getLog(FaultActionHandler.class);

    private static final long serialVersionUID = 1L;

    // keep the default constructor public, otherwise the delegation won't be
    // able to instantiate it
    public FaultActionHandler() {
    }

    public void execute(ExecutionContext exeContext) throws Exception {
      BpelFaultException faultException = (BpelFaultException) exeContext.getException();
      Token token = exeContext.getToken();
      log.debug("handling fault: token=" + token, faultException);
      Scope.getInstance(token).faulted(faultException.getFaultInstance());
    }
  };
}