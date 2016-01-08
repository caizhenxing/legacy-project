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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/05 07:26:07 $
 */
public class DBSchemaTask extends Task {

  private String action = "create";
  private String host = "localhost";
  private int port = 8080;
  private String context = "/jbpm-bpel/dbschema";

  public void execute() throws BuildException {
    try {
      doAction(action);
    }
    catch (IOException e) {
      log(e.getMessage());
      throw new BuildException("could not contact host", e);
    }
  }

  protected void doAction(String action) throws IOException {
    // format the file component
    String file = context + "?action=" + action;

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
      // is the response code out of class 200?
      if (responseCode / 100 != 2) {
        throw new BuildException("db schema operation failed, "
            + "see details in the server console");
      }
    }
    finally {
      httpConnection.disconnect();
    }
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
