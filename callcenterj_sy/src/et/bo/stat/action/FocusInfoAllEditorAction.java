/* ��    ����et.bo.stat.action
 * �� �� ����FocusInfoAllEditorAction.java
 * ע��ʱ�䣺2008-8-28 11:15:11
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

import et.bo.stat.service.FocusInfoAllEditorService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class FocusInfoAllEditorAction.
 * ���㰸����֮ȫ�����α༭�͸��༭���쵼��ʾ���Ĳ�Ʒ������
 * @author Wang Lichun
 */
public class FocusInfoAllEditorAction extends BaseAction {
	private FocusInfoAllEditorService service;

	/**
	 * Gets the service.
	 * The service type is FocusInfoAllEditorService.
	 * @return the service
	 */
	public FocusInfoAllEditorService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is FocusInfoAllEditorService.
	 * @param service the new service
	 */
	public void setService(FocusInfoAllEditorService service) {
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
	 * ִ�и÷�������ʾ���㰸����֮ȫ�����α༭�͸��༭���쵼��ʾ���Ĳ�Ʒ������ͳ�Ƶ������������.
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
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			String name=dto.getString("name").toString();
			if(name.equals("")||name==null){
				request.setAttribute("name", "null");
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
