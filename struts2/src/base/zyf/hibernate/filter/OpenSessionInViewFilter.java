
package base.zyf.hibernate.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import base.zyf.spring.SpringRunningContainer;




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
	 * 取session，需要重写
	 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#lookupSessionFactory()
	 */
	protected SessionFactory lookupSessionFactory() {
		if (logger.isDebugEnabled()) {
			logger.debug("Using SessionFactory '" + getSessionFactoryBeanName() + "' for OpenSessionInViewFilter");
		}
		return (SessionFactory) SpringRunningContainer.getInstance().getBean(getSessionFactoryBeanName());
	}

	/**
	 * 因为默认的FlushMode引起readonly问题，所以重写
	 * (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#closeSession(org.hibernate.Session, org.hibernate.SessionFactory)
	 */
	@Override
	protected void closeSession(Session session, SessionFactory sessionFactory) {
		session.flush();
		super.closeSession(session, sessionFactory);
	}

	/**
	 * 因为默认的FlushMode引起readonly问题，所以重写
	 * 重新设置了FlushMode为auto
	 *  (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#getSession(org.hibernate.SessionFactory)
	 */
	@Override
	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);   
		session.setFlushMode(FlushMode.AUTO);     
        return session;
	}
	
}
