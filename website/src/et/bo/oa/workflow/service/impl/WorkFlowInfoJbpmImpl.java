package et.bo.oa.workflow.service.impl;




import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.oa.workflow.service.WorkFlowInfo;

public class WorkFlowInfoJbpmImpl implements  WorkFlowInfo {

	
	private String action=null;
	private HashMap<String,String> nextSteps=null;
	private String stepName=null;
	
	private String wfDefid=null;
	private String wfInsid=null;
	private String id=null;
	private String type=null;
	private List<String> roles=null;
	private List<String> users=null;
	
	private String message=null;
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getAction()
	 */
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAction() {
		return action;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setAction(java.lang.String)
	 */
	
	public void setAction(String action) {
		this.action = action;
	}
	
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getStepName()
	 */
	
	public String getStepName() {
		return stepName;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setStepName(java.lang.String)
	 */
	
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getTaskid()
	 */
	
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setTaskid(java.lang.String)
	 */
	
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getType()
	 */
	
	public String getType() {
		return type;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setType(java.lang.String)
	 */
	
	public void setType(String type) {
		this.type = type;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getWfDefid()
	 */

	public String getWfDefid() {
		return wfDefid;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setWfDefid(java.lang.String)
	 */
	
	public void setWfDefid(String wfDefid) {
		this.wfDefid = wfDefid;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getWfInsid()
	 */
	
	public String getWfInsid() {
		return wfInsid;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setWfInsid(java.lang.String)
	 */
	
	public void setWfInsid(String wfInsid) {
		this.wfInsid = wfInsid;
	}
	
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getNextSteps()
	 */
	
	public HashMap<String, String> getNextSteps() {
		return nextSteps;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setNextSteps(java.util.HashMap)
	 */
	
	public void setNextSteps(HashMap<String, String> nextSteps) {
		this.nextSteps = nextSteps;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getRoles()
	 */
	public List<String> getRoles() {
		return roles;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setRoles(java.util.List)
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#getUsers()
	 */
	public List<String> getUsers() {
		return users;
	}
	/* (non-Javadoc)
	 * @see et.oa.workflow.impl.WorkFlowInfo#setUsers(java.util.List)
	 */
	public void setUsers(List<String> users) {
		this.users = users;
	}
}
