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
package org.jbpm.scheduler.exe;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.AbstractJbpmTestCase;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.job.Timer;
import org.jbpm.scheduler.SchedulerService;
import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;

public class SchedulerTest extends AbstractJbpmTestCase {

  private static final long serialVersionUID = 1L;

  public static class TestSchedulerServiceFactory implements ServiceFactory {
    private static final long serialVersionUID = 1L;
    public Service openService() {
      return new TestSchedulerService();
    }
    public void close() {
    }
  }

  public static class TestSchedulerService implements SchedulerService {
    List createdTimers = new ArrayList();
    List cancelledTimersByName = new ArrayList();
    List cancelledTimersByProcessInstance = new ArrayList();
    
    private static final long serialVersionUID = 1L;
    public void createTimer(Timer timer) {
      createdTimers.add(timer);
    }
    public void deleteTimersByName(String timerName, Token token) {
      cancelledTimersByName.add(new Object[]{timerName, token});
    }
    public void deleteTimersByProcessInstance(ProcessInstance processInstance) {
      cancelledTimersByProcessInstance.add(processInstance);
    }
    public void close() {
    }
  }

  static JbpmConfiguration jbpmConfiguration = JbpmConfiguration.parseXmlString(
    "<jbpm-configuration>" +
    "  <jbpm-context>" +
    "    <service name='scheduler' factory='org.jbpm.scheduler.exe.SchedulerTest$TestSchedulerServiceFactory' />" +
    "  </jbpm-context>" +
    "  <bean name='jbpm.task.instance.factory' class='org.jbpm.taskmgmt.impl.DefaultTaskInstanceFactoryImpl' singleton='true' />" +
    "</jbpm-configuration>"
  );
  
