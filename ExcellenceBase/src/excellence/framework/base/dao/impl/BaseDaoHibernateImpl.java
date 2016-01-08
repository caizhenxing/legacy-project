/**
 * 沈阳卓越科技有限公司 版权所有
 * 2008-8-9 testJsf
 */
package excellence.framework.base.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sun.rowset.CachedRowSetImpl;

import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;

/**
 * @author zhangfeng
 * 
 */
public class BaseDaoHibernateImpl extends HibernateDaoSupport implements
		BaseDAO {

	public BaseDaoHibernateImpl() {

	}

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BaseDaoHibernateImpl.class);

	private MyQuery myQuery = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see excellence.framework.base.dao.BaseDAO#getRowSetByJDBCsql(java.lang.String)
	 */
	public RowSet getRowSetByJDBCsql(String sql) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		Connection conn = session.connection();
		Statement stmt = null;
		/*
		 * 填充CachedRowSet的两种方式： 1. 调用execute() 2. 调用populate(ResultSet)
		 */
		CachedRowSet cachedRS = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			cachedRS = new CachedRowSetImpl();
			rs = stmt.executeQuery(sql);
			cachedRS.populate(rs);
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cachedRS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see excellence.framework.base.dao.BaseDAO#loadEntity(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public Object loadEntity(Class c, Serializable s) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(c, s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see excellence.framework.base.dao.BaseDAO#removeEntity(java.lang.Object)
	 */
	public void removeEntity(Object o) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see excellence.framework.base.dao.BaseDAO#removeEntity(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public void removeEntity(Class c, Serializable s) {
		// TODO Auto-generated method stub
		Object o = getHibernateTemplate().get(c, s);
		getHibernateTemplate().delete(o);
	}

	public void removeAllEntity(Collection o) {
		// TODO Auto-generated method stub
		getHibernateTemplate().deleteAll(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see excellence.framework.base.dao.BaseDAO#saveEntity(java.lang.Object)
	 */
	public void saveEntity(Object o) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(o);
	}

	public void saveAllEntity(Collection o) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see excellence.framework.base.dao.BaseDAO#updateEntity(java.lang.Object)
	 */
	public void updateEntity(Object o) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(o);
	}

	public int findEntitySize(MyQuery mq) {
		// TODO Auto-generated method stub
		if (mq.getDetachedCriteria() != null)
			return findEntityCriteriaSize(mq.getDetachedCriteria());
		if (mq.getHql() != null)
			return findEntityHqlSize(mq.getHql());
		if (mq.getSql() != null)
			return findEntitySqlSize(mq.getSql());
		return -1;
	}

	private int findEntityCriteriaSize(final DetachedCriteria dc) {
		return ((Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = dc.getExecutableCriteria(session);   
		                return criteria.setProjection(Projections.rowCount()).uniqueResult(); 
					}
				}, true)).intValue();
	}

	private int findEntityHqlSize(final String hql) {
		return ((Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						String token = "from";
//						int first = hql.indexOf(token);
//						String subHql = hql.substring(first);
						String[] subHql = hql.split(token);
						String[] s = subHql[1].split("order");
						StringBuffer sb = new StringBuffer();
						sb.append("select count(*) from ");
						sb.append(s[0]);
						Query query = session.createQuery(sb.toString());
						Integer i = ((Number) query.uniqueResult()).intValue();
						return i.intValue();
					}
				}, true)).intValue();
	}

	private int findEntitySqlSize(final String sql) {
		return ((Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						String token = "from";
						int first = sql.indexOf(token);
						String subSql = sql.substring(first);
						StringBuffer sb = new StringBuffer();
						sb.append("select count(");
						sb.append("*)");
						sb.append(subSql);

						Connection conn = session.connection();
						try {
							Statement st = conn.createStatement();
							ResultSet rs = st.executeQuery(sb.toString());
							rs.next();
							int size = rs.getInt(1);

							return size;
						} catch (SQLException sqle) {
							throw new RuntimeException(sqle);
						}
					}
				}, true)).intValue();
	}

	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().contains(o);
	}

	public List findEntityList(MyQuery mq) {
		// TODO Auto-generated method stub
		this.myQuery = mq;
		if (mq.getDetachedCriteria() != null)
			return findEntityCriteriaList(mq.getDetachedCriteria(),mq.getOrder());
		if (mq.getHql() != null)
			return findEntityHqlList(mq.getHql());
		if (mq.getSql() != null)
			return findEntitySqlList(mq.getType(), mq.getSql());
		return null;
	}

	/**
	 * 返回list列表的信息
	 * 
	 * @param dc
	 * @return
	 */
	private List findEntityCriteriaList(final DetachedCriteria dc,final Order order) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = dc.getExecutableCriteria(session);
				criteria.addOrder(order);
				if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
					// 设置页数以多少开始
					criteria.setFirstResult(myQuery.getFirst());
					// 设置每页开始取多少条
					criteria.setMaxResults(myQuery.getFetch());
				}
				List result = criteria.list();
				return result;
			}
		}, true);
	}

	/**
	 * 根据hql语句返回对象列表
	 * 
	 * @param hql
	 * @return
	 */
	private List findEntityHqlList(final String hql) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createQuery(hql);
				if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
					// 设置页数以多少开始
					query.setFirstResult(myQuery.getFirst());
					// 设置每页开始取多少条
					query.setMaxResults(myQuery.getFetch());
				}
				List result = query.list();
				return result;
			}
		}, true);
	}

	/**
	 * 根据sql语句返回对象列表
	 * 
	 * @param c
	 *            需要实例化的sql语句对应的po类
	 * @param sql
	 * @return
	 */
	private List findEntitySqlList(final Class c, final String sql) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				SQLQuery query = session.createSQLQuery(sql);
