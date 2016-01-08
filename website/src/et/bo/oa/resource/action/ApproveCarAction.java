package et.bo.oa.resource.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.resource.service.ResourceServiceI;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p> 车辆审批Action </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-17
 * 
 */
public class ApproveCarAction extends BaseAction {

	private ResourceServiceI carServiceImpl = null;
	
	public ApproveCarAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> to审批查询页 </p>
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
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;

		if((formDto.get("approveType") == null) || "".equals(formDto.get("approveType").toString())){
			formDto.set("approveType","all");
		}
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
		Object[] useObjs = carServiceImpl.searchResourceUse(formDto,pageInfo);
		int size  = carServiceImpl.getResourceSize();
		
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);
		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("carTurning", papgeTurning);
        request.setAttribute("carUserLists",useObjs);
		return mapping.findForward("approvceList");
	}

	/**
	 * <p> 申请审批详细信息 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ApprovePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id").toString();
		DynaBeanDTO useList = carServiceImpl.getResourceUse("id",id);
		request.setAttribute("sign","approvce");
		request.setAttribute("useList",useList);
		return mapping.findForward("applyPage");
	}
	
	/**
	 * <p> 申请审批详细信息 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		List codeList = carServiceImpl.getResourceList("39","");
		request.setAttribute("codeList",codeList);
		if(formDto.get("approveType").toString().equals("")){
//			request.setAttribute("approve","sys.clew.approveSuccess");
			return mapping.findForward("toApproveList");
		}
		try {
			carServiceImpl.approvceResource(formDto);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("approve","sys.clew.approveError");
			return mapping.findForward("toApproveList");
		}
		request.setAttribute("approve","sys.clew.approveSuccess");
		return mapping.findForward("toApproveList");
	}
	
	/* set/get Method */
	public ResourceServiceI getCarServiceImpl() {
		return carServiceImpl;
	}

	public void setCarServiceImpl(ResourceServiceI carServiceImpl) {
		this.carServiceImpl = carServiceImpl;
	}
}
