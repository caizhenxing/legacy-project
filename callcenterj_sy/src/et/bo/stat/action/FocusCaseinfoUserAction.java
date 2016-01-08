/* ��    ����et.bo.stat.action
 * �� �� ����FocusCaseinfoUserAction.java
 * ע��ʱ�䣺2008-8-28 11:07:16
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
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
 * ���㰸������ϯ������ͳ��Action
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
	 * ִ�и÷�������ʾ��ϯ������ͳ�Ƶ������������.
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
	 * ����ͳ��������ִ����Ӧ�ĵ�ͳ�Ʒ�������ͳ�ƽ�����Ե�displayҳ��.
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
				request.setAttribute("cTitle", "������");
			}
			else
			{
				request.setAttribute("cTitle", "����ʱ��");
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
