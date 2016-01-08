/**
 * 	@(#) FocusCaseInfoStatAction.java 2008-4-11 下午01:09:12
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.stat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.JFreeChart;

import et.bo.stat.service.PriceStatByProductService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author chen gang
 * 
 */
public class PriceStatByProductAction extends BaseAction {
	private PriceStatByProductService service;

	public PriceStatByProductService getService() {
		return service;
	}

	public void setService(PriceStatByProductService service) {
		this.service = service;
	}

	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * 执行该方法，显示座席电话量统计的条件输入界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("query");
	}

	/**
	 * 接受统计条件，执行相应的的统计方法，将统计结果回显到display页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = (String) dto.get("chartType");
		String dictProductType1 = request.getParameter("dictProductType1");
		String dictProductType2 = request.getParameter("dictProductType2");
		dto.set("dictProductType1", dictProductType1);
		dto.set("dictProductType2", dictProductType2);
		String productName = request.getParameter("productName");
		dto.set("productName", productName);
		if (chartType.equals("")) {
			List<DynaBeanDTO> list=service.query(dto);
			request.setAttribute("list", list);
			if(productName!=null&&!"".equals(productName.trim()))
			{
				
				request.setAttribute("cTitle", "评价时间");
			}
			else
			{
				request.setAttribute("cTitle", "产品名称");
			}
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
}
