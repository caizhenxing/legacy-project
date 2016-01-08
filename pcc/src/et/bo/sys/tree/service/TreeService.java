/**
 * @(#)TreeService.java	 06/05/09
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.tree.service;

import ocelot.common.tree.TreeControlI;
import ocelot.framework.base.dto.IBaseDTO;
/**<code>TreeService</code> is interface 
 * which contains a series of action about parameter
 * 
 * @author  yifei zhao
 * 
 * @version 06/05/09
 * @since   1.0
 * @see IBaseDTO
 * @see TreeConrolI
 * @see ClassTreeService
 */
public interface TreeService {


	/**
     * load all tree 
     *
     * 
     *
     * @return  <code>TreeControlI</code> is tree of parameter
     *		
     * 
     */
	public TreeControlI loadTrees();
	
	/**
     * add a new tree into database
     *
     * @param   dto is <code>IBaseDTO</code> contains information about tree's
     *
     * @version  1.0
     * 
     * */
	public void addTree(IBaseDTO dto);
	
	/**
     * remove the tree from database
     *
     * @param   id is <code>String</code> is tree's id
     *
     * @version  1.0
     * 
     * */
	public void removeTree(String id);
	
	/**
     * update the tree 
     *
     * @param   dto is <code>IBaseDTO</code> contains information about tree's
     *
     * @version  1.0
     * 
     * */
	public void updateTree(IBaseDTO dto);
	
	/**
	 * select unique tree's information
	 * @param id is tree's id
	 * @return unique tree's information
	 */
	public IBaseDTO getTree(String id);
}
