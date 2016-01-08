/*
 * @(#)confManager.java	 2008-03-09
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.confManager;
/**
 * <p>会议管理</p>
 * 
 * @version 2008-03-09
 * @author wangwenquan
 */
public interface ConfManagerService {
	/**
	 * 设置审批状态
	 * @param id 主键
	 * @param value 设的值 1 2
	 */
	public void setAllowFlag(String id,String value);
	/**
	 * 设置新状态
	 * @param id 主键
	 * @param value 设的值 1 2 3
	 */
	public void setNewState(String id, String value);
	/**
	 * 设置当前状态
	 * @param id 主键
	 * @param value 设的值 1 2 3
	 */
	public void setCurState(String id, String value);
	/**
	 * 结束会议 新状态设关机退出会议 审核为以审核
	 */
	public void meetExit();
}
