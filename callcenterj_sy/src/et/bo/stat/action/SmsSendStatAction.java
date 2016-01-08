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


import et.bo.stat.service.SmsSendStatService;
import et.bo.stat.service.TelService;
import et.bo.stat.service.BookMedicinfoPService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author jingyuzhuo
 * 
 */
public class SmsSendStatAction extends BaseAction {
	private SmsSendStatService smsSendStatService;
	public ActionForward toSmsStatMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("toSmsStatMain");
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
	public ActionForward toSmsStatQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("toSmsStatQuery");
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
	public ActionForward toSmsStatReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			List<DynaBeanDTO> list=smsSendStatService.query(dto);
			request.setAttribute("list", list);
			return mapping.findForward("toSmsStatReport");
		} else {
			JFreeChart chart = smsSendStatService.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}

	public SmsSendStatService getSmsSendStatService() {
		return smsSendStatService;
	}

	public void setSmsSendStatService(SmsSendStatService smsSendStatService) {
		this.smsSendStatService = smsSendStatService;
	}




}
