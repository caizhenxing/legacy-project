/* 包    名：et.bo.stat.action
 * 文 件 名：PriceInfoAction.java
 * 注释时间：2008-8-28 11:27:26
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

import et.bo.stat.service.PriceInfoService;
import et.bo.sys.common.SysStaticParameter;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class PriceInfoAction.
 * 农产品价格之坐席受理量统计。
 * @author Wang Lichun
 */
public class PriceInfoAction extends BaseAction {
	private PriceInfoService service;

	/**
	 * Gets the service.
	 * The service type is PriceInfoService.
	 * @return the service
	 */
	public PriceInfoService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is PriceInfoService.
	 * @param service the new service
	 */
	public void setService(PriceInfoService service) {
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
		List user=service.userQuery(SysStaticParameter.QUERY_USER_SQL);
		request.setAttribute("user", user);
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
//		System.out.println("priceInfoaction "+chartType);
		if (chartType.equals("")) {
			String name=dto.getString("case_id").toString();
			if(name.equals("")||name==null){
				request.setAttribute("name","null");
			}else{
				request.setAttribute("name","notnull");
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
