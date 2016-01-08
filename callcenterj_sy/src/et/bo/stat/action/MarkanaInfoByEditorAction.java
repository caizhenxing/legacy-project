/* ��    ����et.bo.stat.action
 * �� �� ����MarkanaInfoByEditorAction.java
 * ע��ʱ�䣺2008-8-28 11:23:42
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

import et.bo.stat.service.MarkanaInfoByEditorService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * The Class MarkanaInfoByEditorAction.
 * �г�����ȫ�����α༭�͸��༭�Ĳ�Ʒ����ͳ�ơ�
 * @author Wang Lichun
 */
public class MarkanaInfoByEditorAction extends BaseAction {
	private MarkanaInfoByEditorService service;

	/**
	 * Gets the service.
	 * The service type is MarkanaInfoByEditorService.
	 * @return the service
	 */
	public MarkanaInfoByEditorService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is MarkanaInfoByEditorService.
	 * @param service the new service
	 */
	public void setService(MarkanaInfoByEditorService service) {
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
	 * To unite main.
	 * 
	 * @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * 
	 * @return the action forward
	 */
	public ActionForward toUniteMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("uniteMain");
	}
	
	/**
	 * ִ�и÷�������ʾ��ϯ�绰��ͳ�Ƶ������������.
	 * 
	 * @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * 
	 * @return the action forward
	 */
	public ActionForward toUniteQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return new ActionForward("/stat/markanaInfoByEditorUniteQuery.jsp");
	}
	
	/**
	 * ִ�и÷�������ʾȫ�����α༭�͸��༭�Ĳ�Ʒ����ͳ�Ƶ������������.
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
			List<DynaBeanDTO> list=service.query(dto);
			
			if(!"".equals(dto.get("name").toString()))
				request.setAttribute("date", "notnull");
			else
				request.setAttribute("date", "null");
			
			request.setAttribute("list", list);
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
	
	/**
	 * To unite display.
	 * 
	 * @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * 
	 * @return the action forward
	 */
	public ActionForward toUniteDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = "";
		List<DynaBeanDTO> list=service.queryUnite(dto);
		String name=dto.getString("name");
		if(name.equals("")||name==null){
			request.setAttribute("name", "null");
		}else {
			request.setAttribute("name","notnull");
		}
		request.setAttribute("list", list);
		return new ActionForward("/stat/markanaInfoByEditorUniteReport.jsp");
	}
}
