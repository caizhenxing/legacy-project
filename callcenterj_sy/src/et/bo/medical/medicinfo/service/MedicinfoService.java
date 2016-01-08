/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.medical.medicinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe ��ͨҽ����Ϣ
 * @author  ������
 * @version 1.0, 2008-04-7//
 * @see
 */
public interface MedicinfoService {
	/**
	 * @describe ����ר������ȡר�������б�
	 * @param expertType
	 * @return
	 */
	public List getExpertNameList(String expertType);
	/**
	 * @describe �����ͨҽ����Ϣ
	 * @param
	 * @return void
	 */ 
	public void addMedicinfo(IBaseDTO dto);
	/**
	 * @describe �޸���ͨҽ����Ϣ
	 * @param
	 * @return void
	 */ 
	public boolean updateMedicinfo(IBaseDTO dto);
	/**
	 * @describe ɾ����ͨҽ����Ϣ
	 * @param
	 * @return void
	 */ 
	public void delMedicinfo(String id);
	
	
	/**
	 * @describe ��ѯ��ͨҽ����Ϣ�б�
	 * @param
	 * @return List
	 */ 
	public List medicinfoQuery(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getMedicinfoSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getMedicinfo(String id);
	
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
