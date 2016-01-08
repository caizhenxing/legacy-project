package et.bo.forum.log.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.log.service.ForumLogService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class ForumLogAction extends BaseAction {
	    static Logger log = Logger.getLogger(ForumLogAction.class.getName());
	    
	    private ForumLogService forumLogService = null;
	    
	    private ClassTreeService cts = null;
		/**
		 * @describe 论坛日志
		 */
		public ActionForward toLogMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println("进入");
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
	        String userId = ui.getUserName();
	        try {
	        	String flag = (String)cts.getvaluebyNickName("forum_log_flag");
//				System.out.println("??"+flag+"是空不");
	        	if(flag.equals("1")){
					forumLogService.addLog(userId, cts.getvaluebyNickName("forum_log"), "查看", request.getRemoteAddr(), "");
				}
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			System.out.println("----------------------------------------");
			return map.findForward("logMain");
		}
		/**
		 * @describe 查询主页面
		 */
		public ActionForward toLogQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List moduleNameList = cts.getLabelVaList("log_oper_area");
			request.setAttribute("moduleNameList", moduleNameList);
			return map.findForward("logQuery");
		}
		/**
		 * @describe 查询列表页面
		 */
		public ActionForward toLogList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
//			request.getSession().setAttribute("topicId", moduleId);
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("forumLogpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(5);
			List logList = new ArrayList();
			try {
				logList = forumLogService.logList(dto, pageInfo);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int size = forumLogService.getSize();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("logList", logList);
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("forumLogpageTurning", pt);
			return map.findForward("logList");
		}
		public ForumLogService getForumLogService() {
			return forumLogService;
		}
		public void setForumLogService(ForumLogService forumLogService) {
			this.forumLogService = forumLogService;
		}
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
					
}
