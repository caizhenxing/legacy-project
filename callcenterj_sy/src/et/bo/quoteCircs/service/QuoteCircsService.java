/* ��    ����et.bo.quoteCircs.service
 * �� �� ����QuoteCircsService.java
 * ע��ʱ�䣺2008-7-9 13:28:23
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
 */

package et.bo.quoteCircs.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * The Interface QuoteCircsService.
 * 
 * @author NieYuan
 */
public interface QuoteCircsService {
	
	/**
	 * ȡ�ñ�������б�.
	 * 
	 * @param dto the dto
	 * @param pi the pi
	 * 
	 * @return the quote circs list
	 */
	public List quoteCircsQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * ��ѯ�����б��������.
	 * 
	 * @return �õ�list������
	 */
	public int getQuoteCircsSize(); 
	
	/**
	 * �������Ա�б�
	 * @param sql
	 * @return List
	 */
	public List userQuery(String sql);

}
