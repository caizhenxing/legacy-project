
package base.zyf.hibernate.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import base.zyf.spring.SpringRunningContainer;




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
	 * ȡsession����Ҫ��д
	 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#lookupSessionFactory()
	 */
	protected SessionFactory lookupSessionFactory() {
		if (logger.isDebugEnabled()) {
			logger.debug("Using SessionFactory '" + getSessionFactoryBeanName() + "' for OpenSessionInViewFilter");
		}
		return (SessionFactory) SpringRunningContainer.getInstance().getBean(getSessionFactoryBeanName());
	}

	/**
	 * ��ΪĬ�ϵ�FlushMode����readonly���⣬������д
	 * (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter#closeSession(org.hibernate.Session, org.hibernate.SessionFactory)
	 */
	@Override
	protected void closeSession(Session session, SessionFactory sessionFactory) {
		session.flush();
		super.closeSession(session, sessionFactory);
	}

	/**
	 * ��ΪĬ�ϵ�FlushMode����readonly���⣬������д
	 * ����������FlushModeΪauto
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
