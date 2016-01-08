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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class WebDeployTask extends Task {

  private String par;
  private String host = "localhost";
  private int port = 8080;
  private String context = "/jbpm-bpel/deploy";

  public void execute() throws BuildException {
    File parFile = new File(par);
    if (!parFile.exists())
      throw new BuildException("par file not found: " + par);
    deploy(parFile);
  }

  public void setPar(String par) {
    this.par = par;
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

  protected void deploy(File parFile) throws BuildException {
    URL deployUrl;
    try {
      String file = context + "?archive="
          + URLEncoder.encode(parFile.toURL().toString(), "UTF-8");
      deployUrl = new URL("http", host, port, file);
      log("deployment url: " + deployUrl);
    }
    catch (MalformedURLException e) {
      log(e.toString());
      throw new BuildException("malformed deployment url", e);
    }
    catch (UnsupportedEncodingException e) {
      log(e.toString());
      throw new BuildException("utf-8 encoding unsupported", e);
    }
    HttpURLConnection httpConnection = null;
    try {
      // connect to the remote machine
      httpConnection = (HttpURLConnection) deployUrl.openConnection();
      httpConnection.connect();
      // check the response
      int responseCode = httpConnection.getResponseCode();
      log("got response code: " + responseCode);
      if (responseCode != HttpURLConnection.HTTP_OK) {
        throw new BuildException(
            "deployment failed, see details in the server console");
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
}