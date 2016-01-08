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
package org.jbpm.bpel.integration.jms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.bpel.db.IntegrationSession;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.exe.PartnerLinkInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/09/05 07:26:07 $
 */
public class IntegrationServlet extends HttpServlet {

  private IntegrationControl integrationControl;

  private static final long serialVersionUID = 1L;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
    integrationControl = IntegrationControl.getInstance(jbpmConfiguration);

    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      // start receiving requests
      integrationControl.enableInboundMessageActivities(jbpmContext);
      log("enabled message reception for process: "
          + integrationControl.getAppDescriptor().getName());
    }
    catch (Exception e) {
      jbpmContext.setRollbackOnly();
      throw new ServletException("could not start bpel application", e);
    }
    finally {
      jbpmContext.close();
    }
  }

  public void destroy() {
    if (integrationControl == null) return;
    JbpmContext jbpmContext = JbpmConfiguration.getInstance()
        .createJbpmContext();
    try {
      // stop receiving requests
      integrationControl.disableInboundMessageActivities();
      log("disabled message reception for process: "
          + integrationControl.getAppDescriptor().getName());
    }
    catch (Exception e) {
      jbpmContext.setRollbackOnly();
      log("could not stop bpel application", e);
    }
    finally {
      jbpmContext.close();
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<body>");
    out.println("<h1>JBoss jBPM BPEL</h1>");
    out.println("<h2>Partner Relationships Console</h2>");

    printGeneralDetails(out);

    JbpmContext jbpmContext = JbpmConfiguration.getInstance()
        .createJbpmContext();
    try {
      printRequestListeners(out, jbpmContext);
      printPortConsumers(out, jbpmContext);
      printStartListeners(out, jbpmContext);
      printOutstandingRequests(out, jbpmContext);
    }
    finally {
      jbpmContext.close();
    }

    out.println("</body>");
    out.println("</html>");
  }

  protected void printGeneralDetails(PrintWriter out) {
    out.println("<h2>General details</h2>");
    out.println("<table border='1'>");
    out.println("<tr><td>Process name</td><td>"
        + integrationControl.getAppDescriptor().getName() + "</td></tr>");
    out.println("</table>");
  }

  protected void printRequestListeners(PrintWriter out, JbpmContext jbpmContext) {
    out.println("<h2>Request listeners</h2>");
    out.println("<table border='1'>");
    out.println("<tr><th>Activity</th><th>Partner link</th><th>Operation</th>"
        + "<th>Token</th><th>Request listener</th></tr>");

    IntegrationSession integrationSession = IntegrationSession.getInstance(jbpmContext);
    Map requestListeners = integrationControl.getRequestListeners();

    synchronized (requestListeners) {
      Iterator entryIt = requestListeners.entrySet().iterator();

      while (entryIt.hasNext()) {
        Map.Entry entry = (Map.Entry) entryIt.next();
        RequestListener.Key key = (RequestListener.Key) entry.getKey();

        Receiver receiver = integrationSession.loadReceiver(key.getReceiverId());
        Token token = jbpmContext.loadToken(key.getTokenId());

        out.println("<tr>" + "<td>" + token.getNode() + "</td>" + "<td>"
            + receiver.getPartnerLink() + "</td>" + "<td>"
            + receiver.getOperation().getName() + "</td>" + "<td>" + token
            + "</td>" + "<td>" + entry.getValue() + "</td>" + "</tr>");
      }
    }
    out.println("</table>");
  }

  protected void printOutstandingRequests(PrintWriter out,
      JbpmContext jbpmContext) {
    out.println("<h2>Outstanding requests</h2>");
    out.println("<table border='1'>");
    out.println("<tr><th>Partner link</th><th>Operation</th><th>Message exchange</th>"
        + "<th>Outstanding request</th></tr>");

    IntegrationSession integrationSession = IntegrationSession.getInstance(jbpmContext);
    Map outstandingRequests = integrationControl.getOutstandingRequests();

    synchronized (outstandingRequests) {
      Iterator entryIt = outstandingRequests.entrySet().iterator();

      while (entryIt.hasNext()) {
        Map.Entry entry = (Map.Entry) entryIt.next();
        OutstandingRequest.Key key = (OutstandingRequest.Key) entry.getKey();

        PartnerLinkInstance partnerLinkInstance = integrationSession.loadPartnerLinkInstance(key.getPartnerLinkId());

        out.println("<tr>" + "<td>" + partnerLinkInstance + "</td>" + "<td>"
            + key.getOperationName() + "</td>" + "<td>"
            + key.getMessageExchange() + "</td>" + "<td>" + entry.getValue()
            + "</td>" + "</tr>");
      }
    }
    out.println("</table>");
  }

  protected void printPortConsumers(PrintWriter out, JbpmContext jbpmContext) {
    out.println("<h2>Port consumers</h2>");
    out.println("<table border='1'>");
    out.println("<tr><th>Partner link</th><th>Port consumer</th></tr>");

    IntegrationSession integrationSession = IntegrationSession.getInstance(jbpmContext);
    Map portConsumers = integrationControl.getPartnerClients();

    synchronized (portConsumers) {
      Iterator entryIt = portConsumers.entrySet().iterator();

      while (entryIt.hasNext()) {
        Map.Entry entry = (Map.Entry) entryIt.next();

        PartnerLinkInstance partnerLinkInstance = integrationSession.loadPartnerLinkInstance(((Long) entry.getKey()).longValue());

        out.println("<tr>" + "<td>" + partnerLinkInstance + "</td>" + "<td>"
            + entry.getValue() + "</td>" + "</tr>");
      }
    }

    out.println("</table>");
  }

  protected void printStartListeners(PrintWriter out, JbpmContext jbpmContext) {
    out.println("<h2>Start listeners</h2>");
    out.println("<table border='1'>");
    out.println("<tr><th>Partner link</th><th>Operation</th>"
        + "<th>Start listener</th></tr>");

    IntegrationSession integrationSession = IntegrationSession.getInstance(jbpmContext);
    List startListeners = integrationControl.getStartListeners();

    Iterator elementIt = startListeners.iterator();

    while (elementIt.hasNext()) {
      StartListener startListener = (StartListener) elementIt.next();

      Receiver receiver = integrationSession.loadReceiver(startListener.getReceiverId());

      out.println("<tr>" + "<td>" + receiver.getPartnerLink() + "</td>"
          + "<td>" + receiver.getOperation().getName() + "</td>" + "<td>"
          + startListener + "</td>" + "</tr>");
    }

    out.println("</table>");

  }
}