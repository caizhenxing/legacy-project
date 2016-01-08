/* ��    ����et.bo.stat.action
 * �� �� ����PriceInfoAction.java
 * ע��ʱ�䣺2008-8-28 11:27:26
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

import et.bo.stat.service.PriceInfoService;
import et.bo.sys.common.SysStaticParameter;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class PriceInfoAction.
 * ũ��Ʒ�۸�֮��ϯ������ͳ�ơ�
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
		List user=service.userQuery(SysStaticParameter.QUERY_USER_SQL);
		request.setAttribute("user", user);
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
