package org.jbpm.scheduler.exe;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.AbstractDbTestCase;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.persistence.db.DbPersistenceService;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;
import org.jbpm.svc.Service;
import org.jbpm.svc.Services;

public class UnsafeSessionUsageTest extends AbstractDbTestCase {

  private static final long serialVersionUID = 1L;

  public void testTimerExecution() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state>" +
      "    <transition to='a' />" +
      "  </start-state>" +
      "  <task-node name='a'>" +
      "    <task name='clean ceiling'>" +
      "      <timer name='ceiling-timer' duedate='0 seconds'>" +
      "        <action class='org.jbpm.taskmgmt.exe.TaskTimerExecutionDbTest$PlusPlus' />" +
      "      </timer>" +
      "    </task>" +
      "  </task-node>" +
      "</process-definition>"
    );

    processDefinition = saveAndReload(processDefinition);
    
    ProcessInstance processInstance = new ProcessInstance(processDefinition);
    processInstance.signal();
    
    jbpmContext.save(processInstance);
    
    processJobs(1500);
  }

  // below is all the code to tweak the default jbpm test configuration to wrap all the sessions that will be created
  // whenever a wrapped session is accessed, a check will be done to see if it the access is done in the same thread  

  static {
    JbpmConfiguration jbpmConfiguration = AbstractDbTestCase.jbpmConfiguration;
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      jbpmContext.getServices().getServiceFactories().put(Services.SERVICENAME_PERSISTENCE, new WrapperPersistenceServiceFactory());
    } finally {
      jbpmContext.close();
    }
  }

  public static class WrapperPersistenceServiceFactory extends DbPersistenceServiceFactory {
    private static final long serialVersionUID = 1L;
    public Service openService() {
      DbPersistenceService dbPersistenceService = (DbPersistenceService) super.openService();
      Session s = dbPersistenceService.getSession();
      Thread t = Thread.currentThread();
      log.debug("wrapping session for thread "+t);
      dbPersistenceService.setSessionWithoutDisablingTx(wrapSession(s, t));
      return dbPersistenceService;
    }
  }

  public static Session wrapSession(Session s, Thread t) {
    InvocationHandler invocationHandler = new ThreadVerificationHandler(s, t);
    Class[] interfaces = new Class[]{Session.class};
    ClassLoader classLoader = UnsafeSessionUsageTest.class.getClassLoader();
    return (Session) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
  }

  public static class UnsafeSessionUsageException extends RuntimeException {
    private static final long serialVersionUID = 1L;
  }
  
  public static class ThreadVerificationHandler implements InvocationHandler {
    Session s;
    Thread t;
    public ThreadVerificationHandler(Session s, Thread t) {
      this.s = s;
      this.t = t;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (t!=Thread.currentThread()) {
        throw new UnsafeSessionUsageException();
      }
      String argString = "";
      if ( (args!=null)
           && (args.length>0)
         ) {
        for(int i=0; i<args.length; i++) {
          if (i>0) argString += ", " ;
          argString += args[i];
        }
      }
      log.debug("SESSION OPERATION: session."+method.getName()+"("+argString+")");
      Object result;
      try {
        result = method.invoke(s, args);
        log.debug("SESSION OPERATION RESULT: "+result);
      } catch (Throwable t) {
        log.error("HIBERNATE EXCEPTION", t);
        throw t;
      }
      return result;
    }
  }

  private static Log log = LogFactory.getLog(UnsafeSessionUsageTest.class);
}
