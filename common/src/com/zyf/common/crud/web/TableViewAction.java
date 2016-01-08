/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-9-3下午04:39:26
 * 文件名：TableViewAction.java
 * 制作者：zhaoyifei
 * 
 */
package com.zyf.common.crud.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zyf.common.crud.service.CommonTableViewService;
import com.zyf.container.ServiceProvider;
import com.zyf.core.ContextInfo;
import com.zyf.framework.codename.UserCodeName;
import com.zyf.web.BaseDispatchAction;

/**
 * @author zhaoyifei
 *
 */
public class TableViewAction extends BaseDispatchAction {

	public ActionForward info(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
			throws Exception {
			String pageId=request.getParameter("pageId");
			UserCodeName ucn=ContextInfo.getContextUser();
			getService().getRows(pageId);
			getService().getRows(ucn.getUsername(), pageId);
			return mapping.findForward("info");
		}
	private CommonTableViewService getService() {
		return (CommonTableViewService)ServiceProvider.getService(CommonTableViewService.SERVICE_NAME);
	}
}
