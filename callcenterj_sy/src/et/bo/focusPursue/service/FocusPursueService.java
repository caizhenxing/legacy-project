/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.focusPursue.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe ����׷��
 * @author  ������
 * @version 1.0, 2008-04-08//
 * @see
 */
public interface FocusPursueService {
	/**
	 * @describe ��ӽ���׷��
	 * @param
	 * @return void
	 */ 
	public void addFocusPursue(IBaseDTO dto);
	/**
	 * @describe �޸Ľ���׷��
	 * @param
	 * @return void
	 */ 
	public boolean updateFocusPursue(IBaseDTO dto);
	/**
	 * @describe ɾ������׷��
	 * @param
	 * @return void
	 */ 
	public void delFocusPursue(String id);
	
	
	/**
	 * @describe ��ѯ����׷���б�
	 * @param
	 * @return List
	 */ 
	public List focusPursueQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getFocusPursueSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getFocusPursue(String id);
	
	/**
	 * ɾ����Ϣ
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	
}
