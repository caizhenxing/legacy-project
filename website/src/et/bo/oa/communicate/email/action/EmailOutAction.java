/**
 * 	@(#)EmailOutAction.java   Sep 8, 2006 10:26:03 AM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.email.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import et.bo.oa.communicate.email.service.EmailService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * 外部邮件操作
 * @author zhang
 * @version Sep 8, 2006
 * @see
 */
public class EmailOutAction extends BaseAction{
	
	private EmailService emailOutService = null;

	public EmailService getEmailOutService() {
		return emailOutService;
	}

	public void setEmailOutService(EmailService emailOutService) {
		this.emailOutService = emailOutService;
	}

	/**
	 * @describe 跳转到main页面
	 * @param map 类型  ActionMapping
	 * @param form 类型  ActionForm
	 * @param request 类型  HttpServletRequest
	 * @param response 类型  HttpServletResponse
	 * @return 类型  ActionForward
	 * 
	 */
    public ActionForward toEmailMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return map.findForward("main");
    }
    
	/**
	 * @describe 外部邮件上传
	 * @param map 类型  ActionMapping
	 * @param form 类型  ActionForm
	 * @param request 类型  HttpServletRequest
	 * @param response 类型  HttpServletResponse
	 * @return 类型  ActionForward
	 * 
	 */
    public ActionForward upload(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return map.findForward("upload");
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
    public ActionForward toEmailQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
    	String userkey = ui.getUserName();
    	request.setAttribute("list", emailOutService.getEmailBoxList(userkey));
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
    public ActionForward toEmailLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        //邮件附件
        List l = null;
		if (request.getSession().getAttribute("outUploadList")==null) {
			l = null;
		}else{
			l = (List)request.getSession().getAttribute("outUploadList");
		}
        if (type.equals("take")) {
        	String id = request.getParameter("emailid");
        	request.getSession().setAttribute("emailboxid", id);
        	formdto.set("emailboxid", id);
        	request.getSession().setAttribute("emailboxid", id);
        	emailOutService.saveEmailToAddresser(formdto, l, "take");
            ActionForward actionforward = new ActionForward(
            "/oa/communicate/outemail.do?method=toEmailList&type=getBox&pagestop=pagestop");
            return actionforward;
		}
        if (type.equals("insert")) {
            formdto.set("sendUser",ui.getUserName());
            return map.findForward("load");
        }
        if (type.equals("see")) {
            String id = request.getParameter("id");
            IBaseDTO dto=emailOutService.getInEmailInfo(id);
            
			request.setAttribute("adjunctList", (List) dto.get("adjunctInfo"));
			request.setAttribute(map.getName(), dto);
            return map.findForward("see");
        }
        if (type.equals("answer")) {
            String id = request.getParameter("id");
            IBaseDTO dto=emailOutService.getInEmailInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("answer");
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
    public ActionForward toEmailList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
        formdto.set("sendUser",ui.getUserName());
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("outemailpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(13);
        
        if (request.getSession().getAttribute("emailboxid")==null) {
			
		}else{
			formdto.set("emailboxid",request.getSession().getAttribute("emailboxid"));
		}
        String type = "";
        if (request.getSession().getAttribute("outmailtype")==null) {
        	type = request.getParameter("type").toString();
		}else{
			type = request.getSession().getAttribute("outmailtype").toString();
		}
        request.getSession().setAttribute("outmailtype", type);
        List l = null;
        l = emailOutService.emailListIndex(formdto,pageInfo,type);
        int size = emailOutService.getEmailIndexSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("outemailpageTurning",pt);
        //request.setAttribute("list",agroMachineService.findArgoMachineInfo(argoSearch.searchHirerOperInfo(qldto)));
        
        //清session，存地址的list
        request.getSession().removeAttribute("outUploadList");
        return map.findForward("list");
    }
    
	/**
	 * @describe 操作邮件信息
	 * @param map 类型  ActionMapping
	 * @param form 类型  ActionForm
	 * @param request 类型  HttpServletRequest
	 * @param response 类型  HttpServletResponse
	 * @return 类型  ActionForward
	 * 
	 */
    public ActionForward operEmail(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DynaActionFormDTO formdto = (DynaActionFormDTO)form;
    	String type = request.getParameter("type");
        List l = null;
		if (request.getSession().getAttribute("outUploadList")==null) {
			l = null;
		}else{
			l = (List)request.getSession().getAttribute("outUploadList");
		}
    	request.setAttribute("opertype",type);
    	//保存到草稿箱
    	if (type.equals("savedraft")) {
    		emailOutService.saveEmailToDraft(formdto,l);
            request.getSession().removeAttribute("uploadList");
            request.setAttribute("idus_state","oa.communicate.email.inaction.savedraft");
            return map.findForward("load");
		}
    	//发送邮件
    	if (type.equals("send")) {
    		String id = request.getSession().getAttribute("emailboxid").toString();
    		formdto.set("emailboxid", id);
    		emailOutService.saveEmailToAddresser(formdto, l, "send");
			request.setAttribute("idus_state","oa.communicate.email.inaction.sendsuccess");
            return map.findForward("load");
		}
        if (type.equals("delete")) {
            String[] str = formdto.getStrings("cchk");
            ActionMessages errors = new ActionMessages();
            if (str.length == 0) {
                errors.add("nullselect", new ActionMessage(
                        "agrofront.email.emailLoad.nullselect"));
                saveErrors(request, errors);
                return map.findForward("error");
            }
            emailOutService.delEmailToDustbin(str);
            request.setAttribute("idus_state","oa.communicate.email.inaction.putdel");
            ActionForward actionforward = new ActionForward(
            "/oa/communicate/outemail.do?method=toEmailList&type="+request.getSession().getAttribute("outmailtype")+"&pagestop=pagestop");
            return actionforward;
        }
        if (type.equals("deleteForever")) {
            String[] str = formdto.getStrings("cchk");
            ActionMessages errors = new ActionMessages();
            if (str.length == 0) {
                errors.add("nullselect", new ActionMessage(
                        "agrofront.email.emailLoad.nullselect"));
                saveErrors(request, errors);
                return map.findForward("error");
            }
            emailOutService.delEmailForever(str);
            request.setAttribute("idus_state","oa.communicate.email.inaction.delsuccess");
            ActionForward actionforward = new ActionForward(
            "/oa/communicate/outemail.do?method=toEmailList&type="+request.getSession().getAttribute("outmailtype")+"&pagestop=pagestop");
            return actionforward;
        }
    	return map.findForward("load");
    }
    
}
