/*
 * �������� 2004-12-13
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
public class testString1 {

	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		
		for(long i=0;i<10000000;i++)
		{
			
//			StringBuffer s=new StringBuffer("aaaaa");
//			s.append("aa");
//			s.append("bb");
//			s.append("aa");
//			s.append("aa");
			String s=new String("aaaaa");
			//String s="aaaaa";
			s+="aa";
			s+="bb";
			s+="aa";
			s+="aa";
		}
		System.out.println(System.currentTimeMillis()-start);
	}
}
