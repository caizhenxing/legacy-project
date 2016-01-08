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
package org.jbpm.bpel.integration.jms;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.bpel.app.AppDescriptor;
import org.jbpm.bpel.app.ScopeMatcher;
import org.jbpm.bpel.app.AppPartnerRole.Initiate;
import org.jbpm.bpel.db.IntegrationSession;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.OnEvent;
import org.jbpm.bpel.def.Pick;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.StructuredActivity.Begin;
import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.catalog.ServiceCatalog;
import org.jbpm.bpel.integration.client.SoapClient;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.def.Replier;
import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.bpel.integration.exe.EndpointReferenceFactory;
import org.jbpm.bpel.integration.exe.PartnerLinkInstance;
import org.jbpm.bpel.integration.exe.wsa.WsaConstants;
import org.jbpm.bpel.xml.AppDescriptorReader;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelException;
import org.jbpm.bpel.xml.ProblemCollector;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.exe.Token;
import org.jbpm.jpdl.xml.Problem;
import org.jbpm.svc.Services;
import org.xml.sax.InputSource;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/08/21 01:05:59 $
 */
public class IntegrationControl {

  private Connection jmsConnection;
  private AppDescriptor appDescriptor;

  private Map partnerLinkEntries;

  private List startListeners = Collections.EMPTY_LIST;
  private Map requestListeners = new Hashtable();
  private Map outstandingRequests = new Hashtable();
  private Map partnerClients = new HashMap();

  public static final String RESOURCE_APP_DESCRIPTOR = "resource.app.descriptor";
  public static final String DEFAULT_APP_DESCRIPTOR = "/bpel-application.xml";

  public static final String NAME_JMS_CONTEXT = "name.jms.context";
  public static final String DEFAULT_JMS_CONTEXT = "java:comp/env/jms";

  public static final String NAME_BIND_CONTEXT = "name.bind.context";
  public static final String DEFAULT_BIND_CONTEXT = "jbpmBpel";

  private static final QName DEFAULT_REFERENCE_NAME = new QName(
      WsaConstants.NS_ADDRESSING, WsaConstants.ELEM_ENDPOINT_REFERENCE);

  private static final Log log = LogFactory.getLog(IntegrationControl.class);
  private static final long serialVersionUID = 1L;

  public Connection getJmsConnection() {
    return jmsConnection;
  }

  public AppDescriptor getAppDescriptor() {
    return appDescriptor;
  }

  public void setAppDescriptor(AppDescriptor appDescriptor) {
    this.appDescriptor = appDescriptor;
  }

  public BpelDefinition findProcessDefinition(JbpmContext jbpmContext) {
    // the descriptor references a particular process definition
    AppDescriptor appDescriptor = getAppDescriptor();
    String name = appDescriptor.getName();
    Integer version = appDescriptor.getVersion();
    // use the existing context to find the definition
    GraphSession graphSession = jbpmContext.getGraphSession();
    BpelDefinition process;
    // check for a version indicator
    if (version != null) {
      // find a specific version of the process
      process = (BpelDefinition) graphSession.findProcessDefinition(name,
          version.intValue());
    }
    else {
      // just retrieve the latest one
      process = (BpelDefinition) graphSession.findLatestProcessDefinition(name);
    }
    if (process == null) {
      throw new BpelException("process not found: name=" + name + ", version="
          + version);
    }
    return process;
  }

  public PartnerLinkEntry getPartnerLinkEntry(PartnerLinkDefinition partnerLink) {
    return (PartnerLinkEntry) partnerLinkEntries.get(new Long(
        partnerLink.getId()));
  }

  public Map getRequestListeners() {
    return Collections.unmodifiableMap(requestListeners);
  }

  public void addRequestListener(RequestListener requestListener) {
    Object listenerKey = createKey(requestListener.getReceiverId(),
        requestListener.getTokenId());
    requestListeners.put(listenerKey, requestListener);
  }

  public RequestListener removeRequestListener(Receiver receiver, Token token) {
    Object listenerKey = createKey(receiver.getId(), token.getId());
    return (RequestListener) requestListeners.remove(listenerKey);
  }

  private static Object createKey(long receiverId, long tokenId) {
    return new RequestListener.Key(receiverId, tokenId);
  }

  public Map getOutstandingRequests() {
    return Collections.unmodifiableMap(outstandingRequests);
  }

