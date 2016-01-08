package et.bo.sys.staff.staffParentInfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import et.bo.sys.staff.staffParentInfo.service.staffParentInfoService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class staffParentInfoAction extends BaseAction {

	private staffParentInfoService spis = null;

		/**
		 * @describe 页面Load
		 */
		public ActionForward toStaffParentInfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);

	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
	        	
	        	return map.findForward("toStaffParentInfoLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = spis.getStaffParentInfoInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	return map.findForward("toStaffParentInfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = spis.getStaffParentInfoInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	
	        	return map.findForward("toStaffParentInfoLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = spis.getStaffParentInfoInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("toStaffParentInfoLoad");
	        }
			return map.findForward("toStaffParentInfoLoad");
	    }
		/**
		 * @describe 用户列表页
		 */
		public ActionForward toStaffParentInfoList(ActionMapping map, ActionForm form,
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
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("StaffParentInfopageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(15);
	        List list = spis.staffParentInfoQuery(dto,pageInfo);
	        int size = spis.getStaffParentInfoSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/Sopho/",map,request);
	        request.getSession().setAttribute("StaffParentInfopageTurning",pt);       
			return map.findForward("toStaffParentInfoList");
	    }
		/**
		 * @describe 用户添加,修改,删除页
		 */
		public ActionForward toStaffParentInfoOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
      
	      
	        String id = request.getSession().getAttribute("SBid").toString();	
	        
			if (type.equals("insert")) {
				try {
					
					spis.addStaffParentInfo(dto,id);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try { 
					boolean b=spis.updateStaffParentInfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("tointerSaveLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toStaffParentInfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					spis.delStaffParentInfo((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toStaffParentInfoLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toStaffParentInfoLoad");
	    }
		public staffParentInfoService getSpis() {
			return spis;
		}
		public void setSpis(staffParentInfoService spis) {
			this.spis = spis;
		}
		
	    
}
