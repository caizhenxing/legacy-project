/* ��    ����et.bo.stat.action
 * �� �� ����SadInfoForProductAction.java
 * ע��ʱ�䣺2008-8-28 11:28:14
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

import et.bo.stat.service.CaseInfoFroExportService;
import et.bo.stat.service.HZCaseInfoFroExportService;
import et.bo.stat.service.SadInfoFroProductService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class SadInfoForProductAction.
 * ũ��Ʒ����֮��Ʒ������ͳ��
 * @author Wang Lichun
 */
public class SadInfoForProductAction extends BaseAction {
	private SadInfoFroProductService service;

	/**
	 * Gets the service.
	 * The service type is SadInfoFroProductService.
	 * @return the service
	 */
	public SadInfoFroProductService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is SadInfoFroProductService.
	 * @param service the new service
	 */
	public void setService(SadInfoFroProductService service) {
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
	 * ִ�и÷�������ʾ��Ʒ������ͳ�Ƶ������������.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
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
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			String proName=dto.getString("proName").toString();
			if(proName.equals("")||proName==null){
				request.setAttribute("name", "null");
			}else{
				request.setAttribute("name", "notnull");
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
