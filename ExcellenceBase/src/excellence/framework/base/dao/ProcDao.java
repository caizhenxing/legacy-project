package excellence.framework.base.dao;

import javax.sql.DataSource;

/**
 * ִ�д洢���̷������ݼ�
 * 
 * @version 1.0
 * @author zhangfeng
 * 
 */
public interface ProcDao extends Dao {
	// public Object execute(String strProc,Object o) ;
	/**
	 * ִ�д洢���̷���list
	 * 
	 */
	DataSource execute();
}
