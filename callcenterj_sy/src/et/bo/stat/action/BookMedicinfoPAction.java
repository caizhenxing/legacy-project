/**
 * 	@(#) TelAction.java 2008-4-14 下午01:09:12
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

import et.bo.stat.service.TelService;
import et.bo.stat.service.BookMedicinfoPService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author jingyuzhuo
 * 
 */
public class BookMedicinfoPAction extends BaseAction {
	private BookMedicinfoPService bmps;



	public ActionForward tobookMedicinfoPMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("tobookMedicinfoPMain");
	}

	/**
	 * 执行该方法，显示预约医疗访问量统计的条件输入界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward tobookMedicinfoPQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("tobookMedicinfoPQuery");
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
	public ActionForward tobookMedicinfoPDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			List<DynaBeanDTO> list=bmps.query(dto);
			request.setAttribute("list", list);
			return mapping.findForward("bookMedicinfoPreport");
		} else {
			JFreeChart chart = bmps.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}



	public BookMedicinfoPService getBmps() {
		return bmps;
	}

	public void setBmps(BookMedicinfoPService bmps) {
		this.bmps = bmps;
	}
}