//				query.addEntity(c);
				if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
					// 设置页数以多少开始
					query.setFirstResult(myQuery.getFirst());
					// 设置每页开始取多少条
					query.setMaxResults(myQuery.getFetch());
				}
				List result = query.list();
				return result;
			}
		}, true);
	}

	public Object[] findEntityObj(MyQuery mq) {
		// TODO Auto-generated method stub
		this.myQuery = mq;
		if (mq.getDetachedCriteria() != null)
			return findEntityCriteria(mq.getDetachedCriteria(),mq.getOrder());
		if (mq.getHql() != null)
			return findEntityHql(mq.getHql());
		if (mq.getSql() != null)
			return findEntitySql(mq.getType(), mq.getSql());
		return null;
	}

	private Object[] findEntityCriteria(final DetachedCriteria dc,final Order order) {
		return (Object[]) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = dc.getExecutableCriteria(session);
						criteria.addOrder(order);
						if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
							// 设置页数以多少开始
							criteria.setFirstResult(myQuery.getFirst());
							// 设置每页开始取多少条
							criteria.setMaxResults(myQuery.getFetch());
						}
						List result = criteria.list();
						return result.toArray();
					}
				}, true);
	}

	private Object[] findEntityHql(final String hql) {
		return (Object[]) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hql);
						if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
							// 设置页数以多少开始
							query.setFirstResult(myQuery.getFirst());
							// 设置每页开始取多少条
							query.setMaxResults(myQuery.getFetch());
						}
						List result = query.list();
						return result.toArray();
					}
				}, true);
	}

	private Object[] findEntitySql(final Class c, final String sql) {
		return (Object[]) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						SQLQuery query = session.createSQLQuery(sql);
//						query.addEntity(c);
						if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
							// 设置页数以多少开始
							query.setFirstResult(myQuery.getFirst());
							// 设置每页开始取多少条
							query.setMaxResults(myQuery.getFetch());
						}
						List result = query.list();
						return result.toArray();
					}
				}, true);
	}

	public void executeSql(String sql) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		Connection conn = session.connection();
		Statement stmt = null;
		/*
		 * 填充CachedRowSet的两种方式： 1. 调用execute() 2. 调用populate(ResultSet)
		 */

		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			stmt.executeUpdate(sql);
			if(stmt!=null)stmt.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ResultSet getResultSetByJDBCsql(final String sql) {
		// TODO Auto-generated method stub

		return (ResultSet) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Connection conn = session.connection();
						Statement stmt = null;
						ResultSet rs = null;
						try {
							stmt = conn.createStatement(
									ResultSet.TYPE_SCROLL_SENSITIVE,
									ResultSet.CONCUR_UPDATABLE);
							rs = stmt.executeQuery(sql);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							try {
								if(rs!=null)rs.close();
								if(stmt!=null)stmt.close();
								if(conn!=null)conn.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						return rs;
					}
				}, true);
	}

	public int execute(String sql) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		Connection conn = session.connection();
		try {
			Statement st = conn.createStatement();
			int size = st.executeUpdate(sql);
			if(st!=null)st.close();
			if(conn!=null)conn.close();
			return size;
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

	public Object[] findEntity(MyQuery mq) {
		// TODO Auto-generated method stub
		this.myQuery = mq;
		if (mq.getDetachedCriteria() != null)
			return findEntity(mq.getDetachedCriteria());
		if (mq.getHql() != null)
			return findEntity(mq.getHql(), mq);
		if (mq.getSql() != null)
			return findEntity(mq.getType(), mq.getIdentifer(), mq.getSql());
		return null;
	}
	
	private Object[] findEntity(DetachedCriteria dc) {
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		Criteria criteria = dc.getExecutableCriteria(session);

		if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
			// 设置页数以多少开始
			criteria.setFirstResult(myQuery.getFirst());

			// criteria.setFetchSize(myQuery.getFetch());
			// criteria.setMaxResults(myQuery.getFetch());
			// 设置每页开始取多少条
			criteria.setMaxResults(myQuery.getFetch());
		}

		if (myQuery.getAsc() != null)
			criteria.addOrder(Order.asc(myQuery.getAsc()));
		if (myQuery.getDesc() != null)
			criteria.addOrder(Order.desc(myQuery.getDesc()));
		List result = criteria.list();
		return result.toArray();
	}

	private Object[] findEntity(String hql, MyQuery mq) {
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);

		Query query = session.createQuery(hql);
		if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
			query.setFirstResult(myQuery.getFirst());
			query.setMaxResults(myQuery.getFetch());
		}
		List l = mq.getParameterL();
		if (l != null) {
			for (int i = 0, size = l.size(); i < size; i++) {
				if (l.get(i) != null)
					query.setParameter(i, l.get(i));
			}
		}
		Map m = mq.getParameterM();
		if (m != null) {
			Iterator i = m.keySet().iterator();
			while (i.hasNext()) {
				String p = (String) i.next();
				query.setParameter(p, m.get(p));
			}
		}
		List result = query.list();
		return result.toArray();
	}

	private Object[] findEntity(Class c, String identifier, String sql) {
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(identifier, c);
		if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
			query.setFirstResult(myQuery.getFirst());
			query.setMaxResults(myQuery.getFetch());
		}

		List result = query.list();
		return result.toArray();
	}

	public Object[] findEntity(Object collections, String hql) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		List result = session.createFilter(collections, hql).list();
		return result.toArray();
	}

	public Object[] findEntity(Object collections, MyQuery mq) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		Query query = session.createFilter(collections, mq.getHql());
		if (myQuery.getFetch() > 0 && myQuery.getFirst() >= 0) {
			query.setFirstResult(myQuery.getFirst());
			query.setMaxResults(myQuery.getFetch());
		}
		List l = mq.getParameterL();
		if (l != null) {
			for (int i = 0, size = l.size(); i < size; i++) {
				if (l.get(i) != null)
					query.setParameter(i, l.get(i));
			}
		}
		Map m = mq.getParameterM();
		if (m != null) {
			Iterator i = m.keySet().iterator();
			while (i.hasNext()) {
				String p = (String) i.next();
				query.setParameter(p, m.get(p));
			}
		}
		List result = query.list();
		return result.toArray();
	}

	public void flush() {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		session.flush();
	}

	public Session getConnSession() {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		return session;
	}

}
