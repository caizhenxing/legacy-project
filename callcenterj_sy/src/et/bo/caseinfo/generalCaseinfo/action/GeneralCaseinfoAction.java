package et.bo.caseinfo.generalCaseinfo.action;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import et.bo.caseinfo.casetype.service.CaseTypeService;
import et.bo.caseinfo.generalCaseinfo.service.GeneralCaseinfoService;

import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.Constants;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class GeneralCaseinfoAction extends BaseAction
{

	private ClassTreeService cts = null;

	private GeneralCaseinfoService gcs = null;
	
	private CaseTypeService cs = null;

	/**
   * @describe ������ͨ����������ҳ��
   */
	public ActionForward toGeneralCaseinfoMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		String str_state=request.getParameter("state");
		gcs.clearMessage(ub.getUserId(), str_state);
		request.setAttribute("state", str_state);
		return map.findForward("toGeneralCaseinfoMain");
	}
	
	/**
	 * �����շ�ͳ����ʱ��������ʱ�ģ�
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toSendAndReceiveMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		return map.findForward("toSendAndReceiveMain");
	}
	public ActionForward toSendAndReceiveQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		return map.findForward("toSendAndReceiveQuery");
	}
	public ActionForward toSendAndReceiveList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		return map.findForward("toSendAndReceiveList");
	}

	/**
	 * �����䰸����ҳ��
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toGeneralCaseinfoMain1(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		return map.findForward("main1");
	}
	
	/**
	 * �����䰸����ѯҳ��
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toGeneralCaseinfoQuery1(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List export=gcs.exportQuery(SysStaticParameter.QUERY_EXPERT_SQL);
		List user=gcs.userQuery(SysStaticParameter.QUERY_USER_SQL);
		request.setAttribute("export", export);
		request.setAttribute("user", user);
		return map.findForward("query1");
	}
	/**
   * @describe ��ͨ�������Ų�ѯҳ
   */
	public ActionForward toGeneralCaseinfoQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List export=gcs.exportQuery("select cust_name from oper_custinfo where dict_cust_type='SYS_TREE_0000002103' order by cust_name asc");
		List user=gcs.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
		request.setAttribute("export", export);
		request.setAttribute("user", user);
		request.setAttribute("state", request.getParameter("state"));
		return map.findForward("toGeneralCaseinfoQuery");
	}
	
	/**
	   * @describe ��ͨ������ͳ������ѡ��ҳ
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toGeneralCaseinfoStatisticQuery");
	}

	/**
	   * @describe ��ͨ������ͳ��������תAction
	   */
	public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String path="";
		String type = request.getParameter("statisticType").toString();
//		System.out.println("type : "+type);
		if(type!=null&&!"".equals(type)){
			if("agent".equals(type)) return new ActionForward("/stat/usercase.do?method=toMain");
			if("case".equals(type)) return new ActionForward("/stat/case.do?method=toMain");
			if("StatisticsByState".equals(type)) return new ActionForward("/stat/commonStateCase.do?method=toMain");
			if("StatisticsByExpert".equals(type)) return new ActionForward("/sate/caseinfoForExport.do?method=toMain");

			if("all".equals(type)) return new ActionForward("/stat/HZCaseInfoStatisticsByState.do?method=toMain");
			//return new ActionForward("/stat/HZCaseInfoStatisticsByState.do?method=toMain");
		}
		return null;
	}
	
	
	/**
   * @describe ҳ��Load
   */
	public ActionForward toGeneralCaseinfoLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		//ר�����
		//System.out.println("####"+cts);
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		DynaActionFormDTO dbd = (DynaActionFormDTO) form;
		
		request.getSession().removeAttribute("oldUploadFile");
		request.getSession().removeAttribute("uploadfile");
		
		String type = request.getParameter("type");
		String path = Constants.getProperty("photo_realpath");
		request.setAttribute("opertype", type);

		UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		
