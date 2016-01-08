package excellence.framework.base.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultReader;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
/**
 * ����springִ��oracle�Ĵ洢����
 * @author zhangfeng
 *
 */
public class SpringStoredProcedure extends StoredProcedure {
	public List<List> listSet = new ArrayList<List>();

	// public ArrayList<HashMap> set = new ArrayList<HashMap>();
	private Map inParam;

	private RowMapper rm = new RowMapper() {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return null;// ���ôӴ洢���̱����ȡ���
		}
	};

	private RowMapperResultReader callback = new RowMapperResultReader(rm) {
		public void processRow(ResultSet rs) throws SQLException {
			List<Map> set = new ArrayList<Map>();
			int count = rs.getMetaData().getColumnCount();
			String[] header = new String[count];
			for (int i = 0; i < count; i++)
				header[i] = rs.getMetaData().getColumnName(i + 1);
			do {
				HashMap row = new HashMap(count + 7);
				for (int i = 0; i < count; i++)
					row.put(header[i], rs.getString(i + 1));
				set.add(row);
			} while (rs.next());
			listSet.add(set);
		}
	}; // RowMapperResultReader��Ϊ��������Ļص����

	public SpringStoredProcedure(DataSource ds, String SQL) {
		setDataSource(ds);
		setSql(SQL);
	}

	public void setOutParameter(String column, int type) {
		declareParameter(new SqlOutParameter(column, type, callback));// ���ûص����ע���������
	}

	public void setParameter(String column, int type) {
		declareParameter(new SqlParameter(column, type));
	}

	public void SetInParam(Map inParam) {
		this.inParam = inParam;
	}

	public Map execute() {
		compile();
		return execute(this.inParam);
	}
}
