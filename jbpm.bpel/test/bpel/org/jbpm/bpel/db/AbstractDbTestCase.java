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
package org.jbpm.bpel.db;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmContextTestHelper;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.db.ContextSession;
import org.jbpm.db.GraphSession;
import org.jbpm.db.LoggingSession;
import org.jbpm.db.MessagingSession;
import org.jbpm.db.SchedulerSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.logging.log.ProcessLog;
import org.jbpm.msg.db.TextMessage;
import org.jbpm.taskmgmt.exe.TaskInstance;

public class AbstractDbTestCase extends TestCase {
  
  protected static JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();

  protected JbpmContext jbpmContext = null;
  protected SchemaExport schemaExport = null;
  
  protected Session session = null;
  protected GraphSession graphSession = null;
  protected TaskMgmtSession taskMgmtSession = null;
  protected ContextSession contextSession = null;
  protected SchedulerSession schedulerSession = null;
  protected LoggingSession loggingSession = null;
  protected MessagingSession messagingSession = null;
  
  public void setUp() throws Exception {
    /* resetting JbpmConfiguration causes the instance retrieved above 
     * to be discarded from the JbpmConfiguration.instances map and MUST NOT be called
    JbpmConfigurationTestHelper.reset(); */
    /* contexts stack is empty at this point, no need to create it again
    JbpmContextTestHelper.reset(); */
    
    /* hibernate is configured to create schema automatically 
    createSchema(); */
    createJbpmContext();
    initializeMembers();
    
    log.debug("### starting "+getName()+" ####################################################");
  }

  public void tearDown() throws Exception {
    log.debug("### "+getName()+" done ####################################################");

    resetMembers();
    closeJbpmContext();
    /* hibernate creates schema automatically, no need to drop it 
    dropSchema(); */
    
    /* AbstractJbpmTestCase.tearDown(): resetting JbpmConfiguration causes the instance retrieved above 
    to be discarded from the JbpmConfiguration.instances map and MUST NOT be called
    JbpmConfigurationTestHelper.reset(); */
    JbpmContextTestHelper.reset();
  }

  public void beginSessionTransaction() {
    createJbpmContext();
    initializeMembers();
  }
  
  public void commitAndCloseSession() {
    closeJbpmContext();
    resetMembers();
  }
  
  protected void newTransaction() {
    try {
      commitAndCloseSession();
      beginSessionTransaction();
    } catch (Throwable t) {
      throw new RuntimeException("couldn't commit and start new transaction", t);
    }
  }

  public ProcessInstance saveAndReload(ProcessInstance pi) {
    jbpmContext.save(pi);
    newTransaction();
    return graphSession.loadProcessInstance(pi.getId());
  }
  public TaskInstance saveAndReload(TaskInstance taskInstance) {
    jbpmContext.save(taskInstance);
    newTransaction();
    return (TaskInstance) session.load(TaskInstance.class, new Long(taskInstance.getId()));
  }
  

  public BpelDefinition saveAndReload(BpelDefinition pd) {
    graphSession.saveProcessDefinition(pd);
    newTransaction();
    return (BpelDefinition) graphSession.loadProcessDefinition(pd.getId());
  }
  public ProcessLog saveAndReload(ProcessLog processLog) {
    loggingSession.saveProcessLog(processLog);
    newTransaction();
    return loggingSession.loadProcessLog(processLog.getId());
  }
  public TextMessage saveAndReload(TextMessage message) {
    session.saveOrUpdate(message);
    newTransaction();
    return (TextMessage) session.load(TextMessage.class, new Long(message.getId()));
  }
  
  protected void createSchema() {
    jbpmConfiguration.createSchema();
  }

  protected void dropSchema() {
    jbpmConfiguration.dropSchema();
  }

  protected void createJbpmContext() {
    jbpmContext = jbpmConfiguration.createJbpmContext();
  }

  protected void closeJbpmContext() {
    jbpmContext.close();
  }
  
  protected void initializeMembers() {
    session = jbpmContext.getSession();
    graphSession = jbpmContext.getGraphSession();
    taskMgmtSession = jbpmContext.getTaskMgmtSession();
    loggingSession = jbpmContext.getLoggingSession();
    schedulerSession = jbpmContext.getSchedulerSession();
    contextSession = jbpmContext.getContextSession();
    messagingSession = jbpmContext.getMessagingSession();
  }
  
  protected void resetMembers() {
    session = null;
    graphSession = null;
    taskMgmtSession = null;
    loggingSession = null;
    schedulerSession = null;
    contextSession = null;
    messagingSession = null;
  }
  
  private static Log log = LogFactory.getLog(AbstractDbTestCase.class);
}
