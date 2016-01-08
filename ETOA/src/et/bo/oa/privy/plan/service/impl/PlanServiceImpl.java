package et.bo.oa.privy.plan.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.privy.plan.service.PlanService;
import et.bo.oa.workflow.service.OaWorkFlowService;
import et.bo.oa.workflow.service.WorkFlowInfo;
import et.bo.oa.workflow.service.WorkFlowService;
import et.po.PlanDetail;
import et.po.PlanInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;



public class PlanServiceImpl implements PlanService {

	private BaseDAO dao=null;
	private KeyService ks=null;
	private OaWorkFlowService oawfs=null;
	private WorkFlowService wfs=null;
	private ClassTreeService cts=null;
	private int planSize=0;
	private int planDetailSize=0;
	public List<IBaseDTO> listPlanDetail(String id) {
		// TODO Auto-generated method stub
		PlanHelp ph=new PlanHelp();
		List<IBaseDTO> re=new ArrayList<IBaseDTO>();
		PlanInfo pi=(PlanInfo)dao.loadEntity(PlanInfo.class,id);
		Set s=pi.getPlanDetails();
		Iterator i=s.iterator();
		//Object[] result=dao.findEntity(ph.createplanDetailInfo(pi));
		while(i.hasNext())
		{
			PlanDetail pd=(PlanDetail)i.next();
			IBaseDTO dto=new DynaBeanDTO();
			dto.set("id",pd.getId());
			
			dto.set("planSign",cts.getvaluebyId(pd.getPlanSign()));
			dto.set("beginDate",TimeUtil.getTheTimeStr(pd.getBeginTime()));
			dto.set("endDate",TimeUtil.getTheTimeStr(pd.getEndTime()));
			dto.set("planInfo",pd.getPlanInfo_1());
			re.add(dto);
		}
		planDetailSize=s.size();
		return re;
	}

	public int listPlanDetailSize(String id) {
		// TODO Auto-generated method stub
		return this.planDetailSize;
	}

	public List<IBaseDTO> listPlan(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		PlanHelp ph=new PlanHelp();
		List<IBaseDTO> re=new ArrayList<IBaseDTO>();
		Object[] result=dao.findEntity(ph.createplanInfo((String)dto.get("employeeId"),(String)dto.get("planTitle"),(String)dto.get("planType"),(String)dto.get("beginDate"),(String)dto.get("endDate"),pi));
		for(int i=0,size=result.length;i<size;i++)
		{
			PlanInfo pin=(PlanInfo)result[i];
			IBaseDTO dto1=new DynaBeanDTO();
			dto1.set("id",pin.getId());
			dto1.set("planTitle",pin.getPlanTitle());
			dto1.set("planType",cts.getvaluebyId(pin.getPlanType()));
			dto1.set("beginDate",TimeUtil.getTheTimeStr(pin.getBeginDate(),"yyyy-MM-dd"));
			dto1.set("endDate",TimeUtil.getTheTimeStr(pin.getEndDate(),"yyyy-MM-dd"));
			dto1.set("employeeId",pin.getEmployeeId());
			dto1.set("planDate",TimeUtil.getTheTimeStr(pin.getPlanDate(),"yyyy-MM-dd"));
			re.add(dto1);
		}
		planSize=dao.findEntitySize(ph.createplanInfo((String)dto.get("employeeId"),(String)dto.get("planTitle"),(String)dto.get("planType"),(String)dto.get("beginDate"),(String)dto.get("endDate"),pi));
		return re;
	}

	public int listPlanSize(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return this.planSize;
	}

	public void addPlan(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PlanInfo pi=new PlanInfo();
		pi.setBeginDate(TimeUtil.getTimeByStr((String)dto.get("beginDate"),"yyyy-MM-dd"));
		pi.setEmployeeId((String)dto.get("employeeId"));
		pi.setEndDate(TimeUtil.getTimeByStr((String)dto.get("endDate"),"yyyy-MM-dd"));
		pi.setId(ks.getNext("plan_info"));
		pi.setPlanDate(TimeUtil.getNowTime());
		pi.setPlanTitle((String)dto.get("planTitle"));
		pi.setPlanType((String)dto.get("planType"));
		pi.setRemark((String)dto.get("remark"));
		oawfs.createAndNext("6",pi.getEmployeeId(),(String)dto.get("approveMan"),null,pi.getId());
		
		dao.saveEntity(pi);
		
	}

