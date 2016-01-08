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

import et.bo.stat.service.IvrStatService;
import et.bo.stat.service.TelService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * @author chen gang
 * 
 */
public class IvrAction extends BaseAction {
	private IvrStatService is;



	public ActionForward toIvrMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("ivrmain");
	}

	/**
	 * 执行该方法，显示IVR模块点率量统计的条件输入界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toIvrQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("ivrquery");
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
	public ActionForward toIvrDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
//		System.out.println("qtype = "+dto.get("qtype"));
		request.setAttribute("querytype", dto.get("qtype"));
		String chartType = (String) dto.get("chartType");
		is.initIvrModele();
		if (chartType.equals("")) {
			List<DynaBeanDTO> list=is.query(dto);
			request.setAttribute("list", list);
			return mapping.findForward("ivrreport");
		} else {
			JFreeChart chart = is.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}

	public IvrStatService getIs() {
		return is;
	}

	public void setIs(IvrStatService is) {
		this.is = is;
	}
}
