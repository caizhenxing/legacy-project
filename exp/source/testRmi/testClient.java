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
public class testClient {

	public static void main(String[] args) 
	{

		try
		{
			String[] bindings=Naming.list("");
			for(int i=0;i<bindings.length;i++)
			{
				System.out.println(bindings[i]);
				Test c1=(Test)Naming.lookup(bindings[i]);
				System.out.println(c1.getD());
			}
			
		}catch(Exception re)
		{
			re.printStackTrace();
		}
	}
}
