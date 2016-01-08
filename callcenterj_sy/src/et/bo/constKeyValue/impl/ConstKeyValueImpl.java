/*
 * @(#)AddressMainService.java	 2009-03-13
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.constKeyValue.impl;

import java.sql.SQLException;

import javax.sql.RowSet;

import net.sf.json.JSONArray;

import et.bo.constKeyValue.service.ConstKeyValueService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * <p>ʡ���ص�ַ��Ϣά��</p>
 * 
 * @version 2009-03-13
 * @author wangwenquan
 */
public class ConstKeyValueImpl implements ConstKeyValueService{
	private BaseDAO dao = null;
	/**
	 * ���ӹ���
	 * @param type
	 * @param key
	 * @param value
	 */
	public void addConstKeyValue(String type,String key, String value)
	{
		String sql = "insert into const_keyValue(type,constKey,constValue)values('"+type+"','"+key+"','"+value+"')";
		dao.execute(sql);
	}
	/**
	 * ���ӻ��޸Ĺ���
	 * @param type
	 * @param key
	 * @param value
	 * @return  int 0�ɹ� 1ʧ��
	 */
	public int addOrUpdateConstKeyValue(String type,String key, String value)
	{
		boolean isExist=false;
		int rV = 0;
		String auSql = "insert into const_keyValue(type,constKey,constValue)values('"+type+"','"+key+"','"+value+"')";
		String sql = "select * from const_keyValue where type='"+type+"' and constKey = '"+key+"'";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		try {
			rs.beforeFirst();
			while (rs.next()) {
				isExist=true;
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			rV = 1;
		}
		if(isExist)
		{
			auSql =  "update const_keyValue set constValue = '"+value+"' where type = '"+type+"' and constKey='"+key+"'";
		}
		dao.execute(auSql);

		return rV;
	}
	/**
	 * �޸Ĺ���
	 * @param id
	 * @param value
	 */
	public void updateConstKeyValue(String id, String value)
	{
		String sql = "update const_keyValue set constValue = '"+value+"' where id = '"+id+"'";
		dao.execute(sql);
	}
	/**
	 * ��ѯ����
	 * @param type
	 * @param key
	 * @return String
	 */
	public String getConstValueByTypeKey(String type,String key)
	{
		String sql = "select * from const_keyValue where type='"+type+"' and constKey = '"+key+"'";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		try {
			rs.beforeFirst();
			while (rs.next()) {
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("constValue", rs.getString("constValue"));
				dbd.set("id", rs.getInt("id"));
				dbd.set("constKey", rs.getString("constKey"));
				dbd.set("type", rs.getString("type"));
				JSONArray jsonArray = JSONArray.fromObject(dbd);
				return jsonArray.toString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

}
