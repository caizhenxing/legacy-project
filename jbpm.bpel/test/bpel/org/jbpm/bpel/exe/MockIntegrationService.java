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
package org.jbpm.bpel.exe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;

import org.jbpm.bpel.integration.IntegrationService;
import org.jbpm.bpel.integration.def.Invoker;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;
import org.jbpm.bpel.integration.def.Replier;
import org.jbpm.bpel.integration.exe.EndpointReference;
import org.jbpm.graph.exe.Token;
import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class MockIntegrationService implements IntegrationService {
  
  private Map myReferences = new HashMap();
  
  private static final long serialVersionUID = 1L;

  public static String getIdString(Object objectWithId) {
    Class classWithId = objectWithId.getClass();
    // get the class name excluding the package
    String className = ClassUtils.getShortClassName(classWithId);
    // obtain the identifier getter method according to jBPM conventions
    String idString = "0";
    try {
      Method idGetter = classWithId.getMethod("getId", null);
      idGetter.setAccessible(true);
      Number id = (Number) idGetter.invoke(objectWithId, null);
      idString = Long.toHexString(id.longValue());
    }
    catch (NoSuchMethodException e) {
      // no id getter, fall back to the hash code
      idString = Integer.toHexString(objectWithId.hashCode());
    }
    catch (IllegalAccessException e) {
      // we made the getter accessible, should not happen
      e.printStackTrace();
    }
    catch (InvocationTargetException e) {
      // strange a getter throws a checked exception
      e.printStackTrace();
    }
    return className + idString;
  }

  public static void createMark(Object marker, Token token) {
    token.getProcessInstance().getContextInstance().createVariable(getIdString(marker), "xxx", token); 
  }
  
  public static void deleteMark(Object marker, Token token) {
    token.getProcessInstance().getContextInstance().deleteVariable(getIdString(marker), token);     
  }
  
  /** {@inheritDoc} */
  public void receive(Receiver receiver, Token token) {
    createMark(receiver, token);
  }

  /** {@inheritDoc} */
  public void receive(List receivers, Token token) {
    Iterator receiverIt = receivers.iterator();
    while (receiverIt.hasNext()) {
      Receiver receiver = (Receiver) receiverIt.next();
      createMark(receiver, token);
    }
  }

  /** {@inheritDoc} */
  public void endReception(Receiver receiver, Token token) {
    deleteMark(receiver, token);
  }

  /** {@inheritDoc} */
  public void endReception(List receivers, Token token) {
    Iterator receiverIt = receivers.iterator();
    while (receiverIt.hasNext()) {
      Receiver receiver = (Receiver) receiverIt.next();
      deleteMark(receiver, token);
    }
  }

  /** {@inheritDoc} */
  public void reply(Replier replier, Token token) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  public void invoke(Invoker invoker, Token token) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  public EndpointReference getMyReference(PartnerLinkDefinition partnerLink) {
    return (EndpointReference) myReferences.get(getIdString(partnerLink));
  }
  
  public void setMyReference(PartnerLinkDefinition partnerLink, EndpointReference myReference) {
    myReferences.put(getIdString(partnerLink), myReference);
  }

  public void enableStartActivities() {
    throw new UnsupportedOperationException();
  }

  public void disableStartActivities() {
    throw new UnsupportedOperationException();
  }

  public void close() {
    // noop
  }

  public static class Factory implements ServiceFactory {

    private static final long serialVersionUID = 1L;

    public Service openService() {
      return new MockIntegrationService();
    }

    public void close() {
      // noop      
    }
  }
}
