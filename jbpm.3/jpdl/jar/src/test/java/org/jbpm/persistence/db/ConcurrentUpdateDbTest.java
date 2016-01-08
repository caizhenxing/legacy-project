package org.jbpm.persistence.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.StaleObjectStateException;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.db.AbstractDbTestCase;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.persistence.JbpmPersistenceException;

public class ConcurrentUpdateDbTest extends AbstractDbTestCase {

  public void testProcessEndEvent() throws Exception {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state name='start'>" +
      "    <transition to='state'/>" +
      "  </start-state>" +
      "  <state name='state'>" +
      "    <transition to='end'/>" +
      "  </state>" +
      "  <end-state name='end'/>" +
      "</process-definition>"
    );
    processDefinition = saveAndReload(processDefinition);
    
    // create the process instance
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    
    processInstance = saveAndReload(processInstance);
    processInstance.signal();
    jbpmContext.save(processInstance);
    
    JbpmContext concurrentContext = jbpmConfiguration.createJbpmContext();
    ProcessInstance sameProcessInstanceInDifferentSession = concurrentContext.loadProcessInstance(processInstance.getId());
    sameProcessInstanceInDifferentSession.end();
    concurrentContext.save(sameProcessInstanceInDifferentSession);
    log.debug("closing concurrent context");
    concurrentContext.close();
    
    try {
      log.debug("closing default test method context");
      commitAndCloseSession();
      fail("expected exception");
    } catch (JbpmException e) {
      assertEquals(JbpmPersistenceException.class, e.getCause().getClass());
      assertEquals(StaleObjectStateException.class, e.getCause().getCause().getClass());
    }
    
    beginSessionTransaction();
  }

  private static Log log = LogFactory.getLog(ConcurrentUpdateDbTest.class);
}
