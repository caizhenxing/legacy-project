/*
 * @(#)AddressMainService.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.confManager;

import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>������Ϣά��dwr����</p>
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
	 * ��������״̬
	 * @param id ����
	 * @param value ���ֵ 1 2
	 */
	public void setAllowFlag(String id,String value)
	{
		service.setAllowFlag(id, value);
	}
	
	/**
	 * ������״̬
	 * @param id ����
	 * @param value ���ֵ 1 2 3
	 */
	public void setNewState(String id, String value)
	{
		service.setNewState(id, value);
	}
	/**
	 * ���õ�ǰ״̬
	 * @param id ����
	 * @param value ���ֵ 1 2 3
	 */
	public void setCurState(String id, String value)
	{
		service.setCurState(id, value);
	}
	/**
	 * �������� ��״̬��ػ��˳����� ���Ϊ�����
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