//		��������
		List list= cs.loadBigType();
		request.setAttribute("bigtypelist", list);
		
		if (type.equals("insert"))
		{
			IBaseDTO dto = (IBaseDTO) form;			
//			����С��
			request.setAttribute("smallTypeList", new ArrayList());
			dto.set("caseRid", ub.getUserId());
			request.setAttribute("pic", "false");
			dto.set("caseTime", TimeUtil.getNowTimeSr());
			request.setAttribute(map.getName(), dto);
			return map.findForward("toGeneralCaseinfoLoad");
		}
		if (type.equals("update"))
		{			
			String id = request.getParameter("id");

			IBaseDTO dto = gcs.getGeneralCaseinfo(id);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			
			//���ݴ���õ�С��
			String caseAttr1 = (String)dto.get("caseAttr1");
			if(caseAttr1!= null&&!caseAttr1.equals("")){				
				request.setAttribute("smallTypeList", cs.loadSmallTypeByBigType(caseAttr1));
			}else{
				request.setAttribute("smallTypeList", new ArrayList());
			}
			
			request.getSession().setAttribute("oldUploadFile", dto.get("uploadfile"));//���ڱȽ�ԭ���Ƿ����ϴ��ļ���
			//Ϊǰ̨JSP����ʱ����
			String videoPath = (String)dto.get("caseVideo");
			//System.out.println("GeneralCaseinfoAction------->�����ϴ���Ƶ��·��Ϊ��"+videoPath);
			request.setAttribute("videoPath", videoPath);
			
			request.setAttribute(map.getName(), dto);
			
			String casePic = (String)dto.get("casePic");
			if(casePic == null || casePic.equals("")){
				request.setAttribute("pic", "false");
			}else{
				dto.set("casePic", path + casePic);
				request.setAttribute("pic", "true");
			}			
			
//			if(!ub.getUserId().equals(dto.get("caseRid"))&&!StaticServlet.userPowerMap.get("��ͨ������").contains(ub.getUserId()))
//				request.setAttribute("opertype", "detail");			
			
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			return map.findForward("toGeneralCaseinfoLoad");
		}
		if (type.equals("detail"))
		{
			String id = request.getParameter("id");

			IBaseDTO dto = gcs.getGeneralCaseinfo(id);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			
//			���ݴ���õ�С��
			String caseAttr1 = (String)dto.get("caseAttr1");
			if(caseAttr1!= null&&!caseAttr1.equals("")){				
				request.setAttribute("smallTypeList", cs.loadSmallTypeByBigType(caseAttr1));
			}else{
				request.setAttribute("smallTypeList", new ArrayList());
			}
			
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
			return map.findForward("toGeneralCaseinfoLoad");
		}
		if (type.equals("delete"))
		{
			String id = request.getParameter("id");
			IBaseDTO dto = gcs.getGeneralCaseinfo(id);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			String casePic = (String)dto.get("casePic");
			if(casePic == null || casePic.equals("")){
				request.setAttribute("pic", "false");
			}else{
				dto.set("casePic", path + casePic);
				request.setAttribute("pic", "true");
			}
			
//			���ݴ���õ�С��
			String caseAttr1 = (String)dto.get("caseAttr1");
			if(caseAttr1!= null&&!caseAttr1.equals("")){				
				request.setAttribute("smallTypeList", cs.loadSmallTypeByBigType(caseAttr1));
			}else{
				request.setAttribute("smallTypeList", new ArrayList());
			}
			
			request.setAttribute(map.getName(), dto);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			
//			if(!ub.getUserId().equals(dto.get("caseRid"))&&!StaticServlet.userPowerMap.get("��ͨ������").contains(ub.getUserId()))
//				request.setAttribute("opertype", "detail");	
			
			return map.findForward("toGeneralCaseinfoLoad");
		}
		return map.findForward("toGeneralCaseinfoLoad");
	}

	/**
   * @describe ��ͨ���������б�ҳ
   */
	public ActionForward toGeneralCaseinfoList(ActionMapping map, ActionForm form,
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
					"consultationCaseinfopageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(19);
		try
		{
			list = gcs.generalCaseinfoQuery(dto, pageInfo);
		}
		catch(Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		int size = gcs.getGeneralCaseinfoSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "/cc_agro_sy/", map, request);
		request.getSession().setAttribute("consultationCaseinfopageTurning", pt);

		return map.findForward("toGeneralCaseinfoList");
	}

	/**
   * @describe ��ͨ�����������,�޸�,ɾ��ҳ
   */
	public ActionForward toGeneralCaseinfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		request.setAttribute("opertype", type);
		
		//��������
		request.setAttribute("bigtypelist", new ArrayList());
//		����С��
		request.setAttribute("smallTypeList", new ArrayList());
		
		if (type.equals("insert"))
		{
			try
			{	
				Map infoMap = (Map) request.getSession().getAttribute(
						SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userId = (String) infoMap.get("userId");
				dto.set("subid", userId);// �ύ��id
				
				String uploadfile = (String)request.getSession().getAttribute("uploadfile");
				if(uploadfile !=null && !uploadfile.equals("")){
					dto.set("uploadfile", uploadfile);
					request.getSession().removeAttribute("uploadfile");
				}//����һ��Ҫ��dto����֮ǰ�ӣ������ܱ��浽���ݿ�
				gcs.addGeneralCaseinfo(dto);
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
//				System.out.println("session uploadfile is "+uploadfile);
				String oldUploadFile = (String)request.getSession().getAttribute("oldUploadFile");//��Դ��ҳ��ļ�¼ԭ�����ݿ�����ϴ��ļ�
//				System.out.println("session olduploadfile is "+oldUploadFile);
				if(uploadfile !=null && !uploadfile.equals("")){			//������ϴ���ϳ��ַ���
					if(oldUploadFile !=null && !oldUploadFile.equals("")){	//���ԭ�����ϴ�����ϳɴ�
						uploadfile = oldUploadFile + "," + uploadfile;
						
					}														//���ԭ��û�ϴ�����ֱ�����´�
					dto.set("uploadfile", uploadfile);
				}else{														//���û�ϴ����ԭ�����ַ���������
					dto.set("uploadfile", oldUploadFile);
				}
				request.getSession().removeAttribute("oldUploadFile");		//������session����
				request.getSession().removeAttribute("uploadfile");
				
				Map infoMap = (Map) request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userId = (String) infoMap.get("userId");
				dto.set("subid", userId);// �ύ��id
				boolean b = gcs.updateGeneralCaseinfo(dto);
				if (b == true)
				{
					request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
					return map.findForward("toGeneralCaseinfoLoad");
				}
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toGeneralCaseinfoLoad");
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
				gcs.delGeneralCaseinfo((String) dto.get("caseId"));
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toGeneralCaseinfoLoad");
			}
			catch(RuntimeException e)
			{
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return map.findForward("error");
			}
		}
		return map.findForward("toGeneralCaseinfoLoad");
	}

	/**
   * �ϴ���Ƭ
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
   * �ϴ���Ƶ
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

	public GeneralCaseinfoService getGcs()
	{
		return gcs;
	}

	public void setGcs(GeneralCaseinfoService gcs)
	{
		this.gcs = gcs;
	}

	public CaseTypeService getCs() {
		return cs;
	}

	public void setCs(CaseTypeService cs) {
		this.cs = cs;
	}

}
