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
import org.jbpm.ui.model.Node;

public class ConnectionCreationAndRemovalTest extends DesignerEditorTestCase {
	
	private static Point FIRST_LOCATION = new Point(180, 5);
	private static Point SECOND_LOCATION = new Point(180, 55);
	
	public void testTransitionCreationAndRemoval() throws Exception {
		verifyEmptyProcessDefinition();
		addNode("org.jbpm.ui.palette.State", FIRST_LOCATION);
		addNode("org.jbpm.ui.palette.State", SECOND_LOCATION);
		verifyNoTransitions(getFirstNode());
		verifyNoTransitions(getSecondNode());
		addTransition(FIRST_LOCATION, SECOND_LOCATION);
		verifyOneLeavingNoArrivingTransitions(getFirstNode());
		verifyNoLeavingOneArrivingTransition(getSecondNode());
		closeAndReopenEditor();
		verifyOneLeavingNoArrivingTransitions(getFirstNode());
		verifyNoLeavingOneArrivingTransition(getSecondNode());		
		Point midpoint = findMidpointBetweenFiguresAt(FIRST_LOCATION, SECOND_LOCATION);
		selectAt(midpoint);
		pressDeleteKey();
		verifyNoTransitions(getFirstNode());
		verifyNoTransitions(getSecondNode());
		closeAndReopenEditor();
		verifyNoTransitions(getFirstNode());
		verifyNoTransitions(getSecondNode());		
	}

	private Node getSecondNode() {
		return (Node)getModel().getNodes().get(1);
	}

	private Node getFirstNode() {
		return (Node)getModel().getNodes().get(0);
	}
	
	private void verifyNoLeavingOneArrivingTransition(Node node) {
		assertEquals("AbstractNode should have 0 leaving transitions", node.getLeavingTransitions().size(), 0);
		assertEquals("AbstractNode should have 1 arriving transition", node.getArrivingTransitions().size(), 1);
	}


	private void verifyOneLeavingNoArrivingTransitions(Node node) {
		assertEquals("AbstractNode should have 1 leaving transition", node.getLeavingTransitions().size(), 1);
		assertEquals("AbstractNode should have 0 arriving transitions", node.getArrivingTransitions().size(), 0);
	}

	private void verifyNoTransitions(Node node) {
		assertTrue("AbstractNode should not have any leaving transitions", node.getLeavingTransitions().isEmpty());
		assertTrue("AbstractNode should not have any arriving transitions", node.getArrivingTransitions().isEmpty());
	}
	
}
