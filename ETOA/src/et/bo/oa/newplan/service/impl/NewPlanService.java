/**
 * 	@(#)NewPlanService.java   2006-11-17 ����02:40:54
 *	 �� 
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
	 * ���ص�ǰ�ƻ��б�
	 * @param
	 * @version 2006-11-17
	 * @return
	 */
	public List<IBaseDTO> getNowPlan(String id);
	
	/**
	 * ���ݲ�ѯ�������ؼƻ��б�
	 * @param
	 * @version 2006-11-17
	 * @return
	 */
	public List<IBaseDTO> getPlans(IBaseDTO dto);
	
	/**
	 * ���ݲ�ѯ�������ظ��ƻ��б�
	 * @param
	 * @version 2006-11-17
	 * @return
	 */
	public List<IBaseDTO> getParentPlans(IBaseDTO dto);
	

	/**
	 * ���Ӽƻ�
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
