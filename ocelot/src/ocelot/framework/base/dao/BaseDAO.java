package ocelot.framework.base.dao;


import java.io.Serializable;

import ocelot.framework.base.query.MyQuery;






 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface BaseDAO extends Dao {
	
	/**
	 * load only one pojo 
	 * @param c Class of pojo
	 * @param s primary key of pojo
	 * @return pojo if exist in db or return null;
	 */
	public Object loadEntity(Class c,Serializable s);
	/**
	 * find 
	 */
	public Object[] findEntity(MyQuery mq);
	
	public Object[] findEntity(Object collections,String hql);
	
	public Object[] findEntity(Object collections,MyQuery mq);
	
	public int findEntitySize(MyQuery mq);
	//public MyQuery findEntity(QbcMyQuery qq);
	//public MyQuery findEntity(SqlMyQuery sq);
	
	public void removeEntity(Object o);
	public void saveEntity(Object o);
	public void updateEntity(Object o);
	
	public boolean removeEntity(Class c,Serializable s);
	
	//public Object[] findEntity(String hql,int begin,int fetch);
	public int execute(String sql);
	
	public void flush();
}
