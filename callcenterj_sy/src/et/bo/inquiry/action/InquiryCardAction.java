/**
 * 	InquiryCardAction.java   2008-4-2 下午02:51:45
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
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
 * 处理调查主题相关的调查问题的请求
 * 
 * @author 梁云锋
 * 
 */
public class InquiryCardAction extends BaseAction {
	// 为Action注入的service属性 对应et.bo.inquiry.service.impl.InquiryCardServiceImpl
	private InquiryCardService inquiryCardService;

	// 为Action注入的classTree属性 用于获取系统配置参数
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
	 * 处理某条问题的加载请求 比如查看、修改、删除某条问题信息
	 * 跳转到/inquiry/inquiryCardLoad.jsp
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
	 * 执行修改、删除操作的保存请求
	 * 成功后给出相应的提示信息并关闭修改窗口
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
