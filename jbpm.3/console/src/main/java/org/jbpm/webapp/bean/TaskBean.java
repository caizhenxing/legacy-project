/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.webapp.bean;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.logging.exe.LoggingInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.def.TaskController;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.log.TaskAssignLog;
import org.jbpm.webapp.taskforms.TaskForms;

public class TaskBean {
  
  private static final String DEFAULT_TRANSITION_NAME = "default";

  IdentityBean identityBean = null;
  JbpmBean jbpmBean = null;

  long taskInstanceId = -1;
  String transitionName = DEFAULT_TRANSITION_NAME;
  String comment = null;
  TaskInstanceVariableMap variables = new TaskInstanceVariableMap();

  TaskInstance taskInstance = null;
  Task task = null;
  Token token = null;
  TaskController taskController = null;
  List availableTransitions = null;

  // this is the input required by this bean.  all other getters and 
  // commands are based on this property
  public void setTaskInstanceId(long taskInstanceId) {
    this.taskInstanceId = taskInstanceId;
    log.debug("selecting task instance "+taskInstanceId);
    this.taskInstance = jbpmBean.getJbpmContext().loadTaskInstance(taskInstanceId);
    this.token = taskInstance.getToken();
    this.variables.setTaskInstance(taskInstance);
  }

  public Map getVariables() {
    return variables;
  }

  public boolean isTaskInstancePresent() {
    return (taskInstanceId!=-1);
  }

  public TaskInstance getTaskInstance() {
    return taskInstance;
  }

  public Task getTask() {
    if ( (task==null)
         && (getTaskInstance()!=null)
       ) {
      task = taskInstance.getTask();
    }
    return task;
  }

  public String getTaskFormPath() {
    return TaskForms.getTaskFormPath(getTask());
  }

  public List getAvailableTransitions() {
    if ( (availableTransitions==null)
            && (getTaskInstance()!=null)
          ) {
      availableTransitions = taskInstance.getAvailableTransitions();
      if ((availableTransitions != null) && (availableTransitions.size() <= 1)) {
        availableTransitions = null;
      }
    }
    return availableTransitions;
  }

  // command methods
  
  public String showTaskForm() {
    setTaskInstanceId(JsfHelper.getId("taskInstanceId"));
    return "taskform";
  }
  
  public String showTaskDiagram() {
    setTaskInstanceId(JsfHelper.getId("taskInstanceId"));
    return "taskdiagram";
  }
  
  public String showTaskReassign() {
    setTaskInstanceId(JsfHelper.getId("taskInstanceId"));
    return "taskreassign";
  }
  
  public String save() {
    log.debug("saving the task parameters " + variables);

    // collect the parameter values from the values that were updated in the
    // parameters by jsf.
    
    saveComment();

    if ( (variables!=null)
         && (getTaskInstance()!=null)
       ) {
      Iterator iter = variables.getUpdates().entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry entry = (Map.Entry) iter.next();
        String variableName = (String) entry.getKey();
        Object value = entry.getValue();
        log.debug("saving variable '"+variableName+"': "+value);
        taskInstance.setVariable(variableName, value);
      }

      jbpmBean.getJbpmContext().save(taskInstance);
    }
    return "participanthome";
  }

  void saveComment() {
    if ( (comment!=null)
         && (!"".equals(comment))
         && (getTaskInstance()!=null)
       ) {
      taskInstance.addComment(comment);
    }
  }
  
  public Token getToken() {
    return token;
  }
  
  public List getComments() {
    if (token==null) {
      return null;
    }
    List comments = token.getComments();
    if ( (comments!=null) 
         && (comments.isEmpty())
       ) {
      // return null for empty collections to make the test succeed
      comments = null;
    }
    return comments;
  }

  public String saveAndClose() {
    save();
    return endTask();
  }

  public String cancel() {
    return "participanthome";
  }
  
  public String endTask() {
    if (getTaskInstance()!=null) {
      // which button was pressed ?
      log.debug("Submitted button:" + transitionName);
      // if the default transition button was pressed
      if (DEFAULT_TRANSITION_NAME.equals(transitionName)) {
        // just finish the task
        taskInstance.end();
      } else {
        // finish the task and provide the user selected transition
        taskInstance.end(transitionName);
      }
      // save the process after finishing the task
      jbpmBean.getJbpmContext().save(taskInstance);
      
      // collect process feedback
      ProcessInstance processInstance = taskInstance.getTaskMgmtInstance().getProcessInstance();
      if (processInstance.hasEnded()) {
        JsfHelper.addMessage("The process has finished.");
      } else {
        LoggingInstance loggingInstance = processInstance.getLoggingInstance();
        List assignmentLogs = loggingInstance.getLogs(TaskAssignLog.class);

        log.debug("assignmentlogs: " + assignmentLogs);

        if (assignmentLogs.size() == 1) {
          TaskAssignLog taskAssignLog = (TaskAssignLog) assignmentLogs.get(0);
          JsfHelper.addMessage("A new task has been assigned to '" + taskAssignLog.getTaskNewActorId() + "'");

        } else if (assignmentLogs.size() > 1) {
          String msg = "New tasks have been assigned to: ";
          Iterator iter = assignmentLogs.iterator();
          while (iter.hasNext()) {
            TaskAssignLog taskAssignLog = (TaskAssignLog) iter.next();
            msg += taskAssignLog.getActorId();
            if (iter.hasNext())
              msg += ", ";
          }
          msg += ".";
          JsfHelper.addMessage(msg);
        }
      }
    }

    return "participanthome";
  }

  // getters and setters //////////////////////////////////////////////////////

  public void setTaskInstance(TaskInstance taskInstance) {
    this.taskInstance = taskInstance;
  }
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
  public long getTaskInstanceId() {
    return taskInstanceId;
  }
  public void setAvailableTransitions(List availableTransitions) {
    this.availableTransitions = availableTransitions;
  }
  public void setTask(Task task) {
    this.task = task;
  }
  public void setTaskController(TaskController taskController) {
    this.taskController = taskController;
  }
  public String getTransitionName() {
    return transitionName;
  }
  public void setTransitionName(String transitionName) {
    this.transitionName = transitionName;
  }
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  
  private static final Log log = LogFactory.getLog(TaskBean.class);
}
