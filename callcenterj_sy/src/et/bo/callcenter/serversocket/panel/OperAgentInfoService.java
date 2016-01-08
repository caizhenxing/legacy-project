/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-5-8
 */
package et.bo.callcenter.serversocket.panel;

import et.po.CcAgentInfo;

/**
 * ���ڴ��е���ϯ״̬��Ϣ����ɾ��
 * 
 * @author wangwenquan
 * 
 */
public interface OperAgentInfoService {
	/**
	 * �޸�CcAgentInfo 
	 * @param infoBean
	 */
	//void updateAgentInfoBean(CcAgentInfo infoBean);
	/**
	 * ��ѯCcAgentInfo
	 * @param String agent_id
	 * @param String yyyyMMdd yyyy-mm-dd
	 * @return CcAgentInfo
	 */
	CcAgentInfo queryCcAgentInfo(String agent_id, String yyyyMMdd);
	/**
	 * ��ѯCcAgentInfo
	 * @param String agent_id
	 * @param String yyyyMMdd yyyy-mm-dd
	 * @return String ��CcAgentInfoƴ���ַ������� agentId@value,insertDate@value,....;
	 */
	String queryCcAgentInfoToStr(String agent_id, String yyyyMMdd);
	/**
	 * ��AgentInfoMap ���Ӧ��AgentInfoBeanɾ��
	 * @param String agentNum
	 */
	//void deleteAgentInfoBean(String agentNum);
	/**
	 * ��CcAgentInfo�������ݿ�
	 * @param infoBean
	 */
	void insertOrUpdateAgentInfoBean(CcAgentInfo infoBean);

}
