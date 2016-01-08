package org.jbpm.webapp.taskforms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmException;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * On the one hand, this class is responsible for calculating the form 
 * path to be included in the task form page, on the other hand, 
 * this class will provide a URL to the facelets framework that provides 
 * access to the form-file that is part of the process file attachments.
 * This class also caches the forms.xml data and the form files to 
 * reduce database traffic and xml parsing.
 */
public abstract class TaskForms {

  private static final long serialVersionUID = 1L;

  static final Map cachedTaskFormPaths = new HashMap();
  static final Map cachedTaskFormUrls = new HashMap();

  /** 
   * gives the task-form-path for a given task.
   * This will be used to generate a facelets include-link in the task form page.
   */
  public static String getTaskFormPath(Task task) {
    Map formPaths = getProcessFormPaths(task.getProcessDefinition());
    String taskName = task.getName();
    String path = (String) formPaths.get(taskName);
    log.debug("task form path for '"+task+"' is '"+path+"'");
    return path;
  }

  /**
   * gives the URL for a given task-form-path.
   * This is used to fetch the facelet .xhtml form resource from a process definition 
   * for the given task-form-path. 
   */
  public static URL getTaskFormUrl(ProcessDefinition processDefinition, String path) {
    String taskFormUrlKey = getTaskFormUrlKey(processDefinition, path);
    URL taskFormUrl = (URL) cachedTaskFormUrls.get(taskFormUrlKey);
    return taskFormUrl;
  }

  static Map getProcessFormPaths(ProcessDefinition processDefinition) {
    Long processDefinitionKey = new Long(processDefinition.getId());
    Map processTaskPaths = (Map) cachedTaskFormPaths.get(processDefinitionKey);
    synchronized(cachedTaskFormPaths) {
      if (processTaskPaths==null) {
        log.debug("initializing task form paths for '"+processDefinition+"'");
        processTaskPaths = new HashMap();
        FileDefinition fileDefinition = processDefinition.getFileDefinition();
        // get the forms.xml from the process files
        InputStream stream = fileDefinition.getInputStream("forms.xml");
        Document formsXmldocument = XmlUtil.parseXmlInputStream(stream);
        Element formsElement = formsXmldocument.getDocumentElement();
        
        // for each form element in the forms.xml
        Iterator iter = XmlUtil.elementIterator(formsElement, "form");
        while(iter.hasNext()) {
          Element formElement = (Element) iter.next();
          String taskName = formElement.getAttribute("task");
          String path = formElement.getAttribute("form");

          // First we fetch the file from the process definition with the given path.
          // We initialize the task form file URL now that we have the file definition at hand.
          byte[] taskFormBytes = fileDefinition.getBytes(path);
          
          // Then, the path is pre-fixed with a slash to make sure that it is 
          // resolved from the root of the webapplication and not relative to 
          // the page where the task form is included.
          if (!path.startsWith("/")) {
            path = "/"+path;
          }

          // create the url and add it to the cache
          URL taskFormUrl = createUrl(processDefinition, path, taskFormBytes);
          String taskFormUrlKey = getTaskFormUrlKey(processDefinition, path);
          cachedTaskFormUrls.put(taskFormUrlKey, taskFormUrl);
          // add the task-name-to-path-mapping to the processTaskPaths
          processTaskPaths.put(taskName, path);
        }
        log.debug("initialized form paths for "+processDefinition+": "+processTaskPaths);
        cachedTaskFormPaths.put(processDefinitionKey, processTaskPaths);
      }
    }
    return processTaskPaths;
  }

  static URL createUrl(ProcessDefinition processDefinition, String path, byte[] taskFormBytes) {
    URL taskFormUrl = null;
    URLStreamHandler streamHandler = new BytesStreamHandler(taskFormBytes);
    try {
      taskFormUrl = new URL("jbpm", "process", (int) processDefinition.getId(), path, streamHandler);
      String cacheKey = getTaskFormUrlKey(processDefinition, path);
      cachedTaskFormUrls.put(cacheKey, taskFormUrl);
    } catch (MalformedURLException e) {
      throw new JbpmException("form URL problem '"+path+"'", e);
    }
    return taskFormUrl;
  }

  static String getTaskFormUrlKey(ProcessDefinition processDefinition, String path) {
    return "/"+processDefinition.getId()+path;
  }

  static class BytesStreamHandler extends URLStreamHandler {
    byte[] bytes = null;
    BytesStreamHandler(byte[] bytes) {
      this.bytes = bytes;
    }
    protected URLConnection openConnection(URL u) throws IOException {
      return new URLConnection(u) {

        public void connect() throws IOException {
        }
        public InputStream getInputStream() throws IOException {
          return new ByteArrayInputStream(bytes);
        }
      };
    }
  }

  private static Log log = LogFactory.getLog(TaskForms.class);
}
