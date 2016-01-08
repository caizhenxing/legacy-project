package excellence.framework.base.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sun.rowset.CachedRowSetImpl;

import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;

public class BaseDAOImpl extends HibernateDaoSupport implements BaseDAO {

	MyQuery myQuery = null;

	private static Log log = LogFactory.getLog(BaseDAOImpl.class);

	/**
	 * 删除数据信息
	 */
	public void removeEntity(Object o) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		session.delete(o);
	}

	/**
	 * 保存数据信息
	 */
	public void saveEntity(Object o) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		session.saveOrUpdate(o);
	}

	/**
	 * 更新数据信息
	 */
	public void updateEntity(Object o) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		session.update(o);
	}

	/**
	 * 返回数据信息的条数,根据传入的查询条件
	 */
	public int findEntitySize(MyQuery mq) {
		// TODO Auto-generated method stub
		if (mq.getDetachedCriteria() != null)
			return findEntitySize(mq.getDetachedCriteria());
		if (mq.getHql() != null)
			return findEntitySize(mq.getHql());
		if (mq.getSql() != null)
			return findEntitySize(mq.getClass(), mq.getIdentifer(), mq.getSql());
		return -1;
	}

	/**
	 * 根据传入的条件得到对象数组
	 */
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

	/*
	 * public Object[] findEntity(String hql,int begin,int fetch) { return
	 * findEntityByHql(hql,begin,fetch); }
	 */

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

	/*
	 * private Object[] findEntityByHql(String hql,int begin,int fetch) {
	 * Session session=null;
	 * session=SessionFactoryUtils.getSession(getSessionFactory(),false); Query
	 * query=session.createQuery(hql); if(fetch>0&&begin>=0) {
	 * query.setFirstResult(begin); query.setMaxResults(fetch); }
	 * 
	 * List result=query.list(); return result.toArray(); }
	 */
	private int findEntitySize(DetachedCriteria dc) {
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		ProjectionList p = Projections.projectionList();
		p.add(Projections.rowCount());
		Criteria criteria = dc.getExecutableCriteria(session);
		criteria.setProjection(p);
		Integer i = (Integer) criteria.uniqueResult();
		return i.intValue();
	}

	private int findEntitySize(String hql) {
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		String token = "from";
		int first = hql.indexOf(token);
		String subHql = hql.substring(first);
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*)");
		sb.append(subHql);
		Query query = session.createQuery(sb.toString());
		Integer i = 0;
		try {
			i = (Integer) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			Long j = (Long) query.uniqueResult();
			return j.intValue();
		}
		return i.intValue();
	}

	private int findEntitySize(Class c, String identifier, String sql) {
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);

		String token = "from";
		int first = sql.indexOf(token);
		String subSql = sql.substring(first);
		StringBuffer sb = new StringBuffer();
		sb.append("select count(");
		// sb.append(identifier);
		sb.append("*)");
		sb.append(subSql);
		// SQLQuery query =session.createSQLQuery(sb.toString());

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
		// Integer size=(Integer)query.uniqueResult();

	}

	/**
	 * 根据数据对象更新信息
	 */
	public Object loadEntity(Class c, Serializable s) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		return session.get(c, s);
	}

	public static void main(String[] arg0) {
		String sql = "select aafdf dsfds fdsafd from dsfdsfsdafdfsdfsd";
		String token = "from";
		String identifier = "cccc";
		int first = sql.indexOf(token);
		String subSql = sql.substring(first);
		StringBuffer sb = new StringBuffer();
		sb.append("select count(");
		sb.append(identifier);
		sb.append(".*)");
		sb.append(subSql);
		System.out.println(sb);
	}

	/**
	 * 执行sql语句
	 */
	public int execute(String sql) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		Connection conn = session.connection();
		try {
			Statement st = conn.createStatement();
			int size = st.executeUpdate(sql);
			return size;
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

	}

	/**
	 * 刷新缓存
	 */
	public void flush() {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		session.flush();
	}

	public Object[] findEntity(Object collections, String hql) {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		List result = session.createFilter(collections, hql).list();
		return result.toArray();
	}

	public void removeEntity(Class c, Serializable s) {
		// TODO Auto-generated method stub
		Object o = loadEntity(c, s);
		removeEntity(o);
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

	public Session getConnSession() {
		// TODO Auto-generated method stub
		Session session = null;
		session = SessionFactoryUtils.getSession(getSessionFactory(), false);
		return session;
	}

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
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cachedRS;
	}
}
