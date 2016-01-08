package com.zyf.common.crud.tag;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.jsp.PageContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.zyf.container.ServiceProvider;
import com.zyf.context.BusinessContext;
import com.zyf.context.OperType;
import com.zyf.security.SecurityContextInfo;
import com.zyf.security.model.CurrentUser;
import com.zyf.security.model.RWCtrlType;

import javax.servlet.http.HttpServletResponse;

public abstract class TagSecurityDefautPolicy implements ITagSecurityPolicy {

	PageContext pageContext = null;

	VisionStatusInfo visionStatusInfo = new VisionStatusInfo();

	HibernateTemplate hibernateTemplate = (HibernateTemplate) ServiceProvider
			.getService("common.hibernateTemplate");

	int rwCtrlType = -1;

	String permissionCode = "";

	String wfPermissionCode = "";

	int field;

	int least;

	public void securityParameterPopulate(String rwCtrlType,
			String permissionCode, String wfPermissionCode) {
		CurrentUser currentUser = SecurityContextInfo.getCurrentUser();
		if (StringUtils.isNotBlank(rwCtrlType)) {
			this.rwCtrlType = Integer.parseInt(rwCtrlType);
		}
		this.permissionCode = permissionCode;
		this.wfPermissionCode = wfPermissionCode;
		if (StringUtils.isNotBlank(permissionCode)) {
			field = currentUser.getFieldRWCtrlType(SecurityContextInfo
					.getCurrentPageUrl(), permissionCode);
			least = this.rwCtrlType > field ? field : this.rwCtrlType;
		} else {
			least = this.rwCtrlType;
		}
	}

	public VisionStatusInfo compomentPermission(String rwCtrlType,
			String permissionCode, String wfPermissionCode,
			PageContext pageContext) {
		this.pageContext = pageContext;

		// 新增的时候，根据当前URL，获取pageRWCtrlType
		if (pageContext.getRequest().getParameter("oid") == null
				|| pageContext.getRequest().getParameter("oid").toString()
						.length() == 0) {
			CurrentUser currentUser = SecurityContextInfo.getCurrentUser();
			String url = SecurityContextInfo.getCurrentPageUrl();
			int pageRWCtrlType = currentUser.getPageDefaultRWCtrlType(url);
			if (2 != pageRWCtrlType) {
				// 如果默认的权限不是可编辑的话，直接跳转到无权限提示页面
				try {
					String qware = pageContext.getServletContext()
							.getInitParameter("publicResourceServer");
					String url2 = qware + "/405.jsp";
					ServletResponse srp = pageContext.getResponse();
					HttpServletResponse hsp = (HttpServletResponse) srp;
					hsp.sendRedirect(url2);
				} catch (IllegalStateException e) {
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		securityParameterPopulate(rwCtrlType, permissionCode, wfPermissionCode);
		int operType = pageType();
		// 新增页
		if (operType == OperType.ADD) {
			visionStatusInfo = addPagePermission();
		}// 查看页
		else if (operType == OperType.VIEW) {
			visionStatusInfo = inviewPagePermission(hibernateTemplate);
		}// 编辑页
		else if (operType == OperType.EDIT) {
			visionStatusInfo = inEditPagePermission(hibernateTemplate);
		}

		return visionStatusInfo;
	}

	// 得到页面状态
	public abstract int pageType();

	public VisionStatusInfo addPagePermission() {
		visionStatusInfo.setPageType(ITagSecurityPolicy.EDITPAGE);
		visionStatusInfo.setVisiableStatus(ITagSecurityPolicy.VISIBLE);
		visionStatusInfo.setEditableStatus(ITagSecurityPolicy.EDITABLE);
		return visionStatusInfo;
	}

	public boolean workFlowIsNeedData(String key) {
		return false;
	}

	public abstract VisionStatusInfo inviewPagePermission(
			HibernateTemplate hibernateTemplate);

	public abstract VisionStatusInfo inEditPagePermission(
			HibernateTemplate hibernateTemplate);
}
