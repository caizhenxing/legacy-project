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
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class WebDBSchemaTask extends Task {

  private String operation = "create";
  private String host = "localhost";
  private int port = 8080;
  private String context = "/jbpm-bpel/dbschema";

  public void execute() throws BuildException {
    String file = context + "?operation=" + operation;
    URL dbSchemaUrl;
    try {
      dbSchemaUrl = new URL("http", host, port, file);
      log("db schema url: " + dbSchemaUrl);
    }
    catch (MalformedURLException e) {
      log(e.toString());
      throw new BuildException("malformed db schema url", e);
    }
    HttpURLConnection httpConnection = null;
    try {
      // connect to the remote machine
      httpConnection = (HttpURLConnection) dbSchemaUrl.openConnection();
      httpConnection.connect();
      // check the response
      int responseCode = httpConnection.getResponseCode();
      log("got response code: " + responseCode);
      // is the response code out of class 200?
      if (responseCode / 100 != 2) {
        throw new BuildException(
            "db schema operation failed, see details in the server console");
      }
    }
    catch (IOException e) {
      log(e.toString());
      throw new BuildException("could not connect to server", e);
    }
    finally {
      if (httpConnection != null) httpConnection.disconnect();
    }
  }

  public void setOperation(String operation) {
    this.operation = operation;
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
