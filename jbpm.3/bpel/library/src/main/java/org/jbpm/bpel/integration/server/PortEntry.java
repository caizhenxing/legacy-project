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
package org.jbpm.bpel.integration.server;

import java.io.Serializable;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class PortEntry implements Serializable {

  private String processName;
  private int processVersion;
  private long partnerLinkId;
  private String wsdlLocationUri;

  private static final long serialVersionUID = 1L;

  public String getProcessName() {
    return processName;
  }

  public void setProcessName(String processName) {
    this.processName = processName;
  }

  public int getProcessVersion() {
    return processVersion;
  }

  public void setProcessVersion(int processVersion) {
    this.processVersion = processVersion;
  }

  public long getPartnerLinkId() {
    return partnerLinkId;
  }

  public void setPartnerLinkId(long partnerLink) {
    this.partnerLinkId = partnerLink;
  }

  public String getWsdlLocationUri() {
    return wsdlLocationUri;
  }

  public void setWsdlLocationUri(String myReference) {
    this.wsdlLocationUri = myReference;
  }

}
