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

public class BendpointCreationAndRemovalTest extends DesignerEditorTestCase {
	
	private static Point FIRST_LOCATION = new Point(180, 5);
	private static Point SECOND_LOCATION = new Point(180, 55);
	
	private Point midpoint;
	
	public void testBendpointCreationAndRemoval() {
		verifyEmptyProcessDefinition();
		addNode("org.jbpm.ui.palette.State", FIRST_LOCATION);
		addNode("org.jbpm.ui.palette.State", SECOND_LOCATION);
		addTransition(FIRST_LOCATION, SECOND_LOCATION);
		verifyNoBendpoints();
	}
	
	private void verifyNoBendpoints() {
		midpoint = findMidpointBetweenFiguresAt(FIRST_LOCATION, SECOND_LOCATION);
		selectAt(midpoint);
		Object editPart = getModelViewer().getSelectedEditParts().get(0);
		System.out.println("class: " + editPart.getClass().getName());
		//TransitionFigure figure = (TransitionFigure)editPart.getFigure();
		//List bendPoints = (List)figure.getConnectionRouter().getConstraint(figure);
		//assertTrue("There should be no bendpoints", bendPoints.isEmpty());
	}
	
	
}
