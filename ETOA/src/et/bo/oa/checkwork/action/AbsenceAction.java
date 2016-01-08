package et.bo.oa.checkwork.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import et.bo.oa.checkwork.service.AbsenceServiceI;
import et.bo.oa.checkwork.service.CheckWorkServiceI;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p> 缺勤管理 Action </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-10
 * 
 */

public class AbsenceAction extends BaseAction {

	private AbsenceServiceI absenceService = null;

	private CheckWorkServiceI checkWorkService = null;
	
	// 树状结构，注入
	private ClassTreeService ctree = null;
	
	private ClassTreeService departTree = null;

	private Log logger = LogFactory.getLog(AbsenceAction.class);

	public AbsenceAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> 缺勤查询首页 </p>
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
	 * <p> 请假查询首页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQingjiaMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("qingjiaMain");
	}
	/**
	 * <p> 外出查询首页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toWaichuMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("waichuMain");
	}
	/**
	 * <p> 出差查询首页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toChuchaiMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("chuchaiMain");
	}
	/**
	 * <p> 补签登记页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toResign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List hourBean = absenceService.gethour();
		String shortTime = TimeUtil.getShortTime();
		List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists",departLists);
		request.setAttribute("shortTime",shortTime);
		request.setAttribute("hourBean",hourBean);
		List nameList = checkWorkService.getNameList();
		request.setAttribute("nameList", nameList);
		return mapping.findForward("addResign");
	}

	/**
	 * <p> 缺勤登记页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAbsence(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.removeAttribute(mapping.getName());
		List departLists = departTree.getLabelList("1");
		List absenceTypes = ctree.getLabelVaList("absenceType");		
		request.setAttribute("absenceType", absenceTypes);
		request.setAttribute("departLists",departLists);		
		String type = request.getParameter("operType");
		request.setAttribute("operType",type);		
		List nameList = checkWorkService.getNameList();
		request.setAttribute("nameList", nameList);
		return mapping.findForward("addAbsence");
	}

	/**
	 * <p> 缺勤查询条件页 </p>
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
		String type = request.getParameter("type");
		request.setAttribute("type",type);
		List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists",departLists);
		List absenceTypes = ctree.getLabelVaList("absenceType");
		request.setAttribute("absenceType", absenceTypes);
		return mapping.findForward("absenceQuery");
	}

	/**
	 * <p> 缺勤查询 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward absenceList(ActionMapping mapping, ActionForm form,
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
					.getAttribute("checkworkTurning");
			pageInfo = pageTurning.getPage();
			if (pageState != null)
				pageInfo.setState(pageState);
			formDto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(10);
		Object[] absenceList = absenceService.selectAbsenseList(formDto,pageInfo);
		int size  = absenceService.getAbsenceSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);
		
		request.setAttribute("absenceList",absenceList);
		request.setAttribute("page", "absence");
		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("checkworkTurning", papgeTurning);
        
		return mapping.findForward("absenceList");
	}

	/**
	 * <p> 缺勤登记操作 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAbsence(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.removeAttribute(mapping.getName());
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		List departLists = departTree.getLabelList("1");
		List absenceTypes = ctree.getLabelVaList("absenceType");
		
		request.setAttribute("absenceType", absenceTypes);
		request.setAttribute("departLists",departLists);
		
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		formDto.set("userId",ui.getUserName());
		
		List nameList = checkWorkService.getNameList();
		request.setAttribute("nameList", nameList);
		
		String type = request.getParameter("type");
		request.setAttribute("operType",type);
		try
		{
		absenceService.addAbsence(formDto);
		}catch(Exception e)
		{
			request.setAttribute("page","sys.clew.error");
			return mapping.findForward("addAbsence");
		}
		request.setAttribute("page","sys.clew.success");
		return mapping.findForward("addAbsence");
	}
	
	/**
	 * <p> 补签登记操作 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addResign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.removeAttribute(mapping.getName());
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		List hourBean = absenceService.gethour();
		String shortTime = TimeUtil.getShortTime();
		List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists",departLists);
		request.setAttribute("shortTime",shortTime);
		request.setAttribute("hourBean",hourBean);
		List nameList = checkWorkService.getNameList();
		request.setAttribute("nameList", nameList);
		try
		{
		absenceService.addResign(formDto);
		}
		catch(Exception e)
		{
			request.setAttribute("page","sys.clew.error");
			return mapping.findForward("addResign");
		}
		
		request.setAttribute("page","sys.clew.success");
		return mapping.findForward("addResign");
	}
	
	/**
	 * <p> 人员选择页 </p>
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
		Object[] userInfos = absenceService.getUserList(page,formDto);
		List departLists = departTree.getLabelVaList("1");
		request.setAttribute("departLists",departLists);
		request.setAttribute("userInfos",userInfos);
		return mapping.findForward("selectEmployee");
	}
	
	/**
	 * <p> 外出状态查询 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOutStateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List outStateList = absenceService.getOutStateList();
		request.setAttribute("outStateList",outStateList);
		return mapping.findForward("toGetOutStateList");
	}
	
	/**
	 * <p> 所有人外出状态查询 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllOutStateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		List outStateList = absenceService.getAllOutStateList();
		request.setAttribute("outStateList",outStateList);       
		return mapping.findForward("toGetAllOutStateList");
	}
	
	/* set&get Method start */
	public AbsenceServiceI getAbsenceService() {
		return absenceService;
	}

	public ClassTreeService getDepartTree() {
		return departTree;
	}

	public void setDepartTree(ClassTreeService departTree) {
		this.departTree = departTree;
	}

	public void setAbsenceService(AbsenceServiceI absenceService) {
		this.absenceService = absenceService;
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}
	/* set&get Method end */

	public CheckWorkServiceI getCheckWorkService() {
		return checkWorkService;
	}

	public void setCheckWorkService(CheckWorkServiceI checkWorkService) {
		this.checkWorkService = checkWorkService;
	}

}
