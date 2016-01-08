package et.bo.sys.department.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.department.service.DepartmentService;
import et.bo.sys.module.service.ModuleService;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;


public class DepartmentAction extends BaseAction {

	private DepartmentService ds=null;
	public ActionForward load(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String type=request.getParameter("type");
		if(type!=null&&type.equals("insert"))
		{
			IBaseDTO dto=(IBaseDTO)form;
			String id=request.getParameter("parentId");
			request.setAttribute("parentId",id);
			return mapping.findForward("insert");
		}
		String id=request.getParameter("id");
		
		String name=mapping.getName();
		IBaseDTO dto=ds.getDepartment(id);
		request.setAttribute(name,dto);
		return mapping.findForward("info");
    }
	public ActionForward loadTree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String operflag=(String)request.getAttribute("operflag");
		if(operflag!=null)
		{
			request.setAttribute("operflag",operflag);
		}
		try
		{
		TreeControlI tci=ds.loadDepartments();
		request.getSession().setAttribute("depSession",tci.getTreeControl());
		return mapping.findForward("main");
		}catch(Exception e)
		{
			e.printStackTrace();
			return mapping.findForward("error");
		}
    }
	public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO aform=(IBaseDTO) form;
		String id=(String) aform.get("id");
		try
		{
		ds.updateDepartment(aform);
		request.setAttribute(mapping.getName(),aform);
		request.setAttribute("operflag","sys.clew.success");
		}catch(Exception e)
		{
			request.setAttribute("operflag","sys.clew.error");
		}
		
		return mapping.findForward("info");
    }
	public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO aform=(IBaseDTO) form;
		String id=request.getParameter("id");
		try{
		ds.removeDepartment(id);
		request.setAttribute("operflag","sys.clew.success");
		}catch(Exception e)
		{
			request.setAttribute("operflag","sys.clew.error");
		}
		return mapping.findForward("delete");
    }
	public ActionForward todelete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO aform=(IBaseDTO) form;
		String id=request.getParameter("id");
		request.setAttribute("id",id);
		
		
		return mapping.findForward("todelete");
    }
	public ActionForward insert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO aform=(IBaseDTO) form;
		try
		{
			ds.addDepartment(aform);
			request.setAttribute("operflag","sys.clew.success");
		}catch(Exception e)
		{
			request.setAttribute("operflag","sys.clew.error");
		}
		
		return mapping.findForward("insertsuccess");
    }
	
	public DepartmentService getDs() {
		return ds;
	}
	public void setDs(DepartmentService ds) {
		this.ds = ds;
	}
}
