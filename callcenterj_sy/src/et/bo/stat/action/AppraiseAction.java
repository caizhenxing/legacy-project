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

import et.bo.stat.service.AppraiseService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author chen gang
 * 
 */
public class AppraiseAction extends BaseAction {
	private AppraiseService service;



	public ActionForward toIvrMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * ִ�и÷�������ʾIVRģ�������ͳ�Ƶ������������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toIvrQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("user", service.userQuery());
		return mapping.findForward("query");
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
	public ActionForward toIvrDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			String type = dto.get("qtype")==null?"":dto.get("qtype").toString();
			if("".equals(type.trim()))
			{
				request.setAttribute("statType", "��ϯ����");
			}
			else if("agent".equals(type.trim()))
			{
				request.setAttribute("statType", "��ϯ����");
			}
			else
			{
				request.setAttribute("statType", "ר�Һ���");
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

	public AppraiseService getService() {
		return service;
	}

	public void setService(AppraiseService service) {
		this.service = service;
	}

}
