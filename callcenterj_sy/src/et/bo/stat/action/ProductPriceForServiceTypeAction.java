/* ��    ����et.bo.stat.action
 * �� �� ����ProductPriceForServiceTypeAction.java
 * ע��ʱ�䣺2008-8-28 11:27:59
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

import et.bo.stat.service.ProductPriceForTypeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class ProductPriceForServiceTypeAction.
 * ũ��Ʒ�۸�֮������Ա�ı��۴����ͱ���������
 * @author Wang Lichun
 */
public class ProductPriceForServiceTypeAction extends BaseAction {
	private ProductPriceForTypeService service;

	/**
	 * Gets the service.
	 * The service type is ProductPriceForTypeService.
	 * @return the service
	 */
	public ProductPriceForTypeService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is ProductPriceForTypeService.
	 * @param service the new service
	 */
	public void setService(ProductPriceForTypeService service) {
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
	 * To main1.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toMain1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main1");
	}

	/**
	 * ִ�и÷�������ʾ����Ա���۴���ͳ�Ƶ������������.
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
