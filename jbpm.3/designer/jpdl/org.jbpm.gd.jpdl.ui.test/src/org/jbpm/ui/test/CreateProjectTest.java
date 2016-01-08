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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.junit.ITestRunListener;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.jbpm.ui.editor.DesignerEditor;
import org.jbpm.ui.model.ProcessDefinition;
import org.jbpm.ui.wizard.NewProcessProjectWizard;


public class CreateProjectTest extends TestCase {
	
	private static final String PROJECT_NAME = "test";
	private static final String PROJECT_NAME_FIELD = "projectNameField";
	private static ILaunchShortcut shortcut;
//	private static ICommand command;
	private WizardDialog dialog;
	private NewProcessProjectWizard wizard;
	private int numberOfTestsRun = 0;
	private int numberOfTestsFailed = 0;
	
//	private static ICommand getJUnitRunCommand() throws Exception {
//		if (command == null) {
//			IExtension[] extensions = 
//				Platform.getExtensionRegistry().getExtensionPoint("org.eclipse.ui.commands").getExtensions();
//			for (int i = 0; i < extensions.length; i++) {
//				IConfigurationElement[] configElements = extensions[i].getConfigurationElements();
//				for (int j = 0; j < configElements.length; j++) {
//					if ("command".equals(configElements[j].getName()) && 
//							"org.eclipse.jdt.junit.junitShortcut.run".equals(configElements[j].getAttribute("id"))) {
//						command = (ICommand)configElements[j].createExecutableExtension("class");
//					}
//				}
//			}		
//		}
//		return command;
//	}
	
