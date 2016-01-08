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

import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.integration.def.Receiver;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.4 $ $Date: 2006/09/12 06:48:12 $
 */
public class OnEventDbTest extends AbstractHandlerDbTestCase {

  /** {@inheritDoc} */
  protected ScopeHandler create(BpelDefinition process) {
    OnEvent onEvent = new OnEvent();
    process.getGlobalScope().addOnEvent(onEvent);
    return onEvent;
  }

  /** {@inheritDoc} */
  protected ScopeHandler get(BpelDefinition process) {
    return (OnEvent) process.getGlobalScope().getOnEvents().iterator().next();
  }

  public void testReceiver() {
    // prepare persistent objects
    BpelDefinition process = scopeHandler.getBpelDefinition();
    // partner link
    String partnerLinkName = "partnerLink";
    PartnerLinkDefinition partnerLink = new PartnerLinkDefinition();
    partnerLink.setName(partnerLinkName);
    process.getGlobalScope().addPartnerLink(partnerLink);
    // receiver
    Receiver receiver = new Receiver();
    receiver.setPartnerLink(partnerLink);
    // handler
    OnEvent onEvent = (OnEvent) scopeHandler;
    onEvent.setReceiver(receiver);
    
    // save objects and load them back
    process = saveAndReload(process);
    onEvent = (OnEvent) get(process);
    
    // verify retrieved objects
    assertEquals(partnerLinkName, onEvent.getReceiver().getPartnerLink().getName());
  }
}
