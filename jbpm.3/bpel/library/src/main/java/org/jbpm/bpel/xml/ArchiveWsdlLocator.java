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

import org.xml.sax.InputSource;

import org.jbpm.jpdl.par.ProcessArchive;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ArchiveWsdlLocator extends ImportWsdlLocator {

  private final ProcessArchive archive;

  public ArchiveWsdlLocator(String bpelURI, ProcessArchive archive) {
    super(bpelURI);
    this.archive = archive;
  }

  protected InputSource createInputSource(String importURI) {
    InputSource inputSource = new InputSource(importURI);
    byte[] entry = archive.getEntry(importURI);
    if (entry != null) {
      inputSource.setByteStream(new ByteArrayInputStream(entry));
    }
    return inputSource;
  }

}