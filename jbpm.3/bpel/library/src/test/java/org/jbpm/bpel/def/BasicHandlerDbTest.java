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

import org.jbpm.bpel.def.Scope.HandlerType;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class BasicHandlerDbTest extends AbstractHandlerDbTest {
  
  private final HandlerType handlerType;

  protected BasicHandlerDbTest(HandlerType handlerType) {
    this.handlerType = handlerType;
  }

  protected ScopeHandler create(BpelDefinition process) {
    ScopeHandler scopeHandler = new ScopeHandler();
    process.getGlobalScope().setHandler(handlerType, scopeHandler);
    return scopeHandler;
  }
  
  protected ScopeHandler get(BpelDefinition process) {
    return process.getGlobalScope().getHandler(handlerType);
  }
  
}
