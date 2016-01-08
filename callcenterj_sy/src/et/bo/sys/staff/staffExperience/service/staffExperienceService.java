/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sys.staff.staffExperience.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe ְ����������
 * @author  ������
 * @version 1.0, 2008-01-30//
 * @see
 */
public interface staffExperienceService {
	/**
	 * @describe ���ְ������
	 * @param
	 * @return void
	 */ 
	public void addStaffExperience(IBaseDTO dto,String id);
	/**
	 * @describe �޸�ְ������
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffExperience(IBaseDTO dto);
	/**
	 * @describe ɾ��ְ������
	 * @param
	 * @return void
	 */ 
	public void delStaffExperience(String id);
	
	
	/**
	 * @describe ��ѯְ�������б�
	 * @param
	 * @return List
	 */ 
	public List staffExperienceQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getStaffExperienceSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getStaffExperienceInfo(String id);
	
}
