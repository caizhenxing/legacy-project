package et.bo.sys.right.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.right.service.RightService;
import excellence.common.tree.TreeControl;
import excellence.common.tree.TreeControlI;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.action.BaseAction;


public class RightAction extends BaseAction {

	private RightService rs=null;
	public ActionForward user(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		/*
		TreeControl tcl=(TreeControl)request.getSession().getAttribute(SysStaticParameter.GRANT_TREE);
		String id=(String)request.getSession().getAttribute(SysStaticParameter.GRANT_ID);
		
		rs.impowerUser(id,tcl);
		return mapping.findForward("success");
		*/
		TreeService tree=(TreeService)request.getSession().getAttribute(SysStaticParameter.GRANT_TREE);
		String id=(String)request.getSession().getAttribute(SysStaticParameter.GRANT_ID);
		rs.impowerUser(id,tree);
		return mapping.findForward("success");
	}
	public ActionForward group(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		TreeService tree=(TreeService)request.getSession().getAttribute(SysStaticParameter.GRANT_TREE);
		String id=(String)request.getSession().getAttribute(SysStaticParameter.GRANT_ID);
		rs.impowerGroup(id,tree);
		return mapping.findForward("success");
	}
	
	public ActionForward loadGroup(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		String group=request.getParameter("group");
		//RightService rs
		TreeService tree=rs.loadGroupRight(group);
		request.getSession().setAttribute(SysStaticParameter.GRANT_TREE,tree);
		request.getSession().setAttribute(SysStaticParameter.GRANT_ID,group);
		return mapping.findForward("group");
	}
	public ActionForward loadUser(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {
		String user=request.getParameter("user");
		TreeService tree=rs.loadUserRight(user);
		request.getSession().setAttribute(SysStaticParameter.GRANT_TREE,tree);
		request.getSession().setAttribute(SysStaticParameter.GRANT_ID,user);
		return mapping.findForward("user");
	
	}
	public RightService getRs() {
		return rs;
	}
	public void setRs(RightService rs) {
		this.rs = rs;
	}
}
