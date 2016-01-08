/* ��    ����et.bo.stat.action
 * �� �� ����CorpInfoByServiceTypeAction.java
 * ע��ʱ�䣺2008-8-28 11:03:05
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

import et.bo.stat.service.CorpInfoByServiceTypeService;
import et.bo.sys.common.SysStaticParameter;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class CorpInfoByServiceTypeAction.
 * ��ҵ��Ϣ��֮��Ϣ������ͳ��
 * @author Wang Lichun
 */
public class CorpInfoByServiceTypeAction extends BaseAction {
	private CorpInfoByServiceTypeService service;

	/**
	 * Gets the service.
	 * The service type is CorpInfoByServiceTypeService.
	 * @return the service
	 */
	public CorpInfoByServiceTypeService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is CorpInfoByServiceTypeService.
	 * @param service the new service
	 */
	public void setService(CorpInfoByServiceTypeService service) {
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
	 * ִ�и÷�������ʾ��ҵ��Ϣ��֮��Ϣ������ͳ�Ƶ������������.
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
		if (chartType.equals("")) {		
			String agentId=dto.getString("agentId").toString();
			if(agentId.equals("")||agentId==null){
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
