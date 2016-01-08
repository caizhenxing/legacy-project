package et.bo.caseinfo.casetype.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import et.bo.caseinfo.casetype.service.CaseTypeService;
import excellence.framework.base.action.BaseAction;


public class CaseTypeAction extends BaseAction{
	private CaseTypeService cs = null;
	
	public ActionForward toCaseTypeMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception	{
		List list= cs.loadBigType();
		request.setAttribute("bigtypelist", list);
		return map.findForward("Main");
	}	

	public CaseTypeService getCs() {
		return cs;
	}

	public void setCs(CaseTypeService cs) {
		this.cs = cs;
	}

	

}
