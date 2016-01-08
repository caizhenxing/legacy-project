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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.jbpm.ui.editor.DesignerEditor;
import org.jbpm.ui.editor.DesignerGraphicalEditorPage;
import org.jbpm.ui.editor.DesignerModelViewer;
import org.jbpm.ui.editor.DesignerPaletteRoot;
import org.jbpm.ui.editor.DesignerPaletteViewer;
import org.jbpm.ui.model.ProcessDefinition;
import org.jbpm.ui.test.helper.TestProject;


public class DesignerEditorTestCase extends TestCase {
	
	MouseEvent mouseEvent;
	KeyEvent keyEvent;
	
	private TestProject project;
	private DesignerEditor editor;
	
	private void initMouseEvent() {
		Event event = new Event();
		event.widget = getModelViewer().getControl();
		mouseEvent = new MouseEvent(event);
	}
	
	private void initKeyEvent() {
		Event event = new Event();
		event.widget = getModelViewer().getControl();
		keyEvent = new KeyEvent(event);
	}
	
	protected IWorkbenchPage getPage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}

	protected void setUp() throws Exception {
		super.setUp();
		project = new TestProject();
		setEditor((DesignerEditor)IDE.openEditor(getPage(), getProject().openFile("gpd.xml")));
		initMouseEvent();
		initKeyEvent();
	}
	
	protected void tearDown() throws Exception {
		getPage().closeAllEditors(false);
		getProject().dispose();
		super.tearDown();
	}

	protected TestProject getProject() {
		return project;
	}

	protected DesignerEditor getEditor() {
		return editor;
	}

	protected void setEditor(DesignerEditor editor) {
		this.editor = editor;
	}

	protected DesignerPaletteViewer getPaletteViewer() {
		return ((DesignerGraphicalEditorPage)getEditor().getGraphPage()).getDesignerPaletteViewer();
	}

	protected DesignerPaletteRoot getPaletteRoot() {
		return (DesignerPaletteRoot)getPaletteViewer().getPaletteRoot();
	}

	protected EditDomain getEditDomain() {
		return getEditor().getEditDomain();
	}

	protected DesignerModelViewer getModelViewer() {
		return ((DesignerGraphicalEditorPage)getEditor().getGraphPage()).getDesignerModelViewer();
	}

	protected ProcessDefinition getModel() {
		return getEditor().getProcessDefinition();
	}

	protected HashMap getPaletteEntryMap() {
		HashMap result = new HashMap();
		PaletteGroup group = (PaletteGroup)getPaletteRoot().getChildren().get(0);
		List entries = group.getChildren();
		Iterator iterator = entries.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (object instanceof ToolEntry) {
				ToolEntry entry = (ToolEntry)object;
				result.put(entry.getId(), entry);
			}
		}
		return result;
	}
					
	protected void mouseLeftClick() {
		mouseEvent.button = 1;
		getEditDomain().mouseDown(mouseEvent, getModelViewer());
		getEditDomain().mouseUp(mouseEvent, getModelViewer());
	}

	protected void moveMouseTo(Point point) {
		mouseEvent.x = point.x;
		mouseEvent.y = point.y;
		getEditDomain().mouseMove(mouseEvent, getModelViewer());
	}
	
	protected void pressDeleteKey() {
		keyEvent.character = SWT.DEL;
		getEditDomain().keyDown(keyEvent, getModelViewer());
		getEditDomain().keyUp(keyEvent, getModelViewer());
	}

	protected void chooseTool(String id) {
		ToolEntry toolEntry = (ToolEntry)getPaletteEntryMap().get(id);
		getEditDomain().setActiveTool(toolEntry.createTool());
	}

	protected void closeAndReopenEditor() throws Exception {
		getEditor().doSave(null);
		getPage().closeAllEditors(true);
		setEditor((DesignerEditor)IDE.openEditor(getPage(), getProject().openFile("firstprocess.par")));
	}

	protected void addNode(String toolId, Point location) {
		chooseTool(toolId);
		moveMouseTo(location);
		mouseLeftClick();
	}
	
	protected void selectAt(Point location) {
		chooseTool("org.jbpm.ui.palette.Selection");
		moveMouseTo(location);
		mouseLeftClick();
	}
	
	protected void removeNode(Point location) {
		selectAt(location);
		pressDeleteKey();
	}

	protected void addTransition(Point from, Point to) {
		chooseTool("org.jbpm.ui.palette.Transition");
		moveMouseTo(from);
		mouseLeftClick();
		moveMouseTo(to);
		mouseLeftClick();
	}

	protected void verifyEmptyProcessDefinition() {
		assertTrue(
				"The processdefinition should not have any nodes", 
				getModel().getNodes().isEmpty());		
	}

	protected Point findMidpointBetweenFiguresAt(Point firstLocation, Point secondLocation) {
		NodeEditPart firstPart = (NodeEditPart)getModelViewer().findObjectAt(firstLocation);
		Point firstCenter = firstPart.getFigure().getBounds().getCenter();
		NodeEditPart secondPart = (NodeEditPart)getModelViewer().findObjectAt(secondLocation);
		Point secondCenter = secondPart.getFigure().getBounds().getCenter();
		int xDiff = secondCenter.x - firstCenter.x;
		int yDiff = secondCenter.y - firstCenter.y;
		return firstCenter.getTranslated(xDiff / 2, yDiff / 2);
	}
	
}
