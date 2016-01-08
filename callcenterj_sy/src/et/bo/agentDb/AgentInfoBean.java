/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.agentDb;

import java.util.Map;

/**
 * <p>得到座席面板信息</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public abstract class AgentInfoBean {
	/**
	 * 给ajax呈现数据的
	 * @param ymd
	 * @param agent
	 * @param otherParam
	 * @return
	 */
	public abstract String getAgentInfo(String ymd, String agent,Map otherParam);
}
