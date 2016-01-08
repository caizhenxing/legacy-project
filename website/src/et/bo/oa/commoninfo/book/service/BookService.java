/**
 * 	@(#)BookService.java   Aug 31, 2006 5:05:48 PM
 *	 �� 
 *	 
 */
package et.bo.oa.commoninfo.book.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * ͼ�������Ϣ������ͼ���ѯ������
 * @author zhang
 * @version Aug 31, 2006
 * @see
 */
public interface BookService {

	/**
	 * ¼��ͼ����Ϣ
	 * @param dto ���� IBaseDTO ͼ����Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addBookInfo(IBaseDTO dto);
	
	/**
	 * �޸�ͼ����Ϣ
	 * @param dto ���� IBaseDTO ͼ����Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean updateBookInfo(IBaseDTO dto);
	
	/**
	 * ɾ��ͼ����Ϣ
	 * @param dto ���� IBaseDTO ͼ����Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean delBookInfo(IBaseDTO dto);
	
	/**
	 * ��ʧͼ����Ϣ������������ɾ��������ɾ����־��ͼ�鶪ʧ��
	 * @param dto ���� IBaseDTO ͼ����Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean loseBookInfo(IBaseDTO dto);
	
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
	 * ���������Ϣ
	 * @param dto ���� IBaseDTO ͼ��軹��Ϣ
	 * @version Aug 31, 2006
	 * @return
	 */
	public boolean addReBorrowInfo(IBaseDTO dto);
	
	/**
	 * �õ�ͼ����ϸ��Ϣ
	 * @param id ���� String ͼ����
	 * @version Aug 31, 2006
	 * @return
	 */
	public IBaseDTO getBookInfo(String id);
	
	/**
	 * ��ѯͼ����Ϣ
	 * @param dto ���� IBaseDTO ͼ����Ϣ
	 * @param pageInfo ���� PageInfo ��ҳ��Ϣ
	 * @return ���� List �����ʼ��б���Ϣ
	 */
	public List bookIndex(IBaseDTO dto,PageInfo pageInfo);
	public int getBookSize();
	
	/**
	 * ��ʾԱ���û��б���Ϣ
	 * @param
	 * @version Sep 14, 2006
	 * @return
	 */
	public List<LabelValueBean> getEmployeeList();
	
	/**
	 * �õ���������Ϣ
	 */
	public List getBorrowInfo(String id);
	
}
