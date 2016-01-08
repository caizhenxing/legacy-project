/**
 * @(#)ModuleService.java	 06/04/20
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.module.service;

import excellence.common.tree.TreeControlI;
import excellence.framework.base.dto.IBaseDTO;
/**<code>ModuleService</code> is interface 
 * which contains a series of action about module
 * 
 * @author  yifei zhao
 * 
 * @version 06/04/20
 * @since   1.0
 * @see IBaseDTO
 * @see TreeConrolI
 * @see ClassTreeService
 */
public interface ModuleService {

	/**
     * load module that user has right
     *
     * @param   userId is user's id 
     *
     * @return  <code>TreeControlI</code> is tree of module
     *		
     * 
     */
	public TreeControlI loadModules(String userId);
	
	/**
     * load all module 
     *
     * 
     *
     * @return  <code>TreeControlI</code> is tree of module
     *		
     * 
     */
	public TreeControlI loadModules();
	
	/**
     * add a new module into database
     *
     * @param   dto is <code>IBaseDTO</code> contains information about module's
     *
     * @version  1.0
     * 
     * */
	public void addModule(IBaseDTO dto);
	
	/**
     * remove the module from database
     *
     * @param   id is <code>String</code> is module's id
     *
     * @version  1.0
     * 
     * */
	public void removeModule(String id);
	
	/**
     * update the module 
     *
     * @param   dto is <code>IBaseDTO</code> contains information about module's
     *
     * @version  1.0
     * 
     * */
	public void updateModule(IBaseDTO dto);
	
	/**
	 * select unique Module's information
	 * @param id is Module's id
	 * @return unique Module's information
	 */
	public IBaseDTO getModule(String id);
}
