/**
 * @(#)UserService.java	 06/04/17
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.user.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**<code>UserService</code> is interface 
 * which contains a series of action about user
 * 
 * @author  yifei zhao
 * 
 * @version 06/04/17
 * @since   1.0
 * @see IBaseDTO
 */
public interface UserService {

	/**
     * check the user's id and password is right
     *
     * @param   userId is user's id 
     * @param   password is password
     * @return  <code>true</code> if the userId and password is right
     *		<code>false</code> otherwise.
     * 
     */
	public abstract boolean check(String userId,String password);
	
	/**
     * add a new user into database
     *
     * @param   dto is <code>IBaseDTO</code> contains information about user's
     *
     * @version  1.0
     * 
     */
	public abstract void insertUser(IBaseDTO dto);
	
	/**
     * judge the user's id is existing
     *
     * @param   userId is user's id
     *
     *@return  <code>true</code> if the userId is exist
     *		<code>false</code> otherwise.
     * @version  1.0
     * 
     */
	public abstract boolean exist(String userId);
	
	/**
	 * delete user's information 
	 * @param id is user's id
	 * @version  1.0
	 */
	public abstract boolean deleteUser(String id);
	
	/**
	 * freeze user 
	 * @param id is user's id
	 * @version  1.0
	 */
	public abstract void freezeUser(String id);
	
	/**
	 * thaw user 
	 * @param id is user's id
	 * @version  1.0
	 */
	public abstract void thawUser(String id);
	
	/**
	 * update user's informantion 
	 * @param dto contains new user's information
	 *  @version  1.0
	 */
	public abstract void updateUser(IBaseDTO dto);
	
	/**
	 * 
	 * @param dto is restrict about select
	 * @param pi <code>PageInfo</code> is about page's info
	 * @return <code>List<IBaseDTO></code> contains  user's info
	 */
	public abstract List<IBaseDTO> listUser(IBaseDTO dto,PageInfo pi);
	
	/**
	 * 
	 * @param dto is restrict about select
	 * 
	 * @return int is list of user size
	 */
	public abstract int listUserSize(IBaseDTO dto);
	
	/**
	 * select unique user's information
	 * @param id is user's id
	 * @return unique user's information
	 */
	public abstract IBaseDTO uniqueUser(String id);
	
	/**
	 * select unique user's information
	 * @param id is user's id
	 * @return unique user's information in Pojo
	 */
	public abstract Object uniqueUserPo(String id);
	/**
	 * @describe  用户修改密码
	 * @param  IBaseDTO dto
	 * @return 
	 * 
	 */
	public void updatePwd (IBaseDTO dto);
	/**
	 * @describe 判断原密码是否与当前输入的密码相同 ture为有此用户
	 * @param    String
	 * @return   boolean
	 * 
	 */
	public boolean judgementSameUer(IBaseDTO dto);
	/**
	 * @describe  管理员修改密码
	 * @param  IBaseDTO dto
	 * @return 
	 * 
	 */
//	public void modifyPwd(String userkey,String pwd);
}
