package org.jbpm.webapp.bean;

import org.jbpm.db.GraphSession;

import java.util.List;

/**
 * This will all change...
 */
public final class MonitorBean {

  private JbpmBean jbpmBean = null;
  private List averageNodeTime;

  private long processDefinitionId = 1L;
  private long minimumDurationMillis = 5000L;

  public JbpmBean getJbpmBean() {
    return jbpmBean;
  }

  public void setJbpmBean(final JbpmBean jbpmBean) {
    this.jbpmBean = jbpmBean;
  }

  public String showAverageNodeTime() {
    return "averagenodetime";
  }

  public List getAverageNodeTime() {
    final GraphSession graphSession = jbpmBean.getGraphSession();
    return graphSession.calculateAverageTimeByNode(processDefinitionId, minimumDurationMillis);
  }

  public long getProcessDefinitionId() {
    return processDefinitionId;
  }

  public void setProcessDefinitionId(final long processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public long getMinimumDurationMillis() {
    return minimumDurationMillis;
  }

  public void setMinimumDurationMillis(final long minimumDurationMillis) {
    this.minimumDurationMillis = minimumDurationMillis;
  }
}
