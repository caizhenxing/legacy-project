/**
 * 	@(#) TelAction.java 2008-4-14 ����01:09:12
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
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
	 * ִ�и÷�������ʾԤԼҽ�Ʒ�����ͳ�Ƶ������������
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
	 * ����ͳ��������ִ����Ӧ�ĵ�ͳ�Ʒ�������ͳ�ƽ�����Ե�displayҳ��
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
