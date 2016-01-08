package org.jbpm.webapp.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.taskmgmt.exe.TaskInstance;

public class TaskInstanceVariableMap implements Map, Serializable {

  private static final long serialVersionUID = 1L;
  
  TaskInstance taskInstance = null;
  Map updates = new HashMap();

  public TaskInstanceVariableMap() {
  }

  public void setTaskInstance(TaskInstance taskInstance) {
    log.debug("initializing task instance in variables map");
    this.taskInstance = taskInstance;
  }

  public Object get(Object variableName) {
    if (taskInstance==null) {
      log.debug("no task instance was set so returning null");
      return null;
    }
    Object value = taskInstance.getVariable((String)variableName);
    log.debug("fetched value '"+value+"' for task variable '"+variableName+"'");
    return value;
  }

  public Object put(Object variableName, Object value) {
    log.debug("setting task variable '"+variableName+"' to '"+value+"'");
    return updates.put((String)variableName, value);
  }

  public Map getUpdates() {
    return updates;
  }

  public int size() {
    throw new UnsupportedOperationException();
  }
  public boolean isEmpty() {
    throw new UnsupportedOperationException();
  }
  public boolean containsKey(Object arg0) {
    throw new UnsupportedOperationException();
  }
  public boolean containsValue(Object arg0) {
    throw new UnsupportedOperationException();
  }
  public Object remove(Object arg0) {
    throw new UnsupportedOperationException();
  }
  public void putAll(Map arg0) {
    throw new UnsupportedOperationException();
  }
  public void clear() {
    throw new UnsupportedOperationException();
  }
  public Set keySet() {
    throw new UnsupportedOperationException();
  }
  public Collection values() {
    throw new UnsupportedOperationException();
  }
  public Set entrySet() {
    throw new UnsupportedOperationException();
  }

  private static Log log = LogFactory.getLog(TaskInstanceVariableMap.class);
}
