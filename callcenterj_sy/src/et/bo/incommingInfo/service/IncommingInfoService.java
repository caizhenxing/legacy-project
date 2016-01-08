/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.incommingInfo.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
/**
 * <p>������Ϣdao��</p>
 * 
 * @version 2008-06-13
 * @author wangwenquan
 */
public interface IncommingInfoService {
	/**
	 * @describe addIncommingInfo
	 * @param
	 * @return void
	 */ 
	public void addIncommingInfo(IBaseDTO dto);
	/**
	 * @describe updateIncommingInfoInfo
	 * @param
	 * @return void
	 */ 
	public boolean updateIncommingInfoInfo(IBaseDTO dto);
	/**
	 * @describe ɾ��������Ϣ
	 * @param
	 * @return void
	 */ 
	public void delIncommingInfo(String id);
	
	
	/**
	 * @describe ��ѯ������Ϣ�б�
	 * @param
	 * @return List
	 */ 
	public List incommingInfoList(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getIncommingInfoSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getIncommingInfoDetail(String id);
	
	public List getExpertList();
	
	/**
	 * ��ȡscreen��ר���������
	 */
	public List screenList();
}
