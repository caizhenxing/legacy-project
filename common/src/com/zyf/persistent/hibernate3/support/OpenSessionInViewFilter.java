
package com.zyf.persistent.hibernate3.support;

import org.hibernate.SessionFactory;

import com.zyf.container.ServiceProvider;



/**
 * 覆盖 {@link org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#lookupSessionFactory()} 方法,
 * 从 {@link com.zyf.container.ServiceProvider} 中获取 SessionFactory
 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter  
 * @since 2006-6-26
 * @author 赵一非
 * @version 1.0
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {
	
	/**
	 * 
	 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#lookupSessionFactory()
	 */
	protected SessionFactory lookupSessionFactory() {
		if (logger.isDebugEnabled()) {
			logger.debug("Using SessionFactory '" + getSessionFactoryBeanName() + "' for OpenSessionInViewFilter");
		}
		return (SessionFactory) ServiceProvider.getService(getSessionFactoryBeanName());
	}
	
}
