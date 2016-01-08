/**
 * 	@(#)BookService.java   Aug 31, 2006 5:05:48 PM
 *	 。 
 *	 
 */
package et.bo.oa.commoninfo.book.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 图书管理信息，包括图书查询，借阅
 * @author zhang
 * @version Aug 31, 2006
 * @see
 */
public interface BookService {

	/**
	 * 录入图书信息
	 * @param dto 类型 IBaseDTO 图书信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addBookInfo(IBaseDTO dto);
	
	/**
	 * 修改图书信息
	 * @param dto 类型 IBaseDTO 图书信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean updateBookInfo(IBaseDTO dto);
	
	/**
	 * 删除图书信息
	 * @param dto 类型 IBaseDTO 图书信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean delBookInfo(IBaseDTO dto);
	
	/**
	 * 丢失图书信息，不是真正的删除，打上删除标志，图书丢失等
	 * @param dto 类型 IBaseDTO 图书信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean loseBookInfo(IBaseDTO dto);
	
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
	 * 添加续借信息
	 * @param dto 类型 IBaseDTO 图书借还信息
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addReBorrowInfo(IBaseDTO dto);
	
	/**
	 * 得到图书详细信息
	 * @param id 类型 String 图书编号
	 * @version Aug 31, 2006
	 * @return
	 */
	public IBaseDTO getBookInfo(String id);
	
	/**
	 * 查询图书信息
	 * @param dto 类型 IBaseDTO 图书信息
	 * @param pageInfo 类型 PageInfo 分页信息
	 * @return 类型 List 返回邮件列表信息
	 */
	public List bookIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getBookSize();
	
	/**
	 * 显示员工用户列表信息
	 * @param
	 * @version Sep 14, 2006
	 * @return
	 */
	public List<LabelValueBean> getEmployeeList();
	
	/**
	 * 得到借书人信息
	 */
	public List getBorrowInfo(String id);
	
}
