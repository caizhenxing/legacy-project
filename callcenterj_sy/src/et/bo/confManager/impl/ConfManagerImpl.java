/*
 * @(#)confManager.java	 2008-03-09
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.confManager.impl;

import et.bo.confManager.ConfManagerService;
import excellence.framework.base.dao.BaseDAO;
/**
 * <p>会议管理</p>
 * 
 * @version 2008-03-09
 * @author wangwenquan
 */
public class ConfManagerImpl implements ConfManagerService {
	private BaseDAO dao = null;
	/**
	 * 设置审批状态
	 * @param id 主键
	 * @param value 设的值 1 2
	 */
	public void setAllowFlag(String id,String value)
	{
		String sql = "update EasyConf_ChannelState set allowflag = "+value+" where id = "+id;
		dao.execute(sql);

	}
	/**
	 * 结束会议 新状态设关机退出会议 审核为以审核
	 */
	public void meetExit()
	{
		String sql = "update EasyConf_ChannelState set newstate = 3,allowflag=1 where delete_mark='Y' ";
		dao.execute(sql);
	}
	/**
	 * 设置新状态
	 * @param id 主键
	 * @param value 设的值 1 2 3
	 */
	public void setNewState(String id, String value)
	{
		String sql = "update EasyConf_ChannelState set Newstate = "+value+" where id = "+id;
		dao.execute(sql);

	}
	
	/**
	 * 设置当前状态
	 * @param id 主键
	 * @param value 设的值 1 2 3
	 */
	public void setCurState(String id, String value)
	{
		String sql = "update EasyConf_ChannelState set currentstate = "+value+" where id = "+id;
		dao.execute(sql);

	}
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	
}
