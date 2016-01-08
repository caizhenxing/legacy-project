package et.bo.sys.staff.staffBasic.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.staff.staffBasic.service.staffBasicService;


import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class staffBasicAction extends BaseAction {

	private staffBasicService sbs = null;
	
	private ClassTreeService cts = null;
	
		/**
		 * @describe 进入职工基本信息框架页面
		 */
		public ActionForward toStaffBasicMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			String type = request.getParameter("type");
			String id = request.getParameter("id");
			
			request.setAttribute("type", type);
			request.setAttribute("id", id);
			
			
			return map.findForward("tostaffBasicMain");
	    }
		/**
		 * @describe 职工基本信息查询页
		 */
		public ActionForward toStaffBasicQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			return map.findForward("toStaffBasicQuery");
	    }
		
		/**
		 * @describe 页面Load
		 */
		public ActionForward toStaffBasicLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;
			request.setAttribute("id", "1");
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        
	        List unitWorkList  = sbs.getWorkUnitInfo();
	        request.setAttribute("unitWorkList", unitWorkList);
	        
	        
//	        List departmentList  = sbs.getdepartmentList();
//	        request.setAttribute("departmentList", departmentList);
	        
	        List l = cts.getLabelVaList("departmentRoot");
	        request.setAttribute("departmentList", l);
	        
//	        List DutyList  = sbs.getDutyList();
//	        request.setAttribute("DutyList", DutyList);
	        
	        
	        
	        
	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
	        	
	        	return map.findForward("toStaffBasicLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = sbs.getStaffBasicInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	return map.findForward("toStaffBasicLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	
	        	IBaseDTO dto = sbs.getStaffBasicInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	
	        	return map.findForward("toStaffBasicLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = sbs.getStaffBasicInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("toStaffBasicLoad");
	        }
			return map.findForward("toStaffBasicLoad");
	    }
		/**
		 * @describe 用户列表页
		 */
		public ActionForward toStaffBasicList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;

			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("StaffBasicpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(15);
	        List list = sbs.staffBasicQuery(dto,pageInfo);
	        int size = sbs.getStaffBasicSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/Sopho/",map,request);
	        request.getSession().setAttribute("StaffBasicpageTurning",pt);       
			return map.findForward("toStaffBasicList");
	    }
		/**
		 * @describe 用户添加,修改,删除页
		 */
		public ActionForward toStaffBasicOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        List l = cts.getLabelVaList("departmentRoot");
	        request.setAttribute("departmentList", l);
	       
	        List unitWorkList  = sbs.getWorkUnitInfo();
	        request.setAttribute("unitWorkList", unitWorkList);
	        
	        
			if (type.equals("insert")) {
				try {
					
					sbs.addStaffBasic(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try { 
					boolean b=sbs.updateStaffBasic(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toStaffBasicLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					request.setAttribute("id", "1");
					return map.findForward("toStaffBasicLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					sbs.delStaffBasic((String)dto.get("staffId"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					
					request.setAttribute("id", "1");
					return map.findForward("toStaffBasicLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}
			 request.setAttribute("id", "1");
			return map.findForward("toStaffBasicLoad");
	    }
		public staffBasicService getSbs() {
			return sbs;
		}
		public void setSbs(staffBasicService sbs) {
			this.sbs = sbs;
		}
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
				
	    
}
