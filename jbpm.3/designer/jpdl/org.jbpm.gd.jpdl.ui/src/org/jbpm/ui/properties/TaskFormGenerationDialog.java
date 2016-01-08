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
package org.jbpm.ui.properties;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.jbpm.ui.DesignerLogger;
import org.jbpm.ui.DesignerPlugin;
import org.jbpm.ui.model.Task;
import org.jbpm.ui.model.Transition;
import org.jbpm.ui.model.Variable;
import org.jbpm.ui.taskform.Button;
import org.jbpm.ui.taskform.Field;
import org.jbpm.ui.taskform.FieldType;
import org.jbpm.ui.taskform.FormGenerator;

public class TaskFormGenerationDialog extends StatusDialog {
	
	private static final String pluginId = DesignerPlugin.getDefault().getBundle().getSymbolicName();
	
	private static final IStatus okStatus = new Status(
			Status.INFO, pluginId, 0, "Press OK to generate a form in the specified filename.", null);
	private static final IStatus noFileNameStatus = new Status(
			Status.ERROR, pluginId, 0, "Please specify a correct filename.", null);
	
	String title;
	Task task;
	DefaultControllerConfigurationComposite defaultControllerComposite;
	Text fileNameText;
	
	public TaskFormGenerationDialog(Shell parentShell, Task task) {
		super(parentShell);
		this.title = "Generate Task Form";
		this.task = task;
	}
	
	protected Point getInitialSize() {
		return new Point(500, 400);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite)super.createDialogArea(parent);
		getShell().setText(title);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		area.setLayout(gridLayout);
		createTaskVariablesInfoComposite(area);
		createFormFileField(area);
		getShell().setText(title);
		return area;
	}
	
	private void createTaskVariablesInfoComposite(Composite area) {
		defaultControllerComposite = new DefaultControllerConfigurationComposite(area, task.getControllerVariables());
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		defaultControllerComposite.setLayoutData(gridData);
	}
	
	private void createFormFileField(Composite area) {
		createFileLabel(area);
		createFileText(area);
		createFillSpace(area);
	}
	
	private void createFillSpace(Composite area) {
		Label label  = new Label(area, SWT.NORMAL);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		label.setLayoutData(gridData);
	}

	private void createFileText(Composite area) {
		fileNameText = new Text(area, SWT.BORDER);
		String taskName = task.getName();
		if (taskName == null || "".equals(taskName)) {
			taskName = "default";
		}
		StringBuffer buff = new StringBuffer(taskName);
		for (int i = 0; i < buff.length(); i++) {
			char c = buff.charAt(i); 
			if (!Character.isJavaIdentifierPart(i)) {
				c = '-';
			}
			buff.setCharAt(i, c);
		}
		taskName = buff.toString();
		int i = taskName.length();
		fileNameText.setText(taskName + ".xhtml");
		fileNameText.setSelection(0, i - 1);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		fileNameText.setLayoutData(gridData);
		fileNameText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				checkFileName();
			}			
		});
		checkFileName();
	}

	private void createFileLabel(Composite area) {
		Label label = new Label(area, SWT.NORMAL);
		label.setText("File name:");
		GridData fileLabelData = new GridData();
		fileLabelData.horizontalSpan = 2;
		label.setLayoutData(fileLabelData);
	}
	
	private void checkFileName() {
		String fileName = fileNameText.getText();
		if (fileName == null || "".equals(fileName)) {
			updateStatus(noFileNameStatus);
		} else {
			updateStatus(okStatus);
		}
	}
	
	protected void okPressed() {
		generateForm(); 
		super.okPressed();
	}
	
	private void generateForm() {
		createTaskFormFile();
		updateFormsXmlFile();
		refreshProcessFolder();
	}
	
	private void refreshProcessFolder() {
		try {
			getInputFile().getParent().refreshLocal(1, null);			
		} catch (CoreException e) {
			DesignerLogger.logError("Problem while refreshing process folder.", e);
		}
	}
		
	private boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
	
	private void createTaskFormFile() {
		List variables = defaultControllerComposite.getVariables();
		List fields = new ArrayList();
		List buttons = new ArrayList();
		for (int i = 0; i < variables.size(); i++) {
			Variable variable = (Variable)variables.get(i);
			Field field = new Field();
			field.setVariableName(variable.name);
			field.setLabel(isEmpty(variable.mappedName) ? variable.name : variable.mappedName);
			field.setFieldType(FieldType.getFieldTypes()[0]);
			fields.add(field);
		}
		List leavingTransitions = task.getParent().getLeavingTransitions();
	    buttons.add(Button.BUTTON_SAVE);
	    buttons.add(Button.BUTTON_CANCEL);
		for (int i = 0; i < leavingTransitions.size(); i++) {
			Transition transition = (Transition)leavingTransitions.get(i);
			String name = "End Task";
			if (!isEmpty(transition.getName())) {
				name = transition.getName();
			}
			buttons.add(Button.createTransitionButton(name));
		}
		getFile(fileNameText.getText(), FormGenerator.getForm(fields, buttons));
	}
	
	private void updateFormsXmlFile() {
		IFile formsXmlFile = getFile("forms.xml", "<forms/>");
		Document document = getDocument(formsXmlFile);
		addForm(document);
		saveDocument(formsXmlFile, document);		
	}
	
	private void saveDocument(IFile formFile, Document document) {
		try {
			StringWriter stringWriter = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(stringWriter, OutputFormat.createPrettyPrint());
			xmlWriter.write(document);
			formFile.setContents(new ByteArrayInputStream(stringWriter.getBuffer().toString().getBytes()), true, true, null);
		} catch (IOException e) {
			DesignerLogger.logError("Problem writing xml document to file", e);						
		} catch (CoreException e) {
			DesignerLogger.logError("Problem writing xml document to file", e);						
		}
	}
	
	private void addForm(Document document) {
		Element element = document.getRootElement().addElement("form");
		element.addAttribute("task", task.getName());
		element.addAttribute("form", fileNameText.getText());
	}
	
	private Document getDocument(IFile file) {
		try {
			return new SAXReader().read(new InputStreamReader(file.getContents()));
		} catch (DocumentException e) {
			DesignerLogger.logError("Problem creating DOM document from forms.xml", e);			
		} catch (CoreException e) {
			DesignerLogger.logError("Problem getting the contents from forms.xml", e);			
		}
		return null;
		
	}
	
	private IFile getInputFile() {
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		return ((FileEditorInput)editorPart.getEditorInput()).getFile();
	}
	
	private IFile getFile(String name, String initialContents) {
		IFile file = getInputFile();
		IPath path = file.getProjectRelativePath();
		file = file.getProject().getFile(path.removeLastSegments(1).append(name));
		if (!file.exists()) {
			try {
				file.create(new ByteArrayInputStream(initialContents.getBytes()), true, null);
			} catch (CoreException e) {
				DesignerLogger.logError("Could not create " + name, e);
			}
		}
		return file;
	}
	
}
