/**
 * 	@(#)PostOperAction.java   Nov 29, 2006 9:07:56 AM
 *	 。 
 *	 
 */
package et.bo.forum.postOper.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.postOper.service.PostOperService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhangfeng
 * @version Nov 29, 2006
 * @see
 */
public class PostOperAction extends BaseAction {

	static Logger log = Logger.getLogger(PostOperAction.class.getName());

	private PostOperService postOper = null;

	/**
	 * @describe 跳转到发帖页
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
	public ActionForward toSendPosts(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String itemid = request.getParameter("itemid");
		request.setAttribute("itemid", itemid);
		return map.findForward("sendpost");
	}

	/**
	 * @describe 发表帖子
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
	public ActionForward sendPosts(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String itemid = request.getParameter("itemid");
		if (request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION)==null) {
			return new ActionForward("/forum/userOper/register.do?method=toLogin");
		}
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		String ipfrom = request.getRemoteAddr();
		dto.set("itemid", itemid);
		dto.set("userkey", userkey);
		dto.set("ipfrom", ipfrom);
		postOper.sendPosts(dto);
		return new ActionForward(
				"/forum/forumList.do?method=toPostList&itemid=" + itemid);
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
	public ActionForward toAnswerPosts(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String itemid = request.getParameter("itemid");
		request.setAttribute("itemid", itemid);
		String postid = request.getParameter("postid");
		request.setAttribute("postid", postid);
		return map.findForward("answerpost");
	}

	/**
	 * @describe 回复帖子
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
	public ActionForward answerPosts(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String itemid = request.getParameter("itemid");
		String postid = request.getParameter("postid");
		if (request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION)==null) {
			return new ActionForward("/forum/userOper/register.do?method=toLogin");
		}
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		String ipfrom = request.getRemoteAddr();
		dto.set("itemid", itemid);
		dto.set("parentid", postid);
		dto.set("userkey", userkey);
		dto.set("ipfrom", ipfrom);
		postOper.answerPosts(dto);
		return new ActionForward(
				"/forum/forumList.do?method=toAnswerPostList&itemid=" + itemid
						+ "&postid=" + postid + "");
	}

	/**
	 * @describe 加入收藏夹
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
	public ActionForward addCollection(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		String postsid = request.getParameter("postid");
		dto.set("username", userkey);
		dto.set("postsid", postsid);
		postOper.addCollection(dto);
		return new ActionForward(
				"/forum/postQuery.do?method=toMySavePostList");
	}
	
	/**
	 * @describe 删除帖子
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
	public ActionForward deletePosts(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String areaid = request.getParameter("areaid");
		String postsid = request.getParameter("postsid");
		String result = postOper.delPosts(postsid);
		if (result.equals("delmain")) {
			return new ActionForward("forum/postOper/oper.do?method=sendPosts&itemid="+areaid);
		}else if(result.equals("delin")){
			return new ActionForward("forum/forumList.do?method=toAnswerPostList&itemid="+areaid+"&postid="+postsid+"");
		}
		return null;
	}

	public PostOperService getPostOper() {
		return postOper;
	}

	public void setPostOper(PostOperService postOper) {
		this.postOper = postOper;
	}

}
