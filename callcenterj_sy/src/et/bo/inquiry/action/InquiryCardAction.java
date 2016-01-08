/**
 * 	InquiryCardAction.java   2008-4-2 ����02:51:45
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.common.ConstantsCommonI;
import et.bo.inquiry.service.InquiryCardService;
import et.bo.inquiry.service.impl.InquiryHelp;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * �������������صĵ������������
 * 
 * @author ���Ʒ�
 * 
 */
public class InquiryCardAction extends BaseAction {
	// ΪActionע���service���� ��Ӧet.bo.inquiry.service.impl.InquiryCardServiceImpl
	private InquiryCardService inquiryCardService;

	// ΪActionע���classTree���� ���ڻ�ȡϵͳ���ò���
	private ClassTreeService classTree;

	public ClassTreeService getClassTree() {
		return classTree;
	}

	public void setClassTree(ClassTreeService classTree) {
		this.classTree = classTree;
	}

	public InquiryCardService getInquiryCardService() {
		return inquiryCardService;
	}

	public void setInquiryCardService(InquiryCardService inquiryCardService) {
		this.inquiryCardService = inquiryCardService;
	}

	/**
	 * ����ĳ������ļ������� ����鿴���޸ġ�ɾ��ĳ��������Ϣ
	 * ��ת��/inquiry/inquiryCardLoad.jsp
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
		String id = request.getParameter("id");
		IBaseDTO dto = inquiryCardService.getInquiryCardInfo(id);
		request.setAttribute(mapping.getName(), dto);
		request.setAttribute("questionTypes", InquiryHelp.getQTList());
		return mapping.findForward("load");
	}

	/**
	 * ִ���޸ġ�ɾ�������ı�������
	 * �ɹ��������Ӧ����ʾ��Ϣ���ر��޸Ĵ���
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
		if (ConstantsCommonI.UPDATE_OPER.equals(operation)) {
			inquiryCardService.update(dto);
			request.setAttribute(mapping.getName(), inquiryCardService
					.getInquiryCardInfo(dto.get("id").toString()));
			request.setAttribute("operSign", "sys.common.operSuccess");
		} else if (ConstantsCommonI.DELETE_OPER.equals(operation)) {
			inquiryCardService.delete(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		request.setAttribute("questionTypes", InquiryHelp.getQTList());
		return mapping.findForward("load");
	}

}
