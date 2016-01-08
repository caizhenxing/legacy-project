/**
 * 	@(#)RegisterService.java   Nov 30, 2006 3:43:21 PM
 *	 。 
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
	 * 登录用户注册信息
	 * @param dto IBaseDTO
	 * @version Nov 30, 2006
	 * @return
	 */
	public void registerUser(IBaseDTO dto);
	
	/**
	 * 修改用户注册信息
	 * @param dto IBaseDTO
	 * @version Nov 30, 2006
	 * @return
	 */
	public void updateUserInfo(IBaseDTO dto);
	
	/**
	 * checklogin检查是否登陆
	 * @param
	 * @version Dec 15, 2006
	 * @return
	 */
	public boolean checkLogin(String name,String password);
	
	/**
	 * 根据名称得到id
	 * @param name String
	 * @version Dec 21, 2006
	 * @return
	 */
	public String getIdByName(String name);

}
