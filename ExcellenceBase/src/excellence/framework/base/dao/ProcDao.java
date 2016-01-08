package excellence.framework.base.dao;

import javax.sql.DataSource;

/**
 * 执行存储过程返回数据集
 * 
 * @version 1.0
 * @author zhangfeng
 * 
 */
public interface ProcDao extends Dao {
	// public Object execute(String strProc,Object o) ;
	/**
	 * 执行存储过程返回list
	 * 
	 */
	DataSource execute();
}
