/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb.dwr;

import et.bo.agentDb.IncommingNote;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>得到座席面板当前座席当日来电信息</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public class IncommingNoteDwr {
	/**
	 * <p>得到座席面板当前座席当日来电信息</p>
	 * @param String ymd 时间yyyy-MM-dd
	 * @param String agent 人员id
	 * @return String 将座席来电信息以":"分隔供js解析
	 */
	public String getIncommingNote(String agent, String ymd)
	{
		IncommingNote in = (IncommingNote)SpringRunningContainer.getInstance().getBean("IncommingNoteService");
		return in.getAgentInfo(ymd,agent,null);
	}
}
