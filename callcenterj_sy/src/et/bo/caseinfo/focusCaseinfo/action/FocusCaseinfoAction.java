package et.bo.caseinfo.focusCaseinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.caseinfo.focusCaseinfo.service.FocusCaseinfoService;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.Constants;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class FocusCaseinfoAction extends BaseAction
{
	private ClassTreeService		 cts = null;

	private FocusCaseinfoService fcs = null;

	/**
   * @describe 进入焦点案例库信息主页面
   */
	public ActionForward toFocusCaseinfoMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		String str_state=request.getParameter("state");
		fcs.clearMessage(ub.getUserId(), str_state);
		request.setAttribute("state", str_state);
		return map.findForward("toFocusCaseinfoMain");
	}
	/**
	 *  进入焦点关注主页面
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toFocusCaseinfoMain1(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("main1");
	}
	/**
	 *  进入焦点关注查询面
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toFocusCaseinfoQuery1(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
//		List export=fcs.exportQuery("select cust_name from oper_custinfo where dict_cust_type='SYS_TREE_0000002103' order by cust_name asc");
		List user=fcs.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
//		request.setAttribute("export", export);
		request.setAttribute("user", user);
		return map.findForward("query1");
	}
	/**
   * @describe 焦点案例库信息询页
   */
	public ActionForward toFocusCaseinfoQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
//		List export=fcs.exportQuery("select cust_name from oper_custinfo where dict_cust_type='SYS_TREE_0000002103' order by cust_name asc");
		List user=fcs.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
