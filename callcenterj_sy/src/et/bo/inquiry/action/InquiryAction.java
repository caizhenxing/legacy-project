/**
 * 	@(#) InquiryAction.java 2008-4-1 ����01:12:41
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.inquiry.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.common.ConstantsCommonI;
import et.bo.inquiry.service.InquiryService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * ���ߵ�������ά����Ӧ��Actionʵ����
 * �������ߵ�������ά��ģ��Ĳ�ѯ����ʾ�����ص�ҳ������ 
 * @author ���Ʒ�
 * @version 1.0
 */
public class InquiryAction extends BaseAction {
	static Logger log = Logger.getLogger(InquiryAction.class);

	//ΪActionע���service���� ��Ӧet.bo.inquiry.service.impl.InquiryServiceImpl
	private InquiryService inquiryService;

	//ΪActionע���classTree���� ���ڻ�ȡϵͳ���ò���
	private ClassTreeService classTree;

	public ClassTreeService getClassTree() {
		return classTree;
	}

	public void setClassTree(ClassTreeService classTree) {
		this.classTree = classTree;
	}

	public InquiryService getInquiryService() {
		return inquiryService;
	}

	public void setInquiryService(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}

	/**
	 * ��ת�����ߵ��������ҳ��/inquiry/inquiryMain.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
		String str_state=request.getParameter("state");
		inquiryService.clearMessage(ub.getUserId(), str_state);
		request.setAttribute("state", str_state);
		
		return mapping.findForward("main");
	}
	
	/**
	   * @describe �����ʾ���ƿ�ͳ������ѡ��ҳ
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toinquiryinfoStatisticQuery");
	}

	/**
	   * @describe �����ʾ���ƿ�ͳ��������תAction
	   */
	public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String type = request.getParameter("statisticType").toString();
		if(type!=null&&!"".equals(type)){
			if("ByOrganization".equals(type)) return new ActionForward("/stat/inquiryInfoByOrganization.do?method=toMain");
			if("ByType".equals(type)) return new ActionForward("/stat/inquiryStatisticsForType.do?method=toMain");
			if("OneEditor".equals(type)) return new ActionForward("/stat/markanaInfoByProduct.do?method=toMain");
			if("AllEditor".equals(type)) return new ActionForward("/stat/markanaInfoByProductType.do?method=toMain");
			if("Byorganizers".equals(type)) return new ActionForward("/stat/inquiryStatisticsByOrganizers.do?method=toMain");
		}
		return null;
	}
	

	/**
	 * ��ת�����ߵ���Ĳ�ѯ����ҳ��/inquiry/inquiryQuery.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		// ͨ��classTree���������е����ߵ�������б� 
		// ��������б���ӵ�request�� 
		// ��ҳ���ϵ�<html:select>��ǩʹ��
		
		request.setAttribute("inquiryTypes", classTree.getLabelVaList(
				"inquiryTypes", false));
		return mapping.findForward("query");
	}

	/**
	 * ���ݲ�ѯ����¼��Ĳ�ѯ����ִ�����ߵ�������Ĳ�ѯ����
	 * Ȼ����ת��/inquiry/inquiryList.jsp
	 * ����ѯ�õ��ĵ��������б���ʾ��ҳ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		// ȡ�÷�ҳ��Ϣ ���û��ȡ���ͳ�ʼ����ҳ��Ϣ
		String pageState = request.getParameter("pagestate");
		PageInfo pageInfo = null;
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("inquiryTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(20);
		//����service�Ĳ�ѯ���� ���ز�ѯ�������ߵ��������б�
		List<DynaBeanDTO> list = inquiryService.query(dto, pageInfo);
		int size = inquiryService.getInquirySize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", mapping, request);
		request.getSession().setAttribute("inquiryTurning", pt);
		return mapping.findForward("list");
	}

	/**
	 * ִ�м���ҳ�������
	 * ��ת��/inquiry/inquiryLoad.jsp
	 * ���ݲ�ͬ�ļ����������ִ����ӡ��޸ġ�ɾ���ļ�¼����
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
		//�������������������ѡ��ļ�¼��Ϣ
		if (!ConstantsCommonI.INSERT_OPER.equals(operation)) {
			String id = request.getParameter("id");
			IBaseDTO dto = inquiryService.getInquiryInfo(id);
			request.setAttribute(mapping.getName(), dto);
			
			Object o = dto.get("caserid");
			if(o != null)
				request.setAttribute("caseid", o.toString());
		}
		//��ȡ��������б� ��ҳ���ϵ�<html:select>��ǩʹ��
		request.setAttribute("inquiryTypes", classTree
				.getLabelVaList("inquiryTypes"));
		
		return mapping.findForward("load");
	}

	/**
	 * ִ����ӡ��޸ġ�ɾ�������ı�������
	 * �ɹ��������Ӧ����ʾ��Ϣ���ر��޸Ĵ���
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
		//��ӱ������ ִ��service��add����
		if (ConstantsCommonI.INSERT_OPER.equals(operation)) {
			Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
			String userId = (String)infoMap.get("userId");
			dto.set("subid", userId);//�ύ��id
			
			inquiryService.add(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		//�޸ı������ ִ��service��update����
		else if (ConstantsCommonI.UPDATE_OPER.equals(operation)) {
			Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
			String userId = (String)infoMap.get("userId");
			dto.set("subid", userId);//�ύ��id
			
			inquiryService.update(dto);
			request.setAttribute(mapping.getName(), inquiryService
					.getInquiryInfo(dto.get("id").toString()));
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		//ɾ��������� ִ��service��delete����
		else if (ConstantsCommonI.DELETE_OPER.equals(operation)) {
			inquiryService.delete(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		request.setAttribute("inquiryTypes", classTree
				.getLabelVaList("inquiryTypes"));
		return mapping.findForward("load");
	}

	/**
	 * �������絯�������ߵ��������
	 * ��ת��/inquiry/inquiryFilter.jsp
	 * ��ʾ������Ч�ı�ѡ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//ִ��service��filter���� ��ǰ��Ч�ı�ѡ���������б�
		List<DynaBeanDTO> list = inquiryService.filter();
		request.setAttribute("list", list);
		return mapping.findForward("filter");
	}

	/**
	 * ����/inquiry/inquiryFilter.jsp���������ѡ������
	 * ��ת��/inquiry/inquiryDisplayCard.jsp
	 * ��ʾѡ�е�������ĵ��鿨��Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplayCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");//�ʾ��
		List<DynaBeanDTO> cards = inquiryService.getCardInfo(id);
		request.setAttribute("cards", cards);
		request.setAttribute("inquiryId", id);
		return mapping.findForward("displayCard");
	}
	
	public ActionForward toDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("detail");
	}
	
	public ActionForward toStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");//�ʾ��
		List<DynaBeanDTO> cards = inquiryService.getCardInfoReport(id);
		request.setAttribute("cards", cards);
		
		return mapping.findForward("stat");
	}
	
	public ActionForward toReportLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");//�ʾ��
		IBaseDTO dto = inquiryService.getInquiryInfoReport(id);
		request.setAttribute(mapping.getName(), dto);
		request.setAttribute("reportTopic", dto.get("reportTopic"));
		return mapping.findForward("report");
	}
	
	public ActionForward toReportLoadLast(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		IBaseDTO dto = inquiryService.getInquiryInfoReportLast();
		request.setAttribute(mapping.getName(), dto);
		
		return mapping.findForward("reportLast");
	}
	
	public ActionForward toReportUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		try{
			Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
			String userId = (String)infoMap.get("userId");
			dto.set("subid", userId);//�ύ��id
			
			inquiryService.updateReport(dto);
		}catch(Exception e){
			log.error("ִ���޸ķ���ʱ�쳣��");
		}
		
		request.setAttribute("operSign", "ok");
		return mapping.findForward("report");
	}
	
	/**
	 * ɾ����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toReportDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		try{
			Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
			String userId = (String)infoMap.get("userId");
			dto.set("subid", userId);//�ύ��id
			
			inquiryService.deleteReport(dto);
		}catch(Exception e){
			log.error("ִ��ɾ������ʱ�쳣��");
		}
		
		request.setAttribute("operSign", "ok");
		return mapping.findForward("report");
	}
}
