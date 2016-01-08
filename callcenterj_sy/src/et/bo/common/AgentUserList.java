/**
 * ����׿Խ�Ƽ����޹�˾
 */
package et.bo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangfeng
 *
 */
public class AgentUserList {
	//�����û��б����Ϣ���ڴ˴�����
	private static final Map<String,String> innerAgentMap = new HashMap<String,String>();
	
	//��һ����ϯԱ����Ϣ�ӵ��ڴ���
	public static void addInAgentList(String agentNum,String agentName){
		innerAgentMap.put(agentNum, agentName);
	}
	
	//��һ����ϯԱ����Ϣ���ڴ���ɾ��
	public static void removeAgentInList(String agentNum){
		innerAgentMap.remove(agentNum);
	}
	
	//�õ��������ߵ��û��б����û���½��ʱ�򣬼������
	public static Map<String,String> getAgentList(){
		return innerAgentMap;
	}

}
