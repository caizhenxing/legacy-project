package org.jbpm.persistence.db;

import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.TestCase;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;

public class FlushDbTest extends TestCase {

  static JbpmConfiguration jbpmConfiguration = JbpmConfiguration.parseXmlString(
    "<jbpm-configuration>" +
    "  <jbpm-context> " +
    "    <service name='persistence'>" + 
    "      <factory> " +
    "        <bean class='org.jbpm.persistence.db.DbPersistenceServiceFactory'>" + 
    "          <field name='isTransactionEnabled'><boolean value='false'/></field> " +
    "        </bean> " +
    "      </factory> " +
    "    </service>" +
    "    <service name='tx' factory='org.jbpm.tx.TxServiceFactory' /> " +
    "    <service name='message' factory='org.jbpm.msg.db.DbMessageServiceFactory' />" + 
    "    <service name='scheduler' factory='org.jbpm.scheduler.db.DbSchedulerServiceFactory' />" + 
    "    <service name='logging' factory='org.jbpm.logging.db.DbLoggingServiceFactory' /> " +
    "    <service name='authentication' factory='org.jbpm.security.authentication.DefaultAuthenticationServiceFactory' />" +
    "  </jbpm-context> " + 
    "  <string name='resource.business.calendar' value='org/jbpm/calendar/jbpm.business.calendar.properties' />" +
    "  <string name='resource.default.modules' value='org/jbpm/graph/def/jbpm.default.modules.properties' />" +
    "  <string name='resource.converter' value='org/jbpm/db/hibernate/jbpm.converter.properties' />" +
    "  <string name='resource.action.types' value='org/jbpm/graph/action/action.types.xml' />" +
    "  <string name='resource.node.types' value='org/jbpm/graph/node/node.types.xml' />" +
    "  <string name='resource.parsers' value='org/jbpm/jpdl/par/jbpm.parsers.xml' />" +
    "  <string name='resource.varmapping' value='org/jbpm/context/exe/jbpm.varmapping.xml' />" +
    "</jbpm-configuration>"
  );
  
  // static DataSource dataSource = new Jdbc.MockDataSource();
  
  public void testProcessDeployment() throws Exception {
    Class.forName("org.hsqldb.jdbcDriver");
    Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:jbpm");
    jbpmConfiguration.createSchema();
    connection.commit();
    connection.close();
    
    connection = DriverManager.getConnection("jdbc:hsqldb:mem:jbpm");
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    jbpmContext.setConnection(connection);
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition name='hello' />"
    );
    jbpmContext.deployProcessDefinition(processDefinition);
    jbpmContext.close();
    connection.commit();
    connection.close();

    connection = DriverManager.getConnection("jdbc:hsqldb:mem:jbpm");
    jbpmContext = jbpmConfiguration.createJbpmContext();
    jbpmContext.setConnection(connection);
    jbpmContext.newProcessInstanceForUpdate("hello");
    jbpmContext.close();
    connection.commit();
    connection.close();
  }

}