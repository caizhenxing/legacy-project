package et.bo.sad.action;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import et.bo.sad.service.SadService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.common.util.Constants;

public class SadAction extends BaseAction
{

	private ClassTreeService cts = null;

	private SadService			 ss	= null;
	
	/**
	 * 农产品大卖场中的数据信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toBigMarketLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
//		GeneralCaseinfoService gcs = (GeneralCaseinfoService)SpringRunningContainer.getInstance().getBean("GeneralCaseinfoService");
		JSONArray jsonArray = JSONArray.fromObject(ss.screenList());
		outJsonString(response,jsonArray.toString());
		return null;
	}

	/**
   * @describe 进入市场供求主页面
   */
	public ActionForward toSadMain(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		String str_state=request.getParameter("state");
		ss.clearMessage(ub.getUserId(), str_state);
		request.setAttribute("state", str_state);
		
		return map.findForward("toSadMain");
	}
	/**
	 * 进入每日供求页面
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toSadMain1(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		return map.findForward("main1");
	}
	/**
	 * 进入每日供求查询
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toSadQuery1(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		List sadTypeList = cts.getLabelVaList("sadType");
		request.setAttribute("userList", ss.userQuery());
		request.setAttribute("sadTypeList", sadTypeList);

		return map.findForward("query1");
	}
	/**
   * @describe 市场供求查询页
   */
	public ActionForward toSadQuery(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		List sadTypeList = cts.getLabelVaList("sadType");
		request.setAttribute("sadTypeList", sadTypeList);
		request.setAttribute("userList", ss.userQuery());
		return map.findForward("toSadQuery");
	}