	public void addPlanDetail(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PlanDetail pd=new PlanDetail();
		pd.setBeginTime(TimeUtil.getTimeByStr((String)dto.get("beginDate"),"yyyy-MM-dd"));
		pd.setEndTime(TimeUtil.getTimeByStr((String)dto.get("endDate"),"yyyy-MM-dd"));
		pd.setId(ks.getNext("plan_detail"));
		pd.setPlanInfo_1((String)dto.get("planInfo"));
		pd.setPlanSign((String)dto.get("planSign"));
		pd.setRemark((String)dto.get("remark"));
		String planId=(String)dto.get("planId");
		pd.setPlanInfo((PlanInfo)dao.loadEntity(PlanInfo.class,planId));
		pd.setCarryInfo((String)dto.get("carryInfo"));
		dao.saveEntity(pd);
	}

	public void deletePlan(String id) {
		// TODO Auto-generated method stub
		PlanInfo pi=(PlanInfo)dao.loadEntity(PlanInfo.class,id);
		PlanHelp ph=new PlanHelp();
		Set s=pi.getPlanDetails();
		Iterator i=s.iterator();
		//PlanInfo pi=(PlanInfo)dao.loadEntity(PlanInfo.class,id);
		//Object[] result=dao.findEntity(ph.createplanDetailInfo(pi));
		dao.removeEntity(pi);
		while(i.hasNext())
		{
			dao.removeEntity(i.next());
		}
	}

	public void deletePlanDetail(String id) {
		// TODO Auto-generated method stub
		PlanDetail pd=(PlanDetail)dao.loadEntity(PlanDetail.class,id);
		dao.removeEntity(pd);
	}

	public void updatePlan(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PlanInfo pi=(PlanInfo)dao.loadEntity(PlanInfo.class,(String)dto.get("id"));
		if(pi==null)
			return;
		pi.setBeginDate(TimeUtil.getTimeByStr((String)dto.get("beginDate"),"yyyy-MM-dd"));
		
		pi.setEndDate(TimeUtil.getTimeByStr((String)dto.get("endDate"),"yyyy-MM-dd"));
		
		
		pi.setPlanTitle((String)dto.get("planTitle"));
		pi.setPlanType((String)dto.get("planType"));
		pi.setRemark((String)dto.get("remark"));
		
		
		dao.updateEntity(pi);
	}

	
	public void checkPlan(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PlanInfo pi=(PlanInfo)dao.loadEntity(PlanInfo.class,(String)dto.get("id"));
		if(pi==null)
			return;
		String carryState=(String)dto.get("carryState");
		
		if(carryState.equals("to_end"))
		{
		pi.setApproveMan((String)dto.get("approveMan"));
		pi.setApproveTime(TimeUtil.getNowTime());
		pi.setFlowId((String)dto.get("flowId"));
		dao.updateEntity(pi);
		}
		
	}
	
	
	public void updatePlanDetail(IBaseDTO dto) {
		// TODO Auto-generated method stub
		PlanDetail pd=(PlanDetail)dao.loadEntity(PlanDetail.class,(String)dto.get("id"));
		pd.setBeginTime(TimeUtil.getTimeByStr((String)dto.get("beginDate"),"yyyy-MM-dd"));
		pd.setEndTime(TimeUtil.getTimeByStr((String)dto.get("endDate"),"yyyy-MM-dd"));
		pd.setCarryInfo((String)dto.get("carryInfo"));
		pd.setCarryState((String)dto.get("carryState"));
		pd.setPlanInfo_1((String)dto.get("planInfo"));
		pd.setPlanSign((String)dto.get("planSign"));
		pd.setRemark((String)dto.get("remark"));
		//pd.setPlanInfo((PlanInfo)dao.loadEntity(PlanInfo.class,(String)dto.get("planId")));
		dao.saveEntity(pd);
	}

