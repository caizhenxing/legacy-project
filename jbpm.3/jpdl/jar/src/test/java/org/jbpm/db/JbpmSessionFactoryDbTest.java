package org.jbpm.db;

import junit.framework.TestCase;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;

public class JbpmSessionFactoryDbTest extends TestCase {

  public void testJbpmSessionFactory() {
    JbpmSessionFactory jbpmSessionFactory = JbpmSessionFactory.getInstance();
    JbpmSchema jbpmSchema = new JbpmSchema(jbpmSessionFactory.getConfiguration());
    jbpmSchema.createSchema();
    
    JbpmSession jbpmSession = jbpmSessionFactory.openJbpmSessionAndBeginTransaction();
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition name='old api check'>" +
      "  <start-state name='start'>" +
      "    <transition to='end' />" +
      "  </start-state>" +
      "  <end-state name='end' />" +
      "</process-definition>"
    );
    jbpmSession.getGraphSession().saveProcessDefinition(processDefinition);
    jbpmSession.commitTransactionAndClose();
    
    jbpmSession = jbpmSessionFactory.openJbpmSessionAndBeginTransaction();
    processDefinition = jbpmSession.getGraphSession().findLatestProcessDefinition("old api check");
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    processInstance.signal();
    assertEquals("end", processInstance.getRootToken().getNode().getName());
    jbpmSession.commitTransactionAndClose();
  }
}
