/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾�� ׿Խ�Ƽ� ����һ��Ȩ��
 * 
 */
package et.bo.sys.login.service;

import et.bo.sys.login.bean.UserBean;
import excellence.common.tree.base.service.TreeService;


/**
 * @describe �û���½
 * @author  ������
 * @version 1.0, 2007-04-05//
 * @see
 */
public interface loginService {
	/**
	 * @describe ���û���½��֤
	 * @param
	 * @return void
	 */ 
	public boolean login(String userId, String password);

	/**
	 * @describe �����û�id�õ��û�Ȩ����
	 * @param userId
	 * @return
	 */
	public TreeService loadTree(String userId);
	
	/**
	 * �ŷ����
	 * �õ��û���ϸ��Ϣbean(ָ���û�)
	 * @param userId ָ���û�id
	 * @return UserBean
	 */
	UserBean loadUserBean(String userId);
}
