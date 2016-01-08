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

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.ejb.LocalCommandServiceHome;
import org.jbpm.ejb.RemoteCommandService;
import org.jbpm.ejb.impl.CommandListenerBean;
import org.jbpm.ejb.impl.CommandServiceBean;
import org.jbpm.graph.def.ProcessDefinition;
import org.mockejb.TransactionPolicy;

import com.mockrunner.ejb.EJBTestModule;
import com.mockrunner.jms.JMSTestCaseAdapter;

/**
 * Baseline abstract test for JMS Command tests.
 * Establishes a Mock JMS session, mock deploys
 * the command service (SLSB) and the command
 * listener (MDB).
 * 
 * @author Jim Rigsbee
 *
 */
public abstract class AbstractJmsTestCase extends JMSTestCaseAdapter {
		
	protected final String COMMAND_QUEUE = "JbpmQueue";
	protected QueueConnectionFactory qcf;
	protected Queue queue;
	protected QueueConnection qc;
	protected QueueSession session;
	protected EJBTestModule ejbModule;
	protected static JbpmConfiguration jbpmConfiguration = 
          JbpmConfiguration.parseResource("org/jbpm/jbpm.test.cfg.xml");
	protected JbpmContext context = null;
	protected GraphSession graphSession = null;
	protected RemoteCommandService service = null;
	
	protected void setUp() throws Exception {
			
      super.setUp();
      
      jbpmConfiguration.createSchema();
      context = jbpmConfiguration.createJbpmContext();
      graphSession = context.getGraphSession();
      ProcessDefinition processDefinition = 
          ProcessDefinition.parseXmlResource("org/jbpm/msg/jms/websale-processdefinition.xml");
      context.deployProcessDefinition(processDefinition);
      
      // Setup Mock J2EE container
      ejbModule = createEJBTestModule();
      ejbModule.bindToContext("java:/jbpm/XAJbpmConfiguration", jbpmConfiguration);
        // Deploy Jbpm Service Stateless Session Bean
      ejbModule.setInterfacePackage("org.jbpm.command.ejb.interfaces");
      
      ejbModule.deploySessionBean("ejb/JbpmServiceSessionBean",  
    		  CommandServiceBean.class, TransactionPolicy.REQUIRED);
      service = (RemoteCommandService) ejbModule.createBean("ejb/JbpmServiceSessionBean");
      //ejbModule.bindToContext(LocalCommandServiceHome.JNDI_NAME, service);
        // Setup JMS Resources
      qcf = getJMSMockObjectFactory().getMockQueueConnectionFactory();
      getDestinationManager().createQueue(COMMAND_QUEUE);
	  qc = qcf.createQueueConnection();
	  session = qc.createQueueSession(true, QueueSession.AUTO_ACKNOWLEDGE);
	  queue = session.createQueue(COMMAND_QUEUE);
      
        // Deploy Message Driven Bean - Command Listener
      ejbModule.deployMessageBean("java:/XAConnectionFactory", "queue/JbpmQueue", qcf, queue, new CommandListenerBean());
      	      
	}

	protected void tearDown() throws Exception {
			
		context.close();
		jbpmConfiguration.dropSchema();
		graphSession = null;
		
	    session.commit();
	    session.close();
	    qc.close();
	    
		super.tearDown();
	}

}
