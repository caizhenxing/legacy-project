package et.bo.focusPursue.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import et.bo.focusPursue.service.FocusPursueService;
import et.bo.priceinfo.service.PriceinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class FocusPursueAction extends BaseAction {

	private ClassTreeService cts=null;
	private FocusPursueService fps = null;
	
		/**
		 * @describe 进入焦点追踪主页面
		 */
		public ActionForward toFocusPursueMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
			String str_state=request.getParameter("state");
			fps.clearMessage(ub.getUserId(), str_state);
			request.setAttribute("state", str_state);
			
			return map.findForward("toFocusPursueMain");
	    }
		/**
		 * @describe 焦点追踪查询页
		 */
		public ActionForward toFocusPursueQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{

			return map.findForward("toFocusPursueQuery");
	    }
		
		/**
		   * @describe 焦点追踪库统计类型选择页
		   */
		public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			return map.findForward("tofocusinfoStatisticQuery");
		}

		/**
		   * @describe 焦点追踪库统计类型跳转Action
		   */
		public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			String type = request.getParameter("statisticType").toString();
			//System.out.println("type : "+type);
			if(type!=null&&!"".equals(type)){
				if("ByEditor".equals(type)) return new ActionForward("/stat/focusInfoByEditor.do?method=toMain");
				if("ByType".equals(type)) return new ActionForward("/stat/focusInfoByType.do?method=toMain");
				if("OneEditor".equals(type)) return new ActionForward("/stat/focusInfoOneEditor.do?method=toMain");
				if("AllEditor".equals(type)) return new ActionForward("/stat/focusInfoAllEditor.do?method=toMain");
			}
			return null;
		}
		
		/**
		 * @describe 页面Load
		 */
		public ActionForward toFocusPursueLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	       
	        request.getSession().removeAttribute("oldUploadFile");
			request.getSession().removeAttribute("uploadfile");
	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
	        	dto.set("createTime", TimeUtil.getNowTimeSr());
				request.setAttribute(map.getName(), dto);
	        	return map.findForward("toFocusPursueLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	        	IBaseDTO dto = fps.getFocusPursue(id);
	        	request.getSession().setAttribute("oldUploadFile", dto.get("uploadfile"));//用于比较原来是否有上传文件的
	        	request.setAttribute(map.getName(),dto);
	        	
	        	Object o = dto.get("caserid");
				if(o != null)
					request.setAttribute("caseid", o.toString());
	        	
	        	request.setAttribute("id", id);
	        	return map.findForward("toFocusPursueLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = fps.getFocusPursue(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	
	        	return map.findForward("toFocusPursueLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = fps.getFocusPursue(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("toFocusPursueLoad");
	        }
			return map.findForward("toFocusPursueLoad");
	    }
		/**
		 * @describe 焦点追踪列表页
		 */
		public ActionForward toFocusPursueList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("focusPursuepageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(19);
	        try {
	        	list = fps.focusPursueQuery(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = fps.getFocusPursueSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("focusPursuepageTurning",pt);       
			return map.findForward("toFocusPursueList");
	    }
		/**
		 * @describe 焦点追踪添加,修改,删除页
		 */
		public ActionForward toFocusPursue(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        
			if (type.equals("insert")) {
				try {
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//提交人id
					
					String uploadfile = (String)request.getSession().getAttribute("uploadfile");
					if(uploadfile !=null && !uploadfile.equals("")){
						dto.set("uploadfile", uploadfile);
						request.getSession().removeAttribute("uploadfile");
					}//以上一定要在dto保存之前加，否则不能保存到数据库
					fps.addFocusPursue(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try {
					
					String uploadfile = (String)request.getSession().getAttribute("uploadfile");
					String oldUploadFile = (String)request.getSession().getAttribute("oldUploadFile");//来源于页面的记录原来数据库里的上传文件					
					if(uploadfile !=null && !uploadfile.equals("")){			//如果有上传则合成字符串
						if(oldUploadFile !=null && !oldUploadFile.equals("")){	//如果原来有上传，则合成串
							uploadfile = oldUploadFile + "," + uploadfile;
							
						}														//如果原来没上传，则直接用新串
						dto.set("uploadfile", uploadfile);
					}else{														//如果没上传则把原来的字符串赋回来
						dto.set("uploadfile", oldUploadFile);
					}
					request.getSession().removeAttribute("oldUploadFile");		//最后清除session变量
					request.getSession().removeAttribute("uploadfile");
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//提交人id
					dto.set("accid", "admin");//受理人id
					
					boolean b=fps.updateFocusPursue(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toFocusPursueLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toFocusPursueLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					fps.delFocusPursue((String)dto.get("focusId"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toFocusPursueLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toFocusPursueLoad");
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
		public FocusPursueService getFps() {
			return fps;
		}
		public void setFps(FocusPursueService fps) {
			this.fps = fps;
		}

	    
}
