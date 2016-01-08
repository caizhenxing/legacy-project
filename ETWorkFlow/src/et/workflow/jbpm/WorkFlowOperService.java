package et.workflow.jbpm;

import et.workflow.jbpm.impl.MsgBeanJbpm;

public interface WorkFlowOperService {

	public String createNewFlow(String flowId,boolean send);
	public String singleFlow(String flowId,String transitionName,boolean send);
//	public String execAction(String actionId,String transitionName);
	public String execTask(MsgBeanJbpm mbj,boolean send);
//	public String execNode(String nodeId,String transitionName);
	
	public void setMbj(MsgBeanJbpm mbj);
	
	public void deployDefFlow(String[] paths);
	
	public String createNewAndTask(MsgBeanJbpm mbj);
}