	public IBaseDTO uniquePlan(String id) {
		// TODO Auto-generated method stub
		PlanInfo pin=(PlanInfo)dao.loadEntity(PlanInfo.class,id);
		IBaseDTO dto1=new DynaBeanDTO();
		dto1.set("id",pin.getId());
		dto1.set("planType",pin.getPlanType());
		dto1.set("beginDate",TimeUtil.getTheTimeStr(pin.getBeginDate(),"yyyy-MM-dd"));
		dto1.set("endDate",TimeUtil.getTheTimeStr(pin.getEndDate(),"yyyy-MM-dd"));
		dto1.set("employeeId",pin.getEmployeeId());
		dto1.set("planDate",TimeUtil.getTheTimeStr(pin.getPlanDate()));
		dto1.set("approveMan",pin.getApproveMan());
		dto1.set("approveTime",TimeUtil.getTheTimeStr(pin.getApproveTime()));
		dto1.set("remark",pin.getRemark());
		dto1.set("planTitle",pin.getPlanTitle());
		return dto1;
	}

	public IBaseDTO uniquePlanDetail(String id) {
		// TODO Auto-generated method stub
		PlanDetail pd=(PlanDetail)dao.loadEntity(PlanDetail.class,id);
		IBaseDTO dto=new DynaBeanDTO();
		dto.set("id",pd.getId());
		dto.set("carryInfo",pd.getCarryInfo());
		dto.set("carryState",pd.getCarryState());
		dto.set("carryUser",pd.getCarryUser());
		dto.set("planId",pd.getPlanInfo().getId());
		dto.set("planInfo",pd.getPlanInfo_1());
		dto.set("planSign",pd.getPlanSign());
		dto.set("beginDate",TimeUtil.getTheTimeStr(pd.getBeginTime(),"yyyy-MM-dd"));
		dto.set("endDate",TimeUtil.getTheTimeStr(pd.getEndTime(),"yyyy-MM-dd"));
		dto.set("remark",pd.getRemark());
		return dto;
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

	public List<IBaseDTO> listNow(String user,int size) {
		// TODO Auto-generated method stub
		PlanHelp ph=new PlanHelp();
		List<IBaseDTO> re=new ArrayList<IBaseDTO>();
		Object[] result=dao.findEntity(ph.createNow(user));
		for(int ii=0,size1=result.length;ii<size1;ii++)
		{
			if(size!=0&&ii>=size)
				break;
			PlanInfo pi=(PlanInfo)result[ii];
			IBaseDTO dto=new DynaBeanDTO();
			
			dto.set("id",pi.getId());
			dto.set("beginDate",TimeUtil.getTheTimeStr(pi.getBeginDate(),"yyyy-MM-dd"));
			dto.set("endDate",TimeUtil.getTheTimeStr(pi.getEndDate(),"yyyy-MM-dd"));
			dto.set("planTitle",pi.getPlanTitle());
			
			re.add(dto);
			
		}
		
		return re;
	}

	public List<IBaseDTO> listDetailNow(String planId) {
		// TODO Auto-generated method stub
		List<IBaseDTO> re=new ArrayList<IBaseDTO>();
		PlanInfo pi=(PlanInfo)dao.loadEntity(PlanInfo.class,planId);
		MyQuery mq=new MyQueryImpl();
		
		DetachedCriteria dc=DetachedCriteria.forClass(PlanDetail.class);
		
		dc.add(Restrictions.eq("planInfo",pi));
		
		dc.addOrder(Order.asc("beginTime"));
		
		mq.setDetachedCriteria(dc);
	
	Object[] temp=dao.findEntity(mq);
	//Object[] result=dao.findEntity(ph.createplanDetailInfo(pi));
	for(int i=0,size1=temp.length;i<size1;i++)
	{
		
		PlanDetail pd=(PlanDetail)temp[i];
		IBaseDTO dto=new DynaBeanDTO();
		dto.set("id",pd.getId());
		dto.set("beginDate",TimeUtil.getTheTimeStr(pd.getBeginTime(),"yyyy-MM-dd"));
		dto.set("endDate",TimeUtil.getTheTimeStr(pd.getEndTime(),"yyyy-MM-dd"));
		dto.set("planInfo",pd.getPlanInfo_1());
		dto.set("carryInfo",pd.getCarryInfo());
		re.add(dto);
	}
	return re;
	}

	public OaWorkFlowService getOawfs() {
		return oawfs;
	}

	public void setOawfs(OaWorkFlowService oawfs) {
		this.oawfs = oawfs;
	}

	public WorkFlowService getWfs() {
		return wfs;
	}

	public void setWfs(WorkFlowService wfs) {
		this.wfs = wfs;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	

	


}
