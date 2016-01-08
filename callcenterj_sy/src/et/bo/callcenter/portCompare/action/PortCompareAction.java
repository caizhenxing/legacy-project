package et.bo.callcenter.portCompare.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.portCompare.service.PortCompareService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class PortCompareAction extends BaseAction {
	    static Logger log = Logger.getLogger(PortCompareAction.class.getName());
	    
	    private PortCompareService portCompareService = null;
		/**
		 * @describe 端口管理主页面 main
		 */
		public ActionForward toPortCompareMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("main");
	    }
		/**
		 * @describe 查询页 query
		 */
		public ActionForward toPortCompareQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("query");
	    }
		/**
		 * @describe 详细页面 Load
		 */
		public ActionForward toPortCompareLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
	        if(type.equals("insert")){
	        	return map.findForward("load");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = portCompareService.getPortCompareInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	return map.findForward("load");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = portCompareService.getPortCompareInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	return map.findForward("load");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = portCompareService.getPortCompareInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	return map.findForward("load");
	        }
			return map.findForward("load");
	    }
		/**
		 * @describe 防火墙规则列表页
		 */
		public ActionForward toPortCompareList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
//			String pageState = null;
//	        PageInfo pageInfo = null;
//	        pageState = (String)request.getParameter("pagestate");
//	        if (pageState==null) {
//	            pageInfo = new PageInfo();
//	        }else{
//	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("portComparepageTurning");
//	            pageInfo = pageTurning.getPage();
//	            pageInfo.setState(pageState);
//	            dto = (DynaActionFormDTO)pageInfo.getQl();
//	        }
//	        pageInfo.setPageSize(10);
	        List list = portCompareService.portCompareQuery();
//	        int size = portCompareService.getPortCompareSize();
//	        pageInfo.setRowCount(size);
//	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
//	        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
//	        request.getSession().setAttribute("portComparepageTurning",pt);       
			return map.findForward("list");
	    }
		/**
		 * @describe 信息添加,修改,删除页
		 */
		public ActionForward toPortCompareOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
			if (type.equals("insert")) {
				try {
					if(portCompareService.isHaveSamePort(dto.get("seatNum").toString())||portCompareService.isHaveSameIp(dto.get("ip").toString()))
					{
						//System.out.println("**********");
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("load");
					}
					portCompareService.addPortCompare(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("load");
				} catch (RuntimeException e) {
					e.printStackTrace();
					
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try {
					boolean b=portCompareService.updatePortCompare(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("load");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
//					System.out.println("****************************");
					return map.findForward("load");
				} catch (RuntimeException e) {
					//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					portCompareService.delPortCompare(dto.get("id").toString());
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("load");
				} catch (RuntimeException e) {
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("load");
	    }
		public PortCompareService getPortCompareService() {
			return portCompareService;
		}
		public void setPortCompareService(PortCompareService portCompareService) {
			this.portCompareService = portCompareService;
		}

}
