/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.custinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.custinfo.service.CustinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>
 * �ͻ�����action
 * </p>
 * 
 * @version 2008-03-28
 * @author nie
 */

public class CustinfoAction extends BaseAction {

	static Logger log = Logger.getLogger(CustinfoAction.class.getName());

	private CustinfoService custinfoService = null;

	private ClassTreeService cts = null;

	// ע��service
	public CustinfoService getCustinfoService() {
		return custinfoService;
	}

	public void setCustinfoService(CustinfoService custinfoService) {
		this.custinfoService = custinfoService;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	/**
	 * ����URL����ִ�� toCustinfoMain ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toCustinfoMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String tel = request.getParameter("tel");
		if(tel != null)
			request.getSession().setAttribute("telnum", tel);
		return map.findForward("main");

	}

	/**
	 * ����URL����ִ�� toCustinfoQuery ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toCustinfoQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
		List user=custinfoService.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
		request.setAttribute("user", user);
		return map.findForward("query");

	}

	/**
	 * ����URL����ִ�� toCustinfoList ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCustinfoList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Object o = request.getSession().getAttribute("telnum");
		
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		
		String tel = "";
		if(o != null) {
			tel = o.toString();
			dto.set("cust_tel_home", tel);
			request.getSession().removeAttribute("telnum");
		}
		
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("userpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(14);
		// ȡ��list������
		List list = custinfoService.custinfoQuery(dto, pageInfo);
		int size = custinfoService.getCustinfoSize();

		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", map, request);
		request.getSession().setAttribute("userpageTurning", pt);

		return map.findForward("list");
	}

	/**
	 * ����URL����ִ�� toAllList ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toAllList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List list = custinfoService.custinfoAllQuery();
		request.setAttribute("list", list);

		return map.findForward("alllist");
	}

	/**
	 * ����URL����ִ�� toAllList ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toAllExpertList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List list = custinfoService.custinfoExpertAllQuery();
		request.setAttribute("list", list);

		return map.findForward("allExpertList");
	}

	/**
	 * ����URL����ִ�� toCustinfoLoad ����������Ҫforwardҳ�档 ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCustinfoLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		// ����type�����ж�ִ������Ҫ�Ĳ���
		if (type.equals("insert")) {

			// request.setAttribute(map.getName(),dto);
			return map.findForward("load");
		}

		if (type.equals("detail")) {

			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		if (type.equals("update")) {

			request.setAttribute("opertype", "update");
			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		if (type.equals("delete")) {

			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		return map.findForward("load");
	}

	/**
	 * ����URL����ִ�� toCustinfoOper ����������Ҫforwardҳ�档 ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCustinfoOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		UserBean ub = (UserBean) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		String userid = ub.getUserId();

		dto.set("cust_rid", userid);

		if (type.equals("insert")) {
			try {

				custinfoService.addCustinfo(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");

			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}

		if (type.equals("update")) {
			try {

				boolean b = custinfoService.updateCustinfo(dto);
				if (b == true) {
					request.setAttribute("operSign", "�޸ĳɹ�");
					return map.findForward("load");
				} else {
					request.setAttribute("operSign", "�޸�ʧ��");
					return map.findForward("load");
				}
			} catch (RuntimeException e) {
				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}

		if (type.equals("delete")) {
			try {
				// custinfoService.delCustinfo((String)dto.get("cust_id"));//���������ɾ���˰���
				// ��������Ǳ��ɾ��
				// boolean b = custinfoService.isDelete((String)
				// dto.get("cust_id"));
				custinfoService.delCustinfo((String) dto.get("cust_id"));
				// if (b == true)
				// {
				request.setAttribute("operSign", "ɾ���ɹ�");
				return map.findForward("load");
				// }
				// else
				// {
				// request.setAttribute("operSign", "ɾ��ʧ��");
				// return map.findForward("load");
				// }
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}
		return map.findForward("load");
	}

	/**
	 * ����URL����ִ�� toCustinfoMain ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toPhoneMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return map.findForward("phonemain");
	}

	/**
	 * ����URL����ִ�� toCustinfoQuery ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toPhoneQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List typeList = cts.getLabelVaList("phonebook");
		request.setAttribute("typeList", typeList);
		return map.findForward("phonequery");
	}

	/**
	 * ����URL����ִ�� toCustinfoList ����������Ҫforwardҳ�档
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toPhoneList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		//System.out.println("pageState=" + pageState);
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("userpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(20);
		// ȡ��list������
		List list = custinfoService.phoneQuery(dto, pageInfo);
		int size = custinfoService.getPhoneSize();
		System.out.println("size=" + size);
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", map, request);
		request.getSession().setAttribute("userpageTurning", pt);

		return map.findForward("phonelist");
	}

	/**
	 * ����URL����ִ�� toCustinfoLoad ����������Ҫforwardҳ�档 ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toPhoneLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("phonebook");
		request.setAttribute("typeList", typeList);

		// ����type�����ж�ִ������Ҫ�Ĳ���
		if (type.equals("insert")) {
			return map.findForward("phoneload");
		}

		if (type.equals("detail")) {
			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("phoneload");
		}

		if (type.equals("update")) {
			request.setAttribute("opertype", "update");
			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("phoneload");
		}
		if (type.equals("delete")) {
			String id = request.getParameter("id");
			IBaseDTO dto = custinfoService.getCustinfoInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("phoneload");
		}

		return map.findForward("phoneload");
	}

	/**
	 * ����URL����ִ�� toCustinfoOper ����������Ҫforwardҳ�档 ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toPhoneOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("phonebook");
		request.setAttribute("typeList", typeList);

		if (type.equals("insert")) {
			try {
				custinfoService.addCustinfo(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("phoneload");
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}

		if (type.equals("update")) {
			try {
				boolean b = custinfoService.updateCustinfo(dto);
				if (b == true) {
					request.setAttribute("operSign", "�޸ĳɹ�");
					return map.findForward("phoneload");
				} else {
					request.setAttribute("operSign", "�޸�ʧ��");
					return map.findForward("phoneload");
				}
			} catch (RuntimeException e) {
				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}

		if (type.equals("delete")) {
			try {
				// custinfoService.delCustinfo((String)dto.get("cust_id"));//���������ɾ���˰���

				// ��������Ǳ��ɾ��
				// boolean b = custinfoService.isDelete((String)
				// dto.get("cust_id"));
				custinfoService.delCustinfo((String) dto.get("cust_id"));

				request.setAttribute("operSign", "ɾ���ɹ�");
				return map.findForward("phoneload");

			} catch (RuntimeException e) {
				e.printStackTrace();
				return map.findForward("error");
			}
		}

		return map.findForward("phoneload");
	}
}
