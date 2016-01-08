/* 包    名：et.bo.quoteCircs.service
 * 文 件 名：QuoteCircsService.java
 * 注释时间：2008-7-9 13:28:23
 * 版权所有：沈阳市卓越科技有限公司。
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
	 * 取得报价情况列表.
	 * 
	 * @param dto the dto
	 * @param pi the pi
	 * 
	 * @return the quote circs list
	 */
	public List quoteCircsQuery(IBaseDTO dto, PageInfo pi);
	
	/**
	 * 查询数据列表的条数。.
	 * 
	 * @return 得到list的条数
	 */
	public int getQuoteCircsSize(); 
	
	/**
	 * 获得联络员列表
	 * @param sql
	 * @return List
	 */
	public List userQuery(String sql);

}
