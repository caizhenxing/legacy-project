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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.PortType;
import javax.wsdl.WSDLException;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.ibm.wsdl.util.xml.DOMUtils;

import org.jbpm.JbpmConfiguration;
import org.jbpm.bpel.alarm.Alarm;
import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.Catch;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.Import;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.OnAlarm;
import org.jbpm.bpel.def.OnEvent;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.ScopeHandler;
import org.jbpm.bpel.def.Import.Type;
import org.jbpm.bpel.integration.def.Correlation;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.def.Correlation.Initiate;
import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.sublang.def.JoinCondition;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.variable.def.ElementType;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.variable.def.VariableType;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.wsdl.PropertyAlias;
import org.jbpm.bpel.wsdl.PartnerLinkType.Role;
import org.jbpm.bpel.wsdl.impl.MessageImpl;
import org.jbpm.bpel.wsdl.impl.OperationImpl;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.wsdl.xml.WsdlConstants;
import org.jbpm.bpel.xml.util.NodeIterator;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.jpdl.par.ProcessArchive;
import org.jbpm.jpdl.xml.Problem;
import org.jbpm.util.ClassLoaderUtil;

/**
 * @author Juan Cantú
 * @author Alejandro Guízar
 * @version $Revision: 1.6 $ $Date: 2006/08/21 01:05:59 $
 */
public class BpelReader {

  private Map activityReaders = new HashMap();
  private ProblemHandler problemHandler;

  public static final String RESOURCE_ACTIVITY_READERS = "resource.activity.readers";

  private static final ThreadLocal bpelReaderLocal = new ThreadLocal() {

    protected Object initialValue() {
      return new BpelReader();
    }
  };
  private static Map activityReaderClasses = new HashMap();
  private static Templates bpelUpdateTemplates;
  private static final Log log = LogFactory.getLog(BpelReader.class);

  protected BpelReader() {
    initActivityReaders();
  }

  /**
   * Populates a BPEL process definition with content from the given source.
   * @param processDefinition the definition to populate
   * @param documentSource the content to read
   */
  public void read(BpelDefinition processDefinition, InputSource documentSource) {
    // get the thread-local parser
    DocumentBuilder builder = XmlUtil.getDocumentBuilder();
    // prepare custom error handling
    ProblemHandler problemHandler = getProblemHandler();
    ErrorHandlerAdapter problemAdapter = new ErrorHandlerAdapter(problemHandler);
    builder.setErrorHandler(problemAdapter);
    // save the document location
    String documentURI = documentSource.getSystemId();
    processDefinition.setLocation(documentURI);
    Document bpelDocument;
    try {
      // parse content
      bpelDocument = builder.parse(documentSource);
      // proceed only if no parse errors arose
      if (!problemAdapter.hasErrors()) {
        // prepare a locator of imported documents, based on the document
        // location
        ImportWsdlLocator wsdlLocator = null;
        if (documentURI != null) {
          wsdlLocator = new ImportWsdlLocator(documentURI);
          wsdlLocator.setProblemHandler(problemHandler);
        }
        // populate definition
        read(processDefinition, bpelDocument.getDocumentElement(), wsdlLocator);
      }
    }
    catch (Exception e) {
      problemHandler.add(new Problem(Problem.LEVEL_ERROR,
          "could not read process document", e));
    }
    finally {
      // reset error handling behavior
      builder.setErrorHandler(null);
    }
  }

  /**
   * Populates a BPEL process definition with the content of the given archive.
   * @param processDefinition the definition to populate; its
   *          <code>location</code> property specifies the process document
   *          entry within the archive
   * @param archive the archive to read
   */
  public void read(BpelDefinition processDefinition, ProcessArchive archive) {
    String location = processDefinition.getLocation();
    byte[] bpelEntry = archive.getEntry(location);
    if (bpelEntry == null) {
      getProblemHandler().add(new Problem(Problem.LEVEL_ERROR,
          "process document entry not found"));
      return;
    }
    // get the thread-local parser
    DocumentBuilder documentParser = XmlUtil.getDocumentBuilder();
    // prepare custom error handling
    ProblemHandler problemHandler = getProblemHandler();
    ErrorHandlerAdapter parseAdapter = new ErrorHandlerAdapter(problemHandler);
    documentParser.setErrorHandler(parseAdapter);
    try {
      // parse content
      Document bpelDocument = documentParser.parse(new ByteArrayInputStream(
          bpelEntry));
      // proceed only if no parse errors arose
      if (!parseAdapter.hasErrors()) {
        ImportWsdlLocator wsdlLocator = new ArchiveWsdlLocator(location,
            archive);
        wsdlLocator.setProblemHandler(problemHandler);
        // populate definition
        read(processDefinition, bpelDocument.getDocumentElement(), wsdlLocator);
      }
    }
    catch (Exception e) {
      problemHandler.add(new Problem(Problem.LEVEL_ERROR,
          "could not read process document", e));
    }
    finally {
      // reset error handling behavior
      documentParser.setErrorHandler(null);
    }
  }

