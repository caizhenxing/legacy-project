/**
 * 	@(#)NewPlanService.java   2006-11-17 下午02:40:54
 *	 。 
 *	 
 */
package et.bo.oa.newplan.service.impl;

import java.util.List;

import excellence.framework.base.dto.IBaseDTO;

/**
 * @author 
 * @version 2006-11-17
 * @see
 */
public interface NewPlanService {

	/**
	 * 返回当前计划列表
	 * @param
	 * @version 2006-11-17
	 * @return
	 */
	public List<IBaseDTO> getNowPlan(String id);
	
	/**
	 * 根据查询条件返回计划列表
	 * @param
	 * @version 2006-11-17
	 * @return
	 */
	public List<IBaseDTO> getPlans(IBaseDTO dto);
	
	/**
	 * 根据查询条件返回父计划列表
	 * @param
	 * @version 2006-11-17
	 * @return
	 */
	public List<IBaseDTO> getParentPlans(IBaseDTO dto);
	

	/**
	 * 增加计划
	 * @param
	 * @version 2006-11-17
	 * @return
	 */
	public void addPlan(IBaseDTO dto);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-17
	 * @return
	 */
	public void addParentPlan(IBaseDTO dto);
}
