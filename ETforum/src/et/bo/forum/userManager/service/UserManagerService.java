/**
 * 	@(#)UserManagerService.java   2006-12-25 ����09:16:25
 *	 �� 
 *	 
 */
package et.bo.forum.userManager.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @discribe �û�����
 * @author Ҷ����
 * @version 2006-12-25
 * @see
 */
public interface UserManagerService {
	/**
	 * @discribe �û���ѯ
	 * @param
	 * @version 2006-12-25
	 * @return List
	 */
	public List userQuery(IBaseDTO dto, PageInfo pageInfo);
	/**
	 * @discribe ȡ������
	 * @param
	 * @version 2006-12-25
	 * @return
	 */
	public int getSize();
	/**
	 * �û�ɾ��
	 * @param
	 * @version 2006-12-25
	 * @return
	 */
	public void deleteUser(String[] idArray);
}
