/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb.dwr;

import et.bo.agentDb.WorkSumTime;
import excellence.framework.base.container.SpringRunningContainer;
/**
 * <p>得到座席面板信息 日资询量统计</p>
 * 
 * @version 2009-02-11
 * @author wangwenquan
 */
public class WorkSumTimeDwr {
	/**
	 * <p>统计座席面板座席当日工作了多长时间</p>
	 * @param String agent 
	 * @param String ymd  为空则认为系时间
	 * @return String 以时分秒形式显示座席当日工作时间
	 */
	public String getWorkSumTime(String agent,String ymd) {
		WorkSumTime wst = (WorkSumTime)SpringRunningContainer.getInstance().getBean("WorkSumTimeService");
		return wst.getAgentInfo(ymd,agent,null);
	}
}
