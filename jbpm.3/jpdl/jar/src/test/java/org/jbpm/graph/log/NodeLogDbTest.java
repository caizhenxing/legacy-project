/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.graph.log;

import java.util.Date;

import org.jbpm.db.AbstractDbTestCase;
import org.jbpm.graph.def.Node;

public class NodeLogDbTest extends AbstractDbTestCase {

  public void testNodeLogNode() {
    Node node = new Node();
    session.save(node);
    NodeLog nodeLog = new NodeLog(node, new Date(), new Date());
    
    nodeLog = (NodeLog) saveAndReload(nodeLog);
    assertNotNull(nodeLog.getNode());
  }

  public void testNodeLogEnterDate() {
    Node node = new Node();
    session.save(node);

    Date enter = new Date();
    Date leave = new Date(enter.getTime()+5);
    NodeLog nodeLog = new NodeLog(node, enter, leave);
    
    nodeLog = (NodeLog) saveAndReload(nodeLog);
    assertEquals(enter, nodeLog.getEnter());
  }

  public void testNodeLogLeaveDate() {
    Node node = new Node();
    session.save(node);

    Date enter = new Date();
    Date leave = new Date(enter.getTime()+5);
    NodeLog nodeLog = new NodeLog(node, enter, leave);
    
    nodeLog = (NodeLog) saveAndReload(nodeLog);
    assertEquals(leave, nodeLog.getLeave());
  }

  public void testNodeLogDuration() {
    Node node = new Node();
    session.save(node);

    Date enter = new Date();
    Date leave = new Date(enter.getTime()+5);
    NodeLog nodeLog = new NodeLog(node, enter, leave);
    
    nodeLog = (NodeLog) saveAndReload(nodeLog);
    assertEquals(5, nodeLog.getDuration());
  }


}
