/**
 * @(#)RightService.java	 06/04/17
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.right.service;

import java.util.List;

import excellence.common.tree.TreeControl;
import excellence.common.tree.TreeControlI;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dto.IBaseDTO;


/**<code>RightService</code> is interface 
 * which contains a series of action about right
 * 
 * @author  yifei zhao
 * 
 * @version 06/04/17
 * @since   1.0
 * @see IBaseDTO
 * @see TreeControlI
 */
public interface RightService {

	/**
     * accredit to group 
     *
     * @param   group is group's id 
     * @param   rights <code>TreeControl</code> is tree of rights
     * @since   1.0
     * @see		TreeControl
     */
	public abstract void impowerGroup(String group,TreeService rights);
	
	/**
     * accredit to user 
     *
     * @param   user is user's id 
     * @param   rights <code>TreeControl</code> is tree of rights
     * @since   1.0
     * @see		TreeControl
     */
	public abstract void impowerUser(String user,TreeService rights);
	/**
     * accredit to roleRight 
     *
     * @param   user is user's id 
     * @param   rights <code>TreeControl</code> is tree of rights
     * @since   1.0
     * @see		TreeControl
     */
	//public abstract void impowerRoleRight(String user,TreeService rights);
	/**
	 * 
	 * @param userId is user's id
	 * @return <code>List<String></code> is a list of right 
	 */
	public abstract List<String> loadRight(String userId);
	
	public TreeService loadGroupRight(String group);
	
	public TreeService loadUserRight(String user);
	
	/**
	 * 由用户id得到该用户所具有的模块权限
	 * @param userId
	 * @return
	 */
	public TreeService getModuleTreeImpowers(String userId);
}
