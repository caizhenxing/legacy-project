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
package org.jbpm.help;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class GenerateToc {
	
	private static final String firstLine = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	private static final String secondLine = "<?NLS TYPE=\"org.eclipse.help.toc\"?>\n\n";
	private String xmlDir;
	private String htmlDir;
	private String tocDir;
	private String tocName;
	private String tocLabel;
	
	public static void main(String[] args) {
		GenerateToc generator = new GenerateToc();
		for (int i = 0; i < args.length; i++) {
			int index = args[i].indexOf('=');
			String name = args[i].substring(0, index);
			String value = args[i].substring(index + 1);
			generator.inject(name, value);
		}
		generator.createToc();
	}
	
	private void inject(String name, String value) {
		if ("xmlDir".equals(name)) xmlDir = value;
		if ("htmlDir".equals(name)) htmlDir = value;
		if ("tocDir".equals(name)) tocDir = value;
		if ("tocName".equals(name)) tocName = value;
		if ("tocLabel".equals(name)) tocLabel = value;
	}
	
	private void createToc() {
		System.out.println("Generating TOC for " + tocName);
		TreeMap modules = getModules(xmlDir + "master.xml");
		HashMap chapters = getChapters(modules);
		try {
			FileWriter writer = new FileWriter(new File(tocDir + "toc" + tocName + ".xml"));
			writer.write(firstLine);
			writer.write(secondLine);
			writer.write("<toc label=\"" + tocLabel + "\" link_to=\"toc.xml#" + tocName + "\">\n");
			writer.write("    <topic label=\"Table of Contents\" href=\"" + htmlDir + "index.html\" />\n");
			Iterator iterator = modules.keySet().iterator();
			while (iterator.hasNext()) {
				Integer index = (Integer)iterator.next();
				String htmlName = (String)modules.get(index);
				String chapterId = (String)chapters.get(htmlName);
				System.out.println("index = " + index);
				System.out.println("htmlName = " + htmlName);
				System.out.println("chapterId = " + chapterId);
				writer.write("    <topic label=\"" + chapterId + "\" href=\"" + htmlDir + htmlName + "\" />\n");
			}
			writer.write("</toc>");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private HashMap getChapters(TreeMap modules) {
		HashMap result = new HashMap();
		Iterator iterator = modules.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			String htmlName = (String)modules.get(key);
			result.put(htmlName, getChapterLabel(htmlDir + htmlName));
		}
		return result;
	}
	
	private String getContents(String fileName) {
		StringBuffer result = new StringBuffer();
		try {
			System.out.println("Reading file : " + fileName);
			FileReader reader = new FileReader(new File(fileName));
			int byteRead = 0;
			while ((byteRead = reader.read()) != -1) {
				result.append((char)byteRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString().trim();
	}
	
	private TreeMap getModules(String masterName) {
		String contents = getContents(masterName); 
		System.out.println("contents:");
		System.out.println(contents);
		TreeMap result = new TreeMap();
		int start= 0, end = 0;
		while (true) {
			start = contents.indexOf("ENTITY", end);
			if (start == -1) break;
			start += 6;
			end = contents.indexOf("SYSTEM", start);
			String moduleName = contents.substring(start, end).trim();
			start = contents.indexOf('"', end) + 1;
			end = contents.indexOf('"', start);
			String fileName = contents.substring(start, end).trim();
			String searchString = '&' + moduleName + ';';
			int moduleLocation = contents.indexOf(searchString);
			String htmlName = getChapterId(xmlDir + fileName) + ".html";
			result.put(new Integer(moduleLocation), htmlName);
		}
		return result;		
	}
	
	private String getChapterId(String fileName) {
		String contents = getContents(fileName);
		int start = contents.indexOf('"', contents.indexOf("id", contents.indexOf("chapter"))) + 1;
		int end = contents.indexOf('"', start);
		return contents.substring(start, end).trim();
	}

	private String getChapterLabel(String fileName) {
		String contents = getContents(fileName);
		int start = contents.indexOf("<title>") + 7;
		int end = contents.indexOf("</title>", start);
		return contents.substring(start, end).replaceAll("&nbsp;", " ");
	}

}
