/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.commoninfo.leaveWord.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.commoninfo.leaveWord.service.LeaveWordService;
import et.bo.oa.privy.addressListSort.service.AddressListSortService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
/**
 * @describe 留言板action
 * @author   叶浦亮
 * @version 1.0, //
 * @see
 * @see
 */
public class LeaveWordAction extends BaseAction {
    static Logger log = Logger.getLogger(LeaveWordAction.class.getName());
    private LeaveWordService leaveWordService = null;
    
    /**
	 * @describe  测试
	 * @param
	 * @return
	 * 
	 */
    public ActionForward toTest(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	System.out.println("......1.......");
    	return map.findForward("ok");
    }
    /**
	 * @describe 类别主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toLeaveWordMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("......1.......");
		return map.findForward("main");
    }
	/**
	 * @describe 类别主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toSeeLeaveWordMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("......1.......");
		return map.findForward("seeMain");
    }
	/**
	 * @describe 个人类别主页面
	 * @param
	 * @return
	 * 
	 */
//	public ActionForward toPersonalAddressListSortMain(ActionMapping map, ActionForm form,
//            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		return map.findForward("personalMain");
//    }
	/**
	 * @describe 公共类别主页面
	 * @param
	 * @return
	 * 
	 */
//	public ActionForward toCommonAddressListSortMain(ActionMapping map, ActionForm form,
//            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		return map.findForward("commonMain");
//    }
	/**
	 * @describe 查询页
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toLeaveWordQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		String leaveType = request.getParameter("leaveType");
		request.setAttribute("leaveType",leaveType);
		return map.findForward("query");
    }
	/**
	 * @describe 类别信息管理Load
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toLeaveWordLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        String addressListSign = request.getParameter("addressListSign");
        request.setAttribute("addressListSign", addressListSign);
		if (type.equals("insert")) {
            return map.findForward("seeLoad");
        }
		if (type.equals("detail")) {
            String id = request.getParameter("id");
            System.out.println("id :"+id);
            IBaseDTO dto=leaveWordService.getLeaveWordInfo(id);   
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
            
        }
		if (type.equals("update")) {
            String id = request.getParameter("id");
            IBaseDTO dto=leaveWordService.getLeaveWordInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
		if (type.equals("delete")) {
            String id = request.getParameter("id");
            IBaseDTO dto=leaveWordService.getLeaveWordInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
		return map.findForward("load");
    }
	/**
	 * @describe 类别信息显示
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toLeaveWordList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
//        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
//		String userkey = ui.getUserName();
//		formdto.set("userId", userkey);
        if (pageState == null && page == null) {
            pageInfo = new PageInfo();
        } else {
            PageTurning pageTurning = (PageTurning) request.getSession()
                    .getAttribute("agropageTurning");
            pageInfo = pageTurning.getPage();
            if (pageState != null)
                pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO) pageInfo.getQl();
        }
        
        pageInfo.setPageSize(10);
        List l = leaveWordService.findLeaveWordInfo(formdto, pageInfo);
        int size = leaveWordService.getLeaveWordSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
        request.getSession().setAttribute("agropageTurning", pt);
        return map.findForward("list");
    }
	/**
	 * @describe 类别信息显示
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toSeeLeaveWordList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
//        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
//		String userkey = ui.getUserName();
//		formdto.set("userId", userkey);
        if (pageState == null && page == null) {
            pageInfo = new PageInfo();
        } else {
            PageTurning pageTurning = (PageTurning) request.getSession()
                    .getAttribute("agropageTurning");
            pageInfo = pageTurning.getPage();
            if (pageState != null)
                pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO) pageInfo.getQl();
        }
        
        pageInfo.setPageSize(10);
        List l = leaveWordService.findSeeLeaveWordInfo(formdto, pageInfo);
        int size = leaveWordService.getLeaveWordSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
        request.getSession().setAttribute("agropageTurning", pt);
        return map.findForward("seeList");
    }
	/**
	 * @describe 类别信息添加
	 * @param
	 * @return
	 * 
	 */
	public ActionForward operLeaveWord(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		String addressListSign = request.getParameter("addressListSign");
        request.setAttribute("addressListSign", addressListSign);
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		formdto.set("name", userkey);
		if (type.equals("insert")) {
        	formdto.set("userId", userkey);
//        	if(leaveWordService.isHaveSameName(formdto)==true){
//        		request.setAttribute("idus_state","sys.addfail.nameexists");
//            	return map.findForward("load");
//        	}
        	boolean flag = leaveWordService.addLeaveWordInfo(formdto);
            if(flag==false){
            	request.setAttribute("idus_state","sys.addfail.nameexists");
            	return map.findForward("seeLoad");
            }
            request.setAttribute("idus_state","sys.addsuccess");
//            List departLists = departTree.getLabelList("1");
//    		request.setAttribute("departList0",departLists);
//            request.setAttribute("userlist",listValueService.getLabelValue("SysUser", "userId", "userId", "deleteMark", "1"));
            return map.findForward("seeLoad");
        }
//		if (type.equals("update")) {
//			formdto.set("userId", userkey);
//            leaveWordService.updateLeaveWordInfo(formdto);
//            request.setAttribute("idus_state", "sys.updatesuccess");
//            return map.findForward("load");
//        }
		if (type.equals("delete")) {
			boolean flag=leaveWordService.deleteLeaveWordInfo(formdto);
			if(flag==false){
				request.setAttribute("idus_state","sys.addfail.nameexists");
            	return map.findForward("load");
			}
            request.setAttribute("idus_state", "sys.delsuccess");
            return map.findForward("load");
        }
		return map.findForward("load");
    }
	public LeaveWordService getLeaveWordService() {
		return leaveWordService;
	}
	public void setLeaveWordService(LeaveWordService leaveWordService) {
		this.leaveWordService = leaveWordService;
	}
	
	
	
}
