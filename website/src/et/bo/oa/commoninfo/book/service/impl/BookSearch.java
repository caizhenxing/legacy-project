/**
 * 	@(#)BookSearch.java   Aug 31, 2006 7:34:23 PM
 *	 �� 
 *	 
 */
package et.bo.oa.commoninfo.book.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import et.po.BookBorrowInfo;
import et.po.BookInfo;
import et.po.EmployeeInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Aug 31, 2006
 * @see
 */
public class BookSearch extends MyQueryImpl {

	/**
	 * @describe �ռ���
	 * @param dto
	 *            ���� IBaseDTO
	 * @param pi
	 *            ���� PageInfo
	 * @return ����
	 * 
	 */

	public MyQuery searchBookList(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(BookInfo.class);
		if (dto.get("bookName") != null
				&& !dto.get("bookName").toString().equals("")) {
			dc.add(Expression.like("bookName", "%"
					+ dto.get("bookName").toString() + "%"));
		}
		if (dto.get("bookNum") != null
				&& !dto.get("bookNum").toString().equals("")) {
			dc.add(Expression.like("bookNum", "%"
					+ dto.get("bookNum").toString() + "%"));
		}
		if (dto.get("bookType").toString().equals("0")) {
			
		}else{
			dc.add(Expression.eq("borrowState", dto.get("bookType").toString()));
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * @describe ��ѯͼ��黹��Ϣ
	 * @param dto
	 *            ���� IBaseDTO
	 * @param pi
	 *            ���� PageInfo
	 * @return ����
	 * 
	 */

	public MyQuery searchBookBorrowInfo(IBaseDTO dto, BookInfo bookInfo) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(BookBorrowInfo.class);
		dc.add(Expression.eq("bookInfo", bookInfo));
		dc.add(Expression.isNull("returnTime"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * @describe ��ѯ�û���Ϣ
	 * @param dto
	 *            ���� IBaseDTO
	 * @param pi
	 *            ���� PageInfo
	 * @return ����
	 * 
	 */

	public MyQuery searchEmployeeInfo() {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
		dc.add(Restrictions.eq("delSign","1"));
		dc.add(Restrictions.eq("isLeave", "1"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * @describe ��ѯ�û���Ϣ
	 * @param dto
	 *            ���� IBaseDTO
	 * @param pi
	 *            ���� PageInfo
	 * @return ����
	 * 
	 */

	public MyQuery searchBorrowBookUser(String id) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	/**
	 * ��ѯ��������Ϣ
	 * @param
	 * @version Sep 26, 2006
	 * @return
	 */
	public MyQuery searchBorrowUser(BookInfo bookInfo) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(BookBorrowInfo.class);
		dc.add(Expression.eq("bookInfo", bookInfo));
		dc.add(Expression.isNull("returnTime"));
		mq.setDetachedCriteria(dc);
		return mq;
	}

}
