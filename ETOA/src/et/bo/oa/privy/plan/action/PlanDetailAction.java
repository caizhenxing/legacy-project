package et.bo.oa.privy.plan.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.privy.plan.service.PlanService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


public class PlanDetailAction extends BaseAction {

	private PlanService ps=null;
	private ClassTreeService cts=null;
	public ActionForward list(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String id=request.getParameter("id");
        List l =ps.listPlanDetail(id);
        int size = ps.listPlanDetailSize(id);
        
        request.setAttribute("list",l);
       
        return mapping.findForward("detaillist");
    }
	
	public ActionForward info(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String type = request.getParameter("type");
		String planId=request.getParameter("id");
        request.setAttribute("type",type);
        IBaseDTO dto1=new DynaBeanDTO();
        dto1.set("planId",planId);
        request.setAttribute(mapping.getName(),dto1);
        List l=cts.getLabelVaList("planType");
		request.setAttribute("tl",l);
        if (type.equals("insert")) {
        	
            return mapping.findForward("detailinfo");
        }
       
        String id = request.getParameter("id");
        IBaseDTO dto=ps.uniquePlanDetail(id);

        request.setAttribute(mapping.getName(),dto);
        return mapping.findForward("detailinfo");
    }
	public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		try
		{
		ps.updatePlanDetail(dto);
		}catch(Exception e)
		{
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
    }
	public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String id=request.getParameter("id");
		try
		{
		ps.deletePlanDetail(id);
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
		return mapping.findForward("delete");
    }
	public ActionForward insert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		try
		{
		ps.addPlanDetail(dto);
		}catch(Exception e)
		{
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
    }
	public ActionForward top(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String id=request.getParameter("id");
		try
		{
		List l=ps.listDetailNow(id);
		request.setAttribute("list",l);
		}catch(Exception e)
		{
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("top");
    }
	public PlanService getPs() {
		return ps;
	}
	public void setPs(PlanService ps) {
		this.ps = ps;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
}
