/**
 * 	@(#)SearchAction.java   Oct 30, 2006 5:53:39 PM
 *	 。 
 *	 
 */
package et.bo.pcc.phonesearch.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.pcc.phonesearch.service.SearchService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Oct 30, 2006
 * @see
 */
public class SearchAction extends BaseAction {
	
	private SearchService search = null;

	public SearchService getSearch() {
		return search;
	}

	public void setSearch(SearchService search) {
		this.search = search;
	}

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
	public ActionForward toSearchMain(ActionMapping map, ActionForm form,
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
	public ActionForward toSearchQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        UserInfo ui = (UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
        if(!ui.getRole().equals("operator")){
        	request.setAttribute("checkrole", "checkrole");
        }
		return map.findForward("query");
	}
	
	/**
	 * @describe 跳转到load页面
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
	public ActionForward toSearchLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);
        //增
		if (type.equals("insert")) {
			return map.findForward("load");
		}
		//改
		if (type.equals("update")) {
			String id = request.getParameter("id");
			IBaseDTO dto = search.getPhoneSearch(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//删
		if (type.equals("delete")) {
			String id = request.getParameter("id");
			IBaseDTO dto = search.getPhoneSearch(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//详细
		if (type.equals("see")) {
			String id = request.getParameter("id");
			IBaseDTO dto = search.getPhoneSearch(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("see");
		}
		return map.findForward("load");
	}
	
	/**
	 * @describe 跳转到list页面
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
	public ActionForward toSearchList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("searchpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        UserInfo ui = (UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
        if(!ui.getRole().equals("operator")){
        	request.setAttribute("checkrole", "checkrole");
        }
        pageInfo.setPageSize(5);
        List l = search.phoneSearch(formdto, pageInfo);
        int size = search.getPhoneSearchSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
        request.getSession().setAttribute("searchpageTurning",pt);
        return map.findForward("list");
	}
	
	/**
	 * @describe 招行添删改操作
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
    public ActionForward operSearch(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
        	search.addSearchInfo(formdto);
            request.setAttribute("idus_state","sys.addsuccess");
            return map.findForward("load");
        }
        if (type.equals("update")) {
        	search.upSearchInfo(formdto);
            request.setAttribute("idus_state","sys.updatesuccess");
            return map.findForward("load");
        }
        if (type.equals("delete")) {
        	search.delSearchInfo(formdto);
            request.setAttribute("idus_state","sys.delsuccess");
            return map.findForward("load");
        }
        return map.findForward("load");
    }
}
