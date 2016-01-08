/* 包    名：et.bo.stat.action
 * 文 件 名：FocusCaseinfoUserAction.java
 * 注释时间：2008-8-28 11:07:16
 * 版权所有：沈阳市卓越科技有限公司。
 */

package et.bo.stat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.JFreeChart;

import et.bo.stat.service.FocusCaseInfoUserService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class FocusCaseinfoUserAction.
 * 焦点案例库座席受理量统计Action
 * @author Wang Lichun
 */
public class FocusCaseinfoUserAction extends BaseAction {
	private FocusCaseInfoUserService service;

	/**
	 * Gets the service.
	 * The service type is FocusCaseInfoUserService.
	 * @return the service
	 */
	public FocusCaseInfoUserService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is FocusCaseInfoUserService.
	 * @param service the new service
	 */
	public void setService(FocusCaseInfoUserService service) {
		this.service = service;
	}

	/**
	 * To main.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * 执行该方法，显示座席受理量统计的条件输入界面.
	 * 
	 * @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * 
	 * @return the action forward
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("query");
	}

	/**
	 * 接受统计条件，执行相应的的统计方法，将统计结果回显到display页面.
	 * 
	* @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * 
	 * @return the action forward
	 */
	public ActionForward toDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
//		System.out.println("AAAA");
//		System.out.println(dto.get("beginTime"));
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			List<DynaBeanDTO> list=service.query(dto);
			if(dto.get("userId")!=null&&!"".equals(dto.get("userId")))
			{
				request.setAttribute("cTitle", "受理工号");
			}
			else
			{
				request.setAttribute("cTitle", "受理时间");
			}
			request.setAttribute("list", list);
//			request.setAttribute("condition", dto.get("condition"));
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
}
