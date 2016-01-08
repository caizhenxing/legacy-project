/*
 * @(#)confManager.java	 2008-03-09
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.confManager.impl;

import et.bo.confManager.ConfManagerService;
import excellence.framework.base.dao.BaseDAO;
/**
 * <p>�������</p>
 * 
 * @version 2008-03-09
 * @author wangwenquan
 */
public class ConfManagerImpl implements ConfManagerService {
	private BaseDAO dao = null;
	/**
	 * ��������״̬
	 * @param id ����
	 * @param value ���ֵ 1 2
	 */
	public void setAllowFlag(String id,String value)
	{
		String sql = "update EasyConf_ChannelState set allowflag = "+value+" where id = "+id;
		dao.execute(sql);

	}
	/**
	 * �������� ��״̬��ػ��˳����� ���Ϊ�����
	 */
	public void meetExit()
	{
		String sql = "update EasyConf_ChannelState set newstate = 3,allowflag=1 where delete_mark='Y' ";
		dao.execute(sql);
	}
	/**
	 * ������״̬
	 * @param id ����
	 * @param value ���ֵ 1 2 3
	 */
	public void setNewState(String id, String value)
	{
		String sql = "update EasyConf_ChannelState set Newstate = "+value+" where id = "+id;
		dao.execute(sql);

	}
	
	/**
	 * ���õ�ǰ״̬
	 * @param id ����
	 * @param value ���ֵ 1 2 3
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
