/* 包    名：et.bo.inquiry.detail.action
 * 文 件 名：DetailAction.java
 * 注释时间：2008-8-28 14:03:56
 * 版权所有：沈阳市卓越科技有限公司。
 */

package et.bo.inquiry.detail.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.inquiry.detail.service.DetailService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class DetailAction.
 * 
 * @author NieYuan
 */
public class DetailAction extends BaseAction {
	static Logger log = Logger.getLogger(DetailAction.class);
	
	private DetailService service;

	/**
	 * Gets the service.
	 * 
	 * @return the detail service
	 */
	public DetailService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * 
	 * @param service the new service
	 */
	public void setService(DetailService service) {
		this.service = service;
	}
	
	/**
	 * To query.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List user=service.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001'");
		request.setAttribute("user", user);
		
		
		return mapping.findForward("query");
	}
	
	/**
	 * To list.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String qid =  request.getParameter("qid");//因为qid是url传参，所以用request取
		dto.set("qid", qid);
		
		// 取得分页信息 如果没有取到就初始化分页信息
		String pageState = request.getParameter("pagestate");
		PageInfo pageInfo = null;
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession().getAttribute("inquiryDetail");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(20);
		//调用service的查询方法 返回查询到的热线调查主题列表
		List<DynaBeanDTO> list = service.query(dto, pageInfo);
		int size = service.getSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", mapping, request);
		request.getSession().setAttribute("inquiryDetail", pt);
		
		return mapping.findForward("list");
	}
}
