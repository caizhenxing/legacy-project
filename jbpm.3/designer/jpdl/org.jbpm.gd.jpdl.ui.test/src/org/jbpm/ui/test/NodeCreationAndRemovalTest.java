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
package org.jbpm.ui.test;

import org.eclipse.draw2d.geometry.Point;
import org.jbpm.ui.model.Decision;
import org.jbpm.ui.model.EndState;
import org.jbpm.ui.model.Fork;
import org.jbpm.ui.model.Join;
import org.jbpm.ui.model.StartState;
import org.jbpm.ui.model.State;

public class NodeCreationAndRemovalTest extends DesignerEditorTestCase {
	
	private static Point LOCATION = new Point(180, 5);
	
	public void testStartState() throws Exception {
		verifyEmptyProcessDefinition();
		addNode("org.jbpm.ui.palette.Start", LOCATION);
		verifyStartStatePresent();	
		closeAndReopenEditor();
		verifyStartStatePresent();
		verifyNodeRemoval();
	}

	private void verifyStartStatePresent() {
		Object node = getModel().getNodes().get(0);
		assertNotNull("The start state must exist in the model", node);
		assertTrue("Start state is of the wrong class", node instanceof StartState);
	}

	public void testState() throws Exception {
		verifyEmptyProcessDefinition();
		addNode("org.jbpm.ui.palette.State", LOCATION);
		verifyStatePresent();
		closeAndReopenEditor();
		verifyStatePresent();
		verifyNodeRemoval();
	}
	
	private void verifyStatePresent() {
		Object nodeDecorator = getModel().getNodes().get(0);
		assertNotNull("The state node must exist in the model", nodeDecorator);
		assertTrue("State node is of the wrong class", nodeDecorator instanceof State);
	}
	
	public void testDecision() throws Exception {
		verifyEmptyProcessDefinition();
		addNode("org.jbpm.ui.palette.Decision", LOCATION);
		verifyDecisionPresent();
		closeAndReopenEditor();
		verifyDecisionPresent();
		verifyNodeRemoval();
	}
	
	private void verifyDecisionPresent() {
		Object nodeDecorator = getModel().getNodes().get(0);
		assertNotNull("The decision node must exist in the model", nodeDecorator); 
		assertTrue("Decision node is of the wrong class", nodeDecorator instanceof Decision);
	}
	
	public void testEndState() throws Exception {
		verifyEmptyProcessDefinition();
		addNode("org.jbpm.ui.palette.End", LOCATION);
		verifyEndStatePresent();
		closeAndReopenEditor();
		verifyEndStatePresent();
		verifyNodeRemoval();
	}
	
	private void verifyEndStatePresent() {
		Object nodeDecorator = getModel().getNodes().get(0);
		assertNotNull("The end state must exist in the model", nodeDecorator);
		assertTrue("End state is of the wrong class", nodeDecorator instanceof EndState);
	}
	
	public void testFork() throws Exception {
		verifyEmptyProcessDefinition();
		addNode("org.jbpm.ui.palette.Fork", LOCATION);
		verifyForkPresent();
		closeAndReopenEditor();
		verifyForkPresent();
		verifyNodeRemoval();
	}
	
	private void verifyForkPresent() {
		Object nodeDecorator = getModel().getNodes().get(0);
		assertNotNull("The fork node must exist in the model", nodeDecorator);
		assertTrue("Fork node is of the wrong class", nodeDecorator instanceof Fork);
	}
	
	public void testJoin() throws Exception {
		verifyEmptyProcessDefinition();
		addNode("org.jbpm.ui.palette.Join", LOCATION);
		verifyJoinPresent();
		closeAndReopenEditor();
		verifyJoinPresent();
		verifyNodeRemoval();
	}
	
	private void verifyJoinPresent() {
		Object nodeDecorator = getModel().getNodes().get(0);
		assertNotNull("The join node must exist in the model", nodeDecorator);
		assertTrue("Join node is of the wrong class", nodeDecorator instanceof Join);
	}
	
	void verifyNodeRemoval() throws Exception {
		removeNode(LOCATION);
		verifyEmptyProcessDefinition();
		closeAndReopenEditor();
		verifyEmptyProcessDefinition();		
	}
	
}
