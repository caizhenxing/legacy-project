/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.agentDb.dwr;

import et.bo.agentDb.IncommingNote;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>�õ���ϯ��嵱ǰ��ϯ����������Ϣ</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class IncommingNoteDwr {
	/**
	 * <p>�õ���ϯ��嵱ǰ��ϯ����������Ϣ</p>
	 * @param String ymd ʱ��yyyy-MM-dd
	 * @param String agent ��Աid
	 * @return String ����ϯ������Ϣ��":"�ָ���js����
	 */
	public String getIncommingNote(String agent, String ymd)
	{
		IncommingNote in = (IncommingNote)SpringRunningContainer.getInstance().getBean("IncommingNoteService");
		return in.getAgentInfo(ymd,agent,null);
	}
}
