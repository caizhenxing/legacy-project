package et.bo.sys.staff.staffExperience.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;






import et.bo.sys.staff.staffExperience.service.staffExperienceService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class staffExperienceAction extends BaseAction {

	private staffExperienceService ses = null;



		/**
		 * @describe 页面Load
		 */
		public ActionForward toStaffExperienceLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
	        	
	        	
	        	return map.findForward("toStaffExperienceLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	 
	        	IBaseDTO dto = ses.getStaffExperienceInfo(id);
	        	request.setAttribute(map.getName(),dto);

	        	
	        	request.setAttribute("id",id);
	        	return map.findForward("toStaffExperienceLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	

	        	IBaseDTO dto = ses.getStaffExperienceInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id",id);
	        	
	        	return map.findForward("toStaffExperienceLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	
	        	request.setAttribute("id",id);
	        	IBaseDTO dto = ses.getStaffExperienceInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	return map.findForward("toStaffExperienceLoad");
	        }
			return map.findForward("toStaffExperienceLoad");
	    }
		/**
		 * @describe 用户列表页
		 */
		public ActionForward toStaffExperienceList(ActionMapping map, ActionForm form,
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
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("StaffExperiencepageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(15);
	        List list = ses.staffExperienceQuery(dto,pageInfo);
	        int size = ses.getStaffExperienceSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/Sopho/",map,request);
	        request.getSession().setAttribute("StaffExperiencepageTurning",pt);       
			return map.findForward("toStaffExperienceList");
	    }
		/**
		 * @describe 用户添加,修改,删除页
		 */
		public ActionForward toStaffExperienceOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        String SBid = request.getSession().getAttribute("SBid").toString();	        
	      

	        
			if (type.equals("insert")) {
				try {
					
					ses.addStaffExperience(dto,SBid);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try { 
					boolean b=ses.updateStaffExperience(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toStaffExperienceLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toStaffExperienceLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					ses.delStaffExperience((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toStaffExperienceLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toStaffExperienceLoad");
	    }
		public staffExperienceService getSes() {
			return ses;
		}
		public void setSes(staffExperienceService ses) {
			this.ses = ses;
		}
		
		
    
}
