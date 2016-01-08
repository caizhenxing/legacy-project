/* 包    名：et.bo.stat.action
 * 文 件 名：InquiryInfoByOrganizationAction.java
 * 注释时间：2008-8-28 11:22:40
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

import et.bo.stat.service.InquiryInfoByOrganizationService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.classtree.impl.ClassTreeServiceImpl;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class InquiryInfoByOrganizationAction.
 * 专题调查库之各机构发起的调查次数统计
 * @author Wang Lichun
 */
public class InquiryInfoByOrganizationAction extends BaseAction {
	private InquiryInfoByOrganizationService service;
	private ClassTreeService cts = new ClassTreeServiceImpl();

	/**
	 * Gets the service.
	 * The service Type is InquiryInfoByOrganizationService.
	 * @return the service
	 */
	public InquiryInfoByOrganizationService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service Type is InquiryInfoByOrganizationService.
	 * @param service the new service
	 */
	public void setService(InquiryInfoByOrganizationService service) {
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
	 * 执行该方法，显示各机构发起的调查次数统计的条件输入界面.
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
		
//		List evaluatingList = cts.getLabelVaList("evaluating");   
//        request.setAttribute("evaluatingList", evaluatingList);
		
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
			List<DynaBeanDTO> list=service.query(dto);
			
			if(!"".equals(dto.get("name").toString()))
				request.setAttribute("date", "notnull");
			else
				request.setAttribute("date", "null");
			
			request.setAttribute("list", list);
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
}
