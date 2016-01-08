package et.bo.oa.workflow.service;

import java.util.List;

public interface WorkFlowService {

	//取得代办事宜列表
	public List getTaskList(String role,String userName,int size);
	//取得任务
	public WorkFlowInfo getTask(String id,String actor);
	//开启
	public boolean operBegin(String id,String actor);
	//失败，回滚
	public boolean operFalse(String id,String actor);
	//成功，提交
	public boolean operSuccess(String id,String actor);
	//发送消息
	public void sendMsg(String id, String next, String nextActor,String message);
	
	//取得有权限启动的工作流程
	public List getWorkFlows(String role,String userName);
	//启动流程
	public void startFlow(String id,String nextActor,String message);
	
	//接收消息
	public void receiveMsgs();
	
//	启动流程并且执行第一步
	public void startFlowAndFirst(String id,String actor,String nextActor,String tranName,String message);
}