	/**
	   * @describe 产品供求库统计类型选择页
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toSadStatisticQuery");
	}

	/**
	   * @describe 产品供求库统计类型跳转Action
	   */
	public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String path="";
		String type = request.getParameter("statisticType").toString();
		//System.out.println("type : "+type);
		if(type!=null&&!"".equals(type)){
			if("proc_productPriceStatisticsBySeat".equals(type)) return new ActionForward("/stat/priceAgentProCount.do?method=toMain");
			if("proc_sadStatisticsByType".equals(type)) return new ActionForward("/stat/sadStatisticsByType.do?method=toMain");
			if("proc_sadStatisticsByProduct".equals(type)) return new ActionForward("/stat/sadInfoForProduct.do?method=toMain");
			if("StatisticsByState".equals(type)) return new ActionForward("/stat/productPriceStatisticsByState.do?method=toMain");
		}
		return null;
	}
	
	/**
   * @describe 页面Load
   */
	public ActionForward toSadLoad(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		// 首先在load方法中清除session变量
		request.getSession().removeAttribute("oldUploadFile");
		request.getSession().removeAttribute("uploadfile");

		DynaActionFormDTO dbd = (DynaActionFormDTO) form;

		String path = Constants.getProperty("photo_realpath");
		String type = request.getParameter("type");

		request.setAttribute("opertype", type);

		List sadTypeList = cts.getLabelVaList("sadType");
		request.setAttribute("sadTypeList", sadTypeList);

		if (type.equals("insert"))
		{
			IBaseDTO dto = (IBaseDTO) form;
			UserBean ub = (UserBean)request.getSession().getAttribute(
					SysStaticParameter.USERBEAN_IN_SESSION);
			request.setAttribute("pic", "false");
			
			dto.set("sadRid", ub.getUserId());
			
			return map.findForward("toSadLoad");
		}
		if (type.equals("update"))
		{
			String id = request.getParameter("id");
			IBaseDTO dto = ss.getSadInfo(id);
		
			request.setAttribute(map.getName(), dto);

			request.getSession().setAttribute("oldUploadFile", dto.get("uploadfile"));// 用于比较原来是否有上传文件的

			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("sadRid").toString());
			return map.findForward("toSadLoad");
		}
		if (type.equals("detail"))
		{
			String id = request.getParameter("id");

			IBaseDTO dto = ss.getSadInfo(id);
			request.setAttribute(map.getName(), dto);

			

			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("sadRid").toString());
			return map.findForward("toSadLoad");
		}
		if (type.equals("delete"))
		{
			String id = request.getParameter("id");

			IBaseDTO dto = ss.getSadInfo(id);
			request.setAttribute(map.getName(), dto);

			
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("sadRid").toString());
			return map.findForward("toSadLoad");
		}
		return map.findForward("toSadLoad");
	}

	/**
   * @describe 市场供求列表页
   */
	public ActionForward toSadList(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		List list = null;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null)
		{
			pageInfo = new PageInfo();
		}
		else
		{
			PageTurning pageTurning = (PageTurning) request.getSession().getAttribute("sadpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(17);
		try
		{
			list = ss.sadQuery(dto, pageInfo);
		}
		catch(Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		int size = ss.getSadSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "/cc_agro_sy/", map, request);
		request.getSession().setAttribute("sadpageTurning", pt);
		return map.findForward("toSadList");
	}
	
	/**
	   * @describe 市场供求列表页
	   */
		public ActionForward toSadInfoList(ActionMapping map, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception
		{
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			List list = null;
			String pageState = null;
			PageInfo pageInfo = null;
			pageState = (String) request.getParameter("pagestate");
			if (pageState == null)
			{
				pageInfo = new PageInfo();
			}
			else
			{
				PageTurning pageTurning = (PageTurning) request.getSession().getAttribute("sadpageTurning");
				pageInfo = pageTurning.getPage();
				pageInfo.setState(pageState);
				dto = (DynaActionFormDTO) pageInfo.getQl();
			}
			pageInfo.setPageSize(200);
			try
			{
				list = ss.sadInfoQuery(dto, pageInfo);
			}
			catch(Exception e)
			{
				// TODO: handle exception
				e.printStackTrace();
			}

			int size = ss.getSadSize();
			pageInfo.setRowCount(size);
			pageInfo.setQl(dto);
			request.setAttribute("list", list);
			
			PageTurning pt = new PageTurning(pageInfo, "/cc_agro_sy/", map, request);
			request.getSession().setAttribute("sadpageTurning", pt);
			return map.findForward("sadInfoLoad");
		}

	/**
   * @describe 价格供求添加,修改,删除页
   */
	public ActionForward toSadOper(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");

		request.setAttribute("opertype", type);
		List sadTypeList = cts.getLabelVaList("sadType");
		request.setAttribute("sadTypeList", sadTypeList);

		if (type.equals("insert"))
		{
			try
			{
				Map infoMap = (Map) request.getSession().getAttribute(
						SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userId = (String) infoMap.get("userId");
				dto.set("subid", userId);// 提交人id
				
				String uploadfile = (String) request.getSession().getAttribute("uploadfile");// 一定要在dto保存之前加，否则不能保存到数据库
				if (uploadfile != null && !uploadfile.equals(""))
				{
					dto.set("uploadfile", uploadfile);
					request.getSession().removeAttribute("uploadfile");
				}
				ss.addSad(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
			}
			catch(RuntimeException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return map.findForward("error");
			}
		}
		if (type.equals("update"))
		{
			try
			{
				Map infoMap = (Map) request.getSession().getAttribute(
						SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userId = (String) infoMap.get("userId");
				dto.set("subid", userId);// 提交人id
				dto.set("accid", "admin");// 受理人id

				String uploadfile = (String) request.getSession().getAttribute("uploadfile");
				String oldUploadFile = (String) request.getSession().getAttribute("oldUploadFile");// 来源于页面的记录原来数据库里的上传文件
				if (uploadfile != null && !uploadfile.equals(""))
				{ // 如果有上传则合成字符串
					if (oldUploadFile != null && !oldUploadFile.equals(""))
					{ // 如果原来有上传，则合成串
						uploadfile = oldUploadFile + "," + uploadfile;

					} // 如果原来没上传，则直接用新串
					dto.set("uploadfile", uploadfile);
				}
				else
				{ // 如果没上传则把原来的字符串赋回来
					dto.set("uploadfile", oldUploadFile);
				}
				request.getSession().removeAttribute("oldUploadFile"); // 最后清除session变量
				request.getSession().removeAttribute("uploadfile");

				boolean b = ss.updateSad(dto);
				if (b == true)
				{
					request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
					return map.findForward("toSadLoad");
				}
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toSadLoad");
			}
			catch(RuntimeException e)
			{
				// log.error("PortCompareAction : update
        // ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}
		if (type.equals("delete"))
		{
			try
			{
				ss.delSad((String) dto.get("sadId"));
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toSadLoad");
			}
			catch(RuntimeException e)
			{
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return map.findForward("error");
			}
		}
		return map.findForward("toSadLoad");
	}

	/**
   * 上传照片
   * @param
   * @version 2006-9-16
   * @return
   */
	public ActionForward upload(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String type = (String) request.getParameter("type");

		String id = request.getParameter("id").toString();
		request.setAttribute("id", id);

		return map.findForward("upload");
	}

	public ActionForward popIntersave(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		return map.findForward("popIntersave");
	}

	public SadService getSs()
	{
		return ss;
	}

	public void setSs(SadService ss)
	{
		this.ss = ss;
	}

	public ClassTreeService getCts()
	{
		return cts;
	}

	public void setCts(ClassTreeService cts)
	{
		this.cts = cts;
	}

}
