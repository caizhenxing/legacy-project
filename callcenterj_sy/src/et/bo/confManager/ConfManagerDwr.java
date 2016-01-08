/*
 * @(#)AddressMainService.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.confManager;

import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>会议信息维护dwr调用</p>
 * 
 * @version 2009-03-09
 * @author wangwenquan
 */
public class ConfManagerDwr {
	public ConfManagerDwr()
	{
		service = (ConfManagerService)SpringRunningContainer.getInstance().getBean("confManagerService");
	}
	ConfManagerService service;
	
	/**
	 * 设置审批状态
	 * @param id 主键
	 * @param value 设的值 1 2
	 */
	public void setAllowFlag(String id,String value)
	{
		service.setAllowFlag(id, value);
	}
	
	/**
	 * 设置新状态
	 * @param id 主键
	 * @param value 设的值 1 2 3
	 */
	public void setNewState(String id, String value)
	{
		service.setNewState(id, value);
	}
	/**
	 * 设置当前状态
	 * @param id 主键
	 * @param value 设的值 1 2 3
	 */
	public void setCurState(String id, String value)
	{
		service.setCurState(id, value);
	}
	/**
	 * 结束会议 新状态设关机退出会议 审核为以审核
	 */
	public void meetExit()
	{
		service.meetExit();
	}
	public ConfManagerService getService() {
		return service;
	}
	public void setService(ConfManagerService service) {
		this.service = service;
	}
	
	
	
	
	
}
