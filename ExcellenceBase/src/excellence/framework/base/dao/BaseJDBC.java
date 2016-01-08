/**
 * ����׿Խ�Ƽ����޹�˾
 */
package excellence.framework.base.dao;

import java.util.List;

/**
 * @author zhangfeng
 * ������ִ��jdbc����
 *
 */
public interface BaseJDBC extends Dao{
	
	/**
	 * ִ�в���������sql���
	 * create table
	 * delete table��
	 * @param sql
	 */
	void execute(String sql);
	
	/**
	 * ִ����ӻ��߸��²���
	 * @param sql Ҫִ�е�sql���
	 * @param obj �����Ĳ�������Ҫ���µ�������Ϊһ����������ļ���
	 * �� new Object[]{name,age}
	 */
	void executeUpdate(String sql,Object[] obj);
	
	/**
	 * ִ��sql��䷵��int���͵�ֵ
	 * @param sql ִ����seelct count(*) from ��
	 * @return ����ֵΪint����
	 */
	int queryForInt(String sql);
	
	/**
	 * ��ѯ����sql��䲢�ҷ���ָ������
	 * @param sql Ҫ��ѯ��sql��䣬�������ó�?��
	 * @param obj �����б� new Object[]{name,age}
	 * @param typeClass ָ�����ص����ͣ�����Ƿ���String����Ϊjava.lang.String.class
	 * @return ָ�����͵�ֵ
	 */
	Object queryForObject(String sql,Object[] obj,Class typeClass);
	
	/**
	 * ����sql����ѯ���ҷ���list
	 * @param sql �����sql���
	 * @return list�б��ֵ
	 */
	List queryForList(String sql);

}
