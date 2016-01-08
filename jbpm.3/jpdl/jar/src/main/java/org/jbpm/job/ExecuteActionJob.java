package org.jbpm.job;

import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

public class ExecuteActionJob extends Job {

  private static final long serialVersionUID = 1L;

  Action action;
  
  public ExecuteActionJob() {
  }
  
  public ExecuteActionJob(Token token) {
    super(token);
  }
  
  public boolean execute(JbpmContext jbpmContext) throws Exception {
    ExecutionContext executionContext = new ExecutionContext(token);
    action.execute(executionContext);
    return true;
  }

  public Action getAction() {
    return action;
  }
  public void setAction(Action action) {
    this.action = action;
  }
}