  public void testTimerCreation() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state>" +
      "    <transition to='catch crooks' />" +
      "  </start-state>" +
      "  <state name='catch crooks'>" +
      "    <timer name='reminder' " +
      "           duedate='3 business hours' " +
      "           repeat='10 business minutes'" +
      "           transition='time-out-transition' >" +
      "      <action class='the-remainder-action-class-name' />" +
      "    </timer>" +
      "  </state>" +
      "</process-definition>"
    );

    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      TestSchedulerService testSchedulerService = (TestSchedulerService) jbpmContext.getServices().getSchedulerService();
      
      ProcessInstance processInstance = new ProcessInstance(processDefinition);
      processInstance.signal();
      
      assertEquals(1, testSchedulerService.createdTimers.size());
      Timer scheduledTimer = (Timer) testSchedulerService.createdTimers.get(0);
      assertEquals("reminder", scheduledTimer.getName());
      assertEquals(processDefinition.getNode("catch crooks"), scheduledTimer.getGraphElement());
      System.out.println("due date: "+scheduledTimer.getDueDate());
      assertNotNull(scheduledTimer.getDueDate());
      assertEquals("10 business minutes", scheduledTimer.getRepeat());
      assertEquals("the-remainder-action-class-name", scheduledTimer.getAction().getActionDelegation().getClassName());
      assertSame(processInstance.getRootToken(), scheduledTimer.getToken());
      assertEquals("time-out-transition", scheduledTimer.getTransitionName());
    } finally {
      jbpmContext.close();
    }
  }

  public void testCreateTimerAction() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state>" +
      "    <transition to='catch crooks' />" +
      "  </start-state>" +
      "  <state name='catch crooks'>" +
      "    <event type='node-enter'>" +
      "      <create-timer name='reminder' " +
      "                    duedate='3 business hours' " +
      "                    repeat='10 business minutes'" +
      "                    transition='time-out-transition' >" +
      "        <action class='the-remainder-action-class-name' />" +
      "      </create-timer>" +
      "    </event>" +
      "    <transition to='end'/>" +
      "  </state>" +
      "  <end-state name='end'/>" +
      "</process-definition>"
    );
    
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      TestSchedulerService testSchedulerService = (TestSchedulerService) jbpmContext.getServices().getSchedulerService();
      
      ProcessInstance processInstance = new ProcessInstance(processDefinition);
      processInstance.signal();
      
      assertEquals(1, testSchedulerService.createdTimers.size());
      Timer scheduledTimer = (Timer) testSchedulerService.createdTimers.get(0);
      assertEquals("reminder", scheduledTimer.getName());
      assertEquals(processDefinition.getNode("catch crooks"), scheduledTimer.getGraphElement());
      System.out.println("due date: "+scheduledTimer.getDueDate());
      assertNotNull(scheduledTimer.getDueDate());
      assertEquals("10 business minutes", scheduledTimer.getRepeat());
      assertEquals("the-remainder-action-class-name", scheduledTimer.getAction().getActionDelegation().getClassName());
      assertSame(processInstance.getRootToken(), scheduledTimer.getToken());
      assertEquals("time-out-transition", scheduledTimer.getTransitionName());
  
      // while we are at it, i might as well check if the cancel timer is not executed ;)
      processInstance.signal();
      assertEquals(0, testSchedulerService.cancelledTimersByName.size());
      
    } finally {
      jbpmContext.close();
    }
  }

  public void testTimerCancelAction() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state>" +
      "    <transition to='catch crooks' />" +
      "  </start-state>" +
      "  <state name='catch crooks'>" +
      "    <timer name='reminder' " +
      "           duedate='3 business hours' " +
      "           repeat='10 business minutes'" +
      "           transition='time-out-transition' >" +
      "      <action class='the-remainder-action-class-name' />" +
      "    </timer>" +
      "    <transition to='end'/>" +
      "  </state>" +
      "  <end-state name='end'/>" +
      "</process-definition>"
    );
    
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      TestSchedulerService testSchedulerService = (TestSchedulerService) jbpmContext.getServices().getSchedulerService();
      
      ProcessInstance processInstance = new ProcessInstance(processDefinition);
      processInstance.signal();
      processInstance.signal();
      
      List cancelledTimerNames = testSchedulerService.cancelledTimersByName;
      assertEquals(1, cancelledTimerNames.size());
      Object[] cancelledTimer = (Object[]) cancelledTimerNames.get(0);
      assertEquals("reminder", cancelledTimer[0]);
      assertSame(processInstance.getRootToken(), cancelledTimer[1]);
      
    } finally {
      jbpmContext.close();
    }
  }

  public static class TimerCreateAction implements ActionHandler {
    private static final long serialVersionUID = 1L;
    static Timer timer = null;
    public void execute(ExecutionContext executionContext) throws Exception {
      timer = executionContext.getTimer();
    }
  }
  public void testTimerEvent() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state>" +
      "    <transition to='catch crooks' />" +
      "  </start-state>" +
      "  <state name='catch crooks'>" +
      "    <event type='timer-create'>" +
      "      <action class='org.jbpm.scheduler.exe.SchedulerTest$TimerCreateAction' />" +
      "    </event>" +
      "    <timer name='reminder' " +
      "           duedate='2 seconds' >" +
      "      <action class='the-timer-create-event-class-name' />" +
      "    </timer>" +
      "    <transition to='end'/>" +
      "  </state>" +
      "  <end-state name='end'/>" +
      "</process-definition>"
    );
    
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      ProcessInstance processInstance = new ProcessInstance(processDefinition);
      assertNull(TimerCreateAction.timer);
      processInstance.signal();
      assertNotNull(TimerCreateAction.timer);
      assertEquals("the-timer-create-event-class-name", TimerCreateAction.timer.getAction().getActionDelegation().getClassName());

    } finally {
      jbpmContext.close();
    }
  }

  public void testUnavailableSchedulerService() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state>" +
      "    <transition to='catch crooks' />" +
      "  </start-state>" +
      "  <state name='catch crooks'>" +
      "    <timer name='reminder' " +
      "           duedate='2 seconds' >" +
      "      <action class='the-timer-create-event-class-name' />" +
      "    </timer>" +
      "  </state>" +
      "</process-definition>"
    );
    
    try {
      new ProcessInstance(processDefinition).signal();
      fail("expected exception");
    } catch (JbpmException e) {
      // OK
    }
  }

  public void testTaskTimerExecution() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state>" +
      "    <transition to='timed task' />" +
      "  </start-state>" +
      "  <task-node name='timed task'>" +
      "    <task name='find the hole in the market'>" +
      "      <timer duedate='23 business seconds'>" +
      "        <action class='geftem-eu-shuppe-oender-ze-konte'/>" +
      "      </timer>" +
      "    </task>" +
      "  </task-node>" +
      "</process-definition>"
    );
    
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      TestSchedulerService testSchedulerService = (TestSchedulerService) jbpmContext.getServices().getSchedulerService();
      ProcessInstance processInstance = new ProcessInstance(processDefinition);
      processInstance.signal();
      
      List scheduledTimers = testSchedulerService.createdTimers;
      assertEquals(1, scheduledTimers.size());

    } finally {
      jbpmContext.close();
    }
  }
  
  
  static boolean isCustomized = false;
  public static class TimerCustomizingAction implements ActionHandler {
    private static final long serialVersionUID = 1L;
    public void execute(ExecutionContext executionContext) throws Exception {
      assertNotNull(executionContext.getTimer());
      assertEquals("reminder", executionContext.getTimer().getName());
      isCustomized = true;
    }
  }

  public void testTaskTimerActionExecution() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition>" +
      "  <start-state>" +
      "    <transition to='timed task' />" +
      "  </start-state>" +
      "  <task-node name='timed task'>" +
      "    <task name='find the hole in the market'>" +
      "      <event type='timer-create'>" +
      "        <action class='org.jbpm.scheduler.exe.SchedulerTest$TimerCustomizingAction' />" +
      "      </event>" +
      "      <timer name='reminder' duedate='23 business seconds'>" +
      "        <action class='geftem-eu-shuppe-oender-ze-konte'/>" +
      "      </timer>" +
      "    </task>" +
      "  </task-node>" +
      "</process-definition>"
    );
    
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      ProcessInstance processInstance = new ProcessInstance(processDefinition);
      processInstance.signal();
      assertTrue(isCustomized);

    } finally {
      jbpmContext.close();
    }
  }
}
