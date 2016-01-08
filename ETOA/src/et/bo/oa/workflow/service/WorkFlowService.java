package et.bo.oa.workflow.service;

import java.util.List;

public interface WorkFlowService {

	//ȡ�ô��������б�
	public List getTaskList(String role,String userName,int size);
	//ȡ������
	public WorkFlowInfo getTask(String id,String actor);
	//����
	public boolean operBegin(String id,String actor);
	//ʧ�ܣ��ع�
	public boolean operFalse(String id,String actor);
	//�ɹ����ύ
	public boolean operSuccess(String id,String actor);
	//������Ϣ
	public void sendMsg(String id, String next, String nextActor,String message);
	
	//ȡ����Ȩ�������Ĺ�������
	public List getWorkFlows(String role,String userName);
	//��������
	public void startFlow(String id,String nextActor,String message);
	
	//������Ϣ
	public void receiveMsgs();
	
//	�������̲���ִ�е�һ��
	public void startFlowAndFirst(String id,String actor,String nextActor,String tranName,String message);
}
