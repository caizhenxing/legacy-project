/**
 * 	@(#) InquiryResultAction.java 2008-4-9 下午02:05:25
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
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
 * 热线调查参与、结果查询相关请求的Action实现类
 * @author 梁云锋
 * 
 */
public class InquiryResultAction extends BaseAction {
	static Logger log = Logger.getLogger(InquiryAction.class);
	private ClassTreeService classTree;
	//对应et.bo.inquiry.service.impl.InquiryResultServiceImpl类 用于查询、保存调查主题参与结果
	private InquiryResultService inquiryResultService;
	//对应et.bo.inquiry.service.impl.InquiryServiceImpl类 用于查询符合条件调查主题 根据主题信息查询参与结果信息
	private InquiryService inquiryService;
	public InquiryService getInquiryService() {
		return inquiryService;
	}
	public void setInquiryService(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	/**
	 * 跳转到调查结果的主框架页/inquiry/inquiryResultMain.jsp
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
	 * 跳转到调查结果查询条件录入界面/inquiry/inquiryResultQuery.jsp
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
	   * @describe 调查问卷分析库统计类型选择页
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toinquiryinfoAnalysisStatisticQuery");
	}

	/**
	   * @describe 调查问卷分析库统计类型跳转Action
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
	 * 执行调查结果查询请求 将查询的调查主题列表显示于页面上
	 * 跳转到/inquiry/inquiryResultList.jsp
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
	 * 处理显示调查结果详细信息的请求
	 * 跳转到/inquiry/inquiryResultDetail.jsp
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
	 * 处理保存用户参与调查的结果的请求
	 * 显示相应的成功信息并关闭窗口
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
			
			String[] answer = request.getParameterValues(cardIds[i]);//取第几个问题的所有答案
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
		//向session里存信息，用于来电弹屏

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
