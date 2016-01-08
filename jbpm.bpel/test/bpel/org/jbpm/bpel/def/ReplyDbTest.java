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

import org.jbpm.bpel.db.AbstractDbTestCase;
import org.jbpm.bpel.integration.def.Replier;

public class ReplyDbTest extends AbstractDbTestCase {

  public void testReplier() {
    BpelDefinition processDefinition = new BpelDefinition();
    Reply reply = new Reply("child");
    Replier replier = new Replier();
    reply.setReplier(replier);
    
    processDefinition.addNode(reply);
    
    processDefinition = saveAndReload(processDefinition);

    reply = (Reply) processDefinition.getNode("child");
    assertNotNull( reply.getReplier() );
  }
}
