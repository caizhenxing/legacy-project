/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.agentDb.dwr;

import et.bo.agentDb.DayQuestionVolume;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>�õ���ϯ�����Ϣ ����ѯ��ͳ��</p>
 * 
 * @version 2009-02-11
 * @author wangwenquan
 */
public class DayQuestionVolumeDwr {

	/**
	 * <p>�õ���ϯ�����Ϣ ����ѯ��ͳ��</p>
	 * @param String ymd ʱ��yyyy-MM-dd
	 * @param String agent ��Աid
	 * @return String ajaxΪ��ϯ���������� ��ʾ��ϯ����Ѷ��
	 */
	public String getDayQuestionVolume(String agent, String ymd)
	{
		DayQuestionVolume dqv = (DayQuestionVolume)SpringRunningContainer.getInstance().getBean("DayQuestionVolumeService");
		return dqv.getAgentInfo(ymd,agent,null);
	}
	/**
	 * �õ�����Ļ������ѯ��
	 * @param ymd
	 * @return 532291@1202@94@68@19@9@49 �������� �������� ������ѯ �г���ѯ ������ѯ �ȴ���Ļ�����Ϣ
	 */
	public String getScreenQuestions(String ymd)
	{
		DayQuestionVolume dqv = (DayQuestionVolume)SpringRunningContainer.getInstance().getBean("DayQuestionVolumeService");
		return dqv.getScreenQuestions(ymd);
	}
}
