/**
 * 	@(#) InquiryResultAction.java 2008-4-9 ����02:05:25
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.inquiry.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.inquiry.service.InquiryResultService;
import et.bo.inquiry.service.InquiryService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * ���ߵ�����롢�����ѯ��������Actionʵ����
 * @author ���Ʒ�
 * 
 */
public class InquiryResultAction extends BaseAction {
	static Logger log = Logger.getLogger(InquiryAction.class);
	private ClassTreeService classTree;
	//��Ӧet.bo.inquiry.service.impl.InquiryResultServiceImpl�� ���ڲ�ѯ������������������
	private InquiryResultService inquiryResultService;
	//��Ӧet.bo.inquiry.service.impl.InquiryServiceImpl�� ���ڲ�ѯ���������������� ����������Ϣ��ѯ��������Ϣ
	private InquiryService inquiryService;
	public InquiryService getInquiryService() {
		return inquiryService;
	}
	public void setInquiryService(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	/**
	 * ��ת���������������ҳ/inquiry/inquiryResultMain.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward("main");
	}
	/**
	 * ��ת����������ѯ����¼�����/inquiry/inquiryResultQuery.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("inquiryTypes", classTree
				.getLabelVaList("inquiryTypes"));
		return mapping.findForward("query");
	}
	
	/**
	   * @describe �����ʾ������ͳ������ѡ��ҳ
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toinquiryinfoAnalysisStatisticQuery");
	}

	/**
	   * @describe �����ʾ������ͳ��������תAction
	   */
	public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String type = request.getParameter("statisticType").toString();
		if(type!=null&&!"".equals(type)){
			if("BySeat".equals(type)) return new ActionForward("/stat/inquiryStatisticsBySeat.do?method=toMain");
			if("ByQuestion".equals(type)) return new ActionForward("/stat/inquiryStatisticsByQuestion.do?method=toMain");
			if("ByAnswer".equals(type)) return new ActionForward("/stat/inquiryStatisticsByAnswer.do?method=toMain");
		}
		return null;
	}
	
	/**
	 * ִ�е�������ѯ���� ����ѯ�ĵ��������б���ʾ��ҳ����
	 * ��ת��/inquiry/inquiryResultList.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
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
	 * ������ʾ��������ϸ��Ϣ������
	 * ��ת��/inquiry/inquiryResultDetail.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toResultDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		List<DynaBeanDTO> results=inquiryResultService.getResultInfo(id);
		request.setAttribute("results", results);
		return mapping.findForward("resultDetail");
	}
	/**
	 * �������û��������Ľ��������
	 * ��ʾ��Ӧ�ĳɹ���Ϣ���رմ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String inquiryId = request.getParameter("inquiryId");
		String[] cardIds = request.getParameterValues("cardIds");
		String[] questionTypes = request.getParameterValues("questionTypes");
		String[] questionS = request.getParameterValues("questionS");
		
		if(cardIds == null || questionTypes == null || questionS == null){
			return null;
		}
		
		java.util.Map infoMap = (java.util.Map)request.getSession().getAttribute(et.bo.sys.common.SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
		String userId = (String)infoMap.get("userId");
		List answerList = new ArrayList();
		
		for(int i = 0; i < cardIds.length; i++){
			
			String[] answer = request.getParameterValues(cardIds[i]);//ȡ�ڼ�����������д�
			Map valueMap = new HashMap();
			valueMap.put("topic_id", inquiryId);
			valueMap.put("rid", userId);
			valueMap.put("card_id", cardIds[i]);
			valueMap.put("question_type", questionTypes[i]);
			valueMap.put("question", questionS[i]);
			valueMap.put("answer", answer);
			
			answerList.add(valueMap);
		}
		inquiryResultService.save(answerList);
		//��session�����Ϣ���������絯��

		request.getSession().setAttribute("inquiry_num", inquiryResultService.getInquiryNum());
		return mapping.findForward("success");
	}

	public InquiryResultService getInquiryResultService() {
		return inquiryResultService;
	}

	public void setInquiryResultService(
			InquiryResultService inquiryResultService) {
		this.inquiryResultService = inquiryResultService;
	}
	public ClassTreeService getClassTree() {
		return classTree;
	}
	public void setClassTree(ClassTreeService classTree) {
		this.classTree = classTree;
	}
}
