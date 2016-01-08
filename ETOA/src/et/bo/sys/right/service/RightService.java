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
	public abstract void impowerGroup(String group,TreeControl rights);
	
	/**
     * accredit to user 
     *
     * @param   user is user's id 
     * @param   rights <code>TreeControl</code> is tree of rights
     * @since   1.0
     * @see		TreeControl
     */
	public abstract void impowerUser(String user,TreeControl rights);
	
	/**
	 * 
	 * @param userId is user's id
	 * @return <code>List<String></code> is a list of right 
	 */
	public abstract List<String> loadRight(String userId);
	
	public TreeControlI loadGroupRight(String group);
	
	public TreeControlI loadUserRight(String user);
}
