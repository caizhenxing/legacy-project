package et.bo.medical.bookMedicinfo.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.medical.bookMedicinfo.service.BookMedicinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class BookMedicinfoAction extends BaseAction {

	private ClassTreeService cts=null;
	private BookMedicinfoService bmis = null;
	
		/**
		 * @describe 进入预约医疗主页面
		 */
		public ActionForward toBookMedicinfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
			String str_state=request.getParameter("state");
			bmis.clearMessage(ub.getUserId(), str_state);
			request.setAttribute("state", str_state);			
			return map.findForward("toBookMedicinfoMain");
	    }
		/**
		 * @describe 预约医疗查询页
		 */
		public ActionForward toBookMedicinfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			request.setAttribute("expertList", bmis.getExpertList());
			request.setAttribute("state", request.getParameter("state"));
			request.setAttribute("userList", bmis.userQuery());
			return map.findForward("toBookMedicinfoQuery");
	    }
		
		public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			String type = request.getParameter("statisticType").toString();
			if(type!=null&&!"".equals(type)){
				if("BySeat".equals(type)) return new ActionForward("/stat/medicInfoBySeat.do?method=toMain");
				if("ByExpert".equals(type)) return new ActionForward("/stat/medicInfoByExpert.do?method=toMain");
				if("ByState".equals(type)) return new ActionForward("/stat/medicInfoByState.do?method=toMain");
				if("ByParter".equals(type)) return new ActionForward("/stat/medicInfoByParter.do?method=toMain");
				if("ByUser".equals(type)) return new ActionForward("/stat/medicInfoByUser.do?method=toMain");
				if("ForService".equals(type)) return new ActionForward("/stat/medicInfoForService.do?method=toMain");
				if("ForVisit".equals(type)) return new ActionForward("/stat/medicInfoForVisit.do?method=toMain");
				if("ForNavigator".equals(type)) return new ActionForward("/stat/medicInfoForNavigator.do?method=toMain");				
			}
			return null;
		}
		
		public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			return map.findForward("toBookMedicinfoStatisticQuery");
		}
		/**
		 * @describe 预约医疗页面Load
		 */
		public ActionForward toBookMedicinfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	    	List expertList = cts.getLabelVaList("zhuanjialeibie");
			request.setAttribute("expertList", expertList);
//	        classTreeService.getLabelVaList("ivrNodeRoot");
	        List diagnoseList = cts.getLabelVaList("diagnose");   
	        request.setAttribute("diagnoseList", diagnoseList);
	        
	        request.setAttribute("expertNameList",new ArrayList());
	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
	        	UserBean u=(UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
	        	dto.set("bookRid", u.getUserId());
	        	request.setAttribute(map.getName(), dto);
	        	return map.findForward("toBookMedicinfoLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	        	IBaseDTO dto = bmis.getBookMedicinfo(id);
	        	
	        	Object expSort = dto.get("billNum");
	        	if(expSort!=null&&!"".equals(expSort.toString())){
	        		List expNameList = bmis.getExpertNameList(expSort.toString());
					request.setAttribute("expertNameList", expNameList);
	        	}
	        	
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("bookRid").toString());
	        	return map.findForward("toBookMedicinfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = bmis.getBookMedicinfo(id);
	        	
	        	Object expSort = dto.get("billNum");
	        	if(expSort!=null&&!"".equals(expSort.toString())){
	        		List expNameList = bmis.getExpertNameList(expSort.toString());
					request.setAttribute("expertNameList", expNameList);
	        	}
	        	
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("bookRid").toString());
	        	return map.findForward("toBookMedicinfoLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = bmis.getBookMedicinfo(id);
	        	
	        	Object expSort = dto.get("billNum");
	        	if(expSort!=null&&!"".equals(expSort.toString())){
	        		List expNameList = bmis.getExpertNameList(expSort.toString());
					request.setAttribute("expertNameList", expNameList);
	        	}
	        	
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("bookRid").toString());
	        	return map.findForward("toBookMedicinfoLoad");
	        }
			return map.findForward("toBookMedicinfoLoad");
	    }
		/**
		 * @describe 预约医疗列表页
		 */
		public ActionForward toBookMedicinfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("bookMedicinfopageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(19);

	        String str_state = request.getParameter("state");
	        if(str_state!=null&&str_state.length()>1){
	        	dto.set("state", bmis.changeState(str_state));
	        }
	        
	        try {
	        	list = bmis.bookMedicinfoQuery(dto,pageInfo);
			} catch (Exception e) {
				System.err.println(e);
			}
	        
	       
	        int size = bmis.getBookMedicinfoSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("bookMedicinfopageTurning",pt);       
			return map.findForward("toBookMedicinfoList");
	    }
		/**
		 * @describe 预约医疗添加,修改,删除页
		 */
		public ActionForward toBookMedicinfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	    	List expertList = cts.getLabelVaList("zhuanjialeibie");
			request.setAttribute("expertList", expertList);
	        List diagnoseList = cts.getLabelVaList("diagnose");   
	        request.setAttribute("diagnoseList", diagnoseList);
	        
	        request.setAttribute("expertNameList",new ArrayList());
	        
			if (type.equals("insert")) {
				try {		
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//提交人id
					
					bmis.addBookMedicinfo(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try {
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//提交人id
					dto.set("accid", "admin");//受理人id
					
					boolean b=bmis.updateBookMedicinfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toBookMedicinfoLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toBookMedicinfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					bmis.delBookMedicinfo((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toBookMedicinfoLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toBookMedicinfoLoad");
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
		public BookMedicinfoService getBmis() {
			return bmis;
		}
		public void setBmis(BookMedicinfoService bmis) {
			this.bmis = bmis;
		}
}
