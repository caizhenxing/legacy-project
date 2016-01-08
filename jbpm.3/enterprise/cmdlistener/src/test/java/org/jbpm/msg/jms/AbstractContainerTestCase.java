package org.jbpm.msg.jms;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.jbpm.ejb.RemoteCommandService;
import org.jbpm.ejb.RemoteCommandServiceHome;

public class AbstractContainerTestCase extends TestCase {

  protected QueueConnectionFactory qcf = null; 
  protected QueueConnection qc = null;
  protected QueueSession session = null;
  protected Queue queue = null;
  protected QueueSender sender = null;
  
  protected QueueConnectionFactory rqcf = null;
  protected QueueConnection rqc = null;
  protected QueueSession rsession = null;
  protected TemporaryQueue rqueue = null;
  protected QueueReceiver receiver = null;
  
  protected void createQueue() throws Exception {
		
	InitialContext ic = new InitialContext();
	qcf = (QueueConnectionFactory) ic.lookup("XAConnectionFactory");
	qc = qcf.createQueueConnection();
	qc.start();
	session = qc.createQueueSession(true, QueueSession.AUTO_ACKNOWLEDGE);
	queue = (Queue) ic.lookup("queue/JbpmQueue");
	sender = session.createSender(queue);	  
	  
  }
  
  protected void releaseQueue() {

	  try
	  {
		  
		  if (sender != null) {
			  sender.close();
			  sender = null;
		  }
	
		  if (session != null) {
			  session.commit();
			  session.close();	
			  session = null;
		  }
		  
		  if (qc != null) {
			  qc.stop();
			  qc.close();
			  qc = null;
		  }

	  }
	  catch(JMSException e) {
		  // do nothing
		  qcf = null;
		  queue = null;
	  }
	  
  }
  
  protected void createReplyQueue() throws Exception {
	  
		InitialContext ic = new InitialContext();
		rqcf = (QueueConnectionFactory) ic.lookup("ConnectionFactory");
		rqc = rqcf.createQueueConnection();
		rqc.start();
		rsession = rqc.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		rqueue = rsession.createTemporaryQueue();
		receiver = rsession.createReceiver(rqueue);	  
  }
  
  protected void releaseReplyQueue() {
	  
	  try
	  {
		  
		  if (rqueue != null) {
			  rqueue.delete();
			  rqueue = null;
		  }
		  
		  if (receiver != null) {
			  receiver.close();
			  receiver = null;
		  }
	
		  if (rsession != null) {
			  rsession.commit();
			  rsession.close();	
			  rsession = null;
		  }
		  
		  if (rqc != null) {
			  rqc.stop();
			  rqc.close();
			  rqc = null;
		  }

	  }
	  catch(JMSException e) {
		  // do nothing
		  rqcf = null;
	  }  
	  
  }
  
  protected RemoteCommandService getCommandService() throws Exception
  {
	  RemoteCommandService service = null;
	  
	  InitialContext ic = new InitialContext();
      /*
	  RemoteCommandServiceHome home = (RemoteCommandServiceHome)
      ic.lookup( RemoteCommandServiceHome.JNDI_NAME );
	  service = home.create();
      */

	  return service;
  }
  
}
