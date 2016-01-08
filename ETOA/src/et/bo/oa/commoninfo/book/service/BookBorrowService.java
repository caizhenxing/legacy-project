/**
 * 	@(#)BookBorrowService.java   Sep 1, 2006 11:04:29 AM
 *	 。 
 *	 
 */
package et.bo.oa.commoninfo.book.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 
 * @author zhang
 * @version Sep 1, 2006
 * @see
 */
public interface BookBorrowService {
	
	/**
	 * 借阅图书录入
	 * @param dto 类型 IBaseDTO 图书借还信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addBorrowInfo(IBaseDTO dto);
	
	/**
	 * 添加返还登记信息
	 * @param dto 类型 IBaseDTO 图书借还信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addReturnInfo(IBaseDTO dto);
	
	/**
	 * 查询图书历史记录信息
	 * @param dto 类型 IBaseDTO 图书信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回邮件列表信息
	 */
	public List bookHistoryIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getBookHistorysSize();
	
	/**
	 * 查询图书历史记录信息
	 * @param dto 类型 IBaseDTO 图书信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回邮件列表信息
	 */
	public List bookBeyondIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getBookBeyondSize();
	
	/**
	 * 显示员工用户列表信息
	 * @param
	 * @version Sep 14, 2006
	 * @return
	 */
	public List<LabelValueBean> getEmployeeList();
}
