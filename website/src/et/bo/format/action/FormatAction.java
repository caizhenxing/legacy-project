package et.bo.format.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.format.service.FormatService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class FormatAction extends BaseAction {
	    static Logger log = Logger.getLogger(FormatAction.class.getName());
	    
	    private FormatService formatService = null;
		/**
		 * @describe 进入样式主页面
		 */
		public ActionForward formatMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("main");
		}
		/**
		 * @describe 进入样式查询
		 */
		public ActionForward toFormatQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("query");
		}
		/**
		 * @describe 进入样式添加页
		 */
		public ActionForward toFormatLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String type = (String) request.getParameter("type");
			request.setAttribute("type", type);
			if(type.equals("insert")){			
				return map.findForward("load");
			}
			if(type.equals("update")){
				String id = request.getParameter("id");
				IBaseDTO dto = formatService.getFormatInfo(id);
				request.setAttribute(map.getName(),dto);
				return map.findForward("load");
			}
			if(type.equals("delete")){
				String id = request.getParameter("id");
				IBaseDTO dto = formatService.getFormatInfo(id);
				request.setAttribute(map.getName(),dto);
				return map.findForward("load");
			}
			return map.findForward("load");
		}
		/**
		 * @describe 进入样式详细
		 */
		public ActionForward toFormatDetail(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("detail");
		}
		/**
		 * @describe 样式添加
		 */
		public ActionForward operFormat(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = (String) request.getParameter("type");
			request.setAttribute("type", type);
			if(type.equals("insert")){
				try {
					formatService.formatAdd(dto);
					request.setAttribute("operSign", "操作成功");
					return map.findForward("load");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(type.equals("update")){
				formatService.formatUpdate(dto);
				request.setAttribute("operSign", "操作成功");
				return map.findForward("load");
			}
			if(type.equals("delete")){
				formatService.formatDel(dto.get("id").toString());
				request.setAttribute("operSign", "操作成功");
				return map.findForward("load");
			}
			return map.findForward("load");
		}
		/**
		 * @describe 进入样式列表
		 */
		public ActionForward toFormatList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("formatpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(10);
	        List list = formatService.formatList(dto, pageInfo);
	        int size = formatService.getSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"",map,request);
	        request.getSession().setAttribute("formatpageTurning",pt);       
			return map.findForward("list");
		}
		public FormatService getFormatService() {
			return formatService;
		}
		public void setFormatService(FormatService formatService) {
			this.formatService = formatService;
		}
					
}
