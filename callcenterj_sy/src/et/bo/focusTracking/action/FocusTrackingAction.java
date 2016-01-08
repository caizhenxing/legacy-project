package et.bo.focusTracking.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.focusTracking.service.FocusTrackingService;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;

import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class FocusTrackingAction extends BaseAction {	
	
	private FocusTrackingService fts = null;
	
		/**
		 * @describe 进入焦点追踪主页面
		 */
		public ActionForward toFocusTrackingMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {			
			return map.findForward("toFocusTrackingMain");
	    }
		
		/**
		 * @describe 焦点追踪查询页
		 */
		public ActionForward toFocusTrackingQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			return map.findForward("toFocusTrackingQuery");
	    }
				
		/**
		 * @describe 页面Load
		 */
		public ActionForward toFocusTrackingLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {		
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	       
	        if(type.equals("insert")){	        	
//	        	dto.set("createTime", TimeUtil.getNowTimeSr());				
//	        	return map.findForward("toFocusTrackingLoad");
	        }else if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	        	IBaseDTO dto = fts.getFocusTracking(id);	        	
	        	request.setAttribute(map.getName(),dto);
	        	
//	        	return map.findForward("toFocusTrackingLoad");
	        }else if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = fts.getFocusTracking(id);
	        	request.setAttribute(map.getName(), dto);
	        	
//	        	return map.findForward("toFocusTrackingLoad");
	        }else if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = fts.getFocusTracking(id);
	        	request.setAttribute(map.getName(), dto);
	        	
//	        	return map.findForward("toFocusTrackingLoad");
	        }
			return map.findForward("toFocusTrackingLoad");
	    }
		/**
		 * @describe 焦点追踪列表页
		 */
		public ActionForward toFocusTrackingList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("focusTrackingpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(15);
	        try {
	        	list = fts.focusTrackingQuery(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = fts.getFocusTrackingSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("focusTrackingpageTurning",pt);       
			return map.findForward("toFocusTrackingList");
	    }
		/**
		 * @describe 焦点追踪添加,修改,删除页
		 */
		public ActionForward toFocusTracking(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");			
	        request.setAttribute("opertype",type);

			if (type.equals("insert")) {
				try {					
					UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);					
					dto.set("ftCreateUser", ub.getUserName());//提交人。id：ub.getUserId()					
					dto.set("ftCreateTime", TimeUtil.getNowTimeSr());					
					fts.addFocusTracking(dto);
					request.setAttribute("operSign", "operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}else if (type.equals("update")){
				try {					
					UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);					
					dto.set("ftCreateUser", ub.getUserName());//提交人。id：ub.getUserId()					
					dto.set("ftCreateTime", TimeUtil.getNowTimeSr());					
					fts.updateFocusTracking(dto);					
					request.setAttribute("operSign", "operSuccess");
					
					return map.findForward("toFocusTrackingLoad");
				} catch (RuntimeException e) {
					e.printStackTrace();
					return map.findForward("error");
				}
			}else if (type.equals("delete")){
				try {
					fts.delFocusTracking((String)dto.get("ftId"));
					request.setAttribute("operSign", "Success");
					return map.findForward("toFocusTrackingLoad");
				} catch (RuntimeException e) {
					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toFocusTrackingLoad");
	    }
		
		public FocusTrackingService getFts() {
			return fts;
		}
		public void setFts(FocusTrackingService fts) {
			this.fts = fts;
		}
		
}
