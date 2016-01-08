/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sys.staff.workUnitInfo.service;

import java.util.HashMap;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @describe ��˾��Ϣ����
 * @author  ������
 * @version 1.0, 2008-01-29//
 * @see
 */
public interface workUnitInfoService {
	/**
	 * @describe ��ӹ�˾��Ϣ
	 * @param
	 * @return void
	 */ 
	public void addWorkUnitInfo(IBaseDTO dto);
	/**
	 * @describe �޸Ĺ�˾��Ϣ
	 * @param
	 * @return void
	 */ 
	public boolean updateWorkUnitInfo(IBaseDTO dto);
	/**
	 * @describe ɾ����˾��Ϣ
	 * @param
	 * @return void
	 */ 
	public void delWorkUnitInfo(String id);
	
	
	/**
	 * @describe ��ѯ��˾��Ϣ�б�
	 * @param
	 * @return List
	 */ 
	public List workUnitInfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getWorkUnitInfoSize();
	
	
	/**
	 * @describe ����Idȡ�ù�˾��Ϣ
	 * @param
	 * @return dto(����)
	 */ 
	public IBaseDTO getWorkUnitInfoInfo(String id);
	
	
}
