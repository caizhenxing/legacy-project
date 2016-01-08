/* 包    名：et.bo.stat.action
 * 文 件 名：MedicInfoForNavigatorAction.java
 * 注释时间：2008-8-28 11:26:18
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

import et.bo.stat.service.MedicInfoForNavigatorService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * The Class MedicInfoForNavigatorAction.
 * 医疗之导诊量统计
 * @author Wang Lichun
 */
public class MedicInfoForNavigatorAction extends BaseAction {
	private MedicInfoForNavigatorService service;

	/**
	 * Gets the service.
	 * The service type is MedicInfoForNavigatorService.
	 * @return the service
	 */
	public MedicInfoForNavigatorService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is MedicInfoForNavigatorService.
	 * @param service the new service
	 */
	public void setService(MedicInfoForNavigatorService service) {
		this.service = service;
	}

	/**
	 * To main.
	 * 
	 * @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * 
	 * @return the action forward
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * 执行该方法，显示医疗之导诊量统计的条件输入界面.
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
		String chartType = (String) dto.get("chartType");

		if (chartType.equals("")) {		
			String agent = (String)dto.get("navigator");
			if(agent!=null&&!"".equals(agent.trim()))
			{
				request.setAttribute("cTitle", "导诊员");
			}
			else
			{
				request.setAttribute("cTitle", "导诊时间");
			}
			List<DynaBeanDTO> list=service.query(dto);
			request.setAttribute("list", list);
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
}
