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
package org.jbpm.bpel;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jbpm.bpel.alarm.AlarmDbTest;
import org.jbpm.bpel.alarm.AlarmExeDbTest;
import org.jbpm.bpel.db.IntegrationSessionDbTest;
import org.jbpm.bpel.db.ScopeSessionDbTest;
import org.jbpm.bpel.db.type.QNameTypeDbTest;
import org.jbpm.bpel.def.ActivityDbTest;
import org.jbpm.bpel.def.AssignDbTest;
import org.jbpm.bpel.def.BpelDefinitionDbTest;
import org.jbpm.bpel.def.CatchAllDbTest;
import org.jbpm.bpel.def.CatchDbTest;
import org.jbpm.bpel.def.CompensateDbTest;
import org.jbpm.bpel.def.CompensationHandlerDbTest;
import org.jbpm.bpel.def.FlowDbTest;
import org.jbpm.bpel.def.IfDbTest;
import org.jbpm.bpel.def.ImportDbTest;
import org.jbpm.bpel.def.InvokeDbTest;
import org.jbpm.bpel.def.OnAlarmDbTest;
import org.jbpm.bpel.def.OnEventDbTest;
import org.jbpm.bpel.def.PickDbTest;
import org.jbpm.bpel.def.ReceiveDbTest;
import org.jbpm.bpel.def.ReplyDbTest;
import org.jbpm.bpel.def.ScopeDbTest;
import org.jbpm.bpel.def.SequenceDbTest;
import org.jbpm.bpel.def.TerminationHandlerDbTest;
import org.jbpm.bpel.def.ThrowDbTest;
import org.jbpm.bpel.def.WhileDbTest;
import org.jbpm.bpel.exe.FaultInstanceDbTest;
import org.jbpm.bpel.exe.LinkInstanceDbTest;
import org.jbpm.bpel.exe.ScopeInstanceDbTest;
import org.jbpm.bpel.integration.def.CorrelationDbTest;
import org.jbpm.bpel.integration.def.CorrelationSetDefinitionDbTest;
import org.jbpm.bpel.integration.def.InvokerDbTest;
import org.jbpm.bpel.integration.def.PartnerLinkDefinitionDbTest;
import org.jbpm.bpel.integration.def.ReceiverDbTest;
import org.jbpm.bpel.integration.def.ReplierDbTest;
import org.jbpm.bpel.integration.exe.CorrelationSetInstanceDbTest;
import org.jbpm.bpel.integration.exe.PartnerLinkInstanceDbTest;
import org.jbpm.bpel.integration.exe.wsa.WsaEndpointReferenceDbTest;
import org.jbpm.bpel.integration.exe.wsdl.WsdlEndpointReferenceDbTest;
import org.jbpm.bpel.sublang.def.ExpressionDbTest;
import org.jbpm.bpel.sublang.def.JoinConditionDbTest;
import org.jbpm.bpel.sublang.def.QueryDbTest;
import org.jbpm.bpel.variable.def.MessageTypeDbTest;
import org.jbpm.bpel.variable.def.VariableDefinitionDbTest;
import org.jbpm.bpel.variable.def.XmlTypeDbTest;
import org.jbpm.bpel.variable.exe.ElementInstanceDbTest;
import org.jbpm.bpel.variable.exe.ElementValueDbTest;
import org.jbpm.bpel.variable.exe.MessageValueDbTest;
import org.jbpm.bpel.variable.exe.SchemaValueDbTest;
import org.jbpm.bpel.wsdl.impl.MessageImplDbTest;
import org.jbpm.bpel.wsdl.impl.OperationImplDbTest;
import org.jbpm.bpel.wsdl.impl.PartnerLinkTypeImplDbTest;
import org.jbpm.bpel.wsdl.impl.PropertyImplDbTest;

/**
 * @author Juan Cantú
 * @version $Revision: 1.14 $ $Date: 2006/08/23 08:00:34 $
 */
public class AllDbTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("all db tests");

    // process definition
    suite.addTestSuite(BpelDefinitionDbTest.class);
    suite.addTestSuite(ImportDbTest.class);
    
    // activity
    suite.addTestSuite(ActivityDbTest.class);
    suite.addTestSuite(AssignDbTest.class);
    suite.addTestSuite(FlowDbTest.class);
    suite.addTestSuite(PickDbTest.class);
    suite.addTestSuite(SequenceDbTest.class);
    suite.addTestSuite(IfDbTest.class);
    suite.addTestSuite(WhileDbTest.class);
    suite.addTestSuite(ScopeDbTest.class);
    suite.addTestSuite(CompensateDbTest.class);
    suite.addTestSuite(ThrowDbTest.class);    
    suite.addTestSuite(ReceiveDbTest.class);  
    suite.addTestSuite(ReplyDbTest.class);  
    suite.addTestSuite(InvokeDbTest.class);
    
    // handler
    suite.addTestSuite(CompensationHandlerDbTest.class);
    suite.addTestSuite(TerminationHandlerDbTest.class);
    suite.addTestSuite(CatchDbTest.class);
    suite.addTestSuite(CatchAllDbTest.class);
    suite.addTestSuite(OnEventDbTest.class);
    suite.addTestSuite(OnAlarmDbTest.class);
    
    // process runtime
    suite.addTestSuite(FaultInstanceDbTest.class);
    suite.addTestSuite(ScopeInstanceDbTest.class);
    suite.addTestSuite(LinkInstanceDbTest.class);
    suite.addTestSuite(ScopeSessionDbTest.class);
    
    // variable definition
    suite.addTestSuite(VariableDefinitionDbTest.class);
    suite.addTestSuite(XmlTypeDbTest.class);
    suite.addTestSuite(MessageTypeDbTest.class);
    
    // variable runtime
    suite.addTestSuite(SchemaValueDbTest.class);
    suite.addTestSuite(ElementValueDbTest.class);
    suite.addTestSuite(MessageValueDbTest.class);
    suite.addTestSuite(ElementInstanceDbTest.class);
    suite.addTestSuite(QNameTypeDbTest.class);
    
    // sublang definition
    suite.addTestSuite(ExpressionDbTest.class);
    suite.addTestSuite(QueryDbTest.class);
    suite.addTestSuite(JoinConditionDbTest.class);
    
    // integration definition
    suite.addTestSuite(ReceiverDbTest.class);
    suite.addTestSuite(ReplierDbTest.class);
    suite.addTestSuite(InvokerDbTest.class);
    suite.addTestSuite(CorrelationDbTest.class);
    suite.addTestSuite(CorrelationSetDefinitionDbTest.class);
    suite.addTestSuite(PartnerLinkDefinitionDbTest.class);
    
    // integration runtime
    suite.addTestSuite(CorrelationSetInstanceDbTest.class);
    suite.addTestSuite(PartnerLinkInstanceDbTest.class);
    suite.addTestSuite(WsaEndpointReferenceDbTest.class);
    suite.addTestSuite(WsdlEndpointReferenceDbTest.class);
    suite.addTestSuite(IntegrationSessionDbTest.class);
    
    // wsdl
    suite.addTestSuite(MessageImplDbTest.class);
    suite.addTestSuite(OperationImplDbTest.class);
    suite.addTestSuite(PartnerLinkTypeImplDbTest.class);
    suite.addTestSuite(PropertyImplDbTest.class);
    
    // alarm
    suite.addTestSuite(AlarmDbTest.class);
    suite.addTestSuite(AlarmExeDbTest.class);
    
    return suite;
  }
}