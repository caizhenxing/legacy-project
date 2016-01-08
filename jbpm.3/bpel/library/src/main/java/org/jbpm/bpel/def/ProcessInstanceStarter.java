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
package org.jbpm.bpel.def;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

import org.jbpm.bpel.integration.def.Receiver;

/**
 * @author Juan Cantu
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class ProcessInstanceStarter implements BpelVisitor {

  private final ExecutionContext context;
  private final Receiver trigger;

  ProcessInstanceStarter(Token token, Receiver trigger) {
    this.context = new ExecutionContext(token);
    this.trigger = trigger;
  }

  /** {@inheritDoc} */
  public void visit(BpelDefinition process) {
    process.getGlobalScope().getRoot().accept(this);
  }

  /** {@inheritDoc} */
  public void visit(Empty empty) {
    validateNonInitial(empty);
  }

  /** {@inheritDoc} */
  public void visit(Receive receive) {
    if (!trigger.equals(receive.getReceiver())) {
      receive.enter(context);
    }
    else if (receive.isCreateInstance()) {
      receive.leave(context);
    }
    else {
      throw new RuntimeException("receive " + receive.getFullyQualifiedName()
          + " can't create instances");
    }
  }

  /** {@inheritDoc} */
  public void visit(Reply reply) {
    validateNonInitial(reply);
  }

  /** {@inheritDoc} */
  public void visit(Invoke invoke) {
    validateNonInitial(invoke);
  }

  /** {@inheritDoc} */
  public void visit(Assign assign) {
    validateNonInitial(assign);
  }

  /** {@inheritDoc} */
  public void visit(Throw throwActivity) {
    validateNonInitial(throwActivity);
  }

  /** {@inheritDoc} */
  public void visit(Exit exit) {
    validateNonInitial(exit);
  }

  /** {@inheritDoc} */
  public void visit(Wait wait) {
    validateNonInitial(wait);
  }

  /** {@inheritDoc} */
  public void visit(Sequence sequence) {
    ((Activity) sequence.getNodes().get(0)).accept(this);
  }

  /** {@inheritDoc} */
  public void visit(If ifActivity) {
    validateNonInitial(ifActivity);
  }

  /** {@inheritDoc} */
  public void visit(While whileActivity) {
    validateNonInitial(whileActivity);
  }

  /** {@inheritDoc} */
  public void visit(Pick pick) {
    Collection onMessages = pick.getOnMessages();

    if (!onMessages.contains(trigger)) {
      pick.getBegin().enter(context);
    }
    else if (pick.isCreateInstance()) {
      pick.pickPath(context, pick.getOnMessage(trigger));
    }
    else {
      throw new RuntimeException("pick " + pick.getFullyQualifiedName()
          + " can't create instances");
    }
  }

  /** {@inheritDoc} */
  public void visit(Flow flow) {
    Token token = context.getToken();
    if (flow.createVariableContext()) token = new Token(token, flow.getName());
    flow.initLinks(token);
    // phase one: collect all the flow tokens
    Map flowTokens = flow.createFlowTokens(token);
    // phase two: visit child activities with their corresponding token
    Iterator flowTokenIt = flowTokens.entrySet().iterator();
    // stop spawning new children if the parent token ends abruptly
    while (flowTokenIt.hasNext() && !token.hasEnded()) {
      Entry flowTokenEntry = (Entry) flowTokenIt.next();
      ProcessInstanceStarter recursiveStarter = new ProcessInstanceStarter(
          (Token) flowTokenEntry.getValue(), trigger);
      ((Activity) flowTokenEntry.getKey()).accept(recursiveStarter);
    }
  }

  public void visit(Scope scope) {
    validateNonInitial(scope);
  }

  /** {@inheritDoc} */
  public void visit(Compensate compensate) {
    throw new RuntimeException("activity " + compensate.getFullyQualifiedName()
        + " can't be initial");
  }

  /** {@inheritDoc} */
  public void visit(Rethrow rethrow) {
    throw new RuntimeException("activity " + rethrow.getFullyQualifiedName()
        + " can't be initial");
  }

  /** {@inheritDoc} */
  public void visit(Validate validate) {
    validateNonInitial(validate);
  }

  private static void validateNonInitial(Activity activity) {
    if (activity.getTargets().isEmpty()) {
      throw new RuntimeException("activity " + activity.getFullyQualifiedName()
          + " can't be initial");
    }
  }
}