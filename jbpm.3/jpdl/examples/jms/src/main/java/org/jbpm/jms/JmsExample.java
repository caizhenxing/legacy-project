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
package org.jbpm.jms;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jbpm.command.Command;
import org.jbpm.command.service.CommandFaultResponse;
import org.jbpm.msg.jms.CommandAck;
import org.jbpm.msg.jms.JmsMessageConstants;

/**
 * This JMS client application will control the websale 
 * process from beginning to its conclusion.  Running
 * this test requires that the CommandListenerBean and
 * the JbpmServiceSessionBean be deployed to the application
 * server named in the jndi.properties file.
 * 
 * The best way to run this is by using the jBPM Starter's Kit.
 * Make sure the JBoss Application Server is running and that the
 * jBPM services have been deployed successfully.
 * 
 * @author Jim Rigsbee
 *
 */
public class JmsExample {

	/**
	 * @param args No arguments processed by this main entry point
	 */
	public static void main(String[] args) {
		
		JmsExample example = new JmsExample();
		example.run();

	}
	
	public void run()
	{
		QueueConnection qc = null;
		QueueSession session = null;
		
		try {
			InitialContext ic = new InitialContext();
			QueueConnectionFactory qcf = 
				(QueueConnectionFactory) ic.lookup("XAConnectionFactory");
			qc = qcf.createQueueConnection();
			qc.start();
			session = qc.createQueueSession(true, QueueSession.AUTO_ACKNOWLEDGE);
			Queue queue = (Queue) ic.lookup("queue/JbpmQueue");
			QueueSender sender = session.createSender(queue);
			
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
			msg.setStringProperty(JmsMessageConstants.PROPERTY_ACTOR, "bert");
			
			// Create acknowledgement temporary queue
			TemporaryQueue replyQueue = session.createTemporaryQueue();
			msg.setJMSReplyTo(replyQueue);
			
			sender.send(msg);
			sender.close();
			session.commit();
			
			// Wait for reply
			QueueReceiver receiver = session.createReceiver(replyQueue);
			ObjectMessage replyMessage = null;
			for(int i = 0; i < 5; i++) // try 5 times to get the ack message
			{
				replyMessage = (ObjectMessage) receiver.receive(2000); // wait 2 seconds
				if (replyMessage != null)
					break;
			}
			
			if (replyMessage == null)
				System.out.println("Command acknowledgement not received.");
			else
			{
				CommandAck ack = (CommandAck) replyMessage.getObject();
				Object o = ack.getResult();
				if (o != null)
				  System.out.println(o instanceof CommandFaultResponse ? "failure" : "success");
				Long id = (Long) o;
				System.out.println("New process instance id:  " + id);
			}

			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
			 if (session != null)
				 session.close();
			 
			 if (qc != null)
			 {
				 qc.stop();
				 qc.close();
			 }
			}
			catch(JMSException e) { };
		}
		
	}

}
