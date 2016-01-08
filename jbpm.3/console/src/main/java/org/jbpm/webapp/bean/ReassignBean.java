package org.jbpm.webapp.bean;

import org.jbpm.identity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.model.SelectItem;
import javax.faces.component.html.HtmlSelectOneMenu;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
public final class ReassignBean {
  private IdentityBean identityBean = null;
  private JbpmBean jbpmBean = null;
  private TaskBean taskBean = null;
  private HtmlSelectOneMenu actorMenu;

  public List getIdentityUserSelectionItems() {
    List selectionItems = new ArrayList();
    List userList = getIdentityBean().getIdentitySession().getUsers();
    Iterator userListIterator = userList.iterator();
    while (userListIterator.hasNext()) {
      User user = (User)userListIterator.next();
      final String userName = user.getName();
      selectionItems.add(new SelectItem(userName, userName));
    }
    return selectionItems;
  }

  // actions

  public String reassign() {
    final String actorId = actorMenu.getValue().toString();
    log.debug("Setting actor to " + actorId);
        taskBean.getTaskInstance().setActorId(actorId);
    return "participanthome";
  }

  public String cancel() {
    return "participanthome";
  }

  // accessors

  public IdentityBean getIdentityBean() {
    return identityBean;
  }

  public void setIdentityBean(final IdentityBean identityBean) {
    this.identityBean = identityBean;
  }

  public JbpmBean getJbpmBean() {
    return jbpmBean;
  }

  public void setJbpmBean(final JbpmBean jbpmBean) {
    this.jbpmBean = jbpmBean;
  }

  public TaskBean getTaskBean() {
    return taskBean;
  }

  public void setTaskBean(final TaskBean taskBean) {
    this.taskBean = taskBean;
  }

  public HtmlSelectOneMenu getActorMenu() {
    return actorMenu;
  }

  public void setActorMenu(final HtmlSelectOneMenu actorMenu) {
    this.actorMenu = actorMenu;
  }

  private static final Log log = LogFactory.getLog(TaskBean.class);
}
