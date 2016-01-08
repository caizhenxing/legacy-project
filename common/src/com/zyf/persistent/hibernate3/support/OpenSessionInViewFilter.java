
package com.zyf.persistent.hibernate3.support;

import org.hibernate.SessionFactory;

import com.zyf.container.ServiceProvider;



/**
 * ���� {@link org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#lookupSessionFactory()} ����,
 * �� {@link com.zyf.container.ServiceProvider} �л�ȡ SessionFactory
 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter  
 * @since 2006-6-26
 * @author ��һ��
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
