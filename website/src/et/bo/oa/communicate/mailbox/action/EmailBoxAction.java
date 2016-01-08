/**
 * 	@(#)EmailBoxAction.java   Aug 30, 2006 7:31:56 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.mailbox.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.communicate.mailbox.service.MailBoxService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Aug 30, 2006
 * @see
 */
public class EmailBoxAction extends BaseAction{
	
	private MailBoxService mailboxService = null;
	
	/**
	 * @describe 跳转到main页面
	 * @param map 类型  ActionMapping
	 * @param form 类型  ActionForm
	 * @param request 类型  HttpServletRequest
	 * @param response 类型  HttpServletResponse
	 * @return 类型  ActionForward
	 * 
	 */
    public ActionForward toEmailBoxMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//
    	return map.findForward("main");
    }
    
	/**
	 * @describe 跳转到query页面
	 * @param map 类型  ActionMapping
	 * @param form 类型  ActionForm
	 * @param request 类型  HttpServletRequest
	 * @param response 类型  HttpServletResponse
	 * @return 类型  ActionForward
	 * 
	 */
    public ActionForward toEmailBoxQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	return map.findForward("query");
    }
    
	/**
	 * @describe 跳转到load页面
	 * @param map 类型  ActionMapping
	 * @param form 类型  ActionForm
	 * @param request 类型  HttpServletRequest
	 * @param response 类型  HttpServletResponse
	 * @return 类型  ActionForward
	 * 
	 */
    public ActionForward toEmailBoxLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
            return map.findForward("load");
        }
        if (type.equals("update")) {
            String id = request.getParameter("id");
            IBaseDTO dto=mailboxService.getEmailBox(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            String id = request.getParameter("id");
            IBaseDTO dto=mailboxService.getEmailBox(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
        return map.findForward("load");
    }
    
	/**
	 * @describe 跳转到list页面
	 * @param map 类型  ActionMapping
	 * @param form 类型  ActionForm
	 * @param request 类型  HttpServletRequest
	 * @param response 类型  HttpServletResponse
	 * @return 类型  ActionForward
	 * 
	 */
    public ActionForward toEmailBoxList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("mailboxpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(5);
        List l = mailboxService.emailBoxIndex(formdto,pageInfo);
        int size = mailboxService.getEmailBoxSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("mailboxpageTurning",pt);
        return map.findForward("list");
    }
    
	/**
	 * @describe 执行增删改操作
	 * @param map 类型  ActionMapping
	 * @param form 类型  ActionForm
	 * @param request 类型  HttpServletRequest
	 * @param response 类型  HttpServletResponse
	 * @return 类型  ActionForward
	 * 
	 */
    public ActionForward operEmailBox(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
        formdto.set("userid", ui.getUserName());
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
        	//
        	mailboxService.addMailBox(formdto);
            request.setAttribute("idus_state","sys.savesuccess");
            return map.findForward("load");
        }
        if (type.equals("update")) {
        	mailboxService.updateMailBox(formdto);
            request.setAttribute("idus_state","sys.updatesuccess");
            return map.findForward("load");
        }
        if (type.equals("delete")) {
        	mailboxService.delMailBox(formdto);
            request.setAttribute("idus_state","sys.delsuccess");
            return map.findForward("load");
        }
        return map.findForward("load");
    }

	public MailBoxService getMailboxService() {
		return mailboxService;
	}

	public void setMailboxService(MailBoxService mailboxService) {
		this.mailboxService = mailboxService;
	}

}
