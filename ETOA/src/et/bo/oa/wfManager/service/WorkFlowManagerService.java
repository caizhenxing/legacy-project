/**
 * @(#)WorkFlowManagerService.java	 06/04/28
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.oa.wfManager.service;

import java.util.List;

import excellence.framework.base.dto.IBaseDTO;

/**<code>WorkFlowManagerService</code> is interface 
 * which contains a series of action about wrokflow instances
 * 
 * @author  yifei zhao
 * 
 * @version 06/04/28
 * @since   1.0
 *
 */
public interface WorkFlowManagerService {

	/**
     * return list of workflow instances
     *
     * @param   dto <code>IBaseDTO</code> is 
     *
     * @return  list of workflow instances
     */
	public List<IBaseDTO> getInsList(IBaseDTO dto);
	
	/**
     * update workflow instance
     *
     * @param   dto <code>IBaseDTO</code> is 
     *
     * @return  list of workflow instances
     */
	public void update (IBaseDTO dto);
	
}
