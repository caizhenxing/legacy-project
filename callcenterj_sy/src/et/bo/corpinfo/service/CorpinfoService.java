/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.corpinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe ��ҵ����
 * @author  ������
 * @version 1.0, 2008-04-01//
 * @see
 */
public interface CorpinfoService {
	/**
	 * @describe �����ҵ����
	 * @param
	 * @return void
	 */ 
	public void addOperCorpinfo(IBaseDTO dto);
	/**
	 * ���ר���б�
	 * @param sql
	 * @return List
	 */
	public List exportQuery(String sql);
	/**
	 * �����ϯԱ�б�
	 * @param sql
	 * @return List
	 */
	
	public List userQuery(String sql);
	/**
	 * @describe �޸���ҵ����
	 * @param
	 * @return void
	 */ 
	public boolean updateOperCorpinfo(IBaseDTO dto);
	/**
	 * @describe ɾ����ҵ����
	 * @param
	 * @return void
	 */ 
	public void delOperCorpinfo(String id);
	
	
	/**
	 * @describe ��ѯ��ҵ�����б�
	 * @param
	 * @return List
	 */ 
	public List operCorpinfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getOperCorpinfoSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getOperCorpinfo(String id);
	
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
