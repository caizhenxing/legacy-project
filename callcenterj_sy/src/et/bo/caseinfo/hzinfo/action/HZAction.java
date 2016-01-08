package et.bo.caseinfo.hzinfo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.caseinfo.casetype.service.CaseTypeService;
import et.bo.caseinfo.hzinfo.service.HZService;
import et.bo.servlet.StaticServlet;
import et.bo.stat.service.impl.StatDateStr;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class HZAction extends BaseAction
{

	private ClassTreeService cts = null;

	private HZService hzs = null;

	private CaseTypeService cs = null;
	
	/**
	   * @describe ���ﰸ����ͳ������ѡ��ҳ
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toHZInfoStatisticQuery");
	}

	/**
	   * @describe ���ﰸ����ͳ��������תAction
	   */
	public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String type = request.getParameter("statisticType").toString();
		if(type!=null&&!"".equals(type)){
			if("agent".equals(type)) return new ActionForward("/stat/hzCaseInfo.do?method=toMain");
			if("case".equals(type)) return new ActionForward("/stat/hzCaseInfoSeat.do?method=toMain");
			if("all".equals(type)) return new ActionForward("/stat/HZCaseInfoStatisticsByState.do?method=toMain");
			if("expert".equals(type)) return new ActionForward("/stat/hzCaseInfoForExport.do?method=toMain");
		}
		return null;
	}
	
	/**
   * @describe ������ﰸ��������ҳ��
   */
	public ActionForward tohzinfoMain(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		String str_state=request.getParameter("state");
		hzs.clearMessage(ub.getUserId(), str_state);
		request.setAttribute("state", str_state);
		return map.findForward("tohzinfoMain");
	}

	/**
   * @describe ���ﰸ������ѯҳ
   */
	public ActionForward tohzinfoQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List export=hzs.exportQuery("select cust_name from oper_custinfo where dict_cust_type='SYS_TREE_0000002103' order by cust_name asc");
		List user=hzs.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141'");
		request.setAttribute("export", export);
		request.setAttribute("user", user);
		request.setAttribute("state", request.getParameter("state"));
		return map.findForward("tohzinfoQuery");
	}

	/**
   * @describe ҳ��Load
   */
	/**
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward tohzinfoLoad(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

//		ר�����
		//System.out.println("####"+cts);
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		//System.out.println("##################################expertList"+expertList);
		
		DynaActionFormDTO dbd = (DynaActionFormDTO) form;
		
		request.getSession().removeAttribute("oldUploadFile");
		request.getSession().removeAttribute("uploadfile");
		
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		UserBean ub = (UserBean)request.getSession().getAttribute(
				SysStaticParameter.USERBEAN_IN_SESSION);
		
//		��������
		List list= cs.loadBigType();
		request.setAttribute("bigtypelist", list);
		
		if (type.equals("insert"))
		{
			IBaseDTO dto = (IBaseDTO) form;
			
//			����С��
			request.setAttribute("smallTypeList", new ArrayList());
			dto.set("caseRid", ub.getUserId());
			dto.set("caseTime", TimeUtil.getNowTimeSr());
			request.setAttribute(map.getName(), dto);
			request.setAttribute("pic", "false");
			return map.findForward("tohzinfoLoad");
		}
		if (type.equals("update"))
		{
			String id = request.getParameter("id");
			IBaseDTO dto = hzs.getHZinfo(id);

			//���ݴ���õ�С��
			String caseAttr1 = (String)dto.get("caseAttr1");
			if(caseAttr1!= null&&!caseAttr1.equals("")){				
				request.setAttribute("smallTypeList", cs.loadSmallTypeByBigType(caseAttr1));
			}else{
				request.setAttribute("smallTypeList", new ArrayList());
			}
			
			request.getSession().setAttribute("oldUploadFile", dto.get("uploadfile"));//���ڱȽ�ԭ���Ƿ����ϴ��ļ���

			request.setAttribute(map.getName(), dto);
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			
//			if(!ub.getUserId().equals(dto.get("caseRid"))&&!StaticServlet.userPowerMap.get("���ﰸ����").contains(ub.getUserId()))
//				request.setAttribute("opertype", "detail");	
			
			return map.findForward("tohzinfoLoad");
		}
		if (type.equals("detail"))
		{
			String id = request.getParameter("id");
			IBaseDTO dto = hzs.getHZinfo(id);

			//���ݴ���õ�С��
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
			return map.findForward("tohzinfoLoad");
		}
		if (type.equals("delete"))
		{
			String id = request.getParameter("id");
			IBaseDTO dto = hzs.getHZinfo(id);

			//���ݴ���õ�С��
			String caseAttr1 = (String)dto.get("caseAttr1");
			if(caseAttr1!= null&&!caseAttr1.equals("")){				
				request.setAttribute("smallTypeList", cs.loadSmallTypeByBigType(caseAttr1));
			}else{
				request.setAttribute("smallTypeList", new ArrayList());
			}
			
			request.setAttribute(map.getName(), dto);
			request.setAttribute("id", id);
			request.setAttribute("caseid", dto.get("caseRid").toString());
			request.setAttribute("rExpertName", dto.get("caseExpert"));
			
//			if(!ub.getUserId().equals(dto.get("caseRid"))&&!StaticServlet.userPowerMap.get("���ﰸ����").contains(ub.getUserId()))
//				request.setAttribute("opertype", "detail");
			
			return map.findForward("tohzinfoLoad");
		}
		return map.findForward("tohzinfoLoad");
	}

	/**
   * @describe ���ﰸ�������б�ҳ
   */
	public ActionForward tohzinfoList(ActionMapping map, ActionForm form, HttpServletRequest request,
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
			PageTurning pageTurning = (PageTurning) request.getSession().getAttribute("hzpageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(19);
		try
		{
			list = hzs.hzinfoQuery(dto, pageInfo);
		}
		catch(Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		int size = hzs.getHZinfoSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "/cc_agro_sy/", map, request);
		request.getSession().setAttribute("hzpageTurning", pt);
		return map.findForward("tohzinfoList");
	}

	/**
   * @describe �����������,�޸�,ɾ��ҳ
   */
	public ActionForward tohzinfo(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
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
				
				hzs.addHZinfo(dto);
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
				String uploadfile = (String)request.getSession().getAttribute("uploadfile");
				String oldUploadFile = (String)request.getSession().getAttribute("oldUploadFile");//��Դ��ҳ��ļ�¼ԭ�����ݿ�����ϴ��ļ�					
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
				
				Map infoMap = (Map) request.getSession().getAttribute(
						SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userId = (String) infoMap.get("userId");
				dto.set("subid", userId);// �ύ��id
				
				boolean b = hzs.updateHZinfo(dto);
				if (b == true)
				{
					request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
					return map.findForward("tohzinfoLoad");
				}
				
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("tohzinfoLoad");
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
				hzs.delHZinfo((String) dto.get("caseId"));
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("tohzinfoLoad");
			}
			catch(RuntimeException e)
			{
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return map.findForward("error");
			}
		}
		return map.findForward("tohzinfoLoad");
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

	public HZService getHzs()
	{
		return hzs;
	}

	public void setHzs(HZService hzs)
	{
		this.hzs = hzs;
	}

	public CaseTypeService getCs() {
		return cs;
	}

	public void setCs(CaseTypeService cs) {
		this.cs = cs;
	}

}
