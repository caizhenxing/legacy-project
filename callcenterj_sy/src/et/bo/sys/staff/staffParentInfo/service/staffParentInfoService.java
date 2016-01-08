/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sys.staff.staffParentInfo.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe ְ����������
 * @author  ������
 * @version 1.0, 2008-01-31//
 * @see
 */
public interface staffParentInfoService {
	/**
	 * @describe ���ְ������
	 * @param
	 * @return void
	 */ 
	public void addStaffParentInfo(IBaseDTO dto,String id);
	/**
	 * @describe �޸�ְ������
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffParentInfo(IBaseDTO dto);
	/**
	 * @describe ɾ��ְ������
	 * @param
	 * @return void
	 */ 
	public void delStaffParentInfo(String id);
	
	
	/**
	 * @describe ��ѯְ�������б�
	 * @param
	 * @return List
	 */ 
	public List staffParentInfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getStaffParentInfoSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getStaffParentInfoInfo(String id);
	

}
