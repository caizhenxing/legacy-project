/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.agentDb;

import java.util.Map;

/**
 * <p>�õ���ϯ�����Ϣ</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public abstract class AgentInfoBean {
	/**
	 * ��ajax�������ݵ�
	 * @param ymd
	 * @param agent
	 * @param otherParam
	 * @return
	 */
	public abstract String getAgentInfo(String ymd, String agent,Map otherParam);
}
