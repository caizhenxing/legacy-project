package org.jbpm.webapp.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

public class ParticipantBean implements Serializable {

  private static final long serialVersionUID = 1L;

  IdentityBean identityBean = null;
  JbpmBean jbpmBean = null;
  TaskBean taskBean = null;

  public List getPersonalTaskInstances() {
    String userName = JsfHelper.getAuthenticatedUserName();
    return jbpmBean.getJbpmContext().getTaskList(userName);
  }

  public List getPooledTaskInstances() {
    List pooledTaskInstances = null;
    List poolIds = identityBean.getUserPoolIds();
    if (poolIds!=null) {
      pooledTaskInstances = jbpmBean.getJbpmContext().getTaskMgmtSession().findPooledTaskInstances(poolIds);
    }
    return pooledTaskInstances;
  }

  public List getAvailableProcessDefinitions() {
    return jbpmBean.getJbpmContext().getGraphSession().findLatestProcessDefinitions();
  }

  public String selectTaskInstance() {
    long taskInstanceId = JsfHelper.getId("taskInstanceId");
    log.debug("selecting task instance "+taskInstanceId);
    taskBean.setTaskInstanceId(taskInstanceId);
    return "taskform";
  }

  public String takeTaskInstance() {
    long taskInstanceId = JsfHelper.getId("taskInstanceId");
    log.debug("taking task instance "+taskInstanceId);
    taskBean.setTaskInstanceId(taskInstanceId);
    return "taskform";
  }
  
  public String startNewProcessInstance() {
    long processDefinitionId = JsfHelper.getId("processDefinitionId");
    log.debug("starting new process instance "+processDefinitionId);

    // fetch the process definition from the db
    ProcessDefinition processDefinition = jbpmBean.getJbpmContext().getGraphSession().loadProcessDefinition(processDefinitionId);
    // create a new process execution
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    // create a new taskinstance for the start task
    TaskInstance taskInstance = processInstance.getTaskMgmtInstance().createStartTaskInstance();
    // Save the process instance along with the task instance
    jbpmBean.getJbpmContext().save(processInstance);

    // enter the task instance as view information for the task page
    taskBean.setTaskInstanceId(taskInstance.getId());
    taskBean.setTaskInstance(taskInstance);
    
    // go to the task page
    return "taskform";
  }

  // getters and setters //////////////////////////////////////////////////////

  public IdentityBean getIdentityBean() {
    return identityBean;
  }
  public void setIdentityBean(IdentityBean identityBean) {
    this.identityBean = identityBean;
  }
  public JbpmBean getJbpmBean() {
    return jbpmBean;
  }
  public void setJbpmBean(JbpmBean jbpmBean) {
    this.jbpmBean = jbpmBean;
  }
  public TaskBean getTaskBean() {
    return taskBean;
  }
  public void setTaskBean(TaskBean taskBean) {
    this.taskBean = taskBean;
  }

  private static Log log = LogFactory.getLog(ParticipantBean.class);
}
