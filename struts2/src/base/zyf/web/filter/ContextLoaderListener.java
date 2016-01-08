/**
 * 
 * 项目名称：struts2
 * 制作时间：May 15, 20099:49:41 AM
 * 包名：base.zyf.web.filter
 * 文件名：ContextLoaderListener.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.web.filter;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import base.zyf.spring.SpringRunningContainer;

/**
 * 重写了contextInitialized方法，主要作用是加载SpringRunningContainer
 * @author zhaoyifei
 * @version 1.0
 */
public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		super.contextInitialized(event);
		WebApplicationContext ctx = WebApplicationContextUtils
		.getRequiredWebApplicationContext(event.getServletContext());
		SpringRunningContainer.loadContext(ctx);
	}

}
