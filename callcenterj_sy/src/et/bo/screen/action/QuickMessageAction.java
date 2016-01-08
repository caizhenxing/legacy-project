/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.screen.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.screen.service.QuickMessageService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>
 * 客户管理action
 * </p>
 * 
 * @version 2008-03-28
 * @author nie
 */

public class QuickMessageAction extends BaseAction {

	static Logger log = Logger.getLogger(QuickMessageAction.class.getName());

	private QuickMessageService quickMessageService = null;

	private ClassTreeService cts = null;

	



	public QuickMessageService getQuickMessageService() {
		return quickMessageService;
	}

	public void setQuickMessageService(QuickMessageService quickMessageService) {
		this.quickMessageService = quickMessageService;
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
	public ActionForward toQMMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
	public ActionForward toQMQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return map.findForward("query");

	}

	/**
	 * 根据URL参数执行 toQMList 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toQMList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		
		
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
		pageInfo.setPageSize(20);
		// 取得list及条数
		List list = quickMessageService.quickMessageQuery(dto, pageInfo);
		int size = quickMessageService.getQuickMessageSize();

		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", map, request);
		request.getSession().setAttribute("userpageTurning", pt);
		return map.findForward("alllist");
	}

	/**
	 * 根据URL参数执行 toAllQMList 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toAllQMList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List list = quickMessageService.quickMessageAllQuery();
		request.setAttribute("list", list);

		return map.findForward("alllist");
	}

	/**
	 * 根据URL参数执行 toQMLoad 方法，返回要forward页面。 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toQMLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);


		// 根本type参数判断执行所需要的操作
		if (type.equals("insert")) {

			// request.setAttribute(map.getName(),dto);
			DynaBeanDTO dto = new DynaBeanDTO();
			dto.set("createDate", new java.util.Date());
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		if (type.equals("detail")) {

			String id = request.getParameter("id");
			IBaseDTO dto = quickMessageService.getQuickMessageInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		if (type.equals("update")) {

			request.setAttribute("opertype", "update");
			String id = request.getParameter("id");
			IBaseDTO dto = quickMessageService.getQuickMessageInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		if (type.equals("delete")) {

			String id = request.getParameter("id");
			IBaseDTO dto = quickMessageService.getQuickMessageInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		return map.findForward("load");
	}

	/**
	 * 根据URL参数执行 toQMOper 方法，返回要forward页面。 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toQMOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		if (type.equals("insert")) {
			try {
				UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);					
				dto.set("inputMan", ub.getUserName());//提交人。id：ub.getUserId()					
				quickMessageService.addQuickMessage(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");

			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}

		if (type.equals("update")) {
			try {

				boolean b = quickMessageService.updateQuickMessage(dto);
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
				quickMessageService.delQuickMessage((String) dto.get("id"));
				request.setAttribute("operSign", "删除成功");
				return map.findForward("load");
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}
		return map.findForward("load");
	}
}
