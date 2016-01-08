/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sys.staff.staffHortation.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe ְ������
 * @author  ������
 * @version 1.0, 2008-01-30//
 * @see
 */
public interface staffHortationService {
	/**
	 * @describe ���ְ������
	 * @param
	 * @return void
	 */ 
	public void addStaffHortation(IBaseDTO dto,String id);
	/**
	 * @describe �޸�ְ������
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffHortation(IBaseDTO dto);
	/**
	 * @describe ɾ��ְ������
	 * @param
	 * @return void
	 */ 
	public void delStaffHortation(String id);
	
	
	/**
	 * @describe ��ѯְ�������б�
	 * @param
	 * @return List
	 */ 
	public List StaffHortationQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getStaffHortationSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getStaffHortationInfo(String id);
	

}
