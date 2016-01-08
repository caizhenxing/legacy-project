/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb.dwr;

import et.bo.agentDb.DayQuestionVolume;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>得到座席面板信息 日资询量统计</p>
 * 
 * @version 2009-02-11
 * @author wangwenquan
 */
public class DayQuestionVolumeDwr {

	/**
	 * <p>得到座席面板信息 日资询量统计</p>
	 * @param String ymd 时间yyyy-MM-dd
	 * @param String agent 人员id
	 * @return String ajax为座席面板呈现数据 显示座席日资讯量
	 */
	public String getDayQuestionVolume(String agent, String ymd)
	{
		DayQuestionVolume dqv = (DayQuestionVolume)SpringRunningContainer.getInstance().getBean("DayQuestionVolumeService");
		return dqv.getAgentInfo(ymd,agent,null);
	}
	/**
	 * 得到大屏幕各个质询量
	 * @param ymd
	 * @return 532291@1202@94@68@19@9@49 话务总量 当日总量 生产质询 市场质询 政策质询 等大屏幕相关信息
	 */
	public String getScreenQuestions(String ymd)
	{
		DayQuestionVolume dqv = (DayQuestionVolume)SpringRunningContainer.getInstance().getBean("DayQuestionVolumeService");
		return dqv.getScreenQuestions(ymd);
	}
}
