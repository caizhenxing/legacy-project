/**
 * @(#)GroupService.java	 06/04/17
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.group.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**<code>GroupService</code> is interface 
 * which contains a series of action about group
 * 
 * @author  yifei zhao
 * 
 * @version 06/04/17
 * @since   1.0
 *
 */
public interface GroupService {

	/**
     * add a new group into database
     *
     * @param   dto is <code>IBaseDTO</code> contains information about group's
     *
     * @version  1.0
     * 
     */
	public abstract void insertGroup(IBaseDTO dto);
	
	/**
     * judge the group's id is existing
     *
     * @param   groupId is group's id
     *
     *@return  <code>true</code> if the groupId is exist
     *		<code>false</code> otherwise.
     * @version  1.0
     * 
     */
	public abstract boolean exist(String groupId);
	
	/**
	 * delete group's information 
	 * @param id is group's id
	 * @version  1.0
	 */
	public abstract boolean deleteGroup(String id);
	
	/**
	 * freeze group 
	 * @param id is group's id
	 * @version  1.0
	 */
	public abstract void freezeGroup(String id);
	
	/**
	 * thaw group 
	 * @param id is group's id
	 * @version  1.0
	 */
	public abstract void thawGroup(String id);
	
	/**
	 * update group's informantion 
	 * @param dto contains new group's information
	 *  @version  1.0
	 */
	public abstract void updateGroup(IBaseDTO dto);
	
	/**
	 * 
	 * @param dto is restrict about select
	 * @param pi <code>PageInfo</code> is about page's info
	 * @return <code>List<IBaseDTO></code> contains  group's info
	 */
	public abstract List<IBaseDTO> listGroup(IBaseDTO dto,PageInfo pi);
	
	/**
	 * 
	 * @param dto is restrict about select
	 * 
	 * @return int is list of group size
	 */
	public abstract int listGroupSize(IBaseDTO dto,PageInfo pi);
	
	/**
	 * select unique group's information
	 * @param id is group's id
	 * @return unique group's information
	 */
	public abstract IBaseDTO uniqueGroup(String id);
	
	/**
	 * select unique group's information
	 * @param id is group's id
	 * @return unique group's information in Pojo
	 */
	public abstract Object uniqueGroupPo(String id);
	
	public void test();
}
