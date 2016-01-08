/**
 * @(#)WorkFlowDefineService.java	 06/04/28
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.oa.wfManager.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**<code>WorkFlowManagerService</code> 
 * 
 * 
 * 
 * @author  yifei zhao
 * 
 * @version 06/04/28
 * @since   1.0
 *
 */
public interface WorkFlowDefineService {

	/**
     * add a new user into database
     *
     * @param   dto is <code>IBaseDTO</code> contains information about user's
     *
     * @version  1.0
     * 
     */
	public void addWfDef(IBaseDTO dto);
	
	/**
	 * update workflow instance's informantion 
	 * @param dto contains workflow instance's information
	 *  @version  1.0
	 */
	public void updateWfDef(IBaseDTO dto);
	
	/**
	 * delete workflow instance's information 
	 * @param id is workflow instance's id
	 * @version  1.0
	 */
	public void deleteWfDef(IBaseDTO dto);
	
	/**
	 * select unique workflow instance's information
	 * @param id is workflow instance's id
	 * @return unique workflow instance's information
	 */
	public IBaseDTO getWfDef(String id);
	
	/**
	 * 
	 * @param dto is restrict about select
	 * @param pi <code>PageInfo</code> is about page's info
	 * @return <code>List<IBaseDTO></code> contains  user's info
	 */
	public List<IBaseDTO> getWfDefList(IBaseDTO dto,PageInfo pi);
	
	/**
	 * @return int is list of user size
	 */
	public int getWfDefListSize();
}
