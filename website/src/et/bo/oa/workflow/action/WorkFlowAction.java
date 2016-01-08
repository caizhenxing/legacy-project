package et.bo.oa.workflow.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.workflow.service.WorkFlowInfo;
import et.bo.oa.workflow.service.WorkFlowService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.framework.base.action.BaseAction;



public class WorkFlowAction extends BaseAction {

	private WorkFlowService wfs=null;
	public WorkFlowService getWfs() {
		return wfs;
	}
	public void setWfs(WorkFlowService wfs) {
		this.wfs = wfs;
	}
	public ActionForward list(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		List l=wfs.getTaskList(ui.getRole(),ui.getUserName(),0);
		request.setAttribute("list",l);
		List t=wfs.getWorkFlows(ui.getRole(),ui.getUserName());
		request.setAttribute("wlist",t);
		return mapping.findForward("list");
		
    }
	public ActionForward load(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String id=request.getParameter("id");
		
		try
		{
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		wfs.operBegin(id,ui.getUserName());
		WorkFlowInfo wfi=wfs.getTask(id,ui.getUserName());
		String action=wfi.getAction();
		request.getSession().setAttribute(SysStaticParameter.WORKFLOW_IN_SESSION,wfi);
		
		request.getSession().setAttribute("flowId",id);
		return new ActionForward(action);
		}catch(Exception e)
		{
			return mapping.findForward("error");
		}
		
    }
	public ActionForward create(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String id=request.getParameter("id");
		
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		//wfs.operBegin(id,ui.getUserName());
		//WorkFlowInfo wfi=wfs.getTask(id,ui.getUserName());
		//String action=wfi.getAction();
		//request.getSession().setAttribute(Para.WORKFLOW_SESSION_NAME,wfi);
		
		//request.getSession().setAttribute("flowId",id);
	
		List l=wfs.getTaskList(ui.getRole(),ui.getUserName(),0);
		request.setAttribute("list",l);
		List t=wfs.getWorkFlows(ui.getRole(),ui.getUserName());
		request.setAttribute("wlist",t);
		return mapping.findForward("list");
    }
}
