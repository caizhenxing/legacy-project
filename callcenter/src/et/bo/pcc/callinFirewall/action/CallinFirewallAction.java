package et.bo.pcc.callinFirewall.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.pcc.callinFirewall.service.CallinFirewallService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class CallinFirewallAction extends BaseAction {
	    static Logger log = Logger.getLogger(CallinFirewallAction.class.getName());
	    
	    private CallinFirewallService callinFirewallService = null;
		/**
		 * @describe 来电防火墙主页面
		 */
		public ActionForward toCallinFireWallMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("main");
	    }
		/**
		 * @describe 查询页
		 */
		public ActionForward toCallinFireWallQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("query");
	    }
		/**
		 * @describe 页面Load
		 */
		public ActionForward toCallinFireWallLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
	        if(type.equals("insert")){
	        	return map.findForward("load");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = callinFirewallService.getRuleInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	return map.findForward("load");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = callinFirewallService.getRuleInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	return map.findForward("load");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = callinFirewallService.getRuleInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	return map.findForward("load");
	        }
			return map.findForward("load");
	    }
		/**
		 * @describe 防火墙规则列表页
		 */
		public ActionForward toCallinFireWallList(ActionMapping map, ActionForm form,
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
	        List list = callinFirewallService.ruleQuery(dto, pageInfo);
	        int size = callinFirewallService.getRuleSize();
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
		public ActionForward toCallinFireWallOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
			if (type.equals("insert")) {
				try {
					if(callinFirewallService.ifHaveSameNum(dto.get("callinNumBegin").toString()))
					{
						request.setAttribute("operSign", "et.pcc.callinFirewall.sameName");
						return map.findForward("load");
					}
					callinFirewallService.addRule(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("load");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try {
					callinFirewallService.updateRule(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
					System.out.println("****************************");
					return map.findForward("load");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					log.error("CallFirewallAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					callinFirewallService.delRule(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("load");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("load");
	    }
	    
	    public CallinFirewallService getCallinFirewallService() {
			return callinFirewallService;
		}
		public void setCallinFirewallService(CallinFirewallService callinFirewallService) {
			this.callinFirewallService = callinFirewallService;
		}
}
