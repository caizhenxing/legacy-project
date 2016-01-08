package et.bo.sys.log.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



import et.bo.sys.log.service.LogService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class LogAction extends BaseAction {

	
	
	private LogService logService =null;
	private ClassTreeService cts=null;
	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}


	public ActionForward toMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		return mapping.findForward("main");
    }
	
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		List sl =cts.getLabelVaList("logType");//listValueService.getLabelValue("SysUser","userName","userId","«Î—°‘Ò");
		request.setAttribute("typeList",sl);
		return mapping.findForward("tosearch");
    }
	
	public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("logTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dform = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l =this.logService.listLog(dform,pageInfo);
        int size =this.logService.listLogSize(dform);
        pageInfo.setRowCount(size);
        pageInfo.setQl(dform);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"",mapping,request);
        request.getSession().setAttribute("logTurning",pt);
		return mapping.findForward("searchresult");		
    }
}
