package et.bo.forum.userManager.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.userInfo.service.UserInfoService;
import et.bo.forum.userManager.service.UserManagerService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class UserManagerAction extends BaseAction {
	    static Logger log = Logger.getLogger(UserManagerAction.class.getName());
	    
	    private UserManagerService userManagerService = null;
	    
	    private UserInfoService userInfoService = null;

		/**
		 * @describe 前台模块查询列表
		 */
		public ActionForward toUserMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.removeAttribute(map.getName());
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("userListpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(5);
			List userlist = new ArrayList();
			try {
				userlist = userManagerService.userQuery(dto, pageInfo);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int size = userManagerService.getSize();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("userlist", userlist);
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("userListpageTurning", pt);
			return map.findForward("main");
		}
		
		/**
		 * @describe 删除用户信息
		 */
		public ActionForward toDelUser(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			String oper = (String)request.getParameter("oper");
//			String friendId = (String)request.getParameter("friendId");
//			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
//	        String userId = ui.getUserName();
	        String[] idArray = dto.getStrings("chkId");
			if(oper.equals("delselect")){
	        try {
				userManagerService.deleteUser(idArray);
				return map.findForward("toMain");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map.findForward("toMain");		
		    }
			return map.findForward("toMain");	
		}
		
		/**
		 * @describe 删除用户信息
		 */
		public ActionForward toLoadUser(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			String userId = (String)request.getParameter("userId");
			IBaseDTO dto = null;
			try {
				dto = userInfoService.getUserInfo(userId);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("id="+dto.get("id").toString());
			request.setAttribute("dto", dto);
			return map.findForward("toDetail");	
		}   
			
		public UserInfoService getUserInfoService() {
			return userInfoService;
		}

		public void setUserInfoService(UserInfoService userInfoService) {
			this.userInfoService = userInfoService;
		}

		public UserManagerService getUserManagerService() {
			return userManagerService;
		}
		public void setUserManagerService(UserManagerService userManagerService) {
			this.userManagerService = userManagerService;
		}
					
}
