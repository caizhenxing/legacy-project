/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.agentDb.dwr;

import et.bo.agentDb.WorkSumTime;
import excellence.framework.base.container.SpringRunningContainer;
/**
 * <p>�õ���ϯ�����Ϣ ����ѯ��ͳ��</p>
 * 
 * @version 2009-02-11
 * @author wangwenquan
 */
public class WorkSumTimeDwr {
	/**
	 * <p>ͳ����ϯ�����ϯ���չ����˶೤ʱ��</p>
	 * @param String agent 
	 * @param String ymd  Ϊ������Ϊϵʱ��
	 * @return String ��ʱ������ʽ��ʾ��ϯ���չ���ʱ��
	 */
	public String getWorkSumTime(String agent,String ymd) {
		WorkSumTime wst = (WorkSumTime)SpringRunningContainer.getInstance().getBean("WorkSumTimeService");
		return wst.getAgentInfo(ymd,agent,null);
	}
}
