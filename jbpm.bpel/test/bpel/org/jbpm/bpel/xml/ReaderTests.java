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

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jbpm.bpel.app.ScopeMatcherTest;
import org.jbpm.bpel.par.BpelArchiveParserTest;
import org.jbpm.bpel.par.DefinitionDescArchiveParserTest;
import org.jbpm.bpel.par.ProcessArchiveTest;

/**
 * @author Juan Cantú
 * @version $Revision: 1.15 $ $Date: 2006/08/22 04:13:10 $
 */
public class ReaderTests {
  
  public static Test suite() {
    TestSuite suite = new TestSuite("reader tests");
    
    //process 
    suite.addTestSuite(BpelReaderTest.class);
    suite.addTestSuite(ImportReaderTest.class);
    suite.addTestSuite(PartnerLinkReaderTest.class);
    suite.addTestSuite(CorrelationsReaderTest.class);
    
    //activities
    suite.addTestSuite(ActivityReaderTest.class);
    suite.addTestSuite(InvokeReaderTest.class);
    suite.addTestSuite(ReceiveReaderTest.class);
    suite.addTestSuite(WaitReaderTest.class);
    suite.addTestSuite(SequenceReaderTest.class);
    suite.addTestSuite(FlowReaderTest.class);
    suite.addTestSuite(IfReaderTest.class);
    suite.addTestSuite(SwitchReaderTest.class);
    suite.addTestSuite(WhileReaderTest.class);
    suite.addTestSuite(PickReaderTest.class);
    suite.addTestSuite(ScopeReaderTest.class);
    suite.addTestSuite(ReplyReaderTest.class);
    suite.addTestSuite(CompensateReaderTest.class);
    suite.addTestSuite(ThrowReaderTest.class);
    suite.addTestSuite(AssignReaderTest.class);
    suite.addTestSuite(ValidateReaderTest.class);
    suite.addTestSuite(RepeatUntilReaderTest.class);
    
    //transformation tests
    suite.addTestSuite(BpelConverterTest.class);
    suite.addTestSuite(WsdlConverterTest.class);
    
    // application descriptor tests
    suite.addTestSuite(AppDescriptorReaderTest.class);
    suite.addTestSuite(ScopeMatcherTest.class);
    suite.addTestSuite(URLCatalogReaderTest.class);
    
    // process archive tests
    suite.addTestSuite(DefDescriptorReaderTest.class);
    suite.addTestSuite(BpelArchiveParserTest.class);
    suite.addTestSuite(DefinitionDescArchiveParserTest.class);
    suite.addTestSuite(ProcessArchiveTest.class);
    
    return suite;
  }
}