  protected void read(BpelDefinition process, Element processElem,
      ImportWsdlLocator wsdlLocator) {
    // see if the bpel document requires updating
    if (BpelConstants.NS_BPWS_1_1.equals(processElem.getNamespaceURI())) {
      // prepare custom error handling
      ErrorListenerAdapter transformAdapter = new ErrorListenerAdapter(
          getProblemHandler());
      try {
        // create a bpel transformer
        Transformer bpelUpdater = getBpelUpdateTemplates().newTransformer();
        bpelUpdater.setErrorListener(transformAdapter);
        // upgrade to a new dom document
        Document newProcessDoc = XmlUtil.createDocument();
        bpelUpdater.transform(new DOMSource(processElem.getOwnerDocument()),
            new DOMResult(newProcessDoc));
        processElem = newProcessDoc.getDocumentElement();
        log.debug("converted bpel document: " + process.getLocation());
      }
      catch (TransformerException e) {
        transformAdapter.error(e);
      }
      // halt on transformation errors
      if (transformAdapter.hasErrors()) return;
    }
    // get attributes in the process element, including query language
    readTopLevelAttributes(processElem, process);
    // read imported documents
    ImportsDefinition imports = process.getImports();
    readImports(processElem, imports, wsdlLocator);
    // if our wsdl locator has errors, stop reading
    if (wsdlLocator != null && wsdlLocator.hasErrors()) return;

    try {
      // registration gets the query language from the process definition
      registerPropertyAliases(imports);
      // finally read the top-level scope
      readScope(processElem, process.getGlobalScope());
      log.info("read bpel process: " + process.getLocation());
    }
    catch (BpelException e) {
      getProblemHandler().add(new Problem(Problem.LEVEL_ERROR,
          "bpel process is invalid", e));
    }
  }

  public static synchronized Templates getBpelUpdateTemplates() {
    if (bpelUpdateTemplates == null) {
      bpelUpdateTemplates = XmlUtil.createTemplates(BpelReader.class.getResource("bpel-1-1-converter.xsl"));
    }
    return bpelUpdateTemplates;
  }

  public void registerPropertyAliases(ImportsDefinition importsDef) {
    List imports = importsDef.getImports();
    // easy way out (necessary)
    if (imports == null) return;
    // get the process-level query language
    BpelDefinition process = (BpelDefinition) importsDef.getProcessDefinition();
    String queryLanguage = process.getQueryLanguage();
    // register property aliases in each wsdl document
    for (int i = 0, n = imports.size(); i < n; i++) {
      Import imp = (Import) imports.get(i);
      if (Type.WSDL.equals(imp.getType())) {
        registerPropertyAliases(importsDef,
            (Definition) imp.getDocument(),
            queryLanguage);
      }
    }
  }

  private void registerPropertyAliases(ImportsDefinition importsDef,
      Definition wsdlDef, String queryLanguage) {
    BpelDefinition process = (BpelDefinition) importsDef.getProcessDefinition();
    // first deal with local extensibility elements
    Iterator aliasIt = WsdlUtil.getExtensions(wsdlDef.getExtensibilityElements(),
        WsdlConstants.Q_PROPERTY_ALIAS);
    while (aliasIt.hasNext()) {
      PropertyAlias alias = (PropertyAlias) aliasIt.next();
      Property property = alias.getProperty();
      Message message = alias.getMessage();
      // check the property definition exists
      if (property.isUndefined()) {
        Problem problem = new Problem(Problem.LEVEL_ERROR,
            "aliasing property not found: property=" + property.getQName()
                + ", message=" + message.getQName());
        problem.setResource(wsdlDef.getDocumentBaseURI());
        getProblemHandler().add(problem);
      }
      else {
        importsDef.addProperty(property);
      }
      // check the message definition exists
      if (message.isUndefined()) {
        Problem problem = new Problem(Problem.LEVEL_ERROR,
            "aliased message not found: " + "message=" + message.getQName()
                + ", property=" + property.getQName());
        problem.setResource(wsdlDef.getDocumentBaseURI());
        getProblemHandler().add(problem);
      }
      else {
        // register property alias in message type
        MessageType type = importsDef.getMessageType(message.getQName());
        type.addPropertyAlias(alias);
      }
      // parse query, if any
      Query query = alias.getQuery();
      if (query != null) {
        if (query.getLanguage() == null) {
          query.setLanguage(queryLanguage);
        }
        query.setNamespaces(process.addNamespaces(query.getNamespaces()));
        try {
          query.parse();
        }
        catch (BpelException e) {
          Problem problem = new Problem(Problem.LEVEL_ERROR,
              "could not parse aliased location: property="
                  + property.getQName() + ", message=" + message.getQName(), e);
          problem.setResource(wsdlDef.getDocumentBaseURI());
          getProblemHandler().add(problem);
        }
      }
    }
    // now deal with imported definitions
    Iterator importsIt = wsdlDef.getImports().values().iterator();
    while (importsIt.hasNext()) {
      List imports = (List) importsIt.next();
      for (int i = 0, n = imports.size(); i < n; i++) {
        javax.wsdl.Import imp = (javax.wsdl.Import) imports.get(i);
        registerPropertyAliases(importsDef, imp.getDefinition(), queryLanguage);
      }
    }
  }

  // process properties
  // //////////////////////////////////////////////////////////////

