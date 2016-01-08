package org.jbpm.bpel.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmConfiguration;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/05 07:26:07 $
 */
public class DBSchemaServlet extends HttpServlet {
  
  public static final String ACTION_PARAM = "action";
  
  protected static final String CREATE_ACTION = "create";
  protected static final String DROP_ACTION = "drop";
  
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String operation = request.getParameter(ACTION_PARAM);
    if (operation.equals(CREATE_ACTION)) {
      createSchema(response);
    }
    else if (operation.equals(DROP_ACTION)) {
      dropSchema(response);
    }
    else {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid operation: " + operation);
    }
  }
  
  protected void createSchema(HttpServletResponse response) throws IOException {
    JbpmConfiguration.getInstance().createSchema();
    writeResponse(response, CREATE_ACTION);
  }
  
  protected void dropSchema(HttpServletResponse response) throws IOException {
    JbpmConfiguration.getInstance().dropSchema();
    writeResponse(response, DROP_ACTION); 
  }

  protected void writeResponse(HttpServletResponse response, String operation) throws IOException {
    response.getWriter().write("Database schema operation completed: " + operation);
    log("completed database schema operation: " + operation);
  }
}
