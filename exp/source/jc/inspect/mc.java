/*
 * �������� 2004-12-29
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package jc.inspect;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class mc
{
	String dbmc;
	String jzqmc;
	/**
	 * @return
	 */
	public String getDbmc() {
		return dbmc;
	}

	/**
	 * @return
	 */
	public String getJzqmc() {
		return jzqmc;
	}

	/**
	 * @param string
	 */
	public void setDbmc(String string) {
		dbmc = string;
	}

	/**
	 * @param string
	 */
	public void setJzqmc(String string) {
		jzqmc = string;
	}
	public mc(String d,String j)
	{
		this.dbmc=d;
		this.jzqmc=j;
	}
}
