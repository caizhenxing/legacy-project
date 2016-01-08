/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the JBPM BPEL PUBLIC LICENSE AGREEMENT as
 * published by JBoss Inc.; either version 1.0 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.jbpm.bpel.web.deploy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmConfiguration;

public class DBSchemaServlet extends HttpServlet {
  
  public static final String OPERATION_PARAM = "operation";
  
  protected static final String CREATE_OPER = "create";
  protected static final String DROP_OPER = "drop";
  
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String operation = request.getParameter(OPERATION_PARAM);
    if (operation.equals(CREATE_OPER)) {
      createSchema(response);
    }
    else if (operation.equals(DROP_OPER)) {
      dropSchema(response);
    }
    else {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid operation: " + operation);
    }
  }
  
  protected void createSchema(HttpServletResponse response) throws IOException {
    JbpmConfiguration.getInstance().createSchema();
    writeResponse(response, CREATE_OPER);
  }
  
  protected void dropSchema(HttpServletResponse response) throws IOException {
    JbpmConfiguration.getInstance().dropSchema();
    writeResponse(response, DROP_OPER); 
  }

  protected void writeResponse(HttpServletResponse response, String operation) throws IOException {
    response.getWriter().write("Database schema operation completed: " + operation);
    log("completed database schema operation: " + operation);
  }
}
