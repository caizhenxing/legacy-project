/**
 * 	@(#) TelAction.java 2008-4-11 ����01:09:12
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

import et.bo.stat.service.PriceAgentProCountService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author dengwei
 * 
 */
public class PriceAgentProCountAction extends BaseAction {
	private PriceAgentProCountService service;
	
	public PriceAgentProCountService getService() {
		return service;
	}

	public void setService(PriceAgentProCountService service) {
		this.service = service;
	}

	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * ִ�и÷�������ʾ��Ʒ�����ÿһ��ϯԱ����Ĳ�Ʒ��������ͳ�Ƶ������������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("uList", service.getUserList());
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
	public ActionForward toDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			List<DynaBeanDTO> list=service.query(dto);
			request.setAttribute("list", list);
			String agentId = (String)dto.get("agentId");
			if(agentId.equals("")||agentId==null){
				request.setAttribute("agentId", "null");
			}else{
				request.setAttribute("agentId", "notnull");
			}
//			request.setAttribute("caseRid", dto.get("caseRid").toString());
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
}
