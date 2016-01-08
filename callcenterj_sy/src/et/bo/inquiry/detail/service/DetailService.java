/* ��    ����et.bo.inquiry.detail.service
 * �� �� ����DetailService.java
 * ע��ʱ�䣺2008-8-28 14:03:22
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
 */
package et.bo.inquiry.detail.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * The Interface DetailService.
 * 
 * @author NieYuan
 */
public interface DetailService {
	
	/**
	 * Query.
	 * 
	 * @param dto the dto
	 * @param pi the pi
	 * 
	 * @return the list
	 */
	public List query(IBaseDTO dto, PageInfo pi);
	
	/**
	 * Gets the size.
	 * 
	 * @return the int
	 */
	public int getSize();
	
	/**
	 * User query.
	 * 
	 * @param sql the sql
	 * 
	 * @return the list
	 */
	public List userQuery(String sql);
}
