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
public class testServer {

	public static void main(String[] args) {
		try
		{
			System.out.println("constructing");
			ProductImpl p1=new ProductImpl("ss");
			ProductImpl p2=new ProductImpl("aa");
			System.out.println("Binding server");
			Naming.rebind("p1",p1);
			Naming.rebind("p2",p2);
			System.out.println("ok!");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
