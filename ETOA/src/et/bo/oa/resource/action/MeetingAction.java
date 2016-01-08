package et.bo.oa.resource.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.resource.service.ResourceServiceI;
import et.bo.oa.workflow.service.OaWorkFlowService;
import et.bo.oa.workflow.service.WorkFlowInfo;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p> 会议室管理 </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-14
 * 
 */
public class MeetingAction extends BaseAction {

	private ResourceServiceI meetingServiceImpl = null;

	private ClassTreeService departTree = null;
	
	private ResourceServiceI carServiceImpl = null;

	private OaWorkFlowService oawfs=null;
	
	public MeetingAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> 会议室详细信息页 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toMeetInfoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		formDto.set("type","40");
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = request.getParameter("pagestate");
		String page = request.getParameter("pagestop");

		if (pageState == null && page == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("agropageTurning");
			pageInfo = pageTurning.getPage();
			if (pageState != null)
				pageInfo.setState(pageState);
			formDto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(5);	
		Object[] meetInfoLists = meetingServiceImpl.searhResourceInfo(formDto,pageInfo);
		int size  = meetingServiceImpl.getResourceSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);
		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("agropageTurning", papgeTurning);
        
		request.setAttribute("meetInfoLists", meetInfoLists);
		return mapping.findForward("meetInfoList");
	}
	
	/**
	 * <p> 会议室设置页面 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAddPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists", departLists);
		return mapping.findForward("addPage");
	}

	/**
	 * <p> to更新页面 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toUppPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String operSign = request.getParameter("operSign");
		String resourceId = request.getParameter("id");
		if("update".equals(operSign)){
			DynaBeanDTO info = meetingServiceImpl.getResourceValue("id",resourceId);
			request.setAttribute("meetingInfo",info);
		}
		return mapping.findForward("addPage");
	}
	
	/**
	 * <p> to删除页面 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toDelPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String operSign = request.getParameter("operSign");
		String resourceId = request.getParameter("id");
		if("delete".equals(operSign)){
		DynaBeanDTO info = meetingServiceImpl.getResourceValue("id",resourceId);
		request.setAttribute("meetingInfo",info);
		}
		return mapping.findForward("delPage");
	}
	
	/**
	 * <p> 会议室申请页面 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAdpplyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List resourceInfo = meetingServiceImpl.getResourceList("40","");
	
		request.setAttribute("resourceList",resourceInfo);
		return mapping.findForward("applyPage");
	}
	
	/**
	 * <p> 会议室查询首页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("mainPage");
	}

	/**
	 * <p> 会议室使用查询页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        request.setAttribute("meetingRoomNames", meetingServiceImpl.getLabelList());        
		return mapping.findForward("queryPage");
	}
	
	/**
	 * <p> 会议室信息查询 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward roomList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		formDto.set("type","40");
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = request.getParameter("pagestate");
		String page = request.getParameter("pagestop");

		if (pageState == null && page == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("meetingTurning");
			pageInfo = pageTurning.getPage();
			if (pageState != null)
				pageInfo.setState(pageState);
			formDto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(10);
		Object[] meetingInfos = meetingServiceImpl.searhResourceInfo(formDto,pageInfo);
		
		int size  = meetingServiceImpl.getResourceSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);
		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("meetingTurning", papgeTurning);

		request.setAttribute("meetingInfos",meetingInfos);
		return mapping.findForward("roomLstPage");
	}
	
	/**
	 * <p> 会议室添加 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMeetingInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 清空ActionBean
		request.removeAttribute(mapping.getName());
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		try {
			if(meetingServiceImpl.haveSameResourceName(formDto.get("meetingName").toString())){
				request.setAttribute("page", "et.oa.resource.meeting.addfail");
				return mapping.findForward("addPage");
			}			
			meetingServiceImpl.addResource(formDto);
		} catch (Exception e) {
			request.setAttribute("page", "sys.clew.error");
			return mapping.findForward("addPage");
		}
		request.setAttribute("page", "sys.clew.success");
		formDto.reset(mapping, request);
		return mapping.findForward("addPage");
	}

	/**
	 * <p> 会议室申请 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward applyMeeting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 清空ActionBean
		request.removeAttribute(mapping.getName());
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		
		try {
			meetingServiceImpl.addApply(formDto);
		} catch (Exception e) {
			request.setAttribute("page", "sys.clew.error");
			return mapping.findForward("applyPage");
		}
		
		List resourceInfo = meetingServiceImpl.getResourceList("40","");
		
		//会议申请
		if(null != request.getSession().getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION)){
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
			WorkFlowInfo wfi=(WorkFlowInfo)request.getSession().getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			oawfs.nextStep(true,wfi.getId(),ui.getUserName(),"",null,wfi.getMessage());
		}
		request.setAttribute("resourceList",resourceInfo);
		request.setAttribute("page", "sys.clew.success");
		return mapping.findForward("applyPage");
	}

	/**
	 * <p> 会议室修改 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateMeetingInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		 清空 formBean
		request.removeAttribute(mapping.getName());
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		try {
			meetingServiceImpl.updateResourceInfo(formDto);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("page","sys.clew.error");
			return mapping.findForward("deleteOver");
		}
		request.setAttribute("page","sys.clew.update");
		return mapping.findForward("deleteOver");
	}
	
	/**
	 * <p> 会议室删除 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delMeetingInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
//		String resourceId = request.getParameter("id");
		try {
			meetingServiceImpl.deleteResourceInfo(formDto.get("meetingId").toString());
			request.setAttribute("page","sys.clew.delete");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("page","sys.clew.error");
			return mapping.findForward("deleteOver");
		}
		return mapping.findForward("deleteOver");
	}
	
	/**
	 * <p> 会议室使用查询 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sreachMeetingUse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = request.getParameter("pagestate");
		String page = request.getParameter("pagestop");

		if (pageState == null && page == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("meetingTurning");
			pageInfo = pageTurning.getPage();
			if (pageState != null)
				pageInfo.setState(pageState);
			formDto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(10);	
		Object[] meetingUses;
		try {
			meetingUses = meetingServiceImpl.searchResourceUse(formDto,pageInfo);
			request.setAttribute("meetingUses",meetingUses);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int size  = meetingServiceImpl.getResourceSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);
		
//		request.setAttribute("meetingUses",meetingUses);
		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("meetingTurning", papgeTurning);
		return mapping.findForward("listPage");
	}
	
	/**
	 * <p> 人员选择页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toUserList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		String page = request.getParameter("page").toString();
		Object[] userInfos = meetingServiceImpl.getUserList(page, formDto);
		List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists", departLists);
		request.setAttribute("userInfos", userInfos);
		return mapping.findForward("selectEmployee");
	}
	
	/**
	 * <p> 人员选择页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toSelectUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		String page = request.getParameter("page").toString();
		Object[] userInfos = meetingServiceImpl.getUserList(page, formDto);
		List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists", departLists);
		request.setAttribute("userInfos", userInfos);
		return mapping.findForward("selectPeople");
	}

	/* set / get Method */
	public ResourceServiceI getMeetingServiceImpl() {
		return meetingServiceImpl;
	}

	public void setMeetingServiceImpl(ResourceServiceI meetingServiceImpl) {
		this.meetingServiceImpl = meetingServiceImpl;
	}

	public ClassTreeService getDepartTree() {
		return departTree;
	}

	public void setDepartTree(ClassTreeService departTree) {
		this.departTree = departTree;
	}


	public OaWorkFlowService getOawfs() {
		return oawfs;
	}

	
	public void setOawfs(OaWorkFlowService oawfs) {
		this.oawfs = oawfs;
	}

	public ResourceServiceI getCarServiceImpl() {
		return carServiceImpl;
	}

	public void setCarServiceImpl(ResourceServiceI carServiceImpl) {
		this.carServiceImpl = carServiceImpl;
	}

}
