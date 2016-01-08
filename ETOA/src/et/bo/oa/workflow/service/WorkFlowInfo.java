package et.bo.oa.workflow.service;

import java.util.HashMap;
import java.util.List;

public interface WorkFlowInfo {

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getAction()
	 */
	public String getAction();

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setAction(java.lang.String)
	 */
	public void setAction(String action);

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getStepName()
	 */
	public String getStepName();

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setStepName(java.lang.String)
	 */
	public void setStepName(String stepName);

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getTaskid()
	 */
	public String getId();

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setTaskid(java.lang.String)
	 */
	public void setId(String id);

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getType()
	 */
	public String getType();

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setType(java.lang.String)
	 */
	public void setType(String type);

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getWfDefid()
	 */
	public String getWfDefid();

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setWfDefid(java.lang.String)
	 */
	public void setWfDefid(String wfDefid);

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getWfInsid()
	 */
	public String getWfInsid();

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setWfInsid(java.lang.String)
	 */
	public void setWfInsid(String wfInsid);

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getNextSteps()
	 */
	public HashMap<String, String> getNextSteps();

	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setNextSteps(java.util.HashMap)
	 */
	public void setNextSteps(HashMap<String, String> nextSteps);

	public List<String> getRoles();

	public void setRoles(List<String> roles);

	public List<String> getUsers();

	public void setUsers(List<String> users);
	
	public String getMessage();
	
	public void setMessage(String message);
}