package org.jbpm.db;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;

public class DeleteProcessInstanceDbTest extends AbstractDbTestCase {

  public void testDeleteProcessInstance() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition name='make fondue'>" +
      "  <start-state>" +
      "    <transition to='buy cheese' />" +
      "  </start-state>" +
      "  <state name='buy cheese' />" +
      "</process-definition>"
    );    
    jbpmContext.deployProcessDefinition(processDefinition);
    newTransaction();
    
    ProcessInstance processInstance = jbpmContext.newProcessInstance("make fondue");
    processInstance.signal();
    
    processInstance = saveAndReload(processInstance);
    
    jbpmContext.getGraphSession().deleteProcessInstance(processInstance);
    
    newTransaction();
    
    assertEquals(0, session.createQuery("from org.jbpm.graph.exe.ProcessInstance").list().size());
  }

  public void testDeleteProcessInstanceWithTask() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition name='make fondue'>" +
      "  <start-state>" +
      "    <transition to='buy cheese' />" +
      "  </start-state>" +
      "  <task-node name='buy cheese'>" +
      "    <task />" +
      "  </task-node>" +
      "</process-definition>"
    );    
    jbpmContext.deployProcessDefinition(processDefinition);
    newTransaction();
    
    ProcessInstance processInstance = jbpmContext.newProcessInstance("make fondue");
    processInstance.signal();
    
    processInstance = saveAndReload(processInstance);
    
    jbpmContext.getGraphSession().deleteProcessInstance(processInstance);
    
    newTransaction();
    
    assertEquals(0, session.createQuery("from org.jbpm.graph.exe.ProcessInstance").list().size());
  }

  public void testDeleteProcessInstanceWithSubProcessInstance() {
    ProcessDefinition buyCheese = ProcessDefinition.parseXmlString(
      "<process-definition name='buy cheese'>" +
      "  <start-state>" +
      "    <transition to='find shop' />" +
      "  </start-state>" +
      "  <state name='find shop' />" +
      "</process-definition>"
    );    
    jbpmContext.deployProcessDefinition(buyCheese);
    
    ProcessDefinition makeFondue = ProcessDefinition.parseXmlString(
      "<process-definition name='make fondue'>" +
      "  <start-state>" +
      "    <transition to='buy cheese' />" +
      "  </start-state>" +
      "  <process-state name='buy cheese'>" +
      "    <sub-process name='buy cheese' />" +
      "  </process-state>" +
      "</process-definition>"
    );    
    jbpmContext.deployProcessDefinition(makeFondue);
    newTransaction();
    
    ProcessInstance processInstance = jbpmContext.newProcessInstance("make fondue");
    processInstance.signal();
    
    processInstance = saveAndReload(processInstance);
    
    jbpmContext.getGraphSession().deleteProcessInstance(processInstance);
    
    newTransaction();
    
    assertEquals(0, session.createQuery("from org.jbpm.graph.exe.ProcessInstance").list().size());
  }

  public void testDeleteProcessInstanceWithConcurrentPathsOfExecution() {
    ProcessDefinition makeFondue = ProcessDefinition.parseXmlString(
      "<process-definition name='make fondue'>" +
      "  <start-state>" +
      "    <transition to='fork' />" +
      "  </start-state>" +
      "  <fork name='fork'>" +
      "    <transition name='cheese' to='buy cheese' />" +
      "    <transition name='bread' to='bake bread' />" +
      "  </fork>" +
      "  <state name='buy cheese' />" +
      "  <state name='bake bread' />" +
      "</process-definition>"
    );    
    jbpmContext.deployProcessDefinition(makeFondue);
    newTransaction();
    
    ProcessInstance processInstance = jbpmContext.newProcessInstance("make fondue");
    processInstance.signal();
    
    processInstance = saveAndReload(processInstance);
    
    jbpmContext.getGraphSession().deleteProcessInstance(processInstance);
    
    newTransaction();
    
    assertEquals(0, session.createQuery("from org.jbpm.graph.exe.ProcessInstance").list().size());
  }
}