	private static ILaunchShortcut getJUnitShortcut() throws Exception {
		if (shortcut == null) {
			IExtension[] extensions = 
				Platform.getExtensionRegistry().getExtensionPoint("org.eclipse.debug.ui.launchShortcuts").getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement[] configElements = extensions[i].getConfigurationElements();
				for (int j = 0; j < configElements.length; j++) {
					if ("shortcut".equals(configElements[j].getName()) && 
							"org.eclipse.jdt.junit.junitShortcut".equals(configElements[j].getAttribute("id"))) {
						shortcut = (ILaunchShortcut)configElements[j].createExecutableExtension("class");
					}
				}
			}		
		}
		return shortcut;
	}
	
	protected void tearDown() throws Exception {
		getProject().delete(true, true, null);
//		Thread.sleep(10000);
//		getProject().close(null);
//		getProject().delete(true, true, null);
//		assertFalse("Project does not exist", getProject().exists());
	}
	
	protected void setUp() throws Exception {
		if (getProject().exists()) {
			getProject().delete(true, true, null);
		}
		assertFalse("Project does not exist", getProject().exists());
		switchToJavaPerspective();
		openPackageExplorer();
		runNewProcessProjectWizard();
		JavaCore.create(getProject()).getRawClasspath();
	}
	
	private void switchToJavaPerspective() throws Exception {
		PlatformUI.getWorkbench().showPerspective(
				"org.eclipse.jdt.ui.JavaPerspective", 
				getActiveWorkbenchWindow());
	}
	
	private IWorkbenchWindow getActiveWorkbenchWindow() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}
	
	private void openPackageExplorer() throws Exception {
		getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.jdt.ui.PackageExplorer");
	}

	private void runNewProcessProjectWizard() throws Exception {
		wizard = new NewProcessProjectWizard();
		wizard.init(PlatformUI.getWorkbench(), null);
		dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
		dialog.create();
		dialog.setBlockOnOpen(false);
		dialog.open();
		setProjectName();
		wizard.performFinish();
		dialog.close();
	}

	private void setProjectName() throws Exception {
		WizardNewProjectCreationPage page = (WizardNewProjectCreationPage)wizard.getPage("basicNewProjectPage");
		Field field = page.getClass().getDeclaredField(PROJECT_NAME_FIELD);
		field.setAccessible(true);
		((Text)field.get(page)).setText(PROJECT_NAME);
	}
	
	private IProject getProject() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
	}
	
	private void incrementNumberOfTestsRun() {
		numberOfTestsRun++;
	}
	
	private void incrementNumberOfTestsFailed() {
		numberOfTestsFailed++;
	}
	
	private void verifySimpleProcessTest() throws Exception {
		ITestRunListener listener = new ITestRunListener() {

			public void testRunStarted(int testCount) {
				incrementNumberOfTestsRun();
				System.out.println("testRunStarted");				
			}

			public void testRunEnded(long elapsedTime) {
				System.out.println("testRunEnded");				
			}

			public void testRunStopped(long elapsedTime) {
				System.out.println("testRunStopped");				
			}

			public void testStarted(String testId, String testName) {
				System.out.println("testStarted: " + testId + ", " + testName);				
			}

			public void testEnded(String testId, String testName) {
				System.out.println("testEnded: " + testId + ", " + testName);				
			}

			public void testFailed(int status, String testId, String testName, String trace) {
				incrementNumberOfTestsFailed();
				System.out.println("testFailed");				
			}

			public void testRunTerminated() {
				System.out.println("testRunTerminated");				
			}

			public void testReran(String testId, String testClass, String testName, int status, String trace) {
				System.out.println("testReran");				
			}
			
		};
		JUnitCore.addTestRunListener(listener);
		IFile testFile = getProject().getFile(new Path("test/java/com/sample/SimpleProcessTest.java"));
		IDE.openEditor(getActiveWorkbenchWindow().getActivePage(), testFile);
		getJUnitShortcut().launch(new StructuredSelection(testFile), "debug");
		Thread.sleep(60000);
		JUnitCore.removeTestRunListener(listener);
//		assertEquals(numberOfTestsFailed, 0);
//		assertEquals(numberOfTestsRun, 1);
	}
	
	private void verifyProjectClasspath() throws Exception {
		IJavaProject javaProject = JavaCore.create(getProject());
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		assertEquals(entries.length, 6);
	}
	
	private void verifyOpenSimpleProcess() throws Exception {
		IFile processDefinitionFile = getProject().getFile(new Path("src/process/simple.par/processdefinition.xml"));
		DesignerEditor editor = (DesignerEditor)IDE.openEditor(getActiveWorkbenchWindow().getActivePage(), processDefinitionFile);
		assertNotNull(editor);
		ProcessDefinition processDefinition = editor.getProcessDefinition();
		assertEquals(processDefinition.getNodes().size(), 3);
		assertNotNull(processDefinition.getNodeByName("start"));
		assertNotNull(processDefinition.getNodeByName("first"));
		assertNotNull(processDefinition.getNodeByName("end"));
	}
	
	public void testProcessProjectIsCreated() throws Exception {
		assertTrue("Project does exist", getProject().exists());
		verifyOpenSimpleProcess();
		verifyProjectClasspath();
		verifySimpleProcessTest();
	}
	
	
	// Below was copied from other test. Maybe this needs to be in a utility class.
	
	protected void selectAndReveal(IResource newResource) {
		selectAndReveal(newResource, getActiveWorkbenchWindow());
	}

    private void selectAndReveal(IResource resource,
	           IWorkbenchWindow window) {
		if (!inputValid(resource, window)) return;   
		Iterator itr = getParts(window.getActivePage()).iterator();
		while (itr.hasNext()) {
		    selectAndRevealTarget(
					window, 
					new StructuredSelection(resource), 
					getTarget((IWorkbenchPart)itr.next()));
		}
	}
	
	private boolean inputValid(IResource resource, IWorkbenchWindow window) {
		if (window == null || resource == null) return false;
		else if (window.getActivePage() == null) return false;
		else return true;
	}

	private void selectAndRevealTarget(IWorkbenchWindow window, final ISelection selection, ISetSelectionTarget target) {
		if (target == null) return;
		final ISetSelectionTarget finalTarget = target;
		window.getShell().getDisplay().asyncExec(new Runnable() {
		    public void run() {
		        finalTarget.selectReveal(selection);
		    }
		});
	}
	
	private List getParts(IWorkbenchPage page) {
		List result = new ArrayList();
		addParts(result, page.getViewReferences());
		addParts(result, page.getEditorReferences());
		return result;
	}
	
	private void addParts(List parts, IWorkbenchPartReference[] refs) {
		for (int i = 0; i < refs.length; i++) {
           IWorkbenchPart part = refs[i].getPart(false);
           if (part != null) {
               parts.add(part);
           }
	    }		
	}
	
	private ISetSelectionTarget getTarget(IWorkbenchPart part) {
        ISetSelectionTarget target = null;
        if (part instanceof ISetSelectionTarget) {
            target = (ISetSelectionTarget)part;
        }
        else {
            target = (ISetSelectionTarget)part.getAdapter(ISetSelectionTarget.class);
        }
		return target;		
	}

}
