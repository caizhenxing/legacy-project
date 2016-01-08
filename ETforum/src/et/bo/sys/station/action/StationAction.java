/**
 * 	@(#)StationAction.java   Sep 2, 2006 4:32:09 PM
 *	 。 
 *	 
 */
package et.bo.sys.station.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import et.bo.sys.station.service.StationService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Sep 2, 2006
 * @see
 */
public class StationAction extends BaseAction {

	private StationService stationService = null;

	private ClassTreeService ctree = null;

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	public StationService getStationService() {
		return stationService;
	}

	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	/**
	 * @describe 跳转到main页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toStationMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("main");
	}

	/**
	 * @describe 跳转到load页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toStationLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);
		List l = ctree.getLabelList("treeRoot");
		request.setAttribute("ctreelist", l);
		if (type.equals("insert")) {
			return map.findForward("load");
		}
		if (type.equals("update")) {
			String id = request.getParameter("id");
			IBaseDTO dto = stationService.getStationInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		if (type.equals("delete")) {
			String id = request.getParameter("id");
			IBaseDTO dto = stationService.getStationInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		return map.findForward("load");
	}

	/**
	 * @describe 跳转到query页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toStationQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List l = ctree.getLabelList("treeRoot");
		request.setAttribute("ctreelist", l);
		return map.findForward("query");
	}

	/**
	 * @describe 跳转到list页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toStationList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("stationpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			formdto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(10);
		List l = stationService.StationIndex(formdto, pageInfo);
		int size = stationService.getStationSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formdto);
		request.setAttribute("list", l);
		PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
		request.getSession().setAttribute("stationpageTurning", pt);
		return map.findForward("list");
	}

	/**
	 * @describe 跳转到main页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward operStation(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);
		List l = ctree.getLabelList("treeRoot");
		request.setAttribute("ctreelist", l);
		if (type.equals("insert")) {
			stationService.addStationBox(formdto);
			request.setAttribute("idus_state", "sys.savesuccess");
			return map.findForward("load");
		}
		if (type.equals("update")) {
			stationService.updateStationBox(formdto);
			request.setAttribute("idus_state", "sys.updatesuccess");
			return map.findForward("load");
		}
		if (type.equals("delete")) {
			stationService.delStationBox(formdto);
			request.setAttribute("idus_state", "sys.delsuccess");
			return map.findForward("load");
		}
		return map.findForward("load");
	}
	
	/**
	 * @describe 跳转到list页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toMoreStation(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String stationId = request.getParameter("stationid");
		List l = stationService.StationList(stationId);
		request.setAttribute("list", l);
		return map.findForward("morestation");
	}

	/**
	 * 加载树
	 * 
	 * @param
	 * @version Sep 4, 2006
	 * @return
	 */
	public ActionForward station(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			TreeControlI tci = stationService.loadDepartments();
			request.getSession().setAttribute("stationSession",
					tci.getTreeControl());
			return map.findForward("station");
		} catch (Exception e) {
			e.printStackTrace();
			return map.findForward("error");
		}

	}
}
