/*
 * �������� 2005-1-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testRmi;

/**
 * @author ��һ��
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
import java.rmi.*;
import java.rmi.server.*;
public class First {

	public static void main(String[] args) {
		try
		{
			String[] bindings=Naming.list("");
			for(int i=0;i<bindings.length;i++)
			{
				System.out.println(bindings[i]);
				
			}
		}catch(Exception s)
		{
			s.printStackTrace();
		}
	}
}
