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
package org.jbpm.bpel.tutorial.hello;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.namespace.QName;
import javax.xml.rpc.Call;

import junit.framework.TestCase;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:06:10 $
 */
public class ClientTest extends TestCase {

  private HelloWorldService service;

  public ClientTest(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    InitialContext ctx = getInitialContext();
    // JNDI name of service interface (in application-client.xml)
    String serviceRefName = "service/Hello";
    // lookup service interface in environment context
    service = (HelloWorldService) ctx.lookup("java:comp/env/" + serviceRefName);
  }

  protected InitialContext getInitialContext() throws NamingException {
    // prepare enviroment
    Properties env = new Properties();
    // JNDI name of client environment context (in jboss-client.xml)
    env.setProperty("j2ee.clientName", "hello-client");
    // initial context contains property above, plus those in jndi.properties 
    return new InitialContext(env);
  }
  
  public void testSayHello_proxy() throws Exception {
    // obtain dynamic proxy for web service port
    HelloPT proxy = service.getCallerPort();    
    // use proxy as local java object
    String greeting = proxy.sayHello("Popeye");
    // check proper greeting
    assertEquals("Hello, Popeye!", greeting);
  }
  
  public void testSayHello_dii() throws Exception {
    // obtain port type namespace; it MAY differ from service
    String portTypeNS = service.getServiceName().getNamespaceURI();
    // obtain dynamic invocation instance 
    Call call = service.createCall(new QName(portTypeNS, "callerPort"), "sayHello");
    // invoke operation using request/response interaction mode
    String greeting = (String) call.invoke(new Object[] { "Olive" });
    // check proper greeting
    assertEquals("Hello, Olive!", greeting);
  }
}