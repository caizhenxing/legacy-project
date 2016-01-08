/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sad.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe �۸���
 * @author  ������
 * @version 1.0, 2008-03-27//
 * @see
 */
public interface SadService {
	/**
	 * @describe ����г�����
	 * @param
	 * @return void
	 */ 
	public void addSad(IBaseDTO dto);
	/**
	 * @describe �޸��г�����
	 * @param
	 * @return void
	 */ 
	public boolean updateSad(IBaseDTO dto);
	/**
	 * @describe ɾ���г�����
	 * @param
	 * @return void
	 */ 
	public void delSad(String id);
	
	
	/**
	 * @describe ��ѯ�г������б�
	 * @param
	 * @return List
	 */ 
	public List sadQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe ��ѯ�г������б�
	 * @param
	 * @return List
	 */ 
	public List sadInfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getSadSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getSadInfo(String id);
	
    /**
     * @describe �޸���Ƭ
     * @param
     * @return
     */
    public void updatePhoto(String id,String path);
	
    /**
	 * ɾ����Ϣ
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	/**
	 * ����������б�
	 */
	public List userQuery();
	/**
	 * ����Ļ�����б�
	 * @return
	 */
	public List screenList();
}
