package et.bo.news.newsArea.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.format.service.FormatService;
import et.bo.news.newsArea.service.NewsAreaService;
import et.bo.newsInfo.service.NewsInfoService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class NewsAreaAction extends BaseAction {
	    static Logger log = Logger.getLogger(NewsAreaAction.class.getName());
	    
	    private NewsAreaService newsAreaService = null;
	    
	    private NewsInfoService newsInfoService = null;
	    
	    private FormatService formatService = null;
	    
		/**
		 * @describe 进入样式主页面
		 */
		public ActionForward areaMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println("--->");
			return map.findForward("main");
		}
		/**
		 * @describe 进入样式查询
		 */
		public ActionForward toAreaQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("query");
		}
		/**
		 * @describe 进入样式添加页
		 */
		public ActionForward toAreaLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String type = (String) request.getParameter("type");
			request.setAttribute("type", type);
			List areaList = formatService.getStyleList();
			request.setAttribute("areaList", areaList);
			if(type.equals("insert")){			
				return map.findForward("load");
			}
			if(type.equals("update")){
				String id = request.getParameter("id");
				IBaseDTO dto = newsAreaService.getAreaInfo(id);
				request.setAttribute(map.getName(),dto);
				return map.findForward("load");
			}
			if(type.equals("delete")){
				String id = request.getParameter("id");
				IBaseDTO dto = newsAreaService.getAreaInfo(id);
				request.setAttribute(map.getName(),dto);
				return map.findForward("load");
			}
			return map.findForward("load");
		}
		/**
		 * @describe 进入样式详细
		 */
		public ActionForward toAreaDetail(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("detail");
		}
		/**
		 * @describe 样式添加
		 */
		public ActionForward operArea(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = (String) request.getParameter("type");
			request.setAttribute("type", type);
			List areaList = formatService.getStyleList();
			request.setAttribute("areaList", areaList);
			if(type.equals("insert")){
				
				try {
					newsAreaService.areaAdd(dto);
					request.setAttribute("operSign", "操作成功");
					return map.findForward("load");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(type.equals("update")){
				newsAreaService.areaUpdate(dto);
				request.setAttribute("operSign", "操作成功");
				return map.findForward("load");
			}
			if(type.equals("del")){
				newsAreaService.areaDel(dto.get("id").toString());
				request.setAttribute("operSign", "操作成功");
				return map.findForward("load");
			}
			return map.findForward("load");
		}
		/**
		 * @describe 进入样式列表
		 */
		public ActionForward toAreaList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("areapageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(10);
	        List list = newsAreaService.areaList(dto, pageInfo);
	        int size = newsAreaService.getSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"",map,request);
	        request.getSession().setAttribute("areapageTurning",pt);       
			return map.findForward("list");
		}
		public NewsAreaService getNewsAreaService() {
			return newsAreaService;
		}
		public void setNewsAreaService(NewsAreaService newsAreaService) {
			this.newsAreaService = newsAreaService;
		}
		public NewsInfoService getNewsInfoService() {
			return newsInfoService;
		}
		public void setNewsInfoService(NewsInfoService newsInfoService) {
			this.newsInfoService = newsInfoService;
		}
		public FormatService getFormatService() {
			return formatService;
		}
		public void setFormatService(FormatService formatService) {
			this.formatService = formatService;
		}

}
