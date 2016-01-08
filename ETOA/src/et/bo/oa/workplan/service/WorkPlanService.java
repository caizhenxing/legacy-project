/**
 * 	@(#)WorkPlanService.java   2006-11-9 ����11:01:17
 *	 �� 
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
	 * @return ����õ�plan
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
	 * ���ղ��ŵõ��ƻ��б�
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getPlanListByUseful(String dep);
	
	/**
	 * ���ղ������õ��Ӽƻ��б�
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getSubPlanList(String dep);
	/**
	 * �õ����ƶ��ƻ���Χ
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getPlanDomain(UserInfo ui);
	/**
	 * �õ���ָ�ɵ���һ������
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<LabelValueBean> getSubDep(String planId);
	
	/**
	 * �õ��ҵ�����
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getMyMission(UserInfo ui);
	
	/** 
	 * JING
	 *�¸ĵ�: �õ��ҵ�����
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getMyMyMission(UserInfo ui);
	/**
	 * �õ��ҵļƻ�
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getMyPlan(UserInfo ui);
	/**
	 * �õ��ҵļƻ�
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getMyPlan(UserInfo ui,int num);
	/**
	 * �õ��ҵļƻ����Ӽƻ�
	 * @param
	 * @version 2006-11-13
	 * @return
	 */
	public List<IBaseDTO> getSubPlan(String id);
	
	/**
	 * ��ѯ�ƻ�
	 * @param
	 * @version 2006-11-18
	 * @return
	 */
	public List<IBaseDTO> findPlan(IBaseDTO dto,PageInfo pi);
	
	
//	/**
//	 * ɾ������ƻ�
//	 * @param
//	 * @version 2006-11-18
//	 * @return
//	 */
//	public List<IBaseDTO> findPlandel(IBaseDTO dto,PageInfo pi,String del);
	
	
	/**
	 * ��ѯ�ƻ���Ŀ
	 * @param
	 * @version 2006-11-18
	 * @return
	 */
	public int findPlanSize(IBaseDTO dto);
	/**
	 * ��ѯ����
	 * @param
	 * @version 2006-11-18
	 * @return
	 */
	public List<IBaseDTO> findMission(IBaseDTO dto,PageInfo pi);
	/**
	 * ��ѯ������Ŀ
	 * @param
	 * @version 2006-11-18
	 * @return
	 */
	public int findMissionSize(IBaseDTO dto);
	
	/**
	 * ����id�������û���ѯ��������plan
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public String getPlanId(UserInfo user);
	
	
	
	/**
	 * @describe �޸�ɾ�����
	 * @param
	 * @return void
	 */ 
	public boolean myupdateplan(String linkType,String id,IBaseDTO dto);
	
	
	/**
	 * JING
	 * @describe ��ϸ�ƻ���ɾ�����
	 * @param
	 * @return void
	 */ 
	public boolean myMissionSing(String missionId,IBaseDTO dto,String checktype);
	
	
	
	/**
	 * ����ʱ���õ���ϸ�ƻ���ʾ
	 */
	public List<IBaseDTO> getMyMyMyMission(UserInfo ui);
	
	public List<IBaseDTO> findMissionpage(IBaseDTO dto1,PageInfo pi);
	
	public List<IBaseDTO> missionPlanpage(IBaseDTO dto1,PageInfo pi);
}
