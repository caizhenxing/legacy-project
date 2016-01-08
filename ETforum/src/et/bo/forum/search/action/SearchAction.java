package et.bo.forum.search.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.moduleManager.service.ModuleManagerService;
import et.bo.forum.search.service.SearchService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class SearchAction extends BaseAction {
	    static Logger log = Logger.getLogger(SearchAction.class.getName());
	    
	    private SearchService searchService = null;
	    
	    private ModuleManagerService moduleManagerService = null;
	    
	    private ClassTreeService cts = null; 
		/**
		 * @describe 帖子查询
		 */
		public ActionForward toSearch(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
//			List list = cts.getLabelVaList("date_type");
//			request.setAttribute("dateList", list);
			List allModuleList = moduleManagerService.getAllModuleValueBean();
			request.setAttribute("allModule", allModuleList);
			return map.findForward("toSearch");
		}
		/**
		 * @describe 帖子查询结果列表
		 */
		public ActionForward toSearchList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
//			String moduleId = "";
//	        if (request.getParameter("itemid") == null) {
//	        	moduleId = (String)request.getSession().getAttribute("topicId");
//	        } else {
//	        	moduleId = request.getParameter("itemid");
//	        }
//	        request.setAttribute("itemid", moduleId);
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
//			request.getSession().setAttribute("topicId", moduleId);
			if (pageState == null) {
				pageInfo = new PageInfo();
			} else {
				PageTurning pageTurning = (PageTurning) request.getSession()
						.getAttribute("searchPostpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(5);
			List postlist = new ArrayList();
			try {
				postlist = searchService.postListQuery(dto, pageInfo);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int size = searchService.getSize();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("postlist", postlist);
			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
			request.getSession().setAttribute("searchPostpageTurning", pt);
			return map.findForward("toList");
		}
		public SearchService getSearchService() {
			return searchService;
		}
		public void setSearchService(SearchService searchService) {
			this.searchService = searchService;
		}
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		public ModuleManagerService getModuleManagerService() {
			return moduleManagerService;
		}
		public void setModuleManagerService(ModuleManagerService moduleManagerService) {
			this.moduleManagerService = moduleManagerService;
		}
		
					
}
