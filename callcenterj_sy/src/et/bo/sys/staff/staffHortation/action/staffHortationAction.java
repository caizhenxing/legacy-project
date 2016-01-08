package et.bo.sys.staff.staffHortation.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;






import et.bo.sys.staff.staffHortation.service.staffHortationService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class staffHortationAction extends BaseAction {


	private staffHortationService shs = null;


		/**
		 * @describe 职工奖罚页面Load
		 */
		public ActionForward toStaffHortationLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        

	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
   	
	        	return map.findForward("toStaffHortationLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = shs.getStaffHortationInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	return map.findForward("toStaffHortationLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = shs.getStaffHortationInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	
	        	return map.findForward("toStaffHortationLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = shs.getStaffHortationInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("toStaffHortationLoad");
	        }
			return map.findForward("toStaffHortationLoad");
	    }
		/**
		 * @describe 职工奖罚列表页
		 */
		public ActionForward toStaffHortationList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;

			
			String SBid = null;
			
			if(request.getParameter("SBid")!=null)
			{
				SBid = request.getParameter("SBid").toString();
				request.getSession().setAttribute("SBid",SBid);
			}
			else
			{
				SBid = request.getSession().getAttribute("SBid").toString();
				request.getSession().setAttribute("SBid",SBid);
			}
			
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("StaffHortationpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(15);
	        List list = shs.StaffHortationQuery(dto,pageInfo);
	        int size = shs.getStaffHortationSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/Sopho/",map,request);
	        request.getSession().setAttribute("StaffHortationpageTurning",pt);       
			return map.findForward("toStaffHortationList");
	    }
		/**
		 * @describe 职工奖罚添加,修改,删除页
		 */
		public ActionForward toStaffHortationOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        String SBid = request.getSession().getAttribute("SBid").toString();	        
	      
	        
			if (type.equals("insert")) {
				try {
					
					shs.addStaffHortation(dto,SBid);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try { 
					boolean b=shs.updateStaffHortation(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toStaffHortationLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toStaffHortationLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					shs.delStaffHortation((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toStaffHortationLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toStaffHortationLoad");
	    }
		public staffHortationService getShs() {
			return shs;
		}
		public void setShs(staffHortationService shs) {
			this.shs = shs;
		}
		

	    
}
