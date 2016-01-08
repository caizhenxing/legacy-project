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
package org.jbpm.ui.test.helper;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;


public class TestProject {
	
	public IProject project;
	
	public TestProject() throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject("TestProject");
		project.create(null);
		project.open(null);
	}
	
	public IProject getProject() {
		return project;
	}
	
	public void dispose() throws CoreException {
		project.delete(true, true, null);
	}
	
	public IFile openFile(String path) throws CoreException {
		IFile result = project.getFile(path);
		if (!result.exists()) {
			result = createNewFile(path);
		}
		return result;
	}
	
	public IFile createNewFile(String path) throws CoreException {
		IFile file = project.getFile(path);
		file.create(null, false, null);
//		file.setContents(getEmptyContents(), false, false, null);
		return file;
	}
	
}
