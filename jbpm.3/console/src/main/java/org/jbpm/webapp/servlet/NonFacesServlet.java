package org.jbpm.webapp.servlet;

import java.io.IOException;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.webapp.bean.TaskBean;

public class NonFacesServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    LifecycleFactory lFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
    Lifecycle lifecycle = lFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
    FacesContextFactory fcFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
    FacesContext facesContext = fcFactory.getFacesContext(getServletContext(), request, response, lifecycle);
    Application application = facesContext.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    ExternalContext externalContext = facesContext.getExternalContext();
    Map requestMap = externalContext.getRequestMap();

    String taskInstanceIdText = request.getParameter("id");
    if (taskInstanceIdText!=null) {
      log.debug("redirecting to task "+taskInstanceIdText);
      requestMap.put("taskInstanceId", taskInstanceIdText);
      TaskBean taskBean = (TaskBean) application.getVariableResolver().resolveVariable(facesContext, "taskBean");
      String outcome = taskBean.showTaskForm();
      taskBean.setTaskInstanceId(Long.parseLong(taskInstanceIdText));
      UIViewRoot view = viewHandler.createView(facesContext, "/common/taskform.jsf");
      facesContext.setViewRoot(view);
      NavigationHandler navigationHandler = application.getNavigationHandler();
      navigationHandler.handleNavigation(facesContext, null, outcome);
    } else {
      throw new ServletException("parameter 'id' is not supplied, but required and should be a valid task instance id");
    }

    lifecycle.render(facesContext);
  }
  
  private static Log log = LogFactory.getLog(NonFacesServlet.class);
}
