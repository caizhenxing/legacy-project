/*
 * �������� 2004-7-1
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package inheriance;
import base.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class childern extends parent{
	 public childern(String s)
	{
		super(s);
	}
	 public childern()
		{
			super();
		}
	public void setI(int i)
	{
		super.i=i;
	}
	public void child()
	{
		System.out.println("dddd");
	}
	public static void main(String args[])
	{
		//parent p=new childern("dd");
		//System.out.print(p.getS());
		new childern();
	}
	private int instanceValue = 20;
    public void test() 
    {
        System.out.println("instance value is: " + instanceValue);
    }

}
