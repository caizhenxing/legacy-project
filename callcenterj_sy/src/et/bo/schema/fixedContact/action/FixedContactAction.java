/*
 * @(#)FixedContactAction.java	 2008-06-06
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.schema.fixedContact.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import et.bo.schema.fixedContact.service.FixedContactService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.Constants;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * The Class FixedContactAction.
 * @author ��Ĭ
 */
public class FixedContactAction extends BaseAction
{
	/** The log. */
	static Logger							 log = Logger.getLogger(FixedContactAction.class.getName());

	/** The FixedContact service. */
	private FixedContactService fcs;

	/** The cts. */
	private ClassTreeService		cts;

	/**
   * ����URL����ִ�� toFixedContactMain
   * ����������Ҫforwardҳ�档.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward ����main���ҳ��
   */
	public ActionForward toFixedContactMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return map.findForward("main");
	}

	/**
   * ����URL����ִ�� toFixedContactQuery
   * ����������Ҫforwardҳ�档.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward ���ز�ѯҳ��
   */
	public ActionForward toFixedContactQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
		return map.findForward("query");
	}

	/**
   * ����URL����ִ�� toFixedContactList
   * ����������Ҫforwardҳ�档.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward �����б�ҳ��
   */
	public ActionForward toFixedContactList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null)
		{
			pageInfo = new PageInfo();
		}
		else
		{
			PageTurning pageTurning = (PageTurning) request.getSession().getAttribute("userpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(10);
		// ȡ��list������
		List list = fcs.fixedContactQuery(dto, pageInfo);
		int size = fcs.getFixedContactSize();

		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", map, request);
		request.getSession().setAttribute("userpageTurning", pt);

		return map.findForward("list");
	}

	/**
   * ����URL����ִ�� toAllList ����������Ҫforwardҳ�档.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward �����б�ҳ��
   */
	public ActionForward toAllList(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		List list = fcs.fixedContactAllQuery();
		request.setAttribute("list", list);
		return map.findForward("alllist");
	}

	/**
   * ����URL����ִ�� toFixedContactLoad ����������Ҫforwardҳ�档
   * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward �����б�ҳ��
   */
	public ActionForward toFixedContactLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		HttpSession session = request.getSession();
		// ����type�����ж�ִ������Ҫ�Ĳ���
		if (type.equals("insert"))
		{
			String userId = null;
			if (session.getAttribute(et.bo.sys.common.SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION) != null)
			{
				// ��ϵͳ�л�ȡ��¼�û���SessionID
				java.util.Map infoMap = (java.util.Map) session
						.getAttribute(et.bo.sys.common.SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				userId = (String) infoMap.get("userId");
			}
			IBaseDTO dto = (IBaseDTO) form;
			dto.set("cust_rid", userId);

			request.setAttribute("opertype", "insert");
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		if (type.equals("detail"))
		{
			request.setAttribute("opertype", "detail");
			String id = request.getParameter("id");
			IBaseDTO dto = fcs.getFixedContactInfo(id);

			// ����ǰ̨��ʾͼƬ�ı�־
			if (dto.get("cust_pic_name") != null && !"".equals(dto.get("cust_pic_name"))) request
					.setAttribute("viewPic", "yes");
			else request.setAttribute("viewPic", "no");
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}

		if (type.equals("update"))
		{
			request.setAttribute("opertype", "update");
			String id = request.getParameter("id");
			IBaseDTO dto = fcs.getFixedContactInfo(id);

			// ����ǰ̨��ʾͼƬ�ı�־
			if (dto.get("cust_pic_name") != null && !"".equals(dto.get("cust_pic_name"))) session
					.setAttribute("old_pic_name", dto.get("cust_pic_name"));
			else session.setAttribute("old_pic_name", null);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		if (type.equals("delete"))
		{
			request.setAttribute("opertype", "delete");
			String id = request.getParameter("id");
			IBaseDTO dto = fcs.getFixedContactInfo(id);

			// ����ǰ̨��ʾͼƬ�ı�־
			if (dto.get("cust_pic_name") != null && !"".equals(dto.get("cust_pic_name"))) request
					.setAttribute("viewPic", "yes");
			else request.setAttribute("viewPic", "no");

			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		return map.findForward("load");
	}

	/**
   * ����URL����ִ�� toFixedContactOper ����������Ҫforwardҳ�档
   * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward �����б�ҳ��
   */
	public ActionForward toFixedContactOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;

		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		if (type.equals("insert"))
		{
			HttpSession session = request.getSession();
			//System.out.println("FixedContactAction------->toFixedContactOper-->insert");
			try
			{
				String path = Constants.getProperty("fixedContact_pic_realpath");
				if (session.getAttribute("picName") != null)
				{
					String name = (String) session.getAttribute("picName");
					dto.set("cust_pic_path", path);
					dto.set("cust_pic_name", name);
					// System.out.println("----------------dto.getString(cust_pic_name)="
					// + dto.getString("cust_pic_name"));
					fcs.addFixedContact(dto);
					// �ر�session
					if (session.getAttribute("picPath") != null) session.removeAttribute("picPath");
					if (session.getAttribute("picName") != null) session.removeAttribute("picName");
				}
				// else
				// {
				// fcs.addFixedContact(dto);
				// request.setAttribute("operSign",
        // "sys.common.operSuccess");
				// return map.findForward("load");
				// }
				// �����s��ǿ��Ҫ�ϴ�ͼƬ�������else����ָ�
				else
				{
					log.error("FixedContactAction->toFixedContactOper: insert ERROR");
					request.setAttribute("type", "����ѡ���ϴ���ͼƬ");
					return map.findForward("error");
				}
			}
			catch(RuntimeException e)
			{
				log.error("FixedContactAction->toFixedContactOper : insert ERROR");
				e.printStackTrace();
				request.setAttribute("type", "�����ϴ���ͼƬʱ�������쳣��");
				return map.findForward("error");
			}
		}

		if (type.equals("update"))
		{
			HttpSession session = request.getSession();
			// System.out.println("FixedContactAction------->toFixedContactOper-->update");
			try
			{
				String path = Constants.getProperty("fixedContact_pic_realpath");
				if (session.getAttribute("picName") != null)
				{
					String name = (String) session.getAttribute("picName");
					dto.set("cust_pic_path", path);
					dto.set("cust_pic_name", name);
					//System.out.println("���º�������ǣ�" + dto.getString("cust_name"));
					if (fcs.updateFixedContact(dto))
					{
						request.setAttribute("operSign", "�޸ĳɹ�");

						// ���³ɹ���Ҫɾ��session
						if (session.getAttribute("picPath") != null) session.removeAttribute("picPath");
						if (session.getAttribute("picName") != null) session.removeAttribute("picName");

						// ��ԭ�������ݿ���ȡ���ĸ���ǰ���ļ���(����ʾ����ʱ�ѷ�����session������session�������)
						if (session.getAttribute("old_pic_name") != null) session
								.removeAttribute("old_pic_name");
						return map.findForward("load");
					}
					else
					{
						request.setAttribute("type", "���¹̶�����Աʱ���ֲ����ֵ����");
						return map.findForward("error");
					}
				}
				else
				{
					//System.out.println("����ǰ�ķ�չʱ���ǣ�" + dto.get("cust_develop_time"));
					if (fcs.updateFixedContact(dto))
					{
						request.setAttribute("operSign", "�޸ĳɹ�");
						return map.findForward("load");
					}
					else
					{
						request.setAttribute("type", "���¹̶�����Աʱ���ֲ����ֵ����");
						return map.findForward("error");
					}
				}
			}
			catch(RuntimeException e)
			{
				log.error("FixedContactAction->toFixedContactOper : update ERROR");
				e.printStackTrace();
				// �ر�session
				if (session.getAttribute("picPath") != null) session.removeAttribute("picPath");
				if (session.getAttribute("picName") != null) session.removeAttribute("picName");
				if (session.getAttribute("old_pic_name") != null) session.removeAttribute("old_pic_name");
				request.setAttribute("type", "���¹̶�����Աʧ��");
				return map.findForward("error");
			}
		}

		if (type.equals("delete"))
		{
			//System.out.println("FixedContactAction------->toFixedContactOper-->delete");
			try
			{
				// FixedContactService.delFixedContact((String)dto.get("cust_id"));//���������ɾ���˰���
				// ��������Ǳ��ɾ��
				boolean b = fcs.isDelete((String) dto.get("cust_id"));
				if (b == true)
				{
					request.setAttribute("operSign", "ɾ���ɹ�");
					return map.findForward("load");
				}
				else
				{
					request.setAttribute("operSign", "ɾ��ʧ��");
					return map.findForward("load");
				}
			}
			catch(RuntimeException e)
			{
				log.error("FixedContactAction->toFixedContactOper : delete ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}
		return map.findForward("load");
	}

	/**
   * Gets the cts.
   * @return the cts
   */
	public ClassTreeService getCts()
	{
		return cts;
	}

	/**
   * Sets the cts.
   * @param cts
   * the new cts
   */
	public void setCts(ClassTreeService cts)
	{
		this.cts = cts;
	}

	public FixedContactService getFcs()
	{
		return fcs;
	}

	public void setFcs(FixedContactService fcs)
	{
		this.fcs = fcs;
	}
}
