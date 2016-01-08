/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the JBPM BPEL PUBLIC LICENSE AGREEMENT as
 * published by JBoss Inc.; either version 1.0 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.jbpm.bpel.xml;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.jbpm.bpel.def.Import;
import org.jbpm.bpel.par.DefDescriptor;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.jpdl.xml.Problem;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.4 $ $Date: 2006/08/29 23:02:43 $
 */
public class DefDescriptorReader {

  private static final DefDescriptorReader INSTANCE = new DefDescriptorReader();

  private ProblemHandler problemHandler;

  protected DefDescriptorReader() {
  }

  public void read(DefDescriptor defDescriptor, InputSource input) {
    // get the thread-local parser
    DocumentBuilder builder = XmlUtil.getDocumentBuilder();
    // prepare custom error handling
    ErrorHandlerAdapter problemAdapter = new ErrorHandlerAdapter(
        getProblemHandler());
    builder.setErrorHandler(problemAdapter);
    try {
      // parse content
      Element definitionElem = builder.parse(input).getDocumentElement();
      // proceed only if no parse errors arose
      if (!problemAdapter.hasErrors()) {
        // process document location
        String location = definitionElem.getAttribute(BpelConstants.ATTR_LOCATION);
        defDescriptor.setLocation(location);
        // private imports
        Element importsElem = XmlUtil.getElement(definitionElem,
            BpelConstants.NS_VENDOR, BpelConstants.ELEM_IMPORTS);
        if (importsElem != null) {
          readImports(importsElem, defDescriptor);
        }
      }
    }
    catch (SAXException e) {
      getProblemHandler().add(
          new Problem(Problem.LEVEL_ERROR,
              "definition descriptor contains invalid xml", e));
    }
    catch (IOException e) {
      getProblemHandler().add(
          new Problem(Problem.LEVEL_ERROR,
              "definition descriptor is not readable", e));
    }
    finally {
      // reset error handling behavior
      builder.setErrorHandler(null);
    }
  }

  public void readImports(Element importsElem, DefDescriptor defDescriptor) {
    Iterator importElemIt = XmlUtil.getElements(importsElem,
        BpelConstants.NS_VENDOR);
    while (importElemIt.hasNext()) {
      Object obj = importElemIt.next();
      Element importElem = (Element) obj;
      Import imp = readImport(importElem);
      defDescriptor.addImport(imp);
    }
  }

  public Import readImport(Element importElem) {
    Import imp = new Import();
    imp.setLocation(importElem.getAttribute(BpelConstants.ATTR_LOCATION));
    // namespace is optional as of 1.1.Beta2
    imp.setNamespace(XmlUtil.getAttribute(importElem,
        BpelConstants.ATTR_NAMESPACE));
    // determine type based on element name
    String importName = importElem.getLocalName();
    Import.Type importType = BpelConstants.ELEM_WSDL.equals(importName) ? Import.Type.WSDL
        : Import.Type.XML_SCHEMA;
    imp.setType(importType);
    return imp;
  }

  public void setProblemHandler(ProblemHandler problemHandler) {
    this.problemHandler = problemHandler;
  }

  protected ProblemHandler getProblemHandler() {
    if (problemHandler == null) {
      problemHandler = new DefaultProblemHandler();
    }
    return problemHandler;
  }

  public static DefDescriptorReader getInstance() {
    return INSTANCE;
  }
}
