/**
 * 	@(#)BookBorrowService.java   Sep 1, 2006 11:04:29 AM
 *	 �� 
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
	 * ����ͼ��¼��
	 * @param dto ���� IBaseDTO ͼ��軹��Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addBorrowInfo(IBaseDTO dto);
	
	/**
	 * ��ӷ����Ǽ���Ϣ
	 * @param dto ���� IBaseDTO ͼ��軹��Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addReturnInfo(IBaseDTO dto);
	
	/**
	 * ��ѯͼ����ʷ��¼��Ϣ
	 * @param dto ���� IBaseDTO ͼ����Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List bookHistoryIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getBookHistorysSize();
	
	/**
	 * ��ѯͼ����ʷ��¼��Ϣ
	 * @param dto ���� IBaseDTO ͼ����Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List bookBeyondIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getBookBeyondSize();
	
	/**
	 * ��ʾԱ���û��б���Ϣ
	 * @param
	 * @version Sep 14, 2006
	 * @return
	 */
	public List<LabelValueBean> getEmployeeList();
}
