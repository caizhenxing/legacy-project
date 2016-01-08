/**
 * @(#)UserService.java	 06/04/17
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.user.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <code>UserService</code> is interface which contains a series of action
 * about user
 * 
 * @author yuzhuo Jing
 * 
 * @version 08/02/15
 * @since 1.0
 * @see IBaseDTO
 */
public interface UserService {
	/**
	 * 获得座席员列表
	 * 
	 * @param sql
	 * @return List
	 */

	public List userQuery(String sql);

	/**
	 * @describe 取得OA用户信息条数
	 * @param
	 * @return int
	 */
	public abstract int listUserSize(IBaseDTO dto);

	/**
	 * @describe 根据Id取得OA用户信息
	 * @param
	 * @return dto(类型)
	 */
	public IBaseDTO getUserInfo(String id);

	/**
	 * @describe 添加OA用户信息
	 * @param
	 * @return void
	 */
	public void addUser(IBaseDTO dto);

	/**
	 * @describe 修改OA用户信息
	 * @param
	 * @return void
	 */
	public boolean userUpdate(IBaseDTO dto);

	/**
	 * @describe 删除OA用户信息
	 * @param
	 * @return void
	 */
	public void delUser(String id);

	/**
	 * @describe 查询OA用户信息
	 * @param
	 * @return List
	 */
	public List<IBaseDTO> userList(IBaseDTO dto, PageInfo pi);

	/**
	 * 得到用户列表
	 * 
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getUsers(String src);

	/**
	 * @describe 批量添加OA用户信息
	 * @param
	 * @return boolean
	 */
	public boolean addUserAll(IBaseDTO dto, String str);

	/**
	 * @describe 返回组信息
	 * @param
	 * @return list
	 */
	public List getGroupList();

	/**
	 * @describe 返回角色信息
	 * @param
	 * @return list
	 */
	public List getRoleList();

	/**
	 * 将需要的值存入dto中，返回给座席面板
	 * 
	 * @return
	 */
	public List<IBaseDTO> getUserList();

	/**
	 * zhang feng add 在系统启动的时候将有权限的用户的信息加载到内存中
	 * 如普通案例库，key值putong，后面的数组里面的值是所有具有权限的人员的列表
	 * 这个方法加载后可以在系统的任何时候得到具有权限的人员的列表，现在的主要目的是给发送短消息的人员使用
	 */
	void addPowerInStaticMap();

}
