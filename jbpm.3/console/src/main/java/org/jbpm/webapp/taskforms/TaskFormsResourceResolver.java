package org.jbpm.webapp.taskforms;

import java.io.Serializable;
import java.net.URL;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ProcessDefinition;

import com.sun.facelets.impl.DefaultResourceResolver;
import com.sun.facelets.impl.ResourceResolver;

public class TaskFormsResourceResolver implements ResourceResolver, Serializable {
  
  private static final long serialVersionUID = 1L;

  static final DefaultResourceResolver defaultResourceResolver = new DefaultResourceResolver();

  String processDefinitionExpression = "#{taskBean.task.processDefinition}";

  public URL resolveUrl(String path) {
    // first look in the default implementation
    URL url = defaultResourceResolver.resolveUrl(path);
    
    // if the default impl doesn't find anything in the plain webapp
    if (url==null) {
      // We're going to search for the resource in the files of the current process definition.
      // The current process definition is accessed via the processDefinitionExpression
      
      ProcessDefinition processDefinition = getProcessDefinition();
      log.debug("searching for facelet resource in '"+processDefinition+"'");
      url = TaskForms.getTaskFormUrl(processDefinition, path);
      log.debug("jbpm-forms path resource '"+path+"' resolved to url '"+url+"'");
    }
    return url;
  }
  
  ProcessDefinition getProcessDefinition() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Application application = facesContext.getApplication();
    ValueBinding valueBinding = application.createValueBinding(processDefinitionExpression);
    return (ProcessDefinition) valueBinding.getValue(facesContext);
  }

  private static Log log = LogFactory.getLog(TaskFormsResourceResolver.class);
}