  public void addOutstandingRequest(Receiver receiver, Token token,
      OutstandingRequest request) {
    Object key = createKey(receiver.getPartnerLink().getInstance(token),
        receiver.getOperation(),
        receiver.getMessageExchange());
    if (outstandingRequests.put(key, request) != null) {
      throw new BpelFaultException(BpelConstants.FAULT_CONFLICTING_REQUEST);
    }
    log.debug("added outstanding request: receiver=" + receiver + ", token="
        + token + ", request=" + request);
  }

  public OutstandingRequest removeOutstandingRequest(Replier replier,
      Token token) {
    Object key = createKey(replier.getPartnerLink().getInstance(token),
        replier.getOperation(),
        replier.getMessageExchange());
    OutstandingRequest request = (OutstandingRequest) outstandingRequests.remove(key);
    if (request == null) {
      throw new BpelFaultException(BpelConstants.FAULT_MISSING_REQUEST);
    }
    log.debug("removed outstanding request: replier=" + replier + ", token="
        + token + ", request=" + request);
    return request;
  }

  private static Object createKey(PartnerLinkInstance partnerLinkInstance,
      Operation operation, String messageExchange) {
    return new OutstandingRequest.Key(getOrAssignId(partnerLinkInstance),
        operation.getName(), messageExchange);
  }

  public Map getPartnerClients() {
    return Collections.unmodifiableMap(partnerClients);
  }

  public SoapClient getPartnerClient(PartnerLinkInstance instance) {
    Long instanceId = new Long(getOrAssignId(instance));
    SoapClient partnerClient;
    synchronized (partnerClients) {
      // retrieve cached port consumer
      partnerClient = (SoapClient) partnerClients.get(instanceId);
      if (partnerClient == null) {
        // no cached consumer, create one from partner endpoint reference
        EndpointReference partnerRef = instance.getPartnerReference();
        if (partnerRef == null) {
          // no partner reference, create one containing only the port type as
          // selection criterion
          partnerRef = createPartnerReference(instance.getDefinition());
          instance.setPartnerReference(partnerRef);
          log.debug("initialized partner reference: instance=" + instance
              + ", reference=" + partnerRef);
        }
        // select a port from the service catalog with the criteria known at
        // this point
        Port port = partnerRef.selectPort(getServiceCatalog());
        log.debug("selected partner port: instance=" + instance + ", port="
            + port.getName());
        // create a client for that port
        partnerClient = new SoapClient(port);
        partnerClients.put(instanceId, partnerClient);
      }
    }
    return partnerClient;
  }

  private static long getOrAssignId(PartnerLinkInstance instance) {
    long instanceId = instance.getId();
    // in case instance is transient, assign an identifier to it
    if (instanceId == 0L) {
      Services.assignId(instance);
      instanceId = instance.getId();
    }
    return instanceId;
  }

  EndpointReference createPartnerReference(PartnerLinkDefinition definition) {
    PartnerLinkEntry entry = getPartnerLinkEntry(definition);
    Initiate initiate = entry.getPartnerInitiate();
    EndpointReference partnerReference;
    if (Initiate.STATIC.equals(initiate)) {
      partnerReference = entry.getPartnerReference();
    }
    else if (Initiate.PULL.equals(initiate)) {
      EndpointReferenceFactory refFactory = EndpointReferenceFactory.getInstance(DEFAULT_REFERENCE_NAME,
          null);
      partnerReference = refFactory.createEndpointReference();
    }
    else {
      throw new BpelFaultException(
          BpelConstants.FAULT_UNINITIALIZED_PARTNER_ROLE);
    }
    return partnerReference;
  }

  public ServiceCatalog getServiceCatalog() {
    return getAppDescriptor().getServiceCatalog();
  }

  public List getStartListeners() {
    return Collections.unmodifiableList(startListeners);
  }

