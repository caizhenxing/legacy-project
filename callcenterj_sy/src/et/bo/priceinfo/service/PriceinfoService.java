/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.priceinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe �г��۸�
 * @author  ������
 * @version 1.0, 2008-03-29//
 * @see
 */
public interface PriceinfoService {
	/**
	 * @describe ����г��۸�
	 * @param
	 * @return void
	 */ 
	public void addOperPriceinfoSad(IBaseDTO dto);
	/**
	 * @describe �޸��г��۸�
	 * @param
	 * @return void
	 */ 
	public boolean updateOperPriceinfo(IBaseDTO dto);
	/**
	 * @describe ɾ���г��۸�
	 * @param
	 * @return void
	 */ 
	public void delOperPriceinfo(String id);
	/**
	 * ����������б�
	 */
	public List userQuery();
	
	
	/**
	 * @describe ��ѯ�г��۸��б�
	 * @param
	 * @return List
	 */ 
	public List operPriceinfoQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe ��ѯ�г��۸��б�forExcel
	 * @param
	 * @return List
	 */ 
	public List<List<String>> operPriceinfoExcelQuery(IBaseDTO dto);
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	public int getOperPriceinfoSize();
	
	
	/**
	 * @describe ����Idȡ����Ϣ
	 * @param
	 * @return dto(user����)
	 */ 
	public IBaseDTO getOperPriceinfo(String id);
	
	/**
	 * ɾ����Ϣ
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state);
	/**
	 * ��ȡscreen�ļ۸񿴰�����
	 */
	public List screenList();
}