  public void readTopLevelAttributes(Element processElem, BpelDefinition process) {
    // name & namespace
    process.setName(processElem.getAttribute(BpelConstants.ATTR_NAME));
    process.setTargetNamespace(processElem.getAttribute(BpelConstants.ATTR_TARGET_NAMESPACE));
    // query & expression language
    process.setQueryLanguage(XmlUtil.getAttribute(processElem,
        BpelConstants.ATTR_QUERY_LANGUAGE));
    process.setExpressionLanguage(XmlUtil.getAttribute(processElem,
        BpelConstants.ATTR_EXPRESSION_LANGUAGE));
    // suppress join failure
    Attr suppressAttr = processElem.getAttributeNode(BpelConstants.ATTR_SUPPRESS_JOIN_FAILURE);
    process.getGlobalScope().setSuppressJoinFailure(readTBoolean(suppressAttr,
        Boolean.FALSE));
    // abstract process
    Attr abstractAttr = processElem.getAttributeNode(BpelConstants.ATTR_ABSTRACT_PROCESS);
    process.setAbstractProcess(readTBoolean(abstractAttr, Boolean.FALSE).booleanValue());
    // enable instance compensation
    Attr compensationAttr = processElem.getAttributeNode(BpelConstants.ATTR_INSTANCE_COMPENSATION);
    process.setEnableInstanceCompensation(readTBoolean(compensationAttr,
        Boolean.FALSE).booleanValue());
  }

  public void readImports(Element processElem, ImportsDefinition imports,
      ImportWsdlLocator wsdlLocator) {
    Iterator importElemIt = XmlUtil.getElements(processElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_IMPORT);
    // process document explicitly imports documents, read only those documents
    if (importElemIt.hasNext()) {
      do {
        Import imp = readImport((Element) importElemIt.next());
        if (Import.Type.WSDL.equals(imp.getType())) {
          readWsdlDocument(imp, wsdlLocator);
        }
        imports.addImport(imp);
      } while (importElemIt.hasNext());
    }
    else {

    }
  }

  public Import readImport(Element importElem) {
    Import imp = new Import();
    imp.setNamespace(importElem.getAttribute(BpelConstants.ATTR_NAMESPACE));
    imp.setLocation(importElem.getAttribute(BpelConstants.ATTR_LOCATION));
    imp.setType(Import.Type.valueOf(importElem.getAttribute(BpelConstants.ATTR_IMPORT_TYPE)));
    return imp;
  }

  public void readWsdlDocument(Import imp, ImportWsdlLocator wsdlLocator) {
    String location = imp.getLocation();
    wsdlLocator.resolveBaseURI(location);
    WSDLReader reader = WsdlUtil.getFactory().newWSDLReader();
    try {
      // read imported WSDL document
      Definition def = reader.readWSDL(wsdlLocator);
      log.info("read wsdl definitions: " + location);
      // validate the definitions belong to the import's namespace
      if (!def.getTargetNamespace().equals(imp.getNamespace())) {
        getProblemHandler().add(new Problem(Problem.LEVEL_ERROR,
            "imported definitions do not belong to the import's namespace"));
      }
      imp.setDocument(def);
    }
    catch (WSDLException e) {
      getProblemHandler().add(new Problem(Problem.LEVEL_ERROR,
          "could not read wsdl document", e));
    }
  }

  // general properties
  // //////////////////////////////////////////////////////////////

  public Boolean readTBoolean(Attr attribute, Boolean defaultValue) {
    if (attribute == null) {
      return defaultValue;
    }
    String text = attribute.getValue();
    return BpelConstants.YES.equals(text) ? Boolean.TRUE
        : BpelConstants.NO.equals(text) ? Boolean.FALSE : defaultValue;
  }

  public Expression readExpression(Element enclosingElem,
      CompositeActivity parent) {
    Expression expression = new Expression();
    readExpression(enclosingElem, parent, expression);
    return expression;
  }

  public JoinCondition readJoinCondition(Element conditionElem,
      CompositeActivity parent) {
    JoinCondition joinCondition = new JoinCondition();
    readExpression(conditionElem, parent, joinCondition);
    return joinCondition;
  }

  protected void readExpression(Element enclosingElem,
      CompositeActivity parent, Expression expression) {
    BpelDefinition process = parent.getBpelDefinition();
    // expression
    expression.setText(DOMUtils.getChildCharacterData(enclosingElem));
    expression.setNamespaces(process.addNamespaces(XmlUtil.getNamespaces(enclosingElem)));
    // language
    String language = XmlUtil.getAttribute(enclosingElem,
        BpelConstants.ATTR_EXPRESSION_LANGUAGE);
    if (language == null) {
      language = process.getExpressionLanguage();
    }
    expression.setLanguage(language);
    // parsing
    try {
      expression.parse();
    }
    catch (BpelException e) {
      getProblemHandler().add(new ParseProblem(Problem.LEVEL_ERROR,
          "could not parse script", e, enclosingElem));
    }
  }

  // scope definition properties
  // //////////////////////////////////////////////////////////////

  public void readScope(Element scopeElem, Scope scope) {
    scope.installFaultExceptionHandler();

    // variables
    Element variablesElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_VARIABLES);
    if (variablesElem != null)
      scope.setVariables(readVariables(variablesElem, scope));

