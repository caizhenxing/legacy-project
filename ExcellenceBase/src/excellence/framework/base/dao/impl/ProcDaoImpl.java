package excellence.framework.base.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import excellence.framework.base.dao.ProcDao;

public class ProcDaoImpl extends HibernateDaoSupport implements ProcDao {

	public ProcDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 执行存储过程返回数据源
	 */
	public DataSource execute() {
		// TODO Auto-generated method stub
		DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
		return ds;
	}

	// public Object execute(String strProc, Object o) {
	// Session s = null;
	// CallableStatement cs = null;
	// try {
	// // fetch connection
	// DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
	// s = SessionFactoryUtils.getSession(getSessionFactory(), false);
	// Connection c = s.connection();
	// // invoke procedure
	// cs = c.prepareCall(strProc);
	// // set argument to procedure
	// setArg(cs, o);
	// // fetch result
	// Object result = getResult(cs);
	// return result;
	// } catch (SQLException e) {
	// throw new DataAccessException("procedure error!--" + e.getMessage()) {
	// };
	// }
	//
	// }

	// public List execute(SpringStoredProcedure ssp) {
	// // TODO Auto-generated method stub
	// DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
	// ssp = new SpringStoredProcedure(ds);
	// return null;
	// }

}
