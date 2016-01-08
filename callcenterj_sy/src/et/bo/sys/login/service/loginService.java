/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.sys.login.service;

import et.bo.sys.login.bean.UserBean;
import excellence.common.tree.base.service.TreeService;


/**
 * @describe 用户登陆
 * @author  荆玉琢
 * @version 1.0, 2007-04-05//
 * @see
 */
public interface loginService {
	/**
	 * @describe 做用户登陆验证
	 * @param
	 * @return void
	 */ 
	public boolean login(String userId, String password);

	/**
	 * @describe 根据用户id得到用户权限树
	 * @param userId
	 * @return
	 */
	public TreeService loadTree(String userId);
	
	/**
	 * 张锋添加
	 * 得到用户详细信息bean(指定用户)
	 * @param userId 指定用户id
	 * @return UserBean
	 */
	UserBean loadUserBean(String userId);
}
