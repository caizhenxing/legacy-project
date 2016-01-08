/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.linkmanPriceinfo.service;

import java.util.List;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;


/**
 * @describe �г��۸�
 * @author  ������
 * @version 1.0, 2008-03-29//
 * @see
 */
public interface LinkmanPriceinfoService {
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
	 * @describe ��ѯ�г��۸��б�
	 * @param
	 * @return List
	 */ 
	public List operPriceinfoQuery(IBaseDTO dto, PageInfo pi);
	
	
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
	
//	 ��Ա�б�Ϊ��ѯҳ���ṩ����
	public List<LabelValueBean> getUserList();
	
}
