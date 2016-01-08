package et.bo.newsInfo.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.newsInfo.service.NewsInfoService;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.action.BaseAction;

public class NewsInfoAction extends BaseAction {
	    static Logger log = Logger.getLogger(NewsInfoAction.class.getName());
	    
	    private NewsInfoService newsInfoService = null;
	    
	    private ClassTreeService cts = null;
		/**
		 * @describe 前台模块查询列表
		 */
		public ActionForward toNewsList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List list = new ArrayList();
		    try {
				list = newsInfoService.getNewsList("");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("list", list);
			return map.findForward("newsList");
		}
		/**
		 * @describe 前台模块查询列表
		 */
		public ActionForward toNewsListTest(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List list = new ArrayList();
		    try {
				list = newsInfoService.getNewsList("");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("list", list);
			return map.findForward("newsListTest");
		}
		/**
		 * @describe 前台模块查询列表
		 */
		public ActionForward toNewsTypeSelect(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List list = new ArrayList();
			List listTwo = new ArrayList();
		    try {
				list = newsInfoService.getNewsSelectList(cts.getvaluebyNickName("type_one"), cts.getvaluebyNickName("type_one_class"));
			    listTwo = newsInfoService.getNewsSelectList(cts.getvaluebyNickName("type_two"), cts.getvaluebyNickName("type_two_class"));
		    } catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    request.setAttribute("listTwo", listTwo);
			request.setAttribute("list", list);
			return map.findForward("newsTypeSelect");
		}
		/**
		 * @describe 新闻显示样式测试列表
		 */
		public ActionForward toNewsStyleTest(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List list = new ArrayList();
			List listTwo = new ArrayList();
		    try {
		    	list = newsInfoService.getNewsStyle("NEWS_AREA_0000000043");
//				list = newsInfoService.getNewsSelectList(cts.getvaluebyNickName("type_one"), cts.getvaluebyNickName("type_one_class"));
//			    listTwo = newsInfoService.getNewsSelectList(cts.getvaluebyNickName("type_two"), cts.getvaluebyNickName("type_two_class"));
		    } catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		    request.setAttribute("listTwo", listTwo);
			request.setAttribute("list", list);
			return map.findForward("toNewsStyleTest");
		}
		/**
		 * @describe 新闻显示样式测试列表
		 */
		public ActionForward toStyleModuleOne(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List list = new ArrayList();
			List listTwo = new ArrayList();
			List listThree = new ArrayList();
			List listFour = new ArrayList();
		    try {
		    	list  = newsInfoService.getNewsStyle("NEWS_AREA_0000000043");
		    	listTwo = newsInfoService.getNewsStyle("NEWS_AREA_0000000061");
		    	listThree = newsInfoService.getNewsStyle("NEWS_AREA_0000000062");
		    	listFour = newsInfoService.getNewsStyle("NEWS_AREA_0000000081");
		    	
//				list = newsInfoService.getNewsSelectList(cts.getvaluebyNickName("type_one"), cts.getvaluebyNickName("type_one_class"));
//			    listTwo = newsInfoService.getNewsSelectList(cts.getvaluebyNickName("type_two"), cts.getvaluebyNickName("type_two_class"));
		    } catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    request.setAttribute("listTwo", listTwo);
			request.setAttribute("list", list);
			request.setAttribute("listThree", listThree);
			request.setAttribute("listFour", listFour);
			return map.findForward("toStyleModuleOne");
		}
		public NewsInfoService getNewsInfoService() {
			return newsInfoService;
		}
		public void setNewsInfoService(NewsInfoService newsInfoService) {
			this.newsInfoService = newsInfoService;
		}
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
					
}
