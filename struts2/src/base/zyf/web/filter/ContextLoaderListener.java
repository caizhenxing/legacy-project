/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 15, 20099:49:41 AM
 * ������base.zyf.web.filter
 * �ļ�����ContextLoaderListener.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.web.filter;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import base.zyf.spring.SpringRunningContainer;

/**
 * ��д��contextInitialized��������Ҫ�����Ǽ���SpringRunningContainer
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
