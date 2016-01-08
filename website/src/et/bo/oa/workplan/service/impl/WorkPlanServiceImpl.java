/**
 * 	@(#)WorkPlanServiceImpl.java   2006-11-13 下午12:09:48
 *	 。 
 *	 
 */
package et.bo.oa.workplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.oa.workplan.service.WorkPlanService;
import et.bo.sys.department.service.DepartmentService;
import et.bo.sys.login.UserInfo;
import et.po.WorkPlanInfo;
import et.po.WorkPlanMission;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tree.TreeNode;
import excellence.common.util.regex.StringUtil;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author 
 * @version 2006-11-13
 * @see
 */
public class WorkPlanServiceImpl implements WorkPlanService {

	private BaseDAO dao=null;
	private KeyService ks=null;
	private ClassTreeService cts=null;
	private ClassTreeService depT=null;
	private DepartmentService ds=null;
	
	private int planSize;
	private int missionSize;
	WorkPlanHelper wph=new WorkPlanHelper();
	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#createMission(excellence.framework.base.dto.IBaseDTO)
	 */
	public void createMission(IBaseDTO mission) {
		// TODO Auto-generated method stub
		WorkPlanMission wpm=new WorkPlanMission();
		
		mission.loadValue(wpm);
		wpm.setId(ks.getNext("work_plan_mission"));
		wpm.setWorkPlanInfoByCreatePlanId((WorkPlanInfo)dao.loadEntity(WorkPlanInfo.class,(String)mission.get("planId")));
		wpm.setWorkPlanInfoByPlanId(wpm.getWorkPlanInfoByCreatePlanId());
		wpm.setMissionSign("2");
		dao.saveEntity(wpm);
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#createPlan(excellence.framework.base.dto.IBaseDTO)
	 */
	public IBaseDTO createPlan(IBaseDTO plan) {
		// TODO Auto-generated method stub
		IBaseDTO dto=new DynaBeanDTO();
		WorkPlanInfo wpi=new WorkPlanInfo();
		plan.loadValue(wpi);
		wpi.setId(ks.getNext("work_plan_info"));
		//wpi.setSysClasses((SysClasses)dao.loadEntity(SysClasses.class, (String)plan.get("sysClasses")));
		
		
		dto.setObject(wpi);
		wpi.setCheckId("2");
		/*if("1".equals(wpi.getCheckId()))
		{
			Object[] o=dao.findEntity(wph.myPlan(wpi.getCreateUser()));
			for(int i=0,size=o.length;i<size;i++)
			{
				WorkPlanInfo wpi1=(WorkPlanInfo)o[i];
				wpi1.setCheckId("0");
				dao.saveEntity(wpi1);
			}
		}*/
		dao.saveEntity(wpi);
		return dto;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#delPlan(java.lang.String)
	 */
	public void delPlan(String id) {
		// TODO Auto-generated method stub
		dao.removeEntity(WorkPlanInfo.class,id);
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#getActionPlan(java.lang.String)
	 */
	public List<IBaseDTO> getActionPlan(String user) {
		// TODO Auto-generated method stub
		
		Object[] re=dao.findEntity(wph.actionPlan(user,""));
		List<IBaseDTO> res=new ArrayList<IBaseDTO>();
		for(int i=0,size=re.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(re[i]);
			res.add(dto);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#getActionPlan(excellence.framework.base.dto.IBaseDTO)
	 */
	public List<IBaseDTO> getActionPlan(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#getMissions(java.lang.String)
	 */
	public List<IBaseDTO> getMissions(String planid) {
		// TODO Auto-generated method stub
		WorkPlanInfo wpi=(WorkPlanInfo)dao.loadEntity(WorkPlanInfo.class,planid);
		if(wpi==null)
		return null;
		List<IBaseDTO> res=new ArrayList<IBaseDTO>();
		//Object[] temp=dao.findEntity(wpi.getWorkPlanMissionsForCreatePlanId(), "from WorkPlanMission w order by w.createTime");
		
		/*while(i1.hasNext())
		{
			IBaseDTO dto=new DynaBeanDTO();
			WorkPlanMission wpm=i1.next();
			dto.setObject(wpm);
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			dto.set("planId", wpm.getWorkPlanInfoByPlanId().getId());
			res.add(dto);
		}*/
		//Iterator<WorkPlanMission> i2=wpi.getWorkPlanMissionsForPlanId().iterator();
		Object[] temp=dao.findEntity(wpi.getWorkPlanMissionsForPlanId(), "order by this.createTime");
		for(int i=0,size=temp.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			WorkPlanMission wpm=(WorkPlanMission)temp[i];
			dto.setObject(wpm);
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			dto.set("planId", wpm.getWorkPlanInfoByPlanId().getId());
			dto.set("missionInfo",StringUtil.ellipsis(wpm.getMissionInfo(), 25, "···"));
			dto.set("missionComplete",StringUtil.ellipsis(wpm.getMissionComplete(), 25, "···"));
			res.add(dto);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#getMissionsByDate(java.lang.String)
	 */
	public List<IBaseDTO> getMissionsByDate(String user) {
		// TODO Auto-generated method stub
		List<IBaseDTO> res=new ArrayList<IBaseDTO>();
		Object[] re=dao.findEntity(wph.actionMission(user));
		for(int i=0,size=re.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(re[i]);
			res.add(dto);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#getPlanListByUseful(java.lang.String)
	 */
	public List<LabelValueBean> getPlanListByUseful(String dep) {
		// TODO Auto-generated method stub
		List<LabelValueBean> r=new ArrayList<LabelValueBean>();
		List<TreeNode> l=depT.getParentTree(dep);
		String[] deps=new String[l.size()];
		for(int i=0,size=l.size();i<size;i++)
		{
			deps[i]=((TreeNode)l.get(i)).getName();
		}
		Object[] re=dao.findEntity(wph.parentPlan(deps));
		for(int i=0,size=re.length;i<size;i++)
		{
			LabelValueBean lvb=new LabelValueBean();
			WorkPlanInfo wpi=(WorkPlanInfo)re[i];
			lvb.setLabel(wpi.getPlanTitle());
			lvb.setValue(wpi.getId());
			r.add(lvb);
		}
		return r;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#updateMission(excellence.framework.base.dto.IBaseDTO)
	 */
	public void updateMission(IBaseDTO plan) {
		// TODO Auto-generated method stub
		String id=(String)plan.get("id");
		WorkPlanMission wpm=(WorkPlanMission)dao.loadEntity(WorkPlanMission.class,id);
		if(wpm==null)
			return;
		String planname=wpm.getName();
		String createUser=wpm.getCreateUser();
			plan.loadValue(wpm);
		wpm.setCreateUser(createUser);
		wpm.setName(planname);
			wpm.setMissionSign((String)plan.get("missionSign"));
		if(wpm.getMissionSign().equals("1")||wpm.getMissionSign().equals("-1"));
		wpm.setCompleteTime(TimeUtil.getNowTime());
		
		//wpm.setWorkPlanInfoByPlanId((WorkPlanInfo)dao.loadEntity(WorkPlanInfo.class,(String)plan.get("planId")));
		dao.saveEntity(wpm);
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.workplan.service.WorkPlanService#updatePlan(excellence.framework.base.dto.IBaseDTO)
	 */
	public void updatePlan(IBaseDTO plan) {
		// TODO Auto-generated method stub
		String id=(String)plan.get("id");
		WorkPlanInfo wpi=(WorkPlanInfo)dao.loadEntity(WorkPlanInfo.class,id);
		if(wpi==null)
			return;
		String check=(String)plan.get("check");
		if(!"t".equals(check))
		{
		Date createTime=wpi.getCreateTime();
		String createUser=wpi.getCreateUser();
		plan.loadValue(wpi);
		wpi.setCreateTime(createTime);
		wpi.setCreateUser(createUser);
		}
		if("1".equals(plan.get("checkId")))
		{
			Object[] o=dao.findEntity(wph.myPlan(wpi.getCreateUser()));
			for(int i=0,size=o.length;i<size;i++)
			{
				WorkPlanInfo wpi1=(WorkPlanInfo)o[i];
				wpi1.setCheckId("0");
				dao.saveEntity(wpi1);
			}
			wpi.setCheckId("1");
		}
		
		dao.saveEntity(wpi);
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public ClassTreeService getDepT() {
		return depT;
	}

	public void setDepT(ClassTreeService depT) {
		this.depT = depT;
	}

	public List<LabelValueBean> getSubPlanList(String dep) {
		// TODO Auto-generated method stub
		List<LabelValueBean> r=new ArrayList<LabelValueBean>();
		
		Object[] re=dao.findEntity(wph.subPlan(dep));
		for(int i=0,size=re.length;i<size;i++)
		{
			LabelValueBean lvb=new LabelValueBean();
			WorkPlanInfo wpi=(WorkPlanInfo)re[i];
			lvb.setLabel(wpi.getPlanTitle());
			lvb.setValue(wpi.getId());
			r.add(lvb);
		}
		return r;
	}

	public IBaseDTO getPlan(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto=new DynaBeanDTO();
		Object wpi=dao.loadEntity(WorkPlanInfo.class,id);
		dto.setObject(wpi);
		return dto;
		
	}

	public IBaseDTO getMission(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto=new DynaBeanDTO();
		WorkPlanMission wpm=(WorkPlanMission)dao.loadEntity(WorkPlanMission.class,id);
		dto.setObject(wpm);
		if(wpm.getWorkPlanInfoByPlanId()!=null)
		dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
		else
			dto.set("planName","");
		if(wpm.getWorkPlanInfoByPlanId()!=null)
		dto.set("planId", wpm.getWorkPlanInfoByPlanId().getId());
		else
			dto.set("planId","");
		return dto;
	}

	public List<IBaseDTO> getParentMissions(String subPlanId) {
		// TODO Auto-generated method stub
		List<IBaseDTO> res=new ArrayList<IBaseDTO>();
		WorkPlanInfo wpi=(WorkPlanInfo)dao.loadEntity(WorkPlanInfo.class,subPlanId);
		if(wpi==null)
		return res;
		String p=wpi.getParentId();
		if(p==null&&p.equals(""))
			return res;
		WorkPlanInfo wpip=(WorkPlanInfo)dao.loadEntity(WorkPlanInfo.class,p);
		if(wpip==null)
			return res;
		
		/*Iterator<WorkPlanMission> i1=wpip.getWorkPlanMissionsForCreatePlanId().iterator();
		
		while(i1.hasNext())
		{
			IBaseDTO dto=new DynaBeanDTO();
			WorkPlanMission wpm=i1.next();
			dto.setObject(wpm);
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			dto.set("planId", wpm.getWorkPlanInfoByPlanId().getId());
			res.add(dto);
		}*/
		Iterator<WorkPlanMission> i2=wpip.getWorkPlanMissionsForPlanId().iterator();
		while(i2.hasNext())
		{
			IBaseDTO dto=new DynaBeanDTO();
			WorkPlanMission wpm=i2.next();
			dto.setObject(wpm);
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planId", wpm.getWorkPlanInfoByPlanId().getId());
			res.add(dto);
		}
		return res;
	}

	public List<LabelValueBean> getPlanDomain(UserInfo ui) {
		// TODO Auto-generated method stub
		List<LabelValueBean> r=new ArrayList<LabelValueBean>();
		LabelValueBean lvb=new LabelValueBean();
		lvb.setLabel("个人计划");
		lvb.setValue(ui.getUserName());
		r.add(lvb);
		List<String> deps=ui.getDeps();
		if(deps==null)
		return r;
		Iterator<String> i=deps.iterator();
		while(i.hasNext())
		{
			String dep=i.next();
			String value=depT.getvaluebyId(dep);
			LabelValueBean lvb1=new LabelValueBean();
			lvb1.setLabel(value);
			lvb1.setValue(dep);
			r.add(lvb1);
		}
		return r;
	}

	public List<LabelValueBean> getSubDep(String planId) {
		// TODO Auto-generated method stub
		WorkPlanInfo wpi=(WorkPlanInfo)dao.loadEntity(WorkPlanInfo.class,planId);
		String dep=wpi.getPlanDomainType();
		List<LabelValueBean> l=new ArrayList<LabelValueBean>();
		List<LabelValueBean> a=ds.getDepListByDep(dep);
		List<LabelValueBean> b=ds.getUserListByDep(dep);
		if(a==null&&b==null)
		{
			LabelValueBean lvb=new LabelValueBean();
			
			lvb.setLabel("个人任务");
			lvb.setValue(dep);
			l.add(lvb);
			return l;
		}
		l.addAll(a);
		l.addAll(b);
		return l;
	}
	

	public DepartmentService getDs() {
		return ds;
	}

	public void setDs(DepartmentService ds) {
		this.ds = ds;
	}

	public List<IBaseDTO> getMyMission(UserInfo ui) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.myMission(ui));
		for(int i=0,size=o.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanMission wpm=(WorkPlanMission)o[i];
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			else
				dto.set("planName","");
			dto.set("missionInfo",StringUtil.ellipsis(wpm.getMissionInfo(), 25, "···"));
			dto.set("missionComplete",StringUtil.ellipsis(wpm.getMissionComplete(), 25, "···"));
			l.add(dto);
		}
		return l;
	}
	public List<IBaseDTO> getMyMission(UserInfo ui,int num) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.myMission(ui));
		for(int i=0,size=o.length;i<size&&i<num;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanMission wpm=(WorkPlanMission)o[i];
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			else
				dto.set("planName","");
			dto.set("missionInfo",StringUtil.ellipsis(wpm.getMissionInfo(), 25, "···"));
			dto.set("missionComplete",StringUtil.ellipsis(wpm.getMissionComplete(), 25, "···"));
			l.add(dto);
		}
		return l;
	}

	public List<IBaseDTO> getMyPlan(UserInfo ui) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.myPlan(ui));
		for(int i=0,size=o.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanInfo wpi=(WorkPlanInfo)o[i];
			String planDomain=depT.getvaluebyId(wpi.getPlanDomainType());
			if(planDomain.equals(""))
			dto.set("planDomain",wpi.getPlanDomainType());
			else
				dto.set("planDomain",planDomain);
			l.add(dto);
		}
		return l;
	}

	public List<IBaseDTO> getSubPlan(String id) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.getSubPlan(id));
		for(int i=0,size=o.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanInfo wpi=(WorkPlanInfo)o[i];
			String planDomain=depT.getvaluebyId(wpi.getPlanDomainType());
			if(planDomain.equals(""))
			dto.set("planDomain",wpi.getPlanDomainType());
			else
				dto.set("planDomain",planDomain);
			l.add(dto);
		}
		return l;
		
	}

	public List<IBaseDTO> getMyPlan(UserInfo ui, int num) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.myPlan(ui));
		for(int i=0,size=o.length;i<size&&i<num;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanInfo wpi=(WorkPlanInfo)o[i];
			String planDomain=depT.getvaluebyId(wpi.getPlanDomainType());
			if(planDomain.equals(""))
			dto.set("planDomain",wpi.getPlanDomainType());
			else
				dto.set("planDomain",planDomain);
			l.add(dto);
		}
		return l;
	}

	public List<IBaseDTO> findMission(IBaseDTO dto1,PageInfo pi) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		List<String> temp=null;
		String user=(String)dto1.get("user");
		String begin=(String)dto1.get("planBeignTime");
		String end=(String)dto1.get("planEndTime");
		String begin1=(String)dto1.get("planBeignTime1");
		String end1=(String)dto1.get("planEndTime1");
		String name=(String)dto1.get("planTitle");
		String key=(String)dto1.get("planSubhead");
		String type=(String)dto1.get("missionType");
		String missionSign=(String)dto1.get("missionSign");
		String missionInfo=(String)dto1.get("missionInfo");
		String missionComplete=(String)dto1.get("missionComplete");
		String orderbyasc=pi.getFieldAsc();
		String orderbydesc=pi.getFieldDesc();
		if(!"".equals(user))
		{
			temp=new ArrayList<String>();
		//temp.addAll(ds.getDepartments(user));
		temp.add(user);
		}
		Object[] o=dao.findEntity(wph.mission(temp,name,key,type,begin,end,begin1,end1,pi,missionSign,missionInfo,missionComplete,orderbyasc,orderbydesc));
		missionSize=dao.findEntitySize(wph.mission(temp,name,key,type,begin,end,begin1,end1,pi,missionSign,missionInfo,missionComplete,orderbyasc,orderbydesc));
		for(int i=0,size=o.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanMission wpm=(WorkPlanMission)o[i];
			//wpm.setMissionInfo(StringUtil.ellipsis(wpm.getMissionInfo(), 12, "***"));
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			else
				dto.set("planName","");
			dto.set("missionInfo",StringUtil.ellipsis(wpm.getMissionInfo(), 25, "···"));
			dto.set("missionComplete",StringUtil.ellipsis(wpm.getMissionComplete(), 25, "···"));
			l.add(dto);
		}
		return l;
	}

	public List<IBaseDTO> findPlan(IBaseDTO dto1,PageInfo pi) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		String user=(String)dto1.get("user");
		String begin=(String)dto1.get("planBeignTime");
		String end=(String)dto1.get("planEndTime");
		String begin1=(String)dto1.get("planBeignTime1");
		String end1=(String)dto1.get("planEndTime1");
		String title=(String)dto1.get("planTitle");
		String subhead=(String)dto1.get("planSubhead");
		String checkId=(String)dto1.get("checkId");
		Object[] o=dao.findEntity(wph.plan(user,title,subhead,begin,end,begin1,end1,pi,checkId));
		planSize=dao.findEntitySize(wph.plan(user,title,subhead,begin,end,begin1,end1,pi,checkId));
		for(int i=0,size=o.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanInfo wpi=(WorkPlanInfo)o[i];
			String planDomain=depT.getvaluebyId(wpi.getPlanDomainType());
			if(planDomain.equals(""))
			dto.set("planDomain",wpi.getPlanDomainType());
			else
				dto.set("planDomain",planDomain);
			if(wpi.getCheckId().equals("0"))
				dto.set("checkName","没有发布");
			if(wpi.getCheckId().equals("1"))
				dto.set("checkName","正在发布");
			if(wpi.getCheckId().equals("2"))
				dto.set("checkName","待审核");
			l.add(dto);
		}
		return l;
	}

	public int findMissionSize(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return missionSize;
	}

	public int findPlanSize(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return planSize;
	}

	public String getPlanId(UserInfo user) {
		// TODO Auto-generated method stub
		//List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.myPlan(user));
		if(o.length==0)
			return "";
			WorkPlanInfo wpi=(WorkPlanInfo)o[0];
		return wpi.getId();
		
	}
}
