/**
 * 	@(#) FocusCaseInfoStatAction.java 2008-7-11 ����02:09:12
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

import et.bo.stat.service.CorpInfoBySeatService;
import et.bo.sys.common.SysStaticParameter;
import excellence.common.classtree.ClassTreeService;
import excellence.common.classtree.impl.ClassTreeServiceImpl;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class CorpInfoBySeatAction.
 * 
 * ��ҵ��Ϣ����ϯ������ͳ��
 * 
 * @author Wang Lichun
 */
public class CorpInfoBySeatAction extends BaseAction {
	private CorpInfoBySeatService service;
	private ClassTreeService cts = new ClassTreeServiceImpl();

	/**
	 * Gets the service.
	 * The service type is CorpInfoBySeatService.
	 * @return the service
	 */
	public CorpInfoBySeatService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The service type is CorpInfoBySeatService.
	 * @param service the new service
	 */
	public void setService(CorpInfoBySeatService service) {
		this.service = service;
	}

	/**
	 * ��ʾ��ҵ��Ϣ����ϯ������ͳ��MAIN ҳ��.
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
	 * ִ�и÷�������ʾ��ҵ��Ϣ����ϯ������ͳ�Ƶ������������.
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
		
//		List evaluatingList = cts.getLabelVaList("evaluating");   
//        request.setAttribute("evaluatingList", evaluatingList);
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
			List<DynaBeanDTO> list=service.query(dto);
			
			if(!"".equals(dto.get("name").toString()))
				request.setAttribute("date", "notnull");
			else
				request.setAttribute("date", "null");
			//��request �м���list
			request.setAttribute("list", list);
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
}
