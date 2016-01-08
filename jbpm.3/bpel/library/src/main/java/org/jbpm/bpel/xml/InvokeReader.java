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
package org.jbpm.bpel.xml;

import java.util.Iterator;

import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.PortType;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.CompositeActivity;
import org.jbpm.bpel.def.Invoke;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.integration.def.Correlation;
import org.jbpm.bpel.integration.def.CorrelationSetDefinition;
import org.jbpm.bpel.integration.def.Correlations;
import org.jbpm.bpel.integration.def.Invoker;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Correlation.Pattern;
import org.jbpm.bpel.variable.def.VariableDefinition;
import org.jbpm.bpel.wsdl.PartnerLinkType.Role;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Juan Cantú
 * @version $Revision: 1.5 $ $Date: 2006/08/21 01:05:59 $
 */
public class InvokeReader extends ActivityReader {

  /** {@inheritDoc} */
  public Activity read(Element activityElem, CompositeActivity parent) {
    Element faultHandlersElem = XmlUtil.getElement(activityElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_FAULT_HANDLERS);
    Element compensationHandlerElem = XmlUtil.getElement(activityElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_COMPENSATION_HANDLER);

    if (compensationHandlerElem == null && faultHandlersElem == null)
      return super.read(activityElem, parent);

    /*
     * WS-BPEL 11.3 Invoking Web Service Operations Semantically, the
     * specification of local fault and/or compensation handlers is equivalent
     * target the presence of an implicit scope immediately enclosing the
     * activity and providing those handlers. The name of such an implicit scope
     * is always the same as the name of the activity it encloses.
     */
    Scope scope = new Scope();
    scope.installFaultExceptionHandler();
    scope.setImplicit(true);
    // load standard properties
    readStandardProperties(activityElem, scope, parent);
    // read handlers
    if (compensationHandlerElem != null)
      bpelReader.readHandler(compensationHandlerElem, scope, Scope.COMPENSATION);
    if (faultHandlersElem != null)
      bpelReader.readFaultHandlers(faultHandlersElem, scope);

    // read activity
    Invoke invoke = new Invoke();
    scope.setRoot(invoke);
    readSpecificProperties(activityElem, invoke);

    return scope;
  }

  protected Activity createActivity() {
    return new Invoke();
  }

  public void readSpecificProperties(Element activityElem, Activity activity) {
    Invoke invoke = (Invoke) activity;
    invoke.setInvoker(readInvoker(activityElem, invoke.getCompositeActivity()));
  }

  public Invoker readInvoker(Element invokeElem, CompositeActivity parent) {
    Invoker invoker = new Invoker();
    // partner link
    String partnerLinkName = invokeElem.getAttribute(BpelConstants.ATTR_PARTNER_LINK);
    PartnerLinkDefinition partnerLink = parent.findPartnerLink(partnerLinkName);
    if (partnerLink == null) {
      bpelReader.getProblemHandler().add(new ParseProblem(
          "partner link not found", invokeElem));
      return invoker;
    }
    invoker.setPartnerLink(partnerLink);
    // port type
    Role partnerRole = partnerLink.getPartnerRole();
    // BPEL-181 detect absence of partner role
    if (partnerRole == null) {
      bpelReader.getProblemHandler().add(new ParseProblem(
          "partner link does not indicate partner role", invokeElem));
      return invoker;
    }
    PortType portType = bpelReader.getMessageActivityPortType(invokeElem,
        partnerRole);
    // operation
    Operation operation = bpelReader.getMessageActivityOperation(invokeElem,
        portType);
    invoker.setOperation(operation);
    // input variable
    VariableDefinition input = bpelReader.getMessageActivityVariable(invokeElem,
        BpelConstants.ATTR_INPUT_VARIABLE,
        parent,
        operation.getInput().getMessage());
    invoker.setInputVariable(input);
    // output variable
    VariableDefinition output = null;
    if (operation.getStyle().equals(OperationType.REQUEST_RESPONSE)) {
      output = bpelReader.getMessageActivityVariable(invokeElem,
          BpelConstants.ATTR_OUTPUT_VARIABLE,
          parent,
          operation.getOutput().getMessage());
      invoker.setOutputVariable(output);
    }
    // correlations
    Element correlationsElem = XmlUtil.getElement(invokeElem,
        BpelConstants.NS_BPWS,
        BpelConstants.ELEM_CORRELATIONS);
    if (correlationsElem != null) {
      Correlations inCorrelations = new Correlations();
      Correlations outCorrelations = new Correlations();
      Iterator correlationElemIt = XmlUtil.getElements(correlationsElem,
          BpelConstants.NS_BPWS,
          BpelConstants.ELEM_CORRELATION);
      while (correlationElemIt.hasNext()) {
        Element correlationElem = (Element) correlationElemIt.next();
        Correlation correlation = bpelReader.readCorrelation(correlationElem,
            parent);
        CorrelationSetDefinition set = correlation.getSet();
        // applicability pattern
        Pattern pattern = Pattern.valueOf(correlationElem.getAttribute(BpelConstants.ATTR_PATTERN));
        // is pattern 'out' or 'out-in'?
        if (pattern != Pattern.IN) {
          bpelReader.checkVariableProperties(input, set, correlationElem);
          outCorrelations.addCorrelation(correlation);
        }
        // is pattern 'in' or 'out-in'?
        if (pattern != Pattern.OUT) {
          if (output != null) {
            bpelReader.checkVariableProperties(output, set, correlationElem);
          }
          else {
            bpelReader.getProblemHandler()
                .add(new ParseProblem(
                    "correlation cannot apply to inbound message for one-way operation",
                    correlationElem));
          }
          inCorrelations.addCorrelation(correlation);
        }
      }
      if (inCorrelations.getCorrelations() != null)
        invoker.setInCorrelations(inCorrelations);
      if (outCorrelations.getCorrelations() != null)
        invoker.setOutCorrelations(outCorrelations);
    }
    return invoker;
  }
}
