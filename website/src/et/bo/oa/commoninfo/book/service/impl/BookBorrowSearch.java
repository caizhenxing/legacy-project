/**
 * 	@(#)BookBorrowSearch.java   Sep 1, 2006 1:12:53 PM
 *	 。 
 *	 
 */
package et.bo.oa.commoninfo.book.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.BookBorrowInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Sep 1, 2006
 * @see
 */
public class BookBorrowSearch extends MyQueryImpl{
	
	/**
	 * @describe 图书借阅情况查询
	 * @param dto
	 *            类型 IBaseDTO
	 * @param pi
	 *            类型 PageInfo
	 * @return 类型
	 * 
	 */

	public MyQuery searchBookList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(BookBorrowInfo.class);
		if (dto.get("bookUser") != null
				&& !dto.get("bookUser").toString().equals("")) {
			if (dto.get("bookUser").equals("请选择")) {
				
			}else{
				dc.add(Expression.like("bookUser", "%"+dto.get("bookUser").toString()+"%"));
			}
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	/**
	 * @describe 图书借阅超期情况查询
	 * @param dto
	 *            类型 IBaseDTO
	 * @param pi
	 *            类型 PageInfo
	 * @return 类型
	 * 
	 */

	public MyQuery searchBeyondList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(BookBorrowInfo.class);
		dc.add(Expression.isNull("returnTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
}
