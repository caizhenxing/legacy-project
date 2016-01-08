package et.bo.oa.resource.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.resource.service.ResourceServiceI;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p> 车辆管理 Action </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-17
 * 
 */
public class CarAction extends BaseAction {

	private ResourceServiceI meetingServiceImpl = null;
	
	private ResourceServiceI carServiceImpl = null;
	
	private ClassTreeService departTree = null;
	
	public CarAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> to车辆申请页面 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toApplyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		String page = request.getParameter("pageSign").toString();

		// 车辆查询
		if("addCarPage".equals(page)){
			DynaBeanDTO info = new DynaBeanDTO();
			info = carServiceImpl.getResourceValue("id",formDto.getString("code").toString());
			request.setAttribute("carInfo",info.get("carThing").toString());
		}
		List codeList = carServiceImpl.getResourceList("39","");
		request.setAttribute("codeList",codeList);
		return mapping.findForward("applyPage");
	}

	/**
	 * <p> to车辆登记页面 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("addPage");
	}
	
	/**
	 * <p> to车辆查询页 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toCarList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		formDto.set("type","39");
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = request.getParameter("pagestate");
		String page = request.getParameter("pagestop");

		if (pageState == null && page == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("carTurning");
			pageInfo = pageTurning.getPage();
			if (pageState != null)
				pageInfo.setState(pageState);
			formDto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(5);
		Object[] carInfos = carServiceImpl.searhResourceInfo(formDto,pageInfo);
		
		int size  = carServiceImpl.getResourceSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);
		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("carTurning", papgeTurning);
      
		request.setAttribute("carInfos",carInfos);
		return mapping.findForward("carList");
	}
	
	/**
	 * <p> to车辆信息更新页 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uppCarInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String operSign = request.getParameter("operSign");
		String resourceId = request.getParameter("id");
		if("update".equals(operSign)){
			DynaBeanDTO info = carServiceImpl.getResourceValue("id",resourceId);
			request.setAttribute("resourceInfo",info);
		}
		return mapping.findForward("addPage");
	}
	/**
	 * <p> to车辆信息删除页面 </p>
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
		DynaBeanDTO info = carServiceImpl.getResourceValue("id",resourceId);
		request.setAttribute("resourceInfo",info);
		}
		return mapping.findForward("delPage");
	}
	
	/**
	 * <p> to用车审批页 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toApprovePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String operSign = request.getParameter("operSign");
		String resourceId = request.getParameter("id");
		if("update".equals(operSign)){
			DynaBeanDTO info = carServiceImpl.getResourceValue("id",resourceId);
			request.setAttribute("resourceInfo",info);
		}
		return mapping.findForward("addPage");
	}
	
	/**
	 * <p> 添加申请信息 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addApplyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 清空 formBean
		request.removeAttribute(mapping.getName());
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		List codeList = null;
		try{
			carServiceImpl.addApply(formDto);
			codeList = carServiceImpl.getResourceList("39","");
		}
		catch(Exception e){
			request.setAttribute("page","sys.clew.error");
			return mapping.findForward("applyPage");
		}
		request.setAttribute("codeList",codeList);
		request.setAttribute("page","sys.clew.success");
		return mapping.findForward("applyPage");
	}
	
	/**
	 * <p> 添加车辆信息 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addCarInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 清空 formBean
		request.removeAttribute(mapping.getName());
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		
		try
		{
			if(carServiceImpl.haveSameResourceName(formDto.get("carCode").toString())){
				request.setAttribute("page", "et.oa.resource.car.addFail");
				return mapping.findForward("addPage");
			}		
		carServiceImpl.addResource(formDto);
		}catch(Exception e){
			request.setAttribute("page","sys.clew.error");
			return mapping.findForward("addPage");
		}
		request.setAttribute("page","sys.clew.success");
		return mapping.findForward("addPage");
	}

	/**
	 * <p> 车辆信息更新 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateCarInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		 清空 formBean
		request.removeAttribute(mapping.getName());
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		try {
//			if(carServiceImpl.haveSameResourceName(formDto.get("carCode").toString())){
//				request.setAttribute("page", "et.oa.resource.car.addFail");
//				return mapping.findForward("addPage");
//			}
			carServiceImpl.updateResourceInfo(formDto);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("page","sys.clew.update");
		return mapping.findForward("addPage");
	}
	
	/**
	 * <p> 车辆信息删除 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delCarInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
//		String resourceId = request.getParameter("id");
		carServiceImpl.deleteResourceInfo(formDto.get("carId").toString());
		request.setAttribute("page", "sys.clew.delete");
//		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		formDto.set("type","39");
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = request.getParameter("pagestate");
		String page = request.getParameter("pagestop");

		if (pageState == null && page == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("carTurning");
			pageInfo = pageTurning.getPage();
			if (pageState != null)
				pageInfo.setState(pageState);
			formDto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(5);
		Object[] carInfos = carServiceImpl.searhResourceInfo(formDto,pageInfo);
		
		int size  = carServiceImpl.getResourceSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);
		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("carTurning", papgeTurning);
      
		request.setAttribute("carInfos",carInfos);
		return mapping.findForward("carList");
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

	/* set / get Method  */
	public ResourceServiceI getCarServiceImpl() {
		return carServiceImpl;
	}

	public void setCarServiceImpl(ResourceServiceI carServiceImpl) {
		this.carServiceImpl = carServiceImpl;
	}

	public ClassTreeService getDepartTree() {
		return departTree;
	}

	public void setDepartTree(ClassTreeService departTree) {
		this.departTree = departTree;
	}

	public ResourceServiceI getMeetingServiceImpl() {
		return meetingServiceImpl;
	}

	public void setMeetingServiceImpl(ResourceServiceI meetingServiceImpl) {
		this.meetingServiceImpl = meetingServiceImpl;
	}
}
