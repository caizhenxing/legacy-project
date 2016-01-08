package et.bo.forum.userInfo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.common.UserList;
import et.bo.forum.forumList.service.ForumListService;
import et.bo.forum.userInfo.service.UserInfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class UserInfoAction extends BaseAction {
	    static Logger log = Logger.getLogger(UserInfoAction.class.getName());
	    
	    private UserInfoService userInfoService = null;

		/**
		 * @describe 帖子列表
		 */
		public ActionForward toUserInfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
	        String userId = ui.getUserName();
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("userInfopageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(5);
			List userlist = new ArrayList();
			try {
				userlist = userInfoService.getUserInfoList(userId, pageInfo);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int size = userInfoService.getSize();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("userlist", userlist);
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("userInfopageTurning", pt);
			return map.findForward("userInfoList");
	    }
		/**
		 * @describe 加载用户信息
		 */
		public ActionForward toUserInfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			String userId = (String)request.getParameter("userId");
			try {
				IBaseDTO dto = userInfoService.getUserInfo(userId);
				List list = new ArrayList();
				list.add(dto);
				request.setAttribute("user", list);
				request.setAttribute("userInfo", dto);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map.findForward("userInfoLoad");
			
		}
		/**
		 * @describe 删除用户信息
		 */
		public ActionForward toDelUser(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			String friendId = (String)request.getParameter("friendId");
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
	        String userId = ui.getUserName();
			try {
				userInfoService.delMyFriend(userId,friendId);
				return map.findForward("toDelUser");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map.findForward("toDelUser");		
		}

		public UserInfoService getUserInfoService() {
			return userInfoService;
		}



		public void setUserInfoService(UserInfoService userInfoService) {
			this.userInfoService = userInfoService;
		}
		
}