  /**
   * Prepares inbound message activities annotated to create a process instance
   * for receiving requests.
   */
  public void enableInboundMessageActivities(JbpmContext jbpmContext)
      throws NamingException, JMSException {
    InitialContext initialContext = new InitialContext();
    try {
      // read integration settings
      readAppDescriptor();

      // publish partner link information to JNDI
      BpelDefinition process = findProcessDefinition(jbpmContext);
      createPartnerLinkEntries(initialContext, process);

      // open a jms connection
      createJmsConnection(initialContext);

      // enable start IMAs
      StartListenersBuilder builder = new StartListenersBuilder(this);
      builder.visit(process);
      startListeners = builder.getStartListeners();

      // enable outstanding IMAs
      IntegrationSession integrationSession = IntegrationSession.getInstance(jbpmContext);
      IntegrationService integrationService = Receiver.getIntegrationService(jbpmContext);

      // receive
      Iterator receiveTokenIt = integrationSession.findReceiveTokens(process)
          .iterator();
      while (receiveTokenIt.hasNext()) {
        Token token = (Token) receiveTokenIt.next();
        Receive receive = (Receive) token.getNode();
        integrationService.receive(receive.getReceiver(), token);
      }

      // pick
      Iterator pickTokenIt = integrationSession.findPickTokens(process)
          .iterator();
      while (pickTokenIt.hasNext()) {
        Token token = (Token) pickTokenIt.next();
        // pick points activity token to begin mark
        Begin begin = (Begin) token.getNode();
        Pick pick = (Pick) begin.getCompositeActivity();
        integrationService.receive(pick.getOnMessages(), token);
      }

      // event
      Iterator eventTokenIt = integrationSession.findEventTokens(process)
          .iterator();
      while (eventTokenIt.hasNext()) {
        Token token = (Token) eventTokenIt.next();
        // scope points events token to itself
        Scope scope = (Scope) token.getNode();
        Iterator onEventsIt = scope.getOnEvents().iterator();
        while (onEventsIt.hasNext()) {
          OnEvent onEvent = (OnEvent) onEventsIt.next();
          integrationService.receive(onEvent.getReceiver(), token);
        }
      }

      // start message delivery
      jmsConnection.start();
    }
    finally {
      initialContext.close();
    }
  }

  /**
   * Prevents inbound message activities annotated to create a process instance
   * from further receiving requests.
   */
  public void disableInboundMessageActivities(JbpmContext jbpmContext)
      throws JMSException, NamingException {
    // disable start IMAs
    Iterator startListenerIt = startListeners.iterator();
    while (startListenerIt.hasNext()) {
      StartListener startListener = (StartListener) startListenerIt.next();
      startListener.close();
    }

    // disable outstanding IMAs
    Iterator requestListenerIt = requestListeners.values().iterator();
    while (requestListenerIt.hasNext()) {
      RequestListener requestListener = (RequestListener) requestListenerIt.next();
      requestListener.close();
    }

    // release jms connection
    closeJmsConnection();

    // remove partner link entries
    InitialContext initialContext = new InitialContext();
    try {
      removePartnerLinkEntries(initialContext,
          findProcessDefinition(jbpmContext));
    }
    finally {
      initialContext.close();
    }

    // clear inner data structures
    reset();
  }

  void close() throws JMSException {
    // release jms connection
    closeJmsConnection();
    // clear inner data structures
    reset();
  }

  void readAppDescriptor() {
    String appDescriptorResource = DEFAULT_APP_DESCRIPTOR;
    if (JbpmConfiguration.Configs.hasObject(RESOURCE_APP_DESCRIPTOR)) {
      // use the configured resource instead
      appDescriptorResource = JbpmConfiguration.Configs.getString(RESOURCE_APP_DESCRIPTOR);
    }
    // locate the app descriptor using the context class loader
    URL appDescriptorURL = Thread.currentThread()
        .getContextClassLoader()
        .getResource(appDescriptorResource);
    if (appDescriptorURL == null) {
      log.debug("app descriptor not found by context class loader, falling back to current class loader");
      // fall back to the loader of this class
      appDescriptorURL = getClass().getResource(appDescriptorResource);
      // if the descriptor is really not there, halt
      if (appDescriptorURL == null) {
        throw new BpelException("could not find application descriptor: "
            + appDescriptorResource);
      }
    }
    AppDescriptorReader appDescriptorReader = AppDescriptorReader.getInstance();
    // prepare custom error handling
    ProblemCollector problemHandler = new ProblemCollector(
        appDescriptorResource);
    appDescriptorReader.setProblemHandler(problemHandler);
    // parse content
    appDescriptor = new AppDescriptor();
    appDescriptorReader.read(appDescriptor, new InputSource(
        appDescriptorURL.toExternalForm()));
    // if the descriptor is buggy, halt
    if (Problem.containsProblemsOfLevel(problemHandler.getProblems(),
        Problem.LEVEL_ERROR)) {
      throw new BpelException("errors found in bpel application descriptor");
    }
  }

