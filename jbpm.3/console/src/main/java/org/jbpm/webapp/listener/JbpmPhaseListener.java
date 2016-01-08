package org.jbpm.webapp.listener;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmContext;
import org.jbpm.persistence.db.DbPersistenceService;
import org.jbpm.webapp.bean.JbpmBean;

public class JbpmPhaseListener implements PhaseListener {

  private static final long serialVersionUID = 1L;

  public PhaseId getPhaseId() {
    return PhaseId.ANY_PHASE;
  }

  public void beforePhase(PhaseEvent phaseEvent) {
  }

  public void afterPhase(PhaseEvent phaseEvent) {
    log.debug("after phase "+phaseEvent.getPhaseId());

    if (phaseEvent.getPhaseId()==PhaseId.INVOKE_APPLICATION) {
      JbpmBean jbpmBean = getJbpmBean(phaseEvent);
      if (jbpmBean.hasJbpmContext()) {
        log.debug("starting new transaction for the rendering phase");
        JbpmContext jbpmContext = jbpmBean.getJbpmContext();
        FacesContext.getCurrentInstance().getExternalContext().getRequest();
        DbPersistenceService dbPersistenceService = (DbPersistenceService) jbpmContext.getServices().getPersistenceService();
        dbPersistenceService.endTransaction();
        dbPersistenceService.beginTransaction();
      } else {
        log.debug("no jbpm context created at start of render resonse");
      }

    } else if (phaseEvent.getPhaseId()==PhaseId.RENDER_RESPONSE) {
      JbpmBean jbpmBean = getJbpmBean(phaseEvent);
      if (jbpmBean.hasJbpmContext()) {
        jbpmBean.getJbpmContext().close();
      }
    }
  }

  JbpmBean getJbpmBean(PhaseEvent phaseEvent) {
    FacesContext facesContext = phaseEvent.getFacesContext();
    JbpmBean jbpmBean = (JbpmBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "jbpmBean");
    return jbpmBean;
  }

  private static Log log = LogFactory.getLog(JbpmPhaseListener.class);
}
