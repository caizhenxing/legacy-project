/**
 * ����׿Խ�Ƽ����޹�˾
 * 2008-5-5
 */
package et.bo.callcenter.pbx2ivr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.pbx2ivr.service.Pbx2IvrService;
import et.bo.common.ConstantsCommonI;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * �������˿ں���IVR�˿ڵĶ�Ӧ��Actionʵ���� ���ܽ�������IVR�˿ڶ�Ӧ�Ĳ�ѯ����ʾ�����ص�ҳ������
 * 
 * @author ���Ʒ�
 * 
 */
public class Pbx2IvrAction extends BaseAction {
	private Pbx2IvrService service;

	public Pbx2IvrService getService() {
		return service;
	}

	public void setService(Pbx2IvrService service) {
		this.service = service;
	}

	/**
	 * ��ת����������IVR�˿�ӳ��ģ�������ҳ��/callcenter/pbx2ivr/pbx2ivrMain.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * ��ת����������IVR�˿�ӳ��ģ��Ĳ�ѯҳ��/callcenter/pbx2ivr/pbx2ivrQuery.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("query");
	}

	/**
	 * ��ѯ�����Ѿ����ڵĽ�������IVR�Ķ˿�ӳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<DynaBeanDTO> list = service.query();
		request.setAttribute("list", list);
		return mapping.findForward("list");
	}

	/**
	 * ����ָ���˿�ӳ����Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String operation = request.getParameter("type");
		request.setAttribute("opertype", operation);
		// �������������������ѡ��ļ�¼��Ϣ
		if (!ConstantsCommonI.INSERT_OPER.equals(operation)) {
			String id = request.getParameter("id");
			IBaseDTO dto = service.getPortMapInfo(id);
			request.setAttribute(mapping.getName(), dto);
		}
		return mapping.findForward("load");
	}

	/**
	 * ������µĶ˿�ӳ����Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String operation = request.getParameter("type");
		request.setAttribute("opertype", operation);
		// ��ӱ������ ִ��service��add����
		if (ConstantsCommonI.INSERT_OPER.equals(operation)) {
			service.add(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		// �޸ı������ ִ��service��update����
		else if (ConstantsCommonI.UPDATE_OPER.equals(operation)) {
			service.update(dto);
			request.setAttribute(mapping.getName(), service.getPortMapInfo(dto
					.get("id").toString()));
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		// ɾ��������� ִ��service��delete����
		else if (ConstantsCommonI.DELETE_OPER.equals(operation)) {
			service.delete(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		return mapping.findForward("load");
	}
}
