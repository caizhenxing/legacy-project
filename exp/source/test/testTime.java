/*
 * �������� 2004-12-16
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package test;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class testTime {

	public static void main(String[] args) {
		long time=System.currentTimeMillis();
		for(long i=0;i<100000000;i++)
		{str s=new str();}
		System.out.println(System.currentTimeMillis()-time);
	}
}
