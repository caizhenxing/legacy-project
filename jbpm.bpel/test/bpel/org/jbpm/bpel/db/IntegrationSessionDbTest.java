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
package org.jbpm.bpel.db;

import java.util.Collection;
import java.util.List;

import org.xml.sax.InputSource;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.OnEvent;
import org.jbpm.bpel.def.Pick;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.def.Sequence;
import org.jbpm.bpel.def.StructuredActivity.Begin;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/23 09:58:23 $
 */
public class IntegrationSessionDbTest extends AbstractDbTestCase {

  long processDefinitionId;
  BpelDefinition processDefinition;

  IntegrationSession integrationSession;

  public void setUp() throws Exception {
    super.setUp();

    processDefinition = new BpelDefinition();

    String processResource = getClass().getResource("integrationSession.bpel")
        .toString();
    BpelReader.getInstance().read(processDefinition,
        new InputSource(processResource));

    jbpmContext.deployProcessDefinition(processDefinition);
    processDefinitionId = processDefinition.getId();

    ProcessInstance processInstance = processDefinition.createProcessInstance();
    Token rootToken = processInstance.getRootToken();
    // root token does not point to any node

    Scope globalScope = processDefinition.getGlobalScope();

    // event handler
    Token eventsToken = new Token(rootToken, "events");
    // scope positions the events token in itself
    eventsToken.setNode(globalScope);

    Sequence main = (Sequence) globalScope.getNode("main");
    Token activityToken = new Token(rootToken, "activity");
    activityToken.setNode(main);

    // receive
    Receive receive = (Receive) main.getNode("r");
    Token receiveToken = new Token(activityToken, "r");
    receiveToken.setNode(receive);

    // pick
    Pick pick = (Pick) main.getNode("p");
    Token pickToken = new Token(rootToken, "p");
    // pick positions the token in its begin mark
    pickToken.setNode(pick.getBegin());

    /*
     * position a token in an activity set to execute upon picking a message,
     * because the parent of said activity is the pick. we are not looking for
     * such a token; we want tokens positioned in begin marks
     */
    Activity onMessage = (Activity) pick.getNodes().get(0);
    Token onMessageToken = new Token(pickToken, "on");
    onMessageToken.setNode(onMessage);

    jbpmContext.save(processInstance);

    newTransaction();
    integrationSession = IntegrationSession.getInstance(jbpmContext);
  }

  public void testFindReceiveTokens() {
    Collection tokens = integrationSession.findReceiveTokens(processDefinition);
    assertEquals(1, tokens.size());

    Token token = (Token) tokens.iterator().next();
    assertEquals("r", token.getName());

    Receive receive = (Receive) token.getNode();
    assertEquals("r", receive.getName());

    Receiver receiver = receive.getReceiver();
    assertEquals("schedulingPL", receiver.getPartnerLink().getName());
    assertEquals("returnScheduleTicket", receiver.getOperation().getName());
  }

  public void testFindPickTokens() {
    Collection tokens = integrationSession.findPickTokens(processDefinition);
    assertEquals(1, tokens.size());

    Token token = (Token) tokens.iterator().next();
    assertEquals("p", token.getName());

    Begin begin = (Begin) token.getNode();
    Pick pick = (Pick) begin.getCompositeActivity();
    assertEquals("p", pick.getName());

    List onMessages = pick.getOnMessages();
    assertEquals(2, onMessages.size());

    Receiver receiver1 = (Receiver) onMessages.get(0);
    assertEquals("schedulingPL", receiver1.getPartnerLink().getName());
    assertEquals("requestScheduling", receiver1.getOperation().getName());

    Receiver receiver2 = (Receiver) onMessages.get(1);
    assertEquals("schedulingPL", receiver2.getPartnerLink().getName());
    assertEquals("sendShippingSchedule", receiver2.getOperation().getName());
  }

  public void testFindEventTokens() {
    Collection tokens = integrationSession.findEventTokens(processDefinition);
    assertEquals(1, tokens.size());

    Token token = (Token) tokens.iterator().next();
    assertEquals("events", token.getName());

    Scope scope = (Scope) token.getNode();

    Collection onEvents = scope.getOnEvents();
    assertEquals(1, onEvents.size());

    OnEvent onEvent = (OnEvent) onEvents.iterator().next();
    Receiver receiver = onEvent.getReceiver();
    assertEquals("schedulingPL", receiver.getPartnerLink().getName());
    assertEquals("cancelScheduling", receiver.getOperation().getName());
  }
}