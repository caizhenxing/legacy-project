package et.bo.incommingInfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.custinfo.service.CustinfoService;
import et.bo.incommingInfo.service.IncommingInfoService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class IncommingInfoAction extends BaseAction {
	
		private IncommingInfoService infoService = null;
		
		private CustinfoService custinfoService = null;
			
		public CustinfoService getCustinfoService() {
			return custinfoService;
		}
		public void setCustinfoService(CustinfoService custinfoService) {
			this.custinfoService = custinfoService;
		}
		/**
		 * ����ʵ���е�������Ϣ
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward telCondition(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
//			GeneralCaseinfoService gcs = (GeneralCaseinfoService)SpringRunningContainer.getInstance().getBean("GeneralCaseinfoService");
			JSONArray jsonArray = JSONArray.fromObject(infoService.screenList());
			outJsonString(response,jsonArray.toString());
			return null;
		}
		
		/**
		 * @describe ������ҳ��
		 */
		public ActionForward toIncommingInfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String addTreeInfoId = request.getParameter("addTreeInfoId");
			return map.findForward("toIncommingInfoMain");
	    }
		/**
		 * @describe �����ѯҳ
		 */
		public ActionForward toIncommingInfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{			
			request.setAttribute("expertList", infoService.getExpertList());
			List user=custinfoService.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
			request.setAttribute("user", user);
			return map.findForward("toIncommingInfoQuery");
	    }
		/**
		 * @describe ҳ��Load
		 */
		public ActionForward toIncommingInfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");			
	        request.setAttribute("opertype",type);
	        
	        if(type.equals("insert")){
	        	//����������idҲ��Cc_Ivr_tree_Info����
	        	//do something
	        	return map.findForward("toIncommingInfoLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");      	
	        	IBaseDTO dto = infoService.getIncommingInfoDetail(id);
	        	request.setAttribute(map.getName(),dto);
	        	request.setAttribute("expertList", infoService.getExpertList());
	        	return map.findForward("toIncommingInfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = infoService.getIncommingInfoDetail(id);
	        	request.setAttribute(map.getName(),dto);
	        	request.setAttribute("expertList", infoService.getExpertList());
	        	return map.findForward("toIncommingInfoLoad");
	        }
	        if(type.equals("delete")){
	        	//do something
	        }
	        
			return map.findForward("toIncommingInfoLoad");
	    }
		/**
		 * @describe �����б�ҳ
		 */
		public ActionForward toIncommingInfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
//			System.out.println("dictQuestionType1 = ["+dto.getString("dictQuestionType1")+"]");
//			System.out.println("dictIsAnswerSucceed = ["+dto.getString("dictIsAnswerSucceed")+"]");
//			System.out.println("answerMan = ["+dto.getString("answerMan")+"]");
//			System.out.println("dictIsCallback = ["+dto.getString("dictIsCallback")+"]");
//			System.out.println("billNum = ["+dto.getString("billNum")+"]");
//			System.out.println("expertName = ["+dto.getString("expertName")+"]");
			List<DynaBeanDTO> list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("incommingInfoTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(13);
	        try {
	        	list = infoService.incommingInfoList(dto, pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = infoService.getIncommingInfoSize();

	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
//	        //System.out.println("*******************************************");
	        PageTurning pt = new PageTurning(pageInfo,"/callcenterj_sy/",map,request);
	        request.getSession().setAttribute("incommingInfoTurning",pt);
	       
	        return map.findForward("toIncommingInfoList");
	    }
		/**
		 * @describe ������ϸ��Ϣ���,�޸�,ɾ��ҳ
		 */
		public ActionForward toOperIncommingInfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
	        
			if (type.equals("insert")) {
				try {
					//do someThing
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try { 
					
					boolean b=infoService.updateIncommingInfoInfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toIncommingInfoLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toIncommingInfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					//do someThing
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}	
			
			return map.findForward("toCcIvrTreeinfoLoad");
	    }
		
		
		
		public ActionForward popIntersave(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			//do someThing
			return map.findForward("popIntersave");
		}
		public IncommingInfoService getInfoService() {
			return infoService;
		}
		public void setInfoService(IncommingInfoService infoService) {
			this.infoService = infoService;
		}
		
		
}
