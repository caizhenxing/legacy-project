/**
 * @(#)DepartmentService.java	 06/05/08
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.department.service;

import ocelot.common.tree.TreeControlI;
import ocelot.framework.base.dto.IBaseDTO;

/**<code>DepartmentService</code> is interface 
 * which contains a series of action about department
 * 
 * @author  yifei zhao
 * 
 * @version 06/05/08
 * @since   1.0
 * @see IBaseDTO
 * @see TreeConrolI
 * @see ClassTreeService
 */
public interface DepartmentService {

	
	
	/**
     * load all departments 
     *
     * 
     *
     * @return  <code>TreeControlI</code> is tree of departments
     *		
     * 
     */
	public TreeControlI loadDepartments();
	
	/**
     * add a new department into database
     *
     * @param   dto is <code>IBaseDTO</code> contains information about department's
     *
     * @version  1.0
     * 
     * */
	public void addDepartment(IBaseDTO dto);
	
	/**
     * remove the department from database
     *
     * @param   id is <code>String</code> is department's id
     *
     * @version  1.0
     * 
     * */
	public void removeDepartment(String id);
	
	/**
     * update the department 
     *
     * @param   dto is <code>IBaseDTO</code> contains information about department's
     *
     * @version  1.0
     * 
     * */
	public void updateDepartment(IBaseDTO dto);
	
	/**
	 * select unique department's information
	 * @param id is department's id
	 * @return unique department's information
	 */
	public IBaseDTO getDepartment(String id);
	
}
