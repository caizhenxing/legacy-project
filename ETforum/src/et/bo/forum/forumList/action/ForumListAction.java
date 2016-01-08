package et.bo.forum.forumList.action;

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
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class ForumListAction extends BaseAction {
	    static Logger log = Logger.getLogger(ForumListAction.class.getName());
	    
	    private ForumListService forumListService = null;
		/**
		 * @describe 前台模块查询列表
		 */
		public ActionForward toForumList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String moduleId = (String)request.getParameter("moduleId");
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
			//用户列表,在线人数
			String userId = "GUEST";
			int forumUserCount = 0;
			if(ui!=null){
	        	 userId = ui.getUserName();
	        }		
			System.out.println(userId);
			try {
				if(!userId.equals("GUEST")){
					UserList.setUser(userId, 1);
				}
				forumUserCount = UserList.getCount(0);
				request.setAttribute("forumUserCount", new Integer(forumUserCount));
			} catch (RuntimeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List userList = new ArrayList();
			userList = UserList.getUsers(1);
			request.setAttribute("userList", userList);
	        //模块列表
			HashMap hashmap = new HashMap();
			try {
				hashmap = forumListService.moduleQuery(moduleId);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        request.setAttribute("hashmap", hashmap);
			return map.findForward("list");
	    }
		/**
		 * @describe 帖子列表
		 */
		public ActionForward toPostList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			String moduleId = "";
	        if (request.getParameter("itemid") == null) {
	        	moduleId = (String)request.getSession().getAttribute("topicId");
	        } else {
	        	moduleId = request.getParameter("itemid");
	        }
	        request.setAttribute("itemid", moduleId);
			//以下为区域列表
//			List modulelist = new ArrayList();
//			try {
//				modulelist = forumListService.moduleQuery(moduleId);
//			} catch (RuntimeException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        request.setAttribute("modulelist", modulelist);
	        //以下为帖子列表
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
			request.getSession().setAttribute("topicId", moduleId);
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("postpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(5);
			List postlist = new ArrayList();
			try {
				postlist = forumListService.postListQuery(moduleId, pageInfo);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int size = forumListService.getSizeNum();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("postlist", postlist);
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("postpageTurning", pt);
			return map.findForward("postlist");
	    }
		
		/**
		 * @describe 跳转到回帖页
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
		public ActionForward toAnswerPostList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			String moduleId = "";
	        if (request.getParameter("itemid") == null) {
	        	moduleId = (String)request.getSession().getAttribute("topicId");
	        } else {
	        	moduleId = request.getParameter("itemid");
	        }
	        request.setAttribute("itemid", moduleId);
	        String postid = "";
	        if (request.getParameter("postid")==null) {
				postid = (String)request.getSession().getAttribute("postsId");
			}else{
				postid = request.getParameter("postid");
			}
	        request.setAttribute("postid", postid);
	        
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
			request.getSession().setAttribute("topicId", moduleId);
			request.getSession().setAttribute("postsId", postid);
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("answerpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(5);
			List postlist = forumListService.answerList(moduleId, postid, pageInfo);
			int size = forumListService.getAnswerNum();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("answerlist", postlist);
			request.setAttribute("posttitle", forumListService.getPostsTitle(postid));
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("answerpageTurning", pt);
			return map.findForward("answerlist");
		}
		
		public ForumListService getForumListService() {
			return forumListService;
		}
		public void setForumListService(ForumListService forumListService) {
			this.forumListService = forumListService;
		}
		
}
