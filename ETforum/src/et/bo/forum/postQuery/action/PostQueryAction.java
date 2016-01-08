package et.bo.forum.postQuery.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.postQuery.service.PostQueryService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class PostQueryAction extends BaseAction {
	    static Logger log = Logger.getLogger(PostQueryAction.class.getName());
	    
	    private PostQueryService postQueryService = null;
		/**
		 * @describe 我的发帖列表
		 */
		public ActionForward toMySendPostList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
	        String userId = ui.getUserName();
//			List list = new ArrayList();
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("mySendPostpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(10);
			List postlist =new ArrayList();
			try {
				postlist = postQueryService.mySendPost(userId, pageInfo);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int size = postQueryService.getSizeNum();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("postlist", postlist);
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("mySendPostpageTurning", pt);
//	        request.setAttribute("mySendPost", "我的发帖");
			return map.findForward("mySendPostList");
	    }
		/**
		 * @describe 我的回帖列表
		 */
		public ActionForward toMyAnswerPostList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
	        String userId = ui.getUserName();
//			List list = new ArrayList();
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("myAnswerPostpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(10);
			List postlist = new ArrayList();
			try {
				postlist = postQueryService.myAnswerPost(userId, pageInfo);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int size = postQueryService.getSizeNum();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("postlist", postlist);
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("myAnswerPostpageTurning", pt);
//	        request.setAttribute("myAnswerPost", "我的回帖");
			return map.findForward("myAnswerPostList");
	    }
		/**
		 * @describe 我的收藏列表
		 */
		public ActionForward toMySavePostList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
	        String userId = ui.getUserName();
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("mySavePostpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(10);
			List postlist = new ArrayList();
			try {
				postlist = postQueryService.mySavePost(userId, pageInfo);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int size = postQueryService.getSizeNum();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("postlist", postlist);
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("mySavePostpageTurning", pt);
			return map.findForward("mySavePostList");
	    }
		/**
		 * @describe 删除收藏夹帖子
		 */
		public ActionForward toDelMySavePost(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String id = request.getParameter("id");
			try {
				postQueryService.delSavePost(id);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map.findForward("mySavePost");
	    }
		/**
		 * @describe 回复十大
		 */
		public ActionForward toAnswerTopTenList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List postlist =new ArrayList();
			try {
				postlist = postQueryService.answerTopTen();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("postlist", postlist);
			return map.findForward("answerTopTenList");
	    }
		/**
		 * @describe 最新帖子查询
		 */
		public ActionForward toBestNewPostList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List postlist =new ArrayList();
			try {
				postlist = postQueryService.bestNewPost();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("postlist", postlist);
			return map.findForward("bestNewPostList");
	    }
		/**
		 * @describe 版主推荐
		 */
		public ActionForward toForumHostGroomList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List postlist =new ArrayList();
			try {
				postlist = postQueryService.forumHostGroom();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("postlist", postlist);
			return map.findForward("forumHostGroomList");
	    }
		/**
		 * @describe 网友推荐
		 */
		public ActionForward toNetfriendGroomList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List postlist =new ArrayList();
			try {
				postlist = postQueryService.netfriendGroom();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("postlist", postlist);
			return map.findForward("netfriendGroomList");
	    }
		/**
		 * @describe 发帖排行
		 */
		public ActionForward toSendPostRankList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List postlist =new ArrayList();
			try {
				postlist = postQueryService.sendPostRank();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("postlist", postlist);
			return map.findForward("sendPostRankList");
	    }
		/**
		 * @describe 积分排行
		 */
		public ActionForward toPointRankList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List postlist =new ArrayList();
			try {
				postlist = postQueryService.pointRand();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("postlist", postlist);
			return map.findForward("pointRankList");
	    }
		public PostQueryService getPostQueryService() {
			return postQueryService;
		}
		public void setPostQueryService(PostQueryService postQueryService) {
			this.postQueryService = postQueryService; 
		}		
}
