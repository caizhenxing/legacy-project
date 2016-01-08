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
package org.jbpm.bpel.exe.flow;

import java.io.InputStream;

import org.w3c.dom.Element;

import org.jbpm.bpel.def.Flow;
import org.jbpm.bpel.def.Receive;
import org.jbpm.bpel.exe.AbstractExeTestCase;
import org.jbpm.bpel.xml.util.XmlUtil;
import org.jbpm.graph.exe.Token;

/**
 * @author Juan Cantú
 * @version $Revision: 1.8 $ $Date: 2006/08/22 04:13:16 $
 */
public class LinkScopingExampleTest extends AbstractExeTestCase {

  private Flow f1;
  private Flow f2;
  private Receive r1;
  private Receive r2;
  private Receive nestedR1;
  private Receive nestedR2;
  
  private static Element exampleElem;
  
  public void initActivities() {
    f2 = (Flow) f1.getNode("F2");
    r1 = (Receive) f1.getNode("R1");
    r2 = (Receive) f1.getNode("R2");
    nestedR1 = (Receive) f2.getNode("R1");
    nestedR2 = (Receive) f2.getNode("R2");
  }  
  
  public void testNested() throws Exception {
    f1 = (Flow) readActivity(exampleElem, false);
    plugInner(f1);
    initActivities();
    
    Token startToken = executeInner();
    Token r1Token = findToken( startToken, r1 );
    Token r2Token = findToken( startToken, r2 );
    Token nestedR1Token = findToken( startToken, nestedR1 );
    Token nestedR2Token = findToken( startToken, nestedR2 );
    
    assertReceiveDisabled( r2Token, r2 );
    assertReceiveDisabled(nestedR2Token, nestedR2 );
    
    //r1 is started, it must move at the flow's end. r2 target link is determined. 
    assertReceiveAndAdvance(r1Token, r1, f1.getEnd());

    //validate outer L1 link is determined and inner is unset
    assertEquals(Boolean.FALSE, f1.getLink("L1").getInstance(r1Token).getStatus());
    assertEquals(null, f2.getLink("L1").getInstance(nestedR1Token).getStatus());
    
    //r2 is not executed because its targets. it is skipped.
    assertReceiveDisabled( r2Token, r2 );
    assertReceiveDisabled( nestedR2Token, nestedR2 );
    assertReceiveAndAdvance( nestedR1Token, nestedR1, f2.getEnd() );
    assertReceiveAndComplete( nestedR2Token, nestedR2) ;
  }
  
  public void testInitial() throws Exception {
    f1 = (Flow) readActivity(exampleElem, true);
    plugOuter(f1);
    initActivities();
    nestedR1.setCreateInstance(true);
    
    Token startToken = executeOuter( nestedR1.getReceiver() );
    Token f2Token = startToken.getChild(f2.getName());
    //nested r1 is started, it must be at the flow's end. nested r2 target is determined. 
    Token nestedR1Token = f2Token.getChild(nestedR1.getName());
    assertSame( f2.getEnd(), nestedR1Token.getNode() );
    
    //validate inner L1 link is determined and outer is unset
    assertEquals(Boolean.TRUE, f2.getLink("L1").getInstance(f2Token).getStatus());
    assertEquals(null, f1.getLink("L1").getInstance(startToken).getStatus());

    Token nestedR2Token = f2Token.getChild(nestedR2.getName());
    //nested r2 receives message and moves to f1 end. (f2 flow is completed).
    assertReceiveAndAdvance(nestedR2Token, nestedR2, f2.getEnd());
    assertEquals(f1.getEnd(), f2Token.getNode());

    Token r2Token = startToken.getChild(r2.getName());
    //r2 receives haven't executed since its link (f1r1 is not yet resolved).
    assertEquals(r2, r2Token.getNode());
    assertReceiveDisabled( r2Token, r2 );
    
    Token r1Token = startToken.getChild(r1.getName());
    assertReceiveAndComplete(r1Token, r1);    
    //r2 was skipped (never executed since r1 resolved this link as negative)
    assertReceiveDisabled( r2Token, r2);
  }
  
  static {
    InputStream exampleStream = LinkScopingExampleTest.class.getResourceAsStream("linkScopingExample.bpel");
    try {
      exampleElem = XmlUtil.getDocumentBuilder()
      .parse(exampleStream)
      .getDocumentElement();
      exampleStream.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