//		request.setAttribute("export", export);
		request.setAttribute("user", user);
		request.setAttribute("state", request.getParameter("state"));
		return map.findForward("toFocusCaseinfoQuery");
	}
	
	/**
	   * @describe 焦点案例库统计类型选择页
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toFocusCaseinfoStatisticQuery");
	}
	
	
	/**
	   * @describe 焦点案例库统计类型跳转Action
	   */
	public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String path="";
		String type = request.getParameter("statisticType").toString();

		if(type!=null&&!"".equals(type)){
			if("agent".equals(type)){ 
				return new ActionForward("/stat/focusCaseInfo.do?method=toMain");
			}
			if("case".equals(type)) {
				return new ActionForward("/stat/focusCaseInfoUser.do?method=toMain");
			}
			if("oneOrAll".equals(type))
			{
				return new ActionForward("/stat/focusCaseInfoStatistics.do?method=toMain");
			}
		}
		return null;
	}
	
	/**
   * @describe 页面Load
   */
	public ActionForward toFocusCaseinfoLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//专家类别
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		
		DynaActionFormDTO dbd = (DynaActionFormDTO) form;
		String path = Constants.getProperty("photo_realpath");
		String type = request.getParameter("type");
		request.getSession().removeAttribute("oldUploadFile");
		request.getSession().removeAttribute("uploadfile");
		request.setAttribute("opertype", type);
		List focusCaseList = cts.getLabelVaList("focusCase");
		request.setAttribute("focusCaseList", focusCaseList);

		UserBean ub = (UserBean)request.getSession().getAttribute(
				SysStaticParameter.USERBEAN_IN_SESSION);
		
		if (type.equals("insert"))
		{
			
			IBaseDTO dto = (IBaseDTO) form;
			
			request.setAttribute("pic", "false");
			dto.set("caseRid", ub.getUserId());
			dto.set("caseTime", TimeUtil.getNowTimeSr());
			request.setAttribute(map.getName(), dto);
			return map.findForward("toFocusCaseinfoLoad");
		}
		if (type.equals("update"))
		{	
			String id = request.getParameter("id");			
			IBaseDTO dto = fcs.getFocusCaseinfo(id);
			request.getSession().setAttribute("oldUploadFile", dto.get("uploadfile"));//用于比较原来是否有上传文件的
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			// 为前台JSP更新时所用
			String videoPath = (String)dto.get("caseVideo");
			
			request.setAttribute("videoPath", videoPath);

			request.setAttribute(map.getName(), dto);
			String casePic = (String)dto.get("casePic");
			if(casePic == null || casePic.equals("")){
				request.setAttribute("pic", "false");
			}else{
				dto.set("casePic", path + casePic);
				request.setAttribute("pic", "true");
			}

//			if(!ub.getUserId().equals(dto.get("caseRid"))&&!StaticServlet.userPowerMap.get("焦点案例库").contains(ub.getUserId()))
//				request.setAttribute("opertype", "detail");	
			
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			return map.findForward("toFocusCaseinfoLoad");
		}
		if (type.equals("detail"))
		{
			String id = request.getParameter("id");

			IBaseDTO dto = fcs.getFocusCaseinfo(id);
			request.setAttribute(map.getName(), dto);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			String casePic = (String)dto.get("casePic");
			if(casePic == null || casePic.equals("")){
				request.setAttribute("pic", "false");
			}else{
				dto.set("casePic", path + casePic);
				request.setAttribute("pic", "true");
			}
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			return map.findForward("toFocusCaseinfoLoad");
		}
		if (type.equals("delete"))
		{
			String id = request.getParameter("id");

			IBaseDTO dto = fcs.getFocusCaseinfo(id);
			request.setAttribute(map.getName(), dto);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			String casePic = (String)dto.get("casePic");
			if(casePic == null || casePic.equals("")){
				request.setAttribute("pic", "false");
			}else{
				dto.set("casePic", path + casePic);
				request.setAttribute("pic", "true");
			}
			
//			if(!ub.getUserId().equals(dto.get("caseRid"))&&!StaticServlet.userPowerMap.get("焦点案例库").contains(ub.getUserId()))
//				request.setAttribute("opertype", "detail");	
			
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			return map.findForward("toFocusCaseinfoLoad");
		}
		return map.findForward("toFocusCaseinfoLoad");
	}

	/**
   * @describe 焦点案例库信息列表页
   */
	public ActionForward toFocusCaseinfoList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
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
			PageTurning pageTurning = (PageTurning) request.getSession().getAttribute(
					"focusCaseinfopageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(19);
		try
		{
			list = fcs.focusCaseinfoQuery(dto, pageInfo);
		}
		catch(Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		int size = fcs.getFocusCaseinfoSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "/cc_agro_sy/", map, request);
		request.getSession().setAttribute("focusCaseinfopageTurning", pt);
		return map.findForward("toFocusCaseinfoList");
	}

	/**
   * @describe 焦点案例库信息添加,修改,删除页
   */
	public ActionForward toFocusCaseinfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");

		request.setAttribute("opertype", type);
		List focusCaseList = cts.getLabelVaList("focusCase");
		request.setAttribute("focusCaseList", focusCaseList);
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		if (type.equals("insert"))
		{
			try
			{
				Map infoMap = (Map) request.getSession().getAttribute(
						SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userId = (String) infoMap.get("userId");
				dto.set("subid", userId);// 提交人id
				
				String uploadfile = (String)request.getSession().getAttribute("uploadfile");
				if(uploadfile != null && !uploadfile.equals("")){
					dto.set("uploadfile", uploadfile);
					request.getSession().removeAttribute("uploadfile");
				}//以上一定要在dto保存之前加，否则不能保存到数据库
				fcs.addFocusCaseinfo(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
			}
			catch(RuntimeException e)
			{
				e.printStackTrace();
				return map.findForward("error");
			}
		}
		if (type.equals("update"))
		{
			try
			{
				String uploadfile = (String)request.getSession().getAttribute("uploadfile");
				String oldUploadFile = (String)request.getSession().getAttribute("oldUploadFile");//来源于页面的记录原来数据库里的上传文件					
				if(uploadfile !=null && !uploadfile.equals("")){			//如果有上传则合成字符串
					if(oldUploadFile !=null && !oldUploadFile.equals("")){	//如果原来有上传，则合成串
						uploadfile = oldUploadFile + "," + uploadfile;
						
					}														//如果原来没上传，则直接用新串
					dto.set("uploadfile", uploadfile);
				}else{														//如果没上传则把原来的字符串赋回来
					dto.set("uploadfile", oldUploadFile);
				}
				request.getSession().removeAttribute("oldUploadFile");		//最后清除session变量
				request.getSession().removeAttribute("uploadfile");
				Map infoMap = (Map) request.getSession().getAttribute(
						SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userId = (String) infoMap.get("userId");
				dto.set("subid", userId);// 提交人id

				boolean b = fcs.updateFocusCaseinfo(dto);
				if (b == true)
				{
					request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
					return map.findForward("toFocusCaseinfoLoad");
				}
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toFocusCaseinfoLoad");
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
				fcs.delFocusCaseinfo((String) dto.get("caseId"));
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toFocusCaseinfoLoad");
			}
			catch(RuntimeException e)
			{
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return map.findForward("error");
			}
		}
		return map.findForward("toFocusCaseinfoLoad");
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

	/**
   * 上传视频
   * @param
   * @version 2008-5-4
   * @return
   */
	public ActionForward uploadVideo(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String type = (String) request.getParameter("type");

		String id = request.getParameter("id").toString();
		request.setAttribute("id", id);

		return map.findForward("uploadVideo");
	}

	public ActionForward popIntersave(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		return map.findForward("popIntersave");
	}

	public ClassTreeService getCts()
	{
		return cts;
	}

	public void setCts(ClassTreeService cts)
	{
		this.cts = cts;
	}

	public FocusCaseinfoService getFcs()
	{
		return fcs;
	}

	public void setFcs(FocusCaseinfoService fcs)
	{
		this.fcs = fcs;
	}

}
