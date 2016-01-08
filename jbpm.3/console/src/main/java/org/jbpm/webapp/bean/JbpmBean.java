package org.jbpm.webapp.bean;

import java.io.Serializable;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.identity.hibernate.IdentitySession;

public class JbpmBean implements Serializable {

  private static final long serialVersionUID = 1L;

  JbpmContext jbpmContext = null;
  IdentitySession identitySession = null;
  
  public JbpmContext getJbpmContext() {
    if (jbpmContext==null) {
      jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
      String actorId = JsfHelper.getAuthenticatedUserName();
      jbpmContext.setActorId(actorId);
    }
    return jbpmContext;
  }
  
  public GraphSession getGraphSession() {
    return getJbpmContext().getGraphSession();
  }
  
  public TaskMgmtSession getTaskMgmtSession() {
    return getJbpmContext().getTaskMgmtSession();
  }
  
  public IdentitySession getIdentitySession() {
    if (identitySession==null) {
      identitySession = new IdentitySession(getJbpmContext().getSession());
    }
    return identitySession;
  }

  
  public void close() {
    if (jbpmContext!=null) {
      jbpmContext.close();
    }
  }

  public boolean hasJbpmContext() {
    return (jbpmContext!=null);
  }
}
