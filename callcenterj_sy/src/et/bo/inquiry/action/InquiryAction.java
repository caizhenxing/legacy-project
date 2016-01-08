/**
 * 	@(#) InquiryAction.java 2008-4-1 下午01:12:41
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
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
 * 热线调查主题维护对应的Action实现类
 * 接受热线调查主题维护模块的查询、显示、加载等页面请求 
 * @author 梁云锋
 * @version 1.0
 */
public class InquiryAction extends BaseAction {
	static Logger log = Logger.getLogger(InquiryAction.class);

	//为Action注入的service属性 对应et.bo.inquiry.service.impl.InquiryServiceImpl
	private InquiryService inquiryService;

	//为Action注入的classTree属性 用于获取系统配置参数
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
	 * 跳转到热线调查主框架页面/inquiry/inquiryMain.jsp
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
	   * @describe 调查问卷设计库统计类型选择页
	   */
	public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return map.findForward("toinquiryinfoStatisticQuery");
	}

	/**
	   * @describe 调查问卷设计库统计类型跳转Action
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
	 * 调转到热线调查的查询条件页面/inquiry/inquiryQuery.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		// 通过classTree对象获得所有的热线调查类别列表 
		// 并将这个列表添加到request中 
		// 供页面上的<html:select>标签使用
		
		request.setAttribute("inquiryTypes", classTree.getLabelVaList(
				"inquiryTypes", false));
		return mapping.findForward("query");
	}

	/**
	 * 根据查询界面录入的查询条件执行热线调查主题的查询请求
	 * 然后跳转到/inquiry/inquiryList.jsp
	 * 将查询得到的调查主题列表显示在页面上
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		// 取得分页信息 如果没有取到就初始化分页信息
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
		//调用service的查询方法 返回查询到的热线调查主题列表
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
	 * 执行加载页面的请求
	 * 调转到/inquiry/inquiryLoad.jsp
	 * 根据不同的加载请求可以执行添加、修改、删除的记录加载
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
		//如果不是添加请求则加载选择的记录信息
		if (!ConstantsCommonI.INSERT_OPER.equals(operation)) {
			String id = request.getParameter("id");
			IBaseDTO dto = inquiryService.getInquiryInfo(id);
			request.setAttribute(mapping.getName(), dto);
			
			Object o = dto.get("caserid");
			if(o != null)
				request.setAttribute("caseid", o.toString());
		}
		//获取调查类别列表 供页面上的<html:select>标签使用
		request.setAttribute("inquiryTypes", classTree
				.getLabelVaList("inquiryTypes"));
		
		return mapping.findForward("load");
	}

	/**
	 * 执行添加、修改、删除操作的保存请求
	 * 成功后给出相应的提示信息并关闭修改窗口
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
		//添加保存操作 执行service的add方法
		if (ConstantsCommonI.INSERT_OPER.equals(operation)) {
			Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
			String userId = (String)infoMap.get("userId");
			dto.set("subid", userId);//提交人id
			
			inquiryService.add(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		//修改保存操作 执行service的update方法
		else if (ConstantsCommonI.UPDATE_OPER.equals(operation)) {
			Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
			String userId = (String)infoMap.get("userId");
			dto.set("subid", userId);//提交人id
			
			inquiryService.update(dto);
			request.setAttribute(mapping.getName(), inquiryService
					.getInquiryInfo(dto.get("id").toString()));
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		//删除保存操作 执行service的delete方法
		else if (ConstantsCommonI.DELETE_OPER.equals(operation)) {
			inquiryService.delete(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		request.setAttribute("inquiryTypes", classTree
				.getLabelVaList("inquiryTypes"));
		return mapping.findForward("load");
	}

	/**
	 * 接受来电弹屏处热线调查的请求
	 * 跳转到/inquiry/inquiryFilter.jsp
	 * 显示所有有效的备选调查主题
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//执行service的filter方法 当前有效的备选调查主题列表
		List<DynaBeanDTO> list = inquiryService.filter();
		request.setAttribute("list", list);
		return mapping.findForward("filter");
	}

	/**
	 * 接受/inquiry/inquiryFilter.jsp调查主题的选择请求
	 * 跳转到/inquiry/inquiryDisplayCard.jsp
	 * 显示选中调查主题的调查卡信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplayCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");//问卷号
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
		
		String id = request.getParameter("id");//问卷号
		List<DynaBeanDTO> cards = inquiryService.getCardInfoReport(id);
		request.setAttribute("cards", cards);
		
		return mapping.findForward("stat");
	}
	
	public ActionForward toReportLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");//问卷号
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
			dto.set("subid", userId);//提交人id
			
			inquiryService.updateReport(dto);
		}catch(Exception e){
			log.error("执行修改方法时异常！");
		}
		
		request.setAttribute("operSign", "ok");
		return mapping.findForward("report");
	}
	
	/**
	 * 删除报告内容
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
			dto.set("subid", userId);//提交人id
			
			inquiryService.deleteReport(dto);
		}catch(Exception e){
			log.error("执行删除方法时异常！");
		}
		
		request.setAttribute("operSign", "ok");
		return mapping.findForward("report");
	}
}
