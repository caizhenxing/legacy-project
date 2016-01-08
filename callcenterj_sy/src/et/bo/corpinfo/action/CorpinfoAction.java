package et.bo.corpinfo.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.corpinfo.service.CorpinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class CorpinfoAction extends BaseAction {

	private ClassTreeService cts=null;
	private CorpinfoService ocs = null;
	
		/**
		 * @describe 进入企业库信主页面
		 */
		public ActionForward toOperCorpinfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
			String str_state=request.getParameter("state");
			ocs.clearMessage(ub.getUserId(), str_state);
			request.setAttribute("state", str_state);			
			return map.findForward("toOperCorpinfoMain");
	    }
		/**
		 * @describe 企业库信查询页
		 */
		public ActionForward toOperCorpinfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{

			List ServiceList= cts.getLabelVaList("serviceType");
			request.setAttribute("ServiceList", ServiceList);
			List user=ocs.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
			request.setAttribute("user", user);
			request.setAttribute("state", request.getParameter("state"));
			return map.findForward("toOperCorpinfoQuery");
	    }
		
		/**
		   * @describe 企业信息库统计类型选择页
		   */
		public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			return map.findForward("tocorpinfoStatisticQuery");
		}

		/**
		   * @describe 企业信息库统计类型跳转Action
		   */
		public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			String type = request.getParameter("statisticType").toString();
			if(type!=null&&!"".equals(type)){
				if("BySeat".equals(type)) return new ActionForward("/stat/corpInfoBySeat.do?method=toMain");
				if("ByState".equals(type)) return new ActionForward("/stat/corpInfoByState.do?method=toMain");
				if("ByType".equals(type)) return new ActionForward("/stat/corpInfoByServiceType.do?method=toMain");
//				if("AllEditor".equals(type)) return new ActionForward("/stat/markanaInfoByProductType.do?method=toMain");
			}
			return null;
		}
		
		
		/**
		 * @describe 页面Load
		 */
		public ActionForward toOperCorpinfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
			request.getSession().removeAttribute("oldUploadFile");
			request.getSession().removeAttribute("uploadfile");
			
	        request.setAttribute("opertype",type);
	        List ServiceList= cts.getLabelVaList("serviceType");
			request.setAttribute("ServiceList", ServiceList);

	        
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
	        	UserBean ub = (UserBean)request.getSession().getAttribute(
						SysStaticParameter.USERBEAN_IN_SESSION);
				dto.set("corpRid", ub.getUserId());
	        	return map.findForward("toOperCorpinfoLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	        	IBaseDTO dto = ocs.getOperCorpinfo(id);

	        	request.setAttribute(map.getName(),dto);
	        	
	        	request.getSession().setAttribute("oldUploadFile", dto.get("uploadfile"));//用于比较原来是否有上传文件的
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("corpRid").toString());
	        	return map.findForward("toOperCorpinfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = ocs.getOperCorpinfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("corpRid").toString());
	        	return map.findForward("toOperCorpinfoLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = ocs.getOperCorpinfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	request.setAttribute("caseid", dto.get("corpRid").toString());
	        	return map.findForward("toOperCorpinfoLoad");
	        }
			return map.findForward("toOperCorpinfoLoad");
	    }
		/**
		 * @describe 企业库信列表页
		 */
		public ActionForward toOperCorpinfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("operCorpinfopageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(19);
	        
	        String str_state = request.getParameter("state");
	        if(str_state!=null&&str_state.length()>1){
	        	dto.set("state", ocs.changeState(str_state));
	        }
	        
	        try {
	        	list = ocs.operCorpinfoQuery(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = ocs.getOperCorpinfoSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("operCorpinfopageTurning",pt);       
			return map.findForward("toOperCorpinfoList");
	    }
		/**
		 * @describe 企业库信添加,修改,删除页
		 */
		public ActionForward toOperCorpinfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
	        List ServiceList= cts.getLabelVaList("serviceType");
			request.setAttribute("ServiceList", ServiceList);
	        
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
					ocs.addOperCorpinfo(dto);
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
					
					boolean b=ocs.updateOperCorpinfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toOperCorpinfoLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toOperCorpinfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					ocs.delOperCorpinfo((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toOperCorpinfoLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toOperCorpinfoLoad");
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
		public CorpinfoService getOcs() {
			return ocs;
		}
		public void setOcs(CorpinfoService ocs) {
			this.ocs = ocs;
		}



	    
}
