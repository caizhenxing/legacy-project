/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.msg.jms;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.TextMessage;

import org.jbpm.command.Command;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;

/**
 * Test starting a process asynchronously using JMS.
 * 
 * @author Jim Rigsbee
 *
 */
public class StartProcessJmsTest extends AbstractJmsTestCase {
/*
	public void testStartProcessCommand()
	{
		try {
			
			TextMessage msg = session.createTextMessage();
			
			msg.setStringProperty(JmsMessageConstants.PROPERTY_CMD, Command.CMD_START_PROCESS);
			msg.setStringProperty(JmsMessageConstants.PROPERTY_PROCESSNAME, "websale");
			msg.setIntProperty(JmsMessageConstants.PROPERTY_PROCESSVERSION, 1);
			msg.setBooleanProperty(JmsMessageConstants.PROPERTY_RETURNCONTEXT, false);
			msg.setStringProperty(JmsMessageConstants.PROPERTY_ACTOR, "bert");
			
			QueueSender sender = session.createSender(queue);
			sender.send(msg);
			sender.close();
			
			validateProcessStarted();
		    
		} catch (JMSException e) {
			fail("Unexpected exception " + e.toString());
		}
	}
	
	public void testStartProcessCommandWithReplyAck()
	{
		try {
			
			TextMessage msg = session.createTextMessage();
			
			msg.setStringProperty(JmsMessageConstants.PROPERTY_CMD, Command.CMD_START_PROCESS);
			msg.setStringProperty(JmsMessageConstants.PROPERTY_PROCESSNAME, "websale");
			msg.setIntProperty(JmsMessageConstants.PROPERTY_PROCESSVERSION, 1);
			msg.setBooleanProperty(JmsMessageConstants.PROPERTY_RETURNCONTEXT, false);
			msg.setStringProperty(JmsMessageConstants.PROPERTY_ACTOR, "bert");			
			
			// Create reply queue
			Queue ackQueue = getDestinationManager().createQueue("myAck");
			msg.setJMSReplyTo(ackQueue);
			
			QueueSender sender = session.createSender(queue);
			sender.send(msg);
			sender.close();
			
			// Make sure process got started
			validateProcessStarted();
			
			// Check for Acknowledgement
			QueueReceiver receiver = session.createReceiver(ackQueue);
			Message reply = receiver.receiveNoWait();
			assertNotNull("Reply message received", reply);
			assertTrue("Correct type of message", reply instanceof ObjectMessage);
			Object _ack = ((ObjectMessage) reply).getObject();
			assertNotNull("Acknowledgement object in message", _ack);
			assertTrue("Correct ack class", _ack instanceof CommandAck);
			
			// Check content of Ack

			CommandAck ack = (CommandAck) _ack;
			Object o = ack.getResult();
			assertNotNull("Expecting a return value", o);
			assertTrue("Expecting java.lang.Long", o instanceof Long);
			assertEquals("Process instance id", 1L, ((Long) o).longValue());
			
		} catch (JMSException e) {
			fail("Unexpected exception " + e.toString());
		}
	}
	
	public void testStartProcessCommandWithReplyAckAndPayload()
	{
		try {
			
			ObjectMessage msg = session.createObjectMessage();
			
			HashMap map = new HashMap();
			map.put("long99", new Long(99));
			map.put("stringABC", "ABC");
			map.put("boolean", new Boolean(false));
			msg.setObject(map);
			msg.setStringProperty(JmsMessageConstants.PROPERTY_CMD, Command.CMD_START_PROCESS);
			msg.setStringProperty(JmsMessageConstants.PROPERTY_PROCESSNAME, "websale");
			msg.setIntProperty(JmsMessageConstants.PROPERTY_PROCESSVERSION, 1);
			msg.setBooleanProperty(JmsMessageConstants.PROPERTY_RETURNCONTEXT, false);
			// We did not set actor here on purpose to make sure that a task doesn't start
			// in this case
			
			// Create reply queue
			Queue ackQueue = getDestinationManager().createQueue("myAck");
			msg.setJMSReplyTo(ackQueue);
			
			QueueSender sender = session.createSender(queue);
			sender.send(msg);
			sender.close();
			
			// Make sure process got started
			ProcessDefinition pd = 
				graphSession.findProcessDefinition("websale", 1);
			assertNotNull("Looking for process definition", pd);
			List instances = graphSession.findProcessInstances(pd.getId());
			assertEquals("Looking for process instances", 1,instances.size());
			ProcessInstance pi = validateProcessStarted();
			
			// Check for Acknowledgement
			QueueReceiver receiver = session.createReceiver(ackQueue);
			Message reply = receiver.receiveNoWait();
			assertNotNull("Reply message received", reply);
			assertTrue("Correct type of message", reply instanceof ObjectMessage);
			Object _ack = ((ObjectMessage) reply).getObject();
			assertNotNull("Acknowledgement object in message", _ack);
			assertTrue("Correct ack class", _ack instanceof CommandAck);
			
			// Check content of Ack

			CommandAck ack = (CommandAck) _ack;
			Object o = ack.getResult();
			assertNotNull("Expecting a return value", o);
			assertTrue("Expecting java.lang.Long", o instanceof Long);
			assertEquals("Process instance id", 1L, ((Long) o).longValue());
			
			// Verify context instance values
			ContextInstance ci = pi.getContextInstance();
			String var = (String) ci.getVariable("stringABC");
			assertEquals("stringABC", "ABC", var);
			Long var2 = (Long) ci.getVariable("long99");
			assertEquals("long99", 99L, var2.longValue());
			Boolean var3 = (Boolean) ci.getVariable("boolean");
			assertEquals("boolean", false, var3.booleanValue());
			
			// Verify task not started
			Collection tasks = pi.getTaskMgmtInstance().getTaskInstances();
			assertNull("verify task not started", tasks);
			
		} catch (JMSException e) {
			fail("Unexpected exception " + e.toString());
		}
	}
	
	private ProcessInstance validateProcessStarted() {
		// Make sure process got started
		ProcessDefinition pd = 
			graphSession.findProcessDefinition("websale", 1);
		assertNotNull("Finding process", pd);
		List instances = graphSession.findProcessInstances(pd.getId());
		assertEquals("Finding process instances", 1,instances.size());
		
		return (ProcessInstance) instances.get(0);
	}
    */
}
