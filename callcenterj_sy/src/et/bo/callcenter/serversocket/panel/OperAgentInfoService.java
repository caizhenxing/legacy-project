/**
 * 沈阳卓越科技有限公司
 * 2008-5-8
 */
package et.bo.callcenter.serversocket.panel;

import et.po.CcAgentInfo;

/**
 * 对内存中的座席状态信息做增删改
 * 
 * @author wangwenquan
 * 
 */
public interface OperAgentInfoService {
	/**
	 * 修改CcAgentInfo 
	 * @param infoBean
	 */
	//void updateAgentInfoBean(CcAgentInfo infoBean);
	/**
	 * 查询CcAgentInfo
	 * @param String agent_id
	 * @param String yyyyMMdd yyyy-mm-dd
	 * @return CcAgentInfo
	 */
	CcAgentInfo queryCcAgentInfo(String agent_id, String yyyyMMdd);
	/**
	 * 查询CcAgentInfo
	 * @param String agent_id
	 * @param String yyyyMMdd yyyy-mm-dd
	 * @return String 将CcAgentInfo拼个字符串返回 agentId@value,insertDate@value,....;
	 */
	String queryCcAgentInfoToStr(String agent_id, String yyyyMMdd);
	/**
	 * 将AgentInfoMap 里对应的AgentInfoBean删除
	 * @param String agentNum
	 */
	//void deleteAgentInfoBean(String agentNum);
	/**
	 * 将CcAgentInfo插入数据库
	 * @param infoBean
	 */
	void insertOrUpdateAgentInfoBean(CcAgentInfo infoBean);

}
