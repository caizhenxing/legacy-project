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
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.par.ProcessArchive;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class DeployServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  
  public static final String ARCHIVE_PARAM = "archive";

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String archiveLocation = request.getParameter(ARCHIVE_PARAM);
    
    log("deploying process archive: " + archiveLocation);
    
    ProcessArchive archive;
    try {
      URL archiveURL = new URL(archiveLocation);
      InputStream archiveStream = archiveURL.openStream();
      archive = new ProcessArchive(new ZipInputStream(archiveStream));
      archiveStream.close();
    }
    catch (IOException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
          "Process deployment failed; could not read the process archive");
      log("could not read process archive: " + archiveLocation, e);
      return;
    }
    
    ProcessDefinition definition = archive.parseProcessDefinition();
    List problems = archive.getProblems();
    if (problems.isEmpty()) {
      PrintWriter out = response.getWriter();
      JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
      try {
        jbpmContext.deployProcessDefinition(definition);
        
        out.print("<html><body>Process deployment successful: ");
        out.print(archiveLocation);
        out.print("</body></html>");
        
        log("deployed process archive: " + archiveLocation);
      }
      catch (RuntimeException e) {
        jbpmContext.setRollbackOnly();        
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
            "Process deployment failed; encountered a technical problem");
        log("could not deploy process archive", e);
      }
      finally {
        jbpmContext.close();
      }
    }
    else {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
          "Process deployment failed; found problems in the definition");
      log("found process problems: archive=" + archiveLocation + ", problems=" + problems);
    }
  }
}
