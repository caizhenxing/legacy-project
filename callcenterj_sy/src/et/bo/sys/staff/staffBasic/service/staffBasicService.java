/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sys.staff.staffBasic.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe �û���������
 * @author  ������
 * @version 1.0, 2007-04-05//
 * @see
 */
public interface staffBasicService {
	/**
	 * @describe �������ַ
	 * @param
	 * @return void
	 */ 
	public void addStaffBasic(IBaseDTO dto);
	/**
	 * @describe �޸���ַ
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffBasic(IBaseDTO dto);
	/**
	 * @describe ɾ����ַ
	 * @param
	 * @return void
	 */ 
	public void delStaffBasic(String id);
	
	
	/**
	 * @describe ��ѯ��ַ�б�
	 * @param
	 * @return List
	 */ 
	public List staffBasicQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getStaffBasicSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getStaffBasicInfo(String id);
	
	/**
	 * @describe ���ع�˾��Ϣ
	 * @param
	 * @return list
	 */ 
	public List getWorkUnitInfo();
	
	
	/**
	 * @describe ���ز�����Ϣ
	 * @param
	 * @return list
	 */ 
	public List getdepartmentList();
	
	
	/**
	 * @describe ����ְ����Ϣ
	 * @param
	 * @return list
	 */ 
	public List getDutyList();
}
