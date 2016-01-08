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
package org.jbpm.bpel.ant;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/05 07:26:07 $
 */
public class DeployProcessTask extends Task {

  private String processfile;
  private String host = "localhost";
  private int port = 8080;
  private String context = "/jbpm-bpel/deploy";

  public void execute() throws BuildException {
    File processFile = new File(processfile);
    if (!processFile.exists())
      throw new BuildException("process file not found: " + processfile);
    
    try {
      store(processFile);
    }
    catch (IOException e) {
      log(e.getMessage());
      throw new BuildException("could not contact host", e);
    }
  }

  public void setProcessfile(String processfile) {
    this.processfile = processfile;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setContext(String context) {
    this.context = context;
  }

  protected void store(File processFile) throws IOException {
    // format file component
    String file = context + "?processfile="
        + URLEncoder.encode(processFile.toURI().toString(), "UTF-8");

    // create target URL
    URL targetUrl = new URL("http", host, port, file);
    log("target url: " + targetUrl);
    
    // create http connection
    HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
    try {
      // contact host
      httpConnection.connect();
      // check the response
      int responseCode = httpConnection.getResponseCode();
      log("got response code: " + responseCode);
      if (responseCode != HttpURLConnection.HTTP_OK) {
        throw new BuildException(
            "deployment failed, see details in the server console");
      }
    }
    finally {
      httpConnection.disconnect();
    }
  }
}