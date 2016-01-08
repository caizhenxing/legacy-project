package excellence.framework.base.dao;

import java.io.Serializable;

import javax.sql.RowSet;

import org.hibernate.Session;

import excellence.framework.base.query.MyQuery;

/**
 * @author zhaoyifei 基本数据操作
 * zhang feng add jdbc operator
 * @version 2007-1-15
 * @see
 */
public interface BaseDAO extends Dao {

	/**
	 * load only one pojo
	 * 
	 * 根据po得到该条数据基本信息
	 * 
	 * @param c
	 *            Class of pojo
	 * @param s
	 *            primary key of pojo
	 * @return pojo if exist in db or return null;
	 */
	public Object loadEntity(Class c, Serializable s);

	/**
	 * find 根据查询条件返回对象数组
	 * 
	 * @param mq
	 *            MyQuery 查询条件
	 */
	public Object[] findEntity(MyQuery mq);

	/**
	 * 
	 * @param collections
	 * @param hql
	 * @return
	 */
	public Object[] findEntity(Object collections, String hql);

	/**
	 * 
	 * @param collections
	 * @param mq
	 * @return
	 */
	public Object[] findEntity(Object collections, MyQuery mq);

	/**
	 * 返回查询结果的条数
	 * 
	 * @param mq
	 *            查询条件
	 * @return int
	 */
	public int findEntitySize(MyQuery mq);

	// public MyQuery findEntity(QbcMyQuery qq);
	// public MyQuery findEntity(SqlMyQuery sq);

	/**
	 * 根据传入的对象删除数据库的某条记录
	 * 
	 * @param Object
	 *            o 传入的值对象
	 */
	public void removeEntity(Object o);

	/**
	 * 保存传入的值对象的信息,更新数据库
	 * 
	 * @param o
	 *            传入的值对象
	 */
	public void saveEntity(Object o);

	/**
	 * 修改传入的值对象,更新数据库的信息
	 * 
	 * @param o
	 *            传入的值对象
	 */
	public void updateEntity(Object o);

	/**
	 * 
	 * @param c
	 * @param s
	 * @return
	 */
	public void removeEntity(Class c, Serializable s);

	// public Object[] findEntity(String hql,int begin,int fetch);
	/**
	 * 执行sql语句
	 */
	public int execute(String sql);

	/**
	 * 用于刷新缓存
	 * 
	 */
	public void flush();

	/**
	 * 得到数据连接，得到hibernate的session 张锋添加
	 * 
	 * @return
	 */
	Session getConnSession();
	
	/**
	 * 返回RowSet对象的信息
	 * DataSet，可以将数据源中的数据读取到内存中，进行离线操作，然后再同步到数据源
	 * @param sql 要执行的查询语句
	 * @return RowSet 
	 */
	RowSet getRowSetByJDBCsql(String sql);
}
