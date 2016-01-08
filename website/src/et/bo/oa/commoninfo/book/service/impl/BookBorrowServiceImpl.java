/**
 * 	@(#)BookBorrowServiceImpl.java   Sep 1, 2006 1:01:35 PM
 *	 ¡£ 
 *	 
 */
package et.bo.oa.commoninfo.book.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.oa.commoninfo.book.service.BookBorrowService;
import et.po.BookBorrowInfo;
import et.po.EmployeeInfo;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Sep 1, 2006
 * @see
 */
public class BookBorrowServiceImpl implements BookBorrowService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private int BOOK_NUM = 0;

	private int BOOK_BEYOND_NUM = 0;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.commoninfo.book.service.BookBorrowService#addBorrowInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean addBorrowInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.commoninfo.book.service.BookBorrowService#addReturnInfo(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean addReturnInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.commoninfo.book.service.BookBorrowService#bookHistoryIndex(excellence.framework.base.dto.IBaseDTO,
	 *      excellence.common.page.PageInfo)
	 */
	public List bookHistoryIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		BookBorrowSearch bookBorrowSearch = new BookBorrowSearch();
		Object[] result = (Object[]) dao.findEntity(bookBorrowSearch
				.searchBookList(dto, pageInfo));
		int s = dao.findEntitySize(bookBorrowSearch.searchBookList(dto,
				pageInfo));
		BOOK_NUM = s;
		for (int i = 0, size = result.length; i < size; i++) {
			BookBorrowInfo bookBorrowInfo = (BookBorrowInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("bookName", bookBorrowInfo.getBookInfo().getBookName());
			String bookId = bookBorrowInfo.getBookUser();
			EmployeeInfo employeeInfo = (EmployeeInfo) dao.loadEntity(
					EmployeeInfo.class, bookId);
			dbd.set("bookUser", employeeInfo.getName());
			dbd.set("borrowTime", TimeUtil.getTheTimeStr(bookBorrowInfo
					.getBorrowTime(), "yyyy-MM-dd"));
			dbd.set("returnTime",
					bookBorrowInfo.getReturnTime() == null ? "Î´¹é»¹" : TimeUtil
							.getTheTimeStr(bookBorrowInfo.getReturnTime(),
									"yyyy-MM-dd"));
			l.add(dbd);
		}
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.commoninfo.book.service.BookBorrowService#getBookHistorysSize()
	 */
	public int getBookHistorysSize() {
		// TODO Auto-generated method stub
		return BOOK_NUM;
	}

	public List bookBeyondIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		BookBorrowSearch bookBorrowSearch = new BookBorrowSearch();
		Object[] result = (Object[]) dao.findEntity(bookBorrowSearch
				.searchBeyondList(dto, pageInfo));
		int s = dao.findEntitySize(bookBorrowSearch.searchBeyondList(dto,
				pageInfo));
		BOOK_NUM = s;
		for (int i = 0, size = result.length; i < size; i++) {
			BookBorrowInfo bookBorrowInfo = (BookBorrowInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("bookName", bookBorrowInfo.getBookInfo().getBookName());
			String bookId = bookBorrowInfo.getBookUser();
			EmployeeInfo employeeInfo = (EmployeeInfo) dao.loadEntity(
					EmployeeInfo.class, bookId);
			dbd.set("bookUser", employeeInfo.getName());
			dbd.set("borrowTime", TimeUtil.getTheTimeStr(bookBorrowInfo
					.getBorrowTime(), "yyyy-MM-dd"));
			dbd.set("returnTime",
					bookBorrowInfo.getReturnTime() == null ? "Î´¹é»¹" : TimeUtil
							.getTheTimeStr(bookBorrowInfo.getReturnTime(),
									"yyyy-MM-dd"));
			l.add(dbd);
		}
		return l;
	}

	public int getBookBeyondSize() {
		// TODO Auto-generated method stub
		return BOOK_BEYOND_NUM;
	}

	public List<LabelValueBean> getEmployeeList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		BookSearch bookSearch = new BookSearch();
		Object[] result = (Object[]) dao.findEntity(bookSearch
				.searchEmployeeInfo());
		for (int i = 0, size = result.length; i < size; i++) {
			EmployeeInfo employeeInfo = (EmployeeInfo) result[i];
			l.add(new LabelValueBean(employeeInfo.getName(), employeeInfo
					.getId()));
		}
		return l;
	}

}