    // partner links
    Element partnerLinksElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_PARTNER_LINKS);
    if (partnerLinksElem != null)
      scope.setPartnerLinks(readPartnerLinks(partnerLinksElem, scope));

    // correlation sets
    Element setsElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CORRELATION_SETS);
    if (setsElem != null)
      scope.setCorrelationSets(readCorrelationSets(setsElem, scope));

    // activity
    Element activityElem = getActivityElement(scopeElem);
    readActivity(activityElem, scope);

    // fault handlers
    Element faultHandlerElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_FAULT_HANDLERS);
    if (faultHandlerElem != null) readFaultHandlers(faultHandlerElem, scope);

    // compensation handler
    Element compHandlerElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_COMPENSATION_HANDLER);
    if (compHandlerElem != null)
      readHandler(compHandlerElem, scope, Scope.COMPENSATION);

    // event handlers
    Element eventHandlerElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_EVENT_HANDLERS);
    if (eventHandlerElem != null) readEventHandlers(eventHandlerElem, scope);

    // termination handler
    Element termHandlerElem = XmlUtil.getElement(scopeElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_TERMINATION_HANDLER);
    if (termHandlerElem != null)
      readHandler(termHandlerElem, scope, Scope.TERMINATION);
  }

  public void readHandler(Element element, Scope scope, Scope.HandlerType type) {
    ScopeHandler handler = new ScopeHandler();
    scope.setHandler(type, handler);
    Element activityElement = getActivityElement(element);
    handler.setActivity(readActivity(activityElement, handler));
  }

  public void readFaultHandlers(Element faultHandlerElem, Scope scope) {
    // load catchAll
    Element catchAllElem = XmlUtil.getElement(faultHandlerElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CATCH_ALL);
    if (catchAllElem != null) {
      readHandler(catchAllElem, scope, Scope.CATCH_ALL);
    }

    Iterator catchElemIt = XmlUtil.getElements(faultHandlerElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CATCH);
    while (catchElemIt.hasNext()) {
      Element catchElement = (Element) catchElemIt.next();
      readCatch(catchElement, scope);
    }
  }

  public void readCatch(Element catchElem, Scope scope) {
    /*
     * These valid configurations for a faultHandler are: -Catching faults
     * without data: (faultName) -Catching faults with data: (faultVariable AND
     * (elementType OR messageType)) (faultName AND (faultVariable AND
     * (elementType OR messageType))
     */
    Catch catcher = new Catch();

    // fault name
    QName faultName = getFaultName(catchElem);
    catcher.setFaultName(faultName);

    // fault variable
    VariableDefinition faultVariable = getFaultVariable(catchElem, scope);
    catcher.setFaultVariable(faultVariable);

    // check name or variable is present
    if (faultName == null && faultVariable == null) {
      getProblemHandler().add(new ParseProblem(
          "catch must specify faultName, faultVariable or both", catchElem));
    }

    // activity
    Element activityElem = getActivityElement(catchElem);
    catcher.setActivity(readActivity(activityElem, catcher));

    scope.addCatch(catcher);
  }

  protected QName getFaultName(Element catchElem) {
    QName faultName = null;
    String faultNameAttr = XmlUtil.getAttribute(catchElem,
        BpelConstants.ATTR_FAULT_NAME);
    if (faultNameAttr != null) {
      faultName = XmlUtil.getQName(faultNameAttr, catchElem);
    }
    return faultName;
  }

  protected VariableDefinition getFaultVariable(Element catchElem, Scope scope) {
    VariableDefinition faultVariable = null;
    String faultVariableAttr = XmlUtil.getAttribute(catchElem,
        BpelConstants.ATTR_FAULT_VARIABLE);
    if (faultVariableAttr != null) {
      VariableType faultType = getFaultType(catchElem,
          scope.getBpelDefinition().getImports());
      if (faultType != null) {
        // create variable local to handler
        faultVariable = new VariableDefinition();
        faultVariable.setName(faultVariableAttr);
        faultVariable.setType(faultType);
      }
      // BPEL-199 parse BPEL4WS 1.1 fault handler
      else {
        // retrieve variable from enclosing scope
        faultVariable = scope.findVariable(faultVariableAttr);
        // check variable exists
        if (faultVariable == null) {
          getProblemHandler().add(new ParseProblem("variable not found",
              catchElem));
        }
        // check variable is of message type
        else if (!(faultVariable.getType() instanceof MessageType)) {
          getProblemHandler().add(new ParseProblem(
              "catch must reference a message variable", catchElem));
        }
      }
    }
    return faultVariable;
  }

  private VariableType getFaultType(Element catchElem, ImportsDefinition imports) {
    VariableType type = null;

    String messageType = XmlUtil.getAttribute(catchElem,
        BpelConstants.ATTR_FAULT_MESSAGE_TYPE);
    String elementName = XmlUtil.getAttribute(catchElem,
        BpelConstants.ATTR_FAULT_ELEMENT);

    if (messageType != null) {
      if (elementName != null) {
        getProblemHandler().add(new ParseProblem(
            "found more than one fault type specifier", catchElem));
      }
      type = getMessageType(catchElem, messageType, imports);
    }
    else if (elementName != null) {
      type = getElementType(catchElem, elementName, imports);
    }
    return type;
  }

  public void readEventHandlers(Element eventHandlersElem, Scope scope) {
    // onEvents
    Iterator onEventElemIt = XmlUtil.getElements(eventHandlersElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_ON_EVENT);
    while (onEventElemIt.hasNext()) {
      OnEvent onEvent = new OnEvent();
      scope.addOnEvent(onEvent);
      readOnEvent((Element) onEventElemIt.next(), onEvent);
    }
    // onAlarms
    Iterator alarmElemIt = XmlUtil.getElements(eventHandlersElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_ON_ALARM);
    while (alarmElemIt.hasNext()) {
      OnAlarm onAlarm = new OnAlarm();
      scope.addOnAlarm(onAlarm);
      readOnAlarm((Element) alarmElemIt.next(), onAlarm);
    }
  }

  public void readOnEvent(Element onEventElem, OnEvent onEvent) {
    // the attribute messageType indicates a variable declaration
    String messageName = XmlUtil.getAttribute(onEventElem,
        BpelConstants.ATTR_MESSAGE_TYPE);
    if (messageName != null) {
      VariableDefinition variable = new VariableDefinition();
      // name
      String name = XmlUtil.getAttribute(onEventElem,
          BpelConstants.ATTR_VARIABLE);
      variable.setName(name);
      // type
      VariableType messageType = getMessageType(onEventElem,
          messageName,
          onEvent.getBpelDefinition().getImports());
      variable.setType(messageType);
      // onEvent
      onEvent.setVariableDefinition(variable);
    }
    // correlation sets
    Element setsElem = XmlUtil.getElement(onEventElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CORRELATION_SETS);
    if (setsElem != null)
      onEvent.setCorrelationSets(readCorrelationSets(setsElem, onEvent));
    // activity
    Element activityElem = getActivityElement(onEventElem);
    onEvent.setActivity(readActivity(activityElem, onEvent));
    // receiver
    Receiver receiver = readReceiver(onEventElem, onEvent);
    onEvent.setReceiver(receiver);
  }

  public OnAlarm readOnAlarm(Element onAlarmElem, OnAlarm onAlarm) {
    // alarm
    onAlarm.setAlarm(readAlarm(onAlarmElem, onAlarm));
    // activity
    Element activityElem = getActivityElement(onAlarmElem);
    onAlarm.setActivity(readActivity(activityElem, onAlarm));
    return onAlarm;
  }

  public Map readVariables(Element variablesElem, CompositeActivity parent) {
    Map variables = new HashMap();
    ImportsDefinition imports = parent.getBpelDefinition().getImports();
    Iterator variableElemIt = XmlUtil.getElements(variablesElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_VARIABLE);
    while (variableElemIt.hasNext()) {
      Element variableElem = (Element) variableElemIt.next();
      VariableDefinition variable = readVariable(variableElem, imports);
      String variableName = variable.getName();
      if (variables.containsKey(variableName)) {
        getProblemHandler().add(new ParseProblem("duplicate local name",
            variableElem));
      }
      else {
        variables.put(variableName, variable);
      }
    }
    return variables;
  }

  public VariableDefinition readVariable(Element variableElem,
      ImportsDefinition imports) {
    VariableDefinition variable = new VariableDefinition();
    // name
    variable.setName(variableElem.getAttribute(BpelConstants.ATTR_NAME));
    // type
    variable.setType(getVariableType(variableElem, imports));
    return variable;
  }

  protected VariableType getVariableType(Element variableElem,
      ImportsDefinition imports) {
    VariableType type;

    String schemaType = XmlUtil.getAttribute(variableElem,
        BpelConstants.ATTR_TYPE);
    String messageType = XmlUtil.getAttribute(variableElem,
        BpelConstants.ATTR_MESSAGE_TYPE);
    String elementName = XmlUtil.getAttribute(variableElem,
        BpelConstants.ATTR_ELEMENT);

    if (messageType != null) {
      if (schemaType != null || elementName != null) {
        getProblemHandler().add(new ParseProblem(
            "more than one type specifier present", variableElem));
      }
      type = getMessageType(variableElem, messageType, imports);
    }
    else if (schemaType != null) {
      if (elementName != null) {
        getProblemHandler().add(new ParseProblem(
            "more than one type specifier present", variableElem));
      }
      type = getSchemaType(variableElem, schemaType, imports);
    }
    else if (elementName != null) {
      type = getElementType(variableElem, elementName, imports);
    }
    else {
      getProblemHandler().add(new ParseProblem("no type specifier present",
          variableElem));
      type = null;
    }
    return type;
  }

  private MessageType getMessageType(Element contextElem, String messageType,
      ImportsDefinition imports) {
    QName messageQType = XmlUtil.getQName(messageType, contextElem);
    MessageType type = imports.getMessageType(messageQType);
    if (type == null) {
      getProblemHandler().add(new ParseProblem("message type not found",
          contextElem));
      Message message = new MessageImpl();
      message.setQName(messageQType);
      type = new MessageType(message);
    }
    return type;
  }

  private ElementType getElementType(Element contextElem, String elementName,
      ImportsDefinition imports) {
    QName elementQName = XmlUtil.getQName(elementName, contextElem);
    ElementType type = imports.getElementType(elementQName);
    if (type == null) {
      getProblemHandler().add(new ParseProblem("element not found", contextElem));
      type = new ElementType(elementQName);
    }
    return type;
  }

  private SchemaType getSchemaType(Element contextElem, String schemaType,
      ImportsDefinition imports) {
    QName schemaQType = XmlUtil.getQName(schemaType, contextElem);
    SchemaType type = imports.getSchemaType(schemaQType);
    if (type == null) {
      getProblemHandler().add(new ParseProblem("schema type not found",
          contextElem));
      type = new SchemaType(schemaQType);
    }
    return type;
  }

  // service properties
  // //////////////////////////////////////////////////////////////

  public Map readCorrelationSets(Element setsElem, CompositeActivity superState) {
    Map correlationSets = new HashMap();
    ImportsDefinition imports = superState.getBpelDefinition().getImports();
    Iterator setElemIt = XmlUtil.getElements(setsElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CORRELATION_SET);
    while (setElemIt.hasNext()) {
      Element setElem = (Element) setElemIt.next();
      CorrelationSetDefinition set = readCorrelationSet(setElem, imports);
      String setName = set.getName();
      if (correlationSets.containsKey(setName)) {
        getProblemHandler().add(new ParseProblem("duplicate local name",
            setElem));
      }
      else {
        correlationSets.put(setName, set);
      }
    }
    return correlationSets;
  }

  public CorrelationSetDefinition readCorrelationSet(Element setElem,
      ImportsDefinition imports) {
    CorrelationSetDefinition set = new CorrelationSetDefinition();
    // properties
    String[] propertyNames = setElem.getAttribute(BpelConstants.ATTR_PROPERTIES)
        .split("\\s");
    for (int p = 0; p < propertyNames.length; p++) {
      String propertyName = propertyNames[p];
      Property property = imports.getProperty(XmlUtil.getQName(propertyName,
          setElem));
      if (property == null) {
        throw new BpelException("property not found: " + propertyName, setElem);
      }
      set.addProperty(property);
    }
    // name
    set.setName(setElem.getAttribute(BpelConstants.ATTR_NAME));
    return set;
  }

  public Map readPartnerLinks(Element partnerLinksElem, CompositeActivity parent) {
    Map partnerLinks = new HashMap();
    Iterator partnerLinkElemIt = XmlUtil.getElements(partnerLinksElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_PARTNER_LINK);
    while (partnerLinkElemIt.hasNext()) {
      Element partnerLinkElem = (Element) partnerLinkElemIt.next();
      PartnerLinkDefinition partnerLink = readPartnerLink(partnerLinkElem,
          parent);
      String plinkName = partnerLink.getName();
      if (partnerLinks.containsKey(plinkName)) {
        getProblemHandler().add(new ParseProblem("duplicate local name",
            partnerLinkElem));
      }
      else {
        partnerLinks.put(plinkName, partnerLink);
      }
    }
    return partnerLinks;
  }

  public PartnerLinkDefinition readPartnerLink(Element partnerLinkElem,
      CompositeActivity parent) {
    PartnerLinkDefinition partnerLink = new PartnerLinkDefinition();
    // partner link type
    String typeName = partnerLinkElem.getAttribute(BpelConstants.ATTR_PARTNER_LINK_TYPE);
    QName typeQName = XmlUtil.getQName(typeName, partnerLinkElem);
    PartnerLinkType type = parent.getBpelDefinition()
        .getImports()
        .getPartnerLinkType(typeQName);
    if (type == null) {
      throw new BpelException("partner link type not found", partnerLinkElem);
    }
    partnerLink.setPartnerLinkType(type);

    String myRoleName = XmlUtil.getAttribute(partnerLinkElem,
        BpelConstants.ATTR_MY_ROLE);
    String partnerRoleName = XmlUtil.getAttribute(partnerLinkElem,
        BpelConstants.ATTR_PARTNER_ROLE);

    // first role
    boolean partnerRoleIsFirst;
    Role role = type.getFirstRole();
    String roleName = role.getName();
    if (roleName.equals(myRoleName)) {
      partnerLink.setMyRole(role);
      partnerRoleIsFirst = false;
    }
    else if (roleName.equals(partnerRoleName)) {
      partnerLink.setPartnerRole(role);
      partnerRoleIsFirst = true;
    }
    else {
      throw new BpelException(
          "neither myRole nor partnerRole match the first role",
          partnerLinkElem);
    }
    if (role.getPortType().isUndefined()) {
      throw new BpelException("first role's port type not found",
          partnerLinkElem);
    }
    // second role
    role = type.getSecondRole();
    if (role != null) {
      roleName = role.getName();
      if (partnerRoleIsFirst) {
        if (!roleName.equals(myRoleName)) {
          throw new BpelException("partnerRole matches the first role, "
              + "but myRole does not match the second one", partnerLinkElem);
        }
        partnerLink.setMyRole(role);
      }
      else {
        if (!roleName.equals(partnerRoleName)) {
          throw new BpelException("myRole matches the first role, "
              + "but partnerRole does not match the second one",
              partnerLinkElem);
        }
        partnerLink.setPartnerRole(role);
      }
      if (role.getPortType().isUndefined()) {
        throw new BpelException("second role's port type not found",
            partnerLinkElem);
      }
    }
    else if (partnerRoleIsFirst ? myRoleName != null : partnerRoleName != null) {
      throw new BpelException("both myRole and partnerRole are specified, "
          + "but there is only one role", partnerLinkElem);
    }
    partnerLink.setName(partnerLinkElem.getAttribute(BpelConstants.ATTR_NAME));
    return partnerLink;
  }

  public Receiver readReceiver(Element receiverElem, CompositeActivity parent) {
    Receiver receiver = new Receiver();
    // partner link
    String partnerLinkName = receiverElem.getAttribute(BpelConstants.ATTR_PARTNER_LINK);
    PartnerLinkDefinition partnerLink = parent.findPartnerLink(partnerLinkName);
    if (partnerLink == null) {
      getProblemHandler().add(new ParseProblem("partner link not found",
          receiverElem));
      return receiver;
    }
    receiver.setPartnerLink(partnerLink);
    // port type
    Role myRole = partnerLink.getMyRole();
    // BPEL-181 detect absence of my role
    if (myRole == null) {
      getProblemHandler().add(new ParseProblem(
          "partner link does not indicate my role", receiverElem));
      return receiver;
    }
    PortType portType = getMessageActivityPortType(receiverElem, myRole);
    // operation
    Operation operation = getMessageActivityOperation(receiverElem, portType);
    receiver.setOperation(operation);
    // variable
    VariableDefinition variable = getMessageActivityVariable(receiverElem,
        BpelConstants.ATTR_VARIABLE,
        parent,
        operation.getInput().getMessage());
    receiver.setVariable(variable);
    /*
     * message exchange: if the attribute is not specified then its value is
     * taken to be empty BPEL-74: map the empty message exchange to null instead
     * of the empty string
     */
    receiver.setMessageExchange(XmlUtil.getAttribute(receiverElem,
        BpelConstants.ATTR_MESSAGE_EXCHANGE));
    // correlations
    Element correlationsElem = XmlUtil.getElement(receiverElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CORRELATIONS);
    if (correlationsElem != null) {
      receiver.setCorrelations(readCorrelations(correlationsElem,
          parent,
          variable));
    }
    return receiver;
  }

  public Correlations readCorrelations(Element correlationsElem,
      CompositeActivity parent, VariableDefinition variable) {
    Correlations correlations = new Correlations();
    Iterator correlationElemIt = XmlUtil.getElements(correlationsElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CORRELATION);
    while (correlationElemIt.hasNext()) {
      Element correlationElem = (Element) correlationElemIt.next();
      Correlation correlation = readCorrelation(correlationElem, parent);
      checkVariableProperties(variable, correlation.getSet(), correlationElem);
      correlations.addCorrelation(correlation);
    }
    return correlations;
  }

  public Correlation readCorrelation(Element correlationElem,
      CompositeActivity parent) {
    Correlation correlation = new Correlation();
    // correlation set
    String setName = correlationElem.getAttribute(BpelConstants.ATTR_SET);
    CorrelationSetDefinition set = parent.findCorrelationSet(setName);
    if (set == null) {
      getProblemHandler().add(new ParseProblem("correlation set not found",
          correlationElem));
      set = new CorrelationSetDefinition();
      set.setName(setName);
    }
    correlation.setSet(set);
    // initiate mode
    correlation.setInitiate(Initiate.valueOf(XmlUtil.getAttribute(correlationElem,
        BpelConstants.ATTR_INITIATE)));
    return correlation;
  }

  PortType getMessageActivityPortType(Element serviceElem, Role role) {
    PortType implicitPortType = role.getPortType();
    String portTypeName = XmlUtil.getAttribute(serviceElem,
        BpelConstants.ATTR_PORT_TYPE);
    if (portTypeName != null) {
      QName portTypeQName = XmlUtil.getQName(portTypeName, serviceElem);
      if (!portTypeQName.equals(implicitPortType.getQName())) {
        getProblemHandler().add(new ParseProblem(
            "port type mismatch between message activity and partner link",
            serviceElem));
      }
    }
    return implicitPortType;
  }

  Operation getMessageActivityOperation(Element serviceElem, PortType portType) {
    String operationName = serviceElem.getAttribute(BpelConstants.ATTR_OPERATION);
    Operation op = portType.getOperation(operationName, null, null);
    if (op == null) {
      getProblemHandler().add(new ParseProblem("operation not found",
          serviceElem));
      op = new OperationImpl();
      op.setName(operationName);
    }
    else {
      OperationType opType = op.getStyle();
      if (opType.equals(OperationType.SOLICIT_RESPONSE)
          || opType.equals(OperationType.NOTIFICATION)) {
        getProblemHandler().add(new ParseProblem(
            "operation style not supported", serviceElem));
      }
    }
    return op;
  }

  VariableDefinition getMessageActivityVariable(Element serviceElem,
      String variableAttr, CompositeActivity parent, Message activityMessage) {
    // get variable name
    String variableName = XmlUtil.getAttribute(serviceElem, variableAttr);
    if (variableName == null) {
      throw new BpelException(variableAttr + " attribute is missing",
          serviceElem);
    }
    // find variable definition
    VariableDefinition variable = parent.findVariable(variableName);
    if (variable == null) {
      getProblemHandler().add(new ParseProblem(variableAttr + " not found",
          serviceElem));
      // create a variable stub
      variable = new VariableDefinition();
      variable.setName(variableName);
      variable.setType(parent.getBpelDefinition()
          .getImports()
          .getMessageType(activityMessage.getQName()));
    }
    // validate type
    else if (!variable.getType().getName().equals(activityMessage.getQName())) {
      getProblemHandler().add(new ParseProblem(variableAttr
          + " type is not applicable for the operation", serviceElem));
    }
    return variable;
  }

  void checkVariableProperties(VariableDefinition variable,
      CorrelationSetDefinition set, Element correlationElem) {
    Map varProperties = variable.getType().getPropertyAliases();
    if (varProperties != null) {
      Iterator setPropertyIt = set.getProperties().iterator();
      while (setPropertyIt.hasNext()) {
        Property setProperty = (Property) setPropertyIt.next();
        QName setPropertyName = setProperty.getQName();
        if (!varProperties.containsKey(setPropertyName)) {
          getProblemHandler().add(new ParseProblem(
              "property does not appear in variable: " + setPropertyName,
              correlationElem));
        }
      }
    }
    else {
      getProblemHandler().add(new ParseProblem(
          "variable contains no properties", correlationElem));
    }
  }

  // alarm properties
  // //////////////////////////////////////////////////////////////

  public Alarm readAlarm(Element element, CompositeActivity parent) {
    Alarm alarm = new Alarm();
    Element repeatEveryElem = XmlUtil.getElement(element,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_REPEAT_EVERY);
    if (repeatEveryElem != null) {
      alarm.setRepeatEvery(readExpression(repeatEveryElem, parent));
    }
    Element forElem = XmlUtil.getElement(element,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_FOR);
    if (forElem != null) {
      alarm.setFor(readExpression(forElem, parent));
    }
    else {
      Element untilElem = XmlUtil.getElement(element,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_UNTIL);
      if (untilElem != null) {
        alarm.setUntil(readExpression(untilElem, parent));
      }
      else if (repeatEveryElem == null) {
        getProblemHandler().add(new ParseProblem("alarm expression is missing",
            element));
      }
    }
    return alarm;
  }

  // child activities
  // //////////////////////////////////////////////////////////////

  public Activity readActivity(Element activityElem, CompositeActivity parent) {
    String name = activityElem.getLocalName();
    ActivityReader parser = (ActivityReader) activityReaders.get(name);
    if (parser == null) {
      throw new BpelException("no reader registered for activity", activityElem);
    }
    return parser.read(activityElem, parent);
  }

  protected Element getActivityElement(Element parent) {
    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (ActivityElementPredicate.evaluate(child)) {
        return (Element) child;
      }
    }
    return null;
  }

  protected Iterator getActivityElements(Element parent) {
    return new FilterIterator(new NodeIterator(parent),
        new ActivityElementPredicate());
  }

  public ActivityReader getActivityReader(String name) {
    return (ActivityReader) activityReaders.get(name);
  }

  private void initActivityReaders() {
    // walk through registered activity reader classes
    Iterator readerClassIt = activityReaderClasses.entrySet().iterator();
    while (readerClassIt.hasNext()) {
      Entry readerClassEntry = (Entry) readerClassIt.next();
      Object activityName = readerClassEntry.getKey();
      Class readerClass = (Class) readerClassEntry.getValue();
      try {
        ActivityReader reader = (ActivityReader) readerClass.newInstance();
        reader.setBpelReader(this);
        activityReaders.put(activityName, reader);
      }
      catch (Exception e) {
        log.warn("could not initialize activity reader: " + readerClass, e);
      }
    }
  }

  public ProblemHandler getProblemHandler() {
    if (problemHandler == null) {
      problemHandler = new DefaultProblemHandler();
    }
    return problemHandler;
  }

  public void setProblemHandler(ProblemHandler problemHandler) {
    this.problemHandler = problemHandler;
  }

  public static void registerActivityReaderClass(String activityName,
      Class readerClass) {
    if (ActivityReader.class.isAssignableFrom(readerClass)) {
      activityReaderClasses.put(activityName, readerClass);
      log.debug("registered activity reader: name=" + activityName + ", class="
          + readerClass.getName());
    }
    else {
      log.warn("not an activity reader: " + readerClass);
    }
  }

  private static void readActivityReaderClasses() {
    // load file from the classpath
    String resourceName = JbpmConfiguration.Configs.getString(RESOURCE_ACTIVITY_READERS);
    InputStream resourceStream = ClassLoaderUtil.getStream(resourceName);
    if (resourceStream == null) {
      throw new BpelException("activity readers resource not found: "
          + resourceName);
    }
    Element readersElem;
    try {
      // parse xml document
      readersElem = XmlUtil.getDocumentBuilder()
          .parse(resourceStream)
          .getDocumentElement();
      resourceStream.close();
    }
    catch (Exception e) {
      throw new BpelException("could not parse activity readers document", e);
    }
    // walk through activityReader elements
    Iterator readerElemIt = XmlUtil.getElements(readersElem,
        null,
        "activityReader");
    while (readerElemIt.hasNext()) {
      Element readerElem = (Element) readerElemIt.next();
      String activityName = readerElem.getAttribute("name");
      // load reader class
      String className = readerElem.getAttribute("class");
      Class readerClass = ClassLoaderUtil.loadClass(className);
      // register reader class
      registerActivityReaderClass(activityName, readerClass);
    }
  }

  static {
    readActivityReaderClasses();
  }

  /**
   * Gets the BPEL reader local to the current thread.
   * @return a thread-local BPEL reader
   */
  public static BpelReader getInstance() {
    return (BpelReader) bpelReaderLocal.get();
  }

  /**
   * Sets the BPEL reader local to the current thread. This allows for setting a
   * customized reader.
   * @param reader the new BPEL reader
   */
  public static void setInstance(BpelReader reader) {
    bpelReaderLocal.set(reader);
  }

  private static class ActivityElementPredicate implements Predicate {

    private static final Set activityNames = new HashSet(
        Arrays.asList(BpelConstants.BPEL_2_ACTIVITIES));

    /** {@inheritDoc} */
    public boolean evaluate(Object arg) {
      return evaluate((Node) arg);
    }

    static boolean evaluate(Node node) {
      // the node is an element
      return node.getNodeType() == Node.ELEMENT_NODE
      // in the BPEL namespace
          && BpelConstants.NS_BPWS.equals(node.getNamespaceURI())
          // and in the activities space
          && activityNames.contains(node.getLocalName());
    }
  }
}
