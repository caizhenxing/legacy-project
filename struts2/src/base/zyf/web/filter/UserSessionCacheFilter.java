/**
 * 
 * 项目名称：struts2
 * 制作时间：May 22, 200910:56:43 AM
 * 包名：base.zyf.web.filter
 * 文件名：UserSessionCacheFilter.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import base.zyf.web.condition.ContextInfo;
import base.zyf.web.user.UserBean;

/**
 * 主要作用是session和threadlocal用户信息互换
 * @author zhaoyifei
 * @version 1.0
 */
public class UserSessionCacheFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httprequest = (HttpServletRequest) request;
		UserBean ub = (UserBean) httprequest.getSession().getAttribute(
				UserBean.SESSION_USERBEAN_NAME);
		if (ub != null) {
			ContextInfo.setContextUser(ub);
		}
		try {
			chain.doFilter(request, response);
		} finally {
			// 在这里清除
			if (ub == null) {
				httprequest.getSession().setAttribute(
						UserBean.SESSION_USERBEAN_NAME,
						ContextInfo.getContextUser());
			}
			ContextInfo.clear();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
