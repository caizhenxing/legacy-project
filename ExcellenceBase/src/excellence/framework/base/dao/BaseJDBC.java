/**
 * 沈阳卓越科技有限公司
 */
package excellence.framework.base.dao;

import java.util.List;

/**
 * @author zhangfeng
 * 操作与执行jdbc程序
 *
 */
public interface BaseJDBC extends Dao{
	
	/**
	 * 执行不带参数的sql语句
	 * create table
	 * delete table等
	 * @param sql
	 */
	void execute(String sql);
	
	/**
	 * 执行添加或者更新操作
	 * @param sql 要执行的sql语句
	 * @param obj 所带的参数，即要更新的列名，为一个对象数组的集合
	 * 例 new Object[]{name,age}
	 */
	void executeUpdate(String sql,Object[] obj);
	
	/**
	 * 执行sql语句返回int类型的值
	 * @param sql 执行如seelct count(*) from 表
	 * @return 返回值为int类型
	 */
	int queryForInt(String sql);
	
	/**
	 * 查询根据sql语句并且返回指定类型
	 * @param sql 要查询的sql语句，参数设置成?号
	 * @param obj 参数列表 new Object[]{name,age}
	 * @param typeClass 指定返回的类型，如果是返回String，则为java.lang.String.class
	 * @return 指定类型的值
	 */
	Object queryForObject(String sql,Object[] obj,Class typeClass);
	
	/**
	 * 根据sql语句查询并且返回list
	 * @param sql 传入的sql语句
	 * @return list列表的值
	 */
	List queryForList(String sql);

}
