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
import java.util.Set;

//import net.sf.hibernate.collection.Set;

import org.apache.struts.util.LabelValueBean;

//import sun.security.krb5.internal.crypto.s;

import et.bo.oa.auditingClass.auditingclass;
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
		
		wpm.setPlanmissionSignDel("0");
		wpm.setMissionSign("2");
//		cts.getvaluebyNickName(arg0)
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
		
		
		wpi.setDelSign("0");
//		Set s = new Set();
		
		
		//wpi.setSysClasses((SysClasses)dao.loadEntity(SysClasses.class, (String)plan.get("sysClasses")));
		
		
		
		
		
		
		String planType = plan.get("planType").toString();
		String id = cts.getIdbyNickName("plan_auditing_no");

		
		

		
		wpi.setPlanType(planType);
		

		
		wpi.setCheckId(id);
		
		dto.setObject(wpi);
		
		

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
//			dto.set("missionInfo",StringUtil.ellipsis(wpm.getMissionInfo(), 25, "···"));
//			dto.set("missionComplete",StringUtil.ellipsis(wpm.getMissionComplete(), 25, "···"));
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
	
	
	/**
	 * 正在改动中.............
	 */
	public void updatePlan(IBaseDTO plan) {
		// TODO Auto-generated method stub
		String id=(String)plan.get("id");
		
		String auditingNo = (String)cts.getIdbyNickName("plan_auditing_no");
		String auditingYes = (String)cts.getIdbyNickName("plan_auditing_yes");
		String auditingNot = (String)cts.getIdbyNickName("plan_auditing_not");
		
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
		
		 

		if(auditingYes.equals(plan.get("checkId")))
		{
			Object[] o=dao.findEntity(wph.myPlan(wpi.getCreateUser()));
			for(int i=0,size=o.length;i<size;i++)
			{
				WorkPlanInfo wpi1=(WorkPlanInfo)o[i];
				wpi1.setCheckId(auditingNot);
				
				dao.saveEntity(wpi1);
			}
			wpi.setCheckId(auditingYes);
		}
		
		wpi.setPlanType(plan.get("planType").toString());
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

	
	/**
	 * 查找个人详细计划 页面名称mymissionlist.jsp
	 */
	public List<IBaseDTO> getMyMission(UserInfo ui) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.myMission(ui));
		WorkPlanInfo wpi=(WorkPlanInfo)o[0];
		Iterator i=wpi.getWorkPlanMissionsForPlanId().iterator();
		while(i.hasNext())
		{
			WorkPlanMission wpm=(WorkPlanMission)i.next();
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(wpm);
			
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			else
				dto.set("planName","");
			l.add(dto);
		}
		
		return l;
	}
	
	

	public List<IBaseDTO> getMyMyMyMission(UserInfo ui) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.mymymyMission(ui));
		for(int i=0,size=o.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanMission wpm=(WorkPlanMission)o[i];
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			else
				dto.set("planName","");

			l.add(dto);
		}
		return l;
	}
	
	public List<IBaseDTO> getMyMission(UserInfo ui,int num) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.myMission1(ui));
		for(int i=0,size=o.length;i<size&&i<num;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanMission wpm=(WorkPlanMission)o[i];
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			else
				dto.set("planName","");

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

			l.add(dto);
		}
		return l;
	}
	
	public List<IBaseDTO> findMissionpage(IBaseDTO dto1,PageInfo pi) {
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

			l.add(dto);
		}
		return l;
	}


	
/**
 * 当前正在修改的查找的位子
 */
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
		String planTimeType = (String)dto1.get("planTimeType");
		
		
		
		String auditingNo = (String)cts.getIdbyNickName("plan_auditing_no");
		String auditingYes = (String)cts.getIdbyNickName("plan_auditing_yes");
		String auditingNot = (String)cts.getIdbyNickName("plan_auditing_not");
		
		
		Object[] o=dao.findEntity(wph.plan(user,title,subhead,begin,end,begin1,end1,pi,checkId,planTimeType));
		planSize=dao.findEntitySize(wph.plan(user,title,subhead,begin,end,begin1,end1,pi,checkId,planTimeType));
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
			if(wpi.getCheckId().equals(auditingNot))
				dto.set("checkName","没有发布");
			if(wpi.getCheckId().equals(auditingYes))
				dto.set("checkName","正在发布");
			if(wpi.getCheckId().equals(auditingNo))
				dto.set("checkName","待审核");
			l.add(dto);
		}
		return l;
	}

		
	/**
	 * 更新当前标记
	 */	
	public boolean myupdateplan(String linkType,String id,IBaseDTO dto)
		{
			
			WorkPlanInfo u = (WorkPlanInfo)dao.loadEntity(WorkPlanInfo.class, dto.get("id").toString());
						
			String str = null;					
			
			if(linkType.equals("true"))
			{
				str="1";
			}
			else
			{
				
				str="0";
			}
						
			u.setDelSign(str);
			
			dao.updateEntity(u);

			return true; 
			
	}
	
	
	public int findMissionSize(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return missionSize;
	}

	public int findPlanSize(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return planSize;
	}

	
	public IBaseDTO getPlan(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto=new DynaBeanDTO();
		Object wpi=dao.loadEntity(WorkPlanInfo.class,id);
		dto.setObject(wpi);
		return dto;
		
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

	public boolean myMissionSing( String id, IBaseDTO dto,String checktype) {
		// TODO Auto-generated method stub
		WorkPlanMission u = (WorkPlanMission)dao.loadEntity(WorkPlanMission.class, dto.get("id").toString());
		
//		
		String str = null;
		
		
		
		if(checktype.equals("del"))
		{
			str="1";
		}
		else
		{
			str="0";
		}
	
		u.setPlanmissionSignDel(str);
		
		dao.updateEntity(u);

		return true; 
	}
	
	
	/**
	 * 查找个人详细计划 页面名称mymissionlist.jsp
	 */
	public List<IBaseDTO> getMyMyMission(UserInfo ui) {
		// TODO Auto-generated method stub
		List<IBaseDTO> l=new ArrayList<IBaseDTO>();
		Object[] o=dao.findEntity(wph.mymyMission(ui));
		for(int i=0,size=o.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.setObject(o[i]);
			WorkPlanMission wpm=(WorkPlanMission)o[i];
			if(wpm.getWorkPlanInfoByPlanId()!=null)
			dto.set("planName",wpm.getWorkPlanInfoByPlanId().getPlanTitle());
			else
				dto.set("planName","");
		
			l.add(dto);
		}
		return l;
	}
	
	
	public List<IBaseDTO> missionPlanpage(IBaseDTO dto1,PageInfo pi) {
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
		String planTimeType = (String)dto1.get("planTimeType");
		
		
		
		String auditingNo = (String)cts.getIdbyNickName("plan_auditing_no");
		String auditingYes = (String)cts.getIdbyNickName("plan_auditing_yes");
		String auditingNot = (String)cts.getIdbyNickName("plan_auditing_not");
		
		Object[] o=dao.findEntity(wph.mymissionpage(user,title,subhead,begin,end,begin1,end1,pi,checkId,planTimeType));
		planSize=dao.findEntitySize(wph.mymissionpage(user,title,subhead,begin,end,begin1,end1,pi,checkId,planTimeType));
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
			if(wpi.getCheckId().equals(auditingNot))
				dto.set("checkName","没有发布");
			if(wpi.getCheckId().equals(auditingYes))
				dto.set("checkName","正在发布");
			if(wpi.getCheckId().equals(auditingNo))
				dto.set("checkName","待审核");
			l.add(dto);
		}
		return l;
	}

	
}
