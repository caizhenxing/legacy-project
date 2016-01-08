package et.bo.oa.workflow.service;

public interface OaWorkFlowService {

	public void nextStep(boolean submit,String id,String actor,String next,String nextActor,String message);
	public void createAndNext(String id,String thisActor,String nextActor,String nextName,String message);
	public void startStep(String id,String nextActor,String message);
}
