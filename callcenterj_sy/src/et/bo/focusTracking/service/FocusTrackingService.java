/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.focusTracking.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe ����׷��
 * @author  
 * @version 1.0, 2009-03-12//
 * @see
 */
public interface FocusTrackingService {
	/**
	 * @describe ��ӽ���׷��
	 * @param
	 * @return void
	 */ 
	public void addFocusTracking(IBaseDTO dto);
	/**
	 * @describe �޸Ľ���׷��
	 * @param
	 * @return void
	 */ 
	public void updateFocusTracking(IBaseDTO dto);
	/**
	 * @describe ɾ������׷��
	 * @param
	 * @return void
	 */ 
	public void delFocusTracking(String id);
	
	
	/**
	 * @describe ��ѯ����׷���б�
	 * @param
	 * @return List
	 */ 
	public List focusTrackingQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getFocusTrackingSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getFocusTracking(String id);
	
	public List screenList() ;
}
