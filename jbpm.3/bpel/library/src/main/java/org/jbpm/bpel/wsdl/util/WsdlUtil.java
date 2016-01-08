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
package org.jbpm.bpel.wsdl.util;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Part;
import javax.wsdl.PortType;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.namespace.QName;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.EmptyIterator;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.jbpm.bpel.def.Import;
import org.jbpm.bpel.def.Import.Type;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.wsdl.xml.WsdlConstants;
import org.jbpm.bpel.wsdl.xml.WsdlFactoryImpl;
import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * Utility methods for dealing with JWSDL objects.
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:06:10 $
 */
public class WsdlUtil {

  private static final WSDLFactory factory = new WsdlFactoryImpl();
  private static final Log log = LogFactory.getLog(WsdlUtil.class);

  private WsdlUtil() {
    // supress default constructor, ensuring non-instantiability
  }

  public static WSDLFactory getFactory() {
    return factory;
  }

  public static Definition readText(String text) throws WSDLException {
    return getFactory().newWSDLReader().readWSDL(null,
        new InputSource(new StringReader(text)));
  }

  public static Definition readResource(Class clazz, String resource)
      throws WSDLException {
    String resourceURI = clazz.getResource(resource).toExternalForm();
    return getFactory().newWSDLReader().readWSDL(resourceURI);
  }

  public static Definition writeAndRead(Definition definition)
      throws WSDLException {
    WSDLFactory factory = getFactory();
    // write the definition to an in-memory sink
    CharArrayWriter output = new CharArrayWriter();
    factory.newWSDLWriter().writeWSDL(definition, output);
    // read the definition back from memory
    try {
      return factory.newWSDLReader().readWSDL(definition.getDocumentBaseURI(),
          XmlUtil.parseElement(output.toString()));
    }
    catch (SAXException e) {
      throw new WSDLException(WSDLException.PARSER_ERROR,
          "could not read the WSDL definitions back", e);
    }
  }

  public static void writeFile(WSDLWriter writer, Definition def, File file)
      throws WSDLException {
    // create parent directory if it does not exist
    File parentDir = file.getParentFile();
    if (parentDir != null) parentDir.mkdirs();
    OutputStream fileSink = null;
    try {
      // open a stream for writing
      fileSink = new FileOutputStream(file);
      Writer encoderSink;
      try {
        // try to use UTF-8 encoding
        encoderSink = new OutputStreamWriter(fileSink, "UTF-8");
      }
      catch (UnsupportedEncodingException e) {
        log.debug("could not use UTF-8 to write WSDL document", e);
        // fall back to platform's default encoding
        encoderSink = new OutputStreamWriter(fileSink);
      }
      // introduce a buffered writer to improve performance
      writer.writeWSDL(def, new BufferedWriter(encoderSink));
    }
    catch (FileNotFoundException e) {
      throw new WSDLException(WSDLException.OTHER_ERROR,
          "could not open file for writing: " + file, e);
    }
    finally {
      if (fileSink != null) {
        try {
          fileSink.close();
        }
        catch (IOException e) {
          log.warn("could not close file: " + file, e);
        }
      }
    }
  }

  public static PortType getPortType(Definition def, QName name) {
    return (PortType) new WsdlElementLookup() {

      protected Object getLocalElement(Definition def, QName name) {
        return def.getPortTypes().get(name);
      }
    }.getElement(def, name);
  }

  public static Message getMessage(Definition def, QName name) {
    return (Message) new WsdlElementLookup() {

      protected Object getLocalElement(Definition def, QName name) {
        return def.getMessages().get(name);
      }
    }.getElement(def, name);
  }

  public static PartnerLinkType getPartnerLinkType(Definition def, QName name) {
    return (PartnerLinkType) new WsdlElementLookup() {

      protected Object getLocalElement(Definition def, QName name) {
        Iterator partnerLinkTypeIt = getExtensions(def.getExtensibilityElements(),
            WsdlConstants.Q_PARTNER_LINK_TYPE);
        while (partnerLinkTypeIt.hasNext()) {
          PartnerLinkType partnerLinkType = (PartnerLinkType) partnerLinkTypeIt.next();
          if (partnerLinkType.getQName().equals(name)) return partnerLinkType;
        }
        return null;
      }
    }.getElement(def, name);
  }

  public static Property getProperty(Definition def, QName name) {
    return (Property) new WsdlElementLookup() {

      protected Object getLocalElement(Definition def, QName name) {
        Iterator propertyIt = getExtensions(def.getExtensibilityElements(),
            WsdlConstants.Q_PROPERTY);
        while (propertyIt.hasNext()) {
          Property property = (Property) propertyIt.next();
          if (property.getQName().equals(name)) return property;
        }
        return null;
      }
    }.getElement(def, name);
  }

  public static ExtensibilityElement getExtension(List extensions,
      QName extensionType) {
    if (extensions != null) {
      Iterator extensionIt = extensions.iterator();
      while (extensionIt.hasNext()) {
        ExtensibilityElement extension = (ExtensibilityElement) extensionIt.next();
        if (ExtensionTypePredicate.evaluate(extension, extensionType)) {
          return extension;
        }
      }
    }
    return null;
  }

  public static Iterator getExtensions(List extensions, QName extensionType) {
    return extensions != null ? new FilterIterator(extensions.iterator(),
        new ExtensionTypePredicate(extensionType)) : EmptyIterator.INSTANCE;
  }

  public static QName getDocLitElementName(Message message) {
    Map parts = message.getParts();
    return parts.size() == 1 ? ((Part) parts.values().iterator().next()).getElementName()
        : null;
  }

  public static Import createImport(Definition def) {
    Import imp = new Import();
    imp.setNamespace(def.getTargetNamespace());
    imp.setType(Type.WSDL);
    imp.setDocument(def);
    return imp;
  }

  private abstract static class WsdlElementLookup {

    public Object getElement(Definition def, QName name) {
      Object element = getLocalElement(def, name);
      if (element == null) {
        element = getImportedElement(def, name);
      }
      return element;
    }

    protected Object getImportedElement(Definition def, QName name) {
      // look in imports with a matching target namespace
      List imports = def.getImports(name.getNamespaceURI());
      if (imports != null) {
        for (int i = 0, n = imports.size(); i < n; i++) {
          javax.wsdl.Import imp = (javax.wsdl.Import) imports.get(i);
          Object element = getLocalElement(imp.getDefinition(), name);
          if (element != null) return element;
        }
      }
      // look in imported definitions of all imports
      Iterator importsIt = def.getImports().values().iterator();
      while (importsIt.hasNext()) {
        imports = (List) importsIt.next();
        for (int i = 0, n = imports.size(); i < n; i++) {
          javax.wsdl.Import imp = (javax.wsdl.Import) imports.get(i);
          Object element = getImportedElement(imp.getDefinition(), name);
          if (element != null) return element;
        }
      }
      return null;
    }

    protected abstract Object getLocalElement(Definition def, QName name);
  }

  private static class ExtensionTypePredicate implements Predicate {

    private final QName extensionType;

    ExtensionTypePredicate(QName type) {
      extensionType = type;
    }

    public boolean evaluate(Object arg) {
      return evaluate((ExtensibilityElement) arg, extensionType);
    }

    public static boolean evaluate(ExtensibilityElement extension,
        QName extensionType) {
      return extension.getElementType().equals(extensionType);
    }
  }
}
