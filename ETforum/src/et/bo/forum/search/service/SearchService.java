/**
 * 	@(#)SearchService.java   2006-12-21 上午11:18:07
 *	 。 
 *	 
 */
package et.bo.forum.search.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author 叶浦亮
 * @version 2006-12-21
 * @see
 */
public interface SearchService {
    /**
     * 帖子搜索查询
     * @param
     * @version 2006-12-21
     * @return
     */
	public List postListQuery(IBaseDTO dto, PageInfo pageInfo);
	/**
	 * 取得数量
	 * @param
	 * @version 2006-12-21
	 * @return
	 */
	public int getSize();
}
