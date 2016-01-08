/**
 * 
 */
package excellence.framework.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import excellence.framework.base.dao.BaseJDBC;

/**
 * @author zhangfeng
 *
 */
public class BaseJdbcImpl extends JdbcTemplate implements BaseJDBC {

	private static final long serialVersionUID = 1L;
	
	private DataSource dataSource;
	
	
	
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BaseJdbcImpl.class);

	public void execute(String sql) {
		getJdbcTemplate().execute(sql);
	}

	public void executeUpdate(String sql, Object[] obj) {
		getJdbcTemplate().update(sql, obj);
	}

	public int queryForInt(String sql) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().queryForInt(sql);
	}

	public Object queryForObject(String sql, Object[] obj, Class typeClass) {
		return getJdbcTemplate().queryForObject(sql, obj, typeClass);
	}

	public List queryForList(String sql) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().queryForList(sql);
	}
	
	/** 
	* ��ͨ��ҳ��ѯ<br> 
	* <b>��������ϱȽϴ�Ӧ�õ���setFetchsize() ��setMaxRow��������������һ�£�������ڴ����</b> 
	* @see #setFetchSize(int) 
	* @see #setMaxRows(int) 
	* @param sql 
	* ��ѯ��sql��� 
	* @param startRow 
	* ��ʼ�� 
	* @param rowsCount 
	* ��ȡ������ 
	* @return 
	* @throws DataAccessException 
	*/ 
	@SuppressWarnings("unchecked") 
	public List<Map> querySP(String sql, int startRow, int rowsCount) 
	throws DataAccessException 
	{ 
	return querySP(sql, startRow, rowsCount, getColumnMapRowMapper()); 
	} 
	/** 
	* �Զ����а�װ����ѯ<br> 
	* <b>��������ϱȽϴ�Ӧ�õ���setFetchsize() ��setMaxRow��������������һ�£�������ڴ����</b> 
	* @see #setFetchSize(int) 
	* @see #setMaxRows(int) 
	* @param sql 
	* ��ѯ��sql��� 
	* @param startRow 
	* ��ʼ�� 
	* @param rowsCount 
	* ��ȡ������ 
	* @param rowMapper 
	* �а�װ�� 
	* @return 
	* @throws DataAccessException 
	*/ 
	@SuppressWarnings("unchecked") 
	public List<Map> querySP(String sql, int startRow, int rowsCount, RowMapper rowMapper) 
	throws DataAccessException 
	{ 
	return (List) query(sql, new SplitPageResultSetExtractor(rowMapper, startRow, 
	rowsCount)); 
	} 
	public DataSource getDataSource() 
	{ 
	return dataSource; 
	} 
	public void setDataSource(DataSource dataSource) 
	{ 
	this.dataSource = dataSource; 
	super.setDataSource(dataSource); 
	} 



}
