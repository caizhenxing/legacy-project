package org.jbpm.webapp.bean;

import java.io.Serializable;
import java.util.List;

import org.jbpm.identity.hibernate.IdentitySession;

public class IdentityBean implements Serializable {

  private static final long serialVersionUID = 1L;

  JbpmBean jbpmBean = null;
  IdentitySession identitySession = null;

  public String getUserName() {
    return JsfHelper.getAuthenticatedUserName();
  }

  public boolean isParticipant() {
    return isUserInRole("participant");
  }

  public boolean isManager() {
    return isUserInRole("manager");
  }

  public boolean isAdministrator() {
    return isUserInRole("administrator");
  }

  public boolean isUserInRole(String role) {
    return JsfHelper.getRequest().isUserInRole(role);
  }

  public boolean isLoggedIn() {
    return (JsfHelper.getRequest().getUserPrincipal()!=null);
  }

  public List getUserPoolIds() {
    String userName = JsfHelper.getAuthenticatedUserName();
    return getIdentitySession().getGroupNamesByUserAndGroupType(userName, "organisation");
  }
  
  public String logout() {
    JsfHelper.invalidateSession();
    return "participanthome";
  }

  // helpers //////////////////////////////////////////////////////////////////

  IdentitySession getIdentitySession() {
    if (identitySession==null) {
      identitySession = new IdentitySession(jbpmBean.getJbpmContext().getSession());
    }
    return identitySession;
  }

  // getters and setters //////////////////////////////////////////////////////

  public JbpmBean getJbpmBean() {
    return jbpmBean;
  }
  public void setJbpmBean(JbpmBean jbpmBean) {
    this.jbpmBean = jbpmBean;
  }
}
