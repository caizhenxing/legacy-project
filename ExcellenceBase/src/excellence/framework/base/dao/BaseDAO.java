package excellence.framework.base.dao;

import java.io.Serializable;

import javax.sql.RowSet;

import org.hibernate.Session;

import excellence.framework.base.query.MyQuery;

/**
 * @author zhaoyifei �������ݲ���
 * zhang feng add jdbc operator
 * @version 2007-1-15
 * @see
 */
public interface BaseDAO extends Dao {

	/**
	 * load only one pojo
	 * 
	 * ����po�õ��������ݻ�����Ϣ
	 * 
	 * @param c
	 *            Class of pojo
	 * @param s
	 *            primary key of pojo
	 * @return pojo if exist in db or return null;
	 */
	public Object loadEntity(Class c, Serializable s);

	/**
	 * find ���ݲ�ѯ�������ض�������
	 * 
	 * @param mq
	 *            MyQuery ��ѯ����
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
	 * ���ز�ѯ���������
	 * 
	 * @param mq
	 *            ��ѯ����
	 * @return int
	 */
	public int findEntitySize(MyQuery mq);

	// public MyQuery findEntity(QbcMyQuery qq);
	// public MyQuery findEntity(SqlMyQuery sq);

	/**
	 * ���ݴ���Ķ���ɾ�����ݿ��ĳ����¼
	 * 
	 * @param Object
	 *            o �����ֵ����
	 */
	public void removeEntity(Object o);

	/**
	 * ���洫���ֵ�������Ϣ,�������ݿ�
	 * 
	 * @param o
	 *            �����ֵ����
	 */
	public void saveEntity(Object o);

	/**
	 * �޸Ĵ����ֵ����,�������ݿ����Ϣ
	 * 
	 * @param o
	 *            �����ֵ����
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
	 * ִ��sql���
	 */
	public int execute(String sql);

	/**
	 * ����ˢ�»���
	 * 
	 */
	public void flush();

	/**
	 * �õ��������ӣ��õ�hibernate��session �ŷ����
	 * 
	 * @return
	 */
	Session getConnSession();
	
	/**
	 * ����RowSet�������Ϣ
	 * DataSet�����Խ�����Դ�е����ݶ�ȡ���ڴ��У��������߲�����Ȼ����ͬ��������Դ
	 * @param sql Ҫִ�еĲ�ѯ���
	 * @return RowSet 
	 */
	RowSet getRowSetByJDBCsql(String sql);
}
