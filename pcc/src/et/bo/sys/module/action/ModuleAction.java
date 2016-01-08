package et.bo.sys.module.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ocelot.common.tree.TreeControlI;
import ocelot.framework.base.action.BaseAction;
import ocelot.framework.base.dto.IBaseDTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.module.service.ModuleService;


public class ModuleAction extends BaseAction {

	private ModuleService ms=null;
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
		IBaseDTO dto=ms.getModule(id);
		request.setAttribute(name,dto);
		return mapping.findForward("info");
    }
	public ActionForward loadTree(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		TreeControlI tci=ms.loadModules();
		request.getSession().setAttribute("moduleSession",tci.getTreeControl());
		return mapping.findForward("main");
    }
	public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO aform=(IBaseDTO) form;
		String id=(String) aform.get("id");
		ms.updateModule(aform);
		request.setAttribute(mapping.getName(),aform);
		return mapping.findForward("info");
    }
	public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		//IBaseDTO aform=(IBaseDTO) form;
		String id=request.getParameter("id");
		ms.removeModule(id);
		
		return mapping.findForward("delete");
    }
	public ActionForward insert(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO aform=(IBaseDTO) form;
		try
		{
			ms.addModule(aform);
		}catch(Exception e)
		{
			e.printStackTrace();
			return mapping.findForward("error");
		}
		
		return mapping.findForward("success");
    }
	public ModuleService getMs() {
		return ms;
	}
	public void setMs(ModuleService ms) {
		this.ms = ms;
	}
}
