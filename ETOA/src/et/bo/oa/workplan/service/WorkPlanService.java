/**
 * 	@(#)WorkPlanService.java   2006-11-9 上午11:01:17
 *	 。 
 *	 
 */
package et.bo.oa.workplan.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author yifeizhao
 * @version 2006-11-9
 * @see
 */
public interface WorkPlanService {

	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return 保存好的plan
	 */
	public IBaseDTO createPlan(IBaseDTO plan);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public void createMission(IBaseDTO mission);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public IBaseDTO getPlan(String id);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public List<IBaseDTO> getActionPlan(String user);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public List<IBaseDTO> getActionPlan(IBaseDTO dto);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public List<IBaseDTO> getMissions(String planid);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-15
	 * @return
	 */
	public List<IBaseDTO> getParentMissions(String subPlanId);
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public IBaseDTO getMission(String id);
	
	
	
	public List<IBaseDTO> getMyMission(UserInfo ui,int num);
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public List<IBaseDTO> getMissionsByDate(String user);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public void updatePlan(IBaseDTO plan);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public void updateMission(IBaseDTO plan);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public void delPlan(String id);
	
	/**
	 * 按照部门得到计划列表
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getPlanListByUseful(String dep);
	
	/**
	 * 按照部门名得到子计划列表
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getSubPlanList(String dep);
	/**
	 * 得到可制定计划范围
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getPlanDomain(UserInfo ui);
	/**
	 * 得到可指派得下一级部门
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getSubDep(String planId);
	
	/**
	 * 得到我的任务
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getMyMission(UserInfo ui);
	
	/** 
	 * JING
	 *新改的: 得到我的任务
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getMyMyMission(UserInfo ui);
	/**
	 * 得到我的计划
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getMyPlan(UserInfo ui);
	/**
	 * 得到我的计划
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getMyPlan(UserInfo ui,int num);
	/**
	 * 得到我的计划的子计划
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getSubPlan(String id);
	
	/**
	 * 查询计划
	 * @param
	 * @version 2006-11-18
	 * @return
	 */
	public List<IBaseDTO> findPlan(IBaseDTO dto,PageInfo pi);
	
	
//	/**
//	 * 删除标题计划
//	 * @param
//	 * @version 2006-11-18
//	 * @return
//	 */
//	public List<IBaseDTO> findPlandel(IBaseDTO dto,PageInfo pi,String del);
	
	
	/**
	 * 查询计划数目
	 * @param
	 * @version 2006-11-18
	 * @return
	 */
	public int findPlanSize(IBaseDTO dto);
	/**
	 * 查询任务
	 * @param
	 * @version 2006-11-18
	 * @return
	 */
	public List<IBaseDTO> findMission(IBaseDTO dto,PageInfo pi);
	/**
	 * 查询任务数目
	 * @param
	 * @version 2006-11-18
	 * @return
	 */
	public int findMissionSize(IBaseDTO dto);
	
	/**
	 * 返回id，根据用户查询出发布的plan
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public String getPlanId(UserInfo user);
	
	
	
	/**
	 * @describe 修改删除标记
	 * @param
	 * @return void
	 */ 
	public boolean myupdateplan(String linkType,String id,IBaseDTO dto);
	
	
	/**
	 * JING
	 * @describe 详细计划的删除标记
	 * @param
	 * @return void
	 */ 
	public boolean myMissionSing(String missionId,IBaseDTO dto,String checktype);
	
	
	
	/**
	 * 加载时候用的详细计划显示
	 */
	public List<IBaseDTO> getMyMyMyMission(UserInfo ui);
	
	public List<IBaseDTO> findMissionpage(IBaseDTO dto1,PageInfo pi);
	
	public List<IBaseDTO> missionPlanpage(IBaseDTO dto1,PageInfo pi);
}
