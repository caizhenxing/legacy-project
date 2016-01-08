/**
 * 	@(#)RegisterService.java   Nov 30, 2006 3:43:21 PM
 *	 �� 
 *	 
 */
package et.bo.forum.useroper.register.service;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhangfeng
 * @version Nov 30, 2006
 * @see
 */
public interface RegisterService {
	
	/**
	 * ��¼�û�ע����Ϣ
	 * @param dto IBaseDTO
	 * @version Nov 30, 2006
	 * @return
	 */
	public void registerUser(IBaseDTO dto);
	
	/**
	 * �޸��û�ע����Ϣ
	 * @param dto IBaseDTO
	 * @version Nov 30, 2006
	 * @return
	 */
	public void updateUserInfo(IBaseDTO dto);
	
	/**
	 * checklogin����Ƿ��½
	 * @param
	 * @version Dec 15, 2006
	 * @return
	 */
	public boolean checkLogin(String name,String password);
	
	/**
	 * �������Ƶõ�id
	 * @param name String
	 * @version Dec 21, 2006
	 * @return
	 */
	public String getIdByName(String name);

}
