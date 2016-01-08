/* ��    ����et.bo.stat.action
 * �� �� ����HZCaseInfoForExportAction.java
 * ע��ʱ�䣺2008-8-28 11:20:32
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
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class HZCaseInfoForExportAction.
 * ���ﰸ����ר��������ͳ��Action.
 * @author Wang Lichun
 */
public class HZCaseInfoForExportAction extends BaseAction {
	private HZCaseInfoFroExportService service;
	
	private ClassTreeService cts;

	/**
	 * Gets the cts.
	 * The cts type is ClassTreeService.
	 * @return the cts
	 */
	public ClassTreeService getCts() {
		return cts;
	}

	/**
	 * Sets the cts.
	 * The cts type is ClassTreeService.
	 * @param cts the new cts
	 */
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	
	/**
	 * Gets the service.
	 * The service type is HZCaseInfoFroExportService.
	 * @return the service
	 */
	public HZCaseInfoFroExportService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is HZCaseInfoFroExportService.
	 * @param service the new service
	 */
	public void setService(HZCaseInfoFroExportService service) {
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
	 * ִ�и÷�������ʾ���ﰸ����ר��������ͳ�Ƶ������������.
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
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		return mapping.findForward("query");
	}
	

	/**
	 * ����ͳ��������ִ����Ӧ��ͳ�Ʒ�������ͳ�ƽ�����Ե�displayҳ��.
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
			if(dto.get("caseExpert")!=null)
			{
				request.setAttribute("cTitle", "����ʱ��");
			}
			else
			{
				request.setAttribute("cTitle", "����ר��");
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
