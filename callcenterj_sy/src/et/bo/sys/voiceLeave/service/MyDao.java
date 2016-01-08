package et.bo.sys.voiceLeave.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dao.impl.BaseDAOImpl;
import excellence.framework.base.query.MyQuery;

public class MyDao extends BaseDAOImpl implements BaseDAO {
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
		Long i = (Long) query.uniqueResult();
		return (int)i.longValue();
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
//		System.out.println(sb);
	}
}
