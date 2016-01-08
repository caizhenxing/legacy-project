package org.jbpm.job;

import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

public class ExecuteNodeJob extends Job {

  private static final long serialVersionUID = 1L;
  
  Node node;

  public ExecuteNodeJob() {
  }

  public ExecuteNodeJob(Token token) {
    super(token);
  }
  
  public boolean execute(JbpmContext jbpmContext) throws Exception {
    ExecutionContext executionContext = new ExecutionContext(token);
    node.execute(executionContext);
    return true;
  }
  
  public Node getNode() {
    return node;
  }
  public void setNode(Node node) {
    this.node = node;
  }
}
