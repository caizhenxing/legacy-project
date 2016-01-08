/**
 * 	@(#) FocusCaseInfoStatAction.java 2008-4-11 ����01:09:12
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

import et.bo.stat.service.FocusCaseInfoStatisticsByStateService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>���㰸������ȫ����ϯԱ��ÿһ��ϯԱ����ĸ����״̬�µİ�������action</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public class FocusCaseInfoStatisticsByStateAction extends BaseAction {
	private FocusCaseInfoStatisticsByStateService service;

	public FocusCaseInfoStatisticsByStateService getService() {
		return service;
	}

	public void setService(FocusCaseInfoStatisticsByStateService service) {
		this.service = service;
	}

	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * ִ�и÷�������ʾȫ����ϯԱ��ÿһ��ϯԱ����ĸ����״̬�µİ���������ѯҳ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List user=service.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001'");
		request.setAttribute("user", user);
		return mapping.findForward("query");
	}

	/**
	 * ����ͳ��������ִ����Ӧ�ĵ�ͳ�Ʒ�������ͳ�ƽ�����Ե�displayҳ��
	 * ��ʾȫ����ϯԱ��ÿһ��ϯԱ����ĸ����״̬�µİ������� ��ʾ ������ ���ʱ��
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
			if(dto.get("agentNum")!=null&&!"".equals(dto.get("agentNum")))
			{
				request.setAttribute("cTitle", "������");
			}
			else
			{
				request.setAttribute("cTitle", "���ʱ��");
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
