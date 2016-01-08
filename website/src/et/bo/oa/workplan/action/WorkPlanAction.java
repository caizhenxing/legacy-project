/**
 * 	@(#)WorkPlanAction.java   2006-11-9 下午04:49:51
 *	 。 
 *	 
 */
package et.bo.oa.workplan.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.workplan.service.WorkPlanService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import et.bo.sys.user.service.UserService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author yifei zhao
 * @version 2006-11-9
 * @see
 */
public class WorkPlanAction extends BaseAction {

	private WorkPlanService wps=null;
	private ClassTreeService cts=null;
	private ClassTreeService depT=null;
	private UserService us=null;
	
	
	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward createPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		try
		{
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		dto.set("createUser",ui.getUserName());
		dto.set("planDomainType",ui.getUserName());
		dto.set("createTime",TimeUtil.getNowTimeSr());
		IBaseDTO dto1=wps.createPlan(dto);
		request.setAttribute("WorkPlanBean", dto1);
		request.setAttribute("list",new ArrayList());
		//UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		List planList=wps.getPlanListByUseful(ui.getDep());
		List planDomainList=wps.getPlanDomain(ui);
		List planTimeList=cts.getLabelVaList("planTimeList");
		List planViewList=cts.getLabelVaList("planViewList");
		request.setAttribute("planList", planList);
		request.setAttribute("planDomainList", planDomainList);
		request.setAttribute("planTimeList", planTimeList);
		request.setAttribute("planViewList", planViewList);
		
		return mapping.findForward("planinfo");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward createMission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		dto.set("createTime",TimeUtil.getNowTimeSr());
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		dto.set("createUser",ui.getUserName());
		String type=(String)dto.get("type");
		try
		{
			String missionClasses=(String)dto.get("missionClasses");
			if(missionClasses.equals(""))
				dto.set("missionClasses",ui.getUserName());
		wps.createMission(dto);
		if("only".equals(type))
			return new ActionForward("/workmission.do?method=mymission");
		String planId=(String)dto.get("planId");
		List list=wps.getMissions(planId);
		IBaseDTO dto1=wps.getPlan(planId);
		request.setAttribute("WorkPlanBean", dto1);
		List planList=wps.getPlanListByUseful(ui.getDep());
		List planDomainList=cts.getLabelVaList("planType");
		List planTimeList=cts.getLabelVaList("planTimeList");
		List planViewList=cts.getLabelVaList("planViewList");
		request.setAttribute("planList", planList);
		request.setAttribute("planDomainList", planDomainList);
		request.setAttribute("planTimeList", planTimeList);
		request.setAttribute("planViewList", planViewList);
		request.setAttribute("listTitle","本计划任务");
		request.setAttribute("list",list);
		
		return mapping.findForward("planinfo");
		
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward updatePlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		String check=request.getParameter("check");
		if(check!=null)
			dto.set("check",check);
		try
		{
		wps.updatePlan(dto);
		String id=(String)dto.get("id");
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		IBaseDTO dto1=wps.getPlan(id);
		request.setAttribute("WorkPlanBean", dto1);
		request.setAttribute("list",new ArrayList());
		List list=wps.getMissions(id);
		List planList=wps.getPlanListByUseful(ui.getDep());
		List planDomainList=wps.getPlanDomain(ui);
		List planTimeList=cts.getLabelVaList("planTimeList");
		List planViewList=cts.getLabelVaList("planViewList");
		request.setAttribute("planList", planList);
		request.setAttribute("planDomainList", planDomainList);
		request.setAttribute("planTimeList", planTimeList);
		request.setAttribute("planViewList", planViewList);
		request.setAttribute("list",list);
		if("t".equals(check))
			return mapping.findForward("plancheckinfo");
		return mapping.findForward("planinfo");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward updateMission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		;
		try
		{
		wps.updateMission(dto);
		
		return mapping.findForward("success");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward delPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		try
		{
		wps.delPlan((String)dto.get("id"));
		return mapping.findForward("success");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		try
		{
		List l=wps.getActionPlan(dto);
		request.setAttribute("list",l);
		return mapping.findForward("list");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward topPlans(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO dto=(IBaseDTO)form;
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		try
		{
		List l=wps.getActionPlan(ui.getUserName());
		request.setAttribute("list",l);
		return mapping.findForward("topPlans");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward nowMission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		try
		{
		List l=wps.getMissionsByDate(ui.getUserName());
		request.setAttribute("list",l);
		return mapping.findForward("nowMission");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		List planList=wps.getPlanListByUseful(ui.getDep());
		List planDomainList=wps.getPlanDomain(ui);
		List planTimeList=cts.getLabelVaList("planTimeList");
		List planViewList=cts.getLabelVaList("planViewList");
		request.setAttribute("planList", planList);
		request.setAttribute("planDomainList", planDomainList);
		request.setAttribute("planTimeList", planTimeList);
		request.setAttribute("planViewList", planViewList);
		return mapping.findForward("add");
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward addmission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String target=null;
		String planId=(String)request.getParameter("planId");
		String type=(String)request.getParameter("type");
		IBaseDTO dto=new DynaBeanDTO();
		List depList=us.getAll();
		if(planId!=null)
		{
		dto.set("planId",planId);
		//depList=wps.getSubDep(planId);
		dto.set("type","plan");
		target="addMission";
		}
		if("only".equals(type))
		{
			dto.set("type",type);
			//depList=new ArrayList();
			target="addMissionOnly";
		}
		
		dto.set("beginTime",TimeUtil.getNowTime("yyyy-MM-dd"));
		dto.set("endTime",TimeUtil.getNowTime("yyyy-MM-dd"));
		String name="WorkMissionBean";
		request.setAttribute(name,dto);
		List priTypeList=cts.getLabelVaList("priType");
		List priList=cts.getLabelVaList("pri");
		request.setAttribute("depList", depList);
		request.setAttribute("priTypeList", priTypeList);
		request.setAttribute("priList", priList);
		List typeList=cts.getLabelVaList("planType");
		request.setAttribute("typeList", typeList);
		//request.setAttribute("planViewList", planViewList);
		return mapping.findForward(target);
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward changemission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String type=request.getParameter("type");
		String missionId=(String)request.getParameter("id");
		
		IBaseDTO dto=null;
		try {
			dto = wps.getMission(missionId);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute(mapping.getName(),dto);
		dto.set("type",type);
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		//List depList=wps.getSubDep((String)dto.get("planId"));
		List depList=us.getAll();
		List priTypeList=cts.getLabelVaList("priType");
		List priList=cts.getLabelVaList("pri");
		//List planViewList=cts.getLabelVaList("planViewList");
		request.setAttribute("depList", depList);
		request.setAttribute("priTypeList", priTypeList);
		request.setAttribute("priList", priList);
		List typeList=cts.getLabelVaList("planType");
		request.setAttribute("typeList", typeList);
		return mapping.findForward("updateMission");
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward changemissionlist(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO dto=(IBaseDTO)form;
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		try
		{
		
		String planId=(String)request.getParameter("planId");
		List list=wps.getParentMissions(planId);
		IBaseDTO dto1=wps.getPlan(planId);
		request.setAttribute("WorkPlanBean", dto1);
		List planList=wps.getPlanListByUseful(ui.getDep());
		List planDomainList=cts.getLabelVaList("planType");
		List planTimeList=cts.getLabelVaList("planTimeList");
		List planViewList=cts.getLabelVaList("planViewList");
		request.setAttribute("planList", planList);
		request.setAttribute("planDomainList", planDomainList);
		request.setAttribute("planTimeList", planTimeList);
		request.setAttribute("planViewList", planViewList);
		request.setAttribute("listTitle","上级计划任务");
		request.setAttribute("list",list);
		
		return mapping.findForward("planinfo");
		
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward mymission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO dto=(IBaseDTO)form;
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		try
		{
		
		List list=wps.getMyMission(ui);
		
		request.setAttribute("list",list);
		
		return mapping.findForward("mymissionlist");
		
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward myplan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO dto=(IBaseDTO)form;
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		//String id=request.getParameter("id");
		try
		{
		//UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String id=wps.getPlanId(ui);
		if("".equals(id))
			return mapping.findForward("content");
		IBaseDTO dto1=wps.getPlan(id);
		request.setAttribute("WorkPlanBean", dto1);
		request.setAttribute("list",new ArrayList());
		List list=wps.getMissions(id);
		List planList=wps.getPlanListByUseful(ui.getDep());
		List planDomainList=wps.getPlanDomain(ui);
		List planTimeList=cts.getLabelVaList("planTimeList");
		List planViewList=cts.getLabelVaList("planViewList");
		request.setAttribute("planList", planList);
		request.setAttribute("planDomainList", planDomainList);
		request.setAttribute("planTimeList", planTimeList);
		request.setAttribute("planViewList", planViewList);
		request.setAttribute("list",list);
		return mapping.findForward("planinfo");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward planInfo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String id=request.getParameter("id");
		String check=request.getParameter("check");
		try
		{
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		IBaseDTO dto1=wps.getPlan(id);
		request.setAttribute("WorkPlanBean", dto1);
		request.setAttribute("list",new ArrayList());
		List list=wps.getMissions(id);
		List planList=wps.getPlanListByUseful(ui.getDep());
		List planDomainList=wps.getPlanDomain(ui);
		List planTimeList=cts.getLabelVaList("planTimeList");
		List planViewList=cts.getLabelVaList("planViewList");
		request.setAttribute("planList", planList);
		request.setAttribute("planDomainList", planDomainList);
		request.setAttribute("planTimeList", planTimeList);
		request.setAttribute("planViewList", planViewList);
		request.setAttribute("list",list);
		if("t".equals(check))
			return mapping.findForward("plancheckinfo");
		return mapping.findForward("planinfo");
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward subPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO dto=(IBaseDTO)form;
		String id=request.getParameter("id");
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		try
		{
		
		List list=wps.getSubPlan(id);
		
		request.setAttribute("list",list);
		
		return mapping.findForward("myplanlist");
		
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto = (IBaseDTO) form;
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		dto.set("user",ui.getUserName());
		
		PageInfo pageInfo = null;
		/*String pageState = null;
		  pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
			pageInfo.setPageSize(8);
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("planinfoPageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (IBaseDTO) pageInfo.getQl();
		}*/
		pageInfo=PageInfo.operPara(request,"planinfoPageTurning",8,dto);
		if(pageInfo.getQl()!=null)
			dto=(IBaseDTO)pageInfo.getQl();
		String type=(String)dto.get("type");
		List l = null;
		int size=0 ;
		if(type.equals("mission"))
		{
		l=wps.findMission(dto, pageInfo);
		size=wps.findMissionSize(dto);
		}
		if(type.equals("plan"))
		{
		l=wps.findPlan(dto, pageInfo);
		size=wps.findPlanSize(dto);
		}
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", l);
		PageTurning pt = new PageTurning(pageInfo, "/ETOA/", mapping, request);
		request.getSession().setAttribute("planinfoPageTurning", pt);
		return mapping.findForward(type);
		
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward plancheckquery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto = (IBaseDTO) form;
		PageInfo pageInfo = null;
		/*String pageState = null;
		
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
			pageInfo.setPageSize(8);
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("planinfoPageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (IBaseDTO) pageInfo.getQl();
		}*/
		pageInfo=PageInfo.operPara(request,"planinfoPageTurning", 8, dto);
		//String type=(String)dto.get("type");
		if(pageInfo.getQl()!=null)
			dto=(IBaseDTO)pageInfo.getQl();
		List l = null;
		int size=0 ;
		dto.set("checkId","2");
		l=wps.findPlan(dto, pageInfo);
		size=wps.findPlanSize(dto);
		
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", l);
		PageTurning pt = new PageTurning(pageInfo, "/ETOA/", mapping, request);
		request.getSession().setAttribute("planinfoPageTurning", pt);
		return mapping.findForward("plancheck");
		
    }
	/**
	 * 
	 * @param
	 * @version 2006-11-9
	 * @return
	 */
	public ActionForward plan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String plan=request.getParameter("plan");
		if("day".equals(plan))
		{
			return mapping.findForward("queryday");
		}
		if("phase".equals(plan))
		{
			return mapping.findForward("queryphase");
		}
		if("pday".equals(plan))
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.set("type","mission");
			request.setAttribute(mapping.getName(),dto);
			List typeList=cts.getLabelVaList("planType");
			request.setAttribute("typeList", typeList);
			return mapping.findForward("pday");
		}
		if("pphase".equals(plan))
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.set("type","plan");
			request.setAttribute(mapping.getName(),dto);
			return mapping.findForward("pphase");
		}
		if("check".equals(plan))
		{
			IBaseDTO dto=new DynaBeanDTO();
			dto.set("type","plan");
			request.setAttribute(mapping.getName(),dto);
			return mapping.findForward("checkquery");
		}
		return mapping.findForward("error");
    }
	public WorkPlanService getWps() {
		return wps;
	}

	public void setWps(WorkPlanService wps) {
		this.wps = wps;
	}

	public ClassTreeService getDepT() {
		return depT;
	}

	public void setDepT(ClassTreeService depT) {
		this.depT = depT;
	}
}
