/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.markanainfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe ����׷��
 * @author  ������
 * @version 1.0, 2008-04-09//
 * @see
 */
public interface MarkanainfoService {
	/**
	 * @describe ����г�����
	 * @param
	 * @return void
	 */ 
	public void addMarkanainfo(IBaseDTO dto);
	/**
	 * @describe �޸��г�����
	 * @param
	 * @return void
	 */ 
	public boolean updateMarkanainfo(IBaseDTO dto);
	/**
	 * @describe ɾ���г�����
	 * @param
	 * @return void
	 */ 
	public void delMarkanainfo(String id);
	
	
	/**
	 * @describe ��ѯ�г������б�
	 * @param
	 * @return List
	 */ 
	public List markanainfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getMarkanainfoSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getMarkanainfo(String id);

	/**
	 * ״̬ת��
	 * @param state
	 * @return
	 */
	public String changeState(String state);
	
	/**
	 * ɾ����Ϣ
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);

}
