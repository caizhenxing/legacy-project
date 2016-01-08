package et.bo.sys.module.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.module.service.ModuleService;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;

public class ModuleAction extends BaseAction {

	private ModuleService ms = null;

	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		if (type != null && type.equals("insert")) {
			IBaseDTO dto = (IBaseDTO) form;
			String id = request.getParameter("parentId");
			request.setAttribute("parentId", id);
			return mapping.findForward("insert");
		}
		String id = request.getParameter("id");

		String name = mapping.getName();
		IBaseDTO dto = ms.getModule(id);
		request.setAttribute(name, dto);
		return mapping.findForward("info");
	}

	public ActionForward loadTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String operflag=(String)request.getAttribute("operflag");
		if(operflag!=null)
		{
			request.setAttribute("operflag",operflag);
		}
		TreeControlI tci = ms.loadModules();
		request.getSession()
				.setAttribute("moduleSession", tci.getTreeControl());
		return mapping.findForward("main");
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IBaseDTO aform = (IBaseDTO) form;
		String id = (String) aform.get("id");
		try {
			ms.updateModule(aform);
			request.setAttribute(mapping.getName(), aform);
			request.setAttribute("operflag", "sys.clew.success");
		} catch (Exception e) {
			request.setAttribute("operflag", "sys.clew.error");
		}
		return mapping.findForward("info");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// IBaseDTO aform=(IBaseDTO) form;
		String id = request.getParameter("id");
		try {
			ms.removeModule(id);
			request.setAttribute("operflag", "sys.clew.success");
		} catch (Exception e) {
			request.setAttribute("operflag", "sys.clew.error");
		}
		return mapping.findForward("delete");
	}

	public ActionForward todelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// IBaseDTO aform=(IBaseDTO) form;
		String id = request.getParameter("id");
		request.setAttribute("id", id);

		return mapping.findForward("todelete");
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IBaseDTO aform = (IBaseDTO) form;
		try {
			ms.addModule(aform);
			request.setAttribute("operflag", "sys.clew.success");
		} catch (Exception e) {
			request.setAttribute("operflag", "sys.clew.error");
			e.printStackTrace();
		}

		return mapping.findForward("insertsuccess");
	}

	public ModuleService getMs() {
		return ms;
	}

	public void setMs(ModuleService ms) {
		this.ms = ms;
	}
}
