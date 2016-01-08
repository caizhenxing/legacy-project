/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-8-18下午02:57:13
 * 文件名：CurdAction.java
 * 制作者：Administrator
 * 
 */
package com.zyf.common.crud.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zyf.common.crud.service.CommonService;
import com.zyf.common.crud.service.CommonTableViewService;
import com.zyf.container.ServiceProvider;
import com.zyf.core.ContextInfo;
import com.zyf.framework.codename.UserCodeName;
import com.zyf.tools.Tools;
import com.zyf.web.BaseDispatchAction;
import com.zyf.web.MessageUtils;

/**
 * @author zhaoyifei
 *
 */
public class CurdAction extends BaseDispatchAction {

	public static final String FORWARD_ENTRY = "entry";
	public static final String FORWARD_LIST = "list";
	public static final String FORWARD_INFO = "info";
	
	
	public ActionForward entry(
		ActionMapping mapping, 
		ActionForm form, 
		HttpServletRequest request, 
		HttpServletResponse response) 
		throws Exception {
		
		return mapping.findForward(FORWARD_ENTRY);
	}

	public ActionForward setTable(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
			throws Exception {
			CurdForm sf=(CurdForm)form;
			UserCodeName ucn=ContextInfo.getContextUser();
			String asc=request.getParameter("asc");
			sf.setAll(service().getRows(sf.getPageId()));
			if("asc".equals(asc))
				sf.setSelect(service().getRows(ucn.getUsername(), sf.getPageId(),true));
			else
			sf.setSelect(service().getRows(ucn.getUsername(), sf.getPageId()));
			if(service().isSetIsNull())
				sf.setSelect(new ArrayList());
			if("asc".equals(asc))
				sf.setAsc(true);
			return mapping.findForward("setView");
		}
	public ActionForward saveTable(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
			throws Exception {
			CurdForm sf=(CurdForm)form;
			
			UserCodeName ucn=ContextInfo.getContextUser();
			if(!sf.isAsc())
			service().saveRows(sf.getSaveList(), ucn.getUsername(), sf.getPageId());
			else
				service().saveRows(sf.getSaveList(), ucn.getUsername(), sf.getPageId(),true);
			return mapping.findForward("success");
		}
	private static CommonService getService() {
		return (CommonService)ServiceProvider.getService(CommonService.SERVICE_NAME);
	}
	private static  CommonTableViewService service() {
		return (CommonTableViewService)ServiceProvider.getService(CommonTableViewService.SERVICE_NAME);
	}
}
