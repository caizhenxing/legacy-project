package et.bo.medical.medicinfo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import et.bo.medical.medicinfo.service.MedicinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;

import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class MedicinfoAction extends BaseAction {

	private ClassTreeService cts=null;
	private MedicinfoService mis = null;
	
		/**
		 * @describe 进入普通医疗主页面
		 */
		public ActionForward toMedicinfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
			String str_state=request.getParameter("state");
			mis.clearMessage(ub.getUserId(), str_state);
			request.setAttribute("state", str_state);			
			return map.findForward("toMedicinfoMain");
	    }
		/**
		 * @describe 普通医疗查询页
		 */
		public ActionForward toMedicinfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			request.setAttribute("expertList", mis.getExpertList());
			request.setAttribute("state", request.getParameter("state"));
			request.setAttribute("userList", mis.userQuery());
			return map.findForward("toMedicinfoQuery");
	    }
		
		/**
		   * @describe 普通医疗信息库统计类型选择页
		   */
		public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			return map.findForward("tomedicinfoStatisticQuery");
		}

		/**
		   * @describe 普通医疗信息库统计类型跳转Action
		   */
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
			}
			return null;
		}
		
		
		/**
		 * @describe 页面Load
		 */
		public ActionForward toMedicinfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
			List expertList = cts.getLabelVaList("zhuanjialeibie");
			request.setAttribute("expertList", expertList);
			
			request.setAttribute("expertNameList",new ArrayList());
	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
	        	UserBean u=(UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
	        	dto.set("medicRid", u.getUserId());
	        	request.setAttribute(map.getName(),dto);
	        	return map.findForward("toMedicinfoLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	        	IBaseDTO dto = mis.getMedicinfo(id);
	        	
	        	Object expSort = dto.get("billNum");
	        	if(expSort!=null&&!"".equals(expSort.toString())){
	        		List expNameList = mis.getExpertNameList(expSort.toString());
					request.setAttribute("expertNameList", expNameList);
	        	}
	        	
//	        	request.setAttribute("rExpertName", dto.get("expert"));
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("medicRid").toString());
	        	return map.findForward("toMedicinfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = mis.getMedicinfo(id);
	        	
	        	Object expSort = dto.get("billNum");
	        	if(expSort!=null&&!"".equals(expSort.toString())){
	        		List expNameList = mis.getExpertNameList(expSort.toString());
					request.setAttribute("expertNameList", expNameList);
	        	}
	        	
//	        	request.setAttribute("rExpertName", dto.get("expert"));
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("medicRid").toString());
	        	return map.findForward("toMedicinfoLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = mis.getMedicinfo(id);
	        	
	        	Object expSort = dto.get("billNum");
	        	if(expSort!=null&&!"".equals(expSort.toString())){
	        		List expNameList = mis.getExpertNameList(expSort.toString());
					request.setAttribute("expertNameList", expNameList);
	        	}
	        	
	        	request.setAttribute(map.getName(), dto);
//	        	request.setAttribute("rExpertName", dto.get("expert"));
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("medicRid").toString());
	        	return map.findForward("toMedicinfoLoad");
	        }
			return map.findForward("toMedicinfoLoad");
	    }
		/**
		 * @describe 普通医疗列表页
		 */
		public ActionForward toMedicinfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("medicinfopageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(15);

	        String str_state = request.getParameter("state");
	        if(str_state!=null&&str_state.length()>1){
	        	dto.set("state", mis.changeState(str_state));
	        }
	        
	        try {
	        	list = mis.medicinfoQuery(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = mis.getMedicinfoSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("medicinfopageTurning",pt);       
			return map.findForward("toMedicinfoList");
	    }
		/**
		 * @describe 普通医疗添加,修改,删除页
		 */
		public ActionForward toMedicinfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        List expertList = cts.getLabelVaList("zhuanjialeibie");
			request.setAttribute("expertList", expertList);
			
			request.setAttribute("expertNameList",new ArrayList());
			
			if (type.equals("insert")) {
				try {
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//提交人id
					
					mis.addMedicinfo(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try {
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//提交人id
					dto.set("accid", "admin");//受理人id
					
					boolean b=mis.updateMedicinfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toMedicinfoLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toMedicinfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					mis.delMedicinfo((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toMedicinfoLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toMedicinfoLoad");
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
		public MedicinfoService getMis() {
			return mis;
		}
		public void setMis(MedicinfoService mis) {
			this.mis = mis;
		}

	    
}
