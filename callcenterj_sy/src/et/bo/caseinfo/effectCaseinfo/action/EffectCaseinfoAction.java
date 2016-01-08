package et.bo.caseinfo.effectCaseinfo.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.caseinfo.effectCaseinfo.service.EffectCaseinfoService;
import et.bo.servlet.StaticServlet;
import et.bo.stat.service.impl.StatDateStr;
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

public class EffectCaseinfoAction extends BaseAction
{
	private ClassTreeService cts = null;

	private EffectCaseinfoService ecs = null;

	/**
   * @describe 进入效果案例库信主页面
   */
	public ActionForward toEffectCaseinfoMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		String str_state=request.getParameter("state");
		ecs.clearMessage(ub.getUserId(), str_state);
		request.setAttribute("state", str_state);
		return map.findForward("toEffectCaseinfoMain");
	}
	
	/**
	   * @describe 效果案例库统计类型选择页
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toEffectCaseinfoStatisticQuery");
	}

	/**
	   * @describe 效果案例库统计类型跳转Action
	   */
	public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String path="";
		String type = request.getParameter("statisticType").toString();
//		System.out.println("type : "+type);
		if(type!=null&&!"".equals(type)){
			if("agent".equals(type)) return new ActionForward("/stat/effectCaseInfoSeat.do?method=toMain");
			if("case".equals(type)) return new ActionForward("/stat/effectCaseInfoProperty.do?method=toMain");
			if ("staticbyeffect".equals(type)) return new ActionForward("/stat/effectStat.do?method=toMain");
		}
		return null;
	}

	/**
   * @describe 效果案例库信查询页
   */
	public ActionForward toEffectCaseinfoQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List user=ecs.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
		request.setAttribute("user", user);
		request.setAttribute("state", request.getParameter("state"));
		return map.findForward("toEffectCaseinfoQuery");
	}

	/**
   * @describe 页面Load
   */
	public ActionForward toEffectCaseinfoLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//专家类别
		//System.out.println("####"+cts);
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		
		request.getSession().removeAttribute("oldUploadFile");
		request.getSession().removeAttribute("uploadfile");
		
		DynaActionFormDTO dbd = (DynaActionFormDTO) form;

		String type = request.getParameter("type");
		String path = Constants.getProperty("photo_realpath");
		request.setAttribute("opertype", type);
		List effectCaseList = cts.getLabelVaList("effectCaseType");
		request.setAttribute("effectCaseList", effectCaseList);

		UserBean ub = (UserBean)request.getSession().getAttribute(
				SysStaticParameter.USERBEAN_IN_SESSION);
		
		if (type.equals("insert"))
		{
			IBaseDTO dto = (IBaseDTO) form;
			
			request.setAttribute("pic", "false");
			dto.set("caseRid", ub.getUserId());
			dto.set("caseTime", TimeUtil.getNowTimeSr());
			request.setAttribute(map.getName(), dto);
			
			return map.findForward("toEffectCaseinfoLoad");
		}
		if (type.equals("update"))
		{	
			
			String id = request.getParameter("id");
			IBaseDTO dto = ecs.getEffectCaseinfo(id);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			request.getSession().setAttribute("oldUploadFile", dto.get("uploadfile"));//用于比较原来是否有上传文件的
			// 为前台JSP更新时所用
			String videoPath = dto.get("caseVideo")==null?"":dto.get("caseVideo").toString();
			
			request.setAttribute("videoPath", videoPath);

			request.setAttribute(map.getName(), dto);
			String casePic = (String)dto.get("casePic");
			if(casePic == null || casePic.equals("")){
				request.setAttribute("pic", "false");
			}else{
				dto.set("casePic", path + casePic);
				request.setAttribute("pic", "true");
			}
			
//			if(!ub.getUserId().equals(dto.get("caseRid"))&&!StaticServlet.userPowerMap.get("效果案例库").contains(ub.getUserId()))
//				request.setAttribute("opertype", "detail");	
			
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			return map.findForward("toEffectCaseinfoLoad");
		}
		if (type.equals("detail"))
		{
			String id = request.getParameter("id");
			IBaseDTO dto = ecs.getEffectCaseinfo(id);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			request.setAttribute(map.getName(), dto);
			String casePic = (String)dto.get("casePic");
			if(casePic == null || casePic.equals("")){
				request.setAttribute("pic", "false");
			}else{
				dto.set("casePic", path + casePic);
				request.setAttribute("pic", "true");
			}
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			return map.findForward("toEffectCaseinfoLoad");
		}
		if (type.equals("delete"))
		{
			String id = request.getParameter("id");
			IBaseDTO dto = ecs.getEffectCaseinfo(id);
			String casePic = (String)dto.get("casePic");
			if(casePic == null || casePic.equals("")){
				request.setAttribute("pic", "false");
			}else{
				dto.set("casePic", path + casePic);
				request.setAttribute("pic", "true");
			}
			
//			if(!ub.getUserId().equals(dto.get("caseRid"))&&!StaticServlet.userPowerMap.get("效果案例库").contains(ub.getUserId()))
//				request.setAttribute("opertype", "detail");	
			
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			request.setAttribute(map.getName(), dto);
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			return map.findForward("toEffectCaseinfoLoad");
		}
		return map.findForward("toEffectCaseinfoLoad");
	}

	/**
   * @describe 效果案例库信列表页
   */
	public ActionForward toEffectCaseinfoList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		StatDateStr.setBeginEndTimeAll(dto);
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
					"effectCaseinfopageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(19);
		try
		{
			list = ecs.effectCaseinfoQuery(dto, pageInfo);
		}
		catch(Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		int size = ecs.getEffectCaseinfoSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "/cc_agro_sy/", map, request);
		request.getSession().setAttribute("effectCaseinfopageTurning", pt);
		return map.findForward("toEffectCaseinfoList");
	}

	/**
   * @describe 效果案例库信添加,修改,删除页
   */
	public ActionForward toEffectCaseinfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");

		request.setAttribute("opertype", type);
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		List effectCaseList = cts.getLabelVaList("effectCaseType");
		request.setAttribute("effectCaseList", effectCaseList);

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
				ecs.addEffectCaseinfo(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
			}
			catch(RuntimeException e)
			{
				// TODO Auto-generated catch block
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

				boolean b = ecs.updateEffectCaseinfo(dto);
				if (b == true)
				{
					request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
					return map.findForward("toEffectCaseinfoLoad");
				}
				request.setAttribute("operSign", "sys.common.operSuccess");
				
				return map.findForward("toEffectCaseinfoLoad");
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
				ecs.delEffectCaseinfo((String) dto.get("caseId"));
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toEffectCaseinfoLoad");
			}
			catch(RuntimeException e)
			{
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return map.findForward("error");
			}
		}
		return map.findForward("toEffectCaseinfoLoad");
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
   * @version 2006-9-16
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

	public EffectCaseinfoService getEcs()
	{
		return ecs;
	}

	public void setEcs(EffectCaseinfoService ecs)
	{
		this.ecs = ecs;
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

}
