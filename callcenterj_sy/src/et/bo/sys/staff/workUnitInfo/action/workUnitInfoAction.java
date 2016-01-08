package et.bo.sys.staff.workUnitInfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.staff.workUnitInfo.service.workUnitInfoService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class workUnitInfoAction extends BaseAction {

	private workUnitInfoService wuis = null;

		/**
		 * @describe 进入公司基本信息框架
		 */
		public ActionForward toWorkUnitInfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {

			
			return map.findForward("workUnitInfoMain");
	    }
		
		
		/**
		 * @describe 进入公司基本信息查询页
		 */
		public ActionForward topQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			

			return map.findForward("topQuery");
	    }
		
		
		
		/**
		 * @describe 进入公司基本信息POP查询页
		 */
		public ActionForward popquery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{

			return map.findForward("popquery");
	    }

		
		
		/**
		 * @describe 公司基本信息页面Load
		 */
		public ActionForward toWorkUnitInfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;

	        	return map.findForward("toWorkUnitInfoLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	

	        	IBaseDTO dto = wuis.getWorkUnitInfoInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	return map.findForward("toWorkUnitInfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	
	  
	        	IBaseDTO dto = wuis.getWorkUnitInfoInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	
	        	return map.findForward("toWorkUnitInfoLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");
	
	        	IBaseDTO dto = wuis.getWorkUnitInfoInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("toWorkUnitInfoLoad");
	        }
			return map.findForward("toWorkUnitInfoLoad");
	    }
		/**
		 * @describe 公司基本信息列表页
		 */
		public ActionForward toWorkUnitInfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;

			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("interpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(15);
	        List list = wuis.workUnitInfoQuery(dto,pageInfo);
	        int size = wuis.getWorkUnitInfoSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/Sopho/",map,request);
	        request.getSession().setAttribute("interpageTurning",pt);       
			return map.findForward("toWorkUnitInfoList");
	    }
		/**
		 * @describe 公司基本信息添加,修改,删除页
		 */
		public ActionForward toInterOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        String id = request.getParameter("id").toString();	        
	      

	        
			if (type.equals("insert")) {
				try {
					
					wuis.addWorkUnitInfo(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try { 
					boolean b=wuis.updateWorkUnitInfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("tointerSaveLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toWorkUnitInfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					wuis.delWorkUnitInfo((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toWorkUnitInfoLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toWorkUnitInfoLoad");
	    }
		
		

		public workUnitInfoService getWuis() {
			return wuis;
		}
		public void setWuis(workUnitInfoService wuis) {
			this.wuis = wuis;
		}
		

	    
}
