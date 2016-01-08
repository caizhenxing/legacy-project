package org.jbpm.job.executor;

import org.jbpm.db.AbstractDbTestCase;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;

public class JobExecutorDbTest extends AbstractDbTestCase {

  private static final long serialVersionUID = 1L;
  
  
  
  public static class NodeAction implements ActionHandler {
    private static final long serialVersionUID = 1L;
    public void execute(ExecutionContext executionContext) throws Exception {
    }
  }
  
  public void deployProcess() {
    ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
      "<process-definition name='bulk messages'>" +
      "  <node name='n'>" +
      "    <action class='' />" +
      "  </node>" +
      "  <action name='a' class=''/>" +
      "</process-definition>"
    );
  }
  
  public void testBulkJobs() {
    
  }
}
