/**
 * @(#)PlanService.java	 06/05/10
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.oa.privy.plan.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;


/**<code>PlanService</code> is interface 
 * which contains a series of action about plan of person or company
 * 
 * @author  yifei zhao
 * 
 * @version 06/05/10
 * @since   1.0

 */
public interface PlanService {
	
	/**
	 * 
	 * @param id is plan id
	 * @param pi <code>PageInfo</code> is about page's info
	 * @return <code>List<IBaseDTO></code> contains  plan 's info
	 */
	public abstract List<IBaseDTO> listNow(String user,int size);
	
	/**
	 * 
	 * @param id is plan id
	 * @param pi <code>PageInfo</code> is about page's info
	 * @return <code>List<IBaseDTO></code> contains  plandetail 's info
	 */
	public abstract List<IBaseDTO> listDetailNow(String user);
	
	
	/**
	 * 
	 * @param id is plan id
	 * @param pi <code>PageInfo</code> is about page's info
	 * @return <code>List<IBaseDTO></code> contains  plandetail 's info
	 */
	public abstract List<IBaseDTO> listPlanDetail(String id);
	
	/**
	 * 
	 * @param id is plan id
	 * 
	 * @return int is list of plan size
	 */
	public abstract int listPlanDetailSize(String id);
	
	/**
	 * 
	 * @param dto is restrict about select
	 * @param pi <code>PageInfo</code> is about page's info
	 * @return <code>List<IBaseDTO></code> contains  plan's info
	 */
	public abstract List<IBaseDTO> listPlan(IBaseDTO dto,PageInfo pi);
	
	/**
	 * 
	 * @param dto is restrict about select
	 * 
	 * @return int is list of plansize
	 */
	public abstract int listPlanSize(IBaseDTO dto);
	
	/**
     * add a new plan into database
     *
     * @param   dto is <code>IBaseDTO</code> contains information about plan's
     *
     * @version  1.0
     * 
     */
	public abstract void addPlan(IBaseDTO dto);
	
	/**
     * add a new plandetail into database
     *
     * @param   dto is <code>IBaseDTO</code> contains information about plan's
     *
     * @version  1.0
     * 
     */
	public abstract void addPlanDetail(IBaseDTO dto);
	
	/**
	 * delete plan's information cascade plan detail
	 * @param id is plan's id
	 * @version  1.0
	 */
	public abstract void deletePlan(String id);
	/**
	 * delete plandetail's information 
	 * @param id is plandetail's id
	 * @version  1.0
	 */
	public abstract void deletePlanDetail(String id);
	
	/**
	 * update plan's informantion 
	 * @param dto contains new plan's information
	 *  @version  1.0
	 */
	public abstract void updatePlan(IBaseDTO dto);

	/**
	 * update plan detail's informantion 
	 * @param dto contains new plan detail's information
	 *  @version  1.0
	 */
	public abstract void updatePlanDetail(IBaseDTO dto);
	
	/**
	 * select unique plan's information
	 * @param id is plan's id
	 * @return unique plan's information
	 */
	public abstract IBaseDTO uniquePlan(String id);
	/**
	 * select unique plan detail's information
	 * @param id is plan detail's id
	 * @return unique plan detail's information
	 */
	public abstract IBaseDTO uniquePlanDetail(String id);
	
	/**
	 * check this plan is ok
	 *
	 */
	public void checkPlan(IBaseDTO dto);
}
