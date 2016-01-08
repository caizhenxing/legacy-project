package org.jbpm.ant;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;

public class ShutDownHsqldb extends Task {

  public void execute() throws BuildException {
    Connection connection = null;
    JbpmConfiguration jbpmConfiguration = AntHelper.getJbpmConfiguration(null);
    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    try {
      connection = jbpmContext.getConnection();
      Statement statement = connection.createStatement();
      log("shutting down database");
      statement.executeUpdate("SHUTDOWN");
      
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      jbpmContext.close();
    }
  }

}
