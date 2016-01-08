package et.bo.callcenter.orderMenu.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.orderMenu.service.OrderMenuService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
/**
 * 
 * @author Chen Gang
 *
 */
public class OrderMenuAction extends BaseAction {
	    static Logger log = Logger.getLogger(OrderMenuAction.class.getName());
	    
	    private OrderMenuService om = null;
	    
	    private ClassTreeService cts = null;
	    
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		/**
		 * @describe 业务添加页弹出
		 */
		public ActionForward toOrderMenuAddpop(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getNickName());
				ivrlist.add(lvb);
			}
			request.setAttribute("ivrlist", ivrlist);
			return map.findForward("addpop");
	    }
		/**
		 * @describe 框架主页面
		 */
		public ActionForward toOrderMenuMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("main");
	    }
		/**
		 * @describe 查询页
		 */
		public ActionForward toOrderMenuQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getNickName());
				ivrlist.add(lvb);
			}
			request.setAttribute("ivrlist", ivrlist);
			return map.findForward("query");
	    }
		/**
		 * @describe 页面Load
		 */
		public ActionForward toOrderMenuLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
//			IVRMultiVoice
			TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getNickName());
				ivrlist.add(lvb);
			}
			request.setAttribute("ivrlist", ivrlist);
			return map.findForward("load");
	    }
		/**
		 * @describe 用户评价列表页
		 */
		public ActionForward toOrderMenuList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("firewallpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(10);
//	        String telNum = dto.get("telNum").toString();
	        List list = om.orderMenuSearch(dto, pageInfo);
	        int size = om.getOrderMenuSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
	        request.getSession().setAttribute("firewallpageTurning",pt);       
			return map.findForward("list");
	    }
		/**
		 * @describe 信息添加,修改,删除页
		 */
		public ActionForward toOrderMenuOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			request.setAttribute("ivrlist", new ArrayList());
			boolean flag = om.addOrderMenu(dto);
			if(!flag && "dingzhi".equals(dto.get("orderType"))) {
				request.setAttribute("operSign", "dingzhishibai");
				return map.findForward("load");
			} else if(!flag && "tuiding".equals(dto.get("orderType"))) {
				request.setAttribute("operSign", "tuidingshibai");
				return map.findForward("load");
			}
			
			request.setAttribute("operSign", "sys.common.operSuccess");
			return map.findForward("load");
	    }
		
		public ActionForward toDelOrderMenu(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String id = request.getParameter("id");
			request.setAttribute("list", new ArrayList());
			boolean flag = om.delOrderMenu(id);
			
			if(!flag) {
				request.setAttribute("operSign", "tuidingshibai");
				return map.findForward("load");
			}
			
			request.setAttribute("operSign", "sys.common.operSuccess");
			return map.findForward("load");
		}
		
		public OrderMenuService getOm() {
			return om;
		}
		public void setOm(OrderMenuService om) {
			this.om = om;
		}
	    
}
