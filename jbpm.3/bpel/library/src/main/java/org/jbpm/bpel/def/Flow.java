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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * The flow activity provides concurrency and synchronization.
 * @see "WS-BPEL 2.0 &sect;12.5"
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class Flow extends StructuredActivity {

  private Map links = new HashMap();

  private static final long serialVersionUID = 1L;

  public Flow() {
  }

  public Flow(String name) {
    super(name);
  }

  // behaviour methods
  // /////////////////////////////////////////////////////////////////////////////

  public void execute(ExecutionContext context) {
    Token token = context.getToken();
    if (createVariableContext()) token = new Token(token, getName());
    initLinks(token);

    // phase one: collect all the flow tokens
    Map flowTokens = createFlowTokens(token);
    // phase two: launch child tokens from the fork over the given transitions
    Iterator iter = flowTokens.keySet().iterator();
    // stop spawning the new childs if the parent token is completed abruptly
    while (iter.hasNext() && !token.hasEnded()) {
      Activity child = (Activity) iter.next();
      Token forkedToken = (Token) flowTokens.get(child);
      ExecutionContext childExecutionContext = new ExecutionContext(forkedToken);
      begin.leave(childExecutionContext, child.getDefaultArrivingTransition());
    }
  }

  public void terminate(ExecutionContext context) {
    // terminate the active children
    for (Iterator it = context.getToken().getChildren().values().iterator(); it.hasNext();) {
      Token child = (Token) it.next();
      if (child.isAbleToReactivateParent()) {
        // cancel atomic activities
        Activity activity = ((Activity) child.getNode());
        activity.terminate(new ExecutionContext(child));
      }
    }
  }

  public void leave(ExecutionContext context) {
    Token token = context.getToken();
    // if this token is not able to reactivate the parent,
    // we don't need to check anything
    if (!token.isAbleToReactivateParent()) return;
    // the token arrived in the join and can only reactivate
    // the parent once
    token.setAbleToReactivateParent(false);
    // if the parent token needs to be reactivated from this join node
    Token parentToken = token.getParent();
    if (mustParentBeReactivated(parentToken)) {
      // if a token was created for scoping link status remove it
      if (createVariableContext()) {
        parentToken.setAbleToReactivateParent(false);
        parentToken = parentToken.getParent();
      }
      getEnd().leave(new ExecutionContext(parentToken));
    }
  }

  boolean createVariableContext() {
    return !isInitial() && !links.isEmpty();
  }

  Map createFlowTokens(Token parent) {
    Collection children = getNodes();
    Map flowTokens = new HashMap(children.size());
    Iterator iter = children.iterator();
    while (iter.hasNext()) {
      Activity child = (Activity) iter.next();
      Token forkedToken = new Token(parent, getTokenName(parent,
          child.getName()));
      flowTokens.put(child, forkedToken);
    }
    return flowTokens;
  }

  public static String getTokenName(Token parent, String transitionName) {
    String tokenName = null;
    if (transitionName != null) {
      if (!parent.hasChild(transitionName)) {
        tokenName = transitionName;
      }
      else {
        int i = 2;
        tokenName = transitionName + Integer.toString(i);
        while (parent.hasChild(tokenName)) {
          i++;
          tokenName = transitionName + Integer.toString(i);
        }
      }
    }
    else { // no transition name
      int size = (parent.getChildren() != null ? parent.getChildren().size() + 1
          : 1);
      tokenName = Integer.toString(size);
    }
    return tokenName;
  }

  public static boolean mustParentBeReactivated(Token parentToken) {
    boolean reactivateParent = true;
    Iterator childTokenNameIterator = parentToken.getChildren()
        .keySet()
        .iterator();
    while ((childTokenNameIterator.hasNext()) && (reactivateParent)) {
      String concurrentTokenName = (String) childTokenNameIterator.next();

      Token concurrentToken = parentToken.getChild(concurrentTokenName);

      if (concurrentToken.isAbleToReactivateParent()) {
        reactivateParent = false;
      }
    }
    return reactivateParent;
  }

  public boolean isChildInitial(Activity activity) {
    return true;
  }

  // LinkDefinition methods
  // /////////////////////////////////////////////////////////////////////////////

  public LinkDefinition findLink(String linkName) {
    LinkDefinition link = getLink(linkName);
    return link != null ? link : super.findLink(linkName);
  }

  void initLinks(Token token) {
    if (links.size() > 0) {
      for (Iterator it = links.values().iterator(); it.hasNext();)
        ((LinkDefinition) it.next()).createInstance(token);
    }
  }

  public void addLink(LinkDefinition link) {
    links.put(link.getName(), link);
  }

  public Collection getLinks() {
    return links.values();
  }

  public LinkDefinition getLink(String linkName) {
    return (LinkDefinition) links.get(linkName);
  }

  /** {@inheritDoc} */
  public void accept(BpelVisitor visitor) {
    visitor.visit(this);
  }
}
