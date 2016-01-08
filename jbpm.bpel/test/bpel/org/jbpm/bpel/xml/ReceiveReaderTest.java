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

import org.jbpm.bpel.def.Receive;
/**
 * @author Juan Cantú
 * @version $Revision: 1.5 $ $Date: 2006/08/22 04:13:10 $
 */
public class ReceiveReaderTest extends AbstractReaderTestCase {
  
  public void setUp() throws Exception {
    super.setUp();
    initMessageProperties();
  }  
  
  public void testCreateInstanceYes() throws Exception {
    String xml = 
      "<receive partnerLink='aPartner' operation='o' variable='iv'" +
      "         createInstance='yes'/>";
    
    Receive receive = (Receive) readActivity(xml);
    assertEquals(true, receive.isCreateInstance());
  }
  
  public void testCreateInstanceNo() throws Exception {
    String xml = 
      "<receive partnerLink='aPartner' operation='o' variable='iv'" +
      "         createInstance='no'/>";
    
    Receive receive = (Receive) readActivity(xml);
    assertEquals(false, receive.isCreateInstance());
  }
  
  public void testCreateInstanceDefault() throws Exception {
    String xml = "<receive partnerLink='aPartner' operation='o' variable='iv'/>";
    
    Receive receive = (Receive) readActivity(xml);
    assertEquals(false, receive.isCreateInstance());
  }    
}
