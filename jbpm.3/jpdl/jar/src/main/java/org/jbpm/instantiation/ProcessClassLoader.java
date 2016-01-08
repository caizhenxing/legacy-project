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
package org.jbpm.instantiation;

import java.io.*;

import org.jbpm.JbpmException;
import org.jbpm.file.def.*;
import org.jbpm.graph.def.*;

public class ProcessClassLoader extends ClassLoader {
  
  private ProcessDefinition processDefinition = null;

  public ProcessClassLoader( ClassLoader parent, ProcessDefinition processDefinition ) {
    super(parent);
    this.processDefinition = processDefinition;
  }

  public InputStream getResourceAsStream(String name) {
    InputStream inputStream = null;
    FileDefinition fileDefinition = processDefinition.getFileDefinition();
    if (fileDefinition!=null) {
      byte[] bytes = fileDefinition.getBytes(name);
      if (bytes!=null) {
        inputStream = new ByteArrayInputStream(bytes);
      }
    }
    return inputStream;
  }

  public Class findClass(String name) throws ClassNotFoundException {
    Class clazz = null;

    FileDefinition fileDefinition = processDefinition.getFileDefinition();
    if (fileDefinition!=null) {
      String fileName = "classes/" + name.replace( '.', '/' ) + ".class";
      byte[] classBytes;
      try {
        classBytes = fileDefinition.getBytes(fileName);
        clazz = defineClass(name, classBytes, 0, classBytes.length);
      } catch (JbpmException e) {
        clazz = null;
      }
    }

    if (clazz==null) {
      throw new ClassNotFoundException("class '"+name+"' could not be found by the process classloader");
    }

    return clazz;
  }
}
