/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.custinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.custinfo.service.CustinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>
 * 客户管理action
 * </p>
 * 
 * @version 2008-03-28
 * @author nie
 */

public class CustinfoAction extends BaseAction {

	static Logger log = Logger.getLogger(CustinfoAction.class.getName());

	private CustinfoService custinfoService = null;

	private ClassTreeService cts = null;

	// 注入service
	public CustinfoService getCustinfoService() {
		return custinfoService;
	}

	public void setCustinfoService(CustinfoService custinfoService) {
		this.custinfoService = custinfoService;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	/**
	 * 根据URL参数执行 toCustinfoMain 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toCustinfoMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String tel = request.getParameter("tel");
		if(tel != null)
			request.getSession().setAttribute("telnum", tel);
		return map.findForward("main");

	}

	/**
	 * 根据URL参数执行 toCustinfoQuery 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回查询页面
	 */
	public ActionForward toCustinfoQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
		List user=custinfoService.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
		request.setAttribute("user", user);
		return map.findForward("query");

	}

	/**
	 * 根据URL参数执行 toCustinfoList 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toCustinfoList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Object o = request.getSession().getAttribute("telnum");
		
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		
		String tel = "";
		if(o != null) {
			tel = o.toString();
			dto.set("cust_tel_home", tel);
			request.getSession().removeAttribute("telnum");
		}
		
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("userpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(14);
		// 取得list及条数
		List list = custinfoService.custinfoQuery(dto, pageInfo);
		int size = custinfoService.getCustinfoSize();

		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", map, request);
		request.getSession().setAttribute("userpageTurning", pt);

		return map.findForward("list");
	}

	/**
	 * 根据URL参数执行 toAllList 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toAllList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List list = custinfoService.custinfoAllQuery();
		request.setAttribute("list", list);

		return map.findForward("alllist");
	}

	/**
	 * 根据URL参数执行 toAllList 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toAllExpertList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List list = custinfoService.custinfoExpertAllQuery();
		request.setAttribute("list", list);

		return map.findForward("allExpertList");
	}

	/**
	 * 根据URL参数执行 toCustinfoLoad 方法，返回要forward页面。 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toCustinfoLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		// 根本type参数判断执行所需要的操作
		if (type.equals("insert")) {

			// request.setAttribute(map.getName(),dto);
			return map.findForward("load");
		}

		if (type.equals("detail")) {

			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		if (type.equals("update")) {

			request.setAttribute("opertype", "update");
			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		if (type.equals("delete")) {

			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		return map.findForward("load");
	}

	/**
	 * 根据URL参数执行 toCustinfoOper 方法，返回要forward页面。 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toCustinfoOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		UserBean ub = (UserBean) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		String userid = ub.getUserId();

		dto.set("cust_rid", userid);

		if (type.equals("insert")) {
			try {

				custinfoService.addCustinfo(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");

			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}

		if (type.equals("update")) {
			try {

				boolean b = custinfoService.updateCustinfo(dto);
				if (b == true) {
					request.setAttribute("operSign", "修改成功");
					return map.findForward("load");
				} else {
					request.setAttribute("operSign", "修改失败");
					return map.findForward("load");
				}
			} catch (RuntimeException e) {
				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}

		if (type.equals("delete")) {
			try {
				// custinfoService.delCustinfo((String)dto.get("cust_id"));//这句可是真的删除了啊！
				// 下面这块是标记删除
				// boolean b = custinfoService.isDelete((String)
				// dto.get("cust_id"));
				custinfoService.delCustinfo((String) dto.get("cust_id"));
				// if (b == true)
				// {
				request.setAttribute("operSign", "删除成功");
				return map.findForward("load");
				// }
				// else
				// {
				// request.setAttribute("operSign", "删除失败");
				// return map.findForward("load");
				// }
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}
		return map.findForward("load");
	}

	/**
	 * 根据URL参数执行 toCustinfoMain 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toPhoneMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return map.findForward("phonemain");
	}

	/**
	 * 根据URL参数执行 toCustinfoQuery 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回查询页面
	 */
	public ActionForward toPhoneQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List typeList = cts.getLabelVaList("phonebook");
		request.setAttribute("typeList", typeList);
		return map.findForward("phonequery");
	}

	/**
	 * 根据URL参数执行 toCustinfoList 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toPhoneList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		//System.out.println("pageState=" + pageState);
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("userpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(20);
		// 取得list及条数
		List list = custinfoService.phoneQuery(dto, pageInfo);
		int size = custinfoService.getPhoneSize();
		System.out.println("size=" + size);
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", map, request);
		request.getSession().setAttribute("userpageTurning", pt);

		return map.findForward("phonelist");
	}

	/**
	 * 根据URL参数执行 toCustinfoLoad 方法，返回要forward页面。 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toPhoneLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("phonebook");
		request.setAttribute("typeList", typeList);

		// 根本type参数判断执行所需要的操作
		if (type.equals("insert")) {
			return map.findForward("phoneload");
		}

		if (type.equals("detail")) {
			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("phoneload");
		}

		if (type.equals("update")) {
			request.setAttribute("opertype", "update");
			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("phoneload");
		}
		if (type.equals("delete")) {
			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("phoneload");
		}

		return map.findForward("phoneload");
	}

	/**
	 * 根据URL参数执行 toCustinfoOper 方法，返回要forward页面。 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toPhoneOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("phonebook");
		request.setAttribute("typeList", typeList);

		if (type.equals("insert")) {
			try {
				custinfoService.addCustinfo(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("phoneload");
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}

		if (type.equals("update")) {
			try {
				boolean b = custinfoService.updateCustinfo(dto);
				if (b == true) {
					request.setAttribute("operSign", "修改成功");
					return map.findForward("phoneload");
				} else {
					request.setAttribute("operSign", "修改失败");
					return map.findForward("phoneload");
				}
			} catch (RuntimeException e) {
				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}

		if (type.equals("delete")) {
			try {
				// custinfoService.delCustinfo((String)dto.get("cust_id"));//这句可是真的删除了啊！

				// 下面这块是标记删除
				// boolean b = custinfoService.isDelete((String)
				// dto.get("cust_id"));
				custinfoService.delCustinfo((String) dto.get("cust_id"));

				request.setAttribute("operSign", "删除成功");
				return map.findForward("phoneload");

			} catch (RuntimeException e) {
				e.printStackTrace();
				return map.findForward("error");
			}
		}

		return map.findForward("phoneload");
	}
}
