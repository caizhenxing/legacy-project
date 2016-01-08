package et.bo.oa.privy.plan.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.privy.plan.service.PlanService;
import et.bo.oa.workflow.service.OaWorkFlowService;
import et.bo.oa.workflow.service.WorkFlowInfo;
import et.bo.oa.workflow.service.WorkFlowService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;

public class PlanAction extends BaseAction {

	private WorkFlowService wfs = null;

	private PlanService ps = null;

	private OaWorkFlowService oawfs = null;

	private ClassTreeService cts = null;

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		if (ui == null)
			return mapping.findForward("error");
		IBaseDTO dto = (IBaseDTO) form;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
			pageInfo.setPageSize(10);
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("planinfoPageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (IBaseDTO) pageInfo.getQl();
		}

		dto.set("employeeId", ui.getUserName());
		List l = ps.listPlan(dto, pageInfo);
		int size = ps.listPlanSize(dto);
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", l);
		PageTurning pt = new PageTurning(pageInfo, "/ETOA/", mapping, request);
		request.getSession().setAttribute("planinfoPageTurning", pt);
		return mapping.findForward("planlist");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List l = cts.getLabelVaList("planType");
		request.setAttribute("tl", l);
		return mapping.findForward("query");
	}

	public ActionForward info(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		List l = cts.getLabelVaList("planType");
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		request.setAttribute("tl", l);

		if (type.equals("check")) {
			WorkFlowInfo wfi = (WorkFlowInfo) request.getSession()
					.getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			String id = wfi.getMessage();
			IBaseDTO dto = ps.uniquePlan(id);

			request.setAttribute(mapping.getName(), dto);

			List ll = ps.listPlanDetail(id);

			request.setAttribute("list", ll);
			return mapping.findForward("check");
		}
		if (type.equals("insert")) {

			return mapping.findForward("planinfo");
		}
		if (type.equals("detail")) {
			String id = request.getParameter("id");
			IBaseDTO dto = ps.uniquePlan(id);

			request.setAttribute(mapping.getName(), dto);

		}
		if (type.equals("update")) {
			WorkFlowInfo wfi = (WorkFlowInfo) request.getSession()
					.getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			String id = wfi.getMessage();
			IBaseDTO dto = ps.uniquePlan(id);

			request.setAttribute(mapping.getName(), dto);

		}
		return mapping.findForward("planinfo");
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IBaseDTO dto = (IBaseDTO) form;
		try {
			ps.updatePlan(dto);
			UserInfo ui = (UserInfo) request.getSession().getAttribute(
					SysStaticParameter.USER_IN_SESSION);
			WorkFlowInfo wfi = (WorkFlowInfo) request.getSession()
					.getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			oawfs.nextStep(true, wfi.getId(), ui.getUserName(), null, null, wfi
					.getMessage());
			request.setAttribute("idus_state", "sys.updatesuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("planinfo");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		try {
			ps.deletePlan(id);
			request.setAttribute("idus_state", "sys.delsuccess");
		} catch (Exception e) {
			return mapping.findForward("error");
		}
		return mapping.findForward("planinfo");
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IBaseDTO dto = (IBaseDTO) form;
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		dto.set("employeeId", ui.getUserName());
		request.setAttribute("idus_state", "sys.addsuccess");
		try {
			ps.addPlan(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
	}

	public ActionForward top(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		List l = cts.getLabelVaList("planType");
		// UserInfo
		// ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		request.setAttribute("tl", l);
		String id = request.getParameter("id");
		IBaseDTO dto = ps.uniquePlan(id);

		request.setAttribute(mapping.getName(), dto);

		List ll = ps.listPlanDetail(id);

		request.setAttribute("list", ll);
		return mapping.findForward("top");
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IBaseDTO dto = (IBaseDTO) form;
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		try {
			dto.set("approveMan", ui.getUserName());
			WorkFlowInfo wfi = (WorkFlowInfo) request.getSession()
					.getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			oawfs.nextStep(true, wfi.getId(), ui.getUserName(), (String) dto
					.get("carryState"), null, wfi.getMessage());
			dto.set("flowId", wfi.getWfInsid());
			ps.checkPlan(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
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

	public WorkFlowService getWfs() {
		return wfs;
	}

	public void setWfs(WorkFlowService wfs) {
		this.wfs = wfs;
	}

	public OaWorkFlowService getOawfs() {
		return oawfs;
	}

	public void setOawfs(OaWorkFlowService oawfs) {
		this.oawfs = oawfs;
	}
}
