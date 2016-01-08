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
package org.jbpm.webapp.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

public class ProcessUploadServlet extends HttpServlet {
  
  static JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();

  private static final long serialVersionUID = 1L;
  public static final String UPLOAD_TYPE_DEFINITION = "definition";
  public static final String UPLOAD_TYPE_ARCHIVE = "archive";

  public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //upload from gpd has url mapping: /upload
    //from admin console: /uploadDefinition
    
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      boolean isUploadFromGPD = request.getRequestURI().indexOf("uploadDefinition") < 0;
      if(isUploadFromGPD) {
        response.setContentType("text/html");
        response.getWriter().println(handleRequest(request, isUploadFromGPD));
      } else {
        try {
          this.handleRequest(request, isUploadFromGPD);
          RequestDispatcher rd = request.getRequestDispatcher("/faces/admin.jsp");
          rd.forward(request, response);
        } catch (ServletException e) {
          throw new RuntimeException("Error during process upload", e);
        }
      }
    } finally {
      jbpmContext.close();
    }
  }

  private String handleRequest(HttpServletRequest request, boolean uploadFromGPD) {
    //check if request is multipart content
    if (!FileUpload.isMultipartContent(request)) {
      if (uploadFromGPD) {
        log.debug("Not a multipart request");
        return "Not a multipart request";
      } else {
        throw new RuntimeException("Not a multipart request");
      }
    }

    try {
      DiskFileUpload fileUpload = new DiskFileUpload();
      List list = fileUpload.parseRequest(request);
      if (uploadFromGPD) {
        Iterator iterator = list.iterator();
        if (!iterator.hasNext()) {
          log.debug("No process file in the request");
          return "No process file in the request";
        }
        FileItem fileItem = (FileItem) iterator.next();
        if (fileItem.getContentType().indexOf("application/x-zip-compressed") == -1) {
          log.debug("Not a process archive");
          return "Not a process archive";
        }
        return doDeployArchive(fileItem, uploadFromGPD);
      } else {
        if (list.size() != 2) {
          throw new RuntimeException("No process file in the request");
        }
        FileItem fileItem = (FileItem) list.get(0);
        FileItem paramItem = (FileItem) list.get(1);
        String uploadType = paramItem.getString();
        if (uploadType.equals(UPLOAD_TYPE_ARCHIVE)) {
          if (fileItem.getContentType().indexOf("application/x-zip-compressed") == -1 && fileItem.getContentType().indexOf("application/zip") == -1)
          {
            throw new RuntimeException("Not a process archive");
          }
          this.doDeployArchive(fileItem, uploadFromGPD);
        }
        if (uploadType.equals(UPLOAD_TYPE_DEFINITION)) {
          if (fileItem.getContentType().indexOf("text/xml") == -1) {
            throw new RuntimeException("Not a process definition");
          }
          this.doDeployDefinition(fileItem);
        }
      }
    } catch (FileUploadException e) {
      if (uploadFromGPD) {
        e.printStackTrace();
        return "FileUploadException";
      } else {
        throw new RuntimeException("Exception during process upload", e);
      }
    }
    return null;
  }

  private void doDeployDefinition(FileItem fileItem) {
    try {
      InputStream inputStream = fileItem.getInputStream();
      JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
      ProcessDefinition processDefinition = ProcessDefinition.parseXmlInputStream(inputStream);
      log.debug("Created a processdefinition : " + processDefinition.getName());
      jbpmContext.deployProcessDefinition(processDefinition);
      inputStream.close();
    } catch (IOException e) {
      throw new RuntimeException("Exception during process upload", e);
    }
  }

  private String doDeployArchive(FileItem fileItem, boolean uploadFromGPD) {
    try {
      ZipInputStream zipInputStream = new ZipInputStream(fileItem.getInputStream());
      JbpmContext jbpmContext = JbpmContext.getCurrentJbpmContext();
      ProcessDefinition processDefinition = ProcessDefinition.parseParZipInputStream(zipInputStream);
      log.debug("Created a processdefinition : " + processDefinition.getName());
      jbpmContext.deployProcessDefinition(processDefinition);
      zipInputStream.close();
      return "Deployed archive " + processDefinition.getName() + " successfully";
    } catch (IOException e) {
      if (!uploadFromGPD) {
        throw new RuntimeException("Exception during process upload", e);
      } else {
        return "IOException";
      }
    }
  }

  private static Log log = LogFactory.getLog(ProcessUploadServlet.class);

}