/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sys.staff.staffLanguage.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe ְ�����Բ���
 * @author  ������
 * @version 1.0, 2008-01-30//
 * @see
 */
public interface staffLanguageService {
	/**
	 * @describe ���ְ������
	 * @param
	 * @return void
	 */ 
	public void addStaffLanguage(IBaseDTO dto,String id);
	/**
	 * @describe �޸�ְ������
	 * @param
	 * @return void
	 */ 
	public boolean updateStaffLanguage(IBaseDTO dto);
	/**
	 * @describe ɾ��ְ������
	 * @param
	 * @return void
	 */ 
	public void delStaffLanguage(String id);
	
	
	/**
	 * @describe ��ѯְ�������б�
	 * @param
	 * @return List
	 */ 
	public List staffLanguageQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getStaffLanguageSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto
	 */ 
	public IBaseDTO getStaffLanguageInfo(String id);
	

}
