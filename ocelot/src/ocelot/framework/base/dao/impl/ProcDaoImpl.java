package ocelot.framework.base.dao.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import ocelot.framework.base.dao.ProcDao;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public abstract class ProcDaoImpl extends HibernateDaoSupport implements ProcDao {

	public ProcDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Object execute(String strProc,Object o) {
		Session s=null;
		CallableStatement cs = null;
		try {
			//fetch connection
			s=SessionFactoryUtils.getSession(getSessionFactory(),false);
			Connection c=s.connection();
			//invoke procedure
			cs = c.prepareCall(strProc);
			//set argument to procedure
			setArg( cs, o);
			//fetch result
			Object result =getResult(cs);
			return result;
		} catch (SQLException e) {
			throw new DataAccessException("procedure error!--"+e.getMessage()){};
		}
		
		
	}
	abstract protected void setArg(CallableStatement cs,Object o);
	abstract protected Object getResult(CallableStatement cs);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
