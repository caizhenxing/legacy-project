/*
 * �������� 2004-12-14
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
public class testInheritance extends testfather{
	public void aa()
	{
		super.aa();
		System.out.println("test Inheritance");
		
		
	}
	private testInheritance()
	{
	}
	public static testInheritance getin()
	{
		testInheritance t=new testInheritance();
		return t;
	}
	public static void main(String[] args) {
		testfather tf=testInheritance.getin();//new testInheritance();
		tf.aa();
	}
}
abstract class testfather
{
	public  void aa()
	{
		System.out.println("test father!");
	}
}