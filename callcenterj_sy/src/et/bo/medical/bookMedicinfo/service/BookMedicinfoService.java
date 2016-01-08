/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.medical.bookMedicinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe ԤԼҽ�Ʒ�����Ϣ
 * @author  ������
 * @version 1.0, 2008-04-7//
 * @see
 */
public interface BookMedicinfoService {
	/**
	 * @describe ����ר������ȡר�������б�
	 * @param expertType
	 * @return
	 */
	public List getExpertNameList(String expertType);
	/**
	 * @describe ���ԤԼҽ�Ʒ�����Ϣ
	 * @param
	 * @return void
	 */ 
	public void addBookMedicinfo(IBaseDTO dto);
	/**
	 * @describe �޸�ԤԼҽ�Ʒ�����Ϣ
	 * @param
	 * @return void
	 */ 
	public boolean updateBookMedicinfo(IBaseDTO dto);
	/**
	 * @describe ɾ��ԤԼҽ�Ʒ�����Ϣ
	 * @param
	 * @return void
	 */ 
	public void delBookMedicinfo(String id);
	
	
	/**
	 * @describe ��ѯԤԼҽ�Ʒ�����Ϣ�б�
	 * @param
	 * @return List
	 */ 
	public List bookMedicinfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getBookMedicinfoSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getBookMedicinfo(String id);
	
	public List getExpertList();
	
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
	/**
	 * ����������б�
	 */
	public List userQuery();
		
}
