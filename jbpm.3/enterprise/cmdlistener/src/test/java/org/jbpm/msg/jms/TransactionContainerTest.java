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


public class TransactionContainerTest extends AbstractContainerTestCase {
/**
	public void testCommit()
	{
		try
		{
			// Send message to start a websale instance
			
			createQueue();
			ObjectMessage msg = session.createObjectMessage();
			
			HashMap map = new HashMap();
			map.put("string1", "GGG");
			map.put("boolean1", new Boolean(true));
			msg.setObject(map);
			msg.setStringProperty(JmsMessageConstants.PROPERTY_CMD, Command.CMD_START_PROCESS);
			msg.setStringProperty(JmsMessageConstants.PROPERTY_PROCESSNAME, "websale");
			msg.setIntProperty(JmsMessageConstants.PROPERTY_PROCESSVERSION, 1);
			msg.setBooleanProperty(JmsMessageConstants.PROPERTY_RETURNCONTEXT, true);
			
			createReplyQueue();
			
			msg.setJMSReplyTo(rqueue);
			
			sender.send(msg);
			
			releaseQueue();
					
			ObjectMessage reply = null;
			for(int i = 0; i < 12; i++) {  // give it 60 seconds
				reply = (ObjectMessage) receiver.receive(5000);
				if (reply != null)
					break;
			}
			
			assertNotNull("Did not receive reply", reply);
			
			
			CommandAck ack = (CommandAck) reply.getObject();
			assertNotNull("Did not get acknowledgement from reply", ack);
			
			Object o = ack.getResult();
			assertNotNull("Expecting a result object from the command execution.", o);
			assertTrue("Expecting a java.lang.Long object", o instanceof Long);
			
			Long instanceId = (Long) o;
			
			// Make sure that the instance was created
			RemoteCommandService service = getCommandService();
			
			Object result = 
				service.executeCommand( new QueryStateCommand( instanceId.longValue(), true ) );
			
			assertNotNull("Expected a response from QueryStateCommand", result);
			assertTrue("Expected an instance of QueryStateResponse", result instanceof QueryStateResponse);
			
			QueryStateResponse response = (QueryStateResponse) result;
			assertTrue("Status should be in progress", response.getStatus() 
					== QueryStateResponse.STATUS_IN_PROGRESS);
			
			assertEquals("Expecting one token", 1, response.getActiveTokenPaths().size());
			assertNull("There should be no end date", response.getEnd());
			assertNotNull("There should be a start date", response.getStart());
			
			assertNotNull("There should be a task list", response.getTaskList() );
			assertEquals("There should be one task started", 1, response.getTaskList().size() );
			
			TaskInstance task = (TaskInstance) response.getTaskList().get(0);
			assertNotNull(task);
			
			Map vars = task.getVariables();			
			assertNotNull("Expecting variables to be returned", vars);
			
			Object var = vars.get("string1");
			assertNotNull("Examining string1 variable", var);
			assertTrue(var instanceof String);
			assertEquals("GGG", (String) var);
			
			var = vars.get("boolean1");
			assertNotNull("Examining boolean1 variable", var);
			assertTrue(var instanceof Boolean);
			assertEquals(true, ((Boolean) var).booleanValue() );	
			
		}
		catch(Exception e)
		{
			fail("Unexpected exception:  " + e.getMessage());
		}
		finally
		{
			releaseQueue();
			releaseReplyQueue();
		}
	}
	
	public void testRollback()
	{
		
	}
*/
}
