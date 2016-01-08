package et.bo.oa.message.addrList.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.message.addrList.service.AddrListServiceI;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p> 通讯录Action </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-11
 * 
 */
public class AddrListAction extends BaseAction {

	private AddrListServiceI addrListService = null;

//	 树状结构，注入
	private ClassTreeService ctree = null;
	
	private ClassTreeService departTree = null;
	
	public AddrListAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionForward toAddrMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("main");
	}

	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List departLists = departTree.getLabelList("1");

		request.setAttribute("departList",departLists);
		return mapping.findForward("query");
	}

	/**
	 * <p> 通讯录信息查询 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAddrList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		formDto.set("departId", departTree.getvaluebyId(formDto.get("departId").toString()));
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = request.getParameter("pagestate");
		String page = request.getParameter("pagestop");

		if (pageState == null && page == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("messageTurning");
			pageInfo = pageTurning.getPage();
			if (pageState != null)
				pageInfo.setState(pageState);
			formDto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(10);
		Object[] addressLists = addrListService.getAddrList(formDto, pageInfo);
		int size = addrListService.AddrListSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);
		request.setAttribute("addressLists", addressLists);

		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/",
				mapping, request);
		request.getSession().setAttribute("messageTurning", papgeTurning);

		return mapping.findForward("list");
	}

	/**
	 * <p>  通讯录详细信息 </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addrListInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String employeeId = request.getParameter("employeeId");
		Object[] employeeObjs = addrListService.getAddrInfo(employeeId);
		request.setAttribute("employeeObjs", employeeObjs);

		return mapping.findForward("addrListInfo");
	}

	/*
	 * set / get method
	 * */
	public AddrListServiceI getAddrListService() {
		return addrListService;
	}

	public void setAddrListService(AddrListServiceI addrListService) {
		this.addrListService = addrListService;
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	
	public ClassTreeService getDepartTree() {
		return departTree;
	}

	public void setDepartTree(ClassTreeService departTree) {
		this.departTree = departTree;
	}

	
}
