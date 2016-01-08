package et.bo.oa.workflow.service.impl;

import et.bo.oa.workflow.service.OaWorkFlowService;
import et.bo.oa.workflow.service.WorkFlowService;



public class OaWorkFlowServiceJbpmImpl implements OaWorkFlowService {

	private WorkFlowService wfs=null;
	public WorkFlowService getWfs() {
		return wfs;
	}
	public void setWfs(WorkFlowService wfs) {
		this.wfs = wfs;
	}
	public void nextStep(boolean submit, String id, String actor, String next,
			String nextActor,String message) {
		// TODO Auto-generated method stub
		if(submit)
		{
		wfs.operSuccess(id,actor);
		wfs.sendMsg(id,next,nextActor,message);
		}
		else
			wfs.operFalse(id,actor);
	}
	public void createAndNext(String id, String thisActor, String nextActor, String nextName,String message) {
		// TODO Auto-generated method stub
		wfs.startFlowAndFirst(id,thisActor,nextActor,nextName,message);
	}
	public void startStep(String id, String nextActor,String message) {
		// TODO Auto-generated method stub
		wfs.startFlow(id,nextActor,message);
	}

}
