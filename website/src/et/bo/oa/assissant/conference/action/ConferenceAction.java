package et.bo.oa.assissant.conference.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.assissant.conference.service.ConferenceService;
import et.bo.oa.checkwork.service.AbsenceServiceI;
import et.bo.oa.resource.service.ResourceServiceI;
import et.bo.oa.workflow.service.OaWorkFlowService;
import et.bo.oa.workflow.service.WorkFlowInfo;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class ConferenceAction extends BaseAction {

	private ConferenceService conferenceService =null;
	private AbsenceServiceI absenceService = null;
	private ResourceServiceI meetingServiceImpl = null;
	private OaWorkFlowService oawfs=null;
	/**
	 * @return Returns the oawfs.
	 */
	public OaWorkFlowService getOawfs() {
		return oawfs;
	}

	/**
	 * @param oawfs The oawfs to set.
	 */
	public void setOawfs(OaWorkFlowService oawfs) {
		this.oawfs = oawfs;
	}

	/**
	 * @return Returns the meetingServiceImpl.
	 */
	public ResourceServiceI getMeetingServiceImpl() {
		return meetingServiceImpl;
	}

	/**
	 * @param meetingServiceImpl The meetingServiceImpl to set.
	 */
	public void setMeetingServiceImpl(ResourceServiceI meetingServiceImpl) {
		this.meetingServiceImpl = meetingServiceImpl;
	}

	private boolean isOwner(HttpServletRequest request, String userId)
	{
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		if(null ==ui || null ==userId)
		{
			return false;
		}
		String userIdSession =ui.getUserName();
		if(userIdSession.equals(userId))
		{
			return true;
		}
		return false;
	}
	
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		return new ActionForward("/oa/assissant/conference/manage/main.jsp");
    }
	
	public ActionForward toExamMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		return new ActionForward("/oa/assissant/conference/manage/examMain.jsp");
    }
	
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {		
		List al =meetingServiceImpl.getResourceList("40","");
		request.setAttribute("al",al);
		return new ActionForward("/oa/assissant/conference/manage/toSearch.jsp");
    }
	
	public ActionForward toExamSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {			
		return new ActionForward("/oa/assissant/conference/manage/toExamSearch.jsp");
    }
	
	public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		//TODO
		String oper =request.getParameter("oper");
		if(null !=oper)
		{
			request.setAttribute("oper",oper);
		}
		//TODO
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("conferTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dform = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(3);
        //TODO
        List l =this.conferenceService.searchC(dform,pageInfo);
        int size =this.conferenceService.searchCSize(dform, pageInfo);
        
        pageInfo.setRowCount(size);
        pageInfo.setQl(dform);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",mapping,request);
        request.getSession().setAttribute("conferTurning",pt);
		return new ActionForward("/oa/assissant/conference/manage/searchResult.jsp");
    }
	
	public ActionForward searchExam(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		//TODO
		String oper =request.getParameter("oper");
		if(null !=oper)
		{
			request.setAttribute("oper",oper);
		}
		//TODO
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("conferExamTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dform = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(3);
        //TODO
        List l =this.conferenceService.searchEC(dform,pageInfo);
        int size =this.conferenceService.searchECSize(dform, pageInfo);
        
        pageInfo.setRowCount(size);
        pageInfo.setQl(dform);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",mapping,request);
        request.getSession().setAttribute("conferExamTurning",pt);
		return new ActionForward("/oa/assissant/conference/manage/searchExamResult.jsp");
    }
	
	public ActionForward toLoad(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String type =request.getParameter("type");
		request.setAttribute("type",type);
		List al =meetingServiceImpl.getResourceList("40","");
		request.setAttribute("al",al);
		if("i".equals(type))
		{			
			return new ActionForward("/oa/assissant/conference/manage/info.jsp");
		}
		else if("u".equals(type))
		{
//			String did =request.getParameter("did");
			WorkFlowInfo wfi=(WorkFlowInfo)request.getSession().getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			String did =wfi.getMessage();
			IBaseDTO fdto =this.conferenceService.loadC(did);
			request.setAttribute(mapping.getName(),fdto);
			return new ActionForward("/oa/assissant/conference/manage/info.jsp");
		}
		else if("d".equals(type))
		{
			String did =request.getParameter("did");
			IBaseDTO fdto =this.conferenceService.loadC(did);
			request.setAttribute(mapping.getName(),fdto);
			return new ActionForward("/oa/assissant/conference/manage/info.jsp");
		}
		else if("exam".equals(type))
		{
//			String did =request.getParameter("did");
			WorkFlowInfo wfi=(WorkFlowInfo)request.getSession().getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			String did =wfi.getMessage();
			IBaseDTO fdto =this.conferenceService.loadC(did);
			request.setAttribute(mapping.getName(),fdto);
			return new ActionForward("/oa/assissant/conference/manage/infoExam.jsp");
		}
		else if("end".equals(type))
		{
//			String did =request.getParameter("did");
			WorkFlowInfo wfi=(WorkFlowInfo)request.getSession().getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			String did =wfi.getMessage();
			IBaseDTO fdto =this.conferenceService.loadC(did);
			request.setAttribute(mapping.getName(),fdto);
			return new ActionForward("/oa/assissant/conference/manage/infoEnd.jsp");
		} 
		else if("l".equals(type))
		{
			//TODO
//			return new ActionForward("/");
			String did =request.getParameter("did");
			IBaseDTO fdto =this.conferenceService.loadC(did);
			request.setAttribute(mapping.getName(),fdto);
			return new ActionForward("/oa/assissant/conference/manage/info.jsp");
		}
		else
		{
			return new ActionForward("/oa/assissant/conference/manage/info.jsp");
		}
    }

	
	public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		String userId ="";
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		try {
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
			userId =ui.getUserName();
			dto.set("userid",userId);
//			dto.set("synodOwner",userId);
			this.conferenceService.insertC(dto);
			
				return mapping.findForward("success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
		
    }
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		String did =(String)dto.get("id");
		try {
			boolean flag =this.conferenceService.deleteC(did);
			return mapping.findForward("success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}		
    }
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		try {
			boolean flag =this.conferenceService.updateC(dto);
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
			WorkFlowInfo wfi=(WorkFlowInfo)request.getSession().getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);
			oawfs.nextStep(true,wfi.getId(),ui.getUserName(),null,null,wfi.getMessage());
			return mapping.findForward("success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
    }
	
	public ActionForward toSelectUser(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		String page = request.getParameter("page").toString();
		Object[] userInfos = absenceService.getUserList(page,formDto);
		request.setAttribute("userInfos",userInfos);
		return new ActionForward("/oa/assissant/conference/manage/selectEmployee.jsp");
    }
	
	public ActionForward toSelectOwnerUser(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		String page = request.getParameter("page").toString();
		Object[] userInfos = absenceService.getUserList(page,formDto);
		request.setAttribute("userInfos",userInfos);
		return new ActionForward("/oa/assissant/conference/manage/selectOwnerEmployee.jsp");
    }
	
	public ActionForward exam(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		String userId ="";
		try {
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
			//TODO
			WorkFlowInfo wfi=(WorkFlowInfo)request.getSession().getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);	
			oawfs.nextStep(true,wfi.getId(),ui.getUserName(),(String)formDto.get("examResult"),null,wfi.getMessage());
			//TODO
			userId =ui.getUserName();
//			
			formDto.set("examId",userId);
			formDto.set("flowId",wfi.getWfInsid());
			String did =request.getParameter("did");
			boolean flag =this.conferenceService.examine(did,formDto);
			return mapping.findForward("success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}		
    }
	
	public ActionForward end(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		try {
			//TODO 结束标识位、会议纪要
			String id =(String)formDto.get("id");
			boolean flag =conferenceService.endOfConference(id, formDto);
			//TODO 工作流
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
			WorkFlowInfo wfi=(WorkFlowInfo)request.getSession().getAttribute(SysStaticParameter.WORKFLOW_IN_SESSION);	
			oawfs.nextStep(true,wfi.getId(),ui.getUserName(),null,null,wfi.getMessage());
			return mapping.findForward("success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("success");
		}
		
		
    }
	/**
	 * @return Returns the conferenceService.
	 */
	public ConferenceService getConferenceService() {
		return conferenceService;
	}

	/**
	 * @param conferenceService The conferenceService to set.
	 */
	public void setConferenceService(ConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	/**
	 * @return Returns the absenceService.
	 */
	public AbsenceServiceI getAbsenceService() {
		return absenceService;
	}

	/**
	 * @param absenceService The absenceService to set.
	 */
	public void setAbsenceService(AbsenceServiceI absenceService) {
		this.absenceService = absenceService;
	}
	
	
}
