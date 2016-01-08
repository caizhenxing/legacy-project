/**
 * 
 */
package et.bo.oa.communicate.email.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import et.bo.oa.communicate.email.service.EmailService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class EmailInAction extends BaseAction {

	private Log log = LogFactory.getLog(EmailInAction.class);

	private EmailService emailService = null;

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
	 * @describe ��ת��mainҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toEmailMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// log.debug("����main");
		return map.findForward("main");
	}

	/**
	 * @describe ��ת��queryҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toEmailQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// log.debug("���벢��ת��queryҳ");
		return map.findForward("query");
	}

	/**
	 * @describe �ڲ��ʼ��ϴ�
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward upload(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// log.debug("���벢��ת��queryҳ");
		return map.findForward("upload");
	}

	/**
	 * @describe �����ʼ���Ϣ
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward operEmail(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");
		List l = new ArrayList();
		request.setAttribute("opertype", type);
		// ���浽�ݸ���
		if (type.equals("savedraft")) {
			emailService.saveEmailToDraft(formdto, l);
			request.getSession().removeAttribute("uploadList");
			request.setAttribute("idus_state", "oa.communicate.email.inaction.savedraft");
			return map.findForward("load");
		}
		// �����ʼ�
		if (type.equals("send")) {
			if (request.getSession().getAttribute("inUploadList") == null) {
				l = null;
			} else {
				l = (List) request.getSession().getAttribute("inUploadList");
			}
			emailService.saveEmailToAddresser(formdto, l, "send");
			request.setAttribute("idus_state", "oa.communicate.email.inaction.sendsuccess");
			return map.findForward("load");
		}
		if (type.equals("delete")) {
			String[] str = formdto.getStrings("cchk");
			ActionMessages errors = new ActionMessages();
			if (str.length == 0) {
				errors.add("nullselect", new ActionMessage(
						"agrofront.email.emailLoad.nullselect"));
				saveErrors(request, errors);
				return map.findForward("error");
			}
			emailService.delEmailToDustbin(str);
			request.setAttribute("idus_state", "oa.communicate.email.inaction.putdel");
			ActionForward actionforward = new ActionForward(
					"/oa/communicate/email.do?method=toEmailList&type="
							+ request.getSession().getAttribute("inemailtype")
							+ "&pagestop=pagestop");
			return actionforward;
		}
		if (type.equals("deleteForever")) {
			String[] str = formdto.getStrings("cchk");
			ActionMessages errors = new ActionMessages();
			if (str.length == 0) {
				errors.add("nullselect", new ActionMessage(
						"agrofront.email.emailLoad.nullselect"));
				saveErrors(request, errors);
				return map.findForward("error");
			}
			emailService.delEmailForever(str);
			request.setAttribute("idus_state", "oa.communicate.email.inaction.delsuccess");
			ActionForward actionforward = new ActionForward(
					"/oa/communicate/email.do?method=toEmailList&type="
							+ request.getSession().getAttribute("inemailtype")
							+ "&pagestop=pagestop");
			return actionforward;
		}
		return map.findForward("load");
	}

	/**
	 * @describe ��ת��loadҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toEmailLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);
		if (type.equals("insert")) {
			formdto.set("sendUser", ui.getUserName());
			return map.findForward("load");
		}
		if (type.equals("see")) {
			String id = request.getParameter("id");
			IBaseDTO dto = emailService.getInEmailInfo(id);

			request.setAttribute("adjunctList", (List) dto.get("adjunctInfo"));
			request.setAttribute(map.getName(), dto);
			return map.findForward("see");
		}
		if (type.equals("answer")) {
			String id = request.getParameter("id");
			IBaseDTO dto = emailService.getInEmailInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("answer");
		}
		return map.findForward("load");
	}

	/**
	 * @describe ��ת��listҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toEmailList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		formdto.set("sendUser", ui.getUserName());
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("emailpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			formdto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(14);
		String type = request.getParameter("type").toString();
		if (type == null) {
			if (request.getSession().getAttribute("inemailtype") == null) {

			} else {
				type = request.getSession().getAttribute("inemailtype")
						.toString();
			}
		} else {
			// type =
			// request.getSession().getAttribute("inemailtype").toString();
		}
		// ��סtake����
		request.getSession().setAttribute("inemailtype", type);
		List l = null;
		l = emailService.emailListIndex(formdto, pageInfo, type);
		int size = emailService.getEmailIndexSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formdto);
		request.setAttribute("list", l);
		PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
		request.getSession().setAttribute("emailpageTurning", pt);
		// request.setAttribute("list",agroMachineService.findArgoMachineInfo(argoSearch.searchHirerOperInfo(qldto)));

		// ��session�����ַ��list
		request.getSession().removeAttribute("inUploadList");
		return map.findForward("list");
	}

	/**
	 * @describe ѡ���û��б����
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toEmaiSelectUser(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List ul = emailService.userList();
		request.setAttribute("userList", ul);
		return map.findForward("selectUserOne");
	}

	/**
	 * @describe ѡ���û��б�(���ͣ�����)
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toEmaiSelectList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List ul = emailService.userList();
		request.setAttribute("userList", ul);
		return map.findForward("selectUserTwo");
	}
}
