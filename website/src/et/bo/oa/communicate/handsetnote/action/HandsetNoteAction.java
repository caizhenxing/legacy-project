/**
 * 	@(#)HandsetNoteAction.java   Sep 26, 2006 1:47:59 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.handsetnote.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import et.bo.oa.communicate.handsetnote.service.HandsetNoteService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Sep 26, 2006
 * @see
 */
public class HandsetNoteAction extends BaseAction{
	
	private HandsetNoteService handsetNoteService = null;
	
	/**
	 * @describe 跳转到main页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toHandsetNoteMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("main");
	}
	
	/**
	 * @describe 跳转到query页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toHandsetNoteQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
    public ActionForward toHandsetNoteLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
            return map.findForward("load");
        }
        if (type.equals("update")) {
            String id = request.getParameter("id");
            IBaseDTO dto=handsetNoteService.getHandsetNoteInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            String id = request.getParameter("id");
            IBaseDTO dto = handsetNoteService.getHandsetNoteInfo(id);
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
    public ActionForward toHandsetNoteList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("handsetpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = handsetNoteService.handsetIndex(formdto, pageInfo);
        int size = handsetNoteService.getHandsetIndexSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("handsetpageTurning",pt);
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
    public ActionForward operHandSetNote(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("save")) {
        	handsetNoteService.saveHandsetNote(formdto, "save");
            request.setAttribute("idus_state","sys.savesuccess");
            return map.findForward("load");
        }
        if (type.equals("send")) {
        	handsetNoteService.saveHandsetNote(formdto, "send");
            request.setAttribute("idus_state","sys.savesuccess");
            return map.findForward("load");
		}
        if (type.equals("update")) {
        	handsetNoteService.updateHandsetNote(formdto);
            request.setAttribute("idus_state","sys.updatesuccess");
            return map.findForward("load");
        }
		if (type.equals("delete")) {
			String[] str = formdto.getStrings("chk");
			ActionMessages errors = new ActionMessages();
			if (str.length == 0) {
				errors.add("nullselect", new ActionMessage(
						"agrofront.email.emailLoad.nullselect"));
				saveErrors(request, errors);
				return map.findForward("error");
			}
			handsetNoteService.delHandsetNote(str);
			request.setAttribute("idus_state", "et.oa.communicate.handsetnote.handsetnotelist.delsuccess");
			ActionForward actionforward = new ActionForward(
					"/oa/communicate/handsetnote.do?method=toHandsetNoteList");
			return actionforward;
		}
		if (type.equals("deleteForever")) {
			String[] str = formdto.getStrings("chk");
			ActionMessages errors = new ActionMessages();
			if (str.length == 0) {
				errors.add("nullselect", new ActionMessage(
						"agrofront.email.emailLoad.nullselect"));
				saveErrors(request, errors);
				return map.findForward("error");
			}
			handsetNoteService.delHandsetNoteForever(str);
			request.setAttribute("idus_state", "et.oa.communicate.handsetnote.handsetnotelist.delsuccess");
			ActionForward actionforward = new ActionForward(
					"/oa/communicate/handsetnote.do?method=toHandsetNoteList");
			return actionforward;
		}
        return map.findForward("load");
    }
	

	public HandsetNoteService getHandsetNoteService() {
		return handsetNoteService;
	}

	public void setHandsetNoteService(HandsetNoteService handsetNoteService) {
		this.handsetNoteService = handsetNoteService;
	}
	
}
