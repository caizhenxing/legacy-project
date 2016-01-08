/**
 * 	@(#)SearchService.java   2006-12-21 ����11:18:07
 *	 �� 
 *	 
 */
package et.bo.forum.search.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author Ҷ����
 * @version 2006-12-21
 * @see
 */
public interface SearchService {
    /**
     * ����������ѯ
     * @param
     * @version 2006-12-21
     * @return
     */
	public List postListQuery(IBaseDTO dto, PageInfo pageInfo);
	/**
	 * ȡ������
	 * @param
	 * @version 2006-12-21
	 * @return
	 */
	public int getSize();
}
