/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺2007-9-3����04:39:26
 * �ļ�����TableViewAction.java
 * �����ߣ�zhaoyifei
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
