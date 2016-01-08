package et.bo.linkman.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import et.bo.corpinfo.service.CorpinfoService;
import et.bo.linkman.service.LinkmanService;
import et.bo.sys.common.SysStaticParameter;

import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class LinkmanAction extends BaseAction {

	private ClassTreeService cts=null;
	private LinkmanService ocs = null;
	
		/**
		 * @describe  进入联络员查询主页面
		 */
		public ActionForward toLinkmanMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {

			
			return map.findForward("toLinkmanMain");
	    }
		/**
		 * @describe 联络员查询页
		 */
		public ActionForward toLinkmanQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
				return map.findForward("toLinkmanQuery");
	    }
		/**
		 * @describe 页面Load
		 */
		public ActionForward toOperCorpinfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;
			return  null;
	    }
		/**
		 * @describe 联络员信列表页
		 */
		public ActionForward toLinkmanList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	            pageState=1+"";
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("pageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(20);
	        try {

	        	list = ocs.linkmanQuery(dto,pageInfo,Integer.parseInt(pageState));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = ocs.getLinkmanSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("pageTurning",pt);       
			return map.findForward("toLinkmanList");
	    }
		/**
		 * @describe 联络员添加,修改,删除页
		 */
		public ActionForward toOperCorpinfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			return null;
	    }
		
		
		
		public ActionForward popIntersave(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			return map.findForward("popIntersave");
		}
		

		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		public LinkmanService getOcs() {
			return ocs;
		}
		public void setOcs(LinkmanService ocs) {
			this.ocs = ocs;
		}



	    
}
