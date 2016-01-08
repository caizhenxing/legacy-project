/*
 * @(#)FixedContactAction.java	 2008-06-06
 *
 * 版权所有 沈阳市卓越科技有限公司。
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
 * @author 王默
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
   * 根据URL参数执行 toFixedContactMain
   * 方法，返回要forward页面。.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward 返回main框架页面
   */
	public ActionForward toFixedContactMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return map.findForward("main");
	}

	/**
   * 根据URL参数执行 toFixedContactQuery
   * 方法，返回要forward页面。.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward 返回查询页面
   */
	public ActionForward toFixedContactQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
		return map.findForward("query");
	}

	/**
   * 根据URL参数执行 toFixedContactList
   * 方法，返回要forward页面。.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward 返回列表页面
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
		// 取得list及条数
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
   * 根据URL参数执行 toAllList 方法，返回要forward页面。.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward 返回列表页面
   */
	public ActionForward toAllList(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		List list = fcs.fixedContactAllQuery();
		request.setAttribute("list", list);
		return map.findForward("alllist");
	}

	/**
   * 根据URL参数执行 toFixedContactLoad 方法，返回要forward页面。
   * 并且根据URL参数中type的值来判断所执行的操作:CRUD.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward 返回列表页面
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
		// 根本type参数判断执行所需要的操作
		if (type.equals("insert"))
		{
			String userId = null;
			if (session.getAttribute(et.bo.sys.common.SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION) != null)
			{
				// 从系统中获取登录用户的SessionID
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

			// 处理前台显示图片的标志
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

			// 处理前台显示图片的标志
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

			// 处理前台显示图片的标志
			if (dto.get("cust_pic_name") != null && !"".equals(dto.get("cust_pic_name"))) request
					.setAttribute("viewPic", "yes");
			else request.setAttribute("viewPic", "no");

			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		return map.findForward("load");
	}

	/**
   * 根据URL参数执行 toFixedContactOper 方法，返回要forward页面。
   * 并且根据URL参数中type的值来判断所执行的操作:CRUD.
   * @param map
   * the map
   * @param form
   * the form
   * @param request
   * the request
   * @param response
   * the response
   * @return ActionForward 返回列表页面
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
					// 关闭session
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
				// 如果用s户强烈要上传图片则把以下else语句块恢复
				else
				{
					log.error("FixedContactAction->toFixedContactOper: insert ERROR");
					request.setAttribute("type", "请您选择上传的图片");
					return map.findForward("error");
				}
			}
			catch(RuntimeException e)
			{
				log.error("FixedContactAction->toFixedContactOper : insert ERROR");
				e.printStackTrace();
				request.setAttribute("type", "插入上传的图片时出现了异常！");
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
					//System.out.println("更新后的人名是：" + dto.getString("cust_name"));
					if (fcs.updateFixedContact(dto))
					{
						request.setAttribute("operSign", "修改成功");

						// 更新成功后要删除session
						if (session.getAttribute("picPath") != null) session.removeAttribute("picPath");
						if (session.getAttribute("picName") != null) session.removeAttribute("picName");

						// 把原来从数据库中取到的更新前的文件名(在显示更新时已放在了session里，将其从session中清除掉)
						if (session.getAttribute("old_pic_name") != null) session
								.removeAttribute("old_pic_name");
						return map.findForward("load");
					}
					else
					{
						request.setAttribute("type", "更新固定联络员时出现插入空值问题");
						return map.findForward("error");
					}
				}
				else
				{
					//System.out.println("更新前的发展时间是：" + dto.get("cust_develop_time"));
					if (fcs.updateFixedContact(dto))
					{
						request.setAttribute("operSign", "修改成功");
						return map.findForward("load");
					}
					else
					{
						request.setAttribute("type", "更新固定联络员时出现插入空值问题");
						return map.findForward("error");
					}
				}
			}
			catch(RuntimeException e)
			{
				log.error("FixedContactAction->toFixedContactOper : update ERROR");
				e.printStackTrace();
				// 关闭session
				if (session.getAttribute("picPath") != null) session.removeAttribute("picPath");
				if (session.getAttribute("picName") != null) session.removeAttribute("picName");
				if (session.getAttribute("old_pic_name") != null) session.removeAttribute("old_pic_name");
				request.setAttribute("type", "更新固定联络员失败");
				return map.findForward("error");
			}
		}

		if (type.equals("delete"))
		{
			//System.out.println("FixedContactAction------->toFixedContactOper-->delete");
			try
			{
				// FixedContactService.delFixedContact((String)dto.get("cust_id"));//这句可是真的删除了啊！
				// 下面这块是标记删除
				boolean b = fcs.isDelete((String) dto.get("cust_id"));
				if (b == true)
				{
					request.setAttribute("operSign", "删除成功");
					return map.findForward("load");
				}
				else
				{
					request.setAttribute("operSign", "删除失败");
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