  void createPartnerLinkEntries(InitialContext initialContext,
      BpelDefinition process) throws NamingException {
    // match scopes with their configurations
    Map scopeConfigurations = new ScopeMatcher().match(process,
        getAppDescriptor());
    // lookup destinations & bind port entries
    PartnerLinkEntriesBuilder builder = new PartnerLinkEntriesBuilder(
        scopeConfigurations, getJmsContext(initialContext),
        getProcessContext(initialContext, process));
    builder.visit(process);
    partnerLinkEntries = builder.getPartnerLinkEntries();
  }

  void removePartnerLinkEntries(InitialContext initialContext,
      BpelDefinition process) throws NamingException {
    Context processContext = getProcessContext(initialContext, process);
    Iterator entryIt = partnerLinkEntries.values().iterator();
    while (entryIt.hasNext()) {
      PartnerLinkEntry entry = (PartnerLinkEntry) entryIt.next();
      // does the process play a role in this relation?
      if (entry.getDestination() != null) {
        processContext.unbind(entry.getPortName());
      }
    }
    partnerLinkEntries = null;
  }

  void createJmsConnection(InitialContext initialContext)
      throws NamingException, JMSException {
    // retrieve connection factory
    Context jmsContext = getJmsContext(initialContext);
    ConnectionFactory jmsConnectionFactory = (ConnectionFactory) jmsContext.lookup(IntegrationConstants.CONNECTION_FACTORY_NAME);
    log.debug("retrieved jms connection factory: "
        + IntegrationConstants.CONNECTION_FACTORY_NAME);
    // create a connection
    jmsConnection = jmsConnectionFactory.createConnection();
  }

  void closeJmsConnection() throws JMSException {
    jmsConnection.close();
    jmsConnection = null;
  }

  void reset() {
    appDescriptor = null;
    startListeners = Collections.EMPTY_LIST;
    requestListeners.clear();
    outstandingRequests.clear();
    partnerClients.clear();
  }

  public Context getProcessContext(InitialContext initialContext,
      JbpmContext jbpmContext) throws NamingException {
    BpelDefinition process = findProcessDefinition(jbpmContext);
    return getProcessContext(initialContext, process);
  }

  private static Context getProcessContext(InitialContext initialContext,
      BpelDefinition process) throws NamingException {
    return getProcessContext(initialContext,
        process.getName(),
        process.getVersion());
  }

  private static Context getProcessContext(InitialContext initialContext,
      String processName, int processVersion) throws NamingException {
    String bindContextName = DEFAULT_BIND_CONTEXT;
    if (JbpmConfiguration.Configs.hasObject(NAME_BIND_CONTEXT)) {
      bindContextName = JbpmConfiguration.Configs.getString(NAME_BIND_CONTEXT);
    }
    Context bindContext = lookupOrCreateSubcontext(initialContext,
        bindContextName);
    Context processContext = lookupOrCreateSubcontext(bindContext, processName);
    return lookupOrCreateSubcontext(processContext,
        Integer.toString(processVersion));
  }

  private static Context lookupOrCreateSubcontext(Context namingContext,
      String subcontextName) throws NamingException {
    Context subcontext;
    try {
      subcontext = (Context) namingContext.lookup(subcontextName);
      log.debug("found subcontext: " + subcontextName);
    }
    catch (NamingException e) {
      log.debug("subcontext not found, creating it: " + subcontextName);
      subcontext = namingContext.createSubcontext(subcontextName);
    }
    return subcontext;
  }

  public static Context getJmsContext(InitialContext initialContext) {
    String jmsContextName = DEFAULT_JMS_CONTEXT;
    if (JbpmConfiguration.Configs.hasObject(NAME_JMS_CONTEXT)) {
      jmsContextName = JbpmConfiguration.Configs.getString(NAME_JMS_CONTEXT);
    }
    Context jmsContext;
    try {
      jmsContext = (Context) initialContext.lookup(jmsContextName);
    }
    catch (NamingException e) {
      log.debug("could not retrieve jms context, falling back to initial context");
      jmsContext = initialContext;
    }
    return jmsContext;
  }

  public static IntegrationControl getInstance(
      JbpmConfiguration jbpmConfiguration) {
    JmsIntegrationServiceFactory serviceFactory = (JmsIntegrationServiceFactory) jbpmConfiguration.getServiceFactory(IntegrationService.SERVICE_NAME);
    return serviceFactory.getIntegrationControl();
  }
}
