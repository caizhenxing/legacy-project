/*
 * �������� 2004-7-1
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package base;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class parent {
	protected String s=null;
	protected int i=0;
	public String getS()
	{return s;}
	public int getI()
	{return i;}
	public void addI(int a)
	{i+=a;}
	public void setS(String s)
	{this.s=s;}
	public parent(String s)
	{
		this.s=s;
	}
	public void test() 
    {
    }
 
    public parent() 
    {
        test();
    }
}
