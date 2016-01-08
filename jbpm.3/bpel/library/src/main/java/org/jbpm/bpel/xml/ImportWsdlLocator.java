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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.wsdl.xml.WSDLLocator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import org.jbpm.bpel.wsdl.xml.WsdlConstants;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.jpdl.xml.Problem;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ImportWsdlLocator implements WSDLLocator {

  private final URI importBaseURI;
  private String baseURI;
  private String latestImportURI;
  private ProblemHandler problemHandler;
  private boolean hasErrors;

  private static final String UPGRADABLE_ELEMENTS_XPATH = "*[namespace-uri() = '"
      + BpelConstants.NS_BPWS_1_1
      + "' or"
      + "  namespace-uri() = '"
      + WsdlConstants.NS_PLNK_1_1 + "']";

  private static Templates wsdlUpdateTemplates;

  private static final Log log = LogFactory.getLog(ImportWsdlLocator.class);

  public ImportWsdlLocator(String importBaseURI) {
    this.importBaseURI = URI.create(importBaseURI);
  }

  public void resolveBaseURI(String location) {
    baseURI = importBaseURI.resolve(location).toString();
  }

  public String getBaseURI() {
    return baseURI;
  }

  public InputSource getBaseInputSource() {
    return getInputSource(baseURI);
  }

  public InputSource getImportInputSource(String parentLocation,
      String importLocation) {
    try {
      URI importURI = new URI(importLocation);
      return getInputSource(importURI.isAbsolute() ? importLocation : new URI(
          parentLocation).resolve(importURI).toString());
    }
    catch (URISyntaxException e) {
      return null;
    }
  }

  public String getLatestImportURI() {
    return latestImportURI;
  }

  protected InputSource createInputSource(String importURI) {
    return new InputSource(importURI);
  }

  private InputSource getInputSource(String importURI) {
    InputSource inputSource = createInputSource(importURI);
    latestImportURI = importURI;
    // get the thread-local parser
    DocumentBuilder documentParser = XmlUtil.getDocumentBuilder();
    // prepare custom error handling
    ProblemHandler problemHandler = getProblemHandler();
    ErrorHandlerAdapter parseAdapter = new ErrorHandlerAdapter(problemHandler);
    documentParser.setErrorHandler(parseAdapter);
    try {
      // parse content
      Document wsdlDocument = documentParser.parse(inputSource);
      // halt on parser errors
      if (parseAdapter.hasErrors()) {
        hasErrors = true;
      }
      // see if the bpel document requires updating
      else if (hasUpgradableElements(wsdlDocument)) {
        // prepare custom error handling
        ErrorListenerAdapter transformAdapter = new ErrorListenerAdapter(
            problemHandler);
        try {
          // create an wsdl transformer
          Transformer wsdlUpdater = getWsdlUpdateTemplates().newTransformer();
          wsdlUpdater.setErrorListener(transformAdapter);
          // update to a new byte stream
          ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
          wsdlUpdater.transform(new DOMSource(wsdlDocument), new StreamResult(
              resultStream));
          inputSource.setByteStream(new ByteArrayInputStream(
              resultStream.toByteArray()));
          log.debug("converted wsdl document: " + importURI);
        }
        catch (TransformerException e) {
          transformAdapter.error(e);
        }
        if (transformAdapter.hasErrors()) {
          hasErrors = true;
        }
      }
      else {
        InputStream parsedStream = inputSource.getByteStream();
        // if the parser consumed a stream, we must reset it
        if (parsedStream != null) {
          try {
            parsedStream.reset();
          }
          catch (IOException e) {
            log.error("could not reset document stream: " + importURI, e);
          }
        }
      }
    }
    catch (Exception e) {
      problemHandler.add(new Problem(Problem.LEVEL_ERROR,
          "could not read wsdl document", e));
    }
    finally {
      // reset error handling behavior
      documentParser.setErrorHandler(null);
    }
    return inputSource;
  }

  public static synchronized Templates getWsdlUpdateTemplates() {
    if (wsdlUpdateTemplates == null) {
      wsdlUpdateTemplates = XmlUtil.createTemplates(ImportWsdlLocator.class.getResource("wsdl-1-1-converter.xsl"));
    }
    return wsdlUpdateTemplates;
  }

  private static boolean hasUpgradableElements(Document wsdlDocument) {
    Object value = XmlUtil.evaluateXPath(UPGRADABLE_ELEMENTS_XPATH,
        wsdlDocument.getDocumentElement(),
        null);
    return value instanceof Element || value instanceof List;
  }

  public ProblemHandler getProblemHandler() {
    if (problemHandler == null) {
      problemHandler = new DefaultProblemHandler();
    }
    return problemHandler;
  }

  public void setProblemHandler(ProblemHandler problemHandler) {
    this.problemHandler = problemHandler;
  }

  public boolean hasErrors() {
    return hasErrors;
  }
}