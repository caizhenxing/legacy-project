/**
 * 	@(#)BookBorrowService.java   Sep 1, 2006 11:04:29 AM
 *	 �� 
 *	 
 */
package et.bo.oa.checkwork.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**
 * <p> ȱ�ڹ��� �ӿ� </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-10
 * 
 */
public interface AbsenceServiceI {

	/**
	 * <p> ���ȱ�ڼ�¼ </p>
	 * 
	 * @param infoDTO
	 */
	public void addAbsence(IBaseDTO infoDTO);

	/**
	 * <p> ��Ӳ�ǩ��¼ </p>
	 * 
	 * @param infoDTO
	 */
	public void addResign(IBaseDTO infoDTO);

	/**
	 * <p> ���ȱ�ڼ�¼ </p>
	 * 
	 * @param infoDTO
	 * @return
	 */
	public Object[] selectAbsenseList(IBaseDTO infoDTO,PageInfo pi);

	/**
	 * <p> ����û��б� </p>
	 * @param page: ����ҳ����
	 * @return���û���Ϣ��id��name��
	 */
	public Object[] getUserList(String page,IBaseDTO infoDTO);
	
	/**
	 * <p> ��ü�¼�� </p>
	 * @return
	 */
	public int getAbsenceSize();
	
	/**
	 * <p> ���Сʱ�� </p>
	 * @return
	 */
	public List gethour();
	/**
	 * @describe ������״̬�б�
	 * @param
	 * @return
	 * 
	 */
	public List getOutStateList();
	/**
	 * @describe ������������״̬�б�
	 * @param
	 * @return
	 * 
	 */
	public List getAllOutStateList();
}
